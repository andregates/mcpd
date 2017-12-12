package dao;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import models.Usuario;
import util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {

	@SuppressWarnings({ "deprecation" })
	public Usuario logar(Usuario user) {
		Session sessao  = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("nomeUsuario", user.getNomeUsuario()))
					.add(Restrictions.eq("cpf", user.getCpf()));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpSession s = (HttpSession) ec.getSession(true);
			s.setAttribute("usuario-logado", resultado);
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}

}
