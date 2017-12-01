package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="culturas_propriedades")
public class CulturaPropriedade implements Serializable{

	private static final long serialVersionUID = 5402247954583201635L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int culturaPropriedadeId;
	@Cascade({CascadeType.SAVE_UPDATE})
	@Column
	private Date dataAssociacao;

	@Column
	private Date dataInativacao;
	
	@ManyToOne
	@JoinColumn(name="culturaId")
	private Cultura cultura;
	
	@ManyToOne
	@JoinColumn(name="propriedadeId")
	private Propriedade propriedade;
	
	public CulturaPropriedade() {
		super();
	}	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Date getDataAssociacao() {
		return dataAssociacao;
	}

	public void setDataAssociacao(Date dataAssociacao) {
		this.dataAssociacao = dataAssociacao;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public Propriedade getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(Propriedade propriedade) {
		this.propriedade = propriedade;
	}
	
	public int getCulturaPropriedadeId() {
		return culturaPropriedadeId;
	}

	public void setCulturaPropriedadeId(int culturaPropriedadeId) {
		this.culturaPropriedadeId = culturaPropriedadeId;
	}

}
