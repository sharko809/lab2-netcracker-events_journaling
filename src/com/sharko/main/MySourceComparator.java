package com.sharko.main;

public class MySourceComparator extends MyComparator {

	public MySourceComparator () {
	}
	
	public MySourceComparator(MyComparator nextComparator) {
		super(nextComparator);
	}
	
	@Override
	protected int myCompare(Record r1, Record r2) {
		if (r1.getSource() != null && r2.getSource() != null) {
			return r1.getSource().compareTo(r2.getSource());
		}
		return 0;
	}

}
