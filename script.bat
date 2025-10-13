@echo off
echo Stopping wigellswushi
docker stop wigellssushi
echo Deleting container wigellssushi
docker rm wigellssushi
echo Deleting image wigellssushi
docker rmi wigellssushi
echo Running mvn package
call mvn package -DskipTests
echo Creating image wigellssushi
docker build -t wigellssushi .
echo Creating and running container wigellssushi
docker run -d -p 9595:9595 --name wigellssushi --network services-network wigellssushi
echo Done!