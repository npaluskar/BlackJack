

import java.io.IOException;
import java.util.Scanner;

/**
 * @author npaluskar 
 *     Aim - Implement a command-line blackjack game using Java.
 *         Rules - 
 *         1. Dealer must hit on soft 17 
 *         2. Single Deck. 
 *          3. Player is not allowed to split cards.
 */

public class PlayBlackJack {

	static int cardCount = 0;
	static long dealerCards = 0, userCards = 0;
	static int userWins = 0, round = 0;
	static double percentage = 0.0;
	static int noOfAcesDealer = 0, noOfAcesUser = 0;
	static boolean gameOver = false;
	static int deckLength=52;

	public PlayBlackJack() {
		// TODO Auto-generated constructor stub
		dealerCards = 0;
		userCards = 0;
		noOfAcesDealer = 0;
		noOfAcesUser = 0;
	}

	public static void main(String[] args) throws IOException {
		int decesion;
		char ch;
		PlayBlackJack blackjack = new PlayBlackJack();
		Scanner sc = new Scanner(System.in);
		System.out.println("Hit Enter to start");
		if (sc.nextLine() != null)
			blackjack.startGame();

		while (true) {
			if (blackjack.checkResult() == null) {
				try{
				System.out.println("Do you want to Hit or Stand? press 1 for Hit and 0 for Stand");
				decesion = sc.nextInt();
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("Not a valid input");
					break;					
				}
				/*
				 * User decides to play
				 * 
				 */ 
				if (decesion == 1) {
					if (blackjack.checkResult() == null) {
						userCards += blackjack.pickACard("User");
						//User total is above 21 and user has ace. Then decrease the total by 10.
						if (userCards > 21 && noOfAcesUser > 0)
							userCards -= 10;
						System.out.println("User total is " + userCards);
						//if dealer total is less than 21 and user total is greater than dealer total then dealer will draw a card
						if (dealerCards <= 21 && dealerCards < userCards && userCards < 21) {
							dealerCards += blackjack.pickACard("Dealer");
							if (noOfAcesDealer > 0 && dealerCards > 21)
								dealerCards -= 10;
							System.out.println("Dealer total is " + dealerCards);
						}
					} else {
						System.out.println("Do you want to continue? Press y");
						ch = sc.next().charAt(0);
						if (ch != 'y')
							break;
						else {
							new PlayBlackJack();
							continue;
						}
					}

				} else if (decesion == 0) {
					/*
					 * User decides to stand. check the dealer cards for the
					 * result
					 */
					if (dealerCards > userCards) {
						round++;
						//user stands and dealer total is greater than user total so dealer wins
						System.out.println("Dealer wins!!!");
						System.out.println("Winning % of user " + (double) ((userWins * 100) / round));
						System.out.println("Do you want to continue? Press y");
						ch = sc.next().charAt(0);
						if (ch != 'y')
							break;
						else {
							new PlayBlackJack();
							continue;
						}
					}
					if (dealerCards == userCards && !blackjack.isSoft17()) {
						round++;
						//dealer total and user total is same as well as dealer has hit soft 17 then its a tie.
						System.out.println("Its a Tie!!!");
						System.out.println("Do you want to continue? Press y");
						ch = sc.next().charAt(0);
						if (ch != 'y')
							break;
						else {
							new PlayBlackJack();
							continue;
						}
					}
					while (dealerCards < 21 || dealerCards < userCards) {
						if (!blackjack.isSoft17()) {
							//dealer total is less than 21 and user stands and dealer has not hit soft 17
							if (dealerCards > userCards) {
								round++;
								System.out.println("Dealer Wins !!!");
								System.out.println("Winning % of user " + (double) ((userWins * 100) / round));
								gameOver = true;
							} else if (dealerCards < userCards) {
								userWins++;
								round++;
								System.out.println("User Wins !!!");
								System.out.println("Winning % of user " + (double) ((userWins * 100) / round));
								gameOver = true;
							}
							break;
						}
						//Dealer total is above 21 and dealer has ace. Then decrease the total by 10.
						dealerCards += blackjack.pickACard("Dealer");
						System.out.println("Dealer total is " + dealerCards);
						if (noOfAcesDealer > 0 && dealerCards > 21)
							dealerCards -= 10;

					}
					if (gameOver) {
						new PlayBlackJack();
						continue;
					}
					if (blackjack.checkResult() != null) {

						System.out.println("Do you want to continue? Press y");
						ch = sc.next().charAt(0);
						if (ch != 'y')
							break;
						else {
							new PlayBlackJack();
							continue;
						}
					}
				} else {
					System.out.println("Please enter Valid Input");

				}
			} else {
				System.out.println("Do you want to continue? Press y");
				ch = sc.next().charAt(0);
				if (ch != 'y')
					break;
				else {
					new PlayBlackJack();
					continue;
				}
			}
		}
	}

	/*
	 * Checking the result for the game
	 * 
	*/
	public String checkResult() {

		if (userCards >= 21 || dealerCards >= 21) {
			if ((dealerCards > 21 && userCards <= 21) || (userCards == 21 && dealerCards != 21)
					|| (userCards > dealerCards && userCards <= 21)) {
				userWins++;
				round++;
				System.out.println("User wins !!!");
				System.out.println("Winning % of user " + (double) ((userWins * 100) / round));
				return "User Wins !!!";
			}
			if ((userCards != 21 && dealerCards == 21) || (dealerCards > userCards && dealerCards <= 21)
					|| (dealerCards <= 21 && userCards > 21)) {
				round++;
				System.out.println("Dealer wins !!!");
				System.out.println("Winning % of user " + (double) ((userWins * 100) / round));
				return "Dealer wins !!!";
			}
		}

		return null;
	}

	
	
	private void startGame() {
		// TODO Auto-generated method stub
/*
 * Start the game and pick the initial cards
 * 
*/		dealerCards = pickACard("Dealer");
		userCards = pickACard("User");
		cardCount += 1;

	}

	private long pickACard(String user) {
		// TODO Auto-generated method stub
		/*
		 * pick a card from a shuffled deck
		*/
		long card = shuffle(12);
		String suit = getSuit();
		long cardVal = cardVal(card);
		System.out.println(
				"Picked " + cardName(card) + " for " + user + " is of Suit " + suit + " with value " + cardVal);
		if (user.equals("Dealer") && cardVal == 11)
			noOfAcesDealer++;
		if (user.equals("User") && cardVal == 11)
			noOfAcesUser++;
		deckLength--;
		return cardVal(card);

	}

	private boolean isSoft17() {
		if ((noOfAcesDealer == 1 && dealerCards < 18) || (noOfAcesDealer == 0 && dealerCards < 17))
			return true;
		return false;
	}

	private long shuffle(int max) {
		/*
		 * get a value from max 12 elements
		*/
		double num = Math.random() * max;
		return Math.round(num) + 1;
	}

	private String getSuit() {
		/*
		 * Get a suit from any 4
		*/
		long suit = shuffle(4);
		if (suit == 1)
			return "Spades";
		if (suit == 2)
			return "Clubs";
		if (suit == 3)
			return "Diamonds";
		else
			return "Hearts";
	}

	private String cardName(long card) {
		/*
		 * Return a selected card name
		*/
		if (card == 1)
			return "Ace";
		if (card == 11)
			return "Jack";
		if (card == 12)
			return "Queen";
		if (card == 13)
			return "King";
		return "" + card;
	}

	private long cardVal(long card) {
		/*
		 * Return a card value
		*/
		if (card == 1) {
			if (userCards > 10 || dealerCards > 10)
				return 1;
			else
				return 11;
		}
		if (card > 10)
			return 10;
		return card;
	}

}
