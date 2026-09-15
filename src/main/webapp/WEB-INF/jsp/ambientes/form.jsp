<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty ambiente or empty ambiente.id ? 'Novo Ambiente' : 'Editar Ambiente'} - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="ambientes"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty ambiente or empty ambiente.id ? 'Novo Ambiente' : 'Editar Ambiente'}</h1>
            <p>Defina o nome e detalhes do cômodo ou espaço de cultivo da sua casa</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ambientes">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            <span>Voltar</span>
        </a>
    </div>

    <c:if test="${not empty erros}">
        <div class="alert alert-erro">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="8" x2="12" y2="12"/>
                <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            <div>
                <strong>Por favor, verifique os campos abaixo:</strong>
                <ul>
                    <c:forEach var="erro" items="${erros}">
                        <li><c:out value="${erro}"/></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/ambientes" method="post" class="card">
        <input type="hidden" name="id" value="${ambiente.id}">

        <div class="form-grid">
            <div class="form-group full-width">
                <label for="nome">Nome do Ambiente <span class="required">*</span></label>
                <input id="nome" name="nome" type="text" value="<c:out value='${ambiente.nome}'/>" placeholder="Ex: Sala de Estar, Varanda Gourmet, Escritório" required>
            </div>

            <div class="form-group full-width">
                <label for="descricao">Descrição / Luminosidade</label>
                <textarea id="descricao" name="descricao" placeholder="Ex: Sol direto pela manhã, ventilação constante, espaço ideal para folhagens tropicais..."><c:out value="${ambiente.descricao}"/></textarea>
                <span class="form-hint">Descreva condições como iluminação natural, temperatura e umidade média do local.</span>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Ambiente</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/ambientes">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
