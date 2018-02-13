package Database;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Uses a binary search tree to store a database of
 * PlayStation users. Nodes are ordered by user unique key (see the
 * User class for more detail).
 * Created for Data Structures, SP2 2017
 * @author Fabian Tauriello
 * @version 1.0
 */
public class BinaryTree {

	public User root;
	// Stores a second return value from the recursive delete method that references the item that was stored in the tree
	public User deleteReturn; 
	// Stores a second return value from the recursive add method that indicates whether the item has been inserted
	// It is set to true if a new user is inserted and to false if the item already exists.
	public boolean addReturn; 


	public BinaryTree() {
		root = null;
	}

	public BinaryTree(User root) {
		this.root = root;
	}

	/**
	 * Constructs a new binary tree with data in its root leftTree as its left subtree and 
	 * rightTree as its right subtree
	 * @param key
	 * @param leftTree
	 * @param rightTree
	 */
	public BinaryTree(String username, Calendar dob, int level, BinaryTree leftTree, BinaryTree rightTree) {
		root = new User(username, dob, level);
		if (leftTree != null) {
			root.setLeft(leftTree.root);
		} else {
			root.setLeft(null);
		}
		if (rightTree != null) {
			root.setRight(rightTree.root);
		} else {
			root.setRight(null);
		}
	}

	// ****************************************************************

	/**
	 * Non-recursive starter method for find
	 * @param target The user being sought
	 * @return The User, if found, otherwise null
	 */
	public User find(User target) {
		return find(root, target);
	}

	/**
	 * Recursive find method 
	 * @param localRoot The local subtree's root
	 * @param target The user being sought 
	 * @return The user, if found, otherwise null
	 */
	public User find(User localRoot, User target) {
		// test the local root for null. If it's null, the user is not in the tree, so null is returned.
		if (localRoot == null) {
			return null;
		}
		// If the localRoot is not null, the following compares target to the user at the local root.
		if (target.getKey() == localRoot.getKey()) {
			return localRoot;
		} else if (target.getKey() < localRoot.getKey()) {
			// recursive call to find method, passing the LEFT subtree root as the parameter
			return find(localRoot.getLeft(), target);
		}
		else {
			// recursive call to find method, passing the RIGHT subtree root as the parameter
			return find(localRoot.getRight(), target);
		}
	}



	/**
	 * 
	 * 
	 * @param nodeToFind
	 * @return
	 */
	public boolean contains(User nodeToFind) {
		/*
		 * start from the root and compare root.key with n
		 * if root.key is greater than n that means we need to go to the left of the root
		 * if root.key is smaller than n that means we need to go to the right of the root
		 * if at any point of time root.key is equal to the n we have found the node, return true 
		 * if we reach to the leaves (end of the tree) return false, we didn't find the element
		 */
		User currentUser = root;
		while (currentUser != null) {
			// if target is in local root, return that node
			if (nodeToFind.getKey() == currentUser.getKey()) {
				return true;
			} else if (nodeToFind.getKey() < currentUser.getKey()) {
				currentUser = currentUser.getLeft();
			} else {
				currentUser = currentUser.getRight();
			}
		}
		return false;
	}


	public User findLargestChild(User parent) {
		// if the right child has no right child, it is the inorder predecessor
		if (parent.getRight().getRight() == null) {
			User returnValue = parent.getRight();
			parent.setRight(parent.getRight().getLeft());
			return returnValue;
		} else {
			return findLargestChild(parent.getRight());
		}
	}

	/**
	 * Making new friends is great. This method should add your new
	 * bestie to your database (tree). Remember that they should be
	 * added according to their key.
	 * @param friend The friend to be added
	 * @return true if  successfully added, false for all error cases
	 * @throws IllegalArgumentException if friend is null
	 */
	public boolean beFriend(User friend) throws IllegalArgumentException {		
		if (friend == null) {
			throw new IllegalArgumentException();
		} else {
			// The root is set to the value returned by the recursive method (a modified tree). 
			root = add(root, friend);
			if (addReturn == true) {
				return true;
			} else {
				return false;
			}					
		}
	}

	/**
	 * Recursive add method. The data field addReturn is set to true if the user is added to the tree, 
	 * false if the item is already in the tree.
	 * @param localRoot The local root of the subtree
	 * @param userToAdd The user to be inserted
	 * @return The new local root that now contains the inserted user
	 */
	public User add(User localRoot, User userToAdd) {
		if (localRoot == null) {
			// userToAdd is not in the tree - insert it. This executes when a null branch is reached.
			addReturn = true;
			return userToAdd;
		} else if (userToAdd.getKey() == localRoot.getKey()) {
			// userToAdd key is equal to localRoot key. This executes when the userToAdd is reached (already exists in the tree).
			addReturn = false;
			return localRoot;
		} else if (userToAdd.getKey() < localRoot.getKey()) {
			// userToAdd key is less than localRoot key. This will attempt to insert userToAdd in the LEFT subtree of the localRoot.
			localRoot.setLeft(add(localRoot.getLeft(), userToAdd));
			return localRoot;
		} else {
			// userToAdd key is greater than localRoot key. This will attempt to insert userToAdd in the RIGHT subtree of the localRoot.
			localRoot.setRight(add(localRoot.getRight(), userToAdd));
			return localRoot;
		}
	}

	/**
	 * Sometimes friendships don't work out. In those cases it's best
	 * to remove that "friend" altogether. This method should remove
	 * all trace of that "friend" in the database (tree).
	 * @param friend the "friend" to remove
	 * @return true if successfully removed, false for all error cases
	 * @throws IllegalArgumentException if "friend" is null
	 */
	public boolean deFriend(User friend) throws IllegalArgumentException {

		if (friend == null) {
			throw new IllegalArgumentException();
		} else if (!contains(friend)) {
			return false;
		} else {
			// The root is set to the value returned by the recursive method (a modified tree). 
			root = delete(root, friend);
			if (deleteReturn != null) {
				return true; 
			} else {
				return false;
			}
		}
	}


	/**
	 * Recursive delete method. The data field deleteReturn is equal to the deleted item as 
	 * it was stored in the tree or null if the item was not found.
	 * @param localRoot
	 * @param user
	 * @return
	 */
	public User delete(User localRoot, User userToDelete) {
		if (localRoot == null) {
			// userToDelete is not in the tree
			deleteReturn = null;
			return localRoot;
		}

		// Search for user to delete
		if (userToDelete.getKey() < localRoot.getKey()) {
			// userToDelete key is less than localRoot key.
			localRoot.setLeft(delete(localRoot.getLeft(), userToDelete));
			return localRoot;
		} else if (userToDelete.getKey() > localRoot.getKey()) {
			// userToDelete key is greater than localRoot key.
			localRoot.setRight(delete(localRoot.getRight(), userToDelete));
			return localRoot;
		} else { // userToDelete is now at local root
			deleteReturn = localRoot;
			if (localRoot.getLeft() == null) {
				// If there is no left child, return right child which can also be null
				return localRoot.getRight();
			} else if (localRoot.getRight() == null) {
				// If there is no right child, return left child 
				return localRoot.getLeft();
			} else { // userToDelete has 2 children, replace userToDelete with inorder predecessor
				if (localRoot.getLeft().getRight() == null) {

					localRoot.setUsername(localRoot.getLeft().getUsername());
					localRoot.setLevel(localRoot.getLeft().getLevel());
					localRoot.setKey(localRoot.getLeft().getKey());
					localRoot.setTrophies(localRoot.getLeft().getTrophies());
					localRoot.setGames(localRoot.getLeft().getGames());
					localRoot.setDob(localRoot.getLeft().getDob());

					localRoot.setLeft(localRoot.getLeft().getLeft());

					return localRoot;
				} else {// Search for the inorder predecessor and replace deleted user's data with inorder predecessor

					User x = findLargestChild(localRoot.getLeft());

					localRoot.setLeft(deleteReturn.getLeft());
					localRoot.setRight(deleteReturn.getRight());

					localRoot.setUsername(x.getUsername());
					localRoot.setLevel(x.getLevel());
					localRoot.setKey(x.getKey());
					localRoot.setTrophies(x.getTrophies());
					localRoot.setGames(x.getGames());
					localRoot.setDob(x.getDob());

					return localRoot;       
				}
			}
		}
	}



	/**
	 * In your quest to be the very best you need to know how many
	 * of your friends are ranked higher than you. This method should
	 * return the number of higher ranked users that the provided reference
	 * user, or zero if there are none (woot!).
	 * @param reference The starting point in the search
	 * @return Number of higher ranked users or -1 if user not found
	 * @throws IllegalArgumentException if reference is null
	 */
	public int countBetterPlayers(User reference) throws IllegalArgumentException {

		if (reference == null)
			throw new IllegalArgumentException();
		int levelToBeat = (int)reference.getLevel();
		if (contains(reference)) {
			User currentUser = root;
			User pred;
			int count = 0;
			while (currentUser != null) {
				if (currentUser.getLeft() == null) {
					if ((int)currentUser.getLevel() > levelToBeat) {
						count++;
					}
					currentUser = currentUser.getRight();
				} else {
					pred = currentUser.getLeft();
					while (pred.getRight() != null && pred.getRight() != currentUser) {
						pred = pred.getRight();
					}
					if (pred.getRight() == null) {
						pred.setRight(currentUser);
						currentUser = currentUser.getLeft();
					}
					else {
						pred.setRight(null);
						if ((int)currentUser.getLevel() > levelToBeat) {
							count++;
						}
						currentUser = currentUser.getRight();
					}
				}
			}
			return count;
		}
		return -1;
	}


	/**
	 * Bragging rights are well earned, but it's good to be sure that you're actually
	 * better than those over whom you're lording your achievements. This method
	 * should find all those friends who have a lower level than you, or zero if
	 * there are none (you suck).
	 * @param reference The starting point in the search
	 * @return Number of lower ranked users
	 * @throws IllegalArgumentException if reference is null
	 */
	public int countWorsePlayers(User reference) throws IllegalArgumentException {
		if (reference == null)
			throw new IllegalArgumentException();
		int levelToBeat = (int)reference.getLevel();
		if (contains(reference)) {
			User currentUser = root;
			User pred;
			int count = 0;
			while (currentUser != null) {
				if (currentUser.getLeft() == null) {
					if ((int)currentUser.getLevel() < levelToBeat) {
						count++;
					}
					//System.out.println(currentUser.getUsername() + " " + currentUser.getLevel());
					currentUser = currentUser.getRight();
				} else {
					pred = currentUser.getLeft();
					while (pred.getRight() != null && pred.getRight() != currentUser) {
						pred = pred.getRight();
					}
					if (pred.getRight() == null) {
						pred.setRight(currentUser);
						currentUser = currentUser.getLeft();
					}
					else {
						pred.setRight(null);
						if ((int)currentUser.getLevel() < levelToBeat) {
							count++;
						}
						//System.out.println(currentUser.getUsername() + " " + currentUser.getLevel());
						currentUser = currentUser.getRight();
					}
				}
			}
			return count;
		}
		return -1;
	}

	/**
	 * The best player may not necessarily be measured by who has the highest level.
	 * Platinum trophies are the holy grail, so it would be good to know who has the
	 * most. This method should return the user with the highest number of platinum trophies.
	 * @return the user with the most platinum trophies, or null if there are none
	 */
	public User mostPlatinums() {
		User result = mostPlats(root);
		return result;




	}
	// numOfPlats
	public User mostPlats(User root) {

		if (root.getLeft() == null || root.getRight() == null) {
			return root;
		} 
		User user1 = mostPlats(root.getLeft());                      
		User user2 = mostPlats(root.getRight());



		if (user1.getNumberOfPlats() > user2.getNumberOfPlats()) {
			return user1;
		} else if (user1.getNumberOfPlats() < user2.getNumberOfPlats()) {
			return user2;
		} else if (user1.getNumberOfPlats() == user2.getNumberOfPlats()){
			if (user1.getGolds() > user2.getGolds()) {
				return user1;
			} else {
				return user2;
			} 
		}
		return null;


	}

	/**
	 * You or one of your friends bought a new game! This method should add it to their
	 * GameList.
	 * @param username The user who has bought the game
	 * @param game The game to be added
	 */
	public void addGame(String username, Game game) throws IllegalArgumentException {
		if (username == null || game == null)
			throw new IllegalArgumentException();
		inOrderAddGame(root, username, game);
	}

	/**
	 * You or one of your friends achieved a new trophy! This method should add it to
	 * their trophies.
	 * @param username The user who has earned a new trophy
	 * @param trophy The trophy to be added   
	 */
	public void addTrophy(String username, Trophy trophy) throws IllegalArgumentException {
		if (username == null || trophy == null) 
			throw new IllegalArgumentException();
		inOrderAddTrophy(root, username, trophy);
	}

	/**
	 * You or one of your friends has gained one level! This is great news, except that
	 * it may have ruined your tree structure! A node move may be in order.
	 * @param username The user whose level has increased
	 */
	public void levelUp(String username) throws IllegalArgumentException {
		if (username == null)
			throw new IllegalArgumentException();
		User userToLevelUp = searchWithName(root, username);
		User newUser = new User(userToLevelUp.getUsername(), userToLevelUp.getDob(), userToLevelUp.getLevel() + 1);
		newUser.setTrophies(userToLevelUp.getTrophies());
		newUser.setGames(userToLevelUp.getGames());

		deFriend(userToLevelUp);
		//User newUser = new User (userToLevelUp.getUsername(), userToLevelUp.getDob(), userToLevelUp.getLevel());
		beFriend(newUser);
	}

	public User searchWithName(User localRoot, String name){
		if(localRoot != null){
			if(localRoot.getUsername().equals(name)){
				return localRoot;
			} else {
				User foundNode = searchWithName(localRoot.getLeft(), name);
				if(foundNode == null) {
					foundNode = searchWithName(localRoot.getRight(), name);
				}
				return foundNode;
			}
		} else {
			return null;
		}
	}

	/**
	 * As your friends list grows, adding with regular binary tree rules will
	 * result in an unbalanced and inefficient tree. One approach to fix this
	 * is to implement an add method that uses AVL balancing. This method should
	 * work in the same way as beFriend, but maintain a balanced tree according to
	 * AVL rules.
	 * @param friend The friend to be added
	 * @return true if  successfully added, false for all error cases
	 * @throws IllegalArgumentException if friend is null
	 */
	public boolean addAVL(User friend) throws IllegalArgumentException {
		if (friend == null) {
			throw new IllegalArgumentException();
		}
		return false;
	}

	/**
	 * A nice, neat print-out of your friends would look great in the secret scrap-book
	 * that you keep hidden underneath your pillow. This method should print out the
	 * details of each user, traversing the tree in order.
	 * @return A string version of the tree
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		User currentUser = root;
		User pred;
		while (currentUser != null) {
			if (currentUser.getLeft() == null) {
				sb.append(currentUser.toString() + '\n');
				currentUser = currentUser.getRight();
			} else {
				pred = currentUser.getLeft();
				while (pred.getRight() != null && pred.getRight() != currentUser) {
					pred = pred.getRight();
				}
				if (pred.getRight() == null) {
					pred.setRight(currentUser);
					currentUser = currentUser.getLeft();
				}
				else {
					pred.setRight(null);
					sb.append(currentUser.toString() + '\n');
					currentUser = currentUser.getRight();
				}
			}
		}
		return sb.toString().substring(0,sb.length()-1);

	}

	public void inOrder(User root) {
		if(root != null) {
			inOrder(root.getLeft());
			System.out.println(root);
			inOrder(root.getRight());
		}
	}

	public void inOrderAddGame(User root, String username, Game game) {
		if (root != null) {
			inOrderAddGame(root.getLeft(), username, game);
			if (root.getUsername() == username) {
				root.getGames().addGame(game);
			}
			inOrderAddGame(root.getRight(), username, game);
		}
	}

	public void inOrderAddTrophy(User root, String username, Trophy trophy) {
		if (root != null) {
			inOrderAddTrophy(root.getLeft(), username, trophy);
			if (root.getUsername() == username) {
				root.getTrophies().add(trophy);
			}
			inOrderAddTrophy(root.getRight(), username, trophy);
		}

	}



	// UNUSED METHODS ******************************************************************************************************8
	//
	//
	//	/**
	//	 * Method for getting left subtree from a node
	//	 * @return the root of left subtree
	//	 */
	//	public User getLeftSubtree(User node) {
	//		if (node != null && node.getLeft() != null) {
	//			//return new BinaryTree(node.getLeft());
	//			return node.getLeft();
	//		} else {
	//			return null;
	//		}	
	//	}
	//
	//	/**
	//	 * Method for getting right subtree from a node
	//	 * @return the root of right subtree
	//	 */
	//	public User getRightSubtree(User node) {
	//		if (node != null && node.getRight() != null) {
	//			//return new BinaryTree(node.getRight());
	//			return node.getRight();
	//		} else {
	//			return null;
	//		}
	//	}
	//
	//	

}




