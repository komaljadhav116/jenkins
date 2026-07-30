pipeline {
    agent any 
    stages {
        stage ('PULL') {
            steps {
                git branch: 'main', url: 'https://github.com/komaljadhav116/CDEC-studentapp.git'
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
                // This step should not normally be used in your script. Consult the inline help for details.
                  withDockerRegistry(credentialsId: 'docker-cred', url: 'https://index.docker.io/v1/') {
                 sh '''docker push komal0116/3-tier-docker:frontendjenkinsdocker
                        docker push komal0116/3-tier-docker:backendjenkinsdocker'''
               }
            }
        }
        stage ('APPLY') {
            steps {
                sh 'kubectl apply -f simple-deploy/'
            }
        }
    }
}