 //Test case1 for adding a player:
  @Test
public void testAddPlayer() {
    CardGame game = new CardGame();
    Player player = new Player("Alice");
    game.addPlayer(player);
    assertEquals(1, game.getPlayers().size());
    assertEquals(player, game.getPlayers().get(0));
}

//Test case2 for playing a valid card:
 @Test
public void testPlayValidCard() {
    CardGame game = new CardGame();
    Player player = new Player("Alice");
    player.addCardToHand(new Card(Suit.HEARTS, Rank.FIVE));
    game.addPlayer(player);
    game.playCard(player, new Card(Suit.HEARTS, Rank.FIVE));
    assertEquals(1, game.getDiscardPile().size());
    assertEquals(new Card(Suit.HEARTS, Rank.FIVE), game.getDiscardPile().get(0));
    assertEquals(0, player.getHandSize());
}

//Test case3 for playing an invalid card:
  @Test
public void testPlayInvalidCard() {
    CardGame game = new CardGame();
    Player player = new Player("Alice");
    player.addCardToHand(new Card(Suit.HEARTS, Rank.FIVE));
    game.addPlayer(player);
    game.playCard(player, new Card(Suit.CLUBS, Rank.SEVEN));
    assertEquals(0, game.getDiscardPile().size());
    assertEquals(1, player.getHandSize());
}

// Test case4 for winning the game:
  @Test
public void testWinGame() {
CardGame game = new CardGame();
Player player = new Player("Alice");
player.addCardToHand(new Card(Suit.HEARTS, Rank.FIVE));
game.addPlayer(player);
game.playCard(player, new Card(Suit.HEARTS, Rank.FIVE));
assertEquals(player.getName() + " wins!", systemOutRule.getLog().trim());
}

// Test case5 for handling action cards (Ace):
@Test
public void testHandleAce() {
    CardGame game = new CardGame();
    Player player1 = new Player("Alice");
    Player player2 = new Player("Bob");
    game.addPlayer(player1);
    game.addPlayer(player2);
    game.playCard(player1, new Card(Suit.HEARTS, Rank.FIVE));
    game.playCard(player2, new Card(Suit.HEARTS, Rank.ACE));
    assertEquals(1, game.getCurrentPlayerIndex()); // Player 2 should be skipped
    assertEquals(player1, game.getPlayers().get(0));
    assertEquals(player2, game.getPlayers().get(1));
    game.playCard(player1, new Card(Suit.DIAMONDS, Rank.TWO));
    assertEquals(0, game.getCurrentPlayerIndex()); // Turn should move to player 1
}


// Test case6 for handling action cards (King):
 @Test
public void testHandleKing() {
    CardGame game = new CardGame();
    Player player1 = new Player("Alice");
    Player player2 = new Player("Bob");
    game.addPlayer(player1);
    game.addPlayer(player2);

    game.playCard(player1, new Card(Suit.HEARTS, Rank.FIVE));
    game.playCard(player2, new Card(Suit.HEARTS, Rank.KING));

    // Check current player index and order of players
    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(player1, game.getPlayers().get(1));
    assertEquals(player2, game.getPlayers().get(0));

    // Check that King card is added to discard pile
    Card topCard = game.getDiscardPile().get(game.getDiscardPile().size() - 1);
    assertEquals(Suit.HEARTS, topCard.getSuit());
    assertEquals(Rank.KING, topCard.getRank());
}


//Test case7 for handling action cards (Queen):
@Test
public void testHandleQueen() {
    CardGame game = new CardGame();
    Player player1 = new Player("Alice");
    Player player2 = new Player("Bob");
    game.addPlayer(player1);
    game.addPlayer(player2);

    // Add cards to player1's hand
    player1.addCardToHand(new Card(Suit.HEARTS, Rank.QUEEN));
    player1.addCardToHand(new Card(Suit.CLUBS, Rank.KING));
    player1.addCardToHand(new Card(Suit.SPADES, Rank.TWO));

    // Set the top card of the discard pile
    Card topCard = new Card(Suit.DIAMONDS, Rank.FIVE);
    game.getDiscardPile().add(topCard);

    // Player 1 plays a Queen card
    game.playCard(player1, new Card(Suit.HEARTS, Rank.QUEEN));

    // Check that player1's hand has two cards left
    assertEquals(2, player1.getHandSize());

    // Check that the top card of the discard pile is the Queen card
    assertEquals(new Card(Suit.HEARTS, Rank.QUEEN), game.getDiscardPile().get(game.getDiscardPile().size() - 1));

    // Check that player2 is now the current player
    assertEquals(player2, game.getPlayers().get(game.getCurrentPlayerIndex()));

    // Check that player2's hand has two cards added
    assertEquals(7, player2.getHandSize());

    // Check that the top card of the deck is now the third card in the deck
    assertEquals(new Card(Suit.SPADES, Rank.TWO), game.getDeck().get(2));
}


//Test case8 for handling action cards (jack):
  @Test
public void testJackActionCard() {
    CardGame game = new CardGame();
    Player player1 = new Player("Alice");
    Player player2 = new Player("Bob");
    Player player3 = new Player("Charlie");
    game.addPlayer(player1);
    game.addPlayer(player2);
    game.addPlayer(player3);
    
    // Set the players' hands to a known state for testing
    player1.setHand(Arrays.asList(
        new Card(Suit.HEARTS, Rank.JACK),
        new Card(Suit.SPADES, Rank.SEVEN),
        new Card(Suit.DIAMONDS, Rank.FIVE),
        new Card(Suit.CLUBS, Rank.KING),
        new Card(Suit.HEARTS, Rank.ACE)
    ));
    player2.setHand(Arrays.asList(
        new Card(Suit.SPADES, Rank.TEN),
        new Card(Suit.HEARTS, Rank.NINE),
        new Card(Suit.CLUBS, Rank.QUEEN),
        new Card(Suit.DIAMONDS, Rank.FOUR),
        new Card(Suit.HEARTS, Rank.THREE)
    ));
    player3.setHand(Arrays.asList(
        new Card(Suit.DIAMONDS, Rank.JACK),
        new Card(Suit.CLUBS, Rank.TWO),
        new Card(Suit.HEARTS, Rank.TWO),
        new Card(Suit.SPADES, Rank.TWO),
        new Card(Suit.CLUBS, Rank.THREE)
    ));
    
    // Set the top card of the discard pile to a known state for testing
    game.getDiscardPile().add(new Card(Suit.DIAMONDS, Rank.TEN));
    
    // Set the current player to the first player
    game.setCurrentPlayerIndex(0);
    
    // Play a jack card from the first player's hand
    Card jackCard = player1.getHand().get(0);
    game.playCard(player1, jackCard);
    
    // Check that the jack card was removed from the first player's hand
    assertFalse(player1.getHand().contains(jackCard));
    
    // Check that the discard pile now has the jack card
    assertTrue(game.getDiscardPile().contains(jackCard));
    
    // Check that the current player is now the second player
    assertEquals(1, game.getCurrentPlayerIndex());
    
    // Check that the second player's hand now has 4 additional cards
    assertEquals(9, player2.getHandSize());
    
    // Check that the second player's hand contains the cards that were drawn
    assertTrue(player2.getHand().contains(new Card(Suit.CLUBS, Rank.FIVE)));
    assertTrue(player2.getHand().contains(new Card(Suit.CLUBS, Rank.JACK)));
    assertTrue(player2.getHand().contains(new Card(Suit.SPADES, Rank.FIVE)));
    assertTrue(player2.getHand().contains(new Card(Suit.HEARTS, Rank.FIVE)));
    
    // Check that the current player is now the third player
    assertEquals(2, game.getCurrentPlayerIndex());
    
    // Check that the third player's hand has not changed
    assertEquals(5, player3.getHandSize());
}




// Test case9 for adding a player when the player limit has been reached:
     @Test
public void testAddPlayer_playerLimitReached() {
    CardGame game = new CardGame();
    game.addPlayer(new Player("Player 1"));
    game.addPlayer(new Player("Player 2"));
    game.addPlayer(new Player("Player 3"));
    game.addPlayer(new Player("Player 4"));
    game.addPlayer(new Player("Player 5"));
    assertEquals(4, game.getPlayers().size());
}

//Test case10 for playing a card that doesn't match the suit or rank of the top card in the discard pile:
    @Test
public void testPlayCard_cardDoesntMatch() {
    CardGame game = new CardGame();
    Player player1 = new Player("Player 1");
    player1.addCardToHand(new Card(Suit.CLUBS, Rank.THREE));
    game.addPlayer(player1);
    game.playCard(player1, new Card(Suit.HEARTS, Rank.SEVEN));
    assertEquals(1, player1.getHandSize());
    assertEquals(1, game.getDiscardPile().size());
    assertEquals(Rank.ACE, game.getDiscardPile().get(0).getRank());
    assertEquals(1, game.getCurrentPlayerIndex());
}

//Test case11 for playing a card when the player's hand is empty:
  @Test
public void testPlayCard_emptyHand() {
    CardGame game = new CardGame();
    Player player1 = new Player("Player 1");
    game.addPlayer(player1);
    game.playCard(player1, new Card(Suit.HEARTS, Rank.SEVEN));
    assertEquals(0, player1.getHandSize());
    assertEquals(1, game.getDiscardPile().size());
    assertEquals(Rank.SEVEN, game.getDiscardPile().get(0).getRank());
    assertEquals("Player 1 wins!", game.getWinner());
}

//Test case12 for playing a card when the deck is empty:
   @Test
public void testPlayCard_emptyDeck() {
    CardGame game = new CardGame();
    Player player1 = new Player("Player 1");
    player1.addCardToHand(new Card(Suit.HEARTS, Rank.SEVEN));
    game.addPlayer(player1);
    game.playCard(player1, new Card(Suit.HEARTS, Rank.SEVEN));
    assertEquals(0, game.getDeck().size());
    assertEquals(1, game.getDiscardPile().size());
    assertEquals(Rank.SEVEN, game.getDiscardPile().get(0).getRank());
    assertEquals("Player 1 wins!", game.getWinner());
}

//Test case13 for playing a card when the discard pile is empty:
 @Test
public void testPlayCard_emptyDiscardPile() {
    CardGame game = new CardGame();
    Player player1 = new Player("Player 1");
    player1.addCardToHand(new Card(Suit.HEARTS, Rank.SEVEN));
    game.addPlayer(player1);
    game.setDiscardPile(new ArrayList<Card>()); // Set discard pile to empty
    game.playCard(player1, new Card(Suit.HEARTS, Rank.SEVEN));
    assertEquals(0, game.getDeck().size());
    assertEquals(1, game.getDiscardPile().size());
    assertEquals(Rank.SEVEN, game.getDiscardPile().get(0).getRank());
    assertEquals(null, game.getWinner());
}

// Test case14 for skipping a turn with a Jack card
@Test
public void testHandleJack() {
CardGame game = new CardGame();
Player player1 = new Player("Alice");
Player player2 = new Player("Bob");
Player player3 = new Player("Charlie");
game.addPlayer(player1);
game.addPlayer(player2);
game.addPlayer(player3);

scss
Copy code
// Player 1 plays a Jack card
game.playCard(player1, new Card(Suit.HEARTS, Rank.JACK));

// Check that player2 is skipped and turn moves to player 3
assertEquals(2, game.getCurrentPlayerIndex());
assertEquals(player3, game.getPlayers().get(game.getCurrentPlayerIndex()));
}

                  
