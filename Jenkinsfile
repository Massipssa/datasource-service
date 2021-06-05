pipeline {
    agent any
    tools { 
        maven 'Maven3.8.1' 
        jdk 'jdk8' 
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }

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
