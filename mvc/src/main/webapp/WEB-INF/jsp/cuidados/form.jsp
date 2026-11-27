<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty cuidado or empty cuidado.id ? 'Novo Cuidado' : 'Editar Cuidado'} - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="cuidados"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty cuidado or empty cuidado.id ? 'Novo Cuidado' : 'Editar Cuidado'}</h1>
            <p>Configure tipos de manejo, instruções e a frequência de repetição recomendada</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/cuidados">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            <span>Voltar</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <form action="${pageContext.request.contextPath}/cuidados" method="post" class="card">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <input type="hidden" name="id" value="${cuidado.id}">

        <div class="form-grid">
            <div class="form-group">
                <label for="nome">Nome do Cuidado <span class="required">*</span></label>
                <input id="nome" name="nome" maxlength="100" type="text" value="<c:out value='${cuidado.nome}'/>" placeholder="Ex: Rega moderada, Adubação NPK 10-10-10, Poda de limpeza" required>
            </div>

            <div class="form-group">
                <label for="diasIntervalo">Intervalo de Repetição (em dias) <span class="required">*</span></label>
                <input id="diasIntervalo" name="diasIntervalo" type="number" min="1" value="${cuidado.diasIntervalo}" placeholder="Ex: 7" required>
                <span class="form-hint">De quantos em quantos dias este cuidado deve ser repetido (ex: 7 para semanal).</span>
            </div>

            <div class="form-group full-width">
                <label for="descricao">Instruções / Detalhes de Aplicação</label>
                <textarea id="descricao" name="descricao" placeholder="Instruções sobre quantidade de água, diluição do adubo, horário ideal do dia ou ferramentas necessárias..."><c:out value="${cuidado.descricao}"/></textarea>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Cuidado</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/cuidados">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
