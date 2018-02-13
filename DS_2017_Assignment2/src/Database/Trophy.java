package Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import sun.applet.Main;

/**
 * Class to represent a PlayStation game trophy. A trophy comes in
 * four ranks: bronze, silver, gold and platinum. The date the trophy was
 * earned and its respective game is also stored.
 * Created for Data Structures, SP2 2017
 * @author Fabian Tauriello taufp001
 * @version 1.0
 */
public class Trophy {
	
	public enum Rank { BRONZE, GOLD, SILVER, PLATINUM }
	public enum Rarity { RARE, VERY_RARE, ULTRA_RARE, UNCOMMON, COMMON }

	private String name;
	private Rank rank;
	private Rarity rarity;
	private Calendar obtained;
	private Game game;

	public Trophy() {}

	public Trophy(String name, Rank rank, Rarity rarity, Calendar obtained, Game game) {
		this.name = name;
		this.rank = rank;
		this.rarity = rarity;
		this.obtained = obtained;
		this.game = game;
	}

	public String toString() {
		// Create pattern for formatting date
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, YYYY");
		return "\"" + name + "\"" + ", rank: " + rank + ", rarity: " + rarity + ", obtained on: " + dateFormat.format(obtained.getTime());
	}

	public Object getName() {
		return name;
	}

	public Object getRank() {
		return rank;
	}

	public Object getRarity() {
		return rarity;
	}

	public Object getObtained() {
		return obtained;
	}

	public Object getGame() {
		return game;
	}
}
