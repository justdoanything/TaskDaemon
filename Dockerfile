#FROM openjdk:8u201-jre-alpine3.9
FROM ubuntu
MAINTAINER github.com/justdoanything/ssh_tunneling

RUN apt-get update 
RUN apt-get install -y openjdk-8-jdk

#RUN apt-get -y install software-properties-common && apt-add-repository -y ppa:webupd8team/java && apt update && echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections && apt install -y oracle-java8-installer
#RUN echo "Installed jre8"

### if you want to use private key into your environment directory like .ssh, copy the key and change value in application.yml 

# copy required files such as config files, shell file to need run and jar file 
COPY ./conf/* /usr/src/app/conf
COPY ./run.sh /usr/src/app
COPY ./target/ssh_tunneling.jar /usr/src/app
# run command "chmod"
RUN chmod +x /usr/src/app/run.sh

# set working directory
WORKDIR /usr/src/app

# finally execute run.sh
#CMD ["sh", "run.sh"]