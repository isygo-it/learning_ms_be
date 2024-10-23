# web jobApplication microservices

## Architecture

Our sample microservices-based system consists of the following modules:

- **gateway-service** - a module that Spring Cloud Netflix Zuul for running Spring Boot jobApplication that acts as a
  proxy/gateway in our architecture.
- **config-service** - a module that uses Spring Cloud Config Server for running configuration server in the `native`
  mode. The configuration files are placed on the classpath.
- **discovery-service** - a module that depending on the example it uses Spring Cloud Netflix Eureka or Spring Cloud
  Netlix Alibaba Nacos as an embedded discovery server.
- **messaging-management-service** - a module containing the first of our sample microservices that allows to perform
  CRUD operation on in-memory repository of employees
- **identity-management-service** - a module containing the second of our sample microservices that allows to perform
  CRUD operation on in-memory repository of departments. It communicates with messaging-management-service.
- **key-management-service** - a module containing the third of our sample microservices that allows to perform CRUD
  operation on in-memory repository of organizations. It communicates with both messaging-management-service and
  key-management-service.

### Servers and links

- **Servername** | **Port** | **Link** -
- config.dev.prm.novobit.eu | 8088 | https://config.dev.prm.novobit.eu -
- eureka.dev.prm.novobit.eu | 8061 | https://eureka.dev.prm.novobit.eu -
- gateway.dev.prm.novobit.eu | 8060 | https://gateway.dev.prm.novobit.eu -
- sms.dev.prm.novobit.eu | 55400 | https://sms.dev.prm.novobit.eu -
- ims.dev.prm.novobit.eu | 55402 | https://ims.dev.prm.novobit.eu -
- kms.dev.prm.novobit.eu | 55403 | https://kms.dev.prm.novobit.eu -
- mms.dev.prm.novobit.eu | 55404 | https://mms.dev.prm.novobit.eu -
- dms.dev.prm.novobit.eu | 55405 | https://dms.dev.prm.novobit.eu -
- cms.dev.prm.novobit.eu | 55407 | https://cms.dev.prm.novobit.eu -
- hrm.dev.prm.novobit.eu | 55408 | https://hrm.dev.prm.novobit.eu -
- rpm.dev.prm.novobit.eu | 55409 | https://rpm.dev.prm.novobit.eu -
- lms.dev.prm.novobit.eu | 55410 | https://lms.dev.prm.novobit.eu -
- pms.dev.prm.novobit.eu | 55411 | https://pms.dev.prm.novobit.eu -
- quiz.dev.prm.novobit.eu | 55412 | https://quiz.dev.prm.novobit.eu -
- lnk.dev.prm.novobit.eu | 55413 | https://lnk.dev.prm.novobit.eu -
- fe-gateway.dev.prm.novobit.eu | 4000 | https://fe-gateway.dev.prm.novobit.eu -
- fe-sysadmin.dev.prm.novobit.eu | 4001 | https://fe-sysadmin.dev.prm.novobit.eu -
- fe-recruitment.dev.prm.novobit.eu | 4002 | https://fe-recruitment.dev.prm.novobit.eu -
- fe-calendar.dev.prm.novobit.eu | 4003 | https://fe-calendar.dev.prm.novobit.eu -
- fe-candidate.dev.prm.novobit.eu | 4004 | https://fe-candidate.dev.prm.novobit.eu -
- fe-quiz.dev.prm.novobit.eu | 4005 | https://fe-quiz.dev.prm.novobit.eu -
- fe-quiz.dev.hrm.novobit.eu | 4006 | https://fe-hrm.dev.prm.novobit.eu -
- fe-document.dev.prm.novobit.eu | 4007 | https://fe-document.dev.prm.novobit.eu -
- dev.smartcode.novobit.eu | 4008 | https://dev.smartcode.novobit.eu -
- qa.smartcode.novobit.eu | 4009 | https://qa.smartcode.novobit.eu -
- fe-visio.dev.prm.novobit.eu | 4010 | https://fe-visio.dev.prm.novobit.eu -
- fe-lnk.dev.prm.novobit.eu | 4011 | https://fe-lnk.dev.prm.novobit.eu -
- fe-minio.dev.prm.novobit.eu | 9001 | https://fe-minio.dev.prm.novobit.eu -
