#Linux下修改MySQL用户（root）密码
以下命令适合修改任何mysql用户，仅以root为例。

一、拥有原来的myql的root的密码；
```
# mysql -uroot -p
Enter password: 【输入原来的密码】（如果安装完mysql第一次设置root密码，则直接回车）
mysql>use mysql;
mysql> update user set password=password("123456") where user='root';
mysql> flush privileges;
mysql> exit;
```

二、忘记原来的myql的root的密码；

首先，你必须要有操作系统的root权限了。也就说需要以root的身份登录到操作系统，然后进行一下操作。

1、进入MYSQL目录下，编辑MySQL配置文件my.cnf
```
	# cd /var/lib/mysql
	# vi /etc/my.cnf
```

编辑文件，找到[mysqld]，在下面添加一行skip-grant-tables

```
	[mysqld]
	skip-grant-tables
```

i 是输入
:wq!  #保存退出
service mysqld restart  #重启MySQL服务

2、进入MySQL控制台

	mysql -uroot -p

直接按回车，这时不需要输入root密码。

3、修改root密码
```
	mysql>use mysql;
 	mysql> update user set password=passworD("test") where user='root';
 	mysql> exit;   
```
或者
```
	update mysql.user set password=password('123456') where User="root" and Host="localhost";
	flush privileges; 
	grant all on *.* to 'root'@'localhost' identified by '123456' with grant option;
```

4、取消/etc/my.cnf中的skip-grant-tables
	
	vi /etc/my.cnf
编辑文件，找到[mysqld]，删除skip-grant-tables这一行

:wq!  #保存退出

5、重启mysql

	service mysqld restart

6、进入mysql控制台

	mysql -uroot -p123456