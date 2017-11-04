FROM java:8-jre
COPY ./target/uberjar/ /usr/src/app
WORKDIR /usr/src/app
CMD java -jar track-worker-0.1.0-SNAPSHOT-standalone.jar