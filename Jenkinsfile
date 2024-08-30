pipeline {
    agent any

    environment {
        PATH = "/usr/bin:$PATH"
        HEADLESS = 'true'
        OUT = 'jmeter.save.saveservice.output_format'
        JMX = '/opt/apache-jmeter-5.6.3/bin/InventoryMan_JMeter_Test.jmx'
        JTL = '/opt/apache-jmeter-5.6.3/reports/InventoryMan_JMeter_Test.report.jtl'
    }

    stages {
        stage('Build Frontend') {
            steps {
                sh "echo Building frontend"
                sh "cd frontend && npm install && npm run build"
            }
        }
        stage('Build Backend') {
            steps {
                sh "cd backend && mvn clean install -DskipTests=true" // skip tests for deployment
            }
        }
        stage('Deploy Frontend') {
            steps {
                script {
                    try {
                        withAWS(region: 'us-east-1', credentials: 'AWS_CREDENTIALS') {
                            sh "aws s3 sync frontend/build s3://inventoryman"
                        }
                    } catch (Exception e) {
                        echo "${e}"
                        throw e
                    }
                }
            }
        }
        stage('Deploy Backend') {
            steps {
                script {
                    withAWS(region: 'us-east-1', credentials: 'AWS_CREDENTIALS') {
                        def versionLabel = "0.0.1-${new Date().format('yyyyMMddHHmmss')}" // change version number here
                        def s3Key = "inventoryman-${versionLabel}.jar"

                        sh "aws s3 cp backend/target/inventoryman-0.0.1-SNAPSHOT.jar s3://inventoryman-backend/${s3Key}"
                        sh "aws elasticbeanstalk create-application-version --application-name InventoryMan --version-label ${versionLabel} --source-bundle S3Bucket=inventoryman-backend,S3Key=${s3Key}"
                        sh "aws elasticbeanstalk update-environment --environment-name InventoryMan-env --version-label ${versionLabel}"
                    }
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    def frontendReady = false
                    def backendReady = false

                    // Wait for frontend deployment
                    echo "Waiting for frontend deployment..."

                    retry(5) { // Retry up to 5 times
                        sleep(10) // Wait 10 seconds between each retry
                        def response = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://inventoryman.s3-website-us-east-1.amazonaws.com/", returnStdout: true).trim()
                        if (response == '200') {
                            frontendReady = true
                        } else {
                            echo "Frontend response: ${response}"
                            error "Frontend not ready yet, retrying..."
                        }
                    }

                    // Wait for backend deployment before tests begin
                    echo "Waiting for backend deployment..."
                    retry(5) {
                        sleep(10)
                        def response = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" http://inventoryman-env.us-east-1.elasticbeanstalk.com:8080/api/warehouses", returnStdout: true).trim()
                        if (response == '200') {
                            backendReady = true
                        } else {
                            echo "Backend response: ${response}"
                            error "Backend not ready yet, retrying..."
                        }
                    }

                    // If both frontend and backend are ready, proceed with tests
                    if (frontendReady && backendReady) {
                        sh "cd backend && mvn test jacoco:report" // Run Selenium, Cucumber, and Mockito tests, generate coverage report
                    } else {
                        error "One or both services are not ready. Aborting tests."
                    }
                }
            }
        }
        stage('Archive JaCoCo Report') {
            steps {
                archiveArtifacts artifacts: 'backend/target/site/jacoco/*', allowEmptyArchive: true
            }
        }
        stage('Analyze Frontend with SonarCloud') {
            steps {
                script {
                    withSonarQubeEnv('SonarCloud') {
                        dir('frontend') {
                            sh '''
                                npx sonar-scanner \
                                    -Dsonar.projectKey=salmoncore_InventoryMan \
                                    -Dsonar.projectName=InventoryMan \
                                    -Dsonar.sources=src \
                                    -Dsonar.exclusions=**/__tests__/** \
                                    -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info
                                ''' // Project: https://sonarcloud.io/project/information?id=salmoncore_InventoryMan
                        }
                    }
                }
            }
        }
        stage('Analyze Backend with SonarCloud') {
            steps {
                script {
                    withSonarQubeEnv('SonarCloud') {
                        dir('backend') {
                            sh '''
                                mvn sonar:sonar \
                                    -Dsonar.projectKey=salmoncore_inventoryman-backend \
                                    -Dsonar.projectName=InventoryMan_Backend \
                                    -Dsonar.java.binaries=target/classes \
                                    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                                ''' // Project: https://sonarcloud.io/project/information?id=salmoncore_inventoryman-backend
                        }
                    }
                }
            }
        }
        stage('Run JMeter Test') {
            steps {
                script {
                    sh """
                        /opt/apache-jmeter-5.6.3/bin/jmeter -j \$OUT=xml -n -t \$JMX -l \$JTL
                    """
                }
            }
        }
        stage('Publish JMeter Report') {
            steps {
                perfReport filterRegex: '', sourceDataFiles: '/opt/apache-jmeter-5.6.3/reports/InventoryMan_JMeter_Test.report.jtl'
            }
        }
    }
}
