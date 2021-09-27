def buildjar() {
    sh 'mvn package'
}
def imagebuild() {
    sh 'docker build -t 192.168.179.131:8083/java-manen-app:1.1'
}
def imagepush() {
    withCredentials([usernamePassword(credentialsId: 'Nexus-repo', usernameVariable: 'USER', passwordVariable: 'PWD')]){
    sh "echo $PWD | docker login -u $USER --pasword-stdin 192.168.179.131:8083"
    sh 'docker push 192.168.179.131:8083/java-manen-app:1.1'
    }
}
return this 
