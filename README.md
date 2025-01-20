# API de Administração do Catálogo de Vídeos

Propósito:
-
Esta aplicação serve como um backend de gerenciamento para as funcionalidades de categorias de vídeo, gêneros, elenco e vídeos.

É uma aplicação Spring Boot que interage por meio de uma API REST com um banco de dados MySQL para executar operações CRUD; além de ser responsável pelo download e upload de mídias de vídeo e imagem. 

A aplicação também utiliza o RabbitMQ para mensageria, particularmente para escutar eventos de codificação de vídeo a partir de um microsserviço codificador (encoder) de vídeos e para publicar eventos de domínio relacionados à criação e atualização de mídias de vídeo.

O Spring Security é empregado para manipular a autenticação da aplicação a partir da interação com o provedor de identidade (Identity Provider (IDP)) Keycloak.

Tecnologias envolvidas:
-
- JDK 19
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Swagger
- JUnit
- Mockito
- Docker
- Docker Compose
- RabbitMQ
- Google Cloud Storage
  
![Upload to Google Cloud Storage](media/VIDEO.webm)