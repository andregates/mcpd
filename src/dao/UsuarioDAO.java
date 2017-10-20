package dao;

import org.hibernate.Query;

import org.hibernate.Session;

import models.Usuario;
import util.HibernateUtil;

@SuppressWarnings("deprecation")
public class UsuarioDAO  extends GenericDAO<Usuario>{
	
	public Usuario autenticar(String username, String cpf){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario usuario = null;
		
		try {
			
			Query consulta = session.createQuery("FROM Usuario u WHERE u.nomeUsuario = :username AND u.cpf = :cpf");
			consulta.setString("nomeUsuario", username);
			consulta.setString("cpf", cpf);
			
			usuario = (Usuario) consulta.uniqueResult();
			
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		} finally {
			session.close();
		}
		
		return usuario;
		
	}

}
