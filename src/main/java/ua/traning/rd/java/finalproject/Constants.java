package ua.traning.rd.java.finalproject;

public final class Constants {
    public static final String EN_RU_LETTERS_ONLY_REGX_LEN_2_30 = "[А-ЯЁёҐґЄєІіЇїа-яA-Za-z\\d_\\.]{2,30}";
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


}
