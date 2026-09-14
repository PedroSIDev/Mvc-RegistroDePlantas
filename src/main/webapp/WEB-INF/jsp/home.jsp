<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Home - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <main class="container">
        <h1>Bem-vindo ao Sistema de Plantas Domésticas</h1>

        <c:if test="${not empty sessionScope.mensagemErro}">
            <div class="alert alert-erro"><c:out value="${sessionScope.mensagemErro}"/></div>
            <c:remove var="mensagemErro" scope="session" />
        </c:if>

        <c:if test="${not empty sessionScope.mensagemSucesso}">
            <div class="alert alert-sucesso"><c:out value="${sessionScope.mensagemSucesso}"/></div>
            <c:remove var="mensagemSucesso" scope="session" />
        </c:if>

        <p>Olá, <strong><c:out value="${sessionScope.usuarioLogado.nome}"/></strong>! Perfil: <em><c:out value="${sessionScope.usuarioLogado.perfil.nome}"/></em></p>

        <nav>
            <a href="${pageContext.request.contextPath}/plantas">Plantas</a>
            <a href="${pageContext.request.contextPath}/ambientes">Ambientes</a>
            <a href="${pageContext.request.contextPath}/cuidados">Cuidados</a>
            <a href="${pageContext.request.contextPath}/lembretes">Lembretes</a>
            <c:if test="${sessionScope.usuarioLogado.perfil != null and sessionScope.usuarioLogado.perfil.nome eq 'Administrador'}">
                <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
                <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </main>
</body>
</html>
