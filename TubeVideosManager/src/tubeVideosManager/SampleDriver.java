package tubeVideosManager;

import java.util.Random;
import java.util.ArrayList;

/**
 * Sample driver that illustrates some of the functionality of the system you
 * need to implement. A text file with the generated output is part of this code
 * distribution. Feel free to use this driver to write your student tests.
 * 
 * @author UMCP CS Department
 *
 */
public class SampleDriver {

	public static void main(String[] args) {
		
		String title1 = "Mission Impossible";
		String url1 = "url1....hthsdjnf.com";
		int duration1 = 30;
		Genre genre1 = Genre.FilmAnimation;
		
		String title2 = "Shane Gillis";
		String url2 = "url1....shdguydF.com";
		int duration2 = 70;
		Genre genre2 = Genre.Comedy;
		
		String title3 = "WWII";
		String url3 = "url1....FHJahrfuh.com";
		int duration3 = 40;
		Genre genre3 = Genre.Documentary;
		
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addPlaylist("Playlist");
		tvm.addVideoToPlaylist(title1, "Playlist");
		tvm.addVideoToPlaylist(title2, "Playlist");
		tvm.addVideoToPlaylist(title3, "Playlist");
		System.out.println(tvm.searchForVideos("Playlist", null, -1, null));
		
		Video video12 = new Video(title3, url3, duration3, genre3);
		Video video11 = new Video(title1, url3, duration3, genre3);
		
		System.out.println(video11.compareTo(video12));
		
		System.out.println(video12.toString());
		String output = "";
		String a1 = "Abba";
		String b1 = a1;
		a1 = "Baba";
		System.out.println(a1 + " " + b1);
		/* Creating video and adding comments */
		output += "============== One ==============\n";
		String title = "How to Draw in Java Tutorial";
		String url = "https://www.youtube.com/embed/ifVf9ejuFWI";
		int durationInMinutes = 17;
		Genre genre = Genre.Educational;
		Video video = new Video(title, url, durationInMinutes, genre);
		System.out.println(video.toString());
		video.addComments("Nice video");
		video.addComments("Recommended");
		output += video;
		output += "Comments: " + video.getComments() + "\n\n";

		System.out.println(output);
		
		output += "============== Two ==============\n";
		/* Creating playlist and adding video */
		Playlist playlist = new Playlist("Favorites");
		playlist.addToPlaylist(title);
		playlist.addToPlaylist(title); // We can add title multiple times
		playlist.addToPlaylist("Hello");
		playlist.addToPlaylist("ByeBye");
		playlist.addToPlaylist("Adios");
		playlist.addToPlaylist("Hola");
		playlist.addToPlaylist("Ciao");
		output += playlist + "\n";
		Random random = new Random();
		playlist.shuffleVideoTitles(random);
		output = playlist + "\n";
		System.out.println(output);
		playlist.removeFromPlaylistAll(title);
		output += "After remove" + "\n";
		output += playlist + "\n\n";

		String output1 = output;
		System.out.println(output1);
		output = "";
		
		output += "============== Three ==============\n";
		TubeVideosManager tubeVideosManager = new TubeVideosManager();

		/* Adding first video */
		tubeVideosManager.addVideoToDB(title, url, durationInMinutes, genre);

		/* Adding second video */
		title = "Git & GitHub Crash Course for Beginners";
		url = "https://www.youtube.com/embed/SWYqp7iY_Tc";
		durationInMinutes = 33;
		genre = Genre.Educational;
		tubeVideosManager.addVideoToDB(title, url, durationInMinutes, genre);
		
		/* Getting videos */
		ArrayList<Video> videos = tubeVideosManager.getAllVideosInDB();
		output += videos + "\n\n";

		output1 = output;
		System.out.println(output1);
		output = "";
		
		output += "============== Cuatro ==============\n";
		/* Loading Videos */
		tubeVideosManager = new TubeVideosManager();
		boolean printFeedback = false;
		tubeVideosManager.loadVideosToDBFromFile("videosInfoSet2.txt", printFeedback);
		videos = tubeVideosManager.getAllVideosInDB();
		output += videos + "\n";
		String playlistName = "ToWatch";
		tubeVideosManager.addPlaylist(playlistName);
		title = "One Day More";
		tubeVideosManager.addVideoToPlaylist(title, playlistName);
		playlist = tubeVideosManager.getPlaylist(playlistName);
		output += "Displaying Playlist\n" + playlist + "\n\n";
		output += tubeVideosManager.getStats() + "\n";

		output1 = output;
		System.out.println(output1);
		output = "";
		
		output += "============== Five ==============\n";
		playlistName = "Music Videos";
		title = null; // Any title will match
		durationInMinutes = -1; // We don't care about duration
		Genre targetGenre = Genre.Music;
		playlist = tubeVideosManager.searchForVideos(playlistName, title, durationInMinutes, targetGenre);
		output += "Playlist Resulting from Search\n";
		output += playlist + "\n\n";

		output1 = output;
		System.out.println(output1);
		output = "";
		
		output += "============== Six ==============\n";
		playlistName = "MySelection";
		tubeVideosManager.addPlaylist(playlistName);
		tubeVideosManager.addVideoToPlaylist("One Day More", playlistName);
		tubeVideosManager.addVideoToPlaylist("Better brain health | DW Documentary", playlistName);
		output += tubeVideosManager.getPlaylist(playlistName);
		String htmlFile = "sampleHTML.html";
		printFeedback = false;
		tubeVideosManager.genHTMLForPlaylist(htmlFile, playlistName, printFeedback);
		output += "\nFile " + htmlFile + " has HTML for playlist\n\n";

		output1 = output;
		System.out.println(output1);
		output = "";
		
		/* Final Output */
		System.out.println(output);
	}
}
