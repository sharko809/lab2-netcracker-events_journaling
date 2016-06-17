package com.sharko.main;

import java.util.Comparator;

public abstract class MyComparator implements Comparator<Record>{

	public MyComparator() {
	}

	public MyComparator(MyComparator nextComparator) {
		this.nextComparator = nextComparator;
	}

	protected MyComparator nextComparator = null;

	public int compare(Record r1, Record r2) {
		int t = myCompare(r1, r2);
		if (t == 0) {
			if (nextComparator != null) {
				System.out.println(nextComparator.getClass());
				return nextComparator.myCompare(r1, r2);
			} else {
				return t;
			}
		} else {
			return t;
		}
	}

	protected abstract int myCompare(Record r1, Record r2);

}
