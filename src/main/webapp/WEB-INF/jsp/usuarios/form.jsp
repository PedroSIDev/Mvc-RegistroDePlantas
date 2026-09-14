<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Usuário - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>${empty usuario or empty usuario.id ? 'Novo usuário' : 'Editar usuário'}</h1>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/usuarios">Voltar</a>
        </div>

        <c:if test="${not empty erros}">
            <div class="alert alert-erro">
                <ul>
                    <c:forEach var="erro" items="${erros}">
                        <li>${erro}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/usuarios" method="post" class="card">
            <input type="hidden" name="id" value="${usuario.id}">

            <div class="form-group">
                <label for="nome">Nome</label>
                <input id="nome" name="nome" type="text" value="<c:out value='${usuario.nome}'/>" required>
            </div>

            <div class="form-group">
                <label for="login">Login</label>
                <input id="login" name="login" type="text" value="<c:out value='${usuario.login}'/>" required>
            </div>

            <div class="form-group">
                <label for="senha">Senha ${not empty usuario.id ? '(Opcional)' : ''}</label>
                <input id="senha" name="senha" type="password" autocomplete="new-password" ${empty usuario.id ? 'required minlength="6"' : 'minlength="6"'}>
                <c:if test="${not empty usuario.id}">
                    <small style="display:block; margin-top: 4px; color: #666;">Deixe em branco para manter a senha atual. (Mínimo de 6 caracteres se for alterar)</small>
                </c:if>
            </div>

            <div class="form-group">
                <label for="perfilId">Perfil</label>
                <select id="perfilId" name="perfilId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="perfil" items="${perfis}">
                        <option value="${perfil.id}" ${usuario.perfilId == perfil.id ? 'selected' : ''}>${perfil.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="actions">
                <button class="btn" type="submit">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/usuarios">Cancelar</a>
            </div>
        </form>
    </main>
</body>
</html>
