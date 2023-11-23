#!/usr/bin/env bash
# Sam Abram<WCNGS@HOTMAIL.COM>
# Version V1.3.1
echo -ne "\033[0;33m"
cat<<EOF
                                  _oo0oo_
                                 088888880
                                 88" . "88
                                 (| -_- |)
                                  0\ = /0
                               ___/'---'\___
                             .' \\\\|     |// '.
                            / \\\\|||  :  |||// \\
                           /_ ||||| -:- |||||- \\
                          |   | \\\\\\  -  /// |   |
                          | \_|  ''\---/''  |_/ |
                          \  .-\__  '-'  __/-.  /
                        ___'. .'  /--.--\  '. .'___
                     ."" '<  '.___\_<|>_/___.' >'  "".
                    | | : '-  \'.;'\ _ /';.'/ - ' : | |
                    \  \ '_.   \_ __\ /__ _/   .-' /  /
                ====='-.____'.___ \_____/___.-'____.-'=====
                                  '=---='


              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        佛祖保佑    TTT    永不宕机
EOF
echo -ne "\033[m"


# 全局设置下面的日志级别并且格式化打印机制 debug-1, info-2, warn-3, error-4, always-5
LOG_LEVEL=1

# 调试日志
function log_debug(){
  content="[DEBUG] $(date '+%Y-%m-%d %H:%M:%S') $@"
  [ $LOG_LEVEL -le 1  ] && echo -e "\033[32m"  ${content}  "\033[0m"
}
# 信息日志
function log_info(){
  content="[INFO] $(date '+%Y-%m-%d %H:%M:%S') $@"
  [ $LOG_LEVEL -le 2  ] && echo -e "\033[32m"  ${content} "\033[0m"
}
# 警告日志
function log_warn(){
  content="[WARN] $(date '+%Y-%m-%d %H:%M:%S') $@"
  [ $LOG_LEVEL -le 3  ] && echo -e "\033[33m" ${content} "\033[0m"
}
# 错误日志
function log_err(){
  content="[ERROR] $(date '+%Y-%m-%d %H:%M:%S') $@"
  [ $LOG_LEVEL -le 4  ] && echo -e "\033[31m" ${content} "\033[0m"
}
# 一直都会打印的日志
function log_always(){
   content="[ALWAYS] $(date '+%Y-%m-%d %H:%M:%S') $@"
   [ $LOG_LEVEL -le 5  ] && echo -e  "\033[32m" ${content} "\033[0m"
}

log_debug "Use as follows:"
log_debug "sh startup.sh /APP_NAME/ /PORT/ --spring.profiles.active=/prod/"

# input hostname
hostname=`hostname`
log_debug "User Name of the Current System Operation: $hostname"
JAVA=
if [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    JAVA="$JAVA_HOME/bin/java"
    log_err "Found java executable in JAVA_HOME: $JAVA"
else
    log_err "Java is not installed. Please install JAVA 1.8 or upper version"
    exit 1
fi

# 判断JDK版本
log_info "Start to determine the JDK version and determine the startup parameters"
version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
log_err "Current Java Versions is $version"

IP_NET=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}')

ADATE=$(date +%Y%m%d%H)
CURRENT_DIRECTORY=$(pwd)
ENV_DIR="$CURRENT_DIRECTORY/logs"

LOG_PATH=$ENV_DIR/$ADATE.log
GC_LOG_PATH_DIC=$ENV_DIR/gc

if [ ! -d "$ENV_DIR"  ];then
   mkdir -p $ENV_DIR
fi


#JAVA_HOME="/usr/java/jdk1.8.0_05"
JAVA_OPTS=" -Xms512m -Xmx1024m -Xmn512m -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=60 "
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps -XX:+PrintGCDetails -verbose:gc -Xloggc:$GC_LOG_PATH_DIC"

PID_FILE=./bin/application.pid

if [ -e ${PID_FILE} ]; then
  log_err "restart application"
  PIDCONTENT=$(cat ${PID_FILE})
  ARR=($PIDCONTENT)
  log_info "${ARR[0]}:${ARR[1]}:${ARR[2]}:${ARR[3]}:${ARR[4]}"
  PID=${ARR[4]}
  log_err "try to kill process $PID"
  kill ${PID}
  rm ${PID_FILE}
fi

export SPRING_PROFILES_ACTIVE
#SPRING_PROFILES_ACTIVE=production
exec java $JAVA_OPTS -classpath .:./lib/* io.github.rothschil.RothschilApplication -mmccMaintain &

PID=$!
log_err ${PID} > ${PID_FILE}
