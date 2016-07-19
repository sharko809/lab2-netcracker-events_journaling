package com.sharko.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.sharko.main.Record.Importance;

public class LifeModel {

	ArrayJournal aj = new ArrayJournal();
	private byte[] mainField = null;
	private byte[] backField = null;

	private int width, height;
	private int[] neighborOffsets = null;
	private int[][] neighborXYOffsets = null;

	public LifeModel(int width, int height) {
		this.width = width;
		this.height = height;
		mainField = new byte[width * height];
		backField = new byte[width * height];
		neighborOffsets = new int[] { -width - 1, -width, -width + 1, -1, 1, width - 1, width, width + 1 };
		neighborXYOffsets = new int[][] { { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 }, { 0, 1 },
				{ 1, 1 } };
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void clear() {
		Arrays.fill(mainField, (byte) 0);
	}

	public void setCell(int x, int y, byte c) {
		mainField[y * width + x] = c;
	}

	public byte getCell(int x, int y) {
		return mainField[y * width + x];
	}


	public void simulate() throws IOException {
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				int j = y * width + x;
				byte n = countNeighbors(j);
				byte simulated = simulateCell(mainField[j], n);
				backField[j] = simulated;
			}
		}

		for (int x = 0; x < width; x++) {
			int j = width * (height - 1);
			byte n = countBorderNeighbors(x, 0);
			backField[x] = simulateCell(mainField[x], n);
			n = countBorderNeighbors(x, height - 1);
			backField[x + j] = simulateCell(mainField[x + j], n);
		}

		for (int y = 1; y < height - 1; y++) {
			int j = width * y;
			byte n = countBorderNeighbors(0, y);
			backField[j] = simulateCell(mainField[j], n);
			n = countBorderNeighbors(width - 1, y);
			backField[j + width - 1] = simulateCell(mainField[j + width - 1], n);
		}


		byte[] t = mainField;
		mainField = backField;
		backField = t;
	}


	private byte countNeighbors(int j) {
		byte n = 0;
		for (int i = 0; i < 8; i++) {
			n += mainField[j + neighborOffsets[i]];
		}
		return n;
	}


	private byte countBorderNeighbors(int x, int y) {
		byte n = 0;
		for (int i = 0; i < 8; i++) {
			int bx = (x + neighborXYOffsets[i][0] + width) % width;
			int by = (y + neighborXYOffsets[i][1] + height) % height;
			n += mainField[by * width + bx];
		}
		return n;
	}


	private byte simulateCell(byte self, byte neighbors) throws NullPointerException, IOException {
		return (byte) (self == 0 ? (neighbors == 3 ? checkLife((byte) 1) : checkLife((byte) 0))
				: neighbors == 2 || neighbors == 3 ? checkLife((byte) 1) : checkLife((byte) 0));
	}


	private byte checkLife(byte self) throws NullPointerException, IOException {
		if (self == 0) {
			ToFileWritter.writeToFile(new Record(new Date(), Importance.WARNING, "", "cell died"));
			return self;
		} else {
			ToFileWritter.writeToFile(new Record(new Date(), Importance.NORMAL, "", "cell is alive"));
			return self;
		}
	}

}
