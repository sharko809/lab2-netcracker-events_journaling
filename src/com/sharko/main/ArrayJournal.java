package com.sharko.main;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.sharko.exception.NoSuchIndexException;
import com.sharko.exception.NullJournalException;

/**
 * 
 * @author Sharko Daniel
 *         <p>
 *         In this class we work ONLY with array objects. No collection in this
 *         class :) That's why some methods look stupid :)
 *         </p>
 */
public class ArrayJournal implements Journal, Serializable {

	private static final long serialVersionUID = -649658185919357312L;
	Record[] record = new Record[3];
	int recordsNumber = size();

	/**
	 * Creates new ArrayJournal with recordings from another.
	 * 
	 * @param aj
	 *            another array journal
	 */
	public ArrayJournal(ArrayJournal aj) {
		System.out.println("copy constructor");
		this.record = aj.record;
	}

	/**
	 * Creates an empty Record[] array with 50 elements.
	 */
	public ArrayJournal() {
		this.record = new Record[50];
	}

	/**
	 * @see com.sharko.main.ArrayJournal#addElement(Record[], Record)
	 */
	@Override
	public void add(Record r) {
		// if last element of array is null
		// than the array isn't full yet and we
		// can write to it
		if (record[record.length - 1] == null) {
			for (int i = 0; i < record.length; i++) {
				if (record[i] == null) {
					record[i] = r;
					break;
				}
			}
		} else {
			// if array is full - create extra space for a new record
			record = addElement(record, r);
		}
	}

	/**
	 * Adds an element to array appending it for 1 element.
	 * 
	 * @param arr
	 *            array to append
	 * @param r
	 *            element to be added
	 * @return array with element added in the end
	 */
	private Record[] addElement(Record[] arr, Record r) {
		// I take new array size as 1.5 of old array.
		int newSize = (int) (arr.length*1.5);
		arr = Arrays.copyOf(arr, newSize);
		arr[arr.length - 1] = r;
		return arr;
	}

	@Override
	public void add(Journal j) throws NullJournalException, NoSuchIndexException {
		if (j != null) {
			for (int i = 0; i < j.size(); i++) {
				add(j.get(i));
			}
		} else {
			throw new NullJournalException();
		}
	}

	@Override
	public void remove(Record r) {
		// we definitely can go through a loop to look for
		// recording, but why make your life more
		// complicated? Apache commons is fine
		record = (Record[]) ArrayUtils.removeElement(record, r);
	}

	@Override
	public Record get(int index) {
		if (index > 0 && index < record.length) {
			return record[index];
		} else {
			System.out.println("No such index.");
			return null;
		}
	}

	@Override
	public void set(int index, Record r) {
		if (index > 0 && index < record.length) {
			record[index] = r;
		} else {
			System.out.println("No such index.");
		}

	}

	@Override
	public void insert(int index, Record r) {
		if (index >= 0 && index < record.length) {
			ArrayUtils.add(record, index, r);
		} else {
			System.out.println("No such index");
		}
	}

	/**
	 * {@inheritDoc} The elements are shifted to the left and the last element
	 * becomes null.
	 */
	@Override
	public void remove(int index) {
		if (index >= 0 && index < record.length) {
			for (int i = index; i < record.length - 1; i++) {
				record[index] = record[index + 1];
			}
			record[record.length - 1] = null;
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void remove(int fromIndex, int toIndex) {
		// why don't I shift the elements?
		// well, why should I? This NULLs can easily be
		// overwriten by add method
		if (fromIndex >= 0 && fromIndex < record.length && toIndex >= 0 && toIndex < record.length) {
			for (int i = fromIndex; i < toIndex; i++) {
				record[i] = null;
			}
		} else {
			System.out.println("No such index");
		}
	}

	@Override
	public void removeAll() {
		// just create new array has to be OK
		record = new Record[5];
	}

	@Override
	public int size() {
		int counter = 0;
		for (int i = 0; i < record.length - 1; i++) {
			if (record[i] != null) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public Journal filter(String s) {
		Journal j_temp = new ArrayJournal();
		for (int i = 0; i < record.length - 1; i++) {
			if (record[i] != null && record[i].toString().contains(s)) {
				j_temp.add(record[i]);
			}
		}
		return j_temp;
	}

	@Override
	public Journal filter(Date fromDate, Date toDate) {
		Journal j_temp = new ArrayJournal();
		for (int i = 0; i < record.length - 1; i++) {
			if (record[i].getTime().after(fromDate) && record[i].getTime().before(toDate)) {
				j_temp.add(record[i]);
			}
		}
		return null;
	}
	
	@Override
	public void sortByDate() {
		Arrays.sort(record, new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyDateComparator().compare(r1, r2);
			}
		});
	}

	@Override
	public void sortByImportanceDate() {
		Arrays.sort(record, new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyImportanceComparator(new MyDateComparator()).compare(r1, r2);
			}
		});
	}

	@Override
	public void sortByImportanceSourceDate() {
		Arrays.sort(record, new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MyImportanceComparator(new MySourceComparator(new MyDateComparator())).compare(r1, r2);
			}
		});
	}

	@Override
	public void sortBySourceDate() {
		Arrays.sort(record, new MyComparator() {
			@Override
			protected int myCompare(Record r1, Record r2) {
				return new MySourceComparator(new MyDateComparator()).compare(r1, r2);
			}
		});
	}

	@Override
	public void printRecords() {
		for (int i = 0; i < record.length; i++) {
			if (record[i] != null) {
				System.out.println(record[i].toString());
			}
		}
	}

}
