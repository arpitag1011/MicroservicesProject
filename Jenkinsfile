// pipeline {
//     agent any

//     environment {
//         DOCKER_HUB_USER = 'arpitag1011'
//         DOCKER_HUB_CREDENTIALS = 'docker-hub-creds'
//     }

//     stages {
//         stage('Clone Repository') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/arpitag1011/MicroservicesProject.git'
//             }
//         }

//         stage('Build Microservices') {
//             steps {
//                 dir('UserService') {
//                     sh 'mvn clean package -DskipTests'
//                 }
//                 dir('OrderService') {
//                     sh 'mvn clean package -DskipTests'
//                 }
//             }
//         }

//         stage('Build Docker Images') {
//             steps {
//                 script {
//                     sh "docker build -t ${arpitag1011}/user-service ./UserService"
//                     sh "docker build -t ${arpitag1011}/order-service ./OrderService"
//                 }
//             }
//         }

//         stage('Push to Docker Hub') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDENTIALS}", usernameVariable: 'USER', passwordVariable: 'PASS')]) {
//                     sh 'echo $PASS | docker login -u $USER --password-stdin'
//                     sh "docker push ${arpitag1011}/user-service"
//                     sh "docker push ${arpitag1011}/order-service"
//                 }
//             }
//         }

//         stage('Deploy Containers') {
//             steps {
//                 sh "docker run -d -p 8081:8081 ${arpitag1011}/user-service"
//                 sh "docker run -d -p 8082:8082 ${arpitag1011}/order-service"
//             }
//         }
//     }

//     post {
//         success {
//             echo 'Deployment successful!'
//         }
//         failure {
//             echo 'Pipeline failed!'
//         }
//     }
// }
pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-creds'
        DOCKER_HUB_USERNAME = 'arpitag1011'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/arpitag1011/MicroservicesProject.git'
            }
        }

        stage('Build UserService') {
            steps {
                dir('UserService') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build OrderService') {
            steps {
                dir('OrderService') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_HUB_CREDENTIALS) {
                        def userServiceImage = docker.build("${DOCKER_HUB_USERNAME}/userservice:1.0", "UserService/")
                        userServiceImage.push()
                        def orderServiceImage = docker.build("${DOCKER_HUB_USERNAME}/orderservice:1.0", "OrderService/")
                        orderServiceImage.push()
                    }
                }
            }
        }

        stage('Deploy Services') {
            steps {
                sh '''
                docker stop userservice || true
                docker stop orderservice || true
                docker rm userservice || true
                docker rm orderservice || true
                docker run -d --name userservice -p 8081:8081 ${DOCKER_HUB_USERNAME}/userservice:1.0
                docker run -d --name orderservice -p 8082:8082 ${DOCKER_HUB_USERNAME}/orderservice:1.0
                '''
            }
        }
    }
}
