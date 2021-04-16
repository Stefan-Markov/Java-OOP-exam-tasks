package easterRaces.entities.cars;

import easterRaces.common.ExceptionMessages;

public class SportsCar extends BaseCar {
    private static final double CUBIC_CENTIMETERS = 3000;

    public SportsCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS);
    }

    @Override
    public void setHorsePower(int horsePower) {
        int minHorsePower = 250;
        int maxHorsePower = 450;
        if (horsePower < minHorsePower || horsePower > maxHorsePower) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }
        super.setHorsePower(horsePower);
    }
}
