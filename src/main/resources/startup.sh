#!/usr/bin/env bash

#JAVA_HOME="/usr/java/jdk1.8.0_05"
JAVA_OPTS=" -Xms512m -Xmx1024m -Xmn512m -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=60 "
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -verbose:gc -Xloggc:./logs/gc.log"

PID_FILE=./bin/application.pid

if [ -e ${PID_FILE} ]; then
  echo "restart application"
  PID=$(cat ${PID_FILE})
  echo "try to kill process $PID"
  kill ${PID}
  rm ${PID_FILE}
fi

export SPRING_PROFILES_ACTIVE
#SPRING_PROFILES_ACTIVE=production
exec java $JAVA_OPTS -classpath .:./lib/* com.ffcs.mmcc.maintain.MaintainApplication -mmccMaintain &

PID=$!
echo ${PID} > ${PID_FILE}
