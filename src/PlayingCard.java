/**
 * Created by Ashley Zhang, APCS Period 2 on 5/15/17
 */
public class PlayingCard implements Comparable<PlayingCard> {
    private int rank;
    private String suit;

    /**
     * Creates a PlayingCard object with the given rank and suit. If either rank or
     * suit is invalid,
     * an error message is printed to the console and an ace of spades is constructed.
     *
     * @param cardRank the rand of the card: 1(Ace), 2, 3 ... 11(Jack), 12(Queen),
     *                 13(King)
     * @param cardSuit the suit of the card: "S" (spades), "H" (hearts), "D"
     *                 (diamonds), "C" (clubs)
     */
    public PlayingCard(int cardRank, String cardSuit) {
        String[] suits = {"S", "H", "D", "C"};
        boolean hasSuit = false;
        for(String s : suits){
            if(s.equals(cardSuit)){
                hasSuit = true;
            }
        }
        if(cardRank < 1 || cardRank > 13 || !hasSuit){
            System.out.println("Error: invalid card rank. Ace of spades created instead");
            rank = 1;
            suit = suits[0];
        }else{
            rank = cardRank;
            suit = cardSuit;
        }
    }

    /**
     * Returns a positive number if this card has a lower rank than other card, a
     * negative number if
     * this card has a higher rank than other card, and 0 if they have the same rank
     *
     * @param other the card to be compared to this card
     * @return the numerical comparison of this card and other card
     */
    public int compareTo(PlayingCard other) {
        return other.getRank() - rank;
    }

    /**
     * Returns the String representation of the card concatenated with the file
     * extension .png
     * e.g. the 10 of spades returns 10S.png (see toString method)
     *
     * @return the file name of this card's image file
     */
    public String getImageFileName() {
        return toString() + ".png";
    }

    /**
     * Gets the rank of the card from 1 (Ace) through 13 (King)
     *
     * @return the rank of the card
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the suit of the card: "S", "H", "D", or "C"
     *
     * @return the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns a String representing the card by concatenating the rank with the suit
     * e.g. the 10 of spades returns "10S"
     *
     * @return a String representing the card
     */
    public String toString() {
        return rank + suit;
    }
}