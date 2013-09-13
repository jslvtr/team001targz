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
		try {
			while((line = br.readLine()) != null) {
				l.add(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			line = null;
		}

		lines = new Video[10];

		for(int i2 = 0; i2 < 10; i2++) {
			try {
				lines[i2] = new Video(l.get(i2).split(",")[0], l.get(i2).split(",")[1], l.get(i2).split(",")[2], Integer.parseInt(l.get(i2).split(",")[3]));
			} catch (Exception e) {
				System.out.println("Unable to convert to int.");
				e.printStackTrace();
			}
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
		if(videos == null) {
			return false;
		}
		Video[] newVideos = new Video[videos.length];
		for(int i = 1; i < videos.length; i++) {
			newVideos[i] = videos[i-1];
		}
		newVideos[0] = newVideo;
		for(Video video : newVideos) {
			String duration = video.getDuration();
			String path = video.getPath();
			String type = video.getPreviousType();
			int encodeTime = video.getEncodeTime();
			try {
				writer.write(path + "," + type + "," + duration + "," + String.valueOf(encodeTime) + "\n");
			} catch (IOException e) {
				System.out.println("Error writing Video file info to Last Videos Encoded file.");
				e.printStackTrace();
				return false;
			}
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
				} catch (IOException e) {
					System.out.println("Error writing Video file info to Last Videos Encoded file.");
					e.printStackTrace();
					return false;
				}
			} else {
				try {
					writer.write("\n");
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
		} catch (IOException e) {
			System.out.println("Unable to reset last encoded videos.");
			e.printStackTrace();
		}
	}

}
