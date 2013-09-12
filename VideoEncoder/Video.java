
public class Video {
	
	private String duration;
	private String path;
	private String previousType;
	private int encodeTime;
	
	public Video(String path, String duration, String previousType, int encodeTime) {
		this.duration = duration;
		this.path = path;
		this.previousType = previousType;
		this.encodeTime = encodeTime;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public String getPreviousType() {
		return this.previousType;
	}
	
	public int getEncodeTime() {
		return this.encodeTime;
	}
	
}
