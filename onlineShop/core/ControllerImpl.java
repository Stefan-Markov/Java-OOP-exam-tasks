package onlineShop.core;

import onlineShop.core.interfaces.Controller;
import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public class ControllerImpl implements Controller {

    private List<Computer> computers;

    public ControllerImpl() {
        this.computers = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        if (this.computers.stream().anyMatch(c -> c.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
        }

        Computer computer;
        if (computerType.equals("DesktopComputer")) {
            computer = new DesktopComputer(id, manufacturer, model, price);
        } else if (computerType.equals("Laptop")) {
            computer = new Laptop(id, manufacturer, model, price);
        } else {
            throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }

        this.computers.add(computer);
        return String.format(ADDED_COMPUTER, id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == computerId)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        if (computer.getPeripherals().stream().anyMatch(p -> p.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
        }

        Peripheral peripheral;
        if (peripheralType.equals("Headset")) {
            peripheral = new Headset(id, manufacturer, model, price, overallPerformance, connectionType);
        } else if (peripheralType.equals("Keyboard")) {
            peripheral = new Keyboard(id, manufacturer, model, price, overallPerformance, connectionType);
        } else if (peripheralType.equals("Monitor")) {
            peripheral = new Monitor(id, manufacturer, model, price, overallPerformance, connectionType);
        } else if (peripheralType.equals("Mouse")) {
            peripheral = new Mouse(id, manufacturer, model, price, overallPerformance, connectionType);
        } else {
            throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);
        }

        computer.addPeripheral(peripheral);
        return String.format(ADDED_PERIPHERAL, peripheralType, id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == computerId)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        Peripheral peripheral = computer.removePeripheral(peripheralType);
        return String.format(REMOVED_PERIPHERAL, peripheralType, peripheral.getId());
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == computerId)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        if (computer.getComponents().stream().anyMatch(c -> c.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
        }

        Component component;
        if (componentType.equals("CentralProcessingUnit")) {
            component = new CentralProcessingUnit(id, manufacturer, model, price, overallPerformance, generation);
        } else if (componentType.equals("Motherboard")) {
            component = new Motherboard(id, manufacturer, model, price, overallPerformance, generation);
        } else if (componentType.equals("PowerSupply")) {
            component = new PowerSupply(id, manufacturer, model, price, overallPerformance, generation);
        } else if (componentType.equals("RandomAccessMemory")) {
            component = new RandomAccessMemory(id, manufacturer, model, price, overallPerformance, generation);
        } else if (componentType.equals("SolidStateDrive")) {
            component = new SolidStateDrive(id, manufacturer, model, price, overallPerformance, generation);
        } else if (componentType.equals("VideoCard")) {
            component = new VideoCard(id, manufacturer, model, price, overallPerformance, generation);
        } else {
            throw new IllegalArgumentException(INVALID_COMPONENT_TYPE);
        }

        computer.addComponent(component);

        return String.format(ADDED_COMPONENT, componentType, id, computerId);
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == computerId)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        Component component = computer.removeComponent(componentType);
        return String.format(REMOVED_COMPONENT, componentType, component.getId());
    }

    @Override
    public String buyComputer(int id) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        this.computers.remove(computer);
        return computer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        Computer foundComputer = this.computers
                .stream()
                .filter(c -> c.getPrice() <= budget)
                .sorted((c1, c2) -> Double.compare(c2.getOverallPerformance(), c1.getOverallPerformance()))
                .findFirst()
                .orElse(null);

        if (foundComputer == null) {
            throw new IllegalArgumentException(String.format(CAN_NOT_BUY_COMPUTER, budget));
        }

        this.computers.remove(foundComputer);
        return foundComputer.toString();
    }

    @Override
    public String getComputerData(int id) {
        Computer computer = this.computers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (computer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }

        return computer.toString();
    }
}
