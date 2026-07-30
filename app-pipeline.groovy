pipeline {
    agent any 
    stages {
        stage ('PULL') {
            steps {
                git branch: 'test', url: 'https://github.com/jambhulkarcloudblitz-alt/CDEC-studentapp.git'
        }
        }
        stage ('FRONTEND-DOCKER-IMAGE-BUILD'){
            steps {
                    sh '''cd frontend 
                        docker build -t komal0116/3-tier-docker:frontendjenkinsdocker .'''
            }
        }
        stage ('Backend-DOCKER-IMAGE-BUILD') {
            steps {
                sh '''cd backend
                        docker build -t komal0116/3-tier-docker:backendjenkinsdocker .'''
            }
        }
        stage ('PUSH-FRONTEND-BACKEND-IMAGE') {
            steps {
                sh '''docker push komal0116/3-tier-docker:frontendjenkinsdocker
                        docker push komal0116/3-tier-docker:backendjenkinsdocker'''
            }
        }
        stage ('APPLY') {
            steps {
                sh 'kubectl apply -f simple-deploy/'
            }
        }
    }
}