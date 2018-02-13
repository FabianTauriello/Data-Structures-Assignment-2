package Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Class to represent a PlayStation user.
 * Created for Data Structures, SP2 2017
 * @author Fabian Tauriello taufp001
 * @version 1.0
 */
public class User {

	public static void main(String[] args) { //********************************************TESTING
		
		String username;
		int level;
		ArrayList<Trophy> trophyArrayTest;
		Game g1;
		Game g2;
		Game g3;
		GameList gameListTest;
		Calendar dob;
		
		username = "lewdster";
		level = 4;
		trophyArrayTest = new ArrayList<Trophy>(); {
			trophyArrayTest.add(new Trophy("Whole game, no kills", Trophy.Rank.GOLD, Trophy.Rarity.ULTRA_RARE,
					new GregorianCalendar(2015, 12, 25), new Game("Fallout 4", new GregorianCalendar(2015, 11, 10), 10)));
			trophyArrayTest.add(new Trophy("Big dragon, you killed it", Trophy.Rank.SILVER, Trophy.Rarity.UNCOMMON,
					new GregorianCalendar(2014, 5, 15), new Game("Dragon Age: Inquisition", new GregorianCalendar(2014, 3, 13), 10)));
			trophyArrayTest.add(new Trophy("How did you manage to get this on PS4?!", Trophy.Rank.PLATINUM, Trophy.Rarity.ULTRA_RARE,
					new GregorianCalendar(2016, 1, 21), new Game("Halo 5", new GregorianCalendar(2016, 1, 21), 10)));
			trophyArrayTest.add(new Trophy("Watched a cutscene, here's a trophy", Trophy.Rank.BRONZE, Trophy.Rarity.COMMON,
					new GregorianCalendar(2015, 12, 15), new Game("Lego Star Wars", new GregorianCalendar(2015, 8, 5), 10)));
		}
		g1 = new Game("Assassin's Creed IV: Black Flag", new GregorianCalendar(2013, 10, 29), 10);
		g2 = new Game("Child of Light", new GregorianCalendar(2014, 4, 1), 10);
		g3 = new Game("Dragon Age: Inquisition", new GregorianCalendar(2014, 11, 18), 10);	
		gameListTest = new GameList(g1);
		g1.setNext(g2);
		g2.setNext(g3);
		dob = new GregorianCalendar(1980, 5, 16);
		
		User user = new User(username, dob, level);
		user.setGames(gameListTest);
		user.setTrophies(trophyArrayTest);
		
		System.out.println(user.toString());
		
		
	}
	//**********************************************************************************************

	private String username;
	private int level;
	private double key;
	private ArrayList<Trophy> trophies;
	private GameList games;
	private Calendar dob;
	private User left;
	private User right;
	private User parent;

	public User(String username, Calendar dob, int level) {
		this.username = username;
		this.dob = dob;		
		this.level = level;
		// Calculate unique key based on this user's username and level.
		this.key = calculateKey();
		
	}

	/**
	 * DO NOT MODIFY THIS METHOD
	 * This method uses the username and level to create a unique key.
	 * As we don't want the username's hash to increase the level, it's first converted
	 * to a floating point, then added to the level.
	 * @return the unique key
	 */
	public double calculateKey() {
		int hash = Math.abs(username.hashCode());
		// Calculate number of zeros we need
		int length = (int)(Math.log10(hash) + 1);
		// Make a divisor 10^x
		double divisor = Math.pow(10, length);
		// Return level.hash
		return level + hash / divisor;
	}

	public String toString() {																			
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, YYYY");
		StringBuilder sb = new StringBuilder();
		sb.append("User: " + username + '\n');
		
		sb.append("\nTrophies: \n");
		// test if trophy list is empty before adding any trophies to output
		if (trophies != null) {
			for(Trophy eachTrophy : trophies)
				sb.append(eachTrophy.toString() + '\n');
		}
		
		sb.append("\nGames: \n");
		if (games != null)
			sb.append(games.toString() + '\n');
		
		sb.append("\nBirth Date: " + dateFormat.format(dob.getTime()));
		return sb.toString();
	}
	
	/**
	 * Determine whether this tree is a leaf
	 * @return true if the root has no children
	 */
	public boolean isLeaf() {
		if (this.getLeft() == null && this.getRight() == null)
			return true;
		return false;
	}
	
	public boolean hasOneChild() {
		if ((this.getRight() != null && this.getLeft() == null) || (this.getRight() == null && this.getLeft() != null)) {
			return true;
		}
		return false;
	}
	
	public boolean hasTwoChildren() {
		if (this.getRight() != null && this.getLeft() != null) {
			return true;
		}
		return false;
	}


	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username; 
	}

	public Calendar getDob() {
		return dob;
	}
	
	public void setDob(Calendar dob) {
		this.dob = dob;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public double getKey() {
		return key;
	}
	
	public void setKey(double key) {
		this.key = key;
	}

	public void setGames(GameList games) {
		this.games = games;
	}
	
	public GameList getGames() {
		return games;
	}

	public void setTrophies(ArrayList<Trophy> trophies) {
		this.trophies = trophies;

	}

	public User getLeft() {
		return left;
	}

	public void setLeft(User left) {
		this.left = left;
//		if (left != null)
//			left.setParent(this);
	}

	public User getRight() {
		return right;
	}

	public void setRight(User right) {
		this.right = right;
//		if (right != null)
//			right.setParent(this);
	}

	public ArrayList<Trophy> getTrophies() {
		return trophies;
	}

	public int getNumberOfPlats () {
		int numPlats = 0;
		for (Trophy eachTrophy : trophies) {
			if (eachTrophy.getRank() ==  Trophy.Rank.PLATINUM) {
				numPlats++;
			}
		}
		return numPlats;
	}
	
	public int getGolds () {
		int numGolds = 0;
		for (Trophy eachTrophy : trophies) {
			if (eachTrophy.getRank() ==  Trophy.Rank.GOLD) {
				numGolds++;
			}
		}
		return numGolds;
	}
	

}
