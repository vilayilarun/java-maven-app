#!/usr/bin/env groovy
def version() {
    echo 'incrementing app.vserion'
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit' 
    def matcher=readFile('pom.xml') =~ '<version>(.+)</version>'
    def version=matcher[0][1]
    env.IMG="$version-$BUILD_NUMBER"
}
def buildjar() {
    sh 'mvn clean package'
}
def imagebuild() {
    sh "docker build -t 35.200.245.75:8083/java-maven:${env.IMG} ."
}
def imagepush() {
    withCredentials([usernamePassword(credentialsId: 'Nexus-repo', usernameVariable: 'USER', passwordVariable: 'PWD')]){
    sh "echo $PWD | docker login -u $USER --password-stdin 35.200.245.75:8083"
    sh "docker push 35.200.245.75:8083/java-maven:${env.IMG}"
    }
}
/* terrafrom codes for provisioning infrastracture*/
def provisioning() {
        sh "terraform init"
        sh "terrrafrom apply --auto-approve"
        EC2_PUBLIC_IP = sh(
          script: "terraform output ec2-public-ip"
          returnStdout: true
        ).trim()
    }
}
def deploy() {
    echo "waiting for the instance is come up"
    sleep(time: 120, unit: "SECONDS")
    echo 'deploying docker image to EC2...'
    echo ${EC2_PUBLIC_IP}

    def shellCmd = "bash ./server-cmds.sh ${IMG}"
    def ec2Instance = "ec2-user@${EC2_PUBLIC_IP}"

    sshagent(['ec2-aws']) {
        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"

    }
}
def push() {
    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'USER', passwordVariable: 'PWD')]){
        echo "pushing the updated image tag to github"
        sh 'git config user.name "Jenkins"'
        sh 'git config user.email "jenkins@example.com"'
        sh "git remote set-url origin https://${USER}:${PWD}@github.com/vilayilarun/java-maven-app.git"
        sh 'git add .'
        sh 'git commit -m "Jenkins commit CI/CD"'
        sh 'git push origin HEAD:master'
    }
}
return this 
