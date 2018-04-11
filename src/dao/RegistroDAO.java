package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import models.Cultura;
import models.Registro;
import util.HibernateUtil;

public class RegistroDAO extends GenericDAO<Registro> {

	
	/**Consulta que retorna lista projetada para geração de grafico. 
	 * Implementação baseada em consultas realizadas no sistema SIPAC, mantido pela Superintendencia de Informatica da UFRN (SINFO)*/
	@SuppressWarnings("unchecked")
	public List<List<Registro>> gerarGraficoProjetado(Date dateInicial, Date dateFinal,
			List<Cultura> culturasSelecionadas) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			List<List<Registro>> registroList = new ArrayList<List<Registro>>();
			List<Registro> result = new ArrayList<Registro>();

			for (int i = 0; i < culturasSelecionadas.size(); i++) {

				Query query = sessao.getSession()
						.createQuery("select r.escala, r.dataRegistro, c.culturaId, c.nome from Registro r "
								+ "inner join r.cultura as c " + 
								" where c.culturaId = " + culturasSelecionadas.get(i).getCulturaId());

				@SuppressWarnings("unchecked")
				List<Object> rs = query.list();
				for (int a = 0; a < rs.size(); a++) {

					Object[] obj = (Object[]) rs.get(a);

					Registro registro = new Registro();
					registro.setEscala(((Integer) obj[0]));
					registro.setDataRegistro(((Date) obj[1]));

					Cultura cultura = new Cultura();
					cultura.setCulturaId((Integer) obj[2]);
					cultura.setNome(((String) obj[3]));

					registro.setCultura(cultura);

					result.add(registro);

				}
				
				if(!result.isEmpty())
					registroList.add(result);
				
				result = new ArrayList<Registro>();

			}

			return registroList;

		} catch (Exception e) {
			throw e;

		}

		/*
		 * List<Registro> registros = new ArrayList<>(); List<List<Registro>>
		 * registrosAll = new ArrayList<>(); Session session =
		 * HibernateUtil.getSessionFactory().openSession();
		 * 
		 * for (int i = 0; i < culturasSelecionadas.size(); i++) { String hql =
		 * "select c.culturaId, r.escala, c.nome, r.dataRegistro from Registro r " +
		 * " inner join r.cultura as c" + "	where c.culturaId =:id";
		 * 
		 * 
		 * Query query = session.createQuery(hql); query.setParameter("id",
		 * culturasSelecionadas.get(i).getCulturaId());
		 * 
		 * registros = query.list();
		 * 
		 * registrosAll.add(registros); }
		 */

	}
}
