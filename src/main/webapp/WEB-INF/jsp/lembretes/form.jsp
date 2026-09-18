<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty lembrete or empty lembrete.id ? 'Novo Lembrete' : 'Editar Lembrete'} - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp">
    <jsp:param name="active" value="lembretes"/>
</jsp:include>

<main class="container">
    <div class="page-header">
        <div class="page-header-info">
            <h1>${empty lembrete or empty lembrete.id ? 'Novo Lembrete' : 'Editar Lembrete'}</h1>
            <p>Agende tarefas de cuidados para espécies específicas e acompanhe a conclusão</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/lembretes">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"/>
                <polyline points="12 19 5 12 12 5"/>
            </svg>
            <span>Voltar</span>
        </a>
    </div>

    <jsp:include page="/WEB-INF/jsp/common/mensagens.jsp"/>

    <form action="${pageContext.request.contextPath}/lembretes" method="post" class="card">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
        <input type="hidden" name="id" value="${lembrete.id}">

        <div class="form-grid">
            <div class="form-group">
                <label for="plantaId">Planta <span class="required">*</span></label>
                <select id="plantaId" name="plantaId" required>
                    <option value="">Selecione a planta</option>
                    <c:forEach var="planta" items="${plantas}">
                        <option value="${planta.id}" ${lembrete.plantaId == planta.id ? 'selected' : ''}>
                            <c:out value="${planta.nomePopular}"/>
                            <c:if test="${not empty planta.ambiente}"> &bull; <c:out value="${planta.ambiente.nome}"/></c:if>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="cuidadoId">Tipo de Cuidado <span class="required">*</span></label>
                <select id="cuidadoId" name="cuidadoId" required>
                    <option value="">Selecione o cuidado</option>
                    <c:forEach var="cuidado" items="${cuidados}">
                        <option value="${cuidado.id}" ${lembrete.cuidadoId == cuidado.id ? 'selected' : ''}>
                            <c:out value="${cuidado.nome}"/> (${cuidado.diasIntervalo}d)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="dataAgendada">Data Agendada <span class="required">*</span></label>
                <input id="dataAgendada" name="dataAgendada" type="date" min="1000-01-01" max="9999-12-31" value="<c:out value='${lembrete.dataAgendada}'/>" required>
            </div>

            <div class="form-group">
                <label for="dataRealizada">Data de Realização (obrigatória ao concluir)</label>
                <input id="dataRealizada" name="dataRealizada" type="date" min="1000-01-01" max="9999-12-31" value="<c:out value='${lembrete.dataRealizada}'/>">
                <span class="form-hint">Preencha ao concluir. Para deixar pendente ou cancelar, mantenha esta data vazia.</span>
            </div>

            <div class="form-group">
                <label for="status">Status da Tarefa <span class="required">*</span></label>
                <select id="status" name="status" required>
                    <option value="PENDENTE" ${lembrete.status == 'PENDENTE' ? 'selected' : ''}>PENDENTE</option>
                    <option value="CONCLUIDO" ${lembrete.status == 'CONCLUIDO' ? 'selected' : ''}>CONCLUÍDO</option>
                    <option value="CANCELADO" ${lembrete.status == 'CANCELADO' ? 'selected' : ''}>CANCELADO</option>
                </select>
            </div>

            <div class="form-group full-width">
                <label for="observacao">Observações Adicionais</label>
                <textarea id="observacao" name="observacao" maxlength="255" placeholder="Notas sobre a execução, dosagem de adubo utilizada ou estado da planta..."><c:out value="${lembrete.observacao}"/></textarea>
            </div>
        </div>

        <div class="actions">
            <button class="btn" type="submit">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="20 6 9 17 4 12"/>
                </svg>
                <span>Salvar Lembrete</span>
            </button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/lembretes">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>
