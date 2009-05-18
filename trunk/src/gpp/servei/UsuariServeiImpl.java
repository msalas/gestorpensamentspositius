package gpp.servei;

import javax.annotation.Resource;

import gpp.bean.Usuari;
import gpp.dao.UsuariDao;
import gpp.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("usuariServei")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
public class UsuariServeiImpl implements UsuariServei{

	@Resource
	private UsuariDao uDAO;

	public Usuari getUsuari(int id) {
		return uDAO.getUsuari(id);
	}
	
	public Usuari getUsuariRegistrat(int id) {
		return uDAO.getUsuariRegistrat(id);
	}

	public Usuari login(String username, String password) {
		return uDAO.login(username,password);
		
	}

	
}
