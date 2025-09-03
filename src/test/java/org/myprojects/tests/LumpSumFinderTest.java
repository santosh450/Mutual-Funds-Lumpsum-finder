package org.myprojects.tests;

import org.junit.Test;
import org.myprojects.enums.ChangeType;
import org.myprojects.utils.DataUtils;
import org.myprojects.utils.ReportUtils;

import java.util.*;

public class LumpSumFinderTest {

    @Test
    public void getLumpSumReport(){
//        int[] mfIDs = new int[] {119288, 149039, 143341, 120684, 153089, 148726, 148519, 149283, 150738, 150902, 150452, 148703, 152881, 153003, 149894, 151649};
        int[] mfIDs = new int[] {119288, 143341, 153089, 148519, 150738, 150452, 152881, 149894};

        List<LinkedHashMap<String, String>> reportData = new ArrayList<>();
        reportData.addAll(DataUtils.getLumpSumData(mfIDs, ChangeType.MIN));
        reportData.addAll(DataUtils.getLumpSumData(mfIDs, ChangeType.MAX));

        ReportUtils.generateHtmlReport(reportData);

    }
}
