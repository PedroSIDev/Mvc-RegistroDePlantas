<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lembretes - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="lembretes"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>Lembretes & Rotinas</h1>
            <p>Acompanhe o cronograma de regas, podas e manutenções agendadas</p>
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/lembretes?acao=novo">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            <span>Novo Lembrete</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <c:if test="${not listaFalhou}">
    <div class="table-wrap">
        <c:choose>
            <c:when test="${not empty lembretes}">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 60px;">ID</th>
                            <th>Planta</th>
                            <th>Cuidado</th>
                            <th>Data Agendada</th>
                            <th>Status</th>
                            <th style="width: 110px; text-align: right;">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="lembrete" items="${lembretes}">
                            <tr>
                                <td class="td-id">#${lembrete.id}</td>
                                <td>
                                    <div class="td-main"><c:out value="${lembrete.planta.nomePopular}"/></div>
                                    <c:if test="${not empty lembrete.planta.ambiente}">
                                        <span class="td-sub"><c:out value="${lembrete.planta.ambiente.nome}"/></span>
                                    </c:if>
                                </td>
                                <td>
                                    <span style="font-weight: 500;"><c:out value="${lembrete.cuidado.nome}"/></span>
                                    <c:if test="${not empty lembrete.observacao}">
                                        <span class="td-sub"><c:out value="${lembrete.observacao}"/></span>
                                    </c:if>
                                </td>
                                <td>
                                    <span style="font-family: ui-monospace, SFMono-Regular, monospace; font-size: 0.88rem;">
                                        <c:out value="${lembrete.dataAgendada}"/>
                                    </span>
                                    <c:if test="${not empty lembrete.dataRealizada}">
                                        <span class="td-sub" style="font-size: 0.78rem;">Feito em: <c:out value="${lembrete.dataRealizada}"/></span>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${lembrete.status == 'CONCLUIDO'}">
                                            <span class="badge status-concluido">
                                                <span class="badge-dot"></span>
                                                Concluído
                                            </span>
                                        </c:when>
                                        <c:when test="${lembrete.status == 'CANCELADO'}">
                                            <span class="badge status-cancelado">
                                                <span class="badge-dot"></span>
                                                Cancelado
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge status-pendente">
                                                <span class="badge-dot"></span>
                                                Pendente
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="text-align: right;">
                                    <div class="links" style="justify-content: flex-end;">
                                        <a class="btn-icon" href="${pageContext.request.contextPath}/lembretes?acao=editar&id=${lembrete.id}" title="Editar lembrete">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/>
                                            </svg>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/lembretes" method="post" class="delete-form"
                                              data-nome="<c:out value='${lembrete.planta.nomePopular}'/>"
                                              onsubmit="return window.confirmarExclusao ? window.confirmarExclusao(event) : confirm('Deseja realmente excluir este registro?');">
                                            <input type="hidden" name="acao" value="excluir">
                                            <input type="hidden" name="id" value="${lembrete.id}">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <button type="submit" class="btn-icon btn-icon-danger" title="Excluir lembrete" aria-label="Excluir lembrete">
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
                            <rect width="18" height="18" x="3" y="4" rx="2"/><path d="M16 2v4"/><path d="M8 2v4"/><path d="M3 10h18"/><path d="m9 16 2 2 4-4"/>
                        </svg>
                    </div>
                    <h3>Nenhum lembrete registrado</h3>
                    <p>Agende tarefas de rega, poda ou adubação para manter suas plantas sempre saudáveis e hidratadas.</p>
                    <a class="btn" href="${pageContext.request.contextPath}/lembretes?acao=novo">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"/>
                            <line x1="5" y1="12" x2="19" y2="12"/>
                        </svg>
                        <span>Agendar Primeiro Lembrete</span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    </c:if>
</main>

</body>
</html>
