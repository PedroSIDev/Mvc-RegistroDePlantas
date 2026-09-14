<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Plantas - Registro de Plantas</title>
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
            <h1>Plantas</h1>
            <a class="btn" href="${pageContext.request.contextPath}/plantas?acao=novo">Nova planta</a>
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
                        <th>Nome Popular</th>
                        <th>Nome Científico</th>
                        <th>Ambiente</th>
                        <th>Dono</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty plantas}">
                            <c:forEach var="planta" items="${plantas}">
                                <tr>
                                    <td>${planta.id}</td>
                                    <td>${planta.nomePopular}</td>
                                    <td>${planta.nomeCientifico}</td>
                                    <td>${planta.ambiente.nome}</td>
                                    <td>${planta.usuario.nome}</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/plantas?acao=editar&id=${planta.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/plantas?acao=excluir&id=${planta.id}" onclick="return confirm('Deseja excluir esta planta?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="empty">Nenhuma planta cadastrada.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
