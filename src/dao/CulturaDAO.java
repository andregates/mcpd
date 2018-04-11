package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import models.Cultura;
import util.HibernateUtil;

public class CulturaDAO extends GenericDAO<Cultura> {

	public Set<Cultura> findByCulturaRegistro() {
		try {
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Set<Cultura> result = new HashSet<Cultura>();
			Query query = sessao.getSession().createQuery(
					"select r.cultura.culturaId, r.cultura.nome from Registro r ");

			@SuppressWarnings("unchecked")
			List<Object> rs = query.list();
			for (int i = 0; i < rs.size(); i++) {

				Object[] obj = (Object[]) rs.get(i);

				Cultura cultura = new Cultura();
				cultura.setCulturaId((Integer) obj[0]);
				cultura.setNome(((String) obj[1]));

				result.add(cultura);

			}

			return result;
		} catch (Exception e) {
			throw e;

		}

	}

}
