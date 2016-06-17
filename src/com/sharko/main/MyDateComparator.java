package com.sharko.main;

public class MyDateComparator extends MyComparator {

	public MyDateComparator() {
	}
	
	public MyDateComparator(MyComparator nextComparator) {
		super(nextComparator);
	}
	
	@Override
	protected int myCompare(Record r1, Record r2) {
		if (r1.getTime() != null && r2.getTime() != null) {
			return r1.getTime().compareTo(r2.getTime());
		}
		return 0;
	}

}
