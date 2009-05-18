package gpp.dao;


import gpp.bean.Pensament;
import gpp.bean.PensamentEstat;
import gpp.bean.Usuari;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import org.springframework.stereotype.Repository;



import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.util.List;


@Repository("pensamentDao")
public class PensamentDaoImpl extends SimpleJdbcDaoSupport implements PensamentDao {

    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    public List<Pensament> getPensamentsPopularitat() {
        logger.info("Obtenim llista pensaments positius!");
        List<Pensament> pensaments = getSimpleJdbcTemplate().query(
                "select p.*,nom_usuari,u.id as user_id from pensament p, usuari u where p.estat=1 and p.autor=u.id order by vots desc, data_creacio asc", 
                new PensamentMapper());
        return pensaments;
    }

    public List<Pensament> getPensamentsAModerar() {
        logger.info("Obtenim llista pensaments a moderar!");
        List<Pensament> pensaments = getSimpleJdbcTemplate().query(
                "select p.*,nom_usuari,u.id as user_id from pensament p,usuari u where p.autor=u.id order by data_modificacio desc", 
                new PensamentMapper());
        return pensaments;
    }

    public List<Pensament> getPensamentsPopularitatPerUsuariId(int usuariId) {
        logger.info("Obtenim llista pensaments positius!");
        List<Pensament> pensaments = getSimpleJdbcTemplate().query(
                "select p.*,nom_usuari,u.id as user_id from pensament p, usuari u where p.estat=1 and p.autor="+usuariId+" and p.autor=u.id order by vots desc, data_creacio asc", 
                new PensamentMapper());
        return pensaments;
    }

    public List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId) {
        logger.info("Obtenim llista pensaments a moderar!");
        List<Pensament> pensaments = getSimpleJdbcTemplate().query(
                "select p.*,nom_usuari,u.id as user_id from pensament p,usuari u where p.autor="+usuariId+" and p.autor=u.id order by data_modificacio desc", 
                new PensamentMapper());
        return pensaments;
    }

    /*
    public void savePensament(Pensament p) {
        logger.info("Saving product: " + p.getDescripcio());
        int count = getSimpleJdbcTemplate().update(
            "update products set description = :description where id = :id",
            new MapSqlParameterSource().addValue("description", p.getDescripcio())
                .addValue("id", p.getId()));
        logger.info("Rows affected: " + count);
    }
    */
    
    private static class PensamentMapper implements ParameterizedRowMapper<Pensament> {

        public Pensament mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Pensament p = new Pensament();
        	
        	p.setId(rs.getInt("id"));
            p.setTitol(rs.getString("titol"));
            p.setDataCreacio(rs.getTimestamp("data_creacio"));
            p.setDataModificacio(rs.getTimestamp("data_modificacio"));
            p.setDataPublicacio(rs.getTimestamp("data_publicacio"));
            p.setEstat(PensamentEstat.valueOf(rs.getInt("estat")));
            p.setDescripcio(rs.getString("descripcio"));
            p.setVots(rs.getInt("vots"));
            
            Usuari creadorPensament = new Usuari();
            creadorPensament.setNomUsuari(rs.getString("nom_usuari"));
            creadorPensament.setId(rs.getInt("user_id"));
            p.setAutor(creadorPensament);
            
            return p;
        }
        
    }

    @Autowired
    public PensamentDaoImpl(@Qualifier("dataSourceTarget")
    DataSource ds) {
        setDataSource(ds);
    }


	public void marcarPensament(int pensamentId) {
		getSimpleJdbcTemplate().update("update pensament set estat=3 where id="+pensamentId);
		
	}


	public void votarPensament(Usuari usuari, Pensament p) {
		Integer usuariId = usuari!=null?usuari.getId():null;
		getSimpleJdbcTemplate().update("insert into pensament_vot(votant,pensament) values("+usuariId+","+p.getId()+")");
		
	}


	public Pensament getPensament(int id) {
	
	        logger.info("Obtenim pensament"+id);
	        Pensament pensament = null;
	        
	        try{
	        pensament =  getSimpleJdbcTemplate().queryForObject(
	                "select p.*,nom_usuari,u.id as user_id from pensament p,usuari u where p.autor=u.id and p.id="+id, 
	                new PensamentMapper());
	        
	        }catch (Exception e) {
				
			}
	        return pensament;
	}


}
