pipeline {
    agent any

    tools {
        jdk 'Java 21'           // Must match name in Jenkins Global Tool Configuration
        maven 'Maven 3.9.9'     // Must match Maven installation name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Jenkins automatically checks out the correct branch in Multibranch Pipeline
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Report') {
            steps {
                // Publish HTML test report (requires HTML Publisher plugin)
                publishHTML(target: [
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'emailable-report.html',
                    reportName: 'TestNG Report'
                ])
            }
        }
    }

    post {
        always {
            // Collect test results
            junit 'target/surefire-reports/*.xml'
        }
    }
}
