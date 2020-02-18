#!/bin/sh

APP_HOME=$(cd "$(dirname "$0")/.."; pwd)
appname=check-0.0.1-SNAPSHOT.jar
$APP_HOME/bin/daemon.sh stop ${appname}
