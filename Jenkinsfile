def gv
pipeline{
    agent any
    tools{
    maven 'maven'
    }
    stages{
        stage("Load the groovy script"){
           steps{
              script{
              gv = load "script.groovy"
              }
           }
        }
        stage("Version update") {
            steps{
                script{
                    gv.version()    
                }
            }
        }
        stage("build jar file"){
            steps{
                script{
                    gv.buildjar()
                }
            }
        }
        stage("Build docker image"){
            steps{
                script{
                    gv.imagebuild()
                }
            }
        }
        stage("push the image to nexus repository"){
            steps{
                script{
                   gv.imagepush()
                }
            }
        }
        stage("provision") {
            environment{
                AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
            }
            steps{
                script{
                    dir('terraform'){
                        gv.provisioning()
                    }
                }
            }
        }
        stage("deploy image") {
            steps{
                script{
                   gv.deploy()
                }
            }
        }
        stage("Pushing the tag back to repository"){
            steps{
                script{
                    gv.push()
                }
            }
        }
    }
    post{  
        always {  
            echo 'This will always run'  
        }  
        success {  
            echo 'This will run only if successful'  
        }  
        failure {  
            mail bcc: '', body: "<b>Jenkins-JOB</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR CI: Project name -> ${env.JOB_NAME}", to: "vilayilarun@gmail.com";  
        }  
        unstable {  
            echo 'This will run only if the run was marked as unstable'  
        }  
        changed {  
            echo 'This will run only if the state of the Pipeline has changed'  
            echo 'For example, if the Pipeline was previously failing but is now successful'  
        }  
    }         
 
}
