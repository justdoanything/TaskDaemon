# ssh_tunneling
https://github.com/justdoanything/ssh_tunneling

---
## Intro
This program can execute linux command or a specific query to database repeatedly via SSH tunneling. 
You can run the below types of tasks repeatedly if you change some of values into application.xml.
  - You can run more complex task if you put your logic into `DatabaseService class`.
  - This program are using multiple theard to run several environments(env) so that you should consider indexs about env when you see the code line.
  - There are required fields in `application.yml` if you run this program.You can take a look two classes such as `Configuration class` and `Connector* class` about that.
    - Type1 : Open SSH tunneling and reopen ssh tunnel repeatedly if the local port is not opened. This is useful when accessing mysql on another server through the ssh auth.
      ```yml
        # Type1 : Open ssh tunneling through specific host and port
        - env: prod
          execute:
            interval: 30000
            type: tunnel 
          local:
            port: 3000
          remote:
            name: prodRemoteName
            host: prodRemoteHost
            port: 22
            key: prodRemoteKey
            tunnel: 
              host: 127.0.0.1
              port: 3306
      ```
    - Type2 : Open channel and execute linux command repeatedly.
      ```yml
        # Type2 : Open ssh tunneling and execute linux command repeatedly
        - env: stg
          execute:
            interval: 30000
            type: command
          local:
            port: 3005
          remote:
            name: stgRemoteName
            host: stgRemoteHost
            port: 22
            key: stgRemoteKey
            command:
              line:
                - ls -al
                - pwd
                - lscpu
      ```
    - Type3 : Open SSH tunneling and execute a specific query repeatedly.
      ```yml
        # Type3 : Execute query repeatedly
        - env: dev
          execute:
            interval: 60000
            type: db
          local:
            port: 3010
          remote:
            name: devRemoteName
            host: devRemoteHost
            port: 22
            key: devRemoteKey
            db: 
              mybatis: ./conf/mybatis.xml
              driver: com.mysql.jdbc.Driver
              url: jdbc:mysql://127.0.0.1:3306/test
              id: test
              pwd: test
              query: ./conf/sq.xml
      ```
---
## Project Type
- Project : Java 1.8 (free version) / Maven
- If you want to run by Docker.
  - You can chage values in configurable files such as application.yml, log4j.properties, mybatis.xml, sql.xml and java class.
  - You can run docker commands to make a docker image.
    It can be refered Dockerfile.
    - `docker build -t ssh_tunneling:1.0 .`
    - `docker run -d --name ssh_tunneling ssh_tunneling:1.0`
    - if you want to change configurable values after running docker.
      - Run command : `docker exec -it ssh_tunneling /bin/bash`
      - Run command in docker container : `sh run.sh stop`
      - Chnage values in conf folder.
      - Run command in docker container : `sh run.sh start`
  - I had uploaded basic docker image file in docker hub.\
    Run docker command : `docker pull yongwoo1992/repeatedly_multi_task`
- If you want to run by old fashion.
  - Run `maven build` with dependencies.
    ```xml
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-assembly-plugin</artifactId>
      <configuration>
        <fileName>ssh_tunneling</fileName>
        <archive>
          <manifest>
            <addClasspath>true</addClasspath>
            <mainClass>com.yong.runner.Executor</mainClass>
          </manifest>
        </archive>
        <descriptorRefs>
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
      </configuration>
    </plugin>
    ```
  - Move `ssh_tunneling-jar-with-dependencies.jar`, `conf` folder, `run.sh` to your environment.
  - Run command : `sh run.sh`

Refo : https://github.com/justdoanything/WatchDB
