#!/bin/sh
SERVICE_NAME=$2
CMD=$3
SCRIPT_PATH=$(cd "$(dirname "$0")/.."; pwd)
#BASE=$(dirname ${SCRIPT_PATH})
SERVICE_PID=${SERVICE_NAME}.pid
PID_PATH_NAME=${SCRIPT_PATH}/pids/$SERVICE_PID
# Start service 
start() {
    echo "Starting $SERVICE_NAME ..."
    #获取PID
    if [ -f "$PID_PATH_NAME" ]; then
    	PID=$(cat $PID_PATH_NAME);
    else
	    PID=0;
    fi
    #是否已经启动
    if [ ! -d "/proc/$PID" ]; then
        echo -e "running command \n$CMD\n"
        eval nohup $CMD > service.out 2>&1 &
        #eval  $CMD > /dev/null  &
        #最后运行的后台进程ID
        pid=$!
        sleep 1
        #检查是否启动成功
        if [ ! -d "/proc/$pid" ]; then
            echo "$SERVICE_NAME not started. See log for detail."
            rm -f $PID_PATH_NAME
            exit 1
        else
            echo $pid > $PID_PATH_NAME
            echo "$SERVICE_NAME started ... PID: $!"
        fi
    else
        echo "$SERVICE_NAME is already running ..."
    fi
}

# Start service 
stop() {
    if [ -f "$PID_PATH_NAME" ]; then
    	PID=$(cat $PID_PATH_NAME);
    else
	    PID=0;
    fi

    if [ -d "/proc/$PID" ]; then
        echo "$SERVICE_NAME stoping ..."
        for p_pid in `ps -ef |grep $PID|egrep -v grep | awk '{print $2}'`
        do
         kill -s 9 $p_pid
        done
        echo "$SERVICE_NAME stopped ..."
        rm -f $PID_PATH_NAME
        #rm -f /app/tvc-realtime-spark/$SERVICE_NAME
    else
        echo "$SERVICE_NAME is not running."
    fi
}

status() {
    if [ -f "$PID_PATH_NAME" ]; then
    	PID=$(cat $PID_PATH_NAME);
    else
	    PID=0;
    fi
    #echo $PID;

    if [ -d "/proc/$PID" ]; then
        echo "Service '$SERVICE_NAME' is running..."
	echo "PID: $PID"
    else
        echo "Service '$SERVICE_NAME' is not running."
    fi
}

case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    status)
        status
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|reload|status}"
        exit 1
esac

exit 0
