package br.com.mvc.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.com.mvc.model.Ambiente;
import br.com.mvc.model.Cuidado;
import br.com.mvc.model.Lembrete;
import br.com.mvc.model.Planta;

public class ModuloBusinessTest {

    @Test
    public void ambienteDeveValidarNomeObrigatorio() {
        AmbienteService service = new AmbienteService();
        Ambiente ambiente = new Ambiente();

        List<String> erros = service.validar(ambiente);

        assertFalse(erros.isEmpty());
        assertTrue(erros.contains("Nome do ambiente é obrigatório."));
    }

    @Test
    public void cuidadoDeveValidarIntervaloValido() {
        CuidadoService service = new CuidadoService();
        Cuidado cuidado = new Cuidado();
        cuidado.setNome("Rega");
        cuidado.setDiasIntervalo(0);

        List<String> erros = service.validar(cuidado);

        assertFalse(erros.isEmpty());
        assertTrue(erros.contains("Intervalo em dias deve ser maior que zero."));
    }

    @Test
    public void plantaDeveValidarDadosMinimos() {
        PlantaService service = new PlantaService();
        Planta planta = new Planta();
        planta.setNomePopular("" );
        planta.setUsuarioId(1L);
        planta.setAmbienteId(1L);

        List<String> erros = service.validar(planta);

        assertFalse(erros.isEmpty());
        assertTrue(erros.contains("Nome popular da planta é obrigatório."));
    }

    @Test
    public void lembreteDeveValidarDataAgendada() {
        LembreteService service = new LembreteService();
        Lembrete lembrete = new Lembrete();
        lembrete.setPlantaId(1L);
        lembrete.setCuidadoId(1L);
        lembrete.setStatus("PENDENTE");

        List<String> erros = service.validar(lembrete);

        assertFalse(erros.isEmpty());
        assertTrue(erros.contains("Data agendada é obrigatória."));
    }
}
