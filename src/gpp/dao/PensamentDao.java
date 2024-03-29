package gpp.dao;

import java.util.List;

import gpp.bean.Comentari;
import gpp.bean.Pensament;
import gpp.bean.Usuari;

public interface PensamentDao {

	List<Pensament> getPensamentsPopularitat();
	List<Pensament> getPensamentsAModerar();
	List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId);
	List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId);
	void marcarPensament(int pensamentId);
	void votarPensament(Usuari usuari, Pensament p);
	Pensament getPensament(int id);
	void crearPensament(Pensament p);
	void modificarPensament(Pensament p);
	void esborrarPensament(Pensament p);
	void moderarPensament(Pensament p);
	Comentari getComentari(int id);
    
}
