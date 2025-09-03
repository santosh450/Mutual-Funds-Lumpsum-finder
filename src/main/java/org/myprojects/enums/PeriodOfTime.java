package org.myprojects.enums;

public enum PeriodOfTime {
    DAY_1(2),
    DAY_7(5),
    DAY_30(23),
    MONTH_3(66),
    MONTH_6(130),
    YEAR_1(260);

    private final int limit;

    PeriodOfTime(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
