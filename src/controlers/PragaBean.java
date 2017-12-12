package controlers;

import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;


import dao.PragaDAO;
import models.Praga;

@SuppressWarnings("serial")
@ManagedBean(name = "pragaBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@ViewScoped
public class PragaBean implements Serializable {

	private Praga praga = new Praga();
	private List<Praga> pragas;

	public Praga getPraga() {
		return praga;
	}

	public void setPraga(Praga praga) {
		this.praga = praga;
	}

	public List<Praga> getPragas() {
		
		try {
			PragaDAO pragaDAO = new PragaDAO();
			pragas = pragaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return pragas;
	}

	public void setPragas(List<Praga> pragas) {
		this.pragas = pragas;
	}

	// M�todo para limpar o campo.
	// Chamar l� na view, quando o usu�rio clicar no bot�o.
	public void novo() {
		praga = new Praga();
	}

	public String salvar() {
		try {
			PragaDAO pragaDAO = new PragaDAO();
			pragaDAO.salvar(praga);

			novo();

			Messages.addGlobalInfo("Cadastro realizado com sucesso");

			return "/pragas_e_danos/pragas.xhtml?faces-redirect=true";
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();

			return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";

		}

		

	}

	@PostConstruct // m�todo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {
			PragaDAO pragaDAO = new PragaDAO();
			pragas = pragaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		praga = (Praga) evento.getComponent().getAttributes().get("pragaSelecionada");

	}

	public void excluir(Praga praga) {

		try {
			// pega o coponente do evento, pega os atributos do componente, pega pelo nome
			// do aributo.
			this.praga = praga;

			PragaDAO pragaDAO = new PragaDAO();
			pragaDAO.deletar(praga);

			pragas = pragaDAO.listar();

			Messages.addGlobalInfo("Cadastro removido com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar remover");
			erro.printStackTrace();
		}
	}
	
	 public String atualizar(Praga praga) {
			this.praga = praga;
			return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
		}
}
