package com.sharko.main;

public class MyImportanceComparator extends MyComparator {
	
	public MyImportanceComparator() {
	}

	public MyImportanceComparator(MyComparator nextComparator) {
		super(nextComparator);
	}
	
	@Override
	protected int myCompare(Record r1, Record r2) {
		if (r1.getImportance() < 5 && r2.getImportance() < 5 && r1.getImportance() > 0 && r2.getImportance() > 0) {
			return r1.getImportance()-r2.getImportance();
		}
		return 0;
	}

}
