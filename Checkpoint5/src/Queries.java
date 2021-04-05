
public class Queries {

	public static String artistSearch = "SELECT * FROM person WHERE stageName=? AND artistFlag=true";
	public static String artistAdd = "INSERT into person (firstName, lastName, birthDate, gender, artistFlag, stageName) VALUES(?,?,?,?,true,?)";
	public static String orderMovie = "";
	public static String artistEdit = "UPDATE ";
	public static String tracksBeforeYear = "SELECT M.Title, M.Length, M.Year, M.Genre FROM track AS T, person AS P, artist_tracks AS AT, media AS M WHERE P.stageName=? AND AT.artistID=P.personID AND ";
	public static String albumsPatron = "";
	public static String popularActor = "";
	public static String popularArtist = "";
	public static String mostMoviePatron = "";
}
