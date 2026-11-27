package br.com.mvc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import br.com.mvc.controller.api.ApiException;
import br.com.mvc.controller.api.ApiJson;
import br.com.mvc.dto.PlantaEntrada;
import br.com.mvc.model.Planta;
import br.com.mvc.security.PasswordUtil;

/**
 * Testes da API que nao dependem do banco de dados.
 */
public class PlantaApiUnitTest {

    @Test
    public void plantaDeveValidarCamposObrigatorios() {
        Planta planta = new Planta();
        planta.setNomePopular("   ");

        List<String> erros = new PlantaService().validar(planta);

        assertTrue(erros.contains("Nome popular da planta é obrigatório."));
        assertTrue(erros.contains("Usuário é obrigatório."));
        assertTrue(erros.contains("Ambiente é obrigatório."));
    }

    @Test
    public void plantaDeveRejeitarDataInvalida() {
        Planta planta = new Planta();
        planta.setNomePopular("Jiboia");
        planta.setDataAquisicao("2026-02-31");

        List<String> erros = new PlantaService().validar(planta);

        assertTrue(erros.stream().anyMatch(e -> e.startsWith("Data de aquisição inválida")));
    }

    @Test
    public void entradaDeveConverterJsonParaPlanta() {
        Planta planta = PlantaEntrada.fromJson(ApiJson.objeto(
                "{\"nomePopular\":\"Manjericão\",\"usuarioId\":1,\"ambienteId\":2}"));

        assertEquals("Manjericão", planta.getNomePopular());
        assertEquals(Long.valueOf(1), planta.getUsuarioId());
        assertEquals(Long.valueOf(2), planta.getAmbienteId());
    }

    @Test
    public void entradaDeveRejeitarCampoDesconhecido() {
        try {
            PlantaEntrada.fromJson(ApiJson.objeto("{\"id\":5,\"nomePopular\":\"X\"}"));
            fail("Deveria rejeitar o campo id");
        } catch (ApiException e) {
            assertEquals(400, e.getStatus());
            assertEquals("CAMPO_DESCONHECIDO", e.getCodigo());
        }
    }

    @Test
    public void jsonMalformadoDeveGerar400() {
        try {
            ApiJson.objeto("{nomePopular: 'X'}");
            fail("Deveria rejeitar JSON malformado");
        } catch (ApiException e) {
            assertEquals(400, e.getStatus());
        }
    }

    @Test
    public void senhaDeveUsarBcrypt() {
        String hash = PasswordUtil.hash("123456");
        assertTrue(PasswordUtil.isHashed(hash));
        assertTrue(PasswordUtil.verificar("123456", hash));
        assertFalse(PasswordUtil.verificar("errada", hash));
    }
}
