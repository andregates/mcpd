package controlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

import org.omnifaces.util.Messages;

import dao.PragaDAO;
import dao.UsuarioDAO;
import models.Praga;
import models.Usuario;
import util.Upload;

@SuppressWarnings("serial")
@ManagedBean(name = "pragaBean")
// Os objetos sï¿½ ficam "vivos" enquanto o usuï¿½rio estiver na tela.
@SessionScoped
public class PragaBean implements Serializable {

	private Praga praga;
	private List<Praga> pragas;

	/**
	 * Lista de caminhos das imagens
	 */
	private List<String> paths;
	private List<Part> partList;
	private Part uploadedPhoto;

	/**
	 * Metodo para limpar o campo. Chamar lista na view, quando o usuario clicar no
	 * botão.
	 */
	public void novo() {
		praga = new Praga();
	}
	
	public String moveToCadastraPraga() {
		novo();
		return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
	}

	public PragaBean() {
		this.praga = new Praga();
		this.pragas = new ArrayList<>();
		this.paths = new ArrayList<>();
		this.partList = new ArrayList<>();
	}

	public boolean valida() {
		if (praga.getAcaoCombate().isEmpty() || praga.getDescricao().isEmpty() || praga.getEscala().isEmpty()
				|| praga.getNome().isEmpty() || praga.getNomeCientifico().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String salvar() throws IOException {
		try {
			PragaDAO pragaDAO = new PragaDAO();

			if (praga.getPragaId() == 0) {

				if (valida()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
				} else {
					praga.setListaPath(paths);
					pragaDAO.salvar(praga);

					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cadastro realizado com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/pragas_e_danos/pragas.xhtml?faces-redirect=true";

				}

			} else {
				if (valida()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/pragas_e_danos/cadastrar_usuarios.xhtml?faces-redirect=true";
				} else {
					pragaDAO.update(this.praga);
					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Edição realizada com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/pragas_e_danos/pragas.xhtml?faces-redirect=true";
				}
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();
			return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
		}
	}

	
	/**
	 * Metodo construtor para chamar automaticamente o metodo quando o ManagedBean
	 * for criado
	 */
	@PostConstruct
	public void listar() {
		try {
			PragaDAO pragaDAO = new PragaDAO();
			pragas = pragaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
	}

	public void excluir(Praga praga) {

		PragaDAO pragaDao = new PragaDAO();
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage messagem = new FacesMessage("Praga removida");
		messagem.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, messagem);
		pragaDao.deletar(praga);
		novo();
		this.pragas = pragaDao.listar();
	}

	public void uploadDef() {
		try {

			Upload upload = Upload.getInstance();
			praga.setListaPath(upload.write(uploadedPhoto, paths));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Part> uploadImagem() {
		// List<String> imagensTemp = new ArrayList<>();
		Upload upload = Upload.getInstance();
		if (uploadedPhoto == null)
			Messages.addGlobalInfo("Você não selecionou nenhuma imagem.");
		else if (paths.size() < 5) {
			partList.add(uploadedPhoto);
			paths.add(upload.extractFileName(uploadedPhoto));
		} else
			Messages.addGlobalInfo("Somente é permitido o anexo de cinco imagens");

		return partList;
	}

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


	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

	public Part getUploadedPhoto() {
		return uploadedPhoto;
	}

	public void setUploadedPhoto(Part uploadedPhoto) {
		this.uploadedPhoto = uploadedPhoto;
	}

	public void setPragas(List<Praga> pragas) {
		this.pragas = pragas;
	}

	public List<Part> getPartList() {
		return partList;
	}

	public void setPartList(List<Part> partList) {
		this.partList = partList;
	}


	
	 public String atualizar(Praga praga) {
			this.praga = praga;
			return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
		}

}
