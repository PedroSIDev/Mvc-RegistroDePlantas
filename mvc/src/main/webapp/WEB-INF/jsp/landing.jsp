<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plantas Domésticas - Sistema de Gestão Botânica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body class="landing-body">

<header class="landing-header">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/">
            <span class="brand-icon">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M11 20A7 7 0 0 1 9.8 6.1C15.5 5 17 4.48 19 2c1 2 2 4.18 2 8 0 5.5-4.78 10-10 10Z"/>
                    <path d="M2 21c0-3 1.85-5.36 5.08-6C9.5 14.52 12 13 13 12"/>
                </svg>
            </span>
            <span>Plantas Domésticas</span>
            <span class="brand-tag">MVC</span>
        </a>

        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <a class="btn" href="${pageContext.request.contextPath}/home">
                        <span>Acessar Painel</span>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/></svg>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="btn" href="${pageContext.request.contextPath}/login">
                        <span>Entrar no Sistema</span>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/></svg>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<main class="container">
    <section class="landing-hero">
        <div class="landing-badge">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"/>
            </svg>
            <span>Gestão Botânica Inteligente</span>
        </div>

        <h1 class="landing-title">
            Cultive harmonia e cuide das suas <em>plantas com perfeição</em>
        </h1>

        <p class="landing-desc">
            Organize sua coleção botânica, planeje rotinas de rega e adubação, mapeie os cômodos da residência e nunca mais perca um dia de cuidado.
        </p>

        <div class="landing-actions">
            <c:choose>
                <c:when test="${not empty sessionScope.usuarioLogado}">
                    <a class="btn" href="${pageContext.request.contextPath}/home">
                        <span>Ir para o Dashboard</span>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="9 18 15 12 9 6"/></svg>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="btn" href="${pageContext.request.contextPath}/login">
                        <span>Acessar Painel</span>
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="9 18 15 12 9 6"/></svg>
                    </a>
                </c:otherwise>
            </c:choose>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/login">
                <span>Ver Demonstração</span>
            </a>
        </div>

        <div class="landing-preview">
            <div class="landing-stats-grid">
                <div class="stat-item">
                    <div class="stat-icon">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 22v-7"/><path d="M17 8v.01"/><path d="M7 8v.01"/>
                            <path d="M12 3a9 9 0 0 0-9 9 9 9 0 0 0 6 8.5V15a3 3 0 0 1 6 0v5.5A9 9 0 0 0 21 12a9 9 0 0 0-9-9Z"/>
                        </svg>
                    </div>
                    <div class="stat-title">Catálogo Completo</div>
                    <p class="stat-text">Cadastre nomes populares e científicos, datas de aquisição e anotações específicas de luminosidade.</p>
                </div>

                <div class="stat-item">
                    <div class="stat-icon">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect width="7" height="7" x="3" y="3" rx="1"/><rect width="7" height="7" x="14" y="3" rx="1"/>
                            <rect width="7" height="7" x="14" y="14" rx="1"/><rect width="7" height="7" x="3" y="14" rx="1"/>
                        </svg>
                    </div>
                    <div class="stat-title">Ambientes Mapeados</div>
                    <p class="stat-text">Distribua suas espécies pela sala, varanda ou jardim para acompanhar microclimas ideais.</p>
                </div>

                <div class="stat-item">
                    <div class="stat-icon">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"/>
                        </svg>
                    </div>
                    <div class="stat-title">Rotinas de Cuidados</div>
                    <p class="stat-text">Estabeleça intervalos ideais de rega, poda, adubação e fertilização para cada tipo de folhagem.</p>
                </div>

                <div class="stat-item">
                    <div class="stat-icon">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect width="18" height="18" x="3" y="4" rx="2"/><path d="M16 2v4"/><path d="M8 2v4"/>
                            <path d="M3 10h18"/><path d="m9 16 2 2 4-4"/>
                        </svg>
                    </div>
                    <div class="stat-title">Lembretes & Tarefas</div>
                    <p class="stat-text">Histórico e agendamento de tarefas para garantir que nenhuma planta fique desidratada.</p>
                </div>
            </div>
        </div>
    </section>
</main>

<footer class="landing-footer">
    <div class="container">
        Desenvolvido com Arquitetura MVC &bull; Jakarta Servlet &bull; JSP &bull; MySQL &bull; Design Botânico Impecável
    </div>
</footer>

</body>
</html>
