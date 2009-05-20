package gpp.servei;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;


import gpp.bean.Pensament;
import gpp.bean.Usuari;
import gpp.dao.PensamentDao;
import gpp.exception.BusinessException;



@Service("pensamentServei")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
public class PensamentServeiImpl implements PensamentServei {


	
	@Resource
	private PensamentDao pDAO;
	
    
    public List<Pensament> getPensamentsPopularitat() {
        return pDAO.getPensamentsPopularitat();
    }

    public List<Pensament> getPensamentsAModerar() {
        return pDAO.getPensamentsAModerar();
    }

	public Pensament getPensament(int id) {
		return pDAO.getPensament(id);
	}


	public List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId) {
		return pDAO.getPensamentsAModerarPerUsuariId(usuariId);
	}

	public List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId) {
		return pDAO.getPensamentsPopularitatPerUsuariId(usuariId);
	}


	public void marcarPensament(int pensamentId) {
		pDAO.marcarPensament(pensamentId);
		
	}




	public void votarPensament(Usuari usuari, Pensament p) {
		pDAO.votarPensament(usuari,p);
	}




	public void crearPensament(Pensament p) {
		pDAO.crearPensament(p);
		
	}



	public void modificarPensament(Pensament p) {
		pDAO.modificarPensament(p);		
	}

	public void esborrarPensament(Pensament p) {
		pDAO.esborrarPensament(p);		
	}

	
	public void moderarPensament(Pensament p) {
		pDAO.moderarPensament(p);
		
	}

}
