# API de Administração do Catálogo de Vídeos

Propósito:
-
Servir como um backend de gerenciamento para as funcionalidades de categorias de vídeo, gêneros, elenco e vídeos.

A aplicação permite o cadastro de vídeos, categorias e usuários, além de permitir a associação de vídeos com categorias. A aplicação também permite o upload de vídeos para o Google Cloud Storage.

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

#### Referência
CURSO FULL CYCLE 3.0. Projeto prático - Java (Back-end). 2025. Disponível em: https://plataforma.fullcycle.com.br. Acesso em: 20 jan. 2025.
