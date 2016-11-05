# rod

1. Download and install postgresql database server and create 'rod' database and user/password equals to postgres/postgres;
2. Download wildfly (http://wildfly.org/downloads/) and unpack it to same directoty $WILDFLY;
3. Copy config file config/standalone.xml to $WILDFLY/standalone/ directory;
4. Download project 'https://github.com/mathter/persistence-ee' and build it (mvn clean install);
5. Enter into rod-db folder and start 'mvn flyway:migrate' command;
6. Build 'rod' project (mvn clean package) and deploy to the wildfly server using administration console or copy file rod-ear/target/rod-ear-{version}.ear to the directory WILDFLY/deployements.
7. Use this URL to start http://localhost:8080/rod/registartion ;

Enjoy 

