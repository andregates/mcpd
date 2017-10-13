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

@Entity
@Table(name="pragas")
public class Praga  implements Serializable{

	private static final long serialVersionUID = 559902083182981417L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pragaId;
	
	@Column(length=50, nullable = false)
	private String nome;
	
	@Column(length=50, nullable = false)
	private String nomeCientifico;

	@Column
	private String descricao;

	@Column
	private String acaoCombate;

	@Column
	private String escala1;

	@Column
	private String escala2;

	@Column
	private String escala3;

	@Column
	private String escala4;

	@Column
	private String escala5;
	
	@OneToMany(mappedBy="praga", fetch = FetchType.LAZY, orphanRemoval=true)
	private List<Registro> registros= new ArrayList<Registro>();
	
	@OneToMany(mappedBy="praga", fetch = FetchType.LAZY, orphanRemoval=true)
	private List<PragaCultura> pragaCultura= new ArrayList<PragaCultura>();

	
	public Praga(String nome, String nomeCientifico, String descricao, String acaoCombate, String escala1,
			String escala2, String escala3, String escala4, String escala5, List<Registro> registros,
			List<PragaCultura> pragaCultura) {
		this.nome = nome;
		this.nomeCientifico = nomeCientifico;
		this.descricao = descricao;
		this.acaoCombate = acaoCombate;
		this.escala1 = escala1;
		this.escala2 = escala2;
		this.escala3 = escala3;
		this.escala4 = escala4;
		this.escala5 = escala5;
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

	public String getEscala1() {
		return escala1;
	}

	public void setEscala1(String escala1) {
		this.escala1 = escala1;
	}

	public String getEscala2() {
		return escala2;
	}

	public void setEscala2(String escala2) {
		this.escala2 = escala2;
	}

	public String getEscala3() {
		return escala3;
	}

	public void setEscala3(String escala3) {
		this.escala3 = escala3;
	}

	public String getEscala4() {
		return escala4;
	}

	public void setEscala4(String escala4) {
		this.escala4 = escala4;
	}

	public String getEscala5() {
		return escala5;
	}

	public void setEscala5(String escala5) {
		this.escala5 = escala5;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acaoCombate == null) ? 0 : acaoCombate.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((escala1 == null) ? 0 : escala1.hashCode());
		result = prime * result + ((escala2 == null) ? 0 : escala2.hashCode());
		result = prime * result + ((escala3 == null) ? 0 : escala3.hashCode());
		result = prime * result + ((escala4 == null) ? 0 : escala4.hashCode());
		result = prime * result + ((escala5 == null) ? 0 : escala5.hashCode());
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
		if (escala1 == null) {
			if (other.escala1 != null)
				return false;
		} else if (!escala1.equals(other.escala1))
			return false;
		if (escala2 == null) {
			if (other.escala2 != null)
				return false;
		} else if (!escala2.equals(other.escala2))
			return false;
		if (escala3 == null) {
			if (other.escala3 != null)
				return false;
		} else if (!escala3.equals(other.escala3))
			return false;
		if (escala4 == null) {
			if (other.escala4 != null)
				return false;
		} else if (!escala4.equals(other.escala4))
			return false;
		if (escala5 == null) {
			if (other.escala5 != null)
				return false;
		} else if (!escala5.equals(other.escala5))
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
