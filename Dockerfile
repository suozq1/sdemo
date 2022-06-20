FROM suozq/base:1.0
LABEL author = suozq@lenovoedu.com
ARG JAR_FILE=sdemo-1.0.0.jar 
COPY start.sh /start.sh
COPY ./target/${JAR_FILE}  /app.jar
EXPOSE 8080 3306 6379
ENTRYPOINT ./start.sh

