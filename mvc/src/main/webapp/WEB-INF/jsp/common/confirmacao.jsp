<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<dialog id="confirmar-exclusao" class="confirm-dialog" aria-labelledby="exclusao-titulo" aria-describedby="exclusao-descricao exclusao-vinculos">
    <div class="confirm-dialog-body">
        <span class="confirm-dialog-icon" aria-hidden="true">
            <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M10 10v8M14 10v8"/>
            </svg>
        </span>
        <h2 id="exclusao-titulo">Excluir registro?</h2>
        <p id="exclusao-descricao"></p>
        <p id="exclusao-vinculos" class="confirm-dialog-note">A exclusão é permanente. Se houver registros que dependem deste item, a ação será bloqueada.</p>
    </div>
    <div class="confirm-dialog-actions">
        <button type="button" class="btn btn-secondary" id="cancelar-exclusao" autofocus>Cancelar</button>
        <button type="button" class="btn btn-danger" id="executar-exclusao">Sim, excluir</button>
    </div>
</dialog>
<script src="${pageContext.request.contextPath}/js/acoes.js" defer></script>
