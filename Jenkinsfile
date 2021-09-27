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
    }
}

