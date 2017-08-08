package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Util {

	
	public static ArrayList<String> readFile(String fileName) {
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			File fajl = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(fajl));
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
			return lines;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;


	}
}
