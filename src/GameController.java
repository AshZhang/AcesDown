/**
 * Created by Ashley Zhang, APCS Period 2 on 5/15/17
 */

import java.util.ArrayList;
import java.util.Collections;

public class GameController {
    private ArrayList<PlayingCard> deck = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard> discardPile = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard>[] piles = new ArrayList[4];

    private ArrayList<PlayingCard> oldDeck = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard> oldDiscPile = new ArrayList<PlayingCard>();
    private ArrayList<PlayingCard>[] oldPiles = new ArrayList[4];

    /**
     * Creates a controller to play the game. The constructor creates, populates, and
     * shuffles
     * the deck, and instantiates the 4 ArrayLists contained in the piles array.
     */
    public GameController() {
        String[] suits = {"S", "H", "D", "C"};
        for (String s : suits) {
            for (int i = 0; i < 13; i++) {
                deck.add(new PlayingCard(i + 1, s));
            }
        }
        Collections.shuffle(deck);
        for (int i = 0; i < 4; i++) {
            piles[i] = new ArrayList<PlayingCard>();
        }
        for(int i = 0; i < 4; i++){
            oldPiles[i] = new ArrayList<PlayingCard>();
        }
    }

    /**
     * Adds a card from the top of the deck to the end of each of the piles, provided
     * there are enough cards in the deck to complete the deal.
     */
    public void deal() {
        updateHistory();
        int count = 0;
        while (count < 4 && deck.size() > 0) {
            piles[count].add(deck.remove(deck.size() - 1));
            count++;
        }
    }

    /**
     * Moves the last card on the specified pile to the discard pile provided:
     * -the list is not empty
     * -it is a legal move, i.e. the card has the same suit and a higher rank than
     * the last card on a different pile
     *
     * @param listNum the list to discard from, 0 to 3 inclusive
     */
    public void discard(int listNum) {

        boolean legal = false;

        if (piles[listNum].size() != 0) {
            PlayingCard thisCard = piles[listNum].get(piles[listNum].size() - 1);
            for (int i = 0; i < 4; i++) {
                if (piles[i].size() != 0) {
                    PlayingCard other = getCard(i, piles[i].size() - 1);
                    if (other.getRank() < thisCard.getRank() && other.getSuit().equals(thisCard.getSuit())) {
                        legal = true;
                    }
                }
            }
        }
        if (legal) {
            updateHistory();
            discardPile.add(piles[listNum].remove(piles[listNum].size() - 1));
        }
    }

    /**
     * Move the last card from the specified list to an empty list provided
     * - the specified list contains a card
     * - an empty list exists
     *
     * @param listNum the list to move from, 0 to 3 inclusive
     */
    public void move(int listNum) {
        if (piles[listNum].size() != 0) {
            updateHistory();
            int emptyIndex = -1;
            for (int i = 0; i < 4; i++) {
                if (piles[i].size() == 0) {
                    emptyIndex = i;
                }
            }
            if (emptyIndex >= 0) {
                piles[emptyIndex].add(piles[listNum].remove(piles[listNum].size() - 1));
            }
        }

    }

    /**
     * Moves all games cards back into the deck (removing them from their current
     * piles)
     * and shuffles the deck in preparation for beginning a new game.
     */
    public void startNewGame() {
        clearHistory();
        for (int i = discardPile.size() - 1; i >= 0; i--) {
            deck.add(discardPile.remove(i));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = piles[i].size() - 1; j >= 0; j--) {
                deck.add(piles[i].remove(j));
            }
        }
        Collections.shuffle(deck);
        deal();
        updateHistory();
    }

    /**
     * Return the PlayCard at the specified index of the specified pile. If the given
     * list is empty, or the
     * given index is invalid for the given list, null is returned.
     *
     * @param pileNum the pile from which to retrieve the card, 0 to 3 inclusive
     * @param index   the index from which to retrieve the card, 0 to size of list-1
     *                inclusive
     * @return the reference to the specified card, or null if the request is invalid.
     */
    public PlayingCard getCard(int pileNum, int index) {
        if (index < 0 || index > piles[pileNum].size() - 1) {
            return null;
        }
        return piles[pileNum].get(index);
    }

    /**
     * Gets the current score which is the number of cards in the discard pile.
     * If the deck contains less than 4 cards, "GAME OVER" is concatenated with the
     * score
     * or, if you won, "YOU WIN!" is concatenated with the score.
     *
     * @return the score as a String. If the game is over, "GAME OVER" is added to the
     * score value
     */
    public String getScore() {
        String score = discardPile.size() + "";
        if (deck.size() < 4) {
            score += " GAME OVER";
        } else if (discardPile.size() == 48) {
            score += " YOU WIN";
        }
        return score;
    }

    /**
     * Undoes last move, discard, or deal.
     */

    public void undo(){
        deck.clear();
        discardPile.clear();
        for(ArrayList<PlayingCard> pile : piles){
            pile.clear();
        }
        deck.addAll(oldDeck);
        discardPile.addAll(oldDiscPile);
        for(int i = 0; i < 4; i++){
            piles[i].addAll(oldPiles[i]);
        }
    }

    private void updateHistory(){
        oldDeck.clear();
        oldDeck.addAll(deck);
        oldDiscPile.clear();
        oldDiscPile.addAll(discardPile);
        for(int i = 0; i < 4; i++){
            oldPiles[i].clear();
            oldPiles[i].addAll(piles[i]);
        }
    }

    private void clearHistory(){
        oldDeck.clear();
        oldDiscPile.clear();
        for(int i = 0; i < 4; i++){
            oldPiles[i].clear();
        }
    }
}