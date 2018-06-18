FROM hseeberger/scala-sbt as builder
COPY . /app
WORKDIR /app
RUN sbt dist

FROM mdsol/java10-jre as build_image
RUN mkdir /svc && mkdir -p /tmp/zip
COPY --from=builder /app/target/universal/*.zip /tmp/zip
RUN unzip -d /svc /tmp/zip/*.zip && rm -rf /tmp/zip && mv /svc/*/* /svc/ && rm /svc/bin/*.bat && mv /svc/bin/* /svc/bin/start
EXPOSE 9000 9443
CMD /svc/bin/start -Dhttps.port=9443 -Dplay.crypto.secret=secret
