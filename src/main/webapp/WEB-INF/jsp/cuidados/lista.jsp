<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cuidados - Registro de Plantas</title>
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
            <h1>Cuidados</h1>
            <a class="btn" href="${pageContext.request.contextPath}/cuidados?acao=novo">Novo cuidado</a>
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
                        <th>Intervalo</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty cuidados}">
                            <c:forEach var="cuidado" items="${cuidados}">
                                <tr>
                                    <td>${cuidado.id}</td>
                                    <td>${cuidado.nome}</td>
                                    <td>${cuidado.descricao}</td>
                                    <td>${cuidado.diasIntervalo} dias</td>
                                    <td class="links">
                                        <a href="${pageContext.request.contextPath}/cuidados?acao=editar&id=${cuidado.id}">Editar</a>
                                        <a href="${pageContext.request.contextPath}/cuidados?acao=excluir&id=${cuidado.id}" onclick="return confirm('Deseja excluir este cuidado?');">Excluir</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="empty">Nenhum cuidado cadastrado.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
