<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Planta - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/plantas">Plantas</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>${empty planta or empty planta.id ? 'Nova planta' : 'Editar planta'}</h1>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/plantas">Voltar</a>
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

        <form action="${pageContext.request.contextPath}/plantas" method="post" class="card">
            <input type="hidden" name="id" value="${planta.id}">

            <div class="form-group">
                <label for="nomePopular">Nome popular</label>
                <input id="nomePopular" name="nomePopular" type="text" value="${planta.nomePopular}" required>
            </div>

            <div class="form-group">
                <label for="nomeCientifico">Nome científico</label>
                <input id="nomeCientifico" name="nomeCientifico" type="text" value="${planta.nomeCientifico}">
            </div>

            <div class="form-group">
                <label for="dataAquisicao">Data de aquisição</label>
                <input id="dataAquisicao" name="dataAquisicao" type="date" value="${planta.dataAquisicao}">
            </div>

            <div class="form-group">
                <label for="usuarioId">Usuário</label>
                <select id="usuarioId" name="usuarioId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="usuario" items="${usuarios}">
                        <option value="${usuario.id}" ${planta.usuarioId == usuario.id ? 'selected' : ''}>${usuario.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="ambienteId">Ambiente</label>
                <select id="ambienteId" name="ambienteId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="ambiente" items="${ambientes}">
                        <option value="${ambiente.id}" ${planta.ambienteId == ambiente.id ? 'selected' : ''}>${ambiente.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="observacoes">Observações</label>
                <textarea id="observacoes" name="observacoes" rows="4">${planta.observacoes}</textarea>
            </div>

            <div class="actions">
                <button class="btn" type="submit">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/plantas">Cancelar</a>
            </div>
        </form>
    </main>
</body>
</html>
