<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plantas - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="plantas"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>Plantas</h1>
            <p>Gerencie sua coleção botânica, espécies e localização na residência</p>
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/plantas?acao=novo">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            <span>Nova Planta</span>
        </a>
    </div>

    <c:if test="${not empty sessionScope.mensagemSucesso}">
        <div class="alert alert-sucesso">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                <polyline points="22 4 12 14.01 9 11.01"/>
            </svg>
            <span><c:out value="${sessionScope.mensagemSucesso}"/></span>
        </div>
        <c:remove var="mensagemSucesso" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.mensagemErro}">
        <div class="alert alert-erro">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="8" x2="12" y2="12"/>
                <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            <span><c:out value="${sessionScope.mensagemErro}"/></span>
        </div>
        <c:remove var="mensagemErro" scope="session" />
    </c:if>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${not empty plantas}">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 60px;">ID</th>
                            <th>Espécie / Nome</th>
                            <th>Ambiente</th>
                            <th>Responsável</th>
                            <th style="width: 110px; text-align: right;">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="planta" items="${plantas}">
                            <tr>
                                <td class="td-id">#${planta.id}</td>
                                <td>
                                    <div class="td-main"><c:out value="${planta.nomePopular}"/></div>
                                    <c:if test="${not empty planta.nomeCientifico}">
                                        <span class="td-sub"><em><c:out value="${planta.nomeCientifico}"/></em></span>
                                    </c:if>
                                </td>
                                <td>
                                    <span class="badge badge-ambiente">
                                        <c:out value="${planta.ambiente.nome}"/>
                                    </span>
                                </td>
                                <td><c:out value="${planta.usuario.nome}"/></td>
                                <td style="text-align: right;">
                                    <div class="links" style="justify-content: flex-end;">
                                        <a class="btn-icon" href="${pageContext.request.contextPath}/plantas?acao=editar&id=${planta.id}" title="Editar planta">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/>
                                            </svg>
                                        </a>
                                        <a class="btn-icon btn-icon-danger" href="${pageContext.request.contextPath}/plantas?acao=excluir&id=${planta.id}" onclick="return confirm('Deseja realmente excluir esta planta?');" title="Excluir planta">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M3 6h18"/>
                                                <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/>
                                                <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/>
                                            </svg>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-state-icon">
                        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 22v-7"/>
                            <path d="M12 3a9 9 0 0 0-9 9 9 9 0 0 0 6 8.5V15a3 3 0 0 1 6 0v5.5A9 9 0 0 0 21 12a9 9 0 0 0-9-9Z"/>
                        </svg>
                    </div>
                    <h3>Nenhuma planta registrada</h3>
                    <p>Comece adicionando as espécies da sua casa para acompanhar as rotinas de cuidados e rega.</p>
                    <a class="btn" href="${pageContext.request.contextPath}/plantas?acao=novo">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"/>
                            <line x1="5" y1="12" x2="19" y2="12"/>
                        </svg>
                        <span>Cadastrar Primeira Planta</span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

</body>
</html>
