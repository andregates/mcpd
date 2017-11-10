package dao;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import models.Usuario;
import util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {

	/*public Usuario autenticar(String username, String cpf) {

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
	}*/

	@SuppressWarnings({ "deprecation" })
	public Usuario logar(Usuario user) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("nomeUsuario", user.getNomeUsuario()))
					.add(Restrictions.eq("cpf", user.getCpf()));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}

}
