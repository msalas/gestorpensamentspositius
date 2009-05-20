package gpp.dao;


import gpp.bean.Comentari;
import gpp.bean.Pensament;
import gpp.bean.PensamentEstat;
import gpp.bean.Usuari;
import gpp.bean.UsuariGrup;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.SqlFunction;

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
                "select p.*,nom_usuari,u.id as user_id from pensament p, usuari u where p.estat=1 and p.autor=? and p.autor=u.id order by vots desc, data_creacio asc", 
                new PensamentMapper(),usuariId);
        return pensaments;
    }

    public List<Pensament> getPensamentsAModerarPerUsuariId(int usuariId) {
        logger.info("Obtenim llista pensaments a moderar!");
        List<Pensament> pensaments = getSimpleJdbcTemplate().query(
                "select p.*,nom_usuari,u.id as user_id from pensament p,usuari u where p.autor=? and p.autor=u.id order by data_modificacio desc", 
                new PensamentMapper(),usuariId);
        return pensaments;
    }


    
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

    private static class ComentariMapper implements ParameterizedRowMapper<Comentari> {

        public Comentari mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        	Comentari c = new Comentari();
        	
        	c.setId(rs.getInt("id"));
            c.setDescripcio(rs.getString("text"));
            
            Usuari autorComentari = new Usuari();
            autorComentari.setNomUsuari(rs.getString("nom_usuari"));
            autorComentari.setId(rs.getInt("user_id"));
            c.setAutor(autorComentari);
            
            return c;
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
		getSimpleJdbcTemplate().update("insert into pensament_vot(votant,pensament) values(?,?)",new Object[] {usuariId,p.getId()});
		
	}


	public Pensament getPensament(int id) {
	
	        logger.info("Obtenim pensament"+id);
	        Pensament pensament = null;
	        
	        try{
	        pensament =  getSimpleJdbcTemplate().queryForObject(
	                "select p.*,nom_usuari,u.id as user_id from pensament p,usuari u where p.autor=u.id and p.id=?", 
	                new PensamentMapper(),id);
	        
	        }catch (Exception e) {
				
			}
	        return pensament;
	}

	
	public void crearPensament(Pensament p) {
		getSimpleJdbcTemplate().update("INSERT INTO pensament(titol, descripcio, autor, estat, data_publicacio, data_modificacio) VALUES (?,?,?,?,now(),now())",new Object[] {p.getTitol(),p.getDescripcio(),p.getAutor().getId(),p.getEstat().getId()});
	}

	public void esborrarPensament(Pensament p) {
		getSimpleJdbcTemplate().update("delete from pensament where id=?",p.getId());
	}

	public void modificarPensament(Pensament p) {
		getSimpleJdbcTemplate().update("update pensament set titol=?, descripcio=?, estat=1, data_modificacio=now(),data_publicacio=now() where id=?",new Object[] {p.getTitol(),p.getDescripcio(),p.getId()});
	}


	public void moderarPensament(Pensament p) {
		
		Comentari aux = p.getComentari();
		int idSeq;
		
		if(aux.getId()==null){
			
			idSeq =  getSimpleJdbcTemplate().queryForInt("SELECT nextVal('pensament_comentari_id_seq')");
			 getSimpleJdbcTemplate().update("insert into pensament_comentari(id,text,autor) values (?,?,?)",new Object[]{idSeq,aux.getDescripcio(),aux.getAutor().getId()});
		
		}else{
			getSimpleJdbcTemplate().update("update pensament_comentari set text=?, autor=? where id=?",new Object[] {aux.getDescripcio(),aux.getAutor().getId(),aux.getId()});
			idSeq= aux.getId();
		}
		
		if(p.getEstat()==PensamentEstat.POSITIU)
			getSimpleJdbcTemplate().update("update pensament set estat=1, data_publicacio=now(), set comentari=? where id=?",new Object[] {idSeq,p.getId()});
		else getSimpleJdbcTemplate().update("update pensament set estat=3,comentari=? where id=?",new Object[] {idSeq,p.getId()});
		
		
	}


	public Comentari getComentari(int id) {
		logger.info("Obtenim comentari de pensament "+id);
        Comentari c = null;
        
        try{
        c =  getSimpleJdbcTemplate().queryForObject(
                "select c.id, c.text ,nom_usuari,u.id as user_id from pensament_comentari c,usuari u, pensament p where c.autor=u.id and c.id=p.comentari and p.id=?", 
                new ComentariMapper(),id);
        
        }catch (Exception e) {
			
		}
        return c;
	}


}
