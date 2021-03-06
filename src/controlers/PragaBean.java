package controlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.omnifaces.util.Messages;

import dao.PragaDAO;
import models.Praga;
import util.Upload;

@SuppressWarnings("serial")
@ManagedBean(name = "pragaBean")
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

	private String escalasFormatadas;
	
	@PostConstruct
	public void init() {
		this.pragas = getPragas();
	}

	/**
	 * Metodo para limpar o campo. Chamar lista na view, quando o usuario clicar no
	 * bot�o.
	 */
	public void novo() {
		praga = new Praga();
		pragas = new ArrayList<>();
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
		if (praga.getAcaoCombate().isEmpty() || praga.getDescricao().isEmpty() || praga.getNome().isEmpty()
				|| praga.getNomeCientifico().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validacaoEscala() {
		int count = 0;

		if (praga.getEscala1().isEmpty())
			count++;

		if (praga.getEscala2().isEmpty())
			count++;

		if (praga.getEscala3().isEmpty())
			count++;

		if (praga.getEscala4().isEmpty())
			count++;

		if (praga.getEscala5().isEmpty())
			count++;

		if (count > 2)
			return true;
		else
			return false;
	}

	public String salvar() throws IOException {
		try {
			PragaDAO pragaDAO = new PragaDAO();

			if (praga.getPragaId() == 0) {

				if (valida()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Aten��o! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
				} else if (validacaoEscala()) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Aten��o! Preencha pelo menos 3 n�veis de Escala de Gravidade",
									"Preencha todos os campos"));
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
							"Aten��o! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/pragas_e_danos/cadastrar_usuarios.xhtml?faces-redirect=true";
				} else {
					pragaDAO.update(this.praga);
					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Edi��o realizada com sucesso!"));
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
			Messages.addGlobalInfo("Voc� n�o selecionou nenhuma imagem.");
		else if (paths.size() < 5) {
			partList.add(uploadedPhoto);
			paths.add(upload.extractFileName(uploadedPhoto));
		} else
			Messages.addGlobalInfo("Somente � permitido o anexo de cinco imagens");

		return partList;
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public String atualizar(Praga praga) {
		this.praga = praga;
		return "/pragas_e_danos/cadastrar_pragas.xhtml?faces-redirect=true";
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
			pragas = pragaDAO.listar("Praga");
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

	public String getEscalasFormatadas() {
		return escalasFormatadas;
	}

	public void setEscalasFormatadas(String escalasFormatadas) {
		this.escalasFormatadas = escalasFormatadas;
	}

}
