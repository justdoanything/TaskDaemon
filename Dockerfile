FROM ubuntu
MAINTAINER github.com/justdoanything/ssh_tunneling

RUN apt-get update 
RUN apt-get install -y openjdk-8-jdk
RUN apt-get install -y vim

### if you want to use private key into your environment directory like .ssh, copy the key and change value in application.yml 

# copy required files such as config files, shell file to need run and jar file 
ADD ./conf /usr/src/app/conf
COPY ./run.sh /usr/src/app
COPY ./target/ssh_tunneling-jar-with-dependencies.jar /usr/src/app/ssh_tunneling.jar

# run command "chmod"
RUN chmod +x /usr/src/app/run.sh

# set working directory
WORKDIR /usr/src/app

# finally execute run.sh
#CMD ["cat" "run.sh | tr -d '\r' > new_run.sh"]
#CMD ["sh", "new_run.sh start"]
CMD ["tail", "-f"]
