package robotService.models.robots;

import robotService.common.ExceptionMessages;
import robotService.models.robots.interfaces.Robot;

public abstract class BaseRobot implements Robot {
    private static final String DEFAULT_OWNER = "Service";
    private String name;
    private int happiness;
    private int energy;
    private int procedureTime;
    private String owner;
    private boolean isBought;
    private boolean isRepaired;

    protected BaseRobot(String name, int energy, int happiness, int procedureTime) {
        this.name = name;
        this.energy = energy;
        this.happiness = happiness;
        this.procedureTime = procedureTime;
        this.owner = DEFAULT_OWNER;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHappiness() {

        return this.happiness;
    }

    @Override
    public void setHappiness(int happiness) {
        validateInfo(happiness, ExceptionMessages.INVALID_HAPPINESS);
        this.happiness = happiness;
    }

    private void validateInfo(int value, String message) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        validateInfo(energy, ExceptionMessages.INVALID_ENERGY);
        this.energy = energy;
    }

    @Override
    public int getProcedureTime() {
        return this.procedureTime;
    }

    @Override
    public void setProcedureTime(int procedureTime) {
        this.procedureTime = procedureTime;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public void setBought(boolean bought) {
        this.isBought = bought;
    }

    @Override
    public boolean isRepaired() {
        return this.isRepaired;
    }

    @Override
    public void setRepaired(boolean repaired) {
        this.isBought = repaired;
    }

    @Override
    public String toString() {
        return "Robot type: " + this.getClass().getSimpleName() +
                " - " + this.getName() + " - Happiness: " + this.getEnergy() + " - Energy: {energy}";
    }
}
