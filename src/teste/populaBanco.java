package teste;

import java.util.ArrayList;
import java.util.List;

import dao.CulturaDAO;
import dao.PragaDAO;
import dao.PropriedadeDAO;
import dao.UsuarioDAO;
import models.Cultura;
import models.Praga;
import models.Propriedade;
import models.Usuario;

public class populaBanco {
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		UsuarioDAO udao = new UsuarioDAO();
		PropriedadeDAO pdao = new PropriedadeDAO();
		CulturaDAO cdao = new CulturaDAO();
		PragaDAO pradao = new PragaDAO();
		
		for(int i=0; i<10; i++) {

			Usuario u = new Usuario("Usuario"+i, "cpf"+i, "nomeUsuario"+i, null, null);
			udao.salvar(u);
			/*
			Propriedade p = new Propriedade("nomePropriedade"+i, "localidade"+i, "cidade"+i, "uf"+i, "pais"+i,
					"nomeResponsavel"+i, "telefoneResponsavel"+i, "nomeProprietario"+i, "cpf"+i, 
					"telefoneProprietario+i", "area"+i, 10.0, 10.0, null, null);
			pdao.salvar(p);
			
			Cultura c = new Cultura("Cultura"+i, "descricao"+i, "PeriodoPlantio"+i, "PeriodoColheita"+i, 
					"AreaPlantio"+i, "observacao"+i, null, null, null, null);
			cdao.salvar(c);
			
			Praga pr = new Praga("nome"+i,"nomeScientifico"+i, "Descricaao"+i, "acaoCombate"+i, "escala1 "+i, 
					"escala2 "+i, "escla3 "+i, "escala4 "+i, "escala5 "+i,null, null);
			pradao.salvar(pr); */
			
		}
		
		
	}
	
	
}

