#!/bin/sh

APP_HOME=$(cd "$(dirname "$0")/.."; pwd)
appname=heartBeat-1.2.jar
$APP_HOME/bin/daemon.sh stop ${appname}
