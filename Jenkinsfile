#!groovy

// Define job properties
properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), pipelineTriggers([]), disableConcurrentBuilds()])

pipeline {
    tools {
        maven "M3"
    }
	
	agent none

	stages {
		
		stage ('Checkout') {
			agent any
			steps {
				checkout scm
			}
		}

		stage ('Build') {
			agent any
			steps {
				script { env.PATH = "${tool 'M3'}/bin:${env.PATH}" }
				sh "mvn clean install -Dmaven.test.skip=true"
			}
		}

		stage ('Developement test') {
			agent any
			steps {
				sh "mvn -B -Dmaven.test.failure.ignore test-compile surefire:test"
				junit '**/TEST-*Test.xml'
			}
		}

		stage ('Quality') {
			agent any
			steps {
				sh "mvn clean install site -Psite"
				step([$class: 'FindBugsPublisher'])
				step([$class: 'CheckStylePublisher'])
				step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd.xml', unHealthy: ''])
				step([$class: 'AnalysisPublisher'])
				// see https://docs.sonarqube.org/display/SONAR/Analysis+Parameters
				withCredentials([string(credentialsId: 'sonarSecretKey', variable: 'SONAR_KEY')]) {
					sh "mvn sonar:sonar -Dsonar.host.url=https://sonarqube.com/ -Dsonar.projectKey=com.github.talbotgui.applicationBlanche:applicationBlanche -Dsonar.login=${SONAR_KEY}"
				}
			}
		}
	}
	
	post {
        // success {}
        // unstable {}
        // failure {}
        //always {}
        //changed {}
    }
}
