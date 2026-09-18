<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuários - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="usuarios"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>Usuários</h1>
            <p>Gerenciamento de contas de acesso, perfis e permissões do sistema</p>
        </div>
        <a class="btn" href="${pageContext.request.contextPath}/usuarios?acao=novo">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"/>
                <line x1="5" y1="12" x2="19" y2="12"/>
            </svg>
            <span>Novo Usuário</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <c:if test="${not listaFalhou}">
    <div class="table-wrap">
        <c:choose>
            <c:when test="${not empty usuarios}">
                <table>
                    <thead>
                        <tr>
                            <th style="width: 60px;">ID</th>
                            <th>Nome do Usuário</th>
                            <th>Login</th>
                            <th>Perfil de Acesso</th>
                            <th style="width: 110px; text-align: right;">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td class="td-id">#${usuario.id}</td>
                                <td>
                                    <div class="td-main"><c:out value="${usuario.nome}"/></div>
                                    <c:if test="${sessionScope.usuarioLogado.id == usuario.id}">
                                        <span class="td-sub" style="color: var(--primary); font-weight: 500;">(Você atualmente)</span>
                                    </c:if>
                                </td>
                                <td>
                                    <span style="font-family: ui-monospace, SFMono-Regular, monospace; font-size: 0.88rem; color: var(--text-secondary);">
                                        <c:out value="${usuario.login}"/>
                                    </span>
                                </td>
                                <td>
                                    <span class="badge-role">
                                        <c:out value="${usuario.perfil.nome}"/>
                                    </span>
                                </td>
                                <td style="text-align: right;">
                                    <div class="links" style="justify-content: flex-end;">
                                        <a class="btn-icon" href="${pageContext.request.contextPath}/usuarios?acao=editar&id=${usuario.id}" title="Editar usuário">
                                            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/>
                                            </svg>
                                        </a>
                                        <c:choose>
                                            <c:when test="${sessionScope.usuarioLogado.id != usuario.id}">
                                                <form action="${pageContext.request.contextPath}/usuarios" method="post" class="delete-form"
                                              data-nome="<c:out value='${usuario.nome}'/>"
                                              onsubmit="return window.confirmarExclusao ? window.confirmarExclusao(event) : confirm('Deseja realmente excluir este registro?');">
                                            <input type="hidden" name="acao" value="excluir">
                                            <input type="hidden" name="id" value="${usuario.id}">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <button type="submit" class="btn-icon btn-icon-danger" title="Excluir usuário" aria-label="Excluir usuário">
                                                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path d="M3 6h18"/>
                                                        <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/>
                                                        <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/>
                                                    </svg>
                                                </button>
                                        </form>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="btn-icon" style="opacity: 0.3; cursor: not-allowed;" title="Você não pode excluir seu próprio usuário conectado">
                                                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <circle cx="12" cy="12" r="10"/><line x1="4.93" y1="4.93" x2="19.07" y2="19.07"/>
                                                    </svg>
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
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
                            <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                        </svg>
                    </div>
                    <h3>Nenhum usuário cadastrado</h3>
                    <p>Cadastre os membros da equipe ou família com perfis específicos de acesso.</p>
                    <a class="btn" href="${pageContext.request.contextPath}/usuarios?acao=novo">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"/>
                            <line x1="5" y1="12" x2="19" y2="12"/>
                        </svg>
                        <span>Cadastrar Primeiro Usuário</span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    </c:if>
</main>

</body>
</html>
