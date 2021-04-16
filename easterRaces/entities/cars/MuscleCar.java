package easterRaces.entities.cars;

import easterRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar {
    private static final double CUBIC_CENTIMETERS = 5000;

    public MuscleCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS);
    }

    @Override
    public void setHorsePower(int horsePower) {
        int minHorsePower = 400;
        int maxHorsePower = 600;
        if (horsePower < minHorsePower || horsePower > maxHorsePower) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }
        super.setHorsePower(horsePower);
    }
}
