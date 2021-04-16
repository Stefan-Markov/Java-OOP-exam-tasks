package PlayersAndMonsters.models.battleFields;

import PlayersAndMonsters.models.battleFields.interfaces.Battlefield;
import PlayersAndMonsters.models.cards.interfaces.Card;
import PlayersAndMonsters.models.players.interfaces.Player;

public class BattleFieldImpl implements Battlefield {
    @Override
    public void fight(Player attackPlayer, Player enemyPlayer) {
        if (attackPlayer.isDead() || enemyPlayer.isDead()) {
            throw new IllegalArgumentException("Player is dead!");
        }
        if (attackPlayer.getClass().getSimpleName().equals("Beginner")) {
            this.addBonusStatus(attackPlayer);
        }
        if (enemyPlayer.getClass().getSimpleName().equals("Beginner")) {
            this.addBonusStatus(enemyPlayer);
        }

        this.setBonusHealth(attackPlayer);
        this.setBonusHealth(enemyPlayer);

        while (!attackPlayer.isDead() && !enemyPlayer.isDead()) {
            int attackerDamage = getDeckDamage(attackPlayer);
            int enemyDamage = getDeckDamage(enemyPlayer);
            enemyPlayer.takeDamage(attackerDamage);

            if(enemyPlayer.isDead()){
                break;
            }
            attackPlayer.takeDamage(enemyDamage);
        }
    }

    private void setBonusHealth(Player player) {
        player.setHealth(player.getHealth() +
                player.getCardRepository().getCards().stream().mapToInt(Card::getHealthPoints).sum());
    }

    private int getDeckDamage(Player attackPlayer) {
        return attackPlayer.getCardRepository().getCards()
                .stream().mapToInt(Card::getDamagePoints).sum();

    }
    private void addBonusStatus(Player player) {
        player.setHealth(player.getHealth() + 40);

        for (Card card : player.getCardRepository().getCards()) {
            card.setDamagePoints(card.getDamagePoints() + 30);
        }
    }
}
