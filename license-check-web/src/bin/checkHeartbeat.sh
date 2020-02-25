#!/bin/sh

PRO_NAME=heartBeat-1.2.jar
NUM=`ps aux | grep -w ${PRO_NAME} | grep -v grep |wc -l`
if [ "${NUM}" -lt "1" ];then
  echo "${PRO_NAME} was killed"
  sh /usr/lib/heartbeat/heartBeat-1.2/bin/start.sh
else
  echo "${PRO_NAME} is running"
fi
 