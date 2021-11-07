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

