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

## 🧰 Tecnologias

Java 17, Maven, Jakarta Servlet 6.0, JSP/JSTL, JDBC, MySQL 8.4, Gson, BCrypt, Tomcat 10.1, Docker Compose e JUnit 4.

## 🏗️ Arquitetura

```text
br.com.mvc
├── controller        Servlets das telas JSP
│   └── api           API REST (PlantaApiServlet, ApiJson, ApiException)
├── dto               Objetos de entrada e saída da API (PlantaEntrada, PlantaSaida)
├── service           Regras de negócio e validações
├── dao               Acesso ao MySQL com JDBC
├── model             Entidades
├── filter            AuthFilter (telas, por sessão) e ApiAuthFilter (API, HTTP Basic)
└── security          Hash de senhas (BCrypt)
```

Fluxo de uma requisição à API:

```text
Requisição → ApiAuthFilter → PlantaApiServlet → PlantaService → PlantaDAO → MySQL → JSON
```

## 🚀 Como executar

Pré-requisitos: JDK 17+, Maven 3.8+, Docker e Docker Compose.

1. Gerar o WAR (ele é salvo na pasta `deploy`):

```bash
mvn clean package
```

2. Subir o MySQL e o Tomcat:

```bash
docker compose up -d
```

3. Aguardar cerca de 30 segundos e acessar: http://localhost:8090/mvc/

Ao gerar um novo WAR, o Tomcat reimplanta sozinho (por uns 20 segundos as páginas retornam 404 e as sessões são encerradas).

Banco de dados: host `localhost`, porta `3306`, database `mvcplantas`, usuário `mvc_user`, senha `mvc123`.

Para rodar os testes: `mvn test`.

### Usuários de teste

| Login | Senha | Perfil |
|---|---|---|
| `pedro` | `123456` | Administrador |
| `miguel` | `123456` | Administrador |
| `teste` | `123456` | Jardineiro |

As senhas são guardadas com BCrypt.

## 🌐 API REST de Plantas

Base: `http://localhost:8090/mvc/api/plantas`

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
curl -i -u pedro:123456 http://localhost:8090/mvc/api/plantas

# Consultar
curl -i -u pedro:123456 http://localhost:8090/mvc/api/plantas/1

# Criar
curl -i -u pedro:123456 -X POST -H "Content-Type: application/json" \
  -d '{"nomePopular":"Manjericão","nomeCientifico":"Ocimum basilicum","dataAquisicao":"2026-11-13","observacoes":"Vaso na varanda","usuarioId":1,"ambienteId":1}' \
  http://localhost:8090/mvc/api/plantas

# Alterar (troque 5 pelo ID retornado no POST)
curl -i -u pedro:123456 -X PUT -H "Content-Type: application/json" \
  -d '{"nomePopular":"Manjericão roxo","usuarioId":1,"ambienteId":1}' \
  http://localhost:8090/mvc/api/plantas/5

# Excluir
curl -i -u pedro:123456 -X DELETE http://localhost:8090/mvc/api/plantas/5

# Sem autenticação (401)
curl -i http://localhost:8090/mvc/api/plantas
```

No PowerShell, o JSON com aspas pode dar problema no `-d`. Use um arquivo: `-d "@planta.json"`.

## 💻 Telas do sistema (JSP)

Além da API, o sistema tem telas protegidas por login para gerenciar plantas, ambientes, cuidados, lembretes, usuários e perfis (estes dois só para Administrador). A lista de plantas e a API usam o mesmo banco e as mesmas regras de negócio.

## 🗄️ Banco de Dados

O `init.sql` cria as tabelas `perfis`, `usuarios`, `ambientes`, `cuidados`, `plantas` e `lembretes` e insere dados iniciais para teste. A API não exige nenhuma tabela nova.
