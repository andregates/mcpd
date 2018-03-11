package controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.omnifaces.util.Messages;
import dao.HistoricoDAO;
import models.Cultura;
import models.Historico;

@SuppressWarnings("serial")
@ManagedBean(name = "historicoBean")
@ViewScoped
public class HistoricoBean implements Serializable {

	private Historico historico = new Historico();
	private List<Historico> historicos;
	private Cultura cultura = new Cultura();

	public void novo() {
		historico = new Historico();
		historicos = new ArrayList<Historico>();
		cultura = new Cultura();
	}

	public String salvar() {
		try {
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.salvar(historico);

			novo();
			// Messages.addGlobalInfo("Cadastro realizado com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar");
			erro.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cadastro realizado com sucesso!"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		novo();
		return "/culturas/historicos.xhtml?faces-redirect=true";

	}

	@PostConstruct // metodo construtor para chamar automaticamente o metodo quando o ManagedBean
					// for criado
	public void listar() {
		try {

			historico.setDataHistorico(new Date());
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) ec.getSession(false);
			this.cultura = (Cultura) session.getAttribute("cultura");
			historico.setCultura(this.cultura);

			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicos = historicoDAO.listarCriterio("cultura.culturaId", this.cultura.getCulturaId(), "dataHistorico");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar");
			erro.printStackTrace();
		}
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

	public void editar(ActionEvent evento) {
		historico = (Historico) evento.getComponent().getAttributes().get("historicoSelecionado");

	}

	public void excluir(Historico h) {

		try {
			// pega o coponente do evento, pega os atributos do componente, pega pelo nome
			// do aributo.
			// historico = (Historico)
			// evento.getComponent().getAttributes().get("historicoSelecionado");
			historico = h;
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.deletar(historico);
			historicos = historicoDAO.listarCriterio("cultura.culturaId", this.cultura.getCulturaId(), "dataHistorico");
			Messages.addGlobalInfo("Cadastro removido com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar remover");
			erro.printStackTrace();
		}
	}

	public String atualizar(Historico hist) {
		hist.getHistoricoId();
		this.historico = hist;
		return "/culturas/cadastrar_historico.xhtml?faces-redirect=true";
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

}
