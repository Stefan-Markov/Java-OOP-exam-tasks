package robotService.models.garages;

import robotService.common.ExceptionMessages;
import robotService.models.garages.interfaces.Garage;
import robotService.models.robots.interfaces.Robot;

import java.util.LinkedHashMap;
import java.util.Map;

public class GarageImpl implements Garage {
    private final static int CAPACITY = 10;
    private Map<String, Robot> robots;

    public GarageImpl() {
        this.robots = new LinkedHashMap<>();
    }

    @Override
    public Map<String, Robot> getRobots() {
        return this.robots;
    }

    @Override
    public void manufacture(Robot robot) {
        if (this.robots.size() == CAPACITY) {
            throw new IllegalArgumentException(ExceptionMessages.NOT_ENOUGH_CAPACITY);
        }
        if (this.robots.containsKey(robot.getName())) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXISTING_ROBOT, robot.getName()));
        }
        this.robots.put(robot.getName(), robot);
    }

    @Override
    public void sell(String robotName, String ownerName) {
        Robot robot = this.robots.get(robotName);
        if (robot == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_ROBOT, robotName));
        }
        robot.setOwner(ownerName);
        robot.setBought(true);
        this.robots.remove(robotName);
    }
}
