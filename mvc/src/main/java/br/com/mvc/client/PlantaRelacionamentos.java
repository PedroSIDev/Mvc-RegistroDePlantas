package br.com.mvc.client;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.mvc.model.Ambiente;
import br.com.mvc.model.Planta;
import br.com.mvc.model.Usuario;

/**
 * A API devolve apenas usuarioId e ambienteId de cada planta.
 * Para as telas mostrarem os nomes, o MVC completa esses objetos com as
 * listas de usuários e ambientes que ele já carrega.
 */
public final class PlantaRelacionamentos {

    private PlantaRelacionamentos() {
    }

    public static List<Planta> preencher(List<Planta> plantas, List<Usuario> usuarios, List<Ambiente> ambientes) {
        Map<Long, Usuario> usuariosPorId = usuarios.stream()
                .collect(Collectors.toMap(Usuario::getId, Function.identity(), (a, b) -> a));
        Map<Long, Ambiente> ambientesPorId = ambientes.stream()
                .collect(Collectors.toMap(Ambiente::getId, Function.identity(), (a, b) -> a));
        for (Planta planta : plantas) {
            planta.setUsuario(usuariosPorId.get(planta.getUsuarioId()));
            planta.setAmbiente(ambientesPorId.get(planta.getAmbienteId()));
        }
        return plantas;
    }
}
