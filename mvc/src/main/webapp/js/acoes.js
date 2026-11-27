(() => {
    "use strict";
    const dialog = document.getElementById("confirmar-exclusao");
    let pendingForm = null;
    let pendingButton = null;

    window.confirmarExclusao = (event) => {
        const form = event.target;
        if (form.dataset.confirmed === "true") {
            delete form.dataset.confirmed;
            return true;
        }
        if (!dialog || typeof dialog.showModal !== "function") {
            return window.confirm("Deseja realmente excluir este registro? Esta ação é permanente.");
        }
        pendingForm = form;
        pendingButton = event.submitter;
        document.getElementById("exclusao-descricao").textContent =
            'Você está prestes a excluir "' + (form.dataset.nome || "este registro") + '".';
        dialog.showModal();
        return false;
    };

    if (dialog) {
        document.getElementById("cancelar-exclusao").addEventListener("click", () => dialog.close());
        dialog.addEventListener("close", () => {
            pendingForm = null;
            pendingButton = null;
        });
        document.getElementById("executar-exclusao").addEventListener("click", () => {
            const form = pendingForm;
            const button = pendingButton;
            dialog.close();
            if (form) {
                form.dataset.confirmed = "true";
                form.requestSubmit(button || undefined);
            }
        });
    }

    document.addEventListener("click", (event) => {
        const close = event.target.closest("[data-dismiss-alert]");
        if (close) {
            const alert = close.closest(".feedback");
            const next = document.querySelector("main .btn, main input, main select");
            alert.remove();
            if (next) next.focus();
        }
    });

    document.addEventListener("submit", (event) => {
        if (event.defaultPrevented || !event.target.matches(".delete-form, form.card")) return;
        const form = event.target;
        if (form.dataset.submitting === "true") {
            event.preventDefault();
            return;
        }
        form.dataset.submitting = "true";
        const button = event.submitter;
        if (button) {
            button.disabled = true;
            button.setAttribute("aria-busy", "true");
            button.classList.add("is-loading");
        }
    });

    window.addEventListener("pageshow", () => {
        document.querySelectorAll("[data-submitting]").forEach((form) => {
            delete form.dataset.submitting;
            form.querySelectorAll(".is-loading").forEach((button) => {
                button.disabled = false;
                button.removeAttribute("aria-busy");
                button.classList.remove("is-loading");
            });
        });
    });

    const summary = document.querySelector("[data-validation-summary]");
    if (summary) summary.focus();

    const status = document.getElementById("status");
    const realizada = document.getElementById("dataRealizada");
    if (status && realizada) {
        const updateRequired = () => {
            realizada.required = status.value === "CONCLUIDO";
            realizada.setAttribute("aria-required", String(realizada.required));
        };
        status.addEventListener("change", updateRequired);
        updateRequired();
    }
})();
