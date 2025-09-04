package org.myprojects.utils;

import io.restassured.response.Response;
import org.myprojects.enums.ChangeType;
import org.myprojects.enums.PeriodOfTime;
import org.myprojects.reports.Log4jLogger;
import org.myprojects.restassured.RequestBuilder;

import java.util.*;
import java.util.stream.Collectors;

public final class DataUtils {

    private DataUtils(){}

    private static LinkedHashMap<String, Float> getRequiredPeriodOfData(HashMap<String, Float> data, PeriodOfTime time){
        LinkedHashMap<String, Float>  requiredPeriodOfData = data.entrySet().stream()
                .limit(time.getLimit())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        Log4jLogger.info(time.name()+": "+requiredPeriodOfData);
        return requiredPeriodOfData;
    }

    private static String getChangeFromMinimum(HashMap<String, Float> data, PeriodOfTime time){
        ArrayList<Float> navs = new ArrayList<>(getRequiredPeriodOfData(data, time).values());
        float todayNav = navs.get(0);
        Log4jLogger.info("Current NAV: "+todayNav);
        float min = navs.get(1);
        for(int i=1; i<navs.size(); i++){
            if(navs.get(i)<min){
                min = navs.get(i);
            }
        }
        Log4jLogger.info("Minimum NAV: "+min);
        float minChange = (todayNav-min)*100/min;
        Log4jLogger.info("Min change %: "+minChange);
        return String.format("%.2f", minChange)+"%";
    }

    private static String getChangeFromMaximum(HashMap<String, Float> data, PeriodOfTime time){
        ArrayList<Float> navs = new ArrayList<>(getRequiredPeriodOfData(data, time).values());
        float todayNav = navs.get(0);
        Log4jLogger.info("Current NAV: "+todayNav);
        float max = navs.get(1);
        for(int i=1; i<navs.size(); i++){
            if(navs.get(i)>max){
                max = navs.get(i);
            }
        }
        Log4jLogger.info("Maximum NAV: "+max);
        float maxChange = (todayNav-max)*100/max;
        Log4jLogger.info("Max change %: "+maxChange);
        return String.format("%.2f", maxChange)+"%";
    }

    public static List<LinkedHashMap<String, String>> getLumpSumData(int[] mfIDs, ChangeType type) {
        List<LinkedHashMap<String, String>> dataList = new ArrayList<>();
        for (int id : mfIDs) {
            Response response = RequestBuilder.getMFRequest(id);
            String mfName = JsonUtils.getMFName(response);
            LinkedHashMap<String, Float> navData = JsonUtils.getNavData(response);

            LinkedHashMap<String, String> mfData = new LinkedHashMap<>();
//          mfData.put("MF Name", type.name() + "-" + mfName);

            // Extract "Index Name" from the MF name using regex
            mfData.put("MF Name", mfName.replaceFirst("(?i).*?\\b(Nifty.*?)\\s+Index\\b.*", "$1").trim()
            );

            for (PeriodOfTime period : PeriodOfTime.values()) {
                String value = (type == ChangeType.MIN)
                        ? DataUtils.getChangeFromMinimum(navData, period)
                        : DataUtils.getChangeFromMaximum(navData, period);
                mfData.put(period.name(), value);
            }
            dataList.add(mfData);
        }
        return dataList;
    }

}
