package robotService.core;

import robotService.common.ExceptionMessages;
import robotService.common.OutputMessages;
import robotService.core.interfaces.Controller;
import robotService.models.garages.GarageImpl;
import robotService.models.garages.interfaces.Garage;
import robotService.models.procedures.Charge;
import robotService.models.procedures.Repair;
import robotService.models.procedures.Work;
import robotService.models.procedures.interfaces.Procedure;
import robotService.models.robots.Cleaner;
import robotService.models.robots.Housekeeper;
import robotService.models.robots.interfaces.Robot;

public class ControllerImpl implements Controller {

    private Garage garage;
    private Procedure charge;
    private Procedure repair;
    private Procedure work;

    public ControllerImpl() {
        this.garage = new GarageImpl();
        this.charge = new Charge();
        this.repair = new Repair();
        this.work = new Work();
    }

    @Override
    public String manufacture(String robotType, String name, int energy, int happiness, int procedureTime) {
        Robot robot;
        if (robotType.equals("Cleaner")) {
            robot = new Cleaner(name, energy, happiness, procedureTime);this.garage.manufacture(robot);
            return String.format(OutputMessages.ROBOT_MANUFACTURED, name);
        } else if (robotType.equals("Housekeeper")) {
            robot = new Housekeeper(name, energy, happiness, procedureTime);
            this.garage.manufacture(robot);
            return String.format(OutputMessages.ROBOT_MANUFACTURED, name);
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_ROBOT_TYPE, robotType));
        }
    }

    @Override
    public String repair(String robotName, int procedureTime) {
        this.repair.doService(getRobot(robotName), procedureTime);

        return String.format(OutputMessages.REPAIR_PROCEDURE, robotName);
    }

    @Override
    public String work(String robotName, int procedureTime) {
        this.work.doService(getRobot(robotName), procedureTime);
        return String.format(OutputMessages.WORK_PROCEDURE, robotName, procedureTime);
    }

    @Override
    public String charge(String robotName, int procedureTime) {
        this.charge.doService(getRobot(robotName), procedureTime);
        return String.format(OutputMessages.CHARGE_PROCEDURE, robotName);
    }

    @Override
    public String sell(String robotName, String ownerName) {
        getRobot(robotName);

        this.garage.sell(robotName, ownerName);

        return String.format(OutputMessages.SELL_ROBOT, ownerName, robotName);
    }

    @Override
    public String history(String procedureType) {

        if (procedureType.equals("Repair")) {
            return this.repair.history();
        } else if (procedureType.equals("Charge")) {
            return this.charge.history();
        }

        return this.work.history();
    }

    private Robot getRobot(String name) {
        Robot robot = this.garage.getRobots().get(name);

        if (robot == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_ROBOT, name));
        }
        return robot;
    }
}
