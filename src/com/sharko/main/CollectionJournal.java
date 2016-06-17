package com.sharko.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.sharko.exception.NoSuchIndexException;

public class CollectionJournal implements Journal, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1497520835958827631L;

	ArrayList<Record> record = new ArrayList<Record>();
	int recordsNumber = record.size();

	/**
	 * Creates a new journal taking records from the specified journal.
	 * 
	 * @param cj
	 *            journal from which recording would be copied
	 */
	public CollectionJournal(CollectionJournal cj) {
		System.out.println("copy constructor");
		this.record = cj.record;
	}

	/**
	 * Creates a new empty journal.
	 */
	public CollectionJournal() {
		record = new ArrayList<Record>();
	}

	@Override
	public void add(Record r) {
		// if (r != null) {
		record.add(r);
		// } else {
		// System.out.println("Can't add null object");
		// }
	}

	@Override
	public void add(Journal j) throws NoSuchIndexException {
		// if (j != null) {
		for (int i = 0; i < j.size(); i++) {
			add(j.get(i));
		}
		// } else {
		// System.out.println("Can't add null object");
		// }

	}

	@Override
	public void remove(Record r) {
		if (r != null) {
			record.remove(r);
		} else {
			System.out.println("Can't remove null object");
		}
	}

	@Override
	public Record get(int index) {
		if (index > 0 && index < record.size()) {
			return record.get(index);
		} else {
			System.out.println("No such index");
			return null;
		}
	}

	@Override
	public void set(int index, Record r) {
		if (index > 0 && index < record.size()) {
			record.set(index, r);
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void insert(int index, Record r) {
		if (index > 0 && index < record.size()) {
			// if (r != null) {
			record.add(index, r);
			// } else {
			// System.out.println("Can't add null object");
			// }
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void remove(int index) {
		if (index > 0 && index < record.size()) {
			record.remove(index);
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void remove(int fromIndex, int toIndex) {
		if (fromIndex >= 0 && fromIndex < record.size() && toIndex >= 0 && toIndex < record.size()) {
			record.subList(fromIndex, toIndex).clear();
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void removeAll() {
		record.clear();
	}

	@Override
	public int size() {
		return record.size();
	}

	@Override
	public Journal filter(String s) {
		CollectionJournal cj_temp = new CollectionJournal();
		for (int i = 0; i < record.size(); i++) {
			if (record.get(i).toString().contains(s)) {
				cj_temp.add(record.get(i));
			}
		}
		return cj_temp;
	}

	@Override
	public Journal filter(Date fromDate, Date toDate) {
		Journal j_temp = new CollectionJournal();
		for (int i = 0; i < record.size(); i++) {
			if (record.get(i).getTime().after(fromDate) && record.get(i).getTime().before(toDate)) {
				j_temp.add(record.get(i));
			}
		}
		return j_temp;
	}

	@Override
	public void sortByDate() {
		record.sort(new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyDateComparator().compare(r1, r2);
			}
		});
	}

	@Override
	public void sortByImportanceDate() {
		record.sort(new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyImportanceComparator(new MyDateComparator()).compare(r1, r2);
			}
		});
	}

	@Override
	public void sortByImportanceSourceDate() {
		record.sort(new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyImportanceComparator(new MySourceComparator(new MyDateComparator())).compare(r1, r2);
			}
		});
	}

	@Override
	public void sortBySourceDate() {
		record.sort(new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MySourceComparator(new MyDateComparator()).compare(r1, r2);
			}
		});
	}

	@Override
	public void printRecords() {
		for (int i = 0; i < record.size(); i++) {
			if (record.get(i) != null) {
				System.out.println(record.get(i));
			}
		}
	}

}
