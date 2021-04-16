package PlayersAndMonsters.models.players;

import PlayersAndMonsters.models.cards.interfaces.Card;
import PlayersAndMonsters.models.players.interfaces.Player;
import PlayersAndMonsters.repositories.interfaces.CardRepository;

public abstract class BasePlayer implements Player {
    private String username;
    private int health;
    private CardRepository cardRepository;
    private boolean isDead;


    protected BasePlayer(CardRepository cardRepository, String username, int health) {
        this.cardRepository = cardRepository;
        this.setUsername(username);
        this.setHealth(health);

    }

    @Override
    public CardRepository getCardRepository() {
        return this.cardRepository;
    }

    private void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Player's username cannot be null or an empty string.");
        }
        this.username = username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int healthPoints) {
        if (healthPoints < 0) {
            throw new IllegalArgumentException("Player's health bonus cannot be less than zero.");
        }
        this.health = healthPoints;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    private void setDead(boolean dead) {
        this.isDead = dead;
    }

    @Override
    public void takeDamage(int damagePoints) {
        if (damagePoints < 0) {
            throw new IllegalArgumentException("Damage points cannot be less than zero.");
        }
        this.health = Math.max(this.health - damagePoints, 0);
        this.isDead = this.health == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                String.format("Username %s - Health: %d - Cards %d%n",
                        this.username, this.health, this.cardRepository.getCount()));
        for (Card card : this.cardRepository.getCards()) {
            sb.append(card.toString())
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
