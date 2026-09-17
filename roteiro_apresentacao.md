# Roteiro de Apresentação do Projeto MVC de Plantas Domésticas

## 1. Introdução

Este projeto foi desenvolvido para a disciplina de Aplicações para Internet e tem como objetivo gerenciar plantas domésticas, ambientes, cuidados, lembretes e usuários.

A aplicação foi construída em Java utilizando a arquitetura MVC (Model-View-Controller), com uso de:

- Java
- Jakarta Servlet
- JSP
- JDBC
- MySQL
- Maven
- Docker
- BCrypt para criptografia de senhas

A ideia principal é organizar todas as operações do sistema em camadas, separando regras de negócio, acesso ao banco, controle de requisições e interface gráfica.

---

## 2. Objetivo do sistema

O sistema permite que o usuário:

- cadastre usuários
- faça login e autenticação
- acesse páginas protegidas somente com sessão ativa
- cadastre ambientes da casa
- cadastre plantas com seus dados
- associe cada planta a um usuário e a um ambiente
- cadastre tipos de cuidado
- crie lembretes de manutenção
- atualize o status dos lembretes
- acompanhe a rotina de cuidado das plantas

Em resumo, ele funciona como uma ferramenta digital para cuidar de plantas domésticas de forma organizada.

---

## 3. Estrutura do projeto

A arquitetura do projeto segue o padrão MVC:

- Model: representa as entidades do sistema
- View: páginas JSP que mostram os dados para o usuário
- Controller: servlets que recebem requisições HTTP
- DAO: camada responsável por acessar o banco de dados
- Service: contém regras de negócio e validações
- Filter: controla autenticação e autorização

### Estrutura principal

- `src/main/java/br/com/mvc/model` -> classes de domínio
- `src/main/java/br/com/mvc/dao` -> acesso ao banco
- `src/main/java/br/com/mvc/service` -> regras de negócio
- `src/main/java/br/com/mvc/controller` -> servlets
- `src/main/java/br/com/mvc/filter` -> filtros de segurança
- `src/main/webapp/WEB-INF/jsp` -> telas JSP
- `init.sql` -> script do banco de dados

---

## 4. Banco de dados

O banco de dados foi criado a partir do arquivo `init.sql`.

As principais tabelas são:

### 4.1 Tabela `perfis`
Armazena os tipos de perfil do sistema, como:

- Administrador
- Jardineiro

### 4.2 Tabela `usuarios`
Guarda os dados dos usuários, como:

- nome
- login
- senha
- perfil_id

A senha é armazenada com criptografia BCrypt, o que aumenta a segurança.

### 4.3 Tabela `ambientes`
Representa locais da casa onde as plantas ficam, por exemplo:

- Sala de Estar
- Varanda
- Jardim de Inverno
- Quarto

### 4.4 Tabela `cuidados`
Define tipos de cuidados para as plantas, como:

- Rega Moderada
- Adubação Orgânica
- Poda de Limpeza
- Banho de Sol

### 4.5 Tabela `plantas`
Guarda as plantas vinculadas a:

- um usuário responsável
- um ambiente

### 4.6 Tabela `lembretes`
Guarda lembretes de cuidado com campos como:

- planta_id
- cuidado_id
- data_agendada
- data_realizada
- status
- observacao

Esse relacionamento mostra que o projeto é bem mais do que um CRUD simples: ele modela o domínio real de manutenção de plantas.

---

## 5. Por que o MVC foi usado?

O MVC foi escolhido porque separa responsabilidades e facilita a manutenção.

### Model
Representa os dados do sistema.

Exemplo: a classe `Usuario`, `Planta`, `Ambiente`, `Lembrete`.

Essas classes possuem atributos e métodos de acesso (`get` e `set`).

### View
São as páginas JSP, como:

- login.jsp
- home.jsp
- listagem de plantas
- cadastro de usuários
- cadastro de ambientes

### Controller
Os servlets recebem requisições e decidem o que fazer:

- listar dados
- salvar dados
- editar
- excluir
- encaminhar para outra página

### DAO
A camada DAO acessa o banco usando SQL e devolve objetos Java.

### Service
A camada Service valida regras de negócio antes de gravar no banco.

Essa divisão é importante porque deixa o código organizado e mais fácil de evoluir.

---

## 6. Configuração da conexão com o banco

A conexão com o banco é centralizada na classe `MysqlSingleton`.

Ela é responsável por:

- carregar o driver JDBC
- montar a URL de conexão
- autenticar com MySQL
- reutilizar a mesma conexão
- tentar novamente em caso de falha temporária

### Por que usar Singleton?
Porque o padrão Singleton garante que a aplicação tenha uma única instância da classe de conexão, evitando vários objetos de conexão abertos ao mesmo tempo.

Isso economiza recursos e reduz problemas de performance.

A URL de conexão usa:

- host: localhost
- banco: mvcplantas
- usuário: mvc_user
- senha: mvc123

A classe também usa `System.getenv()` para permitir configuração via variáveis de ambiente, o que torna o projeto mais flexível.

---

## 7. Camada DAO

A classe base `MysqlDAO` atua como apoio para as classes DAO específicas.

Cada DAO possui métodos específicos, por exemplo:

- `UsuarioDAO` -> lista, busca, insere, altera e exclui usuários
- `AmbienteDAO` -> gerencia ambientes
- `PlantaDAO` -> gerencia plantas
- `LembreteDAO` -> gerencia lembretes
- `PerfilDAO` -> gerencia perfis
- `CuidadoDAO` -> gerencia tipos de cuidado

### Exemplo de funcionamento

O DAO faz a Query SQL e transforma o resultado em objetos Java. Por exemplo, o `UsuarioDAO` faz um SELECT e cria um objeto `Usuario` com:

- id
- nome
- login
- senha
- perfil

Esse processo é importante porque o Java trabalha com objetos, e o banco trabalha com tabelas e registros.

---

## 8. Camada Service

A camada Service é onde ficam as regras de negócio.

### Exemplo: `UsuarioService`

Esse service valida dados antes de gravar no banco.

Validações principais:

- nome obrigatório
- login obrigatório
- login duplicado
- senha obrigatória no cadastro
- senha mínima de 6 caracteres
- perfil obrigatório
- não excluir o usuário logado
- não excluir o último administrador

### Por que isso está no Service?
Porque a regra não é do banco e nem da tela. Ela faz parte da lógica do sistema.

Se esta validação ficasse somente no front-end, ela poderia ser contornada facilmente. Centralizar isso no backend é a melhor prática.

---

## 9. Controllers e fluxo da aplicação

Os servlets controlam a navegação e as ações da interface.

### Exemplo: `AmbienteServlet`

Esse servlet funciona com dois métodos principais:

- `doGet()`
- `doPost()`

### `doGet()`
Responsável por:

- listar ambientes
- abrir formulário de cadastro
- abrir formulário de edição
- excluir ambiente

### `doPost()`
Responsável por:

- receber os dados do formulário
- criar um objeto `Ambiente`
- validar
- salvar ou atualizar no banco
- redirecionar para a tela correta

### Exemplo de fluxo

1. o usuário clica no botão de cadastrar ambiente
2. o navegador chama o servlet
3. o servlet recebe os dados
4. o service valida
5. o DAO grava no banco
6. o usuário é redirecionado para a listagem

Esse padrão se repete em praticamente todos os servlets do projeto.

---

## 10. BaseServlet

A classe `BaseServlet` centraliza métodos comuns usados por todos os controllers.

Ela contém:

- `forward()` -> encaminha para uma JSP
- `redirect()` -> redireciona para outra rota
- `parseId()` -> converte id recebida em Long

### Por que isso é útil?
Porque evita duplicação de código e deixa os servlets mais limpos.

Isso é uma boa prática de reutilização e organização.

---

## 11. Filtragem de autenticação

A segurança da aplicação está em `AuthFilter`.

Esse filtro intercepta todas as requisições e verifica:

- se a rota é pública ou privada
- se o usuário está autenticado
- se o usuário possui permissão para acessar a área

### Recursos públicos
Algumas rotas podem ser acessadas sem login, como:

- landing
- login
- logout
- páginas estáticas como CSS e JS

### Rotas protegidas
Se uma rota privada for acessada sem autenticação, o filtro redireciona para `/login`.

### Controle de autorização por perfil
O filtro também bloqueia acesso administrativo:

- usuários com perfil de Administrador podem gerenciar usuários e perfis
- outros perfis são bloqueados

Esse controle mostra que o projeto não só cadastra dados, mas também protege acessos sensíveis.

---

## 12. Segurança e senha

A criptografia de senha é feita por `PasswordUtil`.

### Por que usar BCrypt?
Porque é muito mais seguro armazenar senhas em hash, em vez de texto puro.

Se o sistema fosse salvo em texto puro, qualquer pessoa com acesso ao banco poderia ver todas as senhas.

No projeto, a autenticação verifica se a senha digitada corresponde ao hash armazenado.

Isso é uma boa prática de segurança da informação e demonstra maturidade no desenvolvimento.

---

## 13. Fluxo de login

O fluxo de autenticação funciona assim:

1. o usuário entra na tela de login
2. informa login e senha
3. o servlet chama `UsuarioService.autenticar()`
4. o service consulta o usuário no banco
5. a senha é validada com BCrypt
6. se correta, o usuário é armazenado na sessão
7. o filtro permite o acesso às páginas protegidas

Isso mostra a importância da sessão na arquitetura web Java.

---

## 14. JSP e interface visual

Os arquivos JSP estão em `src/main/webapp/WEB-INF/jsp`.

Essas páginas são responsáveis por:

- renderizar formulários
- mostrar mensagens de sucesso e erro
- listar registros em tabelas
- receber dados do usuário
- navegar entre telas

### Exemplos

- login.jsp
- landing.jsp
- home.jsp
- ambientes/form.jsp
- plantas/lista.jsp
- usuarios/form.jsp

Essa parte da aplicação conecta o backend com a experiência do usuário.

---

## 15. Por que o projeto é relevante academicamente?

Este projeto demonstra vários conceitos importantes da disciplina de aplicações para internet:

- arquitetura web em camadas
- uso de Java e servlet
- uso de banco relacional
- modelo de dados com relacionamento
- autenticação e autorização
- segurança de senhas
- uso de sessão
- manipulação de CRUD
- separação de responsabilidades

Ele funciona como exemplo prático de um sistema web completo, mostrando como teoria e prática se conectam.

---

## 16. O que eu aprendi com o projeto

Durante o desenvolvimento do projeto, aprendi que:

- separar as camadas deixa o código mais legível
- regras de negócio devem ficar em um lugar centralizado
- banco de dados precisa ser pensado antes de programar
- autenticação é essencial em sistemas web
- segurança não é um detalhe, e sim parte da aplicação
- organização aumenta a produtividade e a qualidade do software

Essas aprendizagens são muito importantes para quem quer evoluir como desenvolvedor de aplicações web.

---

## 17. Conclusão

Este sistema foi desenvolvido para demonstrar a criação de uma aplicação web completa usando arquitetura MVC em Java.

Ele reúne vários conceitos essenciais do desenvolvimento web moderno:

- persistência em banco de dados
- organização em camadas
- validação de regras de negócio
- segurança e autenticação
- navegação entre telas
- relacionamento entre dados do domínio

A aplicação representa um exemplo real de como um sistema web pode ser pensado, estruturado e implementado de forma profissional.

---

## 18. Resumo em uma frase

O projeto é um sistema web de gestão de plantas domésticas, desenvolvido em Java com MVC, banco de dados MySQL, autenticação por sessão, regras de negócio, segurança de senhas e CRUD completo para as entidades principais.

---

## 19. Dicas para apresentação oral

Para apresentar bem em sala, vale seguir este roteiro:

1. explicar o problema que o sistema resolve
2. mostrar a arquitetura MVC
3. explicar a estrutura do banco
4. demonstrar o fluxo de login
5. mostrar como os servlets controlam a aplicação
6. explicar a importância do Service e do DAO
7. falar sobre segurança
8. encerrar com os principais aprendizados

Essa ordem deixa a explicação clara, lógica e fácil de entender para a turma.

---

## 20. Frase final para fechar a apresentação

“Este projeto me ensinou que um sistema web não é apenas um conjunto de telas, mas uma arquitetura pensada para organizar dados, regras, segurança e experiência do usuário.”
