package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerRepository implements Repository<Player> {
    private List<Player> models;

    public PlayerRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<Player> getModels() {
        return this.models;
    }

    @Override
    public void add(Player model) {
        if (model == null) {
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_REPOSITORY);
        }
        this.models.add(model);
    }

    @Override
    public boolean remove(Player model) {
        return this.models.remove(model);
    }

    @Override
    public Player findByName(String name) {

        return this.models.stream()
                .filter(p -> p.getUsername()
                        .equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
