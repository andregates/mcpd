package controlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import dao.PropriedadeDAO;
import models.Cultura;
import models.CulturaPropriedade;
import models.Propriedade;

@SuppressWarnings("serial")
@ManagedBean(name = "propriedadeBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@ViewScoped
public class PropriedadeBean {

	private Propriedade propriedade = new Propriedade();
	private ArrayList<Cultura> culturas = new ArrayList<>();
	private List<Propriedade> propriedades;
	
	private List<CulturaPropriedade> culturaPropriedade = new ArrayList<CulturaPropriedade>();

	public Propriedade getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(Propriedade propriedade) {
		this.propriedade = propriedade;
	}

	public ArrayList<Cultura> getCulturas() {
		return culturas;
	}

	public void setCulturas(ArrayList<Cultura> culturas) {
		this.culturas = culturas;
	}

	public List<Propriedade> getPropriedades() {
		try {
			PropriedadeDAO propriedadeDAO = new PropriedadeDAO();
			propriedades = propriedadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return propriedades;
	}

	public void setPropriedades(List<Propriedade> propriedades) {
		this.propriedades = propriedades;
	}

	// M�todo para limpar o campo.
	// Chamar l� na view, quando o usu�rio clicar no bot�o.
	public void novo() {
		propriedade = new Propriedade();
	}

	public void salvar() {
		try {
			PropriedadeDAO propriedadeDAO = new PropriedadeDAO();
			propriedade.setCulturaPropriedade(culturaPropriedade);
			propriedadeDAO.salvar(propriedade);

			novo();

			Messages.addGlobalInfo("Cadastro realizado com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();
		}
	}

	@PostConstruct // metodo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {
			PropriedadeDAO propriedadeDAO = new PropriedadeDAO();
			propriedades = propriedadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		propriedade = (Propriedade) evento.getComponent().getAttributes().get("propriedadeSelecionada");

	}

	public void excluir(ActionEvent evento) {

		try {
			// pega o coponente do evento, pega os atributos do componente, pega pelo nome
			// do aributo.
			propriedade = (Propriedade) evento.getComponent().getAttributes().get("propriedadeSelecionada");

			PropriedadeDAO propriedadeDAO = new PropriedadeDAO();
			propriedadeDAO.deletar(propriedade);

			propriedades = propriedadeDAO.listar();

			Messages.addGlobalInfo("Cadastro removido com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar remover");
			erro.printStackTrace();
		}
	}
	
	public void addCulturaPropriedade(Cultura cultura){
		
		CulturaPropriedade c = new CulturaPropriedade();
		c.setPropriedade(propriedade);
		c.setCultura(cultura);
		Date date = new Date();
		c.setDataAssociacao(date);
		culturaPropriedade.add(c);
		
	}
}
