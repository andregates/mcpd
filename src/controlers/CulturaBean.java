package controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import models.Cultura;
import models.Praga;
import models.PragaCultura;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.omnifaces.util.Messages;

import dao.CulturaDAO;
import dao.PragaCulturaDAO;

@SuppressWarnings("serial")
@ManagedBean(name = "culturaBean")
// Os objetos s� ficam "vivos" enquanto o usu�rio estiver na tela.
@SessionScoped
public class CulturaBean implements Serializable {

	private Cultura cultura = new Cultura();
	private List<Cultura> culturas;

	private List<Praga> allPragas = new ArrayList<>();
	private List<Praga> selectedPragas = new ArrayList<>();
	private List<PragaCultura> pragaCultura = new ArrayList<PragaCultura>();

	@PostConstruct
	public void init() {
		allPragas = new ArrayList<Praga>();
		PragaBean c = new PragaBean();
		this.allPragas = c.getPragas();
		selectedPragas = new ArrayList<Praga>();
	}

	// M�todo para limpar o campo.
	// Chamar l� na view, quando o usu�rio clicar no bot�o.
	public void novo() {
		cultura = new Cultura();
		allPragas = new ArrayList<>();
		culturas = new ArrayList<>();
		pragaCultura = new ArrayList<>();
		selectedPragas = new ArrayList<>();

	}

	public String moveToCulturasCadastro() {
		this.cultura = new Cultura();
		return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
	}

	public boolean valida() {
		if (cultura.getNome().isEmpty() || cultura.getAreaPlantio().isEmpty() || cultura.getDescricao().isEmpty()
				|| cultura.getPeriodoPlantio() == null || cultura.getPeriodoColheita() == null
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
					cultura.setDataAtivacao(new Date());
					culturaDAO.salvar(this.cultura);

					for (Praga c : selectedPragas) {

						PragaCultura pc = new PragaCultura();
						PragaCulturaDAO cpDAO = new PragaCulturaDAO();
						pc.setDataAssociacao(new Date());
						pc.setCultura(cultura);
						pc.setPraga(c);
						cpDAO.salvar(pc);

					}
					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cadastro realizado com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/culturas/culturas.xhtml?faces-redirect=true";
				}
			} else {

				if (valida() == true) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Aten��o! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
				} else {
					culturaDAO.update(this.cultura);
					novo();
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

	public void editar(ActionEvent evento) {
		cultura = (Cultura) evento.getComponent().getAttributes().get("culturaSelecionada");

	}

	public String atualizar(Cultura cultura) {
		cultura.getCulturaId();
		this.cultura = cultura;
		return "/culturas/cadastrar_culturas.xhtml?faces-redirect=true";
	}

	public void excluir(Cultura cultura) {
		CulturaDAO culturaDao = new CulturaDAO();
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage messagem = new FacesMessage("Cultura removida");
		messagem.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, messagem);
		cultura.setDataInativacao(Calendar.getInstance().getTime());
		culturaDao.update(cultura);
		this.cultura = new Cultura();
		this.culturas = culturaDao.listar("Cultura");
	}

	public String historico(Cultura cultura) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.setAttribute("cultura", cultura);
		return "/culturas/historicos.xhtml?faces-redirect=true";
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public List<Cultura> getCulturas() {

		try {
			CulturaDAO culturaDAO = new CulturaDAO();
			culturas = culturaDAO.listar("Cultura");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
		return culturas;
	}

	public void setCulturas(List<Cultura> culturas) {
		this.culturas = culturas;
	}

	public List<Praga> getAllPragas() {
		return allPragas;
	}

	public void setAllPragas(List<Praga> allPragas) {
		this.allPragas = allPragas;
	}

	public List<Praga> getSelectedPragas() {
		return selectedPragas;
	}

	public void setSelectedPragas(List<Praga> selectedPragas) {
		this.selectedPragas = selectedPragas;
		System.out.println(selectedPragas.toString());
	}

	public List<PragaCultura> getPragaCultura() {
		return pragaCultura;
	}

	public void setPragaCultura(List<PragaCultura> pragaCultura) {
		this.pragaCultura = pragaCultura;
	}

}
