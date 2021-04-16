package PlayersAndMonstersSecondSolution.repositories;

import PlayersAndMonstersSecondSolution.models.players.interfaces.Player;
import PlayersAndMonstersSecondSolution.repositories.interfaces.PlayerRepository;


import java.util.*;

public class PlayerRepositoryImpl implements PlayerRepository {
    private Map<String, Player> players;

    public PlayerRepositoryImpl() {
        this.players = new HashMap<>();
    }

    @Override
    public int getCount() {
        return this.players.values().size();
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(new ArrayList<>(this.players.values()));
    }

    @Override
    public void add(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        if (this.players.containsKey(player.getUsername())) {
            String message = String.format("Player %s already exists!", player.getUsername());
            throw new IllegalArgumentException(message);
        }
        this.players.put(player.getUsername(), player);
    }

    @Override
    public boolean remove(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        Player removedPlayer = this.players.remove(player.getUsername());
        return removedPlayer != null;
    }

    @Override
    public Player find(String name) {
        Player player = this.players.get(name);
        return player;
    }
}
