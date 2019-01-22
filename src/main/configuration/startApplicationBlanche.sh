#!/bin/bash

echo "Démarrage de l'application blanche"
cd /var/lib/deployJava
nohup /usr/lib/jvm/jdk-11.0.2/bin/java -jar /var/lib/deployJava/applicationBlancheRest.jar >/var/lib/deployJava/nohup.out 2>&1 &
echo "Application démarrée"
