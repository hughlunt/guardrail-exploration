FROM mdsol/java10-jdk
COPY target/universal /target/universal
RUN mkdir /svc && unzip -d /svc /target/universal/*-1.0-SNAPSHOT.zip && mv /svc/*/* /svc/ && rm /svc/bin/*.bat && mv /svc/bin/* /svc/bin/start
EXPOSE 9000 9443
CMD /svc/bin/start -Dhttps.port=9443 -Dplay.crypto.secret=secret
