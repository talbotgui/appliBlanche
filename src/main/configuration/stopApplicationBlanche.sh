#!/bin/bash

echo "Arrêt de l'application blanche"
APP_ID=$(ps -ef | grep applicationBlancheRest.jar | grep -v grep | awk '{print $2}')

if [ -n "${APP_ID}" ]; then
  kill -9 ${APP_ID}
fi
