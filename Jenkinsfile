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
                    //echo 'Building the jar file'
                    //sh 'mvn package'
                }
            }
        }
        stage("Build docker image"){
            steps{
                script{
                    gv.imagebuild()
                   //sh 'docker build -t 192.168.179.131:8083/java-manen-app:1.1'
                }
            }
        }
        stage("push the image to nexus repository"){
            steps{
                script{
                   gv.imagepush()
                   // withCredentials([usernamePassword(credentialsId: 'Nexus-repo', usernameVariable: 'USER', passwordVariable: 'PWD')]){
                    //sh 'echo $PWD | docker login -u $USER --pasword-stdin 192.168.179.131:8083'
                    //sh 'docker push 192.168.179.131:8083/java-manen-app:1.1'
                    //}
                }
            }
        }
    }
}

