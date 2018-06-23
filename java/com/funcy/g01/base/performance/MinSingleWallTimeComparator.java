package com.funcy.g01.base.performance;

import java.util.Comparator;

public class MinSingleWallTimeComparator implements Comparator<EventInfo> {

    @Override
    public int compare(EventInfo o1, EventInfo o2) {
        if((o1.minCallNum.get() == 0 ? 0 : o1.minTotalWallTime.get() * 1f / o1.minCorrectNum.get()) > (o2.minCallNum.get() == 0 ? 0 : o2.minTotalWallTime.get() * 1f / o2.minCorrectNum.get())) {
            return -1;
        } else {
            return 1;
        }
    }

}
