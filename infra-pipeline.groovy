pipeline {
    agent any
    stages {
        stage ('pull') {
            steps {
                git branch: 'main', url: 'https://github.com/komaljadhav116/Terraform-Notes.git'
            }
        }
        stage ('Plan') {
            steps {
               sh '''
                    cd eks-cluster-tf
                    terraform init
                    terraform plan'''
            }
        }
        stage ('Approve') {
            steps {
                timeout(30) {
                        input 'Shall we procced? ok: Approved'
                            }
               
            }
        }
        stage ('Apply') {
            steps {
                sh '''terraform apply --auto-approve'''
            }
        }
    }
}