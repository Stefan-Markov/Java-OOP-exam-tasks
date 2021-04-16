package CounterStriker.core;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.PlayerImpl;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private GunRepository guns;
    private PlayerRepository players;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepository();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        switch (type) {
            case "Rifle":
                this.guns.add(new Rifle(name, bulletsCount));
                break;
            case "Pistol":
                this.guns.add((new Pistol(name, bulletsCount)));
                break;
            default:
                return ExceptionMessages.INVALID_GUN_TYPE;
        }
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        if (this.guns.findByName(gunName) == null) {
            throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
        }
        Gun gun = this.guns.findByName(gunName);
        switch (type) {
            case "Terrorist":
                this.players.add(new Terrorist(username, health, armor, gun));
                break;
            case "CounterTerrorist":
                this.players.add(new CounterTerrorist(username, health, armor, gun));
                break;
            default:
                return ExceptionMessages.INVALID_PLAYER_TYPE;
        }
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        return this.field.start(players.getModels());
    }

    @Override
    public String report() {
        Comparator<Player> sortByType = Comparator.comparing(p -> p.getClass().getSimpleName());
        Comparator<Player> sortByHealth = Comparator.comparing(Player::getHealth).reversed();
        Comparator<Player> sortByUsername = Comparator.comparing(Player::getUsername);

        List<Player> sortedPlayers = players.getModels().stream()
                .sorted(sortByType
                        .thenComparing(sortByHealth)
                        .thenComparing(sortByUsername))
                .collect(Collectors.toList());

        StringBuilder output = new StringBuilder();
        String separator = System.lineSeparator();

        for (Player player : sortedPlayers) {
            output.append(String.format("%s: %s", player.getClass().getSimpleName(), player.getUsername()))
                    .append(separator)
                    .append(String.format("--Health: %d", player.getHealth()))
                    .append(separator)
                    .append(String.format("--Armor: %d", player.getArmor()))
                    .append(separator)
                    .append(String.format("--Gun: %s", player.getGun().getName()))
                    .append(separator);
        }

        return output.toString().trim();
    }
}
