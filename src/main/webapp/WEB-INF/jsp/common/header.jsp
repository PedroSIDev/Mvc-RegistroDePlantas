<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="topbar">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/home">
            <span class="brand-icon">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M11 20A7 7 0 0 1 9.8 6.1C15.5 5 17 4.48 19 2c1 2 2 4.18 2 8 0 5.5-4.78 10-10 10Z"/>
                    <path d="M2 21c0-3 1.85-5.36 5.08-6C9.5 14.52 12 13 13 12"/>
                </svg>
            </span>
            <span>Plantas Domésticas</span>
            <span class="brand-tag">MVC</span>
        </a>

        <nav>
            <a class="nav-link ${param.active eq 'home' ? 'active' : ''}" href="${pageContext.request.contextPath}/home">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
                <span>Início</span>
            </a>
            <a class="nav-link ${param.active eq 'plantas' ? 'active' : ''}" href="${pageContext.request.contextPath}/plantas">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22v-7"/><path d="M17 8v.01"/><path d="M7 8v.01"/><path d="M12 3a9 9 0 0 0-9 9 9 9 0 0 0 6 8.5V15a3 3 0 0 1 6 0v5.5A9 9 0 0 0 21 12a9 9 0 0 0-9-9Z"/></svg>
                <span>Plantas</span>
            </a>
            <a class="nav-link ${param.active eq 'ambientes' ? 'active' : ''}" href="${pageContext.request.contextPath}/ambientes">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="7" height="7" x="3" y="3" rx="1"/><rect width="7" height="7" x="14" y="3" rx="1"/><rect width="7" height="7" x="14" y="14" rx="1"/><rect width="7" height="7" x="3" y="14" rx="1"/></svg>
                <span>Ambientes</span>
            </a>
            <a class="nav-link ${param.active eq 'cuidados' ? 'active' : ''}" href="${pageContext.request.contextPath}/cuidados">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"/></svg>
                <span>Cuidados</span>
            </a>
            <a class="nav-link ${param.active eq 'lembretes' ? 'active' : ''}" href="${pageContext.request.contextPath}/lembretes">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="18" height="18" x="3" y="4" rx="2"/><path d="M16 2v4"/><path d="M8 2v4"/><path d="M3 10h18"/><path d="m9 16 2 2 4-4"/></svg>
                <span>Lembretes</span>
            </a>

            <c:if test="${sessionScope.usuarioLogado.perfil != null and sessionScope.usuarioLogado.perfil.nome eq 'Administrador'}">
                <a class="nav-link ${param.active eq 'usuarios' ? 'active' : ''}" href="${pageContext.request.contextPath}/usuarios">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M22 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                    <span>Usuários</span>
                </a>
                <a class="nav-link ${param.active eq 'perfis' ? 'active' : ''}" href="${pageContext.request.contextPath}/perfis">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                    <span>Perfis</span>
                </a>
            </c:if>
        </nav>

        <div class="topbar-right">
            <c:if test="${not empty sessionScope.usuarioLogado}">
                <div class="user-chip" title="Conectado como <c:out value='${sessionScope.usuarioLogado.login}'/>">
                    <span class="user-chip-avatar">
                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                    </span>
                    <strong><c:out value="${sessionScope.usuarioLogado.nome}"/></strong>
                    <span class="badge-role"><c:out value="${sessionScope.usuarioLogado.perfil.nome}"/></span>
                </div>
            </c:if>
            <a class="btn-logout" href="${pageContext.request.contextPath}/logout" title="Sair do sistema">
                <svg width="17" height="17" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
            </a>
        </div>
    </div>
</header>

<jsp:include page="/WEB-INF/jsp/common/confirmacao.jsp"/>
