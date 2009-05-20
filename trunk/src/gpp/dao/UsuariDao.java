package gpp.dao;

import gpp.bean.Usuari;

public interface UsuariDao {

	Usuari getUsuari(int id);
	Usuari getUsuariRegistrat(int id);
	Usuari login(String username, String password);
	void modificarUsuari(Usuari u);
}
