# 🚀 RocketNotes API

RocketNotes é uma API desenvolvida para o gerenciamento eficiente de notas. Este projeto faz parte de uma atividade proposta pela equipe da Rocketseat na trilha Explore. No entanto, decidi ir além e recriar a API utilizando Java, enquanto na trilha original foi empregada a tecnologia Node.js.

---

# 🛠 Tecnologias Utilizadas

Esta API foi contruida com as seguintes tecnologias:
- **Java 21**: Linguagem de Programação principal, garantindo desempenho e modernidade.
- **Spring Boot 3.4.0**: Framework para criação de aplicações robustas e escaláveis.
  - **Spring Web**: Para construção de endpoints RESTful.
  - **Spring Data JPA**: Para integração e manipulação com o banco de dados.
  - **Spring Security**: Para garantir segurança da aplicação.
  - **Spring DevTools**: Para desenvolvimento rápido.
- **Hibernate**: ORM (Object Relational Mapping) para mapeamento de entidades.
- **Flyway**: Para controle de versão do banco de dados.
- **SQLite**: Banco de dados relacional leve e de fácil configuração.
- **Lombok**: Para reduzir a verbosidade do código.

---

## ⚙️ Funcionalidades

- **Criação, leitura, atualização e exclusão (CRUD) de notas**.
- **Organização de notas por títulos ou tags**.
- **Cadastro e atualização de usuários**.
- **Integração com banco de dados SQLite para persistência de dados**.
- **Migrações de banco de dados automatizadas com Flyway**.

---

## 📦 Instalação e Execução

### Pré-requisitos:
- Java 21 ou superior
- Maven 3.8 ou superior
- 
### Passos:
1. Clone este repositório:
   ```bash
   git clone https://github.com/rafaelsa/rocketnotes-api.git
   
2. Navegue até o diretório do projeto:
    ```bash
   cd rocketnotes-api

3. Execute o projeto com Maven:
    ```bash
   mvn spring-boot:run

4. Acesse a API na URL padrão
    ```bash
   http://localhost:8080

---

## 🌟 Contribuição

Sinta-se à vontade para contribuir com este projeto. Sugestões, melhorias e relatórios de bugs são bem-vindos!
