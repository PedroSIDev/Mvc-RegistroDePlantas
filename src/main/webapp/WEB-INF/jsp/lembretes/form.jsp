<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Lembrete - Registro de Plantas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
    <header class="topbar">
        <div class="container">
            <strong>Registro de Plantas</strong>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/lembretes">Lembretes</a>
            </nav>
        </div>
    </header>

    <main class="container">
        <div class="page-header">
            <h1>${empty lembrete or empty lembrete.id ? 'Novo lembrete' : 'Editar lembrete'}</h1>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/lembretes">Voltar</a>
        </div>

        <c:if test="${not empty erros}">
            <div class="alert alert-erro">
                <ul>
                    <c:forEach var="erro" items="${erros}">
                        <li>${erro}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/lembretes" method="post" class="card">
            <input type="hidden" name="id" value="${lembrete.id}">

            <div class="form-group">
                <label for="plantaId">Planta</label>
                <select id="plantaId" name="plantaId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="planta" items="${plantas}">
                        <option value="${planta.id}" ${lembrete.plantaId == planta.id ? 'selected' : ''}>${planta.nomePopular}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="cuidadoId">Cuidado</label>
                <select id="cuidadoId" name="cuidadoId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="cuidado" items="${cuidados}">
                        <option value="${cuidado.id}" ${lembrete.cuidadoId == cuidado.id ? 'selected' : ''}>${cuidado.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="dataAgendada">Data agendada</label>
                <input id="dataAgendada" name="dataAgendada" type="date" value="${lembrete.dataAgendada}" required>
            </div>

            <div class="form-group">
                <label for="dataRealizada">Data realizada</label>
                <input id="dataRealizada" name="dataRealizada" type="date" value="${lembrete.dataRealizada}">
            </div>

            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <option value="PENDENTE" ${lembrete.status == 'PENDENTE' ? 'selected' : ''}>PENDENTE</option>
                    <option value="CONCLUIDO" ${lembrete.status == 'CONCLUIDO' ? 'selected' : ''}>CONCLUIDO</option>
                    <option value="CANCELADO" ${lembrete.status == 'CANCELADO' ? 'selected' : ''}>CANCELADO</option>
                </select>
            </div>

            <div class="form-group">
                <label for="observacao">Observação</label>
                <textarea id="observacao" name="observacao" rows="3">${lembrete.observacao}</textarea>
            </div>

            <div class="actions">
                <button class="btn" type="submit">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/lembretes">Cancelar</a>
            </div>
        </form>
    </main>
</body>
</html>
