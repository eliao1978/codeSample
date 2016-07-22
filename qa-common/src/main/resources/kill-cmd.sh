#!/bin/sh
getprocess=`ps -ef|grep lwes-filter-listener`
set $getprocess 
pid=$2
kill -9 $pid

getprocess=`ps -ef|grep lwes-filter-listener`
set $getprocess
pid=$2
kill -9 $pid