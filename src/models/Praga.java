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
@Table(name = "pragas")
public class Praga implements Serializable {

	private static final long serialVersionUID = 559902083182981417L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pragaId;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 50, nullable = false)
	private String nomeCientifico;

	@Column
	private String descricao;

	@Column
	private String acaoCombate;

	@Column
	private String escala;

	@Transient
	private List<String> listaPath;

	@OneToMany(mappedBy = "praga", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Registro> registros = new ArrayList<Registro>();

	@OneToMany(mappedBy = "praga", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PragaCultura> pragaCultura = new ArrayList<PragaCultura>();

	public Praga(String nome, String nomeCientifico, String descricao, String acaoCombate, String escala,
			List<Registro> registros, List<PragaCultura> pragaCultura) {
		this.nome = nome;
		this.nomeCientifico = nomeCientifico;
		this.descricao = descricao;
		this.acaoCombate = acaoCombate;
		this.escala = escala;
		this.registros = registros;
		this.pragaCultura = pragaCultura;
	}

	public Praga() {
		super();
	}

	public int getPragaId() {
		return pragaId;
	}

	public void setPragaId(int pragaId) {
		this.pragaId = pragaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCientifico() {
		return nomeCientifico;
	}

	public void setNomeCientifico(String nomeCientifico) {
		this.nomeCientifico = nomeCientifico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAcaoCombate() {
		return acaoCombate;
	}

	public void setAcaoCombate(String acaoCombate) {
		this.acaoCombate = acaoCombate;
	}

	public String getEscala() {
		return escala;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public List<PragaCultura> getPragaCultura() {
		return pragaCultura;
	}

	public void setPragaCultura(List<PragaCultura> pragaCultura) {
		this.pragaCultura = pragaCultura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public List<String> getListaPath() {
		return listaPath;
	}

	public void setListaPath(List<String> listaPath) {
		this.listaPath = listaPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acaoCombate == null) ? 0 : acaoCombate.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((escala == null) ? 0 : escala.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeCientifico == null) ? 0 : nomeCientifico.hashCode());
		result = prime * result + ((pragaCultura == null) ? 0 : pragaCultura.hashCode());
		result = prime * result + pragaId;
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
		Praga other = (Praga) obj;
		if (acaoCombate == null) {
			if (other.acaoCombate != null)
				return false;
		} else if (!acaoCombate.equals(other.acaoCombate))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (escala == null) {
			if (other.escala != null)
				return false;
		} else if (!escala.equals(other.escala))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeCientifico == null) {
			if (other.nomeCientifico != null)
				return false;
		} else if (!nomeCientifico.equals(other.nomeCientifico))
			return false;
		if (pragaCultura == null) {
			if (other.pragaCultura != null)
				return false;
		} else if (!pragaCultura.equals(other.pragaCultura))
			return false;
		if (pragaId != other.pragaId)
			return false;
		if (registros == null) {
			if (other.registros != null)
				return false;
		} else if (!registros.equals(other.registros))
			return false;
		return true;
	}

}
