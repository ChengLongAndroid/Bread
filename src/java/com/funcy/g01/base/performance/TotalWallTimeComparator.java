package com.funcy.g01.base.performance;

import java.util.Comparator;

public class TotalWallTimeComparator implements Comparator<EventInfo> {

    @Override
    public int compare(EventInfo o1, EventInfo o2) {
        if(o1.totalWallTime.get() > o2.totalWallTime.get()) {
            return -1;
        } else {
            return 1;
        }
    }

}
