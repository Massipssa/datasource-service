pipeline {
    agent any
    stage('build'){
        withMaven(maven: 'mvn') {
            sh "mvn clean package"
        }
    }
}
