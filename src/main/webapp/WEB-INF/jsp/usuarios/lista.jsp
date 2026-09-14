<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Usuários - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
                <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>Usuários</h1>
            <a class="btn" href="${pageContext.request.contextPath}/usuarios?acao=novo">Novo usuário</a>
        </div>

        <c:if test="${not empty sessionScope.mensagemSucesso}">
            <div class="alert alert-erro">${sessionScope.mensagemSucesso}</div>
            <c:remove var="mensagemSucesso" scope="session" />
        </c:if>

        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Login</th>
                        <th>Perfil</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty usuarios}">
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr>
                                    <td>${usuario.id}</td>
                                    <td>${usuario.nome}</td>
                                    <td>${usuario.login}</td>
                                    <td>${usuario.perfil.nome}</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/usuarios?acao=editar&id=${usuario.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/usuarios?acao=excluir&id=${usuario.id}" onclick="return confirm('Deseja excluir este usuário?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="empty">Nenhum usuário cadastrado.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
