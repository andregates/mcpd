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

@Entity
@Table(name="pragas_culturas")
public class PragaCultura  implements Serializable {

	private static final long serialVersionUID = 2539083798634971556L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pragaCulturaId;
	
	@Column
	private Date dataAssociacao;

	@Column
	private Date dataInativacao;
	
	@ManyToOne
	@JoinColumn(name="culturaId")
	private Cultura cultura;
	
	@ManyToOne
	@JoinColumn(name="pragaId")
	private Praga praga;

	public PragaCultura() {
		super();
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

	public Praga getPraga() {
		return praga;
	}

	public void setPraga(Praga praga) {
		this.praga = praga;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPragaCulturaId() {
		return pragaCulturaId;
	}

	public void setPragaCulturaId(int pragaCulturaId) {
		this.pragaCulturaId = pragaCulturaId;
	}
	

}
