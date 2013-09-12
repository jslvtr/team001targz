public class Parser {
	
	private String[] lines;
	
	public Parser(String[] lines) {
		this.lines = lines;
	}
	
	/**
	 * Method to get the Video Type of the input file (eg. MP4)
	 * @return the video type or an Exception if it fails
	 * @throws Exception: upon failure to get the video type.
	 */
	public String getVideoType() throws Exception {
		for(String line : lines) {
			if(line.contains("Input")) {
				String a = line.split("\\.")[1];
				String b = a.split("\'")[0];
				return b;
			}
		}
		throw new NoSuchFieldException("Input field in encode output not found.");
	}
	
	public String getDuration() throws Exception {
		for(String line : lines) {
			if(line.contains("Duration")){
				String a = line.split("Duration: ")[1];
				String b = a.split(",")[0];
				return b;
			}
		}
		throw new NoSuchFieldException("Duration field in encode output not found.");
	}
	
}
