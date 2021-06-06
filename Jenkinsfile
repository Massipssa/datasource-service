pipeline {
    agent any
    tools { 
        maven 'Maven3.8.1' 
        jdk 'jdk8' 
    }
    stages {
        
        /*
        stage('Clone sources') {
            steps {
                git branch: 'master',
                    credentialsId: '$GITHUB-CRED' 
                    url: 'https://github.com/Massipssa/datasource-service.git'
            }
        }
        */
        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
        }
        
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv("SonarQube") {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage("Quality Gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}
