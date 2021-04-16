package PlayersAndMonstersSecondSolution.models.players;

import PlayersAndMonstersSecondSolution.common.ConstantMessages;
import PlayersAndMonstersSecondSolution.models.players.interfaces.Player;
import PlayersAndMonstersSecondSolution.repositories.interfaces.CardRepository;

public abstract class BasePlayer implements Player {
    private static final int HEALTH_POINTS_MIN = 0;
    private static final int DAMAGE_POINTS_MIN = 0;
    private String username;
    private int health;
    private CardRepository cardRepository;
    private boolean isDead;

    protected BasePlayer(CardRepository cardRepository, String username, int health) {
        this.cardRepository = cardRepository;
        this.setUsername(username);
        this.setHealth(health);
        this.setDead(false);
    }

    @Override
    public String toString() {
        int cardCount = this.cardRepository.getCount();
        String result = String.format(ConstantMessages.PLAYER_REPORT_INFO, this.getUsername(), this.getHealth(), cardCount);
        StringBuilder sb = new StringBuilder(result);
        sb.append(System.lineSeparator());
        this.cardRepository.getCards().forEach(c -> sb.append(c.toString()).append(System.lineSeparator()));
        sb.append(ConstantMessages.DEFAULT_REPORT_SEPARATOR);
        return sb.toString().trim();
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
            throw new IllegalArgumentException(ConstantMessages.PLAYER_USERNAME_NULL_OR_EMPTY);
        }
        this.username = username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int healthPoints) {
        if (healthPoints < HEALTH_POINTS_MIN) {
            throw new IllegalArgumentException(ConstantMessages.PLAYER_USERNAME_NULL_OR_EMPTY);
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
        this.health -= damagePoints;
        if (this.health <= 0) {
            this.health = 0;
            this.isDead = true;
        }
    }
}
