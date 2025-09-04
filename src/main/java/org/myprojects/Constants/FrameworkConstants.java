package org.myprojects.Constants;

public final class FrameworkConstants {
    private FrameworkConstants() {
    }

    public static final String HTMLREPORT_PATH = System.getProperty("user.dir") + "/reports/MutualFundReport.html";
    public static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    public static final String CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

}
