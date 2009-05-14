package gpp.dao;

import gpp.bean.Pensament;

import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Repository;


import gpp.dao.util.MappingQuery;

import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.util.List;


@Repository("pensamentDao")
public class PensamentDaoImpl extends SimpleJdbcDaoSupport implements PensamentDao {

    private static final String GENERAL_QUERY = "SELECT p.*, "
            + "       get_stars_for_ambassadorreport( " + "         report.id "
            + "  FROM pensament p";
    
    private static final String STATUS_CONDITION = " report.status = ?";

    private static final String CAMPAIGN_CONDITION = " AND report.campaignid = ? ";

    private static final String NULL_CAMPAIGN_CONDITION = " AND report.campaignid IS NULL";

    private static final String COUNTRY_CONDITION = " AND report.creatorid "
            + "IN (" + "   SELECT id FROM ambassador "
            + "     WHERE cityid IN ("
            + "     SELECT id FROM city WHERE countryid = ?))";

    private static final String TAG_CONDITION = " AND id IN ("
            + "SELECT reportid FROM ambassadorreport_tag WHERE tagid IN ("
            + "SELECT id FROM tag WHERE name = ?)) ";

    private static final String ID_CONDITION = " WHERE report.id = ?";

    private static final String REPORT_CONDITION = " report.title ILIKE ? OR report.text ILIKE ?";

    private static final String TAG_NAME_CONDITION = " OR EXISTS ("
            + "  SELECT 0 FROM tag t, ambassadorreport_tag rt"
            + "    WHERE t.id = rt.tagid"
            + "        AND rt.reportid = report.id"
            + "        AND t.name ILIKE ?)";

    private static final String AMBASSADOR_RELATED_CONDITION = " OR EXISTS ("
            + "    SELECT 0 FROM ambassador a"
            + "        WHERE a.id = report.creatorid"
            + "        AND ("
            + "            EXISTS ("
            + "                SELECT 0 FROM users u "
            + "                    WHERE u.u_id = a.user_id "
            + "                        AND  u.u_username ILIKE ?"
            + "            ) OR a.permissiontoshowcity = true"
            + "                AND EXISTS ("
            + "                    SELECT 0 FROM city c"
            + "                        WHERE c.id = a.cityid"
            + "                            AND ("
            + "                                c.name ILIKE ?"
            + "                                OR EXISTS ("
            + "                                    SELECT 0 FROM country cy"
            + "                                        WHERE cy.ct_id = c.countryid"
            + "                                            AND cy.ct_name ILIKE ?"
            + "                                )"
            + "                            )" + "                 )"
            + "        )" + ")";
    private static final String CAMPAIGN_NAME_CONDITION = " OR EXISTS ("
            + "    SELECT 0 FROM campaign c"
            + "        WHERE c.id = report.campaignid"
            + "            AND c.name ILIKE ?)";

    private static final String AMBASSADOR_ID_CONDITION = " creatorid = ?";
        
    private SqlUpdate deleteReport;
    private GeneralQuery allQuery;
    private GeneralQuery statusAllQuery;
    private GeneralQuery statusCountryAndCampaignQuery;
    private GeneralQuery statusCountryQuery;
    private GeneralQuery statusCampaignQuery;
    private GeneralQuery findByIdQuery;
    private GeneralQuery findByTagQuery;
    private GeneralQuery findByPhraseQuery;
    private GeneralQuery findByCreatorAndCampaign;
    private GeneralQuery findByCreator;
    private GeneralQuery findByCreatorAndStatus;
    private GeneralQuery findByCreatorAndCampaignAndStatus;
    private GeneralQuery statusGeneralCampaignQuery;
    private GeneralQuery findByCreatorAndGeneralCampaign;
    private GeneralQuery findByCreatorAndGeneralCampaignAndStatus;
    private GeneralQuery findLatestReportQuery;
    private GeneralQuery generalCampaignQueryByCountry;


    


    @Autowired
    public PensamentDaoImpl(@Qualifier("dataSourceTarget")
    DataSource ds) {
        setDataSource(ds);
    }

    @Override
    protected void initDao() throws Exception {
        allQuery = new GeneralQuery(GENERAL_QUERY, "", new int[0]);
        statusAllQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION, new int[] { Types.INTEGER });
        statusCountryAndCampaignQuery = new GeneralQuery(GENERAL_QUERY,
                "WHERE " + STATUS_CONDITION + CAMPAIGN_CONDITION
                        + COUNTRY_CONDITION, new int[] { Types.INTEGER,
                        Types.INTEGER, Types.INTEGER });
        statusCountryQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION + COUNTRY_CONDITION, new int[] {
                Types.INTEGER, Types.INTEGER });
        statusCampaignQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION + CAMPAIGN_CONDITION, new int[] {
                Types.INTEGER, Types.INTEGER });
        statusGeneralCampaignQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION + NULL_CAMPAIGN_CONDITION, Types.INTEGER);
        generalCampaignQueryByCountry = new GeneralQuery(GENERAL_QUERY,
                "WHERE " + STATUS_CONDITION + NULL_CAMPAIGN_CONDITION
                        + COUNTRY_CONDITION, Types.INTEGER, Types.INTEGER);
        findByIdQuery = new GeneralQuery(GENERAL_QUERY, ID_CONDITION,
                new int[] { Types.INTEGER });
        findByTagQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION + TAG_CONDITION, Types.INTEGER,
                Types.VARCHAR);
        findByPhraseQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "
                + STATUS_CONDITION + " AND (" + REPORT_CONDITION
                + TAG_NAME_CONDITION + AMBASSADOR_RELATED_CONDITION
                + CAMPAIGN_NAME_CONDITION + ")", Types.INTEGER, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR);
        findByCreatorAndCampaign = new GeneralQuery(GENERAL_QUERY, " WHERE "
                + AMBASSADOR_ID_CONDITION + CAMPAIGN_CONDITION, Types.INTEGER,
                Types.INTEGER);
        findByCreatorAndGeneralCampaign = new GeneralQuery(GENERAL_QUERY,
                " WHERE " + AMBASSADOR_ID_CONDITION + NULL_CAMPAIGN_CONDITION,
                Types.INTEGER);
        findByCreator = new GeneralQuery(GENERAL_QUERY, " WHERE "
                + AMBASSADOR_ID_CONDITION, Types.INTEGER);
        findByCreatorAndStatus = new GeneralQuery(GENERAL_QUERY, " WHERE "
                + AMBASSADOR_ID_CONDITION + " AND " + STATUS_CONDITION,
                Types.INTEGER, Types.INTEGER);
        findByCreatorAndCampaignAndStatus = new GeneralQuery(GENERAL_QUERY,
                " WHERE " + AMBASSADOR_ID_CONDITION + CAMPAIGN_CONDITION
                        + " AND " + STATUS_CONDITION, Types.INTEGER,
                Types.INTEGER, Types.INTEGER);
        findByCreatorAndGeneralCampaignAndStatus = new GeneralQuery(
                GENERAL_QUERY, " WHERE " + AMBASSADOR_ID_CONDITION + " AND "
                        + STATUS_CONDITION + NULL_CAMPAIGN_CONDITION,
                Types.INTEGER, Types.INTEGER);
        findLatestReportQuery = new GeneralQuery(GENERAL_QUERY, "WHERE "+STATUS_CONDITION
                +" ORDER BY createdate DESC LIMIT 1", Types.INTEGER);
        

  
        
        deleteReport = new SqlUpdate(getDataSource(), "DELETE FROM ambassadorreport WHERE id = ?", new int[] { Types.INTEGER });

    }
/*
    public List<Report> findAll() {
        return allQuery.execute();
    }

    public List<Report> findAll(ReportStatus status, int countryId,
            int campaignId) {
        return statusCountryAndCampaignQuery.execute(new Object[] {
                status.getId(), campaignId, countryId });
    }

    public List<Report> findByCountryId(ReportStatus status, int countryId) {
        return statusCountryQuery.execute(status.getId(), countryId);
    }

    public List<Report> findByCampaignId(ReportStatus status, int campaignId) {
        return statusCampaignQuery.execute(status.getId(), campaignId);
    }

    public List<Report> findGeneralCampaignReports(ReportStatus status) {
        return statusGeneralCampaignQuery.execute(status.getId());
    }

    public List<Report> findGeneralCampaignReportsByCountryId(
            ReportStatus status, int countryId) {
        return generalCampaignQueryByCountry.execute(status.getId(), countryId);
    }

    public List<Report> findAll(ReportStatus status) {
        return statusAllQuery.execute(status.getId());
    }

    public Report load(int id) {
        return findByIdQuery.findObject(id);
    }

    public void delete(int id) {
        deleteReport.update(id);
    }
    */
    private class GeneralQuery extends MappingQuery<Pensament> {
        public GeneralQuery(String query, String condition, int... paramTypes) {
            super(getDataSource(), query, condition, paramTypes);
        }

        @Override
        protected Pensament mapRow(ResultSet rs, int rowNum) throws SQLException {
            
        	Pensament p = new Pensament();
        	p.setId(getInteger(rs,"id"));
    
            p.setTitol(rs.getString("titol"));
           /* p.setText(rs.getString("text"));
            p.setFeedbacktext(rs.getString("feedbacktext"));
            p.setStatus(ReportStatus.valueOf(getInteger(rs, "status")));
            p.setCreateDate(rs.getTimestamp("createdate"));
            p.setCreatorId(getInteger(rs, "creatorid"));
            p.setModeratorId(getInteger(rs, "moderatorid"));
            p.setModeratorNote(rs.getString("moderatornote"));
            p.setModeratorRate(rs.getInt("moderatorrate"));
            p.setStarCount((getInteger(rs, "stars")));
            p.setStarCountFor2Weeks(getInteger(rs, "stars_2wks"));
            p.setPeoplePresent(getInteger(rs, "peoplepresent"));
            p.setResponseId(getInteger(rs, "response"));
            p.setModifiedTime(rs.getTimestamp("modifiedtime"));
            p.setConversationDate(rs.getTimestamp("conversationdate"));
            p.setRejectedText(rs.getString("rejectedtext"));
            p.setCampaignId(getInteger(rs, "campaignid"));*/
            return p;
        }
    }
	
    
	public List<Pensament> getPensaments() {
		return allQuery.execute();
	}

    
    /*

    public List<Report> findByTag(ReportStatus status, String tagName) {
        return findByTagQuery.execute(new Object[] { status.getId(), tagName });
    }

    public List<Report> findByPhrase(ReportStatus status, String searchPhrase) {
        String likePhrase = "%" + searchPhrase + "%";
        Object[] params = new Object[] { status.getId(), likePhrase,
                likePhrase, likePhrase, likePhrase, likePhrase, likePhrase,
                likePhrase };

        return findByPhraseQuery.execute(params);
    }

    public List<Report> findByAmbassadorId(int ambassadorId) {
        return findByCreator.execute(ambassadorId);
    }

    public List<Report> findByAmbassadorId(int ambassadorId, ReportStatus status) {
        return findByCreatorAndStatus.execute(ambassadorId, status.getId());
    }

    public List<Report> findByAmbassadorId(int ambassadorId, int campaignId) {
        return findByCreatorAndCampaign.execute(ambassadorId, campaignId);
    }

    public List<Report> findByAmbassadorId(int ambassadorId, int campaignId,
            ReportStatus status) {
        return findByCreatorAndCampaignAndStatus.execute(new Object[] {
                ambassadorId, campaignId, status.getId() });
    }

    public List<Report> findGeneralCampaignReportsByAmbassadorId(
            int ambassadorId, ReportStatus status) {
        return findByCreatorAndGeneralCampaignAndStatus.execute(new Object[] {
                ambassadorId, status.getId() });
    }

    public List<Report> findGeneralCampaignReportsByAmbassadorId(
            int ambassadorId) {
        return findByCreatorAndGeneralCampaign.execute(ambassadorId);
    }

    public Report findLatestPublishedReport() {
        return findLatestReportQuery.execute(new Object[] {ReportStatus.PUBLISHED.getId()}).get(0);
    }
    
    public boolean hasSomeReportsForGeneralCampaign(int ambassadorId) {
        return getSimpleJdbcTemplate()
                .queryForObject(
                        "SELECT COUNT(0) > 0 FROM ambassadorreport WHERE creatorid = ? AND campaignid IS NULL",
                        Boolean.class, ambassadorId);
    }

    public int findCountByCampaign(int campaignId, ReportStatus status) {
        if(status == null)
            return getSimpleJdbcTemplate().queryForInt(
                    "SELECT COUNT(*) FROM ambassadorreport WHERE campaignid=?",
                    campaignId);
        // if status defined..
        return getSimpleJdbcTemplate().queryForInt(
            "SELECT COUNT(*) FROM ambassadorreport WHERE campaignid=? AND status=?",
            new Object[] {campaignId, status.getId()});
    }
    
    // todo: add a model if otherwise ok
    public Map<Integer,Integer> findReportCountsPerCampaign(int campaignId) {
        Map<Integer,Integer> ret = new TreeMap<Integer,Integer>();
        List<Map<String,Integer>> rows = exportQuery.execute(campaignId);
        for(Map<String,Integer> row : rows) {
            ret.put(row.get("ambassadorId"), row.get("reportCount"));
        }
        return ret;
    }*/
}
