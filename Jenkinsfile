pipeline {
    agent any

    environment {
        PATH = "/usr/bin:$PATH"
        HEADLESS = 'true'
    }

    stages {
        stage('Build Frontend') {
            steps {
                sh "echo Building frontend"
                sh "cd frontend && npm install && npm run build"
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
        stage('Build Backend and Run Tests') {
            steps {
                sh "cd backend && mvn clean install && ls target/"
            }
        }
        stage('Deploy Backend') {
            steps {
                script {
                    withAWS(region: 'us-east-1', credentials: 'AWS_CREDENTIALS') {
                        def versionLabel = "0.0.1-${new Date().format('yyyyMMddHHmmss')}"
                        def s3Key = "inventoryman-${versionLabel}.jar"

                        sh "aws s3 cp backend/target/inventoryman-0.0.1-SNAPSHOT.jar s3://inventoryman-backend/${s3Key}"
                        sh "aws elasticbeanstalk create-application-version --application-name InventoryMan --version-label ${versionLabel} --source-bundle S3Bucket=inventoryman-backend,S3Key=${s3Key}"
                        sh "aws elasticbeanstalk update-environment --environment-name InventoryMan-env --version-label ${versionLabel}"
                    }
                }
            }
        }
    }
}
