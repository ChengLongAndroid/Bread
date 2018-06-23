package com.funcy.g01.base.performance;

import java.util.Comparator;

public class SingleWallTimeComparator implements Comparator<EventInfo> {

    @Override
    public int compare(EventInfo o1, EventInfo o2) {
        if((o1.correctNum.get() == 0 ? 0 : o1.totalWallTime.get() * 1f / o1.correctNum.get()) > (o2.correctNum.get() == 0 ? 0 : o2.totalWallTime.get() * 1f / o2.correctNum.get())) {
            return -1;
        } else {
            return 1;
        }
    }

}
