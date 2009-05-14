package gpp.bean;

import java.sql.Timestamp;

public class Pensament {

	Integer id;
	String titol;
	String descripcio;
	Timestamp dataCreacio = new Timestamp(System.currentTimeMillis());
	Timestamp dataPublicacio;
	Timestamp dataModificacio;
	PensamentEstat estat;
	Usuari autor;
	Comentari comentari;
	Integer vots;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitol() {
		return titol;
	}
	public void setTitol(String titol) {
		this.titol = titol;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	public Timestamp getDataCreacio() {
		return dataCreacio;
	}
	public void setDataCreacio(Timestamp dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
	public Timestamp getDataPublicacio() {
		return dataPublicacio;
	}
	public void setDataPublicacio(Timestamp dataPublicacio) {
		this.dataPublicacio = dataPublicacio;
	}
	public Timestamp getDataModificacio() {
		return dataModificacio;
	}
	public void setDataModificacio(Timestamp dataModificacio) {
		this.dataModificacio = dataModificacio;
	}
	public PensamentEstat getEstat() {
		return estat;
	}
	public void setEstat(PensamentEstat estat) {
		this.estat = estat;
	}
	public Usuari getAutor() {
		return autor;
	}
	public void setAutor(Usuari autor) {
		this.autor = autor;
	}
	public Comentari getComentari() {
		return comentari;
	}
	public void setComentari(Comentari comentari) {
		this.comentari = comentari;
	}
	public Integer getVots() {
		return vots;
	}
	public void setVots(Integer vots) {
		this.vots = vots;
	}

	
}
