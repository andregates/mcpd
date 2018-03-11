package controlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import dao.CulturaPropriedadeDAO;
import dao.PropriedadeDAO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import models.Cultura;
import models.CulturaPropriedade;
import models.Praga;
import models.Propriedade;

@ManagedBean(name = "propriedadeBean")
// Os objetos sï¿½ ficam "vivos" enquanto o usuï¿½rio estiver na tela.
@SessionScoped
public class PropriedadeBean {

	private Propriedade propriedade = new Propriedade();

	private List<Propriedade> propriedades;

	private List<Cultura> allCulturas = new ArrayList<>();
	private List<Cultura> selectedCulturas = new ArrayList<>();
	private List<CulturaPropriedade> culturaPropriedade = new ArrayList<CulturaPropriedade>();

	@PostConstruct
	public void init() {

		allCulturas = new ArrayList<Cultura>();
		CulturaBean c = new CulturaBean();
		this.allCulturas = c.getCulturas();
		selectedCulturas = new ArrayList<Cultura>();

	}

	public String moveToCadastroPropriedade() {
		init();
		propriedade = new Propriedade();
		return "/propriedades/cadastrar_propriedade.xhtml?faces-redirect=true";
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
			propriedades = propriedadeDAO.listar("Propriedade");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}

		return propriedades;
	}

	public void setPropriedades(List<Propriedade> propriedades) {
		this.propriedades = propriedades;
	}

	// Mï¿½todo para limpar o campo.
	// Chamar lï¿½ na view, quando o usuï¿½rio clicar no botï¿½o.
	public void novo() {
		propriedade = new Propriedade();
	}

	public boolean valida() {
		if (propriedade.getArea() == null || propriedade.getCidade().isEmpty() || propriedade.getCpf().isEmpty()
				|| propriedade.getLatitude() == null || propriedade.getLongitude() == null
				|| propriedade.getNomePropriedade().isEmpty() || propriedade.getNomeProprietario().isEmpty()
				|| propriedade.getNomeResponsavel().isEmpty() || propriedade.getPais().isEmpty()
				|| propriedade.getTelefoneProprietario().isEmpty() || propriedade.getTelefoneResponsavel().isEmpty()
				|| propriedade.getUf().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public String salvar() {
		try {
			PropriedadeDAO propriedadeDAO = new PropriedadeDAO();
			if (propriedade.getPropriedadeId() == 0) {

				if (valida()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/propriedades/cadastrar_propriedade.xhtml?faces-redirect=true";
				} else {
					propriedadeDAO.salvar(propriedade);

					for (Cultura c : selectedCulturas) {

						CulturaPropriedade cp = new CulturaPropriedade();
						CulturaPropriedadeDAO cpDAO = new CulturaPropriedadeDAO();
						cp.setDataAssociacao(new Date());
						cp.setPropriedade(propriedade);
						cp.setCultura(c);
						cpDAO.salvar(cp);

					}

					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cadastro realizado com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/propriedades/propriedades.xhtml?faces-redirect=true";
				}
			} else {
				if (valida()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Atenção! Preencha todos os campos", "Preencha todos os campos"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					return "/pragas_e_danos/cadastrar_usuarios.xhtml?faces-redirect=true";
				} else {
					propriedadeDAO.update(this.propriedade);
					novo();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Edição realizada com sucesso!"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					return "/propriedades/propriedades.xhtml?faces-redirect=true";
				}
			}

		} catch (RuntimeException erro) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao cadastrar!"));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			erro.printStackTrace();
			return "/propriedades/propriedades.xhtml?faces-redirect=true";
		}
	}

	public String atualizar(Propriedade propriedade) {
		propriedade.getPropriedadeId();
		this.propriedade = propriedade;
		return "/propriedades/cadastrar_propriedade.xhtml?faces-redirect=true";
	}

	public void excluir(Propriedade propriedade) {

		PropriedadeDAO propriedadeDao = new PropriedadeDAO();
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage messagem = new FacesMessage("Praga removida");
		messagem.setSeverity(FacesMessage.SEVERITY_INFO);
		fc.addMessage(null, messagem);
		propriedadeDao.deletar(propriedade);
		novo();
		this.propriedades = propriedadeDao.listar("Propriedade");
	}

	public void addCulturaPropriedade(Cultura cultura) {

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

}
