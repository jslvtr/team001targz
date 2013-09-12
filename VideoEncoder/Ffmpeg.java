import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ffmpeg {
	
	/**
	 * Returns a string containing the file
	 * @param file the file to be read
	 * @return the file info (command line output)
	 */
	public String getFileInfo(File file) {
		// RUN `ffmpeg -i <file>
		String cmd = "ffmpeg -i \"" + file.getAbsolutePath() + "\"";
                return _system(cmd);
	}
	
	/**
	 * Method to call FFmpeg to convert a video file
	 * @param src the file to convert
	 * @param dest the output file
         * 
         * 
	 * @return true if success on conversion, false otherwise.
	 */
	public boolean convertFile(File src, File dest) {
                //Get and parse video info
                String[] info = getFileInfo(src).split("\n");
                Parser parser = new Parser(info);
                String previousType;
                try {
                        previousType = parser.getVideoType();
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        return false;
                }
                
                //Record the starting time
		int startTime = (int) (System.currentTimeMillis() / 1000L);
		
                //Run the convert command
                String cmd = "ffmpeg -i \"" + src.getAbsolutePath() + "\" -vcodec libx264 -acodec libfdk_aac -vb 2500000 -ab 192000 -preset fast \"" + dest.getAbsolutePath() + "\"";
                _system(cmd);
                
                //Record the ending time
                int endTime = (int) (System.currentTimeMillis() / 1000L);
                
                //Calculate encode time
                int encodeTime = endTime - startTime;
                try {
                        //Create video object
                        Video video = new Video(dest.getAbsolutePath(), parser.getDuration(), previousType, encodeTime);
                } catch (Exception ex) {
                        //An error occured, tell the user
                        System.out.println(ex.getMessage());
                        return false;
                }
                
                //Add to queue
                VideosEncoded ve = new VideosEncoded();
                
		
		//END TIMER (RESULT IN MS)
		return true;
	}
        
        private String _system(String cmd) {
                String output = "";
                try {
                        Process p = Runtime.getRuntime().exec(cmd);
                        p.waitFor();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line=reader.readLine();

                        while (line != null) {    
                            output += line + "\n";
                            line = reader.readLine();
                        }
                }
                catch(IOException ex) {}
                catch(InterruptedException ex) {}
                
                return output;
        }
}
