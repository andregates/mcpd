package controlers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import models.Cultura;
import models.Usuario;

import org.omnifaces.util.Messages;

import dao.CulturaDAO;
import dao.UsuarioDAO;

@SuppressWarnings("serial")
@ManagedBean(name = "culturaBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@SessionScoped
public class CulturaBean implements Serializable {

	private Cultura cultura = new Cultura();
	private List<Cultura> culturas;

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public List<Cultura> getCulturas() {

		try {
			CulturaDAO culturaDAO = new CulturaDAO();
			culturas = culturaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return culturas;
	}

	public void setCulturas(List<Cultura> culturas) {
		this.culturas = culturas;
	}

	// M�todo para limpar o campo.
	// Chamar l� na view, quando o usu�rio clicar no bot�o.
	public void novo() {
		cultura = new Cultura();
	}

	public boolean valida() {
		if (cultura.getNome().isEmpty() || cultura.getAreaPlantio().isEmpty() || cultura.getDescricao().isEmpty() || cultura.getPeriodoPlantio().isEmpty()
				|| cultura.getObs().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String salvar() {
		// Flash scope
		// faz com que a mensagem de sucesso seja perpetuada apos o
		// redirecionamento

		try {
			CulturaDAO culturaDAO = new CulturaDAO();

			if (cultura.getCulturaId() == null) {

				if (valida() == true) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o!", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
				} else {
					culturaDAO.salvar(this.cultura);
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cadastro realizado com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/culturas/culturas.xhtml?faces-redirect=true";
				}
			} else {

				if (valida() == true) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
				} else {
					culturaDAO.update(this.cultura);
					cultura = new Cultura();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Edi��o realizada com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/culturas/culturas.xhtml?faces-redirect=true";
				}
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();

			return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";

		}
	}

	@PostConstruct // metodo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {
			CulturaDAO culturaDAO = new CulturaDAO();
			culturas = culturaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		cultura = (Cultura) evento.getComponent().getAttributes().get("culturaSelecionada");

	}
	
	public String atualizar(Cultura cultura) {
		cultura.getCulturaId();
		this.cultura = cultura;
		return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
	}

	public void excluir(ActionEvent evento) {

		try {
			// pega o coponente do evento, pega os atributos do componente, pega pelo nome
			// do aributo.
			cultura = (Cultura) evento.getComponent().getAttributes().get("culturaSelecionada");

			CulturaDAO culturaDAO = new CulturaDAO();
			culturaDAO.deletar(cultura);

			culturas = culturaDAO.listar();

			Messages.addGlobalInfo("Cadastro removido com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar remover");
			erro.printStackTrace();
		}
	}

}
