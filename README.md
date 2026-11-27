# Sistema de Registro de Plantas Domésticas 🌱

Projeto da disciplina de Aplicações para Internet. Sistema em Java para gerenciar plantas domésticas, ambientes, cuidados e lembretes, com telas web (JSP) e uma **API REST de plantas** (Trabalho T2).

## 👥 Integrantes

- Pedro Henrique – RA: 5162347
- Miguel Garcia – RA: 5160094

## ✅ Requisitos do Trabalho T2

| Requisito | Como foi atendido |
|---|---|
| API RESTful em Java com MVC | Endpoints em `/api/plantas`, entrada e saída em JSON |
| Camadas Controller, Service e DAO | `controller/api` → `service` → `dao` |
| CRUD sobre uma entidade | Entidade **Planta** (criar, listar, consultar, alterar, excluir) |
| Banco MySQL | MySQL 8.4 via Docker, criado pelo `init.sql` |
| Boas práticas de organização | Pacotes por camada, DTOs, tratamento central de erros e status HTTP corretos |
| Documentação dos endpoints | Seção [API REST](#-api-rest-de-plantas) deste README |
| MVC e API separados em containers | 3 containers independentes: `mvc`, `api` e `mysql`; o MVC consome a API por HTTP/JSON |

## 🧰 Tecnologias

Java 17, Maven, Jakarta Servlet 6.0, JSP/JSTL, JDBC, MySQL 8.4, Gson, BCrypt, Tomcat 10.1, Docker Compose, `java.net.http.HttpClient` e JUnit 4.

## 🐳 Arquitetura: três containers independentes

```text
                 navegador                         Postman / curl
                     │ :8090                           │ :8081
                     ▼                                 ▼
┌──────────────────────────────┐   HTTP/JSON   ┌──────────────────────────────┐
│  mvc  (Tomcat 10.1)          │ ────────────► │  api  (Tomcat 10.1)          │
│  JSP, Servlets, sessão,      │ http://api:8080│  /api/plantas (CRUD)         │
│  login, perfis, permissões   │  Basic Auth   │  validações e regras         │
│  PlantaApiClient (HttpClient)│               │  PlantaService → PlantaDAO   │
└──────────────┬───────────────┘               └──────────────┬───────────────┘
               │ JDBC (módulos sem API)                       │ JDBC
               ▼                                              ▼
           ┌──────────────────────────────────────────────────────┐
           │  mysql  (MySQL 8.4)  — banco mvcplantas (init.sql)   │
           └──────────────────────────────────────────────────────┘
```

| Container | Imagem | Porta no PC | Função |
|---|---|---|---|
| `plantas-mvc` | construída de `mvc/Dockerfile` | `8090` | Telas JSP, login/sessão, perfis e permissões. Faz o CRUD de plantas **chamando a API** |
| `plantas-api` | construída de `api/Dockerfile` | `8081` | API REST de plantas (JSON), autenticação HTTP Basic, validações, regras de negócio e acesso ao MySQL |
| `plantas-mysql` | `mysql:8.4` | `3306` | Banco compartilhado, criado pelo `database/init.sql` |

Os três ficam na rede Docker `plantas-net`. Dentro dela, o MVC chama a API pelo **nome do serviço**
(`API_BASE_URL=http://api:8080`) e as duas aplicações chegam ao banco por `DB_HOST=mysql`. Nenhuma
comunicação entre containers usa `localhost`.

### Estrutura de pastas

```text
.
├── docker-compose.yml        3 serviços: mysql, api, mvc
├── database/
│   └── init.sql              tabelas e dados iniciais
├── api/                      APLICAÇÃO REST (WAR próprio)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/br/com/mvc/
│       ├── controller/api    PlantaApiServlet, BaseApiServlet, ApiJson, ApiException, ApiNotFoundServlet
│       ├── dto               PlantaEntrada, PlantaSaida
│       ├── filter            ApiAuthFilter (HTTP Basic)
│       ├── service           PlantaService, UsuarioService (autenticação), Validacao
│       ├── dao               PlantaDAO, UsuarioDAO, AmbienteDAO, PerfilDAO, MysqlDAO
│       ├── model             Planta, Usuario, Ambiente, Perfil
│       ├── security          PasswordUtil (BCrypt)
│       └── config            MysqlSingleton, EncerramentoListener
└── mvc/                      APLICAÇÃO WEB (WAR próprio)
    ├── Dockerfile
    ├── pom.xml
    └── src/main/
        ├── java/br/com/mvc/
        │   ├── client        PlantaApiClient (HttpClient), ApiClientException, PlantaRelacionamentos
        │   ├── controller    Servlets das telas (PlantaServlet usa a API)
        │   ├── filter        AuthFilter (sessão e permissões)
        │   ├── service       Usuario, Perfil, Ambiente, Cuidado, Lembrete
        │   ├── dao           DAOs dos módulos que ainda não têm API
        │   ├── model, security, config
        └── webapp            JSP, CSS e JS (layout preservado)
```

### Como o MVC conversa com a API

Toda operação de plantas feita nas telas vira uma requisição HTTP com JSON:

| Tela do MVC | Requisição feita pelo `PlantaApiClient` |
|---|---|
| Lista de plantas | `GET http://api:8080/api/plantas` |
| Editar planta | `GET http://api:8080/api/plantas/{id}` |
| Salvar nova planta | `POST http://api:8080/api/plantas` |
| Salvar edição | `PUT http://api:8080/api/plantas/{id}` |
| Excluir planta | `DELETE http://api:8080/api/plantas/{id}` |
| Formulário de lembrete (lista e validação da planta) | `GET http://api:8080/api/plantas` e `/{id}` |

- Os erros da API voltam para a tela: **422** mostra a lista de validações no formulário; **409** mostra o aviso de exclusão bloqueada; **404** mostra "planta não encontrada".
- Se o container `api` estiver fora, a tela de plantas mostra "O serviço de plantas (API) está indisponível" e o restante do sistema continua funcionando.
- O MVC autentica na API com HTTP Basic usando uma conta de integração (`API_USUARIO` / `API_SENHA`, padrão `pedro` / `123456`).
- Cada chamada aparece no log: `docker compose logs -f mvc` mostra linhas como `MVC -> API GET http://api:8080/api/plantas -> HTTP 200`.

## 🚀 Como executar

Pré-requisito: **Docker Desktop**. Não é preciso ter Java, Maven nem MySQL no PC: o Docker compila as duas aplicações.

```bash
docker compose up --build -d
```

| O quê | Endereço |
|---|---|
| Aplicação web (MVC) | http://localhost:8090 |
| API REST | http://localhost:8081/api/plantas (HTTP Basic: `pedro` / `123456`) |
| MySQL | `localhost:3306`, database `mvcplantas`, usuário `mvc_user`, senha `mvc123` |

Comandos úteis:

```bash
docker compose ps                 # status dos 3 containers
docker compose logs -f mvc        # ver as chamadas MVC -> API
docker compose logs -f api
docker compose stop api           # demonstrar: MVC continua no ar e avisa que a API caiu
docker compose start api
docker compose down               # para tudo (mantém o banco)
docker compose down -v            # para tudo e apaga o banco (o init.sql roda de novo)
```

Portas e credenciais podem ser trocadas com variáveis de ambiente ou um arquivo `.env`:
`MVC_PORT`, `API_PORT`, `MYSQL_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `API_USUARIO`, `API_SENHA`.

### Testes

- **API** (`api/`): os testes unitários (validação, DTO, JSON e BCrypt) não usam banco e rodam no build da imagem.
- **MVC** (`mvc/`): os testes usam o banco e a API, então rodam com os containers no ar: `cd mvc && mvn test`.

### Usuários de teste

| Login | Senha | Perfil |
|---|---|---|
| `pedro` | `123456` | Administrador |
| `miguel` | `123456` | Administrador |
| `teste` | `123456` | Jardineiro |

As senhas são guardadas com BCrypt.

## 🌐 API REST de Plantas

Base: `http://localhost:8081/api/plantas`

**Autenticação:** HTTP Basic em todas as requisições (ex.: `pedro` / `123456`). A API não usa sessão. No Postman: *Authorization → Basic Auth*.

### Endpoints

| Método | URL | Descrição | Sucesso |
|---|---|---|---|
| GET | `/api/plantas` | Lista todas as plantas | 200 |
| GET | `/api/plantas/{id}` | Consulta uma planta | 200 |
| POST | `/api/plantas` | Cria uma planta | 201 (com `Location`) |
| PUT | `/api/plantas/{id}` | Substitui os dados da planta | 200 |
| DELETE | `/api/plantas/{id}` | Exclui a planta | 204 |

### Corpo do POST e do PUT

```json
{
  "nomePopular": "Manjericão",
  "nomeCientifico": "Ocimum basilicum",
  "dataAquisicao": "2026-11-13",
  "observacoes": "Vaso na varanda",
  "usuarioId": 1,
  "ambienteId": 1
}
```

| Campo | Regra |
|---|---|
| `nomePopular` | Obrigatório, até 150 caracteres |
| `nomeCientifico` | Opcional, até 150 caracteres |
| `dataAquisicao` | Opcional, formato `AAAA-MM-DD` |
| `observacoes` | Opcional |
| `usuarioId` | Obrigatório, deve ser um usuário existente |
| `ambienteId` | Obrigatório, deve ser um ambiente existente |

O `id` não vai no corpo: no POST é gerado pelo banco e no PUT vem da URL. Campos desconhecidos são rejeitados. No PUT, os campos opcionais não enviados ficam vazios (`null`).

Resposta de uma planta:

```json
{
  "id": 4,
  "nomePopular": "Manjericão",
  "nomeCientifico": "Ocimum basilicum",
  "dataAquisicao": "2026-11-13",
  "observacoes": "Vaso na varanda",
  "usuarioId": 1,
  "ambienteId": 1
}
```

### Erros

Todo erro volta em JSON:

```json
{
  "status": 422,
  "codigo": "DADOS_INVALIDOS",
  "mensagem": "Revise os dados da planta.",
  "detalhes": ["Nome popular da planta é obrigatório."]
}
```

| Status | Quando acontece |
|---|---|
| 400 | JSON malformado, campo desconhecido, tipo errado ou ID inválido na URL |
| 401 | Credenciais ausentes ou inválidas |
| 404 | Planta ou rota inexistente |
| 405 | Método não permitido para a URL |
| 409 | Exclusão de planta que tem lembretes vinculados |
| 413 | Corpo maior que 128 KiB |
| 415 | `Content-Type` diferente de `application/json` |
| 422 | Dados inválidos (nome vazio, usuário ou ambiente inexistente, data inválida) |
| 500 | Erro interno (os detalhes ficam só no log do servidor) |

### Exemplos com curl

No Windows, use `curl.exe`. Os IDs 1 de usuário e de ambiente existem na carga inicial.

```bash
# Listar
curl -i -u pedro:123456 http://localhost:8081/api/plantas

# Consultar
curl -i -u pedro:123456 http://localhost:8081/api/plantas/1

# Criar
curl -i -u pedro:123456 -X POST -H "Content-Type: application/json" \
  -d '{"nomePopular":"Manjericão","nomeCientifico":"Ocimum basilicum","dataAquisicao":"2026-11-13","observacoes":"Vaso na varanda","usuarioId":1,"ambienteId":1}' \
  http://localhost:8081/api/plantas

# Alterar (troque 5 pelo ID retornado no POST)
curl -i -u pedro:123456 -X PUT -H "Content-Type: application/json" \
  -d '{"nomePopular":"Manjericão roxo","usuarioId":1,"ambienteId":1}' \
  http://localhost:8081/api/plantas/5

# Excluir
curl -i -u pedro:123456 -X DELETE http://localhost:8081/api/plantas/5

# Sem autenticação (401)
curl -i http://localhost:8081/api/plantas
```

No PowerShell, o JSON com aspas pode dar problema no `-d`. Use um arquivo: `-d "@planta.json"`.

## 💻 Telas do sistema (JSP)

O container MVC tem telas protegidas por login para gerenciar plantas, ambientes, cuidados, lembretes, usuários e perfis (estes dois só para Administrador). As telas de plantas não acessam o banco: elas usam a API REST, que concentra as validações e regras de negócio das plantas.

## 🗄️ Banco de Dados

O `database/init.sql` cria as tabelas `perfis`, `usuarios`, `ambientes`, `cuidados`, `plantas` e `lembretes` e insere dados iniciais para teste. Ele roda na primeira vez que o volume do MySQL é criado (para recriar: `docker compose down -v`). A separação não exigiu nenhuma tabela nova.

## ⚠️ Limitações conhecidas

- **Só o módulo de plantas tem API.** Usuários, perfis, ambientes, cuidados e lembretes continuam no MVC com Service e DAO próprios, acessando o MySQL diretamente, como antes. Por isso o container `mvc` também recebe as variáveis `DB_*`.
- **Leituras de vínculo no banco pelo MVC:** a listagem de lembretes faz `JOIN` com `plantas` para mostrar o nome da planta, e a exclusão de usuário/ambiente confere no banco se há plantas vinculadas. São apenas leituras; toda gravação de plantas passa pela API.
- **Conta de integração:** o MVC chama a API sempre com o mesmo login (`API_USUARIO`), e não com o usuário logado na tela. A sessão do MVC guarda só o hash BCrypt da senha, então não há como repassar a credencial de cada usuário sem guardar a senha em texto puro.
- Os nomes de usuário e ambiente exibidos na lista de plantas vêm das tabelas que o MVC já lê, porque a API devolve apenas `usuarioId` e `ambienteId` (o contrato JSON da API foi mantido).
