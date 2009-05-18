package gpp.bean;

public class Usuari {

	Integer id;
	String nomUsuari;
	String contrassenya;
	String email;
	Integer edat;
	String nom;
	String cognoms;
	UsuariGrup grup;
	
	public UsuariGrup getGrup() {
		return grup;
	}
	public void setGrup(UsuariGrup grup) {
		this.grup = grup;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomUsuari() {
		return nomUsuari;
	}
	public void setNomUsuari(String nomUsuari) {
		this.nomUsuari = nomUsuari;
	}
	public String getContrassenya() {
		return contrassenya;
	}
	public void setContrassenya(String contrassenya) {
		this.contrassenya = contrassenya;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getEdat() {
		return edat;
	}
	public void setEdat(Integer edat) {
		this.edat = edat;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCognoms() {
		return cognoms;
	}
	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}
	
}
