mvn clean package -Pqa -Dmaven.test.skip=true
docker rm sdemo -f
docker rmi suozq/sdemo:1.0
docker build -t suozq/sdemo:1.0 .
docker run -d --name=sdemo --privileged=true -p 8080:8080 -p 33066:3306 -p 6379:6379 suozq/sdemo:1.0
