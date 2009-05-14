package gpp.bean;

public class Comentari {

	Integer id;
	Usuari autor;
	String descripcio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Usuari getAutor() {
		return autor;
	}
	public void setAutor(Usuari autor) {
		this.autor = autor;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

}
