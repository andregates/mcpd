package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
//import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
			sessao.save(entidade);
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

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<T> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			List<T> resultado = consulta.list();
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

}
