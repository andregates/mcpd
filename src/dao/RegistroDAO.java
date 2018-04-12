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
								.createQuery("SELECT r.cultura.culturaId, r.cultura.nome, r.dataRegistro, r.escala, (sum(r.escala)/count(r.cultura.culturaId)) "+
										" FROM Registro r INNER JOIN r.cultura as c "+			
										" GROUP BY r.cultura.culturaId, r.dataRegistro, r.dataRegistro " +
										" HAVING (((r.cultura.culturaId) = "+ culturasSelecionadas.get(i).getCulturaId() +")) ORDER BY r.dataRegistro");
				
				@SuppressWarnings("unchecked")
				List<Object> rs = query.list();
				for (int a = 0; a < rs.size(); a++) {

					Object[] obj = (Object[]) rs.get(a);


					Cultura cultura = new Cultura();
					cultura.setCulturaId((Integer) obj[0]);
					cultura.setNome(((String) obj[1]));

					Registro registro = new Registro();
					registro.setDataRegistro(((Date) obj[2]));
					registro.setEscala(((Integer) obj[3]));
					
					cultura.setEscalaPonderada((Long)obj[4]);

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

	}
}
