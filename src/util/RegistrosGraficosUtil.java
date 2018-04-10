package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import dao.CulturaDAO;
import dao.RegistroDAO;
import models.Cultura;
import models.Registro;

@ManagedBean
@SessionScoped
public class RegistrosGraficosUtil implements Serializable {

	private LineChartModel registrosGraficos;
	private Date dataInicial;
	private Date dataFinal;
	private Registro registro;
	private List<Cultura> listaCulturas;
	private ArrayList<Cultura> listaCulturasSelecionadas;

	FacesContext fc = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	@PostConstruct
	public void init() {

		CulturaDAO dao = new CulturaDAO();
		listaCulturas = dao.listar("Cultura");
		listaCulturasSelecionadas = new ArrayList<>();
		createLineModels();
	}

	public LineChartModel getRegistrosGraficos() {
		return registrosGraficos;
	}

	private void createLineModels() {
		RegistroDAO registroDao = new RegistroDAO();

		if (!listaCulturasSelecionadas.isEmpty()) {
			List<List<Registro>> registroList = registroDao.gerarGraficoProjetado(dataInicial, dataFinal,
					listaCulturasSelecionadas);
			registrosGraficos = initCategoryModel(registroList);
			registrosGraficos.setTitle("Registros");
			// registrosGraficos.setLegendPosition("e");
			registrosGraficos.setShowPointLabels(true);
			registrosGraficos.getAxes().put(AxisType.X, new CategoryAxis("Período"));
			Axis yAxis = registrosGraficos.getAxis(AxisType.Y);
			yAxis.setLabel("Longitude");
			yAxis.setMin(0);
			yAxis.setMax(20);
		} else {
			registrosGraficos = null;
		}
	}

	private LineChartModel initCategoryModel(List<List<Registro>> registroList) {

		LineChartModel model = new LineChartModel();

		LineChartSeries culturas = new LineChartSeries();
		for (int i = 0; i < registroList.size(); i++) {

			List<Registro> registros = registroList.get(i);
			Cultura cultura = (Cultura) registros.get(i).getCultura();
			culturas.setLabel(cultura.getNome());

			// String[] data = registro.getDataRegistro().toString().split("-");
			for (int j = 0; j < registroList.get(i).size(); j++)
				culturas.set(registros.get(i).getDataRegistro().toString(), registros.get(i).getEscala());

			model.addSeries(culturas);
			culturas = new LineChartSeries();
		}

		return model;
	}

	public String mostrarGrafico() {
		createLineModels();
		return "/graficos/graficos_ocorrencias.xhtml?faces-redirect=true";
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<Cultura> getListaCulturas() {
		return listaCulturas;
	}

	public void setListaCulturas(ArrayList<Cultura> listaCulturas) {
		this.listaCulturas = listaCulturas;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public ArrayList<Cultura> getListaCulturasSelecionadas() {
		return listaCulturasSelecionadas;
	}

	public void setListaCulturasSelecionadas(ArrayList<Cultura> listaCulturasSelecionadas) {
		this.listaCulturasSelecionadas = listaCulturasSelecionadas;
	}

}
