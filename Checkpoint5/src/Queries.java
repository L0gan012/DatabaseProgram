
public class Queries {

	public static String artistSearch = "SELECT * FROM person WHERE stageName=? AND artistFlag=true";
	public static String artistAdd = "INSERT into person (firstName, lastName, birthDate, gender, artistFlag, stageName) VALUES(?,?,?,?,true,?)";
}
