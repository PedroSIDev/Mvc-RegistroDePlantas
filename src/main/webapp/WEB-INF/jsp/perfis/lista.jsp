<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Perfis - Plantas Domésticas</title>
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
            <h1>Perfis</h1>
            <a class="btn" href="${pageContext.request.contextPath}/perfis?acao=novo">Novo perfil</a>
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
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty perfis}">
                            <c:forEach var="perfil" items="${perfis}">
                                <tr>
                                    <td>${perfil.id}</td>
                                    <td>${perfil.nome}</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/perfis?acao=editar&id=${perfil.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/perfis?acao=excluir&id=${perfil.id}" onclick="return confirm('Deseja excluir este perfil?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="3" class="empty">Nenhum perfil cadastrado.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
