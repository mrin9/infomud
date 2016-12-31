## Infomud.com (Springboot/javascript)
Sourcecode for infomud.com

### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot 1.4](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
REST Documentation| [Swagger UI / Springfox](https://github.com/springfox/springfox) and [ReDoc](https://github.com/Rebilly/ReDoc)
REST Spec         | [Open API Standard](https://www.openapis.org/) 
In Memory DB      | H2 
Persistence       | JPA
Server Build Tools| Maven(Java)
Client Build Tools| TBD
Frontend          | TBD
Gateway Service   | [Netflix zuul](https://github.com/Netflix/zuul)
Localization      | <Pending>     


## Build Instruction
Prerequisite (Ensure you have this installed before proceeding further)
- Java 8
- Maven 3.3.9
- Node 7.2.1, npm 3.9.5
- <Choice of javascript frntend>

Clone the repo in a folder

### Install REST Backend

```bash
#navigate to the root folder where pom.xml is present 
mvn clean install
```

### Install Frontend 


### Start the API and WebUI server ###
```bash
# Start API server (9119)
# port and other configurations for API servere is in [./cofig/application.properties](https://github.com/mrin9/Angular2_SpringBoot/blob/master/config/application.properties) file
java -jar ./target/infomud-1.0.0.jar

```

- Access Server at <http://localhost:9119/index.html>

**Login Credentials**
```
demo:demo
admin:admin
inactive:inactive
```

**To get an authentication token** 

```bash
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"username": "demo", "password": "demo" }' 'http://localhost:9119/session'
```
or POST the username and password to http://localhost:9119/session


after you get the authentication token you must provide this in the header for all the protected urls 

```bash
curl -X GET --header 'Accept: application/json' --header 'Authorization: [replace this with token ]' 'http://localhost:9119/version'
```
