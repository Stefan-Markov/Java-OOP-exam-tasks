package CounterStriker.models.players;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

public abstract class PlayerImpl implements Player {
    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;


    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.gun = gun;
        this.isAlive = true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_NAME);
        }
        this.username = username;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_HEALTH);
        }
        this.health = health;
    }

    @Override
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_ARMOR);

        }
        this.armor = armor;
    }

    @Override
    public boolean isAlive() {
        if (this.getHealth() < 0) {
            setAlive(false);
        }

        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public Gun getGun() {

        return gun;
    }

    public void setGun(Gun gun) {
        if (this.gun == null) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN);
        }
        this.gun = gun;
    }

    @Override
    public void takeDamage(int points) {

        if (this.armor > 0) {
            if (this.getArmor() - points < 0) {
                this.setArmor(0);
                this.health -= points - this.armor;
            } else {
                this.armor -= points;
            }
        } else if (health > 0) {
            this.health -= points;
        }
        if (this.getHealth() <= 0) {
            this.setHealth(0);
            this.isAlive = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("%s: %s"
                , this.getClass().getSimpleName(), this.getUsername())).append(System.lineSeparator());

        sb.append(String.format("--Health: %d", this.getHealth())).append(System.lineSeparator());

        sb.append(String.format("--Armor: %d", this.getHealth())).append(System.lineSeparator());

        sb.append(String.format("--Gun: %s", this.getGun().getName())).append(System.lineSeparator());


        return sb.toString();
    }
}
