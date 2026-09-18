package br.com.mvc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.util.Locale;

import br.com.mvc.dao.CuidadoDAO;
import br.com.mvc.dao.LembreteDAO;
import br.com.mvc.dao.PlantaDAO;
import br.com.mvc.model.Lembrete;

public class LembreteService {

    private final LembreteDAO lembreteDAO;
    private final PlantaDAO plantaDAO;
    private final CuidadoDAO cuidadoDAO;

    public LembreteService() {
        this.lembreteDAO = new LembreteDAO();
        this.plantaDAO = new PlantaDAO();
        this.cuidadoDAO = new CuidadoDAO();
    }

    public List<String> validar(Lembrete lembrete) {
        List<String> erros = new ArrayList<>();

        if (lembrete == null) {
            erros.add("Lembrete inválido.");
            return erros;
        }

        lembrete.setDataAgendada(Validacao.opcional(lembrete.getDataAgendada()));
        lembrete.setDataRealizada(Validacao.opcional(lembrete.getDataRealizada()));
        lembrete.setObservacao(Validacao.opcional(lembrete.getObservacao()));
        Validacao.tamanho(erros, lembrete.getObservacao(), 255, "Observação");
        String status = Validacao.opcional(lembrete.getStatus());
        lembrete.setStatus(status == null ? "PENDENTE" : status.toUpperCase(Locale.ROOT));
        Validacao.data(erros, lembrete.getDataAgendada(), "Data agendada");
        LocalDate realizada = Validacao.data(erros, lembrete.getDataRealizada(), "Data de realização");
        if ("CONCLUIDO".equals(lembrete.getStatus()) && lembrete.getDataRealizada() == null) {
            erros.add("Informe a data de realização para concluir o lembrete.");
        }
        if (!"CONCLUIDO".equals(lembrete.getStatus()) && lembrete.getDataRealizada() != null) {
            erros.add("A data de realização deve ser preenchida apenas para lembretes concluídos.");
        }
        if (realizada != null && realizada.isAfter(LocalDate.now())) {
            erros.add("A data de realização não pode estar no futuro.");
        }

        if (lembrete.getPlantaId() == null) {
            erros.add("Planta é obrigatória.");
        } else if (plantaDAO.buscarPorId(lembrete.getPlantaId()) == null) {
            erros.add("Planta informada não existe.");
        }

        if (lembrete.getCuidadoId() == null) {
            erros.add("Cuidado é obrigatório.");
        } else if (cuidadoDAO.buscarPorId(lembrete.getCuidadoId()) == null) {
            erros.add("Cuidado informado não existe.");
        }

        if (lembrete.getDataAgendada() == null || lembrete.getDataAgendada().trim().isEmpty()) {
            erros.add("Data agendada é obrigatória.");
        }

        if (lembrete.getStatus() != null && !lembrete.getStatus().trim().isEmpty()) {
            List<String> statusPermitidos = Arrays.asList("PENDENTE", "CONCLUIDO", "CANCELADO");
            if (!statusPermitidos.contains(lembrete.getStatus().trim().toUpperCase())) {
                erros.add("Status inválido. Use PENDENTE, CONCLUIDO ou CANCELADO.");
            }
        }

        return erros;
    }

    public List<Lembrete> listarTodos() {
        return lembreteDAO.listarTodos();
    }

    public Lembrete buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return lembreteDAO.buscarPorId(id);
    }

    public void inserir(Lembrete lembrete) {
        Validacao.exigir(validar(lembrete));
        if (lembrete.getStatus() == null || lembrete.getStatus().trim().isEmpty()) {
            lembrete.setStatus("PENDENTE");
        }
        lembreteDAO.inserir(lembrete);
    }

    public void alterar(Lembrete lembrete) {
        Validacao.exigir(validar(lembrete));
        if (buscarPorId(lembrete.getId()) == null) {
            throw new IllegalArgumentException("Lembrete não encontrado. Atualize a lista e tente novamente.");
        }
        if (lembrete.getStatus() == null || lembrete.getStatus().trim().isEmpty()) {
            lembrete.setStatus("PENDENTE");
        }
        lembreteDAO.alterar(lembrete);
    }

    public void excluir(Long id) {
        lembreteDAO.deletar(id);
    }
}
