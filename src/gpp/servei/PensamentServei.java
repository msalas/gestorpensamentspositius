package gpp.servei;

import java.util.List;

import gpp.bean.Pensament;
import gpp.bean.Usuari;

public interface PensamentServei {

    Pensament getPensament(int id);
    List<Pensament> getPensamentsPopularitat();
    List<Pensament> getPensamentsAModerar();
	List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId);
	List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId);
	void marcarPensament(int pensamentId);
	void votarPensament(Usuari usuari, Pensament p);
	void crearPensament(Pensament p);
	void modificarPensament(Pensament p);
	void esborrarPensament(Pensament p);
	void moderarPensament(Pensament p);

}
