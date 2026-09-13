<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<main class="container">
    <h1>Plantas Domésticas</h1>
    <h2>Entrar</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="login">Login</label>
        <input id="login" name="login" type="text" required>

        <label for="senha">Senha</label>
        <input id="senha" name="senha" type="password" required>

        <button type="submit">Entrar</button>
    </form>
</main>

</body>
</html>
