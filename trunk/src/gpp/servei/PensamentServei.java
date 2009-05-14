package gpp.servei;

import java.util.List;

import gpp.bean.Pensament;

public interface PensamentServei {

    void addPensament(Pensament pensament);
    Pensament getPensament(int id);
    List<Pensament> getPensaments();
    

//    void getPublished(ReportsQuery query);
//
//    Report findLatestPublishedReport();
//    
//    
//  
//    
//    List<Country> getAllCountriesHavingSomeReports();
//
//    
//    
//    public void deleteReport(int id);
//
//    void updateReport(Report report);
//
//    /**
//     * Updates report only if provided user is creator of provided report
//     *
//     * @param report
//     *            Report to update
//     * @param user
//     *            Creator of report
//     */
//    void updateReportByCreator(Report report, UserBean user);
//
//    List<Tag> getPublishedReportTags(int limit);
//
//    Ambassador getCreator(Report report);
//
//    UserBean getCreatorUser(Report report);
//
//    Campaign getCampaign(Report report);
//
//    Integer getRating(UserBean user, Report report);
//
//    void addRating(ReportRating rating) throws ReportAlreadyRatedException;
//
//    void loadCampaigns(List<Report> reports);
//
//    /**
//     * Eagerly initializes all associations of creators of given reports
//     *
//     * @param reports
//     */
//    void loadCreators(List<Report> reports);
//
//    void findReports(AmbassadorReportsQuery query);
//
//    List<Tag> findAmbassadorTags(int ambassadorId);
//
//    /**
//     * Checks if ambassador has submitted some reports under
//     * {@link Campaign#GENERAL} campaign
//     *
//     * @param ambassadorId
//     * @return true if ambassador has some reports under general campaign
//     */
//    boolean hasSomeReportsForGeneralCampaign(int ambassadorId);
//    
//    void sendEmail(Report report);
}
