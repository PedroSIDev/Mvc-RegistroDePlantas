<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início - Sistema de Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="home"/>
</jsp:include>

<main class="container">
    <div class="dashboard-hero">
        <h1 class="dashboard-hero-title">Bem-vindo, <c:out value="${sessionScope.usuarioLogado.nome}"/></h1>
        <p class="dashboard-hero-subtitle">Gerencie o cultivo, espaços e rotinas de manutenção da sua coleção botânica</p>
    </div>

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

    <div class="grid-cards">
        <a class="menu-card" href="${pageContext.request.contextPath}/plantas">
            <div class="menu-card-top">
                <div class="menu-card-icon">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M12 22v-7"/>
                        <path d="M17 8v.01"/>
                        <path d="M7 8v.01"/>
                        <path d="M12 3a9 9 0 0 0-9 9 9 9 0 0 0 6 8.5V15a3 3 0 0 1 6 0v5.5A9 9 0 0 0 21 12a9 9 0 0 0-9-9Z"/>
                    </svg>
                </div>
                <div class="menu-card-arrow">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="5" y1="12" x2="19" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </div>
            </div>
            <strong>Plantas</strong>
            <span>Catálogo das espécies cadastradas, datas de aquisição e ambientes associados.</span>
        </a>

        <a class="menu-card" href="${pageContext.request.contextPath}/ambientes">
            <div class="menu-card-top">
                <div class="menu-card-icon">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                        <rect width="7" height="7" x="3" y="3" rx="1"/>
                        <rect width="7" height="7" x="14" y="3" rx="1"/>
                        <rect width="7" height="7" x="14" y="14" rx="1"/>
                        <rect width="7" height="7" x="3" y="14" rx="1"/>
                    </svg>
                </div>
                <div class="menu-card-arrow">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="5" y1="12" x2="19" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </div>
            </div>
            <strong>Ambientes</strong>
            <span>Locais da residência (salas, varandas, jardins) e condições de luminosidade.</span>
        </a>

        <a class="menu-card" href="${pageContext.request.contextPath}/cuidados">
            <div class="menu-card-top">
                <div class="menu-card-icon">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"/>
                    </svg>
                </div>
                <div class="menu-card-arrow">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="5" y1="12" x2="19" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </div>
            </div>
            <strong>Cuidados</strong>
            <span>Parâmetros de rega, adubação, poda, banho de sol e periodicidade recomendada.</span>
        </a>

        <a class="menu-card" href="${pageContext.request.contextPath}/lembretes">
            <div class="menu-card-top">
                <div class="menu-card-icon">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                        <rect width="18" height="18" x="3" y="4" rx="2"/>
                        <path d="M16 2v4"/>
                        <path d="M8 2v4"/>
                        <path d="M3 10h18"/>
                        <path d="m9 16 2 2 4-4"/>
                    </svg>
                </div>
                <div class="menu-card-arrow">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="5" y1="12" x2="19" y2="12"/>
                        <polyline points="12 5 19 12 12 19"/>
                    </svg>
                </div>
            </div>
            <strong>Lembretes</strong>
            <span>Agenda de tarefas botânicas, manutenções pendentes e histórico de cuidados concluídos.</span>
        </a>

        <c:if test="${sessionScope.usuarioLogado.perfil != null and sessionScope.usuarioLogado.perfil.nome eq 'Administrador'}">
            <a class="menu-card" href="${pageContext.request.contextPath}/usuarios">
                <div class="menu-card-top">
                    <div class="menu-card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                            <path d="M22 21v-2a4 4 0 0 0-3-3.87"/>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                        </svg>
                    </div>
                    <div class="menu-card-arrow">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="5" y1="12" x2="19" y2="12"/>
                            <polyline points="12 5 19 12 12 19"/>
                        </svg>
                    </div>
                </div>
                <strong>Usuários</strong>
                <span>Administração de contas, credenciais com BCrypt e perfis de jardineiros.</span>
            </a>

            <a class="menu-card" href="${pageContext.request.contextPath}/perfis">
                <div class="menu-card-top">
                    <div class="menu-card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                        </svg>
                    </div>
                    <div class="menu-card-arrow">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="5" y1="12" x2="19" y2="12"/>
                            <polyline points="12 5 19 12 12 19"/>
                        </svg>
                    </div>
                </div>
                <strong>Perfis de Acesso</strong>
                <span>Definição de papéis e autorização no sistema (Administrador, Jardineiro).</span>
            </a>
        </c:if>
    </div>
</main>

</body>
</html>
