package easterRaces.entities.cars;

import easterRaces.common.ExceptionMessages;

public abstract class BaseCar implements Car {

    private String model;
    private int horsePower;
    private double cubicCentimeters;


    protected BaseCar(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty() || model.length() < 4) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_MODEL, model, 4));
        }
        this.model = model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    public void setHorsePower(int horsePower) {
        if (horsePower < 0) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }
        this.horsePower = horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return (this.cubicCentimeters / this.horsePower) * laps;
    }
}
