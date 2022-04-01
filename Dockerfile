FROM java:8

MAINTAINER MasterLeaf<ysh1356542512@163.com>

ENV MYPATH /usr/local/qq
WORKDIR $MYPATH

COPY build/libs/MiraiY.jar /usr/local/qq/MiraiY.jar
COPY resources /usr/local/qq/resources

VOLUME ["/usr/local/qq"]

ENTRYPOINT ["java","-jar","MiraiY.jar"]