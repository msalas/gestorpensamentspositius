package gpp.dao;

import java.util.List;

import gpp.bean.Pensament;

public interface PensamentDao {

	List<Pensament> getPensamentsPopularitat();
	List<Pensament> getPensamentsAModerar();
	List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId);
	List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId);
    
}
