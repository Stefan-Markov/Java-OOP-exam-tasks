package robotService.models.procedures;

import robotService.common.ExceptionMessages;
import robotService.models.procedures.interfaces.Procedure;
import robotService.models.robots.interfaces.Robot;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseProcedure implements Procedure {
    protected Collection<Robot> robots;

    protected BaseProcedure() {
        this.robots = new ArrayList<>();
    }

    @Override
    public String history() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(System.lineSeparator());
        for (Robot robot : robots) {
            sb.append(String.format(
                    " Robot type: %s - %s - Happiness: %s - Energy: %s", robot.getClass().getSimpleName(),
                    robot.getName(), robot.getHappiness(), robot.getEnergy()));
            sb.append(System.lineSeparator());

        }
        return sb.toString().trim();
    }

    @Override
    public void doService(Robot robot, int procedureTime) {
        if (robot.getProcedureTime() < procedureTime) {
            throw new IllegalArgumentException(ExceptionMessages.INSUFFICIENT_PROCEDURE_TIME);
        }
        int newProcedureTime = robot.getProcedureTime() - procedureTime;
        robot.setProcedureTime(newProcedureTime);
        this.robots.add(robot);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(System.lineSeparator());

        for (Robot robot : robots) {
            sb.append(robot.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
