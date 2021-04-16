package PlayersAndMonstersSecondSolution.models.battleFields.interfaces;

import PlayersAndMonstersSecondSolution.models.players.interfaces.Player;

public interface Battlefield {
    void fight(Player attackPlayer, Player enemyPlayer);
}
