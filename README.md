# Book Search App Build by Spring Boot Template Multi-Module With Clean Architechture

#### Referenced by java-boot-template by [@kimsunhak](https://github.com/kimsunhak)
#### thank you so much


### 🧷 Tech Stack

- #### Architecture : Clean Architecture

- #### Language : Java 21
- #### Infra : Postgresql


[//]: # (### Docker Compose)

[//]: # ()
[//]: # (```bash)

[//]: # ($ docker compose -f docker-compose-redis.yml -f docker-compose-monitor.yml build)

[//]: # ()
[//]: # ($ docker compose -f docker-compose-redis.yml -f docker-compose-monitor.yml up -d)

[//]: # (```)


### Spring Module

- Domain : POJO 모듈

- Infrastructure : RDBMS
  - postgresql
  
[//]: # (- Infrastructure : RDBMS & Redis  )

[//]: # (  - rds)

[//]: # (  - redis)

[//]: # (  - kafka-producer)

- library : Logging & Monitoring & Utils & api-docs 모듈
  - logging
  - monitoring
  - utils
  - api-docs

- client : REST API Module
  - rest