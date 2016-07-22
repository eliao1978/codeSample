package com.yp.pm.kp.ui;

public class UIMapper {
    // page loading object and element
    public static final String LOADING = "div[class='pm-loading']";
    public static final String LOADING_TABLE = "span[class='pm-loading']";
    public static final String NO_RECORDS_TO_LOAD = "//tr/td[normalize-space(.)='No records to load.']";
    public static final String POPUP_ALERT = "div[id='showAlertDivId']>div>p[class='alert-text ng-binding']";
    public static final String APP_VERSION = "application-version>div>span";
    public static final String LOGIN_ERROR = "div[class='error ng-binding']";

    // status
    public static final String EDIT_STATUS_BUTTON = "span[class='dropdown']>button[class='btn btn-xs btn-default dropdown-toggle'][has-permission='ACCOUNT_UPDATE']";
    public static final String DELETE_STATUS_BUTTON = "ul[class='dropdown-menu']>li[ng-click=\"bulkEdit({ value: 'DELETED', label: 'Deleted' })\"]";
    public static final String PAUSE_STATUS_BUTTON = "ul[class='dropdown-menu']>li[ng-click=\"bulkEdit({ value: 'PAUSED', label: 'Paused' })\"]";

    // login page
    public static final String USER_INPUT_TEXTBOX = "input[id='pmEmailAddress']";
    public static final String PASSWORD_INPUT_TEXTBOX = "input[id='pmLoginPassword']";
    public static final String LOGIN_FORM_SUBMIT_BUTTON = "button[type='submit']";
    public static final String MISSING_EMAIL_MSG = "div[class='form-group']>div[class='error'][ng-show='userNameReqError']";
    public static final String INVALID_EMAIL_FORMAT_MSG = "div[class='form-group']>div[class='error'][ng-show='userNameValidError']";
    public static final String MISSING_PASSWORD_MSG = "div[class='form-group']>div[class='error'][ng-show='passwordReqError']";
    public static final String FORGOT_PASSWORD_SUBMIT_BUTTON = "form[ng-submit='forgotPassword()']>div>button[type='submit']";
    public static final String FORGOT_PASSWORD_MISSING_EMAIL_MSG = "div[class='form-group']>div[ng-show='emailIdReqError']";
    public static final String FORGOT_PASSWORD_INVALID_EMAIL_MSG = "div[class='form-group']>div[ng-show='invalidEmailError']";
    public static final String FORGOT_PASSWORD_LINK = "div[class='form-inline']>a[ng-click='handleForgotPassword()']";
    public static final String FORGOT_PASSWORD_EMAIL_INPUT_TEXTBOX = "div[class='form-group']>input[id='forgotEmail']";
    public static final String AGREE_TO_TERMS= "button[ng-click='updateTermsAsAgreed()']";

    // Account
    public static final String CREATE_ACCOUNT_BUTTON = "button[ng-click='createAccountVisible=true'][has-permission='ACCOUNT_CREATE']";
    public static final String CLIENT_NAME_TEXT_BOX = "input[ng-model='clientName'][name='name']";
    public static final String SEARCH_CLIENT_NAME_TEXT_BOX = "input[ng-model='searchText'][placeholder='Client Name']";
    public static final String CLIENT_SEARCH_BUTTON = "div[class='input-group input-group-xs']>button[class='input-group-addon btn btn-default btn-xs'][type='submit']";
    public static final String TIMEZONE_DROPDOWN = "span[items='timeZones']>div>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String SUBMIT_ACCOUNT = "div[class='pm-actions']>button[ng-click='isProcessing=true;cafCtrl.save()']";
    public static final String CANCEL_ACCOUNT = "div[class='pm-actions']>button[ng-click='cafCtrl.cancel()']";
    public static final String ACCOUNT_SETTINGS_ICON = "span[class='fa fa-2x fa-gear']";
    public static final String ADD_USERS_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click='showAcctAccess=!showAcctAccess;showCreateAgency=false'][has-permission='ACCOUNT_ACCESS_ADMIN']";
    public static final String USER_EMAIL_TEXT_BOX = "input[type='email'][ng-model='email']";
    public static final String USER_NAME_TEXT_BOX = "input[type='text'][ng-model='name']";
    public static final String SEND_INVITATION_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click='showAcctAccess=!showAcctAccess']";
    public static final String USER_CANCEL_BUTTON = "div[class='pm-actions']>button[ng-click='cancel()']";
    public static final String AD_GROUP_TABLE_COLUMN = "//table[@class='table table-bordered table-hover ng-scope ng-isolate-scope']/tbody//td[3]";
    public static final String USER_ACCOUNT_ACCESS_TEXTBOX = "//input[@ng-model='searchText.user.name']";
    public static final String USER_INVITED_ACCESS_TEXTBOX = "div>input[ng-model='inviteSearchText.user.name']";
    public static final String USERS_TABLE_ONE = "//div[@class='tab-pane active']/table[1]/tbody/tr";
    public static final String USERS_TABLE_TWO = "//div[@class='tab-pane active']/table[2]/tbody/tr";
    public static final String SHOW_ROWS_BUTTON = "span[class='dropdown-select dropdown btn-rows ng-isolate-scope']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding'][ng-click='isVisible = !isVisible']";
    public static final String chart_button_class = "btn btn-xs btn-default";

    // Budget
    public static final String BUDGET_INPUT_RADIO = "input[ng-value='row.budget.budgetId']";
    public static final String CREATE_BUDGET_BUTTON = "button[ng-click='createBudgetVisible=true'][has-permission='ACCOUNT_UPDATE']";
    public static final String BUDGET_NAME_TEXT_BOX = "input[ng-model='name'][name='name']";
    public static final String BUDGET_AMOUNT_TEXT_BOX = "input[ng-model='amount'][name='amount']";
    public static final String SUBMIT_BUDGET = "div[class='pm-actions']>button[ng-click='cafCtrl.save()']";
    public static final String CANCEL_BUDGET = "div[class='pm-actions']>button[ng-click='cafCtrl.cancel()']";
    public static final String DELETE_BUDGET_BUTTON = "div[id='budgetsTable']>button[class='btn btn-xs btn-default dropdown-toggle'][ng-click='bulkBudgetDelete()']";
    public static final String FILTER_BUDGET_BUTTON = "div[id='budgetFilter']>span[items='staticFilters']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding'][ng-click='isVisible = !isVisible']";
    public static final String BUDGET_DELIVERY_METHOD_LINK = "div[class='col-md-8']>a[ng-click='showDeliveryMethod = !showDeliveryMethod']";
    public static final String BUDGET_DELIVERY_METHOD_ACCELERATED = "div[class='col-md-8']>div[class='form-group']>div[class='radio']>label>input[value='ACCELERATED']";
    public static final String BUDGET_DELIVERY_METHOD_STANDARD = "div[class='col-md-8']>div[class='form-group']>div[class='radio']>label>input[value='STANDARD']";
    public static final String MISSING_BUDGET_AMOUNT_MSG = "section[class='pm-create-section']>div:nth-of-type(6)";
    public static final String MISSING_BUDGET_NAME_MSG = "section[class='pm-create-section']>div:nth-of-type(4)";
    public static final String DUPLICATED_BUDGET_MSG = "section[class='pm-create-section']>div:nth-of-type(9)";
    public static final String BUDGET_AMOUNT_TOO_LOW_MSG = "section[class='pm-create-section']>div:nth-of-type(7)";
    public static final String BUDGET_AMOUNT_TOO_HIGH_MSG = "section[class='pm-create-section']>div:nth-of-type(8)";
    public static final String BUDGET_NAME_TOO_LONG_MSG = "section[class='pm-create-section']>div:nth-of-type(5)";

    // Campaign
    public static final String CREATE_CAMPAIGN_BUTTON = "button[class='btn btn-xs btn-primary'][has-permission='ACCOUNT_UPDATE']";
    public static final String CAMPAIGN_NAME_TEXT_BOX = "input[ng-model='campaignName'][name='campaignName']";
    public static final String CONVERSION_TAB = "//ul[@class='nav navbar-nav']/li/a[contains(concat(' ', @ui-sref, ' '), 'root.account.conversions')]";
    public static final String EDIT_SETTINGS_CAMPAIGN_NAME_TEXT_BOX = "input[ng-model='campaignSettings.campaign.campaignName'][name='campaignName']";
    public static final String SUBMIT_CAMPAIGN = "div[class='pm-actions']>button[ng-click='createCampaignCtrl.save()']";
    public static final String CANCEL_CAMPAIGN = "div[class='pm-actions']>button[ng-click='createCampaignCtrl.cancelForm()']";
    public static final String EDIT_CAMPAIGN = "span[ng-show='!showEditName']>a[ng-click='!hasPermission || (showEditName=!showEditName)']";
    public static final String EDIT_CAMPAIGN_LOCATION = "p[ng-show='!showEditLocation']>a[ng-click='!hasPermission || showEditLocationsTab()']";
    public static final String UPDATE_CAMPAIGN_NAME = "button[class='btn btn-xs btn-primary'][ng-click='updateCampaignName();']";
    public static final String ALL_CAMPAIGNS = "ul[class='pm-lnav-list']>li>a[ng-click='routeToAllCampaigns()']";
    public static final String CAMPAIGNS_TAB = "ul[class='nav nav-tabs']>li>a[ui-sref='root.account.campaigns']";
    public static final String SEARCH_CAMPAIGN = "input[ng-model='searchText'][placeholder='Campaign Name']";
    public static final String CAMPAIGN_FILTER_BUTTON = "div[id='campaignFilters']>button[class='btn btn-xs btn-default']";
    public static final String CAMPAIGN_TYPE_DROPDOWN = "div[id='campaignFilters']>span[class='dropdown-select ng-isolate-scope']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String CAMPAIGN_NAME_ERROR_MESSAGE = "div[class='col-md-6']>div[id='VE_130009']";
    public static final String CAMPAIGN_NAME_TOO_LONG_ERROR_MESSAGE = "div[class='col-md-6']>div[id='VE_130010']";
    public static final String CAMPAIIGN_REMOVE_LOCATION_BUTTON = "table[class='table loc-table']>tbody>tr>td>a[ng-click='createCampaignCtrl.removeCampaignRecord(geo)']";
    public static final String CAMPAIGN_LOCATION_SEARCH_BUTTON = "div[id='locSearch']>button[class='btn btn-xs btn-default'][ng-click='showSearchResults()']";
    public static final String CAMPAIGN_MISSING_SEARCH_LOCATION_ERROR = "div[id='locSearch']>div[ng-show='setingSearchTermReqError']";
    public static final String CAMPAIGN_LOCATION_REQUIRED_ERROR = "div[ng-show='locationRequiredError']";
    public static final String CAMPAIGN_END_DATE_CALENDAR_BUTTON = "div[ng-show='!showSchedule']>div>div>div>div[class='radio has-datepicker']>input:nth-of-type(2)[ng-model='endDate']";
    public static final String CAMPAIGN_SELECT_PAST_DATE = "table[class='ui-datepicker-calendar']>tbody>tr:nth-of-type(1)>td:nth-of-type(7)>a";
    public static final String CAMPAIGN_START_DATE_ERROR_MESSAGE = "div[class='col-md-6 pm-settings-value']>div[id='VE_130017']";
    public static final String CAMPAIGN_END_DATE_ERROR_MESSAGE = "div[CLASS='col-md-6']>div[id='VE_130016']";
    public static final String CAMPAIGN_DUPLICATE_ERROR_MESSAGE = "div[class='col-md-6']>div[id='VE_130019']";
    public static final String BUDGET_REQUIRED_ERROR_MESSAGE = "div[class='col-md-6']>div[ng-show='budgetNotSelectedError']";
    public static final String CAMPAIGN_NAME_TEXT = "div[class='create-ad-group-form ng-scope ng-isolate-scope']>div[class='pm-bc ng-binding'][ng-show='displayCampaignName']";
    public static final String FILTER_BY_DATE_DROPDOWN = "li[class='tab-daterange']>date-range-filter>div>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String DOWNLOAD_BUTTON = "div[class='dropdown pm-download']>div[class='dropdown-toggle']>button[class='btn btn-xs btn-default ng-scope']";
    public static final String DOWNLOAD_BUTTON_XLCSV = ".//*[@id='campaignDevicesTable']/span[3]/div/ul/li[1]/a";
    public static final String DOWNLOAD_BUTTON_CSV = ".//*[@id='campaignDevicesTable']/span[3]/div/ul/li[2]/a";
    public static final String DOWNLOAD_BUTTON_XLCSVGZ = ".//*[@id='campaignDevicesTable']/span[3]/div/ul/li[3]/a";
    public static final String DOWNLOAD_BUTTON_CSVGZ = ".//*[@id='campaignDevicesTable']/span[3]/div/ul/li[4]/a";
    public static final String AD_EXTENSIONS_CHECKALL = "input[ng-model='checkAllModel']";
    public static final String SHOW_CHART_BUTTON = "div[ng-click='showChart = !showChart']>button[class='btn btn-xs btn-default ng-scope']";
    public static final String ADVANCE_SEARCH_LINK = "div[id='locSearch']>a[ng-click='clearSearchResults();open()']";
    public static final String ADVANCE_SEARCH_TEXT_BOX = "div[id='locSearch']>input[ng-model='input.searchTerm']";
    public static final String ADVANCE_SEARCH_ADD_ALL = "div[class='search-results']>table>thead>tr>th:nth-of-type(2)>div>a[ng-click='addAllSearchResults()']";
    public static final String ADVANCE_SEARCH_EXCLUDE_ALL = "div[class='search-results']>table>thead>tr>th:nth-of-type(2)>div>a[ng-click='excludeAllSearchResults()']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_TAB = "div[class='loc-modal-tabs']>button[id='radiusTab'][ng-click='showRadiusTab()']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_TEXT_BOX = "div[id='locRadius']>input[ng-model='input.radiusAddress']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_MILES_TEXT_BOX = "div[id='locRadius']>input[ng-model='input.radiusInMiles']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_LOCATION_BUTTON = "div[id='locRadius']>button[class='btn btn-xs btn-default'][ng-click='showRadiusResults()']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_NO_SEARCH_LOCATION_ERROR = "div[id='locRadius']>div[ng-show='radiusAddressReqError']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_INVALID_MILES_ERROR = "div[id='locRadius']>div[ng-show='radiusInMilesReqError']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_MILES_MAX_RANGE_ERROR = "div[id='locRadius']>div[ng-show='radiusInMilesMaxError']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_MILES_MIN_RANGE_ERROR = "div[id='locRadius']>div[ng-show='radiusInMilesMinError']";
    public static final String ADVANCE_SEARCH_RADIUS_TARGETING_SEARCH_INVALID_ADDRESS_ERROR = "div[id='locRadius']>div[class='error ng-binding']";
    public static final String ADVANCE_SEARCH_BULK_LOCATIONS_TAB = "div[class='loc-modal-tabs']>button[id='bulkTab'][ng-click='showBulkTab()']";
    public static final String ADVANCE_SEARCH_BULK_LOCATIONS_TEXT_BOX = "div[id='locBulk']>form[name='bulkLocForm']>textarea[ng-model='input.bulkSearch']";
    public static final String ADVANCE_SEARCH_BULK_LOCATION_SEARCH_BUTTON = "div[class='pm-actions']>button[class='btn btn-xs btn-default'][ng-click='showBulkResults()']";
    public static final String ADVANCE_SEARCH_BULK_LOCATION_NO_INPUT_ERROR = "FORM[CLASS='bulk-loc-form ng-pristine ng-invalid ng-invalid-required']>div[ng-show='bulkLocationsReqError']";
    public static final String ADVANCE_SEARCH_BULK_LOCATION_INVALID_INPUT_ERROR = "form[class='bulk-loc-form ng-dirty ng-valid ng-valid-required']>div[class='error ng-binding']";
    public static final String SETTINGS_TAB_ERROR = "div[class='pm-settings-edit error']";
    public static final String CAMPAIGNS_SETTINGS_TAB = "div[id='campaignContext']>div[class='no-hover']>section[class='pm-create-section']>div[class='form-group']>div[class='col-md-6']>";
    public static final String CUSTOMER_INFO = "div[class='pm-cust-info dropdown-toggle']>div[class='pm-cust-id ng-binding']";
    public static final String SETTINGS_TAB = "li[class='ng-scope'][ng-class=\"{ active: $state.includes('root.account.settings') }\"]>a[class='ng-isolate-scope']";

    // Ad Group
    public static final String CREATE_AD_GROUP_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click=\"goToState('root.account.adgroups.new')\"][has-permission='ACCOUNT_UPDATE']";
    public static final String AD_GROUP_NAME_TEXT_BOX = "input[id='agName']";
    public static final String KEYWORD_TEXT_BOX = "textarea[ng-model='newKeywords']";
    public static final String AD_GROUP_BID_TEXT_BOX = "input[id='createAgBid']";
    public static final String SUBMIT_AD_GROUP = "div[class='pm-actions']>button[ng-click='createAdGroupCtrl.save()']";
    public static final String CANCEL_AD_GROUP = "div[class='pm-actions']>button[ng-click='createAdGroupCtrl.cancelForm()']";
    public static final String SEARCH_AD_GROUP_TEXT_BOX = "input[ng-model='searchText'][placeholder='AdGroup Name']";
    public static final String AD_GROUP_SEARCH_BUTTON = "div[class='input-group input-group-xs']>button[type='submit']";
    public static final String ADGROUP_FILTER_BUTTON = "div[id='adGroupsTable']>button[class='btn btn-xs btn-default']";
    public static final String ADGROUP_NAME_TOO_LONG_ERROR_MESSAGE = "div[class='form-group']>div[ng-show='VE_140006']";
    public static final String ADGROUP_DUPLICATE_ERROR_MESSAGE = "div[class='form-group']>div[ng-show='VE_140008']";
    public static final String BID_REQUIRED_ERROR_MESSAGE = "div[class='form-group']>div[class='edit-label']>div[ng-show='agBidReqError']";
    public static final String BID_TOO_LARGE_ERROR_MESSAGE = "div[class='form-group']>div[class='edit-label']>div[ng-show='agBidMaxError']";
    public static final String BID_TOO_SMALL_ERROR_MESSAGE = "div[class='form-group']>div[class='edit-label']>div[ng-show='agBidMinError']";
    public static final String AD_GROUP_CAMPAIGN_REQUIRED_ERROR_MESSAGE = "div[class='create-ad-group-form ng-scope ng-isolate-scope']>div[class='error'][ng-show='campaignReqError']";
    public static final String HEADLINE_TOO_LONG_AD_GROUP_ERROR_MESSAGE = "div[id='VE_180014'][class='error']";
    public static final String DESCRIPTION_ONE_TOO_LONG_AD_GROUP_ERROR_MESSAGE = "div[id='VE_180016'][class='error']";
    public static final String DESCRIPTION_TWO_TOO_LONGAD_GROUP_ERROR_MESSAGE = "div[id='VE_180018'][class='error']";
    public static final String DISPLAY_URL_TOO_LONG_AD_GROUP_ERROR_MESSAGE = "div[id='VE_180020'][class='error']";
    public static final String DESTINATION_URL_TOO_LONG_AD_GROUP_ERROR_MESSAGE = "div[id='VE_180022'][class='error']";
    public static final String ADGROUP_FILTER_DROPDOWN = "div[id='adGroupsTable']>span[class='dropdown-select ng-isolate-scope']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String SUMMARY_STATS = "table[summary-stats=\"summaryStats\"]>tbody>tr:nth-of-type(2)";
    public static final String LOCATIONS_TAB = "li[ng-class=\"{ active: $state.current.name === 'root.account.settings.geos' }\"]>a[class='ng-isolate-scope']";
    public static final String ADD_TARGET_LOCATION = "table[class='table loc-table']>tbody>tr:nth-of-type(1)>td:nth-of-type(2)>span:nth-of-type(3)>a";
    public static final String EXCLUDED_LOCATION_LINK = "div[class='ng-scope']>a[ng-click='showExcludedLocations = !showExcludedLocations']";
    public static final String BID_ADJUSTMENT_ERROR = "form[class='ng-scope ng-valid ng-dirty']>div>div:nth-of-type(4)";
    public static final String ADD_LOCATION_BUTTON = "div[id='geoEdits']>button[class='btn btn-xs btn-primary'][has-permission='ACCOUNT_UPDATE']";
    public static final String LOCATION_NAME_TEXT_BOX = "input[name='searchTerm']";
    public static final String SAVE_LOCATION_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click='cafCtrl.updateCampaignGeoRecords()']";
    public static final String SAVE_CAMPAIGN_LOCATION_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click='updateCampaignGeoRecords()']";
    public static final String DELETE_EXTENSION_BUTTON = "//button[@class='btn btn-xs btn-default dropdown-toggle'][contains(concat(' ', @ng-click, ' '), 'bulkDelete()')]";
    public static final String SEARCH_LOCATION_BUTTON = "button[class='btn btn-xs btn-default'][ng-click='showSearchResults()']";
    public static final String CANCEL_LOCATION_BUTTON = "button[class='btn btn-xs btn-default'][ng-click='cafCtrl.cancelUpdateCampaignGeoRecords()']";
    public static final String GEO_LOCATION_NO_MATCH_FOUND = "table[class='table loc-table']>thead>tr>th>span[ng-show='settingSearchResults.length == 0']";
    public static final String DATE_RANGE_BUTTON = "li[class='tab-daterange']>date-range-filter>div[class='pm-daterange']>button";
    public static final String DATE_RANGE_TODAY = "//li[@class='tab-daterange']/date-range-filter/div[@class='pm-daterange open']/ul[@class='dropdown-menu']/li[normalize-space(.)='Today']";

    // Ad Schedule
    public static final String ADD_SCHEDULE_BUTTON = "div[id='addScheduleTableButtons']>button[class='btn btn-xs btn-primary'][has-permission='ACCOUNT_UPDATE']";
    public static final String SCHEDULE_START_TIME_SELECT = "span[items='hourSelectionList'][selection='row.startHourSelection']>select";
    public static final String SCHEDULE_END_TIME_SELECT = "span[items='hourSelectionList'][selection='row.endHourSelection']>select";
    public static final String AD_SCHEDULE_DAY_SELECT = "span[items='daySelectionList']>select";
    public static final String SAVE_SCHEDULE_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click='saveNewAdSchedule();']";
    public static final String CANCEL_SCHEDULE_BUTTON = "button[class='btn btn-xs btn-default'][ng-click='showAddAdSchedule=!showAddAdSchedule']";
    public static final String DUPLICATE_AD_SCHEDULE_ERROR = "form[class='form-inline ng-valid ng-dirty']>div:nth-of-type(2)";
    public static final String TIME_PERIOD_OVERLAP_ERROR = "form[class='form-inline ng-valid ng-dirty']>div:nth-of-type(3)";
    public static final String INVALID_END_TIME_ERROR = "form[class='form-inline ng-valid ng-dirty']>div:nth-of-type(1)>div:nth-of-type(2)";

    // Ad
    public static final String CREATE_AD_BUTTON = "button[class='btn btn-xs btn-primary'][ng-click*='createAdVisible=true'][has-permission='ACCOUNT_UPDATE']";
    public static final String HEADLINE_TEXT_BOX = "input[id='agHl']";
    public static final String AD_DISAPPROVED_ICON = "div>span[ng-show='row.ad.disapprovalReasons']";
    public static final String AD_DISAPPROVAL_REASON = "div[class='dropdown-menu'][ng-show='row.ad.disapprovalReasons']";
    public static final String DESCRIPTION1_TEXT_BOX = "input[id='agDesc1']";
    public static final String EDIT_HEADLINE_TEXT_BOX = "input[id='editHl']";
    public static final String DESCRIPTION2_TEXT_BOX = "input[id='agDesc2']";
    public static final String DISPLAY_URL_TEXT_BOX = "input[id='agDisplayURL']";
    public static final String DESTINATION_URL_TEXT_BOX = "input[id='agDestURL']";
    public static final String MOBILE_PREFER_CHECKBOX = "input[id='mPreferred'][type='checkbox']";
    public static final String MOBILE_PREFER_CHECKBOX_CHECKED = "input[id='mPreferred'][type='checkbox'][class='ng-pristine ng-valid']";
    public static final String SUBMIT_AD = "div[class='pm-actions']>button[ng-click='cafCtrl.save()']";
    public static final String SAVE_EDIT_AD_BUTTON = "div[class='pm-actions']>button[ng-click='save()']";
    public static final String CANCEL_EDIT_AD_BUTTON = "div[class='pm-actions']>button[ng-click='cancel();stCtrl.closeEditor()']";
    public static final String CANCEL_AD = "div[class='pm-actions']>button[ng-click='cafCtrl.cancel()']";
    public static final String AD_SEARCH_TEXTBOX = "input[ng-model='searchText'][placeholder='Ad Text']";
    public static final String AD_SEARCH_BUTTON = "div[class='input-group input-group-xs']>button[type='submit']";
    public static final String ROWS_DROPDOWN = "//span[@class='dropdown-select dropdown btn-rows ng-isolate-scope']/div[@class='btn-group']/button[@class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String PAGE_DROPDOWN = "//span[@class='dropdown-select dropdown ng-scope ng-isolate-scope']/div[@class='btn-group']/button[@class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String DUPLICATE_AD_ERROR_MESSAGE = "div[ng-show='selectedAdgroup.adgroup.adGroupId']>form>div[id='VE_180024'][ng-show='VE_180024']";
    public static final String MOBILE_PREFERRED_CHECKBOX="div[class='col-md-6']>div:nth-of-type(6)>div>input[ng-model='mobilePreferred']";
    public static final String AD_HEADLINE_REQUIRED_ERROR = "form[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[id='VE_180003']";
    public static final String AD_DESCRIPTION1_REQUIRED_ERROR = "form[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[id='VE_180004']";
    public static final String AD_DESCRIPTION2_REQUIRED_ERROR = "form[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[id='VE_180005']";
    public static final String AD_DISPLAY_URL_REQUIRED_ERROR = "form[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[id='VE_180006']";
    public static final String AD_DESTINATION_URL_REQUIRED_ERROR = "form[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[id='VE_180007']";
    public static final String AD_DYNAMIC_KEYWORD_INSERTION_HEADLINE_ERROR = "//*[@name='adForm']/div[@ng-show='headlineDKIError']";
    public static final String AD_DYNAMIC_KEYWORD_INSERTION_DESCRIPTION_ONE_ERROR = "//*[@name='adForm']/div[@ng-show='description1DKIError']";
    public static final String AD_DYNAMIC_KEYWORD_INSERTION_DESCRIPTION_TWO_ERROR = "//*[@name='adForm']/div[@ng-show='description2DKIError']";
    public static final String AD_DYNAMIC_KEYWORD_INSERTION_DISPLAY_URL_ERROR = "//*[@name='adForm']/div[@ng-show='displayurlDKIError']";
    public static final String AD_DYNAMIC_KEYWORD_INSERTION_DESTINATION_URL_ERROR = "//*[@name='adForm']/div[@ng-show='destinationurlDKIError']";

    //Keyword
    public static final String SHOW_NEGATIVE_KEYWORDS ="a[class='toggle-neg-kwd ng-scope'][ng-click='$stateParams.showNegativeKwd = !$stateParams.showNegativeKwd;']";
    public static final String CREATE_KEY_WORD_BUTTON = "button[ng-click='createKeywordVisible=true'][has-permission='ACCOUNT_UPDATE']";
    public static final String SUBMIT_KEY_WORD = "div[class='pm-actions']>button[ng-click='cafCtrl.save()']";
    public static final String CANCEL_KEY_WORD = "div[class='pm-actions']>button[ng-click='cafCtrl.cancel()']";
    public static final String NEGATIVE_KEYWORD_LINK = "a[class='toggle-neg-kwd ng-scope'][ng-click='$stateParams.showNegativeKwd = !$stateParams.showNegativeKwd;']";
    public static final String NEGATIVE_KEYWORD_CAMPAIGN_ADD_BUTTON = "div[class='neg-kwd ng-scope'][ui-view='campaignNegativesPane']>div[id='keywordEdits'][class='pm-filters clearfix ng-scope']>button[class='btn btn-xs btn-primary'][ng-click='createNegativeKeywordVisible=true'][has-permission='ACCOUNT_UPDATE']";
    public static final String NEGATIVE_KEYWORD_GROUP_ADD_BUTTON = "div[class='neg-kwd ng-scope'][ui-view='adgroupNegativesPane']>div[id='keywordEdits'][class='pm-filters clearfix ng-scope']>button[class='btn btn-xs btn-primary'][ng-click='createNegativeKeywordVisible=true'][has-permission='ACCOUNT_UPDATE']";
    public static final String ACCEPT_POPUP_WARNING_BUTTON = "button[class='btn btn-xs btn-default'][ng-click='showEditKeyword = !showEditKeyword']";
    public static final String KEYWORD_TABLE_Header = "//table[@class='table table-bordered table-hover ng-scope ng-isolate-scope']/thead/tr/";
    public static final String KEYWORD_TABLE_Body = "//table[@class='table table-bordered table-hover ng-scope ng-isolate-scope']/tbody/tr";
    public static final String KEYWORD_TABLE_COLUMN = "//table[@class='table table-bordered table-hover ng-scope ng-isolate-scope']/tbody//td[3]";
    public static final String KEYWORD_SEARCH_TEXTBOX = "input[ng-model='searchText'][placeholder='Keyword Text']";
    public static final String FILTER_SUBMIT_BUTTON = "button[class='input-group-addon btn btn-default btn-xs'][type='submit']";
    public static final String KEYWORD_FILTER_BUTTON = "div[id='keywordsTable']>button[class='btn btn-xs btn-default'][role='button']";
    public static final String KEYWORD_ELEMENT_DROPDOWN = "div[id='keywordsTable']>span>div>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String KEYWORD_UPDATE_DROPDOWN = "div[id='keywordEdits']>span>button[class='btn btn-xs btn-default dropdown-toggle'][has-permission='ACCOUNT_UPDATE']";
    public static final String AD_FILTER_BUTTON = "div[id='adsTable']>button[class='btn btn-xs btn-default'][ng-click='isVisible = true']";
    public static final String ADS_FILTER_DROPDOWN = "div[id='adsTable']>span[class='dropdown-select ng-isolate-scope']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String DUPLICATE_KEYWORD_ERROR_MESSAGE = "div[class='modal-dialog ng-scope']>div[class='modal-body']>table[class='table']";
    public static final String MODAL_DUPLICATE_ERROR_OK_BUTTON = "div[class='pm-actions']>button[class='btn btn-sm btn-default'][ng-click='closeModal()']";
    public static final String KEYWORD_CHARACTER_LENGTH_ERROR = "ul>li[ng-repeat='kwdError in keywordErrorArray']>div[class='error ng-binding']";
    public static final String ADGROUP_LEVEL_NEGATIVE_KEYWORD_MAX_CHARACTER_ERROR_MESSAGE = "div[ui-view='adgroupNegativesPane']>div>form>ul>li>div[class='error ng-binding']";
    public static final String CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_MAX_CHARACTER_ERROR_MESSAGE = "div[ui-view='campaignNegativesPane']>div>form>ul>li>div[class='error ng-binding']";
    public static final String CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_DUPLICATE_ERROR_MESSAGE = "div[ui-view='campaignNegativesPane']>div>form>div:nth-of-type(3)";
    public static final String EDIT_KEYWORD_SAVE_BUTTON = "div[class='table-cell-editor-actions']>button[class='btn btn-xs btn-primary'][ng-click='save()']";
    public static final String CAMPAIGN_LEVEL_NEGATIVE_KEYWORD_INVALID_CHARACTER_ERROR_MESSAGE = "div[ui-view='campaignNegativesPane']>div>form>ul>li>div[class='error ng-binding']";
    public static final String NEGATIVE_KEYWORD_MAX_WORD_ERROR_MESSAGE = "div[class='popup-alert alert alert-danger alert-dismissable']>p[class='alert-text ng-binding']";
    public static final String ADGROUP_LEVEL_NEGATIVE_KEYWORD_INVALID_CHARACTERS_ERROR_MESSAGE = "div[ui-view='adgroupNegativesPane']>div>form>ul>li>div[class='error ng-binding']";
    public static final String KEYWORD_DISAPPROVAL_REASON = "div[class='dropdown-menu'][ng-show='row.keyword.disapprovalReasons']";
    public static final String KEYWORD_DISAPPROVED_ICON = "div>span[ng-show='row.keyword.disapprovalReasons']";

    //AdExtension SiteLink
    public static final String EXTENSION_BUTTON = "button[ng-click='addAdExtension()'][has-permission='ACCOUNT_UPDATE']";
    public static final String NEW_EXTENSION_BUTTON = "button[ng-click='showAddExtension()']";
    public static final String SITELINKTEXT = "input[name='sitelinkText']";
    public static final String SITELINKURL = "input[name='sitelinkUrl']";
    public static final String NEW_SITELINK_TEXT = "//div[@class='modal-header ng-scope']/h5[normalize-space(.)='New Sitelink']";
    public static final String SITELINK_LINK_TEXT_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[id='VE_165001']";
    public static final String SITELINK_LINK_URL_INVALID_CHARACTERS_ERROR = "div[class='col-md-7 has-input']>div:nth-of-type(3)";
    public static final String SITELINK_URL_TOO_LONG_ERROR = "div[class='col-md-7 has-input']>div[id='165011']";
    public static final String SITELINK_TEXT_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='VE_165009']";
    public static final String SITELINK_LINK_URL_REQUIRED_ERROR = "div[class='col-md-7 has-input']>div[id='165000']";
    public static final String ADEXTENSION_LOCATION_ADDRESS_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[id='VE_155008']";
    public static final String ADEXTENSION_LOCATION_ADDRESS1_TOO_LONG__ERROR = "div[class='col-md-6 has-input']>div[id='VE_155014']";
    public static final String ADEXTENSION_LOCATION_ADDRESS2_TOO_LONG__ERROR = "div[class='col-md-6 has-input']>div[id='VE_155016']";
    public static final String ADEXTENSION_LOCATION_CITY_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='155018']";
    public static final String ADEXTENSION_LOCATION_STATE_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='155020']";
    public static final String ADEXTENSION_LOCATION_ZIP_CODE_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='VE_155022']";
    public static final String ADEXTENSION_LOCATION_ZIP_CODE_INVALID_ERROR = "div[class='col-md-6 has-input']>div[id='VE_155023']";
    public static final String ADEXTENSION_LOCATION_PHONE_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='VE_160009']";
    public static final String ADEXTENSION_LOCATION_CITY_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[id='155009']";
    public static final String ADEXTENSION_LOCATION_STATE_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[id='155010']";
    public static final String ADEXTENSION_LOCATION_ZIPCODE_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[id='VE_155000']";
    public static final String ADEXTENSION_CALLEXTENSION_ERROR = "div[class='col-md-6 has-input']>div[id='VE_160001']";
    public static final String ADEXTENSION_CALL_EXTENSION_TOO_LONG_ERROR = "div[class='col-md-6 has-input']>div[id='VE_160009']";
    public static final String ADEXTENSION_LOGO_LABEL_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[ng-show='labelRequired']";
    public static final String ADEXTENSION_LOGO_IMAGE_SIZE_ERROR = "div[class='col-md-6 has-input']>div[ng-show='VE_168010']";
    public static final String ADEXTENSION_LOGO_INVALID_FILE_FORMAT_ERROR = "div[class='col-md-6 has-input']>div[ng-show='invalidFileFormat']";
    public static final String ADEXTENSION_LOGO_IMAGE_REQUIRED_ERROR = "div[class='col-md-6 has-input']>div[ng-show='fileNotSelected']";
    public static final String ADEXTENSION_LOGO_LABEL_TOO_LONG = "div[class='col-md-6 has-input']>div[ng-show='labelTooLong']";
    public static final String SAVE_NEW_SITE_LINK = "button[ng-click='saveSitelinkExtension()']";
    public static final String AD_EXTENSION_REMOVE_BUTTON = "button[ng-click='showDeletePrompt()']";
    public static final String AD_EXTENSION_CONFIRM_DELETE = "button[ng-click='closeModalWithConfirmation()']";
    public static final String AD_EXTENSION_CANCEL_BUTTON = "button[ng-click='closeModal()']";
    public static final String EXTENSION_DROPDOWN = "span[items='extensionFilters']>div>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String SITELINK_FILTER_BUTTON = "/html/body/div[2]/div[2]/div[2]/div[2]/div/div/div[1]/span[3]/button";
    public static final String TABLE_BODY_VALID = "table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>tbody>tr";
    public static final String ADEXTENSION_TABLE_HEAD = "table[class='table table-bordered table-hover ng-scope ng-isolate-scope']>thead>tr>th:nth-of-type";
    public static final String ADEXTENSION_FILTER_DROPDOWN = "div[class='ng-scope ng-pristine ng-valid']>div[class='filter-row']>span[class='dropdown-select ng-isolate-scope']>div[class='btn-group']>button[class='btn btn-xs btn-default dropdown-toggle ng-binding']";
    public static final String EXTENSION_EDIT_BUTTON = "div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-default'][ng-click='editAdExtension()']";

    //Ad Extension Location extension
    public static final String NEW_LOCATION_ADDRESS_LINE_ONE_TEXT_BOX = "input[ng-model='input.address1'][name='address1']";
    public static final String NEW_LOCATION_ADDRESS_LINE_TWO_TEXT_BOX = "input[ng-model='input.address2'][name='address2']";
    public static final String NEW_LOCATION_CITY_TEXT_BOX = "input[ng-model='input.city'][name='city']";
    public static final String NEW_LOCATION_STATE_TEXT_BOX = "input[ng-model='input.state'][name='state']";
    public static final String NEW_LOCATION_ZIP_TEXT_BOX = "input[ng-model='input.zip'][name='zip']";
    public static final String NEW_LOCATION_COMPANY_NAME_TEXT_BOX = "input[ng-model='input.companyName'][name='companyName']";
    public static final String NEW_LOCATION_PHONE_NUMBER_TEXT_BOX = "input[ng-model='input.locPhoneNumber'][name='locPhoneNumber']";
    public static final String LOCATION_EXTENSION_EDIT_BUTTON = "div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-default'][ng-click='showEditPrompt()']";
    public static final String SITE_EXTENSION_EDIT_BUTTON = "//*[@ng-click='addExtension(extn)']";
    public static final String SELECT_CAMPAIGN_AD_EXTENSION = "//*[@ng-click='addCampaign(cmp)']";
    public static final String EDITED_AD_EXTENSION_SAVE_BUTTON = "section[class='pm-create-ad edit-ext']>div[class='pm-actions']>button[class='btn btn-xs btn-primary'][ng-click='saveCampaignExtension()']";
    public static final String AD_EXTENSION_EDIT = "//button[@ng-click='editAdExtension()']";
    public static final String SITE_EXTENSION_EDIT_EXTENSION_BUTTON = "//*[@ng-click='showEditExtension(extn)']";
    public static final String NEW_LOCATION_SAVE_BUTTON = "button[class='btn btn-sm btn-primary'][ng-click='saveLocationExtension()']";
    public static final String CONFIRM_DELETE_BUTTON = "button[class='btn btn-sm btn-primary'][ng-click='closeModalWithConfirmation()']";
    public static final String DELETE_CLOSAL_MODEL_BUTTON = "button[class='btn btn-sm btn-primary'][ng-click='closeModalWithConfirmation()']";
    public static final String STATIC_FILTERS = "span[items='staticFilters']>div>button[class='btn btn-xs btn-default dropdown-toggle ng-binding'][ng-click='isVisible = !isVisible']";
    public static final String IMPORT_EXTENSION = "div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-primary'][ng-click='importAdExtension()']";
    public static final String LOCATION_EDIT_BUTTON = "div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-default'][ng-click='showEditPrompt()']";
    public static final String LOCATION_REMOVE_BUTTON = "div[id='cmpExtensionEdit']>button[class='btn btn-xs btn-danger'][ng-click='showDeletePrompt()']";

    //Ad Extension New Call Extension
    public static final String NEW_CALL_EXTENSION_PHONENUMBER = "input[ng-model='input.phoneNumber'][name='phoneNumber']";
    public static final String NEW_CALL_EXTENSION_SAVE_BUTTON = "button[class='btn btn-sm btn-primary'][ng-click='savePhoneExtension()']";

    //Ad Extension Logo Extension
    public static final String NEW_LOGO_EXTENSION_LABEL_TEXTBOX = "input[ng-model ='input.label'][name='label']";
    public static final String NEW_LOGO_EXTENSION_FILE = "/html/body/div[7]/div/div/div[2]/form/div[3]/div/input";
    public static final String NEW_LOGO_EXTENSION_SAVE_BUTTON = "button[ng-click='saveLogoExtensions(uploadingFile)']";
    public static final String NEW_LOGO_EXTENSION_CANCEL_BUTTON = "button[ng-click='closeModal()']";
    public static final String CHANGE_HISTORY_FILTER_BUTTON = "div[id='historyFilters']>button[class='btn btn-xs btn-default'][ng-click='isVisible = true']";
    public static final String REPORTING_HEADER_TAB = "//*[@class='nav navbar-nav']/li[2]/a";
    public static final String AGENCY_HEADER_TAB = "//*[@class='nav navbar-nav']/li[1]/a";
    public static final String CREATE_REPORT_BUTTON = "div[class='pm-filters']>button[class='btn btn-xs btn-primary'][ng-click='showCreateReportForm = !showCreateReportForm']";
    public static final String DOWNLOAD_REPORT_CANCEL_BUTTON = "div[class='form-horizontal ng-pristine ng-invalid ng-invalid-required']>div[class='pm-actions']>button[class='btn btn-xs btn-default'][ng-click='reloadForm();']";

    //Header Footer:
    public static final String HEADER_LOGO = "div[class='pm-logo pull-left']";
    public static final String HEADER_AGECNY_DROPDOWN = "div[class='pm-topbar ng-scope']>select[class='ng-scope ng-pristine ng-valid']";
    public static final String HEADER_USER_NAME = "div[class='pm-cust-drop']>div[class='dropdown']>div[class='pm-cust-info dropdown-toggle']>div[class='pm-cust-id ng-binding']";
    public static final String HEADER_SETTINGS_ICON = "div[class='dropdown']>div[class='dropdown-toggle']>span[class='fa fa-2x fa-gear']";
    public static final String HEADER_BELL_ICON = "div[class='pm-notify ng-scope']>span[class='fa fa-bell']";
    public static final String HEADER_MY_AGENCY_TEXT = "div[class='pm-topbar ng-scope']>a[class='pm-topbar-text ng-isolate-scope']";
    public static final String FOOTER_TERMS_CONDITIONS_LINK = "div[class='ng-scope']>div>a[href='http://national.yp.com/downloads/ypsm-terms.pdf']";
    public static final String FOOTER_BROWSER_VERSION_TEXT = "footer[class='pm-footer']>div[class='pm-browsers']";
    public static final String FOOTER_TEXT = "//*[@class='pm-footer']";

    //CONVERSION TRACKING
    public static final String CREATE_CONVERSION = "div[class='pm-filters']>button[ng-click=\"$state.go('root.account.conversions',{'mode':'new'})\"]";
    public static final String CONVERSION_NAME = "input[type='text'][ng-model='conversion.name']";
    public static final String CONVERSION_VALUE = "input[type='number'][ng-model='conversion.defaultConversionValue']";
    public static final String SAVE_AND_CONTINUE = "div[class='pm-actions']>button[class='btn btn-sm btn-primary'][ng-click='saveAndContinue()']";
    public static final String DONE_BUTTON = "div[class='pm-actions']>button[class='btn btn-sm btn-primary'][ng-click='done()']";

    // TEMP
    public static final String BID_ADJUSTMENT_BUTTON = "div[id='geoEdits']>button[class='btn btn-xs btn-default'][ng-click='bulkEditBidsVisible=true;createLocationVisible=false']";
    public static final String BID_ADJUSTMENT_ADSCHEDULE_BUTTON = "div[id='addScheduleTableButtons']>button[class='btn btn-xs btn-default'][ng-click='bulkEditBidsVisible=true;createLocationVisible=false;deleteAdScheduleError=false']";
    public static final String LOCATIONS_CHECKALL = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/table/thead/tr/th[1]/input";
    public static final String ADSCHEDULES_CHECKALL = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/table/thead/tr/th[1]/input";
    public static final String INCREASE_BY_TEXTFIELD = "div[class='form-group']>input[class='form-control inline-edit currency-input ng-pristine ng-valid ng-valid-number ng-valid-min']";
    public static final String MAKE_CHANGES_BUTTON = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[4]/form/div[2]/button[1]";
    public static final String MAKE_SCHEDULE_CHANGES_BUTTON = "//*[@id='addScheduleTableButtons']/div[1]/form/div[2]/button[1]";
    public static final String COPYRIGHT_TEXT_MATCH = "html/body/footer/legal-footer/div/div[3]";
    public static final String KEYWORD_CREATE_ERROR = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[3]/section/form/ul[2]/li/div";
    public static final String KEYWORD_DKI_LINK = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div/a[1]";
    public static final String KEYWORD_DLI_LINK = "//*[@id='indexPageId']/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div/a[2]";
    public static final String SELECT_OPTION = "//li/a[normalize-space(.)='4 Weeks']";
}