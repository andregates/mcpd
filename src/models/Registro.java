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
@Table(name="registros")
public class Registro implements Serializable {

	private static final long serialVersionUID = 8284820065630536253L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int registroId;
	
	@Column
	private Date dataRegistro;
	
	@Column
	private double latitude;
	
	@Column
	private double longitude;
	
	@Column
	private int escala;
	
	@Column
	private boolean tratamento;
	
	@Column
	private Date dataTratamento;
	
	@Column
	private String tipo;
	
	@Column
	private String obs;
	
	@ManyToOne
	@JoinColumn(name="usuarioId")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="pragaId")
	private Praga praga;
	
	@ManyToOne
	@JoinColumn(name="culturaId")
	private Cultura cultura;
	
	@ManyToOne
	@JoinColumn(name="propriedadeId")
	private Propriedade propriedade;

	public Registro() {
		super();
	}

	public int getRegistroId() {
		return registroId;
	}

	public void setRegistroId(int registroId) {
		this.registroId = registroId;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

	public boolean isTratamento() {
		return tratamento;
	}

	public void setTratamento(boolean tratamento) {
		this.tratamento = tratamento;
	}

	public Date getDataTratamento() {
		return dataTratamento;
	}

	public void setDataTratamento(Date dataTratamento) {
		this.dataTratamento = dataTratamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Praga getPraga() {
		return praga;
	}

	public void setPraga(Praga praga) {
		this.praga = praga;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setKey(String key) {
		// TODO Auto-generated method stub
		
	}
	
	
}
