
public class Queries {

	public static String artistSearch = "SELECT * FROM person WHERE stageName LIKE ? AND artistFlag=1";
	public static String artistAdd = "INSERT into person (firstName, lastName, birthDate, gender, artistFlag, stageName) VALUES(?,?,?,?,1,?)";
	public static String orderInventory = "INSERT into Inventory (NumPhysicalCopies, NumDigitalCopies, orderDate, cost, isOrdered) VALUES(?,?,?,?,1)";
	public static String mediaAdd = "INSERT into media VALUES((SELECT InventoryID FROM Inventory ORDER BY InventoryID DESC LIMIT 1),?,?,?,?)";
	public static String movieAdd = "INSERT into movie VALUES((SELECT InventoryID FROM Inventory ORDER BY InventoryID DESC LIMIT 1),?,?,?,?,?,0,'G')";
	public static String activateItem = "UPDATE Inventory SET isOrdered=0 WHERE InventoryID=?";
	public static String artistEdit = "UPDATE person SET firstName=?, lastName=?, birthDate=?, gender=?, stageName=? WHERE stageName=?";
	public static String tracksBeforeYear = "SELECT title FROM person as P INNER JOIN Artist_Tracks as A ON P.personID = A.artistID INNER JOIN Media as M ON A.trackID = M.inventoryID WHERE M.Year < ? AND P.stageName LIKE ?";
	public static String albumsPatron = "SELECT count(*) FROM person as P INNER JOIN log_system as L ON P.cardNum = L.cardNum INNER JOIN album as A ON L.inventoryID = A.invID WHERE P.personID = ? AND L.action = 'Checkout'";
	public static String popularActor = "SELECT P.firstName, P.lastName FROM media INNER JOIN log_system as LS ON media.InventoryID = LS.inventoryID INNER JOIN movie ON movie.InvID = LS.inventoryID INNER JOIN acts_in as AI ON movie.InvID = AI.movieID INNER JOIN person as P ON AI.actorID = P.personID WHERE LS.action = 'Checkout' GROUP BY media.Title ORDER BY count(media.Title) DESC LIMIT 1";
	public static String popularArtist = "SELECT P.firstName, P.lastName FROM media INNER JOIN log_system as LS ON media.InventoryID = LS.inventoryID INNER JOIN album ON album.InvID = LS.inventoryID INNER JOIN artist_album as AA ON album.InvID = AA.albumID INNER JOIN person as P ON AA.artistID = P.personID WHERE LS.action = 'Checkout' GROUP BY media.Title ORDER BY count(media.Title) * media.Length DESC LIMIT 1";
	public static String mostMoviePatron = "SELECT P.firstName, P.lastName, Q.act_count FROM (SELECT cardNum, COUNT(action) act_count FROM log_system WHERE inventoryID IN (SELECT invID FROM movie) AND action = 'Checkout' GROUP BY cardNum ORDER BY act_count DESC LIMIT 1) AS Q INNER JOIN person as P on Q.cardNum = P.cardNum";
}
