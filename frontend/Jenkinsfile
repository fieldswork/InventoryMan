pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh "echo Building - Stage 1"
                sh "echo Building - please lol"
            }
        }

        stage('Test') {
            steps {
                sh "echo Testing - Stage 2"
            }
        }

        stage('Deploy') {
            steps {
                sh "echo Deployment - Stage 3"
            }
        }
    }
}