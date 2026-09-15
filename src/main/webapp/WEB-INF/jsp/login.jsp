<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entrar - Sistema de Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body class="login-page">

<div class="login-card">
    <div class="login-header">
        <div class="login-emblem">
            <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M11 20A7 7 0 0 1 9.8 6.1C15.5 5 17 4.48 19 2c1 2 2 4.18 2 8 0 5.5-4.78 10-10 10Z"/>
                <path d="M2 21c0-3 1.85-5.36 5.08-6C9.5 14.52 12 13 13 12"/>
            </svg>
        </div>
        <h1>Plantas Domésticas</h1>
        <p>Acesse seu painel botânico para continuar</p>
    </div>

    <c:if test="${not empty erroLogin}">
        <div class="alert alert-erro">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="8" x2="12" y2="12"/>
                <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            <span><c:out value="${erroLogin}"/></span>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="login">Login de acesso</label>
            <input id="login" name="login" type="text" placeholder="Ex: pedro" autofocus required>
        </div>

        <div class="form-group">
            <label for="senha">Senha</label>
            <input id="senha" name="senha" type="password" placeholder="••••••••" required>
        </div>

        <button type="submit" class="btn">
            <span>Acessar Painel</span>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="5" y1="12" x2="19" y2="12"/>
                <polyline points="12 5 19 12 12 19"/>
            </svg>
        </button>
    </form>

    <div class="login-footer">
        Arquitetura MVC &bull; Jakarta Servlet &bull; MySQL
    </div>
</div>

</body>
</html>
