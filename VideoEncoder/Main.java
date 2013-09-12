/*import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;*/

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*String[] lines;
		List<String> l = new ArrayList<String>();
		BufferedReader br = null;
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("/Users/jslvtr/Documents/workspace/VideoEncoder/bin/hello.txt");
			DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String line;
		try {
			while((line = br.readLine()) != null) {
				l.add(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			line = null;
		}

		lines = new String[l.size()];

		for(int i2 = 0; i2 < l.size(); i2++) {
			lines[i2] = l.get(i2);
		}

		Parser p = new Parser(lines);

		try {
			System.out.println(p.getVideoType());
			System.out.println(p.getDuration());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			br.close();
			fstream.close();
		} catch (Exception e) {
			System.out.println("Could not close BufferedReader. Is it open?");
		}*/
		
		Menu m = new Menu();
		m.runMenu();
	}

}
