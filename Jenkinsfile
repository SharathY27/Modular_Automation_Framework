pipeline {
    agent any

    tools {
        jdk 'Java 21'            // Make sure this matches the name in Jenkins
        maven 'Maven 3.9.9'      // Use the configured Maven version
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: '**',
                    url: 'https://github.com/SharathY27/Modular_Automation_Framework.git'
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
            junit 'target/surefire-reports/*.xml'
        }
    }
}
