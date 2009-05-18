package gpp.dao;

import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import org.springframework.stereotype.Repository;



import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;



@Repository("usuariDao")
public class UsuariDaoImpl extends SimpleJdbcDaoSupport implements UsuariDao {

    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());



    public Usuari getUsuari(int id) {
        logger.info("Obtenim usuari!");
        Usuari usuari =  getSimpleJdbcTemplate().queryForObject("select u.* from usuari u where id="+id,new UsuariMapper());
        return usuari;
    }
    public Usuari getUsuariRegistrat(int id) {
        logger.info("Obtenim usuari registrat!");
        Usuari usuari = null;
        try{
        usuari =  getSimpleJdbcTemplate().queryForObject("select u.* from usuari u, usuari_grup ug where u.id="+id+" and tipus=ug.id and tipus=1" ,new UsuariMapper());
        }catch (Exception e) {
			// No fem res, retornara null
		}
        return usuari;
    }
    
   

    private static class UsuariMapper implements ParameterizedRowMapper<Usuari> {

        public Usuari mapRow(ResultSet rs, int rowNum) throws SQLException {

        	Usuari u = new Usuari();
        	
        	u.setId(rs.getInt("id"));
        	u.setNomUsuari(rs.getString("nom_usuari"));
        	u.setNom(rs.getString("nom"));
        	u.setCognoms(rs.getString("cognoms"));
        	u.setContrassenya(rs.getString("contrassenya"));
        	u.setEdat(rs.getInt("edat"));
        	u.setGrup(UsuariGrup.valueOf(rs.getInt("tipus")));
        	u.setEmail(rs.getString("email"));
      
        	return u;
        }
        
    }

    @Autowired
    public UsuariDaoImpl(@Qualifier("dataSourceTarget")
    DataSource ds) {
        setDataSource(ds);
    }

	public Usuari login(String username, String password) {
		 Usuari usuari = null;
	        try{
	        	usuari =  getSimpleJdbcTemplate().queryForObject("select u.* from usuari u where u.nom_usuari='"+username+"' and contrassenya='"+password+"'" ,new UsuariMapper());
	        }catch (Exception e) {
				e.printStackTrace();
			}
	        return usuari;
	}



}
