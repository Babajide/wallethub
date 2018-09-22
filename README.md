# Introduction
Thank you for the opportunity. As the saying goes, there are multiple ways to skin a cat, I have decided to go the `springboot` way. Other options are:
* java application with JDBC for persistence
* java application with JPA using persistence.xml (Hibernate)
* using spring batch - see `https://spring.io/projects/spring-batch`

### How to run this app
``
Please run using java -jar example:
```
java -jar parser.jar --accesslog=./access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200
```

## Brief Overview
Please note the following:
* `LogAnalyzer` -  Analyzes the log data.
* `CommandLineParser` - Helps convert program arg to a POJO.
* `CommandLineValidator` Validates program arg.
*  `ParserService` service layer .
*  `FileReader` used to read log file into memory before processing.
*  `BlockedRequestRepository` data access layer for persisting BlockedRequests
*  `AccessLogRepository` data access layer for persisting AccessLogs

### SQL Scheama
##### DDLs -MySQL schema used for the log data)
* access log 
```
create table access_log (
id bigint not null auto_increment, 
access_time datetime, ip varchar(255), 
request_line varchar(255), 
status integer not null, 
user_agent varchar(255), 
primary key (id)) 
engine=MyISAM
```


* blocked ips
```
create table blocked_request (
id bigint not null auto_increment, 
comment varchar(255), 
ip varchar(255), 
primary key (id)) 
engine=MyISAM
```


##### DMLs - SQL queries for SQL test

(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
```
select ip , count(ip)  as requests from parser.access_log 
where access_time 
between '2017-01-01.15:00:00' and '2017-01-01.16:00:00' 
group by ip having requests >100
```


(2)  Write MySQL query to find requests made by a given IP.

```
select request_line from parser.access_log where ip = '192.168.11.231'
```

```select ip , count(ip)  as requests from parser.access_log 
   where access_time 
   between '2017-01-01.15:00:00' and '2017-01-01.16:00:00' 
   group by ip
   ```

#### Database connection
This application can use in-memory db, please see `application.properties` file for connection details.