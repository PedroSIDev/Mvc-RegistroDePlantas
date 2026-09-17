# Sistema de Registro de Plantas Domésticas 🌱

Projeto acadêmico desenvolvido para a disciplina de Aplicações para Internet, com foco em arquitetura MVC em Java para a gestão de plantas domésticas, ambientes, cuidados e lembretes.

## 👥 Integrantes

- Pedro Henrique – RA: 5162347
- Miguel Garcia – RA: 5160094

## 📌 Visão Geral

A aplicação permite que usuários cadastrem, consultem e gerenciem:

- plantas domésticas
- ambientes da casa onde cada planta é mantida
- tipos de cuidados botânicos
- lembretes de manutenção e rotina de cuidado
- perfis de acesso e usuários do sistema

O sistema foi implementado com Java, Jakarta Servlet, JSP, JSTL, JDBC, MySQL e Docker, seguindo a organização em camadas MVC.

## ✨ Funcionalidades

- autenticação de usuários com sessão
- cadastro e edição de usuários
- controle de perfis (Administrador e Jardineiro)
- cadastro de ambientes
- cadastro de plantas com vínculo a usuário e ambiente
- cadastro de tipos de cuidado
- agendamento de lembretes por planta e cuidado
- atualização do status dos lembretes (PENDENTE, CONCLUIDO, CANCELADO)
- filtros de acesso para rotas protegidas
- mensagens de sucesso e erro na interface

## 🏗️ Arquitetura

A estrutura principal do código segue o padrão MVC:

- `br.com.mvc.model` — entidades de negócio
- `br.com.mvc.dao` — acesso ao banco com JDBC
- `br.com.mvc.service` — regras de negócio e validações
- `br.com.mvc.controller` — servlets responsáveis por requisições HTTP
- `br.com.mvc.filter` — controle de autenticação e proteção de rotas
- `src/main/webapp/WEB-INF/jsp` — páginas JSP da camada de visualização

## 🗄️ Banco de Dados

O banco é criado automaticamente pelo script `init.sql` e inclui as tabelas:

- `perfis`
- `usuarios`
- `ambientes`
- `cuidados`
- `plantas`
- `lembretes`

Além disso, o script já insere dados iniciais para testes, como usuários, perfis, ambientes, cuidados e plantas.

## 🔐 Usuários padrão

Os usuários de seed criados no banco são:

- login: `pedro` | senha: `123456`
- login: `miguel` | senha: `123456`
- login: `teste` | senha: `123456`

A senha é armazenada em formato BCrypt.

## 🧰 Tecnologias

- Java 17
- Maven
- Jakarta Servlet 6.0
- JSP + JSTL
- MySQL 8.4
- Apache Tomcat 10.1
- Docker e Docker Compose
- JUnit 4 para testes

## 🚀 Como executar

### Pré-requisitos

- JDK 17+
- Maven 3.8+
- Docker + Docker Compose

### 1. Subir os containers

Na raiz do projeto, execute:

```bash
docker compose up -d
```

Esse comando inicia:

- container do MySQL na porta 3306
- container do Tomcat na porta 8080

### 2. Compilar o projeto

```bash
mvn clean package
```

O WAR gerado é empacotado para a pasta `deploy` conforme configuração do Maven.

### 3. Acessar a aplicação

Após a compilação e o container estar funcionando, acesse:

- http://localhost:8080/mvc/

### 4. Conexão com banco

- host: `localhost`
- porta: `3306`
- database: `mvcplantas`
- usuário: `mvc_user`
- senha: `mvc123`

## 📁 Estrutura do projeto

```text
mvc/
├── docker-compose.yml
├── init.sql
├── pom.xml
├── README.md
├── deploy/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── webapp/
│   └── test/
├── target/
└── mysql/
```

## 🧪 Testes

O projeto contém testes unitários na pasta `src/test/java`, cobrindo regras de negócio dos serviços principais.

Para executar os testes:

```bash
mvn test
```

## ✅ Observações

- O processo de autenticação protege rotas internas e redireciona usuários não autenticados para a tela de login.
- A aplicação foi pensada para uso em ambiente acadêmico e demonstração de MVC com Java EE/Jakarta.
- O arquivo `docker-compose.yml` já automatiza a inicialização do banco e do servidor web.

---
