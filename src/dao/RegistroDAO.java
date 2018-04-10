package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.hibernate.Session;
import models.Cultura;
import models.Registro;
import util.HibernateUtil;

public class RegistroDAO extends GenericDAO<Registro> {

	@SuppressWarnings("unchecked")
	public List<List<Registro>> gerarGraficoProjetado(Date dateInicial, Date dateFinal,
			List<Cultura> culturasSelecionadas) {

		List<Registro> registros = new ArrayList<>();
		List<List<Registro>> registrosAll = new ArrayList<>(); /*
																 * Session session =
																 * HibernateUtil.getSessionFactory().openSession();
																 * 
																 * for (int i = 0; i < culturasSelecionadas.size(); i++)
																 * { String hql =
																 * "select c.culturaId, r.escala, c.nome, r.dataRegistro from Registro r "
																 * + " inner join r.cultura as c" +
																 * "	where c.culturaId =:id";
																 * 
																 * 
																 * Query query = session.createQuery(hql);
																 * query.setParameter("id",
																 * culturasSelecionadas.get(i).getCulturaId());
																 * 
																 * registros = query.list();
																 * 
																 * registrosAll.add(registros); }
																 */

		for (int i = 0; i < culturasSelecionadas.size(); i++) {
			registros = (List<Registro>) getJdbcTemplate().query(
					"select cult.culturaId, regist.escala, cult.nome, regist.dataRegistro from registros as regist inner join culturas as cult on (cult.culturaId = regist.culturaId)"
							+ " WHERE cult.culturaId = ?",
					new Object[] { culturasSelecionadas.get(i).getCulturaId() }, new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int row) throws SQLException {
							Registro registro = new Registro();
							registro.setCultura(new Cultura());
							registro.getCultura().setCulturaId(rs.getInt("culturaId"));
							registro.setEscala(rs.getInt("escala"));
							registro.getCultura().setNome(rs.getString("periodo_ingresso"));
							registro.getCultura().setDataAtivacao(rs.getDate("dataRegistro"));
							return registro;
						}
					});
			registrosAll.add(registros);

		}
		return registrosAll;
	}
}
