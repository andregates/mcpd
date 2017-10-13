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
@Table (name="historico")

public class Historico implements Serializable {


	private static final long serialVersionUID = 7102759269082928803L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int historicoId;
	
	@Column
	private Date dataHistorico;
	
	@Column(nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="culturaId")
	private Cultura cultura;

	public Historico() {
		super();
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getHistoricoId() {
		return historicoId;
	}

	public void setHistoricoId(int historicoId) {
		this.historicoId = historicoId;
	}

}
