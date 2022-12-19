## 打开
```bash
cd C:\java\kafka_2.12-2.3.0
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
bin\windows\kafka-server-start.bat config\server.properties
```
## 关闭
```bash
zookeeper-server-stop.bat
kafka-server-stop.bat
```
## 服务器端口被占用
```bash
#占用查询端口的pid查询
C:\Users\helloworld>netstat -ano|findstr "8080"
#关闭对应pid
C:\Users\helloworld>taskkill  -F -PID 6832
```

### 导入mysql
```bash
mysql -u root -p
use community;
source C:/java/sql/community-init-sql-1.5/tables_mysql_innodb.sql;
```

### 部署
```bash
wget -i -c 链接
scp -P 22 文件名 root@ip:/root/test
yum list unzip*
yumm install -y unzip
java-latest-openjdk.x86_64
java -version
tar -zvxf 文件名 -C /opt
maven3.6.1
redis 3.2.12
# 配环境变量
/etc/profile
export PATH=$PATH:路径
source /etc/profile
echo $PATH
mvn -version
改/conf/settings.xml
#装mysql
yum install -y mysql...
systemctl start mysqld
systemctl status mysqld
grep 'password' /var/log/mysqld.log
mysql -u root -p
alter user root@localhost identified by 'Nowcoder_123'
mysql -u root -p
create database community;
source tables_mysql_innodb.sql; 三个表
#调整user表
update user set header_url = 'http://images.nowcoder.com/head/666t.png' where header_url like '%localhost%';
unzip -d /root 
#redis
systemctl start redis
systemctl status redis
redis -cli
#kafaka
解压可安装，没有配置环境变量
#后台启动
bin/zookeeper-server-start.sh -daemon  config/zookeeper.properties
nohup  bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
use community;
#elasticsearch
解压，还有解压中文分词到/plugins/ik
配置config/elasticsearch.yml
cluster.name: nowcoder
path.data: /tmp/elatic/data
path.logs: /tmp/elatic/logs
#jvm.options
-Xms256m
-Xmx512m
elasticsearch只能用普通用户启动
groupadd nowcoder
useradd nowcoder1 -p 123456 -g nowcoder
cd /opt
cd /tmp
chown -R nowcoder1:nowcoder * 开权限
su - nowcoder1
bin/elasticsearch -d
curl -X GET "localhost:9200/_cat/health?v"
yum list wkhtmltopdf*  0.12.1

yum list *xvfb*
xorg...  1.20.1.5

#tomcat解压缩安装,装环境变量
startup.sh
#nginx yum 安装 1:1.12.2
vim nginx.conf
配server
upstream myserver {
  server 127.0.0.1:8080 max_fails=3 fail_timeout=30s;
}
server{
  listen 80;
  server_name  ip地址;
  location / {
    proxy_pass http://myserver;
  }
}
systemctl start nginx
shutdown.sh 关tomcat
进webapps,都删了


打包源码，
mvn clean package -Dmaven.test.skip=true  忽略测试
进入target,拷贝ROOT.war到tomcat的webapps

tomcat  cd /logs  
vim catalina.....log
```
