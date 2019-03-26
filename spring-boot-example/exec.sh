#!/bin/bash
# author: 13
# date: 2017-06-30


env_args="-Xms128m -Xmx128m"
sleeptime=0
arglen=$#

# get luvx pid
get_pid(){
    pname="`find .. -name 'spring-boot-example-1.0.1-SNAPSHOT.jar'`"
    pname=${pname:3}
    pid=`ps -ef | grep $(basename $pname) | grep -v grep | awk '{print $2}'`
    echo "$pid"
}

startup(){

    #构建并启动
    mvn clean install -Dmaven.test.skip=true
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        echo "Blog already startup!"
    else
        jar_path=`find .. -name 'spring-boot-example-1.0.1-SNAPSHOT.jar'`
        echo "jarfile=$jar_path"
        cmd="java $1 -jar $jar_path > ./luvx.out < /dev/null &"
        echo "cmd: $cmd"
        java $1 -jar $jar_path > ./luvx.out < /dev/null &
        show_log
    fi
}

shut_down(){
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        kill -9 $pid
        echo "Blog is stop!"
    else
        echo "Blog already stop!"
    fi
}

show_log(){
    tail -f luvx.out
}

show_help(){
    echo -e "\r\n\t欢迎使用Blog"
    echo -e "\r\nUsage: sh exec.sh start|stop|reload|status|log"
    exit
}

show_status(){
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        echo "Blog is running with pid: $pid"
    else
        echo "Blog is stop!"
    fi
}

if [ $arglen -eq 0 ]
 then
    show_help
else
    if [ "$2" != "" ]
    then
        env_args="$2"
    fi
    case "$1" in
        "start")
            startup "$env_args"
            ;;
        "stop")
            shut_down
            ;;
        "reload")
            echo "reload"
            ;;
        "status")
            show_status
            ;;
        "log")
            show_log
            ;;
    esac
fi