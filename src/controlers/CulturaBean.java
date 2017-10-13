package controlers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import models.Cultura;
import org.omnifaces.util.Messages;

import dao.CulturaDAO;

@SuppressWarnings("serial")
@ManagedBean(name = "culturaBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@ViewScoped
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

	public void salvar() {
		try {
			CulturaDAO culturaDAO = new CulturaDAO();
			culturaDAO.salvar(cultura);

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
