package PlayersAndMonsters.models.battleFields.interfaces;

import PlayersAndMonsters.models.players.interfaces.Player;

public interface Battlefield {
    void fight(Player attackPlayer, Player enemyPlayer);
}
