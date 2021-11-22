## charset
export LANG=ko_KR.eucKR

## main file
MAIN_JAR=watcher_ssh_tunneling_v1.0.jar
CONTEXT=

PID=$(ps x | grep $CONTEXT | grep -v grep | aws '/java/ {print $1}')

start(){
	if [$PID] ; then
		ps aux|grep $CONTEXT | grep -v grep
		echo "already started."
	else
		echo "start process..."
		java -jar $MAIN_JAR $CONTEXT &
		ps -ef | grep $CONTEXT | grep -v grep
	fi
}

stop(){
	if [$PID] ; then
		ps aux|grep $CONTEXT | grep -v grep
		kill $PID
		echo "stop process."
	else
		echo "not running."
	fi
}

restart(){
	stop
	
	echo "start process..."
	java -jar $MAIN_JAR $CONTEXT &
	ps -ef | grep $CONTEXT | grep -v grep
}

show(){
	top -c -p$PID
}

status(){
	if ps aux|grep $CONTEXT | grep -v grep
	then
		echo "running.."
	else
		echo "not running."
	fi
}

# See how we were called.
case "$1" in
	start)
	start
	;;
	stop)
	stop
	;;
	restart)
	restart
	;;
	show)
	show
	;;
	*)
	echo $"Usage: $0 {start|stop|restart|show}"
	status
esac
