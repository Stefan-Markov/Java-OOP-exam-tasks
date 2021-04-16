package PlayersAndMonsters.repositories.interfaces;

import PlayersAndMonsters.models.players.interfaces.Player;

import java.util.List;

public interface PlayerRepository {
    int getCount();

    List<Player> getPlayers();

    void add(Player player);

    boolean remove(Player player);

    Player find(String name);
}
