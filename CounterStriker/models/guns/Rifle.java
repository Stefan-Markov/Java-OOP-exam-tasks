package CounterStriker.models.guns;

public class Rifle extends GunImpl {

    private static final int RIFLE_BULLETS = 10;

    public Rifle(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

}
