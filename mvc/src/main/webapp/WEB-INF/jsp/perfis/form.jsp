<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty perfil or empty perfil.id ? 'Novo Perfil' : 'Editar Perfil'} - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="perfis"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty perfil or empty perfil.id ? 'Novo Perfil' : 'Editar Perfil'}</h1>
            <p>Defina a identificação do perfil de acesso aos recursos da aplicação</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/perfis">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            <span>Voltar</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <form action="${pageContext.request.contextPath}/perfis" method="post" class="card">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <input type="hidden" name="id" value="${perfil.id}">

        <div class="form-grid">
            <div class="form-group full-width">
                <label for="nome">Nome do Perfil <span class="required">*</span></label>
                <input id="nome" name="nome" maxlength="100" type="text" value="<c:out value='${perfil.nome}'/>" placeholder="Ex: Administrador, Operador, Usuário" required>
                <span class="form-hint">Dica: O perfil 'Administrador' concede acesso às abas de gerenciamento de Usuários e Perfis.</span>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Perfil</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/perfis">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
