#!/usr/bin/env groovy
def version() {
    echo 'incrementing app.vserion'
    sh 'mvn build-helper:parse-version vserions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit' # update the version in pom.xml file 
    def matcher=readFile('pom.xml') =~ '<version>(.+)</version>'
    def version=matcher[0][1]
    env.IMG="$version-$BUILD_NUMBER"
}
def buildjar() {
    sh 'mvn package'
}
def imagebuild() {
    sh "docker build -t 35.200.245.75:8083/${env.IMG}."
}
def imagepush() {
    withCredentials([usernamePassword(credentialsId: 'Nexus-repo', usernameVariable: 'USER', passwordVariable: 'PWD')]){
    sh "echo $PWD | docker login -u $USER --password-stdin 35.200.245.75:8083"
    sh "docker push 35.200.245.75:8083/${env.IMG}"
    }
}
def deploy() {
    echo 'deploying docker image to EC2...'

    def shellCmd = "bash ./server-cmds.sh ${IMG}"
    def ec2Instance = "ec2-user@35.180.251.121"

    sshagent(['ec2-server-key']) {
        sh "scp server-cmds.sh ${ec2Instance}:/home/ec2-user"
        sh "scp docker-compose.yaml ${ec2Instance}:/home/ec2-user"
        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"

    }
}
def push() {
    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'USER', passwordVariable: 'PWD')]){
        echo "pushing the updated image tag to github"
        sh 'git config user.name "Jenkins"'
        sh 'git config user.email "jenkins@example.com"'
        sh "git remote set-url origin https://${USER}:${PWD}@https://github.com/vilayilarun"
        sh 'git commit -m "commit from jenkins"'
        sh 'git push origin HEAD:java-maven-app'
    }
}
return this 
