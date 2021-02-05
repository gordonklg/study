cd C:\study\rocketmq\rocketmq-all-4.3.2-bin-release\bin

start mqnamesrv.cmd

echo starting nameserver

choice /t 10 /d y /n >nul

start mqbroker.cmd -n 127.0.0.1:9876 -c ../conf/broker.conf

echo starting broker -n 127.0.0.1:9876 -c ../conf/broker.conf

choice /t 10 /d y /n >nul

cd C:\study\rocketmq

java -jar rocketmq-console-ng-1.0.0.jar
