pipeline {
    agent any
    
    environment {
        DOCKER_BUILDKIT = '1'
        COMPOSE_DOCKER_CLI_BUILD = '1'
    }

    tools {
        maven 'Maven 3.9'
        jdk 'JDK 21'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }
        
        stage('Build All Services') {
            parallel {
                stage('Build Eureka Server') {
                    steps {
                        dir('eureka_server') {
                            echo 'Building Eureka Server...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build API Gateway') {
                    steps {
                        dir('api_gateway') {
                            echo 'Building API Gateway...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build Anggota Service') {
                    steps {
                        dir('anggota') {
                            echo 'Building Anggota Service...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build Buku Service') {
                    steps {
                        dir('buku') {
                            echo 'Building Buku Service...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build Peminjaman Service') {
                    steps {
                        dir('peminjaman') {
                            echo 'Building Peminjaman Service...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build Pengembalian Service') {
                    steps {
                        dir('pengembalian') {
                            echo 'Building Pengembalian Service...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
                
                stage('Build Email Service') {
                    steps {
                        dir('email') {
                            echo 'Building Email Service...'
                            bat 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }
        
        stage('Test All Services') {
            parallel {
                stage('Test Eureka Server') {
                    steps {
                        dir('eureka_server') {
                            echo 'Testing Eureka Server...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test API Gateway') {
                    steps {
                        dir('api_gateway') {
                            echo 'Testing API Gateway...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test Anggota Service') {
                    steps {
                        dir('anggota') {
                            echo 'Testing Anggota Service...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test Buku Service') {
                    steps {
                        dir('buku') {
                            echo 'Testing Buku Service...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test Peminjaman Service') {
                    steps {
                        dir('peminjaman') {
                            echo 'Testing Peminjaman Service...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test Pengembalian Service') {
                    steps {
                        dir('pengembalian') {
                            echo 'Testing Pengembalian Service...'
                            bat 'mvn test'
                        }
                    }
                }
                
                stage('Test Email Service') {
                    steps {
                        dir('email') {
                            echo 'Testing Email Service...'
                            bat 'mvn test'
                        }
                    }
                }
            }
        }
        
        stage('Build Docker Images') {
            parallel {
                stage('Docker Build Eureka Server') {
                    steps {
                        script {
                            echo 'Building Docker image for Eureka Server...'
                            bat 'docker build -t eureka-server:latest ./eureka_server'
                        }
                    }
                }
                
                stage('Docker Build API Gateway') {
                    steps {
                        script {
                            echo 'Building Docker image for API Gateway...'
                            bat 'docker build -t api-gateway:latest ./api_gateway'
                        }
                    }
                }
                
                stage('Docker Build Anggota') {
                    steps {
                        script {
                            echo 'Building Docker image for Anggota Service...'
                            bat 'docker build -t anggota-service:latest ./anggota'
                        }
                    }
                }
                
                stage('Docker Build Buku') {
                    steps {
                        script {
                            echo 'Building Docker image for Buku Service...'
                            bat 'docker build -t buku-service:latest ./buku'
                        }
                    }
                }
                
                stage('Docker Build Peminjaman') {
                    steps {
                        script {
                            echo 'Building Docker image for Peminjaman Service...'
                            bat 'docker build -t peminjaman-service:latest ./peminjaman'
                        }
                    }
                }
                
                stage('Docker Build Pengembalian') {
                    steps {
                        script {
                            echo 'Building Docker image for Pengembalian Service...'
                            bat 'docker build -t pengembalian-service:latest ./pengembalian'
                        }
                    }
                }
                
                stage('Docker Build Email') {
                    steps {
                        script {
                            echo 'Building Docker image for Email Service...'
                            bat 'docker build -t email-service:latest ./email'
                        }
                    }
                }
            }
        }
        
        stage('Verify Images') {
            steps {
                echo 'Verifying Docker images...'
                bat 'docker images | findstr "eureka-server api-gateway anggota-service buku-service peminjaman-service pengembalian-service email-service"'
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline completed successfully!'
            echo 'All services built and Docker images created.'
        }
        failure {
            echo '❌ Pipeline failed!'
            echo 'Check the logs for details.'
        }
        always {
            echo 'Cleaning up workspace...'
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '**/target/**', type: 'INCLUDE']])
        }
    }
}
