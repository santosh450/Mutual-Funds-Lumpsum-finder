package org.myprojects.utils;

import org.myprojects.reports.Log4jLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public final class ReportUtils {

    private ReportUtils(){}


    public static void generateHtmlReport(List<LinkedHashMap<String, String>> dataList) {

        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='en'><head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>MF LumpSum Advice</title>");
        html.append("<link href='https://fonts.googleapis.com/css2?family=Roboto&display=swap' rel='stylesheet'>");
        html.append("<style>");
        html.append("body {font-family: 'Roboto', sans-serif; margin: 20px; background-color: #f9f9f9;}");
        html.append("h2 {text-align: center; color: #333;}");
        html.append("table {border-collapse: collapse; width: 100%; box-shadow: 0 2px 8px rgba(0,0,0,0.1);}");
        html.append("th, td {border: 1px solid #ddd; padding: 12px; text-align: center;}");
        html.append("th {background-color: #4CAF50; color: white;}");
        html.append("tr:hover {background-color: #c8e6c9;}");
        html.append("</style>");
        html.append("</head><body>");
        html.append("<h2>📊 Mutual Funds - LumpSum Advice - ").append(yesterday).append("</h2>");
        html.append("<table>");

        // Header Row
        Set<String> headers = dataList.get(0).keySet();
        html.append("<tr>");
        for (String header : headers) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</tr>");

        // Data Rows with custom background and red text for negative values
        int totalRows = dataList.size();
        int half = totalRows / 2;

        for (int i = 0; i < totalRows; i++) {
            LinkedHashMap<String, String> row = dataList.get(i);
            String bgColor = (i < half) ? "#fff3e0" : "#e3f2fd"; // light orange / light blue
            html.append("<tr style='background-color:").append(bgColor).append(";'>");

            for (String header : headers) {
                String value = row.get(header);
                if (value != null && value.startsWith("-")) {
                    html.append("<td><span style='color:red;'>").append(value).append("</span></td>");
                } else {
                    html.append("<td>").append(value != null ? value : "").append("</td>");
                }
            }

            html.append("</tr>");
        }

        html.append("</table></body></html>");

        // Save HTML file
        try {
            Files.write(Paths.get("reports/MutualFundReport.html"), html.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Log4jLogger.info("✅ Modern HTML Report generated: MutualFundReport.html");
    }
}
