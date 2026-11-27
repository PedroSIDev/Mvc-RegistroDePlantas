<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${not empty sessionScope.mensagemSucesso}">
    <div class="alert alert-sucesso feedback" role="status" aria-live="polite">
        <span class="feedback-icon" aria-hidden="true">✓</span>
        <div class="feedback-content">
            <strong>Tudo certo!</strong>
            <p><c:out value="${sessionScope.mensagemSucesso}"/></p>
        </div>
        <button type="button" class="feedback-close" aria-label="Fechar aviso de sucesso" data-dismiss-alert>×</button>
    </div>
    <c:remove var="mensagemSucesso" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.mensagemErro or not empty requestScope.mensagemErro}">
    <div class="alert alert-erro feedback" role="alert">
        <span class="feedback-icon" aria-hidden="true">!</span>
        <div class="feedback-content">
            <strong>Não foi possível concluir</strong>
            <c:if test="${not empty sessionScope.mensagemErro}">
                <p><c:out value="${sessionScope.mensagemErro}"/></p>
            </c:if>
            <c:if test="${not empty requestScope.mensagemErro}">
                <p><c:out value="${requestScope.mensagemErro}"/></p>
            </c:if>
            <c:if test="${listaFalhou}">
                <p><a href="" class="feedback-retry">Tentar carregar novamente</a></p>
            </c:if>
        </div>
        <button type="button" class="feedback-close" aria-label="Fechar aviso de erro" data-dismiss-alert>×</button>
    </div>
    <c:remove var="mensagemErro" scope="session"/>
</c:if>
<c:if test="${not empty erros}">
    <div class="alert alert-erro feedback" role="alert" tabindex="-1" data-validation-summary>
        <span class="feedback-icon" aria-hidden="true">!</span>
        <div class="feedback-content">
            <strong>Não foi possível salvar</strong>
            <p>Revise os itens abaixo e tente novamente.</p>
            <ul>
                <c:forEach var="erro" items="${erros}">
                    <li><c:out value="${erro}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>
