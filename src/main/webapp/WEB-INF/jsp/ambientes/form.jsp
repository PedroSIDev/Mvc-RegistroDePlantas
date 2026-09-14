<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Ambiente - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/ambientes">Ambientes</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>${empty ambiente or empty ambiente.id ? 'Novo ambiente' : 'Editar ambiente'}</h1>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ambientes">Voltar</a>
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

        <form action="${pageContext.request.contextPath}/ambientes" method="post" class="card">
            <input type="hidden" name="id" value="${ambiente.id}">

            <div class="form-group">
                <label for="nome">Nome</label>
                <input id="nome" name="nome" type="text" value="${ambiente.nome}" required>
            </div>

            <div class="form-group">
                <label for="descricao">Descrição</label>
                <input id="descricao" name="descricao" type="text" value="${ambiente.descricao}">
            </div>

            <div class="actions">
                <button class="btn" type="submit">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ambientes">Cancelar</a>
            </div>
        </form>
    </main>
</body>
</html>
