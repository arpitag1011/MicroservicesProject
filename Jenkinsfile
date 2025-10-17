pipeline {
    agent any

    environment {
        DOCKER_HUB_USER = 'arpitag1011'
        DOCKER_HUB_CREDENTIALS = 'docker-hub-creds'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/arpitag1011/MicroservicesProject.git'
            }
        }

        stage('Build Microservices') {
            steps {
                dir('UserService') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('OrderService') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh "docker build -t ${arpitag1011}/user-service ./UserService"
                    sh "docker build -t ${arpitag1011}/order-service ./OrderService"
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDENTIALS}", usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh "docker push ${arpitag1011}/user-service"
                    sh "docker push ${arpitag1011}/order-service"
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                sh "docker run -d -p 8081:8081 ${arpitag1011}/user-service"
                sh "docker run -d -p 8082:8082 ${arpitag1011}/order-service"
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
