<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cuidados - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="cuidados"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>Cuidados</h1>
            <p>Tipos de manejo, rega, adubação e podas periódicas das suas plantas</p>
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/cuidados?acao=novo">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            <span>Novo Cuidado</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <c:if test="${not listaFalhou}">
    <div class="table-wrap">
        <c:choose>
            <c:when test="${not empty cuidados}">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 60px;">ID</th>
                            <th>Procedimento / Cuidado</th>
                            <th>Frequência Recomendada</th>
                            <th style="width: 110px; text-align: right;">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cuidado" items="${cuidados}">
                            <tr>
                                <td class="td-id">#${cuidado.id}</td>
                                <td>
                                    <div class="td-main"><c:out value="${cuidado.nome}"/></div>
                                    <c:if test="${not empty cuidado.descricao}">
                                        <span class="td-sub"><c:out value="${cuidado.descricao}"/></span>
                                    </c:if>
                                </td>
                                <td>
                                    <span class="badge badge-interval">
                                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>
                                        A cada ${cuidado.diasIntervalo} ${cuidado.diasIntervalo == 1 ? 'dia' : 'dias'}
                                    </span>
                                </td>
                                <td style="text-align: right;">
                                    <div class="links" style="justify-content: flex-end;">
                                        <a class="btn-icon" href="${pageContext.request.contextPath}/cuidados?acao=editar&id=${cuidado.id}" title="Editar cuidado">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/>
                                            </svg>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/cuidados" method="post" class="delete-form"
                                              data-nome="<c:out value='${cuidado.nome}'/>"
                                              onsubmit="return window.confirmarExclusao ? window.confirmarExclusao(event) : confirm('Deseja realmente excluir este registro?');">
                                            <input type="hidden" name="acao" value="excluir">
                                            <input type="hidden" name="id" value="${cuidado.id}">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <button type="submit" class="btn-icon btn-icon-danger" title="Excluir cuidado" aria-label="Excluir cuidado">
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
                            <path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"/>
                        </svg>
                    </div>
                    <h3>Nenhum cuidado cadastrado</h3>
                    <p>Defina rotinas como Rega, Borrifação de Folhas ou Adubação quinzenal para criar lembretes automáticos.</p>
                    <a class="btn" href="${pageContext.request.contextPath}/cuidados?acao=novo">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"/>
                            <line x1="5" y1="12" x2="19" y2="12"/>
                        </svg>
                        <span>Cadastrar Primeiro Cuidado</span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    </c:if>
</main>

</body>
</html>
