package gpp.servei;

import gpp.bean.Usuari;

public interface UsuariServei {

	Usuari getUsuari(int id);
	Usuari getUsuariRegistrat(int id);
	Usuari login(String username, String password);
	void modificarUsuari(Usuari u);
}
