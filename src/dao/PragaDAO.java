package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import models.Praga;
import util.HibernateUtil;

public class PragaDAO extends GenericDAO<Praga> {

	public List<Praga> listar() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Praga> query = builder.createQuery(Praga.class);

		Root<Praga> klassRoot = query.from(Praga.class);

		query.select(klassRoot);

		List<Praga> result = session.createQuery(query).getResultList();

		formataEscala(result);

		session.close();
		return result;
	}

	private void formataEscala(List<Praga> pragas) {

		ArrayList<String> listaEscalas = new ArrayList<>();

		for (int i = 0; i < pragas.size(); i++) {

			if (pragas.get(i).getEscala1() != null)
				listaEscalas.add(pragas.get(i).getEscala1());

			if (pragas.get(i).getEscala2() != null)
				listaEscalas.add(pragas.get(i).getEscala2());

			if (pragas.get(i).getEscala3() != null)
				listaEscalas.add(pragas.get(i).getEscala3());

			if (pragas.get(i).getEscala4() != null)
				listaEscalas.add(pragas.get(i).getEscala4());

			if (pragas.get(i).getEscala5() != null)
				listaEscalas.add(pragas.get(i).getEscala5());

			pragas.get(i).setEscalas(listaEscalas.toString());
		}
	}

}
