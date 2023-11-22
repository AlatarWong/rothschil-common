#!/usr/bin/env bash

ADATE=$(date +%Y%m%d%H)
CURRENT_DIRECTORY=$(pwd)
ENV_DIR="$CURRENT_DIRECTORY/logs"

LOG_PATH=$ENV_DIR/$ADATE.log
GC_LOG_PATH_DIC=$ENV_DIR/gc

log_info "Directory of all Log Files is: $ENV_DIR"

if [ ! -d "$ENV_DIR"  ];then
   mkdir -p $ENV_DIR
fi


#JAVA_HOME="/usr/java/jdk1.8.0_05"
JAVA_OPTS=" -Xms512m -Xmx1024m -Xmn512m -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=60 "
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps -XX:+PrintGCDetails -verbose:gc -Xloggc:$GC_LOG_PATH"

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
exec java $JAVA_OPTS -classpath .:./lib/* io.github.rothschil.RothschilApplication -mmccMaintain &

PID=$!
echo ${PID} > ${PID_FILE}
