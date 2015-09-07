1、创建数据库（resources），执行脚本：resources.sql

2、解压resource.tar.gz，拷贝到/data/目录下。

3、修改数据库连接信息（linux :  /data/resource/run/classes/SqlMapConfig.xml）
      <property name="JDBC.ConnectionURL" value="jdbc:mysql://127.0.0.1:3306/resources?useUnicode=true"/>
      <property name="JDBC.Username" value="root"/>
      <property name="JDBC.Password" value="lookgenming"/>

4、运行资源中心（sh /data/resource/deploy/start.sh）

5、访问http://xxx.xxx:8081,出现如下信息表示启动成功（xxx.xxx为您部署resource的服务器ip，本地使用localhost）
    welcome to scallop resource center.