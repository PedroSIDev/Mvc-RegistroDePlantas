<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Ambientes - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/ambientes">Ambientes</a>
                <a href="${pageContext.request.contextPath}/cuidados">Cuidados</a>
                <a href="${pageContext.request.contextPath}/plantas">Plantas</a>
                <a href="${pageContext.request.contextPath}/lembretes">Lembretes</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>Ambientes</h1>
            <a class="btn" href="${pageContext.request.contextPath}/ambientes?acao=novo">Novo ambiente</a>
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
                        <th>Descrição</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty ambientes}">
                            <c:forEach var="ambiente" items="${ambientes}">
                                <tr>
                                    <td>${ambiente.id}</td>
                                    <td>${ambiente.nome}</td>
                                    <td>${ambiente.descricao}</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/ambientes?acao=editar&id=${ambiente.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/ambientes?acao=excluir&id=${ambiente.id}" onclick="return confirm('Deseja excluir este ambiente?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="empty">Nenhum ambiente cadastrado.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
