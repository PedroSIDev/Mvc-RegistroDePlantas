<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty planta or empty planta.id ? 'Nova Planta' : 'Editar Planta'} - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="plantas"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty planta or empty planta.id ? 'Nova Planta' : 'Editar Planta'}</h1>
            <p>Preencha os detalhes botânicos, ambiente de cultivo e responsável</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/plantas">
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
                <strong>Por favor, corrija os seguintes campos:</strong>
                <ul>
                    <c:forEach var="erro" items="${erros}">
                        <li><c:out value="${erro}"/></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/plantas" method="post" class="card">
        <input type="hidden" name="id" value="${planta.id}">

        <div class="form-grid">
            <div class="form-group">
                <label for="nomePopular">Nome popular <span class="required">*</span></label>
                <input id="nomePopular" name="nomePopular" type="text" value="<c:out value='${planta.nomePopular}'/>" placeholder="Ex: Costela-de-Adão" required>
            </div>

            <div class="form-group">
                <label for="nomeCientifico">Nome científico</label>
                <input id="nomeCientifico" name="nomeCientifico" type="text" value="<c:out value='${planta.nomeCientifico}'/>" placeholder="Ex: Monstera deliciosa">
            </div>

            <div class="form-group">
                <label for="ambienteId">Ambiente da casa <span class="required">*</span></label>
                <select id="ambienteId" name="ambienteId" required>
                    <option value="">Selecione um ambiente</option>
                    <c:forEach var="ambiente" items="${ambientes}">
                        <option value="${ambiente.id}" ${planta.ambienteId == ambiente.id ? 'selected' : ''}>
                            <c:out value="${ambiente.nome}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="usuarioId">Usuário responsável <span class="required">*</span></label>
                <select id="usuarioId" name="usuarioId" required>
                    <option value="">Selecione o responsável</option>
                    <c:forEach var="usuario" items="${usuarios}">
                        <option value="${usuario.id}" ${planta.usuarioId == usuario.id ? 'selected' : ''}>
                            <c:out value="${usuario.nome}"/> (<c:out value="${usuario.login}"/>)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="dataAquisicao">Data de aquisição</label>
                <input id="dataAquisicao" name="dataAquisicao" type="date" value="${planta.dataAquisicao}">
            </div>

            <div class="form-group full-width">
                <label for="observacoes">Observações de cultivo</label>
                <textarea id="observacoes" name="observacoes" placeholder="Informações adicionais sobre luminosidade, substrato ou necessidades específicas..."><c:out value="${planta.observacoes}"/></textarea>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Planta</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/plantas">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
