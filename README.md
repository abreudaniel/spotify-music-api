# Requisitos
## Requisitos obrigatórios
 - [X] Seguimentação de commits
 - [X] Lint
 - [X] Autenticação via Spotify
 - [X] Listar artistas
 - [X] Listar albuns de um artista
 - [X] Utilizar paginação (scroll infinito ou não)
 - [X] Funcionamento offline
 - [X] Testes unitários
 - [X] Deploy da aplicação

## Bônus
 - [ ] Testes E2E
 - [ ] Integração com Sentry
 - [ ] CI/CD
 - [ ] Responsividade (celular e tablet)
 - [X] Qualidade de código (Sonarqube)
 - [ ] PWA


# Spotify Music API

## 📝 Descrição do Projeto
API REST desenvolvida para interagir com a API do Spotify, permitindo buscar artistas e seus álbuns, com suporte a funcionamento offline através de cache.

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**: Versão LTS do Java com recursos modernos
- **Spring Boot**: Framework para desenvolvimento de aplicações Java
- **Spring Security**: Segurança e autenticação
- **Spring Cloud OpenFeign**: Cliente HTTP declarativo
- **Memcached**: Sistema de cache distribuído
- **JaCoCo**: Cobertura de código
- **JUnit 5**: Framework de testes
- **Mockito**: Framework para mocking em testes
- **SonarQube**: Análise de qualidade de código
- **Lombok**: Redução de boilerplate code

## 🏗️ Padrões Arquiteturais e Design Patterns

- **Arquitetura em Camadas**:
    - Controller: APIs REST
    - Service: Regras de negócio
    - Repository: Camada de cache
    - Client: Integração com Spotify

- **Design Patterns**:
    - DTO (Data Transfer Object)
    - Builder
    - Factory
    - Dependency Injection
    - Cache-Aside

## 🚀 Como Executar o Projeto

### Pré-requisitos
1. Java 17
2. Maven
3. Memcached
4. SonarQube (opcional)
5. Credenciais do Spotify Developer

### Configuração do Ambiente

1. **Instalação do Memcached**:
   ```bash
   # Ubuntu/Debian
   sudo apt-get install memcached
   
   # MacOS
   brew install memcached
   
   # Iniciar serviço
   memcached -d
   ```

2. **Configuração do Spotify**:
    - Acesse [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
    - Crie um novo aplicativo
    - Obtenha o Client ID e Client Secret

3. **Configuração do application.properties**:
   ```properties
   spotify.client.id=seu-client-id
   spotify.client.secret=seu-client-secret
   memcached.servers=localhost:11211
   memcached.pool-size=1
   ```

### Executando a Aplicação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/abreudaniel/spotify-music-api.git
   cd spotify-music-api
   ```

2. **Build do projeto**:
   ```bash
   mvn clean install
   ```

3. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

### Análise de Qualidade (SonarQube)

1. **Iniciar SonarQube**:
   ```bash
   docker run -d --name sonarqube -p 9000:9000 sonarqube
   ```

2. **Executar análise**:
   ```bash
   mvn sonar:sonar
   ```

## 📡 Endpoints da API

### Autenticação

- `POST /api/token`: Gerar token de acesso
- `GET /authorize`: Iniciar fluxo de autorização
- `GET /callback`: Callback da autorização OAuth

## 📝 Notas Importantes

- A aplicação utiliza Redis para gerenciamento de sessões
- Memcached é utilizado para cache de dados
- O tempo de sessão está configurado para 600 segundos
- CORS está configurado para permitir requisições de localhost nas portas 3000 e 8080


## 🔐 Segurança
- Autenticação OAuth 2.0 com Spotify
- CORS configurado para ambientes específicos
- Cache distribuído com Memcached


