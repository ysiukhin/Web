package ua.traning.rd.java.finalproject;

public final class Constants {
    public static final String EN_RU_LETTERS_ONLY_REGX_LEN_2_30 = "[-А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\.\\- ]{2,30}";
    public static final String EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50 = "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\.\\- ]{2,50}";
    public static final String EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_100 = "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\.\\- ]{2,100}";
    public static final String EMAIL_REGX =
            "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z0-9]{1}" +
                    "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z0-9_\\.]*" +
                    "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z0-9]*" +
                    "@" +
                    "([-A-Za-z]{1,}\\.){1,2}[-A-Za-z]{2,}";

    public static final String PASSWORD_REGX = "[\\S]{1,}";
//    public static final String PASSWORD_REGX = "(?=(?:[^A-Z]*[A-Z]){2,4}" +
//            "[^A-Z]*\\z)(?=(?:[^a-z]*[a-z]){2,4}" +
//            "[^a-z]*\\z)(?=(?:\\D*\\d){2,4}\\D*\\z)[A-Za-z\\d]+";


    public static final String SQL_LIMIT_OFFSET_BOUNDS = " LIMIT ? OFFSET ?";

    public static final String SQL_ADMIN_ACTIVITY =
            "SELECT ifnull(a.id, 0) id, a.activity_en, a.activity_ru, k.kind_en, k.kind_ru, k.id kind_id" +
                    " FROM activity a RIGHT JOIN kind k ON k.id = a.kind_id";

    public static final String SQL_ADMIN_REQUEST =
            "SELECT ac.id account_id, ac.first_name, ac.last_name, ac.email, rq.id request_id, rq.request, rq.activity_id, " +
                    "aa.activity_en, aa.activity_ru FROM account ac INNER JOIN request rq ON ac.id = rq.account_id " +
                    "INNER JOIN activity aa ON rq.activity_id = aa.id";

    public static final String SQL_ADMIN_REPORT_ACCOUNT =
            "SELECT acc.id, acc.first_name, acc.last_name, COUNT(DISTINCT aat.id) activity_count" +
                    ", IFNULL(SUM(TIMESTAMPDIFF(MINUTE,rec.start, rec.end)), 0) AS total_time_In_minutes FROM account_activity aat " +
                    "RIGHT JOIN account acc ON acc.id = aat.account_id LEFT JOIN record rec ON aat.id = rec.account_activity_id " +
                    "GROUP BY acc.id";

    public static final String SQL_ADMIN_REPORT_ACTIVITY =
            "SELECT at.id, ki.kind_en, ki.kind_ru, at.activity_en, at.activity_ru, COUNT(*) account_count " +
                    " FROM activity at LEFT JOIN kind ki ON at.kind_id = ki.id LEFT JOIN account_activity aa " +
                    " ON aa.activity_id = at.id " +
                    " GROUP BY aa.activity_id ";

    public static final String CALL_GET_USER_ACTIVITIES_AND_REQUEST = "{call GetUserActivitiesAndRequest(?, ?, ?)}";

    public static final String CALL_GET_USER_ACTIVITIES_AND_RECORDS = "{call GetUserActivities(?, ?, ?)}";

    public static final String STOP_TIMER_QUERY = "UPDATE record SET end = NOW() WHERE id = ?";

    public static final int DEFAULT_ROWS_PER_PAGE = 10;

    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_RUSSIAN = "ru";
    public static final String ACCOUNT_ACTIONS_HISTORY = "ACCOUNT_ACTIONS_HISTORY";
    public static final String CURRENT_ACCOUNT = "CURRENT_ACCOUNT";
    public static final String INDEX_JSP = "/index.jsp";
    public static final String ERROR_JSP = "/WEB-INF/error.jsp";
    public static final String LOGIN_JSP = "/login.jsp";
    public static final String USER_SECTION = "/WEB-INF/user/userbasis.jsp";
    public static final String ADMIN_SECTION = "/WEB-INF/admin/adminbasis.jsp";
    public static final String USER_REQUEST_LIST_JSP = "/WEB-INF/user/userrequestlist.jsp";
    public static final String USER_TIMER_LIST_JSP = "/WEB-INF/user/usertimerlist.jsp";
    public static final String ADMIN_ACTIVITY_REPORT_LIST_JSP = "/WEB-INF/admin/reportactivitylist.jsp";
    public static final String ADMIN_ACCOUNT_REPORT_LIST_JSP = "/WEB-INF/admin/reportaccountlist.jsp";
    public static final String ADMIN_ACTIVITY_LIST_JSP = "/WEB-INF/admin/activitylist.jsp";
    public static final String ADMIN_ACCOUNT_LIST_JSP = "/WEB-INF/admin/accountlist.jsp";
    public static final String ADMIN_KIND_LIST_JSP = "/WEB-INF/admin/kindlist.jsp";
    public static final String ADMIN_REQUEST_LIST_JSP = "/WEB-INF/admin/requestlist.jsp";


    public static final String SERVER_ADDRESS_AND_PORT = "http://localhost:8080/";
    public static final String REDIRECT = "redirect";
    public static final String PAGE = "page";
    public static final String PAGINATION = "pages";

    public static final String PAGE_NUMBER = "pagenumber";

    public static final String ROWS_PER_PAGE = "rowsPerPage";
    public static final String ERROR_MESSAGES_BUNDLE = "error_messages";
    public static final String MESSAGES_BUNDLE = "messages";
    public static final String LOGGED_ACCOUNT = "account";
    public static final String ALL_LOGGED_ACCOUNTS = "loggedAccounts";

    public static final String ACCOUNT_ACTIVITY_ID_VALUE = "account_activity_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ACCOUNT_ID = "account_id";
    public static final String ACTIVITY_ID = "activity_id";
    public static final String ACTIVITY_ENGLISH = "activity_en";
    public static final String ACTIVITY_RUSSIAN = "activity_ru";

    public static final String ACCOUNT_LAST_NAME = "account_last_name";
    public static final String ACCOUNT_FIRST_NAME = "account_first_name";

    public static final String MIDDLE_NAME = "middle_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_MD5 = "md5";
    public static final String KIND_ENGLISH = "kind_en";
    public static final String KIND_RUSSIAN = "kind_ru";
    public static final String KIND_ID = "kind_id";

    public static final String REPORT_ACCOUNT_LIST = "accountReportList";
    public static final String ACCOUNT_LIST = "accounts";
    public static final String ACTIVITY_LIST = "activities";
    public static final String KIND_LIST = "kinds";
    public static final String RESULT_LIST = "resultList";
    public static final String TIMER_ACTIVITY_LIST = "timerActivityList";

    public static final String REPORT_ACTIVITY_LIST = "activityReportList";


    public static final String ID = "id";
    public static final String SPACE = " ";
    public static final String EMPTY_STRING = "";

    public static final String ZERO = "0";


    public static final String IS_MESSAGE_TO_SHOW = "isMessage";
    public static final String LAST_ACTION_STATUS = "actionStatus";
    public static final String LAST_ACTION_MESSAGE_SHORT = "actionCaption";
    public static final String LAST_ACTION_MESSAGE_FULL = "actionMessage";

    public static final String MESSAGE_VALIDATION_ERROR = "message.validation.error";
    public static final String MESSAGE_AUTHORIZATION_ERROR = "message.authorization.failed";
    public static final String MESSAGE_AUTHORIZATION_ERROR_FULL = "message.authorization.failed.full";
    public static final String MESSAGE_APPLICATION_FAILED = "message.application.failed";
    public static final String MESSAGE_ACCOUNT_ALREADY_IN_USE = "message.user.already.logged";
    public static final String MESSAGE_ACCOUNT_ALREADY_IN_USE_FULL = "message.user.already.logged.full";
    public static final String MESSAGE_UNAUTHORIZED_ACCESS_SHORT = "message.unauthorized.access.short";
    public static final String MESSAGE_UNAUTHORIZED_ACCESS_FULL = "message.unauthorized.access.long";

    public static final String RESULT_MESSAGE_TEXT = "request.result.message.text";
    public static final String TIMER_STARTED_MESSAGE = "user.timer.started.message";
    public static final String TIMER_STOPPED_MESSAGE = "user.timer.stopped.message";


    public static final String REQUEST_REJECTED = "request.result.message.reject";
    public static final String REQUEST_APPROVED = "request.result.message.approve";

    public static final String SIGN_IN = "table.request.request.message.true";
    public static final String SIGN_OUT = "table.request.request.message.false";


    public static final String USER_REQUEST_SUCCESS = "user.request.ok";
    public static final String USER_REQUEST_FAILED = "user.request.bad";

    public static final String REQUEST = "request";
    public static final String ACTIVITY = "activity";
    public static final String RECORD = "record";
    public static final String ACTION = "action";
    public static final String ENTITY_INSERT_ACTION = "entity.action.create";
    public static final String ENTITY_UPDATE_ACTION = "entity.action.update";
    public static final String ENTITY_DELETE_ACTION = "entity.action.delete";
    public static final String REQUEST_APPROVE_ACTION = "entity.action.approve";

    public static final String DAO_ACTION_RESULT_FAIL = "entity.action.result.bad";
    public static final String DAO_ACTION_RESULT_OK = "entity.action.result.ok";

    public static final String DAO_OPERATION = "entity.dao.operation";

    public static final String EMPTY_RESULT = "message.request.data.empty";


    public static final String LANGUAGE = "lang";
    public static final String SESSION_LOCALE = "sessionLocale";

    public static final String DBNAME = "jdbc/timecounterdb";


    public static final int ACCESS_SECTION = 2;
    public static final int SESSION_TIMEOUT = 180;
    public static final String ENCODING_UTF_8 = "UTF-8";


    public static final String COMMAND_CHANGE_LANGUAGE = "/changeLanguage";

    public static final String COMMAND_LOGIN = "/login";

    public static final String COMMAND_LOGOUT = "/logout";
    public static final String COMMAND_ERROR = "/error";

    public static final String COMMAND_ADMIN_SECTION = "/admin";
    public static final String COMMAND_USER_SECTION = "/user";
    public static final String COMMAND_USER_TIMER = "/user/userTimer";
    public static final String COMMAND_USER_REQUEST_LIST = "/user/userRequestList";

    public static final String COMMAND_ADMIN_ACCOUNT_LIST = "/admin/accountList";
    public static final String COMMAND_ADMIN_ACTIVITY_LIST = "/admin/activityList";
    public static final String COMMAND_ADMIN_KIND_LIST = "/admin/kindList";
    public static final String COMMAND_ADMIN_REQUEST_LIST = "/admin/requestList";
    public static final String COMMAND_ADMIN_REPORT_REQUEST_LIST = "/admin/reportActivityList";
    public static final String COMMAND_ADMIN_REPORT_ACCOUNT_LIST = "/admin/reportAccountList";

    public static final String COMMAND_ADMIN_TO_PAGE_ACCOUNT = "/admin/topageaccount";
    public static final String COMMAND_ADMIN_TO_PAGE_ACTIVITY = "/admin/topageactivity";
    public static final String COMMAND_ADMIN_TO_PAGE_KIND = "/admin/topagekind";
    public static final String COMMAND_ADMIN_TO_PAGE_REQUEST = "/admin/topagerequest";

    public static final String COMMAND_USER_TO_PAGE_REQUEST = "/user/topageuserrequest";
    public static final String COMMAND_USER_TO_PAGE_TIMER = "/user/topageusertimer";

    public static final String COMMAND_ADMIN_ACCOUNT_ACTION = "/admin/accountAction";
    public static final String COMMAND_ADMIN_ACTIVITY_ACTION = "/admin/activityAction";
    public static final String COMMAND_ADMIN_KIND_ACTION = "/admin/kindAction";
    public static final String COMMAND_ADMIN_REQUEST_ACTION = "/admin/requestAction";
    public static final String COMMAND_USER_REQUEST_ACTION = "/user/userRequestAction";
    public static final String COMMAND_USER_TIMER_ACTION = "/user/userTimerAction";


}