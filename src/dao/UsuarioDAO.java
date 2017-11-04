package dao;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import models.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

	public Usuario autenticar(String username, String cpf) {

		// Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario usuarioLogado = new Usuario();
		List<Usuario> usuarios = listar();
		// boolean flag = false;
		
		for (Usuario usuario : usuarios) {
			if (username.equals(usuario.getNomeUsuario()) && cpf.equals(usuario.getCpf())) {
				usuarioLogado = usuario;
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext ec = context.getExternalContext();
				HttpSession s = (HttpSession) ec.getSession(true);
				s.setAttribute("usuario-logado", usuario);
				break;
			} else {
				usuarioLogado = null;
			}
		}

		return usuarioLogado;
	}

}
