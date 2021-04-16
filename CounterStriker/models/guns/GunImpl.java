package CounterStriker.models.guns;

import CounterStriker.common.ExceptionMessages;

public abstract class GunImpl implements Gun {
    private String name;
    private int bulletsCount;

    protected GunImpl(String name, int bulletsCount) {
        this.setName(name);
        this.setBulletsCount(bulletsCount);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_NAME);
        }
        this.name = name;
    }

    @Override
    public int getBulletsCount() {
        return this.bulletsCount;
    }

    public void setBulletsCount(int bulletsCount) {
        if (bulletsCount < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_BULLETS_COUNT);
        }
        this.bulletsCount = bulletsCount;
    }
    @Override
    public int fire() {
        int firedBullets = 0;
        if (this instanceof Pistol) {
            firedBullets = 1;
        } else if (this instanceof Rifle) {
            firedBullets = 10;
        }

        if (this.getBulletsCount() - firedBullets >= 0) {
            this.setBulletsCount(this.getBulletsCount() - firedBullets);
            return firedBullets;
        }
        return 0;
    }
}
