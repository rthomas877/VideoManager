package tubeVideosManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A playlist keeps tracks of titles and it has a name. An ArrayList of string
 * references stores titles.
 * 
 * @author UMCP CS Department
 *
 */

public class Playlist {
	private String name;
	private ArrayList<String> videoTitles;

	/**
	 * Initializes playlist with the specified name and creates an empty ArrayList.
	 * If the parameter is null or is a blank string (according to String class
	 * isBlank() method) the method will throw an IllegalArgumentException (with any
	 * message) and perform no processing.
	 * 
	 * @param name
	 */
	public Playlist(String name) { //initializes a playlist
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Invalid Playlist");
		} else {
			this.name = name;
			this.videoTitles = new ArrayList<String>();
		}
	}

	/**
	 * Get method for name
	 * 
	 * @return name string
	 */
	public String getName() { //returns playlist name
		return this.name;
	}

	/**
	 * Initializes the current object so changes to the current object will not
	 * affect the parameter object.
	 * 
	 * @param playlist
	 */
	public Playlist(Playlist playlist) { //copies existing playlist avoiding privacy leak
		this.name = playlist.name;
		this.videoTitles = new ArrayList<String>(playlist.videoTitles);
	}

	/**
	 * Provided; please don't modify. toString for class
	 * 
	 * @return string with object info
	 */
	public String toString() { 
		String answer = "Playlist Name: " + name + "\n";

		answer += "VideoTitles: " + videoTitles;

		return answer;
	}

	/**
	 * Adds the title to the Arraylist storing titles. We can add the same video
	 * title several times. If the parameter is null or is a blank string (according
	 * to String class isBlank() method) the method will throw an
	 * IllegalArgumentException (with any message) and perform no processing.
	 * 
	 * @param videoTitle
	 * @return true if title is added; false otherwise
	 */
	public boolean addToPlaylist(String videoTitle) { //adds a video title to the current playlist
		if (videoTitle == null || videoTitle.isBlank()) {
			throw new IllegalArgumentException("Invalid Video Title");
		} else {
			this.videoTitles.add(videoTitle);
			return true;
		}
	}

	/**
	 * Get method for the ArrayList of titles. You must avoid privacy leaks.
	 * 
	 * @return ArrayList with titles
	 */
	public ArrayList<String> getPlaylistVideosTitles() { // need to perform deep copy iteratively, returns copy of video titles list
		ArrayList<String> deepCopy = new ArrayList<String>();
		for (String videos : this.videoTitles) {
			deepCopy.add(videos);
		}
		return deepCopy;
	}

	/**
	 * Removes all instances of the title parameter from the ArrayList of titles. If
	 * the parameter is null or is a blank string (according to String class
	 * isBlank() method) the method will throw an IllegalArgumentException (with any
	 * message) and perform no processing.
	 * 
	 * @param videoTitle
	 * @return true if the ArrayList (videoTitles) was changed as a result of
	 *         calling this method and false otherwise.
	 * 
	 */
	
	public boolean removeFromPlaylistAll(String videoTitle) { //creates a new list that adds from the old list any video not with the title parameter, then returns the new list (but now referenced to the old list)
		boolean flag = false;
		if (videoTitle == null || videoTitle.isBlank()) {
			throw new IllegalArgumentException("Invalid Video Title");
		} else {
			ArrayList<String> videoTitlesCopy = new ArrayList<String>();
			for (String video : this.videoTitles) {
				if (!(video.equals(videoTitle))) {
					videoTitlesCopy.add(video);
				} else {
					flag = true;
				}
			}
		this.videoTitles = videoTitlesCopy;
		}
		return flag;
	}

	/**
	 * Randomizes the list of titles using a random parameter and
	 * Collections.shuffle. If the parameter is null, call Collections.shuffle with
	 * just the ArrayList.
	 * 
	 * @param random
	 */
	public void shuffleVideoTitles(Random random) { //uses collections.shuffle to sort videotitles with either random being null, or seeded specifically
		if (random == null) {
			Collections.shuffle(videoTitles);
		} else {
			Collections.shuffle(videoTitles, random);
		}
	}
}
