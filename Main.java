import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    private ArrayList<Card> deck;
    private ArrayList<Card> discardPile;
    private ArrayList<Player> players;
    private int currentPlayerIndex;

    public CardGame() {
        deck = new ArrayList<Card>();
        discardPile = new ArrayList<Card>();
        players = new ArrayList<Player>();
        currentPlayerIndex = 0;

        // Create a standard deck of 52 cards
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        // Deal 5 cards to each player
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addCardToHand(deck.remove(0));
            }
        }

        // Move the top card of the deck to the discard pile to start the game
        discardPile.add(deck.remove(0));
    }

    public void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(player);
        }
    }

    public void playCard(Player player, Card card) {
        Card topCard = discardPile.get(discardPile.size() - 1);

        if (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank()) {
            player.removeCardFromHand(card);
            discardPile.add(card);
            // Check if the player has won
            if (player.getHandSize() == 0) {
                System.out.println(player.getName() + " wins!");
                return;
            }
            // Handle action cards
            switch (card.getRank()) {
                case ACE:
                    currentPlayerIndex = (currentPlayerIndex + 2) % players.size();
                    break;
                case KING:
                    Collections.reverse(players);
                    currentPlayerIndex = (players.size() - 1) - currentPlayerIndex;
                    break;
                case QUEEN:
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    players.get(currentPlayerIndex).addCardsToHand(deck.remove(0), deck.remove(0));
                    break;
                case JACK:
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    for (int i = 0; i < 4; i++) {
                        players.get(currentPlayerIndex).addCardToHand(deck.remove(0));
                    }
                    break;
                default:
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    break;
            }
        }
    }
}

public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
