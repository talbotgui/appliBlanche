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

		stage ('Build & Tests') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
			steps {
				script { currentBuild.displayName = currentBuild.number + "-build" }
				sh "mvn clean install"
				junit '**/TEST-*Test.xml'
			}
		}

		stage ('Quality') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
			steps {
				script { currentBuild.displayName = currentBuild.number + "-qualimétrie Maven" }
				sh "mvn clean install site -Pqualimetrie"
				step([$class: 'FindBugsPublisher'])
				step([$class: 'CheckStylePublisher'])
				step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd.xml', unHealthy: ''])
				step([$class: 'AnalysisPublisher'])
				// see https://docs.sonarqube.org/display/SONAR/Analysis+Parameters
				script { currentBuild.displayName = currentBuild.number + "-qualimétrie Sonar" }
				withCredentials([string(credentialsId: 'sonarSecretKey', variable: 'SONAR_KEY')]) {
					sh "mvn sonar:sonar -Dsonar.projectKey=ApplicationBlanche -Dsonar.organization=talbotgui-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_KEY}"
				}
			}
		}

		stage ('Deploy Unix') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/jdk-10.0.1/' }
			steps {
				script {
					currentBuild.displayName = currentBuild.number + "-déploiement"

					sh "mvn clean install -Dmaven.test.skip=true -Punix"

					sh "/var/lib/deployJava/stopApplicationBlanche.sh"
					sh "rm -rf /var/www/html/applicationBlanche/* || true"
					sh "cp -r ./web-angular/dist/applicationBlanche/* /var/www/html/applicationBlanche"
					sh "rm /var/lib/deployJava/applicationBlancheRest.jar || true"
					sh "cp ./rest/target/rest-1.0.0.jar /var/lib/deployJava/applicationBlancheRest.jar"
					// @see https://issues.jenkins-ci.org/browse/JENKINS-28182
					sh "BUILD_ID=dontKillMe JENKINS_NODE_COOKIE=dontKillMe /var/lib/deployJava/startApplicationBlanche.sh"

					currentBuild.displayName = "#" + currentBuild.number
				}
			}
		}
	}
}
