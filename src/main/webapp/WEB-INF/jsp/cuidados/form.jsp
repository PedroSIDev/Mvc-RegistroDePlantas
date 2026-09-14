<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Cuidado - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/cuidados">Cuidados</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>${empty cuidado or empty cuidado.id ? 'Novo cuidado' : 'Editar cuidado'}</h1>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/cuidados">Voltar</a>
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

        <form action="${pageContext.request.contextPath}/cuidados" method="post" class="card">
            <input type="hidden" name="id" value="${cuidado.id}">

            <div class="form-group">
                <label for="nome">Nome</label>
                <input id="nome" name="nome" type="text" value="${cuidado.nome}" required>
            </div>

            <div class="form-group">
                <label for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao" rows="4">${cuidado.descricao}</textarea>
            </div>

            <div class="form-group">
                <label for="diasIntervalo">Intervalo em dias</label>
                <input id="diasIntervalo" name="diasIntervalo" type="number" min="1" value="${cuidado.diasIntervalo}" required>
            </div>

            <div class="actions">
                <button class="btn" type="submit">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/cuidados">Cancelar</a>
            </div>
        </form>
    </main>
</body>
</html>
