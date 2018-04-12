package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "culturas")

public class Cultura implements Serializable {

	private static final long serialVersionUID = 3806417840065842124L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer culturaId;

	@Column(length = 50, nullable = false)
	private String nome;
	@Cascade({CascadeType.SAVE_UPDATE})
	@Column
	private String descricao;

	@Column
	@Temporal(TemporalType.DATE)
	private Date periodoPlantio;

	@Column
	@Temporal(TemporalType.DATE)
	private Date periodoColheita;

	@Column
	private String areaPlantio;

	@Column
	private String obs;

	@Column
	private Date dataAtivacao;

	@Column
	private Date dataInativacao;

	@OneToMany(mappedBy = "cultura", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Historico> historicoDaCultura = new ArrayList<Historico>();

	@OneToMany(mappedBy = "cultura", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Registro> registros = new ArrayList<Registro>();

	@OneToMany(mappedBy = "cultura", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PragaCultura> pragaCultura = new ArrayList<PragaCultura>();

	@OneToMany(mappedBy = "cultura", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CulturaPropriedade> culturaPropriedade = new ArrayList<CulturaPropriedade>();
	
	/**Atributo não persistido, com objetivo de medir a quantidade de vezes que foi realizado o registro
	 * de uma determinada cultura, realizado a divisão da soma das escalas pela quantidade de ocorrencias<br/>
	 * <b>(sum(escala)/count(ocorrencias))</b>*/
	@Transient
	private Long escalaPonderada;
	
	public Cultura(Integer culturaId, String nome, String descricao, Date periodoPlantio, Date periodoColheita,
			String areaPlantio, String obs, Date dataAtivacao, Date dataInativacao, List<Historico> historicoDaCultura,
			List<Registro> registros, List<PragaCultura> pragaCultura, List<CulturaPropriedade> culturaPropriedade) {
		super();
		this.culturaId = culturaId;
		this.nome = nome;
		this.descricao = descricao;
		this.periodoPlantio = periodoPlantio;
		this.periodoColheita = periodoColheita;
		this.areaPlantio = areaPlantio;
		this.obs = obs;
		this.dataAtivacao = dataAtivacao;
		this.dataInativacao = dataInativacao;
		this.historicoDaCultura = historicoDaCultura;
		this.registros = registros;
		this.pragaCultura = pragaCultura;
		this.culturaPropriedade = culturaPropriedade;
	}

	public Cultura() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getPeriodoPlantio() {
		return periodoPlantio;
	}

	public void setPeriodoPlantio(Date periodoPlantio) {
		this.periodoPlantio = periodoPlantio;
	}

	public Date getPeriodoColheita() {
		return periodoColheita;
	}

	public void setPeriodoColheita(Date periodoColheita) {
		this.periodoColheita = periodoColheita;
	}

	public String getAreaPlantio() {
		return areaPlantio;
	}

	public void setAreaPlantio(String areaPlantio) {
		this.areaPlantio = areaPlantio;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public List<Historico> getHistoricoDaCultura() {
		return historicoDaCultura;
	}

	public Integer getCulturaId() {
		return culturaId;
	}

	public void setCulturaId(Integer culturaId) {
		this.culturaId = culturaId;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public void setHistoricoDaCultura(List<Historico> historicoDaCultura) {
		this.historicoDaCultura = historicoDaCultura;
	}

	public List<PragaCultura> getPragaCultura() {
		return pragaCultura;
	}

	public void setPragaCultura(List<PragaCultura> pragaCultura) {
		this.pragaCultura = pragaCultura;
	}

	public List<CulturaPropriedade> getCulturaPropriedade() {
		return culturaPropriedade;
	}

	public void setCulturaPropriedade(List<CulturaPropriedade> culturaPropriedade) {
		this.culturaPropriedade = culturaPropriedade;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}
	
	public Long getEscalaPonderada() {
		return escalaPonderada;
	}

	public void setEscalaPonderada(Long escalaPonderada) {
		this.escalaPonderada = escalaPonderada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaPlantio == null) ? 0 : areaPlantio.hashCode());
		result = prime * result + ((culturaId == null) ? 0 : culturaId.hashCode());
		result = prime * result + ((culturaPropriedade == null) ? 0 : culturaPropriedade.hashCode());
		result = prime * result + ((dataAtivacao == null) ? 0 : dataAtivacao.hashCode());
		result = prime * result + ((dataInativacao == null) ? 0 : dataInativacao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((historicoDaCultura == null) ? 0 : historicoDaCultura.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((periodoColheita == null) ? 0 : periodoColheita.hashCode());
		result = prime * result + ((periodoPlantio == null) ? 0 : periodoPlantio.hashCode());
		result = prime * result + ((pragaCultura == null) ? 0 : pragaCultura.hashCode());
		result = prime * result + ((registros == null) ? 0 : registros.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cultura other = (Cultura) obj;
		if (areaPlantio == null) {
			if (other.areaPlantio != null)
				return false;
		} else if (!areaPlantio.equals(other.areaPlantio))
			return false;
		if (culturaId == null) {
			if (other.culturaId != null)
				return false;
		} else if (!culturaId.equals(other.culturaId))
			return false;
		if (culturaPropriedade == null) {
			if (other.culturaPropriedade != null)
				return false;
		} else if (!culturaPropriedade.equals(other.culturaPropriedade))
			return false;
		if (dataAtivacao == null) {
			if (other.dataAtivacao != null)
				return false;
		} else if (!dataAtivacao.equals(other.dataAtivacao))
			return false;
		if (dataInativacao == null) {
			if (other.dataInativacao != null)
				return false;
		} else if (!dataInativacao.equals(other.dataInativacao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (historicoDaCultura == null) {
			if (other.historicoDaCultura != null)
				return false;
		} else if (!historicoDaCultura.equals(other.historicoDaCultura))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (periodoColheita == null) {
			if (other.periodoColheita != null)
				return false;
		} else if (!periodoColheita.equals(other.periodoColheita))
			return false;
		if (periodoPlantio == null) {
			if (other.periodoPlantio != null)
				return false;
		} else if (!periodoPlantio.equals(other.periodoPlantio))
			return false;
		if (pragaCultura == null) {
			if (other.pragaCultura != null)
				return false;
		} else if (!pragaCultura.equals(other.pragaCultura))
			return false;
		if (registros == null) {
			if (other.registros != null)
				return false;
		} else if (!registros.equals(other.registros))
			return false;
		return true;
	}

}
