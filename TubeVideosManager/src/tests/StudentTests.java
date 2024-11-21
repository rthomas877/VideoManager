package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import tubeVideosManager.Genre;
import tubeVideosManager.Video;
import tubeVideosManager.Playlist;
import tubeVideosManager.TubeVideosManager;

/**
 * 
 * You need student tests if you are asking for help during
 * office hours about bugs in your code. Feel free to use
 * tools available in TestingSupport.java
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

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
	
	Video v1 = new Video(title1, url1, duration1, genre1);
	Video v2 = new Video(title2, url2, duration2, genre2);
	Video v3 = new Video(title3, url3, duration3, genre3);
	
	
	@Test
	public void videoConstructorTest() { //tests basic constructor, tests getters and setters
		Video video = new Video(title1, url1, duration1, genre1);
		String expected = "Mission Impossibleurl1....hthsdjnf.com30FilmAnimation";
		String actual = "";
		actual += video.getTitle();
		actual += video.getUrl();
		actual += video.getDurationInMinutes();
		actual += video.getGenre();
		assertEquals(expected, actual); 
	}
	
	@Test
	public void videoConstructorExceptions() {
		assertThrows(IllegalArgumentException.class, () -> new Video(null, null, duration1, genre1)); //tests null title and url
		assertThrows(IllegalArgumentException.class, () -> new Video(title1, url1, 0, genre1)); //tests bad duration
		assertThrows(IllegalArgumentException.class, () -> new Video("", url1, 10, genre1)); //tests if blank
	}
	
	@Test
	public void videoConstructorPrivacyLeak() { //checks for privacy leak among comments arraylists
		Video video = new Video(title1, url1, duration1, genre1);
		video.addComments("Comment1");
		Video videoCopy = new Video(video);
		video.addComments("Comment2");
		assertTrue(!(video.getComments().equals(videoCopy.getComments()))); //checks if not equals
	}
	
	@Test
	public void videoAddGetComments() {
		Video video = new Video(title3, url3, duration3, genre3);
		assertThrows(IllegalArgumentException.class, () -> video.addComments(null)); //tests bad inputs
		assertThrows(IllegalArgumentException.class, () -> video.addComments("")); //tests bad inputs
		video.addComments("Really cool");
		video.addComments("Learned a lot");
		ArrayList<String> copyCommentsExp = video.getComments();
		video.addComments("WOW");
		assertTrue(!(copyCommentsExp.equals(video.getComments()))); //checks for privacy leak
	}
	
	@Test
	public void videoCompare() {
		Video video = new Video(title1, url1, duration1, genre1);
		Video video1 = new Video(title1, url2, duration2, genre2);
		Video video2 = new Video(title3, url3, duration1, genre1);
		assertEquals(-1, video.compareTo(video2)); //M compared to W (-10), which is returned as -1
		assertEquals(0, video.compareTo(video1)); //equal each other
		assertEquals(1, video2.compareTo(video1)); //video1 is (5) greater than video2
	}
	
	@Test
	public void videoEquals() {
		Video video = new Video(title1, url1, duration1, genre1);
		Video video1 = new Video(title1, url3, duration1, genre2);
		Video video2 = new Video(title3, url1, duration1, genre1);
		assertTrue(video.equals(video1)); //Both Mission Impossible title
		assertFalse(video.equals(video2)); //Mission Impossible vs WWII
	}
	
	@Test
	public void playlistConstructorTest() {
		Playlist p1 = new Playlist("Playlist1");
		assertTrue(p1.getName().equals("Playlist1"));
	}
	
	@Test
	public void playlistConstructorExceptions() {
		assertThrows(IllegalArgumentException.class, () -> new Playlist((String) null)); //tests null title, need to cast null as a String (not to conflict with other constructor that takes in single object
		assertThrows(IllegalArgumentException.class, () -> new Playlist("")); //tests blank title
	}
	
	@Test
	public void playlistPrivacyLeakAddingAndGetting() { //tests to ensure no playlist privacy leak, as well as adding to and getting playlist video titles
		Playlist p1 = new Playlist("Playlist1");
		p1.addToPlaylist(v1.getTitle());
		Playlist p2 = new Playlist(p1);
		p1.addToPlaylist(v2.getTitle());
		assertFalse(p1.getPlaylistVideosTitles().equals(p2.getPlaylistVideosTitles())); //tests to ensure two separate playlists are created
		assertThrows(IllegalArgumentException.class, () -> p1.addToPlaylist(null)); //tests null input
		assertThrows(IllegalArgumentException.class, () -> p1.addToPlaylist("")); //tests blank
	}
	
	@Test
	public void removeFromPlaylist() {
		Playlist p1 = new Playlist("Playlist1");
		p1.addToPlaylist(v1.getTitle());
		p1.addToPlaylist(v1.getTitle());
		p1.addToPlaylist(v2.getTitle());
		Playlist copy = new Playlist(p1);
		p1.removeFromPlaylistAll(v1.getTitle());
		assertFalse(p1.getPlaylistVideosTitles().equals(copy.getPlaylistVideosTitles())); //checks to see if videos were removed, avoiding privacy leak
		assertTrue(p1.getPlaylistVideosTitles().size() == 1); //checks to make sure both v1 video titles are removed, not just the first one
	}
	
	@Test
	public void shuffleTest() { //to reduce chances of shuffling back to original state, we'll use a for loop to add 100 video titles to the playlist
		Playlist p1 = new Playlist("Playlist1");
		for (int i = 0; i < 99; i++) {
			if (i <= 32) {
				p1.addToPlaylist(v1.getTitle());
			} else if (i <= 65) {
				p1.addToPlaylist(v2.getTitle());
			} else {
				p1.addToPlaylist(v3.getTitle());
			}
		}
		Playlist copy = new Playlist(p1);
		p1.shuffleVideoTitles(null);
		assertFalse(p1.getPlaylistVideosTitles().equals(copy.getPlaylistVideosTitles()));
	}
	
	@Test
	public void tvmGetAddTest() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addVideoToDB(title3, url3, duration3, genre3);
		ArrayList<Video> vd1 = new ArrayList<Video>();
		vd1.add(v1);
		vd1.add(v2);
		vd1.add(v3);
		assertEquals(vd1, tvm.getAllVideosInDB()); //checks to see if adding and getting works
		assertFalse(tvm.addVideoToDB(null, url1, duration1, genre1)); //checks bad input
	}

	@Test
	public void findVideoTest() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addVideoToDB(title3, url3, duration3, genre3);
		assertEquals(0, v1.compareTo(tvm.findVideo(title1))); //makes sure videos match up
		assertThrows(IllegalArgumentException.class, () -> tvm.findVideo(null)); //tests bad input
		assertThrows(IllegalArgumentException.class, () -> tvm.findVideo("")); //tests bad input
	}
	
	@Test
	public void commentsTest() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addComments(title1, "Really cool");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Really cool");
		assertEquals(expected, tvm.findVideo(title1).getComments());
		assertFalse(tvm.addComments(title1, null)); //tests bad input
		assertFalse(tvm.addComments(null, "Really cool")); //tests bad input
		assertFalse(tvm.addComments("", "Really cool")); //tests bad input
	}
	
	@Test
	public void addTVMPL() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addPlaylist("Playlist");
		tvm.addVideoToPlaylist(title1, "Playlist");
		tvm.addVideoToPlaylist(title2, "Playlist");
		
		Playlist expected = new Playlist("Playlist1");
		expected.addToPlaylist(title1);
		expected.addToPlaylist(title2);
		assertEquals(tvm.getPlaylist("Playlist").getPlaylistVideosTitles(), expected.getPlaylistVideosTitles()); //checks to see if titles were added correctly
		
		assertFalse(tvm.addVideoToPlaylist(title3, "Playlist1")); //invalid title and playlist
		assertFalse(tvm.addVideoToPlaylist("title", "Playlist")); //invalid title
		assertFalse(tvm.addVideoToPlaylist(title2, "Playlist1")); //invalid playlist
		assertFalse(tvm.addVideoToPlaylist(null, "Playlist1")); //invalid title
		assertFalse(tvm.addVideoToPlaylist(title2, null)); //invalid playlist
		assertFalse(tvm.addVideoToPlaylist("", "Playlist1")); //invalid title
		assertFalse(tvm.addVideoToPlaylist(title2, "")); //invalid playlist
	}
	
	@Test
	public void testClear() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addPlaylist("Playlist");
		tvm.addVideoToPlaylist(title1, "Playlist");
		tvm.addVideoToPlaylist(title2, "Playlist");
		assertEquals(tvm.getAllVideosInDB().size(),2); //should be the 2 titles added
		assertEquals(tvm.getPlaylists().size(),1); //should be the one playlist added
		tvm.clearDatabase();
		assertEquals(tvm.getAllVideosInDB().size(),0); //should completely cleared
		assertEquals(tvm.getPlaylists().size(),0); //should completely cleared
	}
	
	@Test
	public void testSearch() {
		TubeVideosManager tvm = new TubeVideosManager();
		tvm.addVideoToDB(title1, url1, duration1, genre1);
		tvm.addVideoToDB(title2, url2, duration2, genre2);
		tvm.addPlaylist("Playlist");
		tvm.addVideoToPlaylist(title1, "Playlist");
		tvm.addVideoToPlaylist(title2, "Playlist");
		tvm.addVideoToPlaylist(title3, "Playlist");
		Playlist expected = new Playlist("Playlist1");
		expected.addToPlaylist(title1);
		assertEquals(tvm.searchForVideos("Playlist", null, 50, null).getPlaylistVideosTitles(), expected.getPlaylistVideosTitles()); //checks search
	}
}