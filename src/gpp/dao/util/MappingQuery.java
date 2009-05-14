package gpp.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlOperation;

/**
 * Helper class for creating mapped queries
 * 
 */
@SuppressWarnings("unchecked")
public abstract class MappingQuery<T> extends MappingSqlQuery {

    @Override
    protected T mapRow(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }

    @Override
    public List<T> execute() throws DataAccessException {
        return super.execute();
    }

    @Override
    public List<T> execute(int p1, int p2, Map context) throws DataAccessException {
        return super.execute(p1, p2, context);
    }

    @Override
    public List<T> execute(int p1, int p2) throws DataAccessException {
        return super.execute(p1, p2);
    }

    @Override
    public List<T> execute(int p1, Map context) throws DataAccessException {
        return super.execute(p1, context);
    }

    @Override
    public List<T> execute(int p1) throws DataAccessException {
        return super.execute(p1);
    }

    @Override
    public List<T> execute(long p1, Map context) throws DataAccessException {
        return super.execute(p1, context);
    }

    @Override
    public List<T> execute(long p1) throws DataAccessException {
        return super.execute(p1);
    }

    @Override
    public List<T> execute(Map context) throws DataAccessException {
        return super.execute(context);
    }

    @Override
    public List<T> execute(Object[] params, Map context)
            throws DataAccessException {
        return super.execute(params, context);
    }

    @Override
    public List<T> execute(Object[] params) throws DataAccessException {
        return super.execute(params);
    }

    @Override
    public List<T> execute(String p1, Map context) throws DataAccessException {
        return super.execute(p1, context);
    }

    @Override
    public List<T> execute(String p1) throws DataAccessException {
        return super.execute(p1);
    }

    @Override
    public List<T> executeByNamedParam(Map paramMap, Map context)
            throws DataAccessException {
        return super.executeByNamedParam(paramMap, context);
    }

    @Override
    public List<T> executeByNamedParam(Map paramMap) throws DataAccessException {
        return super.executeByNamedParam(paramMap);
    }

    @Override
    public T findObject(int p1, int p2, Map context)
            throws DataAccessException {
        return (T) super.findObject(p1, p2, context);
    }

    @Override
    public T findObject(int p1, int p2) throws DataAccessException {
        return (T) super.findObject(p1, p2);
    }

    @Override
    public T findObject(int p1, Map context) throws DataAccessException {
        return (T) super.findObject(p1, context);
    }

    @Override
    public T findObject(int p1) throws DataAccessException {
        return (T) super.findObject(p1);
    }

    @Override
    public T findObject(long p1, Map context) throws DataAccessException {
        return (T) super.findObject(p1, context);
    }

    @Override
    public T findObject(long p1) throws DataAccessException {
        return (T) super.findObject(p1);
    }

    @Override
    public T findObject(Object[] params, Map context)
            throws DataAccessException {
        return (T) super.findObject(params, context);
    }

    @Override
    public T findObject(Object[] params) throws DataAccessException {
        return (T) super.findObject(params);
    }

    @Override
    public T findObject(String p1, Map context) throws DataAccessException {
        return (T) super.findObject(p1, context);
    }

    @Override
    public T findObject(String p1) throws DataAccessException {
        return (T) super.findObject(p1);
    }

    @Override
    public T findObjectByNamedParam(Map paramMap, Map context)
            throws DataAccessException {
        return (T) super.findObjectByNamedParam(paramMap, context);
    }

    @Override
    public T findObjectByNamedParam(Map paramMap)
            throws DataAccessException {
        return (T) super.findObjectByNamedParam(paramMap);
    }

    public MappingQuery(DataSource ds, String query, String condition,
            int... paramTypes) {
        this(ds, query + (condition != null ? " " + condition : ""), paramTypes);
    }

    public MappingQuery(DataSource source, String query, int paramTypes[]) {
        assert source != null : "datasource==null";
        setDataSource(source);
        setQuery(query, paramTypes);
        compile();
    }

    protected void setQuery(String sqlQuery, int[] sqlTypes) {
        setSql(sqlQuery);
        SqlOperation sqlOp = this;
        declareParameters(sqlOp, sqlTypes);
        compile();
    }

    /**
     * @param sqlOp
     * @param sqlTypes
     */
    public static void declareParameters(SqlOperation sqlOp, int[] sqlTypes) {
        SqlParameter[] paramTypes = createParameters(sqlTypes);
        if (paramTypes != null) {
            for (int i = 0; i < paramTypes.length; i++) {
                sqlOp.declareParameter(paramTypes[i]);
            }
        }
    }

    /**
     * @param paramTypes
     */
    private static SqlParameter[] createParameters(int[] paramTypes) {

        if (paramTypes == null) {
            return new SqlParameter[0];
        }
        SqlParameter parameters[] = new SqlParameter[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            parameters[i] = new SqlParameter(paramTypes[i]);
        }
        return parameters;
    }

    /**
     * @param rs
     * @param columnName
     * @return null if column was null or integer value
     * @throws SQLException
     */
    protected static Integer getInteger(ResultSet rs, String columnName)
            throws SQLException {

        Integer value = rs.getInt(columnName);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }
    
    
    
}