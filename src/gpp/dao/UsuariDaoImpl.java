package gpp.dao;

import gpp.bean.Usuari;

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
      
        	return u;
        }
        
    }

    @Autowired
    public UsuariDaoImpl(@Qualifier("dataSourceTarget")
    DataSource ds) {
        setDataSource(ds);
    }



}
