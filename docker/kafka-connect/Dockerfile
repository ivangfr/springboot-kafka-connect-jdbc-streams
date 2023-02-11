FROM confluentinc/cp-kafka-connect:7.3.1

LABEL maintainer="ivangfr@yahoo.com.br"

USER root
RUN yum install unzip -y

COPY jars/*.jar /etc/kafka-connect/jars

# confluentinc-kafka-connect-elasticsearch
ADD confluentinc-kafka-connect-elasticsearch-14.0.5.zip /tmp/confluentinc-kafka-connect-elasticsearch.zip
RUN unzip /tmp/confluentinc-kafka-connect-elasticsearch.zip -d /usr/share/java && rm /tmp/confluentinc-kafka-connect-elasticsearch.zip

# confluentinc-kafka-connect-jdbc
ADD confluentinc-kafka-connect-jdbc-10.6.3.zip /tmp/confluentinc-kafka-connect-jdbc.zip
RUN unzip /tmp/confluentinc-kafka-connect-jdbc.zip -d /usr/share/java && rm /tmp/confluentinc-kafka-connect-jdbc.zip
