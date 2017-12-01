package teste;

import models.CulturaPropriedade;

import java.util.Date;

import dao.CulturaDAO;
import dao.CulturaPropriedadeDAO;
import dao.PropriedadeDAO;

public class TesteCulturaPropriedade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CulturaPropriedade cp = new CulturaPropriedade();
		CulturaPropriedadeDAO cpDAO = new CulturaPropriedadeDAO();
		
		cp.setDataAssociacao(new Date());
		cp.setPropriedade((new PropriedadeDAO()).buscar(1));
		cp.setCultura((new CulturaDAO()).buscar(2));
		cpDAO.salvar(cp);

	}

}
