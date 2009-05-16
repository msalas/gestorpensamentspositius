package gpp.servei;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;


import gpp.bean.Pensament;
import gpp.dao.PensamentDao;
import gpp.exception.BusinessException;



@Service("pensamentServei")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
public class PensamentServeiImpl implements PensamentServei {


	
	@Resource
	private PensamentDao pDAO;
	
	
	public void addPensament(Pensament pensament) {
		// TODO Auto-generated method stub
		
	}


    
    public List<Pensament> getPensamentsPopularitat() {
        return pDAO.getPensamentsPopularitat();
    }

    public List<Pensament> getPensamentsAModerar() {
        return pDAO.getPensamentsAModerar();
    }

	public Pensament getPensament(int id) {
		// TODO Auto-generated method stub
		return null;
	}
/*
    @Resource
    private ReportDao reportDao;

    @Resource
    private CountryDao countryDao;

    @Resource
    private ReportTagDao reportTagDao;

    @Resource
    private CampaignDao campaignDao;

    @Resource
    private AmbassadorService ambassadorService;

    @Resource
    private ReportRatingDao reportRatingDao;
    
    @Resource
    private ReportEmailService reportEmailService;
    
    @Resource
    private UserDAO userDao;
    

    private final Comparator<Report> dateCreatedComparator = new DateCreatedReportComparator();

    private final Comparator<Report> ratingComparator = new RatingReportComparator();

    private final Last2WeeksRatingReportComparator last2WeeksRatingComparator = new Last2WeeksRatingReportComparator();

    private final Comparator<Report> titleComparator = new TitleReportComparator();

    private final Comparator<Report> statusComparator = new StatusReportComparator();

    private final Comparator<Report> unratedAndStatusAndDateComparator = new UnratedAndStatusAndDateReportComparator();

    private final Comparator<Report> moderatorRatingComparator = new ModeratorRatingReportComparator();

    private final Comparator<Report> campaignComparator = new CampaignReportComparator();


    
    public void getAll(ReportsQuery query) {
        sortAndFill(query, reportDao.findAll());
    }

    public void findReports(AmbassadorReportsQuery query) {
        final List<Report> reports;

        if (query.getCampaignId() == Campaign.ALL.getId()) {
            if (query.getStatus() == null) {
                reports = reportDao.findByAmbassadorId(query.getAmbassadorId());
            } else {
                reports = reportDao.findByAmbassadorId(query.getAmbassadorId(),
                        query.getStatus()); 
            }
        } else {
            if (query.getStatus() == null) {
                if (query.getCampaignId() == Campaign.GENERAL.getId()) {
                    reports = reportDao
                            .findGeneralCampaignReportsByAmbassadorId(query
                                    .getAmbassadorId());
                } else {
                    reports = reportDao.findByAmbassadorId(query
                            .getAmbassadorId(), query.getCampaignId());
                }
            } else {
                if (query.getCampaignId() == Campaign.GENERAL.getId()) {
                    reports = reportDao
                            .findGeneralCampaignReportsByAmbassadorId(query
                                    .getAmbassadorId(), query.getStatus());
                } else {
                    reports = reportDao.findByAmbassadorId(query
                            .getAmbassadorId(), query.getCampaignId(), query
                            .getStatus());
                }
            }
        }

        sortAndFill(query, reports);
        loadTags(query.getResults());
    }

    public Report findLatestPublishedReport() {
        return reportDao.findLatestPublishedReport();
    }
    
    public void getPublished(ReportsQuery query) {
        List<Report> allReports;
        if (query.isSearchByTag()) {
            allReports = reportDao.findByTag(ReportStatus.PUBLISHED, query
                    .getTag());
        } else {
            allReports = getByStatus(query);
        }
        sortAndFill(query, allReports);

        loadTags(query.getResults());
    }

    private List<Report> getByStatus(ReportsQuery query) {
        if (query.getCampaignId() == Campaign.ALL.getId()) {
            if (query.getCountryId() == null) {
                return reportDao.findAll(ReportStatus.PUBLISHED);
            }
            return reportDao.findByCountryId(ReportStatus.PUBLISHED, query
                    .getCountryId());
        }
        if (query.getCountryId() == null) {
            if (query.getCampaignId() == Campaign.GENERAL.getId()) {
                return reportDao
                        .findGeneralCampaignReports(ReportStatus.PUBLISHED);
            }
            return reportDao.findByCampaignId(ReportStatus.PUBLISHED, query
                    .getCampaignId());
        }
        if (query.getCampaignId() == Campaign.GENERAL.getId()) {
            return reportDao.findGeneralCampaignReportsByCountryId(
                    ReportStatus.PUBLISHED, query.getCountryId());
        }
        return reportDao.findAll(ReportStatus.PUBLISHED, query.getCountryId(),
                query.getCampaignId());
    }

    private void loadTags(List<Report> reports) {
        for (Report report : reports) {
            report.setTags(reportTagDao.findTagNamesByReportId(report.getId()));
        }
    }

    public void searchReports(SearchReportsQuery query) {
        List<Report> allReports = reportDao.findByPhrase(
                ReportStatus.PUBLISHED, query.getSearchPhrase());

        sortAndFill(query, allReports);

        for (Report report : query.getResults()) {
            report.setTags(reportTagDao.findTagNamesByReportId(report.getId()));
        }
    }

    private void sortAndFill(PaginatedList<Report, ReportSortCriterion> query,
            List<Report> reports) {
        sort(query.getSortOrder(), reports);
        fillPageResults(query, reports);
        loadCreators(query.getResults());
    }

    private void sort(ReportSortCriterion sortCriterion, List<Report> allReports) {
        Comparator<Report> comparator;
        switch (sortCriterion) {
        case DATE:
            comparator = dateCreatedComparator;
            break;
        case RATING:
            comparator = ratingComparator;
            break;
        case RATING_2_WEEKS:
            comparator = last2WeeksRatingComparator;
            break;
        case TITLE:
            comparator = titleComparator;
            break;
        case STATUS:
            comparator = statusComparator;
            break;
        case MODERATOR_RATING:
            comparator = moderatorRatingComparator;
            break;
        case CAMPAIGN:
            comparator = campaignComparator;
            break;
        case UNRATED_STATUS_DATE:
            comparator = unratedAndStatusAndDateComparator;
            break;
         
        default:
            throw new IllegalArgumentException("Unsupported sort "
                    + sortCriterion);
        }

        Collections.sort(allReports, comparator);
    }

    private void fillPageResults(
            PaginatedList<Report, ReportSortCriterion> query,
            List<Report> allReports) {
        List<Report> pageResults = new ArrayList<Report>(allReports.subList(
                query.getOffset(), getPageLastItemIndex(query, allReports)));
        query.setFullListSize(allReports.size());
        query.setResults(pageResults);
    }

    private int getPageLastItemIndex(
            PaginatedList<Report, ReportSortCriterion> query,
            List<Report> allReports) {
        int lastPageItemIndex = query.getOffset() + query.getPageSize();
        if (lastPageItemIndex >= allReports.size()) {
            lastPageItemIndex = allReports.size();
        }
        return lastPageItemIndex;
    }

    protected void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    protected void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    protected void setReportTagDao(ReportTagDao reportTagDao) {
        this.reportTagDao = reportTagDao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addReport(Report report) {
        reportDao.save(report);
        saveReportTags(report);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addReportForTrustedUser(Report report) {
        report.setStatus(ReportStatus.PUBLISHED);
        addReport(report);
    }

    private void saveReportTags(Report report) {
        ArrayList<Tag> tagBeans = new ArrayList<Tag>();
        Set<String> tags = new HashSet<String>(Arrays.asList(report.getTags()));

        for (String tag : tags) {
            if (tag != null && tag.trim().length() > 0) {
                tagBeans.add(reportTagDao.insertTagIfNotExists(tag));
            }
        }

        reportTagDao.saveReportTags(report, tagBeans);
    }

    public List<Country> getAllCountriesHavingSomeReports() {
        return countryDao.findAllHavingAmbassadorReports();
    }

    public Report getReport(int id) {
        Report report = reportDao.load(id);

        if (report == null) {
            throw new IllegalArgumentException("Report id not found " + id);
        }

        report.setTags(reportTagDao.findTagNamesByReportId(report.getId()));

        return report;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteReport(int id) {
        reportTagDao.deleteReportTags(id);
        reportRatingDao.delete(id);
        reportDao.delete(id);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateReport(Report report) {
        reportDao.save(report);
        saveReportTags(report);
    }

    
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void sendEmail(Report report) {
       
        Ambassador ambassador = ambassadorService.loadAmbassador(report.getCreatorId());
        UserBean user = userDao.getUser(ambassador.getUserId());

         if (ambassador.isEmailalert()&&(report.getStatus()==ReportStatus.PUBLISHED ||report.getStatus()==ReportStatus.REJECTED||report.getStatus()==ReportStatus.REJECTED_EDITABLE)) {
            try {
                reportEmailService.sendUserReportStatusNotification(report, user);
            } catch (Exception ex) {
                Log.error(Log.GENERAL, "Unable to send notification mail.",ex);
            }
        }
    }

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateReportByCreator(Report report, UserBean user) {
        if (user.isEndUser()) {
            Ambassador ambassador = user.getAmbassador();
            if (report.getStatus() != ReportStatus.REJECTED_EDITABLE) {
                throw new SystemException("Report cannot be edited");

            } else if (report.getCreatorId().intValue() != ambassador.getId().intValue()) {
                throw new SystemException(
                        "You are not the creator of this report, thus it cannot be edited by you");
            }

            if (user.isU_trusted()) {
                report.setStatus(ReportStatus.PUBLISHED);
            } else {
                report.setStatus(ReportStatus.AWAITING_MODERATION);
            }
        }

        reportDao.save(report);

        saveReportTags(report);
    }

    public List<Tag> getPublishedReportTags(int limit) {
        return reportTagDao.findPublishedReportTags(limit);
    }

    public Ambassador getCreator(Report report) {
        Integer creatorId = report.getCreatorId();
        return ambassadorService.loadAmbassador(creatorId);
    }

    public UserBean getCreatorUser(Report report) {
        Ambassador ambassador = getCreator(report);
        ambassadorService.loadUser(ambassador);
        return ambassador.getUser();
    }

    public Campaign getCampaign(Report report) {
        Integer campaignId = report.getCampaignId();
        if (campaignId != null) {
            return campaignDao.load(campaignId);
        }
        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public void addRating(ReportRating rating)
            throws ReportAlreadyRatedException {
        if (rating.getUserId() == null || rating.getReportId() == null) {
            throw new IllegalArgumentException("Invalid rating");
        }

        if (getRating(rating.getUserId(), rating.getReportId()) != null) {
            throw new ReportAlreadyRatedException();
        }

        reportRatingDao.insert(rating);
    }

    public Integer getRating(UserBean user, Report report) {
        return getRating(user.getU_id(), report.getId());
    }

    private Integer getRating(Integer userId, Integer reportId) {
        return reportRatingDao.getRating(userId, reportId);
    }

    public void loadCampaigns(List<Report> reports) {
        for (Report report : reports) {
            Integer campaignId = report.getCampaignId();
            if (campaignId != null) {
                report.setCampaign(campaignDao.load(campaignId));
            }
        }
    }

    public void loadCreators(List<Report> reports) {
        for (Report report : reports) {
            int ambassadorId = report.getCreatorId();
            Ambassador creator = ambassadorService.loadAmbassador(ambassadorId);
            ambassadorService.loadUser(creator);
            report.setCreator(creator);

            ambassadorService.loadCity(creator);
        }
    }

    public List<Tag> findAmbassadorTags(int ambassadorId) {
        return reportTagDao.findAmbassadorTags(ambassadorId);
    }

    protected void setReportRatingDao(ReportRatingDao reportRatingDao) {
        this.reportRatingDao = reportRatingDao;
    }

    public boolean hasSomeReportsForGeneralCampaign(int ambassadorId) {
        return reportDao.hasSomeReportsForGeneralCampaign(ambassadorId);
    }*/

}
