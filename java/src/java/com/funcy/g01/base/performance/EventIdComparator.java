package com.funcy.g01.base.performance;

import java.util.Comparator;

public class EventIdComparator implements Comparator<EventInfo>{

	@Override
	public int compare(EventInfo o1, EventInfo o2) {
		return o1.eventId.compareToIgnoreCase(o2.eventId);
	}

}
