#FROM centos:centos7
#RUN yum install java -y && yum install epel-release -y && yum install redis -y && yum install -y wget
FROM base:0.1
RUN cd /tmp && wget https://dev.mysql.com/get/mysql80-community-release-el7-6.noarch.rpm && yum install -y mysql80-community-release-el7-6.noarch.rpm && yum install -y  mysql-community-server



