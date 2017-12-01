package controlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import dao.CulturaDAO;
import dao.CulturaPropriedadeDAO;
import dao.PropriedadeDAO;
import models.Cultura;
import models.CulturaPropriedade;
import models.Propriedade;

@SuppressWarnings("serial")
@ManagedBean(name = "propriedadeBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@SessionScoped
public class PropriedadeBean {

	private Propriedade propriedade = new Propriedade();
	
	private List<Propriedade> propriedades;
	
	private List<Cultura> allCulturas = new ArrayList<>();
	private List<Cultura> selectedCulturas = new ArrayList<>();
	private List<CulturaPropriedade> culturaPropriedade = new ArrayList<CulturaPropriedade>();
	
	@PostConstruct
	public void init(){
	
		allCulturas = new ArrayList<Cultura>();
		CulturaBean c = new CulturaBean();
		this.allCulturas=c.getCulturas();
		
		selectedCulturas=new ArrayList<Cultura>();
		
			
	}
	
	public List<Cultura> getAllCulturas() {
		return allCulturas;
	}

	public void setAllCulturas(List<Cultura> allCulturas) {
		this.allCulturas = allCulturas;
	}

	public List<Cultura> getSelectedCulturas() {
		return selectedCulturas;
	}

	public void setSelectedCulturas(List<Cultura> selectedCulturas) {
		this.selectedCulturas = selectedCulturas;
		System.out.println(selectedCulturas.toString());
	}

	public List<CulturaPropriedade> getCulturaPropriedade() {
		return culturaPropriedade;
	}

	public void setCulturaPropriedade(List<CulturaPropriedade> culturaPropriedade) {
		this.culturaPropriedade = culturaPropriedade;
	}


	
	
	public Propriedade getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(Propriedade propriedade) {
		this.propriedade = propriedade;
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
			propriedadeDAO.salvar(propriedade);
			
			for(Cultura c:selectedCulturas){
				
				CulturaPropriedade cp = new CulturaPropriedade();
				CulturaPropriedadeDAO cpDAO = new CulturaPropriedadeDAO();
				cp.setDataAssociacao(new Date());
				cp.setPropriedade(propriedade);
				cp.setCultura(c);
				cpDAO.salvar(cp);
			  			
			}
			
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
	
	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}
	
}
