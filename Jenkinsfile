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
}

