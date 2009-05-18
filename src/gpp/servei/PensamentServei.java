package gpp.servei;

import java.util.List;

import gpp.bean.Pensament;
import gpp.bean.Usuari;

public interface PensamentServei {

    void addPensament(Pensament pensament);
    Pensament getPensament(int id);
    List<Pensament> getPensamentsPopularitat();
    List<Pensament> getPensamentsAModerar();
	List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId);
	List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId);
	void marcarPensament(int pensamentId);
	void votarPensament(Usuari usuari, Pensament p);

}
