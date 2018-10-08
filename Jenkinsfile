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
			when { branch 'master' }
			steps {
				script {
					currentBuild.displayName = currentBuild.number + "-déploiementUnix"

					// Rebuild de tout pour ne plus avoir d'instrumentation
					sh "mvn clean install -Dmaven.test.skip=true -Punix -pl !web-angular,!web-vuejs"

					// Déploiement sur le serveur Unix des APIs
					sh "/var/lib/deployJava/stopApplicationBlanche.sh"
					sh "rm /var/lib/deployJava/applicationBlancheRest.jar || true"
					sh "cp ./rest/target/rest-1.0.0.jar /var/lib/deployJava/applicationBlancheRest.jar"

					// Déploiement sur le serveur Unix de l'application Angular
					sh "rm -rf /var/www/html/applicationBlanche/* || true"
					sh "cp -r ./web-angular/dist/unix/applicationBlanche/* /var/www/html/applicationBlanche"

					// Démarrage de l'application
					// @see https://issues.jenkins-ci.org/browse/JENKINS-28182
					sh "BUILD_ID=dontKillMe JENKINS_NODE_COOKIE=dontKillMe /var/lib/deployJava/startApplicationBlanche.sh"

					currentBuild.displayName = "#" + currentBuild.number
				}
			}
		}

		stage ('Deploy GCloud') {
			agent any
			environment { JAVA_HOME = '/usr/lib/jvm/java-8-openjdk-amd64/' }
			when { branch 'master' }
			steps {
				script {
					currentBuild.displayName = currentBuild.number + "-déploiementGcloud"

					// Rebuild avec le profil gcloud et en Java8
					sh "mvn clean install -Dmaven.test.skip -Pgcloud -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 -pl !web-angular,!web-vuejs"

					// Déploiement du back sur le could
					sh "mvn appengine:deploy -Dmaven.test.skip=true -Pgcloud -f rest/pom.xml -Dmaven.compiler.source=8 -Dmaven.compiler.target=8"
					
					// Déploiement du front sur le cloud
					sh "gcloud app deploy web-angular/app.yaml -q"

					currentBuild.displayName = "#" + currentBuild.number
				}
			}
		}
	}
}
