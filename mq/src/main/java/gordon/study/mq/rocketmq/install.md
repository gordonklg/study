## A. 资源与参考文档
官网：<http://rocketmq.apache.org/>
<br/>

## B. Windows 环境安装
参考资料：<https://www.jianshu.com/p/4a275e779afa>

#### a. 安装
下载；解压；配置环境变量 ROCKETMQ_HOME

#### b. 启动
启动 Name Server：`start mqnamesrv.cmd`
启动 Broker：`start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true`

#### c. 管理页面
1\. 下载源码 <https://github.com/apache/rocketmq-externals/tree/release-rocketmq-console-1.0.0>

2\. 修改 application.properties
```
rocketmq.config.namesrvAddr=127.0.0.1:9876
```
3\. 打包 `mvn clean package` ；运行 `java -jar ` ；打开网页 localhost:8080

## C. 核心概念
参考资料：<http://rocketmq.apache.org/docs/core-concept/>
RocketMQ model









