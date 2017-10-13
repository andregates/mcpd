package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "propriedades")
public class Propriedade implements Serializable {

	private static final long serialVersionUID = -8183242639187751808L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int propriedadeId;

	@Column(length = 50, nullable = false)
	private String nomePropriedade;

	@Column
	private String localidade;

	@Column
	private String cidade;

	@Column
	private String uf;

	@Column
	private String pais;

	@Column(length = 50, nullable = false)
	private String nomeResponsavel;

	@Column
	private String telefoneResponsavel;

	@Column
	private String nomeProprietario;

	@Column
	private String cpf;

	@Column
	private String telefoneProprietario;

	@Column
	private Double area;

	@Column
	private Double latitude;

	@Column
	private Double longitude;
	
	@Transient
	private Cultura cultura;

	@OneToMany(mappedBy = "propriedade", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Registro> registros = new ArrayList<Registro>();

	@OneToMany(mappedBy = "propriedade", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CulturaPropriedade> culturaPropriedade = new ArrayList<CulturaPropriedade>();

	public Propriedade(String nomePropriedade, String localidade, String cidade, String uf, String pais,
			String nomeResponsavel, String telefoneResponsavel, String nomeProprietario, String cpf,
			String telefoneProprietario, Double area, Double latitude, Double longitude, List<Registro> registros,
			List<CulturaPropriedade> culturaPropriedade) {
		this.nomePropriedade = nomePropriedade;
		this.localidade = localidade;
		this.cidade = cidade;
		this.uf = uf;
		this.pais = pais;
		this.nomeResponsavel = nomeResponsavel;
		this.telefoneResponsavel = telefoneResponsavel;
		this.nomeProprietario = nomeProprietario;
		this.cpf = cpf;
		this.telefoneProprietario = telefoneProprietario;
		this.area = area;
		this.latitude = latitude;
		this.longitude = longitude;
		this.registros = registros;
		this.culturaPropriedade = culturaPropriedade;
	}

	public Propriedade() {
		super();
	}

	public String getNomePropriedade() {
		return nomePropriedade;
	}

	public void setNomePropriedade(String nomePropriedade) {
		this.nomePropriedade = nomePropriedade;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getNomeProprietario() {
		return nomeProprietario;
	}

	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefoneProprietario() {
		return telefoneProprietario;
	}

	public void setTelefoneProprietario(String telefoneProprietario) {
		this.telefoneProprietario = telefoneProprietario;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public List<CulturaPropriedade> getCulturaPropriedade() {
		return culturaPropriedade;
	}

	public void setCulturaPropriedade(List<CulturaPropriedade> culturaPropriedade) {
		this.culturaPropriedade = culturaPropriedade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPropriedadeId() {
		return propriedadeId;
	}

	public void setPropriedadeId(int propriedadeId) {
		this.propriedadeId = propriedadeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((culturaPropriedade == null) ? 0 : culturaPropriedade.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((localidade == null) ? 0 : localidade.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((nomePropriedade == null) ? 0 : nomePropriedade.hashCode());
		result = prime * result + ((nomeProprietario == null) ? 0 : nomeProprietario.hashCode());
		result = prime * result + ((nomeResponsavel == null) ? 0 : nomeResponsavel.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + propriedadeId;
		result = prime * result + ((registros == null) ? 0 : registros.hashCode());
		result = prime * result + ((telefoneProprietario == null) ? 0 : telefoneProprietario.hashCode());
		result = prime * result + ((telefoneResponsavel == null) ? 0 : telefoneResponsavel.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		Propriedade other = (Propriedade) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (culturaPropriedade == null) {
			if (other.culturaPropriedade != null)
				return false;
		} else if (!culturaPropriedade.equals(other.culturaPropriedade))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (localidade == null) {
			if (other.localidade != null)
				return false;
		} else if (!localidade.equals(other.localidade))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (nomePropriedade == null) {
			if (other.nomePropriedade != null)
				return false;
		} else if (!nomePropriedade.equals(other.nomePropriedade))
			return false;
		if (nomeProprietario == null) {
			if (other.nomeProprietario != null)
				return false;
		} else if (!nomeProprietario.equals(other.nomeProprietario))
			return false;
		if (nomeResponsavel == null) {
			if (other.nomeResponsavel != null)
				return false;
		} else if (!nomeResponsavel.equals(other.nomeResponsavel))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (propriedadeId != other.propriedadeId)
			return false;
		if (registros == null) {
			if (other.registros != null)
				return false;
		} else if (!registros.equals(other.registros))
			return false;
		if (telefoneProprietario == null) {
			if (other.telefoneProprietario != null)
				return false;
		} else if (!telefoneProprietario.equals(other.telefoneProprietario))
			return false;
		if (telefoneResponsavel == null) {
			if (other.telefoneResponsavel != null)
				return false;
		} else if (!telefoneResponsavel.equals(other.telefoneResponsavel))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

}
