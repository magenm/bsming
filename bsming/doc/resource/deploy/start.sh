#!/bin/sh

SERVICE_HOME=/data/resource/run

LIB_DIR=${SERVICE_HOME}/lib

LOGS_DIR=/data/resource/logs

ARCHIVE_SUFFIX=`date +%Y%m%d-%H%M`

MAIN_CLASS="scallop.center.Server"

JAVA_ARGS="-server -Xms32m -Xmx32m -XX:NewSize=16m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=78 -XX:ThreadStackSize=256 -Xloggc:${LOGS_DIR}/gc.log"

JAVA_ARGS="${JAVA_ARGS} -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.server.exceptionTrace=true"

CLASSPATH=$CLASSPATH:${SERVICE_HOME}/classes/
files=`ls -1 ${LIB_DIR}`
for file in ${files} ;do
        CLASSPATH=$CLASSPATH:${LIB_DIR}/${file}
done
export CLASSPATH
#mv ${LOGS_DIR}/stdout.log ${LOGS_DIR}/stdout.log.${ARCHIVE_SUFFIX} 
#mv ${LOGS_DIR}/stderr.log ${LOGS_DIR}/stderr.log.${ARCHIVE_SUFFIX} 

java ${JAVA_ARGS} ${MAIN_CLASS}  1>${LOGS_DIR}/stdout.log 2>${LOGS_DIR}/stderr.log&

echo "resources starting..."
