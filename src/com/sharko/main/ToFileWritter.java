package com.sharko.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ToFileWritter {

	/**
	 * Writes record data to file "Records"
	 * 
	 * @param r
	 *            record to write to file
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static void writeToFile(Record r) throws NullPointerException, IOException {
		FileWriter fw = new FileWriter("Records", true);
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw.write(r.toString() + "\n");
			bw.close();
			fw.close();
		} finally {
			bw.close();
			fw.close();
		}
	}
}
