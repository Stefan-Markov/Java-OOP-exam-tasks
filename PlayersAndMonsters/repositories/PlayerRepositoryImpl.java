package PlayersAndMonsters.repositories;

import PlayersAndMonsters.models.players.interfaces.Player;
import PlayersAndMonsters.repositories.interfaces.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryImpl implements PlayerRepository {

    private List<Player> players;

    public PlayerRepositoryImpl() {
        this.players = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.players.size();
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public void add(Player player) {

        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        boolean contains = this.players.stream().anyMatch(p -> p.getUsername().equals(player.getUsername()));

        if (contains) {
            throw new IllegalArgumentException(String.format("Player %s already exists!", player.getUsername()));
        }
        this.players.add(player);
    }

    @Override
    public boolean remove(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        return this.players.removeIf(p -> p.getUsername().equals(player.getUsername()));
    }

    @Override
    public Player find(String name) {
        return this.players.stream().filter(f -> f.getUsername().equals(name)).findFirst().orElse(null);
    }
}
