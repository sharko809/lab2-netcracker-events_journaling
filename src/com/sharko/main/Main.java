package com.sharko.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.sharko.exception.NoSuchIndexException;
import com.sharko.exception.NullRecordException;

public class Main {

	public static void main(String[] args) throws InterruptedException, NoSuchIndexException, NullRecordException {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LifeSim("LifeSim");
			}
		});

	}

}
