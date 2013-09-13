import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VideosEncoded {

	private static final String fileName = "videos_encoded.txt";
	private static FileWriter writer;

	public VideosEncoded() {
		File f = new File(fileName);
		try {
			f.createNewFile();
			writer = new FileWriter(fileName);
		} catch (IOException e) {
			System.out.println("Error creating videos_encoded.txt file to hold last encoded videos.");
			e.printStackTrace();
		}
	}

	/**
	 * Method to read the file in order and return an array of the Video objects.
	 * @return an array of Video objects
	 */
	public Video[] getVideosEncoded() {
		Video[] lines = new Video[10];
		for(int i = 0; i < lines.length; i++) {
			lines[i] = null;
		}
		List<String> l = new ArrayList<String>();
		BufferedReader br = null;
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		String line;
		int index = 0;
		try {
			while((line = br.readLine()) != null) {
				l.add(line);
				lines[index] = new Video(line.split(",")[0], line.split(",")[1], line.split(",")[2], Integer.parseInt(line.split(",")[3]));
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			line = null;
		}
		
		try {
			br.close();
			fstream.close();
		} catch (Exception e) {
			System.out.println("Could not close BufferedReader. Is it open?");
		}
		return lines;
	}

	public boolean push(Video newVideo) {
		Video[] videos = getVideosEncoded();
		Video[] newVideos = new Video[videos.length];
		for(int i = 1; i < videos.length; i++) {
			newVideos[i] = videos[i-1];
			System.out.println(videos[i-1].getPath());
		}
		newVideos[0] = newVideo;
		for(Video video : newVideos) {
			if(video != null) {
				System.out.println("Hello, world!");
				String duration = video.getDuration();
				String path = video.getPath();
				String type = video.getPreviousType();
				int encodeTime = video.getEncodeTime();
				
				System.out.println(path);
	            System.out.println(duration);
	            System.out.println(type);
	            System.out.println(encodeTime + "ms");
				
				try {
					writer.write(path + "," + type + "," + duration + "," + String.valueOf(encodeTime) + "\n");
				} catch (IOException e) {
					System.out.println("Error writing Video file info to Last Videos Encoded file.");
					e.printStackTrace();
					return false;
				}
			}
		}
		try {
			writer.flush();
		} catch (IOException e) {
			System.out.println("Couldn't flush the writer.");
			//e.printStackTrace();
		}
		return true;
	}

	public boolean pop() {
		Video[] videos = getVideosEncoded();
		if(videos == null) {
			return false;
		}
		Video[] newVideos = new Video[videos.length];
		for(int i = 0; i < videos.length-1; i++) {
			newVideos[i] = videos[i];
		}
		newVideos[videos.length] = null;
		for(Video video : newVideos) {
			if(video != null) {
				String duration = video.getDuration();
				String path = video.getPath();
				String type = video.getPreviousType();
				int encodeTime = video.getEncodeTime();
				try {
					writer.write(path + "," + type + "," + duration + "," + String.valueOf(encodeTime) + "\n");
					writer.flush();
				} catch (IOException e) {
					System.out.println("Error writing Video file info to Last Videos Encoded file.");
					e.printStackTrace();
					return false;
				}
			} else {
				try {
					writer.write("\n");
					writer.flush();
				} catch (IOException e) {
					System.out.println("Error writing Video file info to Last Videos Encoded file.");
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	public void reset() {
		try {
			writer.write("");
			writer.flush();
		} catch (IOException e) {
			System.out.println("Unable to reset last encoded videos.");
			e.printStackTrace();
		}
	}

}
