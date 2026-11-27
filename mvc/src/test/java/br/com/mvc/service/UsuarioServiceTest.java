package br.com.mvc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.mvc.model.Usuario;
import br.com.mvc.security.PasswordUtil;

public class UsuarioServiceTest {

    @Test
    public void autenticarDeveRetornarUsuarioValido() {
        UsuarioService service = new UsuarioService();

        Usuario usuario = service.autenticar("pedro", "123456");

        assertNotNull(usuario);
        assertEquals("pedro", usuario.getLogin());
    }

    @Test
    public void autenticarDeveRejeitarCredenciaisInvalidas() {
        UsuarioService service = new UsuarioService();

        Usuario usuario = service.autenticar("pedro", "senha-errada");

        assertNull(usuario);
    }

    @Test
    public void passwordUtilDeveGerarEValidarHashBcrypt() {
        String senhaPura = "minhaSenha123";
        String hash = PasswordUtil.hash(senhaPura);

        assertNotNull(hash);
        assertTrue(PasswordUtil.isHashed(hash));
        assertTrue(PasswordUtil.verificar(senhaPura, hash));
        assertFalse(PasswordUtil.verificar("outraSenha", hash));
    }

    @Test
    public void usuarioNovoDeveExigirSenhaMinimaDe6Caracteres() {
        UsuarioService service = new UsuarioService();
        Usuario novo = new Usuario();
        novo.setNome("Teste Novo");
        novo.setLogin("testecurto");
        novo.setSenha("123");
        novo.setPerfilId(2L);

        List<String> erros = service.validar(novo);
        assertTrue(erros.contains("A senha deve conter no mínimo 6 caracteres."));
    }

    @Test
    public void usuarioEdicaoPodeManterSenhaVazia() {
        UsuarioService service = new UsuarioService();
        Usuario editado = new Usuario();
        editado.setId(999L);
        editado.setNome("Teste Editado");
        editado.setLogin("testeedit");
        editado.setSenha("");
        editado.setPerfilId(2L);

        List<String> erros = service.validar(editado);
        assertTrue(erros.isEmpty());
    }
}
