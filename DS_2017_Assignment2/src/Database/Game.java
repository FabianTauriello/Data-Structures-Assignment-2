package Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class to represent a PlayStation game.
 * Created for Data Structures, SP2 2017
 * @author Fabian Tauriello taufp001
 * @version 1.0
 */
public class Game {
	
	private String name;
	private Calendar released;
	private Game next;
	private int totalTrophies;
	
    public Game() {}

    public Game(String name, Calendar released, int totalTrophies) {
    	this.name = name;
    	this.released = released;
    	this.totalTrophies = totalTrophies;
    }

    public String toString() { 
    	// Create pattern for formatting date
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, YYYY");
		return "\"" + name + "\"" + ", released on: " + dateFormat.format(released.getTime());
		
    }

    @Override
    public boolean equals(Object o) {
    	// test if object passed in is the same class as Game. If it isn't, it's obviously not equal so return false
    	if (o.getClass() != this.getClass())
    		return false;
    	// Cast object o to Game class 
		Game castedObject = (Game)o;
		// Return true if instance variables match up. Return false if not.
		return (this.name == castedObject.name && 
				this.released == castedObject.released &&
				this.next == castedObject.next && 
				this.totalTrophies == castedObject.totalTrophies);
    }

	public Object getReleased() {
		return released;
	}

	public Object getTotalTrophies() {
		return totalTrophies;
	}

	public String getName() {										// CONFIRM THIS IS OK
		return name;
	}

	public void setNext(Game g2) {
		next = g2;	
	}

	public Game getNext() {
		return next;
	}
}
