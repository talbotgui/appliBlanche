#!groovy

// Define job properties
properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), pipelineTriggers([])])

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
			steps {
				script { currentBuild.displayName = currentBuild.number + "-build" }
				sh "mvn clean install --batch-mode"
				junit '**/TEST-*Test.xml'
			}
		}

		stage ('Quality') {
			agent any
			steps {
				script { currentBuild.displayName = currentBuild.number + "-qualimétrie Maven" }
				sh "mvn angular:analyse -pl web-angular --batch-mode"
				sh "mvn clean install site -Pqualimetrie --batch-mode"
				step([$class: 'FindBugsPublisher'])
				step([$class: 'CheckStylePublisher'])
				step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd.xml', unHealthy: ''])
				step([$class: 'AnalysisPublisher'])
				// see https://docs.sonarqube.org/display/SONAR/Analysis+Parameters
				script { currentBuild.displayName = currentBuild.number + "-qualimétrie Sonar" }
				withCredentials([string(credentialsId: 'sonarSecretKey', variable: 'SONAR_KEY')]) {
					sh "mvn sonar:sonar -Dsonar.projectKey=ApplicationBlanche -Dsonar.organization=talbotgui-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_KEY} -Dsonar.java.coveragePlugin=jacoco -Dsonar.coverage.exclusions=**/*.ts  --batch-mode"
				}
			}
		}

		stage ('Deploy Unix') {
			agent any
			when { branch 'master' }
			steps {
				script {
					currentBuild.displayName = currentBuild.number + "-déploiementUnix"

					// Rebuild de tout pour ne plus avoir d'instrumentation
					sh "mvn clean install -Dmaven.test.skip=true -Punix -pl !web-angular,!web-vuejs  --batch-mode"

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
			agent none
			when { branch 'master' }
			steps {
				// Pour ne pas laisser trainer l'attente d'une saisie durant plus de 1 jour
				timeout(time:1, unit:'DAYS') {
					script {
					
						// Demande de saisie avec milestone pour arrêter les builds précédents en attente au moment où un utilisateur répond à un build plus récent
						milestone(1)
						def userInput = input message: 'Production ?', parameters: [booleanParam(defaultValue: false, description: '', name: 'miseEnProduction')]
						milestone(2)

						// Installation en production et changement du nom indiquant le statut
						if (userInput) {
							node {
								currentBuild.displayName = currentBuild.number + "-déploiementGcloud"

								// Rebuild avec le profil gcloud et en Java8
								sh "mvn clean install -Dmaven.test.skip -Pgcloud -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 -pl !web-angular,!web-vuejs  --batch-mode"

								// Mise en place du fichier de configuration stocké dans Jenkins
								withCredentials([file(credentialsId: 'APPLICATIONBLANCHE-GCLOUD-APP-PROPERTIES', variable: 'applicationblancheGcloudAppProperties')]) {
									sh "cp -f \$applicationblancheGcloudAppProperties $WORKSPACE/rest/src/main/resources/application.properties"
								}

								// Déploiement du back sur le could
								sh "gcloud auth activate-service-account --key-file=/var/lib/deployJava/gcloud-applicationblanche.json"
								sh "gcloud config set project applicationblanche"
								sh "mvn appengine:deploy -Dmaven.test.skip=true -Pgcloud -f rest/pom.xml -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 --batch-mode"
								
								// Déploiement du front sur le cloud
								sh "gcloud app deploy web-angular/app.yaml -q --promote --stop-previous-version"

								currentBuild.displayName = "#" + currentBuild.number
							}
						}
					}
				}
			}
		}
		
		stage ('Clean') {
			agent any
			steps {
				cleanWs notFailBuild: true
			}
		}
	}
}
