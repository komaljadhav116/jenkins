pipeline {
    agent any
    parameters {
        choice(
            name: 'action',
            choices: ['apply', 'destroy'],
            description: 'Select the Terraform action what to perform apply or destroy'
        )
    }
    stages {
        stage ('pull') {
            steps {
                git branch: 'main', credentialsId: 'ssh-key-gihub', url: 'https://github.com/komaljadhav116/Terraform-Notes.git'
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
            when {
                expression { params.action == 'apply' }
            }
            steps {
                sh '''
                      cd eks-cluster-tf
                      terraform apply --auto-approve'''
            }
        }
        stage ('destroy') {
            when {
                expression { params.action == 'destroy' }
            }
            steps {
                sh '''
                      cd eks-cluster-tf
                      terraform destroy --auto-approve'''
            }
        }
    }
}
