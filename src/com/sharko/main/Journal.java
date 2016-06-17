package com.sharko.main;

import java.util.Date;

import com.sharko.exception.NoSuchIndexException;
import com.sharko.exception.NullJournalException;
import com.sharko.exception.NullRecordException;

/**
 * 
 * @author Sharko Daniel
 *         <p>
 *         Interface for working with Record class objects. Some kind of event
 *         journal.
 *         </p>
 */
public interface Journal {

	/**
	 * Add record.
	 * 
	 * @param r
	 *            Record class object.
	 */
	void add(Record r);

	/**
	 * Add all recordings from another journal.
	 * 
	 * @param j
	 *            Journal class object.
	 * @throws NullJournalException
	 * @throws NoSuchIndexException
	 */
	void add(Journal j) throws NullJournalException, NoSuchIndexException;

	/**
	 * Remove recording.
	 * 
	 * @param r
	 *            Record class object.
	 * @throws NullRecordException
	 */
	void remove(Record r) throws NullRecordException;

	/**
	 * Get recording.
	 * 
	 * @param index
	 *            position of recording (0-based)
	 * @return Recording at specified index.
	 * @throws NoSuchIndexException
	 */
	Record get(int index) throws NoSuchIndexException;

	/**
	 * Replace recording in definite position.
	 * 
	 * @param index
	 *            position of recording to be replaced (0-based)
	 * @param record
	 *            new recording to be set
	 * @throws NoSuchIndexException
	 */
	void set(int index, Record record) throws NullRecordException, NoSuchIndexException;

	/**
	 * Insert recording in specific position (other recordings shift).
	 * 
	 * @param index
	 *            position to insert recording
	 * @param record
	 *            recording to be inserted
	 * @throws NoSuchIndexException
	 */
	void insert(int index, Record record) throws NullRecordException, NoSuchIndexException;

	/**
	 * Delete recording by its index.
	 * 
	 * @param index
	 *            recording position
	 * @throws NoSuchIndexException
	 */
	void remove(int index) throws NoSuchIndexException;

	/**
	 * Delete recordings in specific range.
	 * 
	 * @param fromIndex
	 *            Range start.
	 * @param toIndex
	 *            Range end.
	 * @throws NoSuchIndexException
	 */
	void remove(int fromIndex, int toIndex) throws NoSuchIndexException;

	/**
	 * Delete all recordings.
	 */
	void removeAll();

	/**
	 * Get journal size.
	 * 
	 * @return Journal size. Only not null elements are counted.
	 */
	int size();

	/**
	 * Filters journal by string template.
	 * 
	 * @param s
	 *            template to look for
	 * @return Journal object, which stores only recordings containing
	 *         <b><i>s</i></b> template.
	 * @throws NullRecordException
	 */
	Journal filter(String s) throws NullRecordException;

	/**
	 * Filters journal by date.
	 * 
	 * @param fromDate
	 *            time range start
	 * @param toDate
	 *            time range end
	 * @return Journal object, which stores only recordings containing events
	 *         occurred in specific time range.
	 * @throws NullRecordException
	 */
	Journal filter(Date fromDate, Date toDate) throws NullRecordException;

	/**
	 * Sort events by date.
	 */
	void sortByDate();

	/**
	 * Sorts by importance and date simultaneously. Sort by increasing of
	 * importance; for recordings with equal importance - by date increasing.
	 */
	void sortByImportanceDate();

	/**
	 * Sorts by importance, message source and date simultaneously.
	 */
	void sortByImportanceSourceDate();

	/**
	 * Sorts by message source and date simultaneously.
	 */
	void sortBySourceDate();

	/**
	 * Prints all recordings to standard output stream.
	 */
	void printRecords();
}
