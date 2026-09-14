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

        <p>Você está autenticado no sistema.</p>

        <nav>
            <a href="${pageContext.request.contextPath}/plantas">Plantas</a>
            <a href="${pageContext.request.contextPath}/ambientes">Ambientes</a>
            <a href="${pageContext.request.contextPath}/cuidados">Cuidados</a>
            <a href="${pageContext.request.contextPath}/lembretes">Lembretes</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </main>
</body>
</html>
