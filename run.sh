#!/bin/bash
source /etc/profile

CUR_DIR=`dirname $0`
if [ "." == "$CUR_DIR" ]; then
    CUR_DIR=`pwd`
fi

LOG_DIR=$CUR_DIR/logs
if [ ! -d $LOG_DIR ]; then
    mkdir -p $LOG_DIR
fi

cd $CUR_DIR
JAR_NAME=`ls | grep "\.jar$"`

JAVA_OPT="-Xms1G -Xmx1G -Xmn384M"
JAVA_OPT=$JAVA_OPT" -Xloggc:$LOG_DIR/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPT=$JAVA_OPT" -server -jar"

JAVA_ARG=""

function stop_app() {
    PID=`ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
    if [ -n "$PID" ]; then
        echo "kill process $PID"
        kill -9 $PID
        echo "$JAR_NAME stopped!"
    else
        echo "process not exist, pass"
    fi
}

function start_app() {
    PID=`ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
    if [ -n "$PID" ]; then
        echo "process exist, PID: $PID; starting fail"
        exit 1
    else
        echo "starting $JAR_NAME ..."
        nohup java $JAVA_OPT $JAR_NAME $JAVA_ARG >/dev/null 2>&1 &
        
        PID=`ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
        echo "$JAR_NAME started! PID: $PID"
    fi
}

function restart_app() {
    stop_app
    start_app
}

if [ "restart" == "$1" ]; then
    restart_app
elif [ "stop" == "$1" ]; then
    stop_app
elif [ "start" == "$1" ]; then
    start_app
else
    echo "No such operation: $1"
fi