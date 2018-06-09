To run app specify commaind line param: prod, dev

`java -jar -Dspring.profiles.active=dev build/libs/dating-1.0.jar`


./gradlew -x build
docker build -t dating_server .



docker run -d -p 4000:8080 dating_server
docker container ls
docker container stop 1fa4ab2cf395
docker run -d -p 4000:8080 dakishin/dev