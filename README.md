# appliBlanche

Le guide du développeur de ce projet est disponible ici : https://talbotgui.github.io/mesprojets/


# GCloud
## Prérequis :

* Créer une base de données SQL de type PostgreSQL.
* Activer les API SQL et SQL Admin

## Commandes à exécuter pour dépoyer :

* gcloud auth login
* gcloud config set project applicationblanche
* export JAVA_HOME="C:\Program Files\Java\jdk1.8.0_161"
* mvn clean install -Dmaven.test.skip -Pgcloud -Dmaven.compiler.source=8 -Dmaven.compiler.target=8
* mvn appengine:deploy -Dmaven.test.skip -Pgcloud -f rest/pom.xml  -Dmaven.compiler.source=8 -Dmaven.compiler.target=8
