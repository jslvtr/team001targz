import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
	
	private static final int NEW_ENCODE = 1, RESET = 0, VIEW_LAST_ENCODES = 2;
	private static final Ffmpeg ffmpeg = new Ffmpeg();
	
	public void runMenu() {
		showMenu();
		
		int input = getInput();
		while(input != -2) {
			switch(input) {
			case NEW_ENCODE:
				newEncode();
				break;
			case RESET:
				reset();
				break;
			case VIEW_LAST_ENCODES:
				viewEncodes();
				break;
			default:
				System.out.println("Could not execute any operations.");
				break;
			}
			showMenu();
			input = getInput();
		}
	}
	
	private void showMenu() {
		System.out.println("Hello, welcome to the FFmpeg Console Menu.");
		System.out.println("Enter -2 to exit.");
		System.out.println("Enter 0 to reset the last encoded list.");
		System.out.println("Enter 1 to encode a new video.");
		System.out.println("Enter 2 to view up to the last 10 encodes.");
		System.out.print("Enter your option: ");
	}
	
	private int getInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int input = -1;
		boolean OK = false;
		do {
			try {
				input = Integer.parseInt(reader.readLine());
				OK = true;
			} catch (Exception e) {
				System.out.print("Please enter a number: ");
				return getInput();
			}
		} while(OK == false);
		return input;
	}
	
	private void newEncode() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Which is the source file to encode? ");
		
		String src = "";
		try {
			src = reader.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read file name.");
			e.printStackTrace();
		}
		System.out.print("Type output file name: ");
		
		String dest = "";
		try {
			dest = reader.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read file name.");
			e.printStackTrace();
		}
		
		File source = new File(src);
		File destination = new File(dest);
		
		ffmpeg.convertFile(source, destination);
	}
	
	private void reset() {
		new VideosEncoded().reset();
	}
	
	private void viewEncodes() {
		Video[] videos = new VideosEncoded().getVideosEncoded();
		for(Video video : videos) {
			if(video != null) {
				System.out.println(video.getPath() + "\t" + video.getPreviousType() + "\t" + video.getDuration() + "\t");
				System.out.println("This video took " + video.getEncodeTime() + " s to encode.");
			}		
		}
	}
	
}
