package ua.traning.rd.java.finalproject;

public final class Constants {
    public static final String EN_RU_LETTERS_ONLY_REGX_LEN_2_30 = "[-А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\.]{2,30}";
    public static final String EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50 = "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\. ]{2,50}";
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

    public static final String ACCOUNT_ACTIONS_HISTORY = "ACCOUNT_ACTIONS_HISTORY";

    public static final String CURRENT_ACCOUNT = "CURRENT_ACCOUNT";

    public static final String SQL_ADMIN_ACTIVITY =
            "SELECT a.id, a.activity_en, a.activity_ru, k.kind_en, k.kind_ru, a.kind_id" +
                    " FROM activity a INNER JOIN kind k ON k.id = a.kind_id";

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

//            "select acc.id, acc.first_name, acc.last_name, count(distinct aat.id) activity_count" +
//        ",  IFNULL(sum(TIMESTAMPDIFF(MINUTE,rec.start, rec.end)), 0) as total_time_In_minutes from account_activity aat " +
//        "right join account acc on acc.id = aat.account_id left join record rec on aat.id = rec.account_activity_id " +
//        "group by acc.id";

    public static final String CALL_GET_USER_ACTIVITIES_AND_REQUEST = "{call GetUserActivitiesAndRequest(?)}";
    public static final int ROWS_PER_PAGE = 10;
}
