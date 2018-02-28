package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
//import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import models.Historico;
import util.HibernateUtil;

public class GenericDAO<T> {

	private Class<T> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void salvar(T entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = null;

		try {
			t = sessao.beginTransaction();
			sessao.saveOrUpdate(entidade);
			t.commit();
		} catch (RuntimeException erro) {
			if (t != null) {
				t.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public List<T> listar() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(classe);

		Root<T> klassRoot = query.from(classe);

		if (this.classe.getName().equals("models.CulturaPropriedade")
				|| this.classe.getName().equals("models.PragaCultura") || this.classe.getName().equals("models.Usuario")
				|| this.classe.getName().equals("models.Cultura")) {
			query.select(klassRoot).where(builder.isNull(klassRoot.get("dataInativacao")));
		}
		List<T> result = session.createQuery(query).getResultList();

		session.close();
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<T> listarCriterio(String attribute, Integer id, String atributoOrdenacao) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.eq(attribute, id));
			consulta.addOrder(Order.asc(atributoOrdenacao));
			List<T> resultado = (List<T>) consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
        
     }


	@SuppressWarnings({ "unchecked", "deprecation" })
	public T buscar(Integer id) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(id));
			T resultado = (T) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}

	public void deletar(T entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.delete(entidade);
			t.commit();
		} catch (RuntimeException erro) {
			if (t != null) {
				t.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void editar(T entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.update(entidade);
			t.commit();

		} catch (RuntimeException erro) {
			if (t != null) {
				t.rollback();
			}

			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void merge(T entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(entidade);
			t.commit();

		} catch (RuntimeException erro) {
			if (t != null) {
				t.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void update(T object) {
		Transaction transacao = null;
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			transacao = sessao.beginTransaction();
			sessao.update((T) object);
			transacao.commit();
		} catch (Exception e) {
			if (transacao != null) {
				transacao.rollback();
			}
		} finally {
			sessao.close();
		}
	}

}
