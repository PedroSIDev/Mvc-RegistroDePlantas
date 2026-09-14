<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lembretes - Registro de Plantas</title>
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
            <h1>Lembretes</h1>
            <a class="btn" href="${pageContext.request.contextPath}/lembretes?acao=novo">Novo lembrete</a>
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
                        <th>Planta</th>
                        <th>Cuidado</th>
                        <th>Data Agendada</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty lembretes}">
                            <c:forEach var="lembrete" items="${lembretes}">
                                <tr>
                                    <td>${lembrete.id}</td>
                                    <td>${lembrete.planta.nomePopular}</td>
                                    <td>${lembrete.cuidado.nome}</td>
                                    <td>${lembrete.dataAgendada}</td>
                                    <td>${lembrete.status}</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/lembretes?acao=editar&id=${lembrete.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/lembretes?acao=excluir&id=${lembrete.id}" onclick="return confirm('Deseja excluir este lembrete?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="empty">Nenhum lembrete cadastrado.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
