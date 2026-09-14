package br.com.mvc.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.com.mvc.model.Usuario;

public class UsuarioServiceTest {

    @Test
    public void autenticarDeveRetornarUsuarioValido() {
        UsuarioService service = new UsuarioService();

        Usuario usuario = service.autenticar("pedro", "123456");

        assertNotNull(usuario);
    }

    @Test
    public void autenticarDeveRejeitarCredenciaisInvalidas() {
        UsuarioService service = new UsuarioService();

        Usuario usuario = service.autenticar("pedro", "senha-errada");

        assertNull(usuario);
    }
}
