package tubeVideosManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Implements the functionality of the videos manager. It implements the
 * TubeVideosManagerInt interface. The video manager keeps track of videos in an
 * ArrayList called videoDatabase. It also keeps track of playlists that have
 * been created using an Arraylist. We have declared these instance variables for
 * you.
 * 
 * Feel free to add any private instance variables and private methods you
 * understand you need. Do not add static methods, static variables or any
 * public methods except a default constructor.
 * 
 * Some methods have been provided; do not modify them.
 * 
 * @author UMCP CS Department
 *
 */
public class TubeVideosManager implements TubeVideosManagerInt {
	private ArrayList<Video> videoDatabase; // Represents the videos database
	private ArrayList<Playlist> playlists; // Represents the playlist

	/**
	 * Creates any necessary data structures and performs any required
	 * initialization.
	 */
	public TubeVideosManager() {
		this.videoDatabase = new ArrayList<Video>();
		this.playlists = new ArrayList<Playlist>();
	}

	/**
	 * Creates a video based on the parameter information and adds it to the
	 * videoDatase ArrayList. Returns true if video was added and false otherwise
	 * (e.g., invalid parameters were provided). This method will not check for the
	 * validity of parameters. If an exemption takes place while creating a video,
	 * the method will print the error message returned by the exception and return
	 * false. Use System.err.println to print the error message.
	 * 
	 * @return true if video added; false otherwise
	 */
	public boolean addVideoToDB(String title, String url, int durationInMinutes, Genre videoGenre) { //takes in video parameters and then adds it to tubemanager video DB
		try {
			Video toAdd = new Video(title, url, durationInMinutes, videoGenre);
			this.videoDatabase.add(toAdd);
		} catch (Exception e) {
			System.err.println(e.getMessage()); //doesn't end method call, still need to return false (different than throwing an exception which ends method right there) 
			return false;
		}
		return true;
	}

	/**
	 * Returns an ArrayList with copies of the videos in the database. Modifications
	 * to the returned ArrayList should not affect the database.
	 * 
	 * @return ArrayList of Video references
	 */
	public ArrayList<Video> getAllVideosInDB() { //returns a deep copy of the video DB
		ArrayList<Video> deepCopy = new ArrayList<Video>();
		for (Video video : this.videoDatabase) {
			deepCopy.add(video);
		}
		return deepCopy;
	}
	
	public ArrayList<Playlist> getPlaylists() { //returns a deep copy of all the playlists
		ArrayList<Playlist> deepCopy = new ArrayList<Playlist>();
		for (Playlist playlist : this.playlists) {
			deepCopy.add(playlist);
		}
		return deepCopy;
	}


	/**
	 * Find a video in the database, returning a copy of the video if found;
	 * otherwise null will be returned. If the parameter is null or blank (according
	 * to the String class isBlank() method) an
	 * IllegalArgumentException (with any error message) will be thrown.
	 * 
	 * @return reference to copy of Video found or null
	 * 
	 */
	public Video findVideo(String title) { //searches video DB for video with matching title, returns a reference of the video if found
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Invalid Title");
		} else {
			for (Video video : this.videoDatabase) {
				if (video.getTitle().equals(title)) {
					return video;
				}
			}
			return null;
		}
	}

	/**
	 * Adds the specified comments to the video with the specified title (if the
	 * video exists). If the parameters are invalid (null or empty string) the
	 * method will return false.
	 * 
	 * @return true if comments added; false otherwise
	 */
	public boolean addComments(String title, String comments) { //finds a video with matching title in the DB, and then adds the comment from the parameter to that video
		if (title == null || comments == null || title.isBlank() || comments.isBlank()){
			return false;
		} else {
			for (Video video : this.videoDatabase) {
				if (video.getTitle().equals(title)) {
					video.addComments(comments);
					return true;
				}
			} 
		}
		return false;
	}

	/**
	 * Creates and adds a playlist to the manager if a playlist with the specified
	 * name does not already exist. The method will throw an
	 * IllegalArgumentException (with any message) if the parameter is invalid (null
	 * or blank).
	 * 
	 * @return true if playlist has been added; false otherwise
	 */
	public boolean addPlaylist(String playlistName) { //creates a new playlist with parameter name, and then adds to playlists list if title hasn't already been added
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("Invalid Playlist");
		} else {
			Playlist newPlaylist = new Playlist(playlistName);
			for (Playlist playlist : this.playlists) {
				if (playlist.getName().equals(playlistName)) {
					return false;
				}
			}
			this.playlists.add(newPlaylist);
			return true;
		}
	}

	/**
	 * Returns array with the names of playlists. An empty array will be returned if
	 * there are no playlists.
	 * 
	 * @return Array of String references.
	 */
	public String[] getPlaylistsNames() { //returns a String array with every playlist name that has been added into the list
		int size = this.playlists.size();
		String[] names = new String[size];
		for (int i = 0; i < size; i++) {
			names[i] = this.playlists.get(i).getName(); //.get(i) makes it equal a playlist, the get name is to get the playlist name, not to String (that would print entire playlist)
		}
		return names;
	}

	/**
	 * Adds the title to the specified playlist if the title exists in the database
	 * and the playlist also exists.
	 * 
	 * @return true if the title is added to the playlist; false otherwise
	 */
	public boolean addVideoToPlaylist(String title, String playlistName) { //checks to see if both video and playlist titles exist, if so the specified video will be added to the specified playlist
		boolean inDB = false;
		boolean inPL = false;
		for (Video video : this.videoDatabase) {
			if (video.getTitle().equals(title)) {
				inDB = true;
			}
		}
		if (inDB == false) {
			return false;
		}
		for (Playlist playlist : this.playlists) {
			if (playlist.getName().equals(playlistName)) {
				inPL = true;
			}
			if (inDB == true && inPL == true) {
				this.findPlayListInternal(playlistName).addToPlaylist(title);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a copy of the playlist associated with the specified name; null
	 * otherwise (e.g., if playlist is not found). If the parameter is null or is
	 * blank string (according to String class isBlank() method) the method will
	 * throw an IllegalArgumentException (with any message) and perform no
	 * processing.
	 * 
	 * @return Reference to playlist or null
	 */
	public Playlist getPlaylist(String playlistName) { //finds the playlist from the list if it exists
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("Invalid Playlist");
		} else {
			return this.findPlayListInternal(playlistName);
		}
	}

	/**
	 * Clears the database by removing all videos and all playlists.
	 */
	public void clearDatabase() {
		this.playlists.clear();
		this.videoDatabase.clear();
	}

	/**
	 * Returns a playlist with videos that match the specified parameter criteria.
	 * The maximumDurationInMinutes represents the maximum duration allowed for a
	 * video. If a parameter is null or the duration is a negative value, that
	 * particular criteria will be ignored. For example, if we call the method with
	 * null, -1, null a playlist with the titles of all videos in the database will
	 * be returned. The returned playlist will not be added to the list of playlists
	 * kept by the system. 
	 */
	public Playlist searchForVideos(String playlistName, String title, int maximumDurationInMinutes, Genre genre) { //search playlist for video(s) that match parameters, null or -1 can be used to return all videos
		Playlist newPL = new Playlist(playlistName);
		for (Video video : this.videoDatabase) {
			//the following will check if specific values equal video in questions
			boolean titleCheck = video.getTitle().equals(title);
			boolean durationCheck = video.getDurationInMinutes() <= maximumDurationInMinutes;
			boolean genreCheck = video.getGenre().equals(genre);
			//the following will change checks to true if null or -1 (basically overrides it)
			if (title == null) {
				titleCheck = true;
			}
			if (maximumDurationInMinutes == -1) {
				durationCheck = true;
			}
			if (genre == null) {
				genreCheck = true;
			}
			if (titleCheck && durationCheck && genreCheck) {
				newPL.addToPlaylist(video.getTitle());
			}
		}
		return newPL;
	}

	/**
	 * Provided; please don't modify. Loads video information from the specified
	 * file into the database represented by the ArrayList videoDatabase.
	 * 
	 * Information for a video will appear in the file as follows: title, url,
	 * duration and genre (string) each on a line by itself. A line
	 * ("===============================") will mark the end of an entry. The last
	 * entry will also have this line. Do not add empty lines after the line
	 * identifying the end of data.
	 * 
	 * This method relies on the addVideoToDB method to load the data into the
	 * database. Feedback information is printed on the console if requested.
	 * 
	 * @param filename
	 * @return true if data loaded; false otherwise
	 * 
	 *         This method is brought to you by Pepsi, the CS Department and the
	 *         NFAB (National Foundation Against Bugs).
	 */
	public boolean loadVideosToDBFromFile(String filename, boolean printFeedback) { 
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}

		int count = 0;
		while (fileScanner.hasNextLine()) {
			String title = fileScanner.nextLine();
			String url = fileScanner.nextLine();
			int durationInMinutes = fileScanner.nextInt();
			fileScanner.nextLine(); // to consume \n
			String genreString = fileScanner.nextLine();
			fileScanner.nextLine(); // gets rid of line marking end of video info

			/* Mapping genre to Genre enum type must match one present in Genre.java */
			Genre videoGenre = null;
			for (Genre genre : Genre.values()) {
				if (genreString.equals(genre.toString())) {
					videoGenre = genre;
					break;
				}
			}
			if (videoGenre == null) {
				System.err.println("Invalid genre found while loading data");
				System.exit(0);
			}

			if (printFeedback) {
				String message = "Loading: " + title + ", ";
				message += url + ", ";
				message += durationInMinutes + ", ";
				message += videoGenre + ", ";
				System.out.println(message);
			}

			addVideoToDB(title, url, durationInMinutes, videoGenre);
			count++;
		}

		if (printFeedback) {
			System.out.println("Video entries loaded: " + count);
		}

		return true;
	}

	/**
	 * Provided; please don't modify. Statistics about video database and playlists
	 * 
	 * @return string with statistics
	 */
	public String getStats() {
		String answer = "***** Statistics *****\n";

		answer += "Number of video entries: " + videoDatabase.size() + "\n";
		answer += "Number of playlists: " + playlists.size() + "\n";

		for (Genre genre : Genre.values()) {
			int count = 0;
			for (Video video : videoDatabase) {
				if (genre.equals(video.getGenre())) {
					count++;
				}
			}
			answer += "Genre \"" + genre + "\" count " + count + "\n";
		}

		return answer;
	}

	/**
	 * Provided; please don't modify. Generates HTML for the specified playlist in
	 * the specified file. An IllegalArgumentException will be thrown if any
	 * parameter is null or if the playlist does not exist.
	 */
	public void genHTMLForPlaylist(String filename, String playlistName, boolean printFeedback) {
		Playlist playlist;
		String htmlBody = "";

		if (playlistName == null || playlistName.isBlank() || (playlist = findPlayListInternal(playlistName)) == null) {
			throw new IllegalArgumentException("TubeVideosManager genHTMLForPlaylist: Invalid parameter(s)");
		}
		htmlBody += "<h2>Playlist: " + playlistName + "</h2>\n";
		for (String title : playlist.getPlaylistVideosTitles()) {
			String url = findVideo(title).getUrl();

			htmlBody += "<strong>" + title + "</strong><br>";
			htmlBody += "<iframe width=\"100\" height=\"100\" ";
			htmlBody += "src=" + "\"" + url + "\">" + "</iframe><br><br>";
		}
		generateHTMLPageWithBody(filename, htmlBody, printFeedback);
	}

	/* ***** Private methods ***** */
	
	/* Returns reference to playlist if found and null otherwise. You must implement
	 * and use this method.
	 */
	private Playlist findPlayListInternal(String playlistName) { //shouldn't make a new copy of a playlist
		if (playlistName == null || playlistName.isBlank()) {
			throw new IllegalArgumentException("TubeVideosManager findPlaylistInternal: Invalid parameter(s)");
		}
		//loops through playlists to return playlist being searched for
		for (Playlist playlist : this.playlists) {
			if (playlist.getName().equals(playlistName)) {
				return playlist;
			}
		}
		return null;
	}

	/*
	 * Provided; please don't modify. Generates full HTML document using body
	 * parameter as HTML document body.
	 */
	private static void generateHTMLPageWithBody(String filename, String body, boolean printFeedback) {
		String page = "<!doctype html>\n";

		page += "<html lang=\"en\">\n";
		page += "<head>\n";
		page += "<title>Manager</title>\n";
		page += "<meta charset=\"utf-8\" />\n";
		page += "</head>\n";
		page += "<body>\n";
		page += body + "\n";
		page += "</body>\n";
		page += "</html>\n";

		writeStringToFile(filename, page);
		if (printFeedback) {
			System.out.println(
					filename + " has been created (You may need to refresh the Eclipse project to see the file).");
		}
	}

	/*
	 * Provided; please don't modify.
	 * 
	 * Writes a string to a file
	 */
	private static boolean writeStringToFile(String filename, String data) {
		try {
			FileWriter output = new FileWriter(filename);
			output.write(data);
			output.close();
		} catch (IOException exception) {
			System.err.println("ERROR: Writing to file " + filename + " failed.");
			return false;
		}
		return true;
	}
}
