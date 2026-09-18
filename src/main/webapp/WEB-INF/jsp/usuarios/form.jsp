<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty usuario or empty usuario.id ? 'Novo Usuário' : 'Editar Usuário'} - Plantas Domésticas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="usuarios"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty usuario or empty usuario.id ? 'Novo Usuário' : 'Editar Usuário'}</h1>
            <p>Cadastre ou atualize as credenciais e o nível de acesso ao sistema</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/usuarios">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            <span>Voltar</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <form action="${pageContext.request.contextPath}/usuarios" method="post" class="card">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <input type="hidden" name="id" value="${usuario.id}">

        <div class="form-grid">
            <div class="form-group">
                <label for="nome">Nome Completo <span class="required">*</span></label>
                <input id="nome" name="nome" maxlength="150" type="text" value="<c:out value='${usuario.nome}'/>" placeholder="Ex: Pedro Silva" required>
            </div>

            <div class="form-group">
                <label for="login">Login de Acesso <span class="required">*</span></label>
                <input id="login" name="login" maxlength="100" type="text" value="<c:out value='${usuario.login}'/>" placeholder="Ex: pedrosilva" required>
            </div>

            <div class="form-group">
                <label for="senha">
                    Senha de Acesso
                    <c:choose>
                        <c:when test="${empty usuario.id}">
                            <span class="required">*</span>
                        </c:when>
                        <c:otherwise>
                            <span style="font-weight: normal; color: var(--text-muted);">(Opcional)</span>
                        </c:otherwise>
                    </c:choose>
                </label>
                <input id="senha" name="senha" type="password" autocomplete="new-password" placeholder="${not empty usuario.id ? '•••••••• (deixe vazio para manter)' : 'Mínimo de 6 caracteres'}" ${empty usuario.id ? 'required minlength="6"' : 'minlength="6"'}>
                <span class="form-hint">${not empty usuario.id ? 'Preencha apenas se desejar trocar a senha atual (mínimo de 6 caracteres).' : 'Crie uma senha segura de pelo menos 6 caracteres.'}</span>
            </div>

            <div class="form-group">
                <label for="perfilId">Perfil de Permissões <span class="required">*</span></label>
                <select id="perfilId" name="perfilId" required>
                    <option value="">Selecione o perfil</option>
                    <c:forEach var="perfil" items="${perfis}">
                        <option value="${perfil.id}" ${usuario.perfilId == perfil.id ? 'selected' : ''}>
                            <c:out value="${perfil.nome}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Usuário</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/usuarios">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
