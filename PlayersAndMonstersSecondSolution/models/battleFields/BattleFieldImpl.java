package PlayersAndMonstersSecondSolution.models.battleFields;

import PlayersAndMonstersSecondSolution.models.battleFields.interfaces.Battlefield;
import PlayersAndMonstersSecondSolution.models.cards.interfaces.Card;
import PlayersAndMonstersSecondSolution.models.players.Beginner;
import PlayersAndMonstersSecondSolution.models.players.interfaces.Player;

public class BattleFieldImpl implements Battlefield {

    private static final int CARD_DAMAGE_POINTS_INCREASE = 30;

    @Override
    public void fight(Player attackPlayer, Player enemyPlayer) {

        if (attackPlayer.isDead() || enemyPlayer.isDead()) {
            throw new IllegalArgumentException("Player is dead!");
        }

        preFightPreparation(attackPlayer);
        preFightPreparation(enemyPlayer);

        getHealthPointsFromDeck(attackPlayer);
        getHealthPointsFromDeck(enemyPlayer);

        processFight(attackPlayer, enemyPlayer);
    }

    private void processFight(Player attackPlayer, Player enemyPlayer) {
        while (true) {
            int attackPlayerDamagePoints = attackPlayer.getCardRepository().getCards().stream()
                    .mapToInt(Card::getDamagePoints)
                    .sum();

            enemyPlayer.takeDamage(attackPlayerDamagePoints);

            if (enemyPlayer.isDead()) {
                return;
            }
            int enemyPlayerDamage = enemyPlayer.getCardRepository().getCards().stream()
                    .mapToInt(Card::getDamagePoints)
                    .sum();
            attackPlayer.takeDamage(enemyPlayerDamage);

            if (attackPlayer.isDead()) {
                return;
            }
        }
    }

    private void getHealthPointsFromDeck(Player player) {
        int healthPoints = player.getCardRepository().getCards().stream()
                .mapToInt(Card::getHealthPoints)
                .sum();
        player.setHealth(player.getHealth() + healthPoints);
    }

    private void preFightPreparation(Player player) {
        if (!Beginner.class.getSimpleName().equals(player.getClass().getName())) {
            return;
        }
        player.setHealth(player.getHealth() + 40);

        player.getCardRepository().getCards()
                .forEach(c -> c.setDamagePoints(c.getDamagePoints() + CARD_DAMAGE_POINTS_INCREASE));
    }
}
