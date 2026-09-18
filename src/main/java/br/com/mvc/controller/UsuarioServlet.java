package br.com.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvc.model.Usuario;
import br.com.mvc.service.UsuarioService;
import br.com.mvc.service.PerfilService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends BaseServlet {

    private final UsuarioService usuarioService = new UsuarioService();
    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao != null && !acao.isBlank()) {
            try {
                if ("excluir".equals(acao)) {
                    throw new IllegalArgumentException("Para excluir, use o botão de exclusão e confirme a operação.");
                }
                if (!"novo".equals(acao) && !"editar".equals(acao)) {
                    throw new IllegalArgumentException("Ação inválida. Selecione uma opção da lista.");
                }
                Usuario usuario = "novo".equals(acao) ? new Usuario()
                        : usuarioService.buscarPorId(idObrigatorio(request));
                if (usuario == null) {
                    throw new IllegalArgumentException("Usuário não encontrado. O registro pode ter sido excluído.");
                }
                request.setAttribute("usuario", usuario);
                carregarOpcoes(request);
            } catch (RuntimeException e) {
                mensagem(request, false, mensagemErro(e));
                redirect(request, response, "/usuarios");
                return;
            }
            forward(request, response, "/WEB-INF/jsp/usuarios/form.jsp");
            return;
        }

        try {
            request.setAttribute("usuarios", usuarioService.listarTodos());
        } catch (RuntimeException e) {
            request.setAttribute("listaFalhou", true);
            request.setAttribute("mensagemErro", mensagemErro(e));
        }
        forward(request, response, "/WEB-INF/jsp/usuarios/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id;
        try {
            validarFormulario(request);
            if ("excluir".equals(request.getParameter("acao"))) {
                usuarioService.excluir(idObrigatorio(request), (Usuario) request.getSession().getAttribute("usuarioLogado"));
                mensagem(request, true, "Usuário excluído com sucesso.");
                redirect(request, response, "/usuarios");
                return;
            }
            id = idFormulario(request);
            if (id != null && usuarioService.buscarPorId(id) == null) {
                throw new IllegalArgumentException("Usuário não encontrado. Atualize a lista antes de editar.");
            }
        } catch (RuntimeException e) {
            mensagem(request, false, mensagemErro(e));
            redirect(request, response, "/usuarios");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(request.getParameter("nome"));
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        usuario.setPerfilId(parseId(request.getParameter("perfilId")));
        request.setAttribute("usuario", usuario);

        List<String> erros = new ArrayList<>();
        try {
            carregarOpcoes(request);
            erros.addAll(usuarioService.validar(usuario));
            if (erros.isEmpty()) {
                usuario.setPerfil(perfilService.buscarPorId(usuario.getPerfilId()));
                if (id == null) {
                    usuarioService.inserir(usuario);
                } else {
                    usuarioService.alterar(usuario);
                }
            }
        } catch (RuntimeException e) {
            erros.add(mensagemErro(e));
        }

        if (!erros.isEmpty()) {
            usuario.setSenha(null);
            request.setAttribute("erros", erros);
            forward(request, response, "/WEB-INF/jsp/usuarios/form.jsp");
            return;
        }

        Usuario logado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (logado != null && id != null && id.equals(logado.getId())) {
            request.getSession().setAttribute("usuarioLogado", usuario);
        }

        mensagem(request, true, id == null ? "Usuário cadastrado com sucesso."
                : "Usuário atualizado com sucesso.");
        if (logado != null && id != null && id.equals(logado.getId())
                && (usuario.getPerfil() == null
                    || !"Administrador".equalsIgnoreCase(usuario.getPerfil().getNome()))) {
            redirect(request, response, "/home");
            return;
        }
        redirect(request, response, "/usuarios");
    }

    private void carregarOpcoes(HttpServletRequest request) {
        request.setAttribute("perfis", perfilService.listarTodos());
    }
}
