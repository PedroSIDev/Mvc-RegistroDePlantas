package br.com.mvc.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.mvc.model.Perfil;

public class PerfilServiceTest {

    @Test
    public void perfilDeveValidarNomeObrigatorio() {
        PerfilService service = new PerfilService();
        Perfil perfil = new Perfil();

        List<String> erros = service.validar(perfil);

        assertFalse(erros.isEmpty());
        assertTrue(erros.contains("Nome do perfil é obrigatório."));
    }
}
