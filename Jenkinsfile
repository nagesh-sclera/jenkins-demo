pipeline {
    agent any

    environment {
        APP_NAME       = 'springboot-demo'
        IMAGE_NAME     = "springboot-demo:${BUILD_NUMBER}"
        CONTAINER_NAME = 'springboot-demo-container'
        HOST_PORT      = '9090'
        APP_PORT       = '8080'
    }

    tools {
        maven 'Maven-3.9'   // Must match name in Jenkins > Global Tool Configuration
        jdk   'JDK-17'      // Must match name in Jenkins > Global Tool Configuration
    }

    stages {

        stage('Checkout') {
            steps {
                echo "========== Checking out code =========="
                checkout scm
            }
        }

        stage('Code Compile') {
            steps {
                echo "========== Compiling project =========="
                sh 'mvn compile -B'
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo "========== Running Tests =========="
                sh 'mvn test -B'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
                failure {
                    echo "Tests failed! Pipeline stopped."
                }
            }
        }

        stage('Package JAR') {
            steps {
                echo "========== Packaging JAR =========="
                sh 'mvn clean package -DskipTests -B'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "========== Building Docker Image: ${IMAGE_NAME} =========="
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Stop Old Container') {
            steps {
                echo "========== Stopping old container (if any) =========="
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm   ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Deploy') {
            steps {
                echo "========== Deploying Spring Boot App =========="
                sh """
                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p ${HOST_PORT}:${APP_PORT} \
                        --restart unless-stopped \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        ${IMAGE_NAME}
                """
                echo "App deployed at http://localhost:${HOST_PORT}/api/hello"
            }
        }

        stage('Health Check') {
            steps {
                echo "========== Health Check =========="
                sh """
                    echo "Waiting for app to start..."
                    sleep 20
                    curl -f http://localhost:${HOST_PORT}/actuator/health || exit 1
                    echo "Health check passed!"
                """
            }
        }

        stage('Cleanup Old Images') {
            steps {
                echo "========== Cleanup =========="
                sh "docker image prune -f || true"
            }
        }
    }

    post {
        success {
            echo """
            ✅ PIPELINE SUCCEEDED!
            App URL   : http://localhost:${HOST_PORT}/api/hello
            Health URL: http://localhost:${HOST_PORT}/actuator/health
            Build #   : ${BUILD_NUMBER}
            """
        }
        failure {
            echo "❌ PIPELINE FAILED — check logs above"
            sh "docker stop ${CONTAINER_NAME} || true"
        }
        always {
            cleanWs()
        }
    }
}
