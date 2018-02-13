package Database;

import java.util.GregorianCalendar;


/**
 * Class to represent a single linked-list of Database.Game objects.
 * Created for Data Structures, SP2 2017
 * @author Fabian Tauriello taufp001
 * @version 1.0
 */
public class GameList {

	//*******************************************TESTING**********************************************
	public static void main(String[] args) {

		Game g1 = new Game("Assassin's Creed IV: Black Flag", new GregorianCalendar(2013, 10, 29), 10);
		Game g2 = new Game("Child of Light", new GregorianCalendar(2014, 4, 1), 24);
		Game g3 = new Game("Dragon Age: Inquisition", new GregorianCalendar(2014, 11, 18), 53);

		// Build list
		GameList gameList = new GameList(g1);

		// print out head of list
		System.out.println("game in head:					" + gameList.head);

		// add a game to list
		gameList.addGame(g2);

		// add a game that already exists
		gameList.addGame(g2);
		
		// add another game to the list
		gameList.addGame(g3);

		// display second game in list
		System.out.println("second game in list: 				" + gameList.head.getNext()); 


		// grab a game from list
		Game x = gameList.getGame("Child of Light");
		System.out.println("game returned with getGame method: 		" + x);
		
		// testing toString for an empty list
		GameList gameList2 = new GameList(null);
		System.out.println("String for an empty list: 			 " + gameList2.toString());
		// testing toString for an full (3) list
		System.out.println("\nString for a full list:\n" + gameList.toString());
		
		// remove a game from list by passing it a NAME
		gameList.removeGame(g1.getName());
		
		// remove a game from list by passing it a GAME
		//gameList.removeGame(g3);

		// testing toString for the same list above but an item removed
		System.out.println("\nString for the same list above but with an item removed:\n" + gameList.toString());
		
		
	}
	//*****************************************************************************************

	public Game head;

	public GameList(Game head) {
		this.head = head;
	}

	public String toString() {																				// ASK ABOUT RETURNING AN EMPTY LIST
		if (isEmpty())
			return "Empty game list";
		StringBuilder sb = new StringBuilder();
		Game currentGame = head;
		while(currentGame != null) {
			sb.append(currentGame.toString());
			if(currentGame.getNext() != null)
				sb.append('\n');
			currentGame = currentGame.getNext();
		}
		return sb.toString();
	}

	public void addGame(Game game) {
		if(game == null)
			throw new IllegalArgumentException();

		if(isEmpty()) 
			head = game;

		// Check if same game has already been added
		if(this.contains(game))
			return;
		// If it's not already in the list, determine the first available spot and place new game into GameList
		Game currentGame = head;
		while(currentGame.getNext() != null) {
			currentGame = currentGame.getNext();
		}
		currentGame.setNext(game);

	}

	public Game getGame(String name) {
		if(name == null)
			throw new IllegalArgumentException();

		Game currentGame = head;
		// Traverse list looking for game
		while(currentGame != null) {
			if (currentGame.getName() == name)
				return currentGame;
			currentGame = currentGame.getNext();
		}
		return null;
	}

	public void removeGame(String name) {
		Game gameToRemove = getGame(name);
		if(gameToRemove != null) {
			removeGame(gameToRemove);
		}
	}

	public void removeGame(Game game) {
		if(game == null)
			throw new IllegalArgumentException();

		Game currentGame = head;
		if(currentGame.equals(game)) {	
			head = currentGame.getNext();
			return;
		}

		// Traverse list looking for game (this block should remove middle and end games)
		while(currentGame.getNext() != null) {
			if (currentGame.getNext().equals(game)) {
				currentGame.setNext(currentGame.getNext().getNext());
				return;
			}
			currentGame = currentGame.getNext();
		}
	}

	/**
	 * Returns true if this list contains no elements.
	 * @return True if the list is empty. 
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	public boolean contains(Game obj) {
		// if the object is null, throw an exception
		if (obj == null)
			throw new NullPointerException();

		// create a reference of the start position in the list
		Game currentGame = head;
		// While there are nodes remaining in the list, advance through to reach the end.
		while (currentGame != null) {
			if (currentGame.equals(obj)) {
				return true;
			}
			currentGame = currentGame.getNext();
		}
		return false;
	}
	
	public int size() {
		if (head == null) {
			return 0;
		} else {
			int count = 0;
			Game currentGame = head;
			while (currentGame != null) {
				// advance down the list
				currentGame = currentGame.getNext(); 
				count++;
			}
			return count;	
		}		
	}


}
