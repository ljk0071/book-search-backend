# Spring Boot Template Multi-Module With Clean Architechture


### 🧷 Tech Stack

- #### Architecture : Clean Architecture

- #### Language : Java 11
- #### Infra : Docker, Redis


### Docker Compose

```bash
$ docker compose -f docker-compose-redis.yml -f docker-compose-monitor.yml build

$ docker compose -f docker-compose-redis.yml -f docker-compose-monitor.yml up -d
```


### Spring Module

- Domain : POJO 모듈

- Infrastructure : RDBMS & Redis  
  - rds
  - redis
  - kafka-producer

- library : Logging & Monitoring & Utils & api-docs 모듈
  - logging
  - monitoring
  - utils
  - api-docs

- client : REST API Module
  - rest