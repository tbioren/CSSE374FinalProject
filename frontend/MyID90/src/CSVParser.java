import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Many thanks to https://howtodoinjava.com/java/io/parse-csv-files-in-java/
 */
public class CSVParser {
	private File f;
	private Scanner lineScanner;

	CSVParser(File f, int linesSkipped) {
		this.f = f;
		try {
			this.lineScanner = new Scanner(f);
			for (int i = 0; i < linesSkipped; i++) {
				lineScanner.nextLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to open file");
		}
	}

	/**
	 * @return the contents of the next row of the file. Each cell is an entry in
	 *         the list. If there are no more rows, returns an empty list
	 */
	public List<String> getRow() {
		if (!lineScanner.hasNext()) {
			return new ArrayList<String>();
		}
		String line = lineScanner.nextLine();
		Scanner rowScanner = new Scanner(line);
		rowScanner.useDelimiter(",");
		List<String> l = new ArrayList<String>();
		while (rowScanner.hasNext()) {
			l.add(rowScanner.next());
		}
		rowScanner.close();
		return l;
	}

	/**
	 * Closes the scanner this class uses to parse files
	 */
	public void close() {
		lineScanner.close();
	}

	/**
	 * @return true, if there are more lines to scan
	 */
	public boolean hasNext() {
		return lineScanner.hasNext();
	}
}
