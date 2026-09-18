<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ambientes - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="ambientes"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>Ambientes</h1>
            <p>Espaços e cômodos mapeados para organizar suas plantas e condições de luz</p>
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/ambientes?acao=novo">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            <span>Novo Ambiente</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <c:if test="${not listaFalhou}">
    <div class="table-wrap">
        <c:choose>
            <c:when test="${not empty ambientes}">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 60px;">ID</th>
                            <th>Nome do Ambiente</th>
                            <th>Descrição / Características</th>
                            <th style="width: 110px; text-align: right;">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ambiente" items="${ambientes}">
                            <tr>
                                <td class="td-id">#${ambiente.id}</td>
                                <td>
                                    <div class="td-main"><c:out value="${ambiente.nome}"/></div>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty ambiente.descricao}">
                                            <span style="color: var(--text-secondary);"><c:out value="${ambiente.descricao}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: var(--text-muted); font-style: italic;">Sem descrição informada</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="text-align: right;">
                                    <div class="links" style="justify-content: flex-end;">
                                        <a class="btn-icon" href="${pageContext.request.contextPath}/ambientes?acao=editar&id=${ambiente.id}" title="Editar ambiente">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/>
                                            </svg>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/ambientes" method="post" class="delete-form"
                                              data-nome="<c:out value='${ambiente.nome}'/>"
                                              onsubmit="return window.confirmarExclusao ? window.confirmarExclusao(event) : confirm('Deseja realmente excluir este registro?');">
                                            <input type="hidden" name="acao" value="excluir">
                                            <input type="hidden" name="id" value="${ambiente.id}">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <button type="submit" class="btn-icon btn-icon-danger" title="Excluir ambiente" aria-label="Excluir ambiente">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M3 6h18"/>
                                                <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/>
                                                <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/>
                                            </svg>
                                        </button>
                                        </form>
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
                            <rect width="7" height="7" x="3" y="3" rx="1"/>
                            <rect width="7" height="7" x="14" y="3" rx="1"/>
                            <rect width="7" height="7" x="14" y="14" rx="1"/>
                            <rect width="7" height="7" x="3" y="14" rx="1"/>
                        </svg>
                    </div>
                    <h3>Nenhum ambiente cadastrado</h3>
                    <p>Cadastre cômodos da sua residência como Sala, Varanda ou Escritório para alocar suas plantas.</p>
                    <a class="btn" href="${pageContext.request.contextPath}/ambientes?acao=novo">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"/>
                            <line x1="5" y1="12" x2="19" y2="12"/>
                        </svg>
                        <span>Cadastrar Primeiro Ambiente</span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    </c:if>
</main>

</body>
</html>
