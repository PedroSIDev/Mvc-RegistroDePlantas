# Sistema de Registro de Plantas Domésticas 🌱

**Projeto Acadêmico - Arquitetura MVC em Java**  
**Disciplina:** Aplicações para Internet  

---

## 👥 Integrantes do Projeto

- **Pedro Henrique** – RA: `5162347`
- **Miguel Garcia** – RA: `5160094`

---

## 📖 Sobre o Projeto

O **Sistema de Registro de Plantas Domésticas** é uma aplicação web baseada no padrão arquitetural **MVC (Model-View-Controller)** desenvolvida com **Java**, **Jakarta Servlets**, **JSP / JSTL**, **MySQL** e **Docker**.

A aplicação permite aos usuários:
1. **Cadastrar e gerenciar plantas domésticas** pertencentes à sua coleção.
2. **Organizar as plantas por ambientes** da casa (sala, varanda, jardim de inverno, etc.).
3. **Consultar e cadastrar tipos de cuidados botânicos** (rega, adubação, poda, banho de sol, troca de vaso).
4. **Agendar e acompanhar lembretes de cuidados**, monitorando manutenções pendentes e histórico de cuidados concluídos.
5. **Autenticação e controle de perfis de acesso** (Administrador, Usuário/Jardineiro).

---

## 🏛️ Arquitetura do Software (MVC)

O projeto segue estritamente a separação de responsabilidades em camadas:

- **`br.com.mvc.model`**: Entidades e representações de negócio (`Planta`, `Usuario`, `Cuidado`, `Lembrete`, `Ambiente`, `Perfil`).
- **`br.com.mvc.dao`**: Camada de persistência (Data Access Object) responsável pelas operações SQL (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) via JDBC.
- **`br.com.mvc.service`**: Camada de regras de negócio, validações de campos e operações lógicas antes de persistir no banco.
- **`br.com.mvc.controller`**: Servlets que interceptam as requisições HTTP, coordenam a chamada aos serviços e encaminham a resposta para a view correspondente.
- **`br.com.mvc.filter`**: Filtros de autenticação e proteção de rotas privadas (`AuthFilter`).
- **`src/main/webapp/WEB-INF/jsp/`**: Camada de visualização (Views) renderizada com páginas JSP e JSTL.

---

## 🗄️ Modelagem do Banco de Dados (`init.sql`)

O banco de dados relacional foi modelado com as seguintes tabelas:

1. **`perfis`**: Perfis de usuário (Administrador, Jardineiro).
2. **`usuarios`**: Contas com credenciais de login e vínculo ao perfil.
3. **`ambientes`**: Ambientes/cômodos da casa onde as plantas estão localizadas.
4. **`cuidados`**: Tipos e parâmetros de cuidados botânicos (nome, descrição, intervalo em dias).
5. **`plantas`**: Informações da planta (nome popular, nome científico, data de aquisição, observações, usuário e ambiente).
6. **`lembretes`**: Agendamento de cuidados para cada planta com data prevista, data realizada e status (`PENDENTE`, `CONCLUIDO`, `CANCELADO`).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17+**
- **Apache Maven 3.8+**
- **Docker e Docker Compose**

### 1. Subir os Containers (MySQL + Tomcat)
Na raiz do projeto, execute:
```bash
docker compose up -d
```
> O Docker iniciará automaticamente o banco MySQL (executando o `init.sql`) e o servidor Apache Tomcat 10.1.

### 2. Compilar e Gerar o Pacote WAR
Para compilar o código fonte e gerar o arquivo `.war` na pasta de deploy:
```bash
mvn clean package
```

### 3. Acessar a Aplicação
Abra o navegador e acesse:
- **Aplicação Web:** [http://localhost:8080/mvc/](http://localhost:8080/mvc/)
- **Porta do MySQL:** `3306` (usuário: `mvc_user`, senha: `mvc123`, banco: `mvcplantas`)

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Web:** Jakarta Servlet 6.0 & JSTL 3.0
- **Servidor de Aplicação:** Apache Tomcat 10.1 (Docker)
- **Banco de Dados:** MySQL 8.4 (Docker)
- **Gerenciador de Dependências:** Apache Maven
