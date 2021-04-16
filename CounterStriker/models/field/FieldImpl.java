package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FieldImpl implements Field {
    private List<Terrorist> terroristList;
    private List<CounterTerrorist> counterTerrorists;

    public FieldImpl() {
        this.terroristList = new ArrayList<>();
        this.counterTerrorists = new ArrayList<>();
    }

    @Override
    public String start(Collection<Player> players) {
        divideToTeams(players);

        while (hasAliveTerrorist(terroristList) && (hasAliveCounter(counterTerrorists))) {

            terroristsFire();

            counterTerroristsFire();

            if (hasNoMoreBullets(players)) {
                break;
            }
        }

        if (!hasAliveTerrorist(this.terroristList)) {
            return OutputMessages.COUNTER_TERRORIST_WINS;
        } else if (!hasAliveCounter(this.counterTerrorists)) {
            return OutputMessages.TERRORIST_WINS;
        } else {
            return null;
        }
    }

    private boolean hasNoMoreBullets(Collection<Player> players) {
        for (Player player : players) {
            if (player.getGun().getBulletsCount() > 0) {
                return false;
            }
        }
        return true;
    }

    private void counterTerroristsFire() {
        for (CounterTerrorist counterTerrorist : counterTerrorists) {
            for (Terrorist terrorist : terroristList) {
                if (terrorist.isAlive() && counterTerrorist.isAlive() && terrorist.getGun().getBulletsCount() > 0) {
                    terrorist.takeDamage(counterTerrorist.getGun().fire());
                }
            }
        }
    }

    private void terroristsFire() {
        for (Terrorist terrorist : terroristList) {
            for (CounterTerrorist counterTerrorist : counterTerrorists) {
                if (counterTerrorist.isAlive() && terrorist.isAlive() && terrorist.getGun().getBulletsCount() > 0) {
                    counterTerrorist.takeDamage(terrorist.getGun().fire());
                }
            }
        }
    }

    private boolean hasAliveTerrorist(List<Terrorist> players) {
        for (Terrorist player : players) {
            if (player.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAliveCounter(List<CounterTerrorist> players) {
        for (CounterTerrorist player : players) {
            if (player.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private void divideToTeams(Collection<Player> players) {
        for (Player player : players) {
            if (player.getClass().getSimpleName().equals("Terrorist")) {
                this.terroristList.add((Terrorist) player);
            } else {
                this.counterTerrorists.add((CounterTerrorist) player);
            }
        }
    }
}




