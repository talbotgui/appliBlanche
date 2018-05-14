#!groovy

// Define job properties
properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), pipelineTriggers([]), disableConcurrentBuilds()])

pipeline {
	
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
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
			steps {
				sh "mvn clean install -Dmaven.test.skip=true"
			}
		}

		stage ('Developement test') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
			steps {
				sh "mvn -B -Dmaven.test.failure.ignore test-compile surefire:test  -Djvm=/usr/lib/jvm/jdk-10.0.1/"
				junit '**/TEST-*Test.xml'
			}
		}

		stage ('Quality') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
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
}
