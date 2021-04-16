package onlineShopSecondSolution.core;

import onlineShopSecondSolution.core.interfaces.Controller;
import onlineShopSecondSolution.models.products.components.Component;
import onlineShopSecondSolution.models.products.computers.Computer;
import onlineShopSecondSolution.models.products.peripherals.Peripheral;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static onlineShopSecondSolution.common.constants.ExceptionMessages.*;
import static onlineShopSecondSolution.common.constants.OutputMessages.*;

public class ControllerImpl implements Controller {
    private List<Computer> computers;
    private List<Component> components;
    private List<Peripheral> peripherals;

    public ControllerImpl() {
        this.computers = new ArrayList<>();
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        for (Computer computer : computers) {
            if (computer.getId() == id) {
                throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
            }
        }
        String computerCreation = "onlineShop.models.products.computers." + computerType;
        try {
            Class<?> aClass = Class.forName(computerCreation);
            Computer newComputer = (Computer) aClass.getConstructor(int.class, String.class, String.class, double.class).newInstance(id, manufacturer, model, price);
            this.computers.add(newComputer);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }
        return String.format(ADDED_COMPUTER, id);
    }


    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        Computer computer = checkIfComputerExist(computerId);
        String peripheralCreation = "onlineShop.models.products.peripherals." + peripheralType;
        for (Peripheral peripheral : this.peripherals) {
            if (peripheral.getId() == id) {
                throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
            }
        }
        try {
            Class<?> aClass = Class.forName(peripheralCreation);
            Peripheral newPeripheral = (Peripheral) aClass.getConstructor(int.class, String.class, String.class, double.class, double.class, String.class).
                    newInstance(id, manufacturer, model, price, overallPerformance, connectionType);
            computer.addPeripheral(newPeripheral);
            this.peripherals.add(newPeripheral);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);
        }

        return String.format(ADDED_PERIPHERAL, peripheralType, id, computerId);
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        Computer computer = checkIfComputerExist(computerId);
        computer.removePeripheral(peripheralType);
        for (Peripheral peripheral : this.peripherals) {
            if (peripheral.getClass().getSimpleName().equals(peripheralType)) {
                this.peripherals.remove(peripheral);
                return String.format(REMOVED_PERIPHERAL, peripheralType, peripheral.getId());
            }
        }
        return null;
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        Computer computer = checkIfComputerExist(computerId);
        String componentName = "onlineShop.models.products.components." + componentType;
        try {
            Class<?> aClass = Class.forName(componentName);
            Component newComponent = (Component) aClass.getConstructor(int.class, String.class, String.class, double.class, double.class, int.class).
                    newInstance(id, manufacturer, model, price, overallPerformance, generation);
            computer.addComponent(newComponent);
            this.components.add(newComponent);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(INVALID_COMPONENT_TYPE);
        }
        return String.format(ADDED_COMPONENT, componentType, id, computerId);
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        Computer computer = checkIfComputerExist(computerId);
        computer.removeComponent(componentType);
        for (Component component : this.components) {
            if (component.getClass().getSimpleName().equals(componentType)) {
                this.components.remove(component);
                return String.format(REMOVED_COMPONENT, componentType, component.getId());
            }
        }

        return null;
    }

    @Override
    public String buyComputer(int id) {
        Computer computer = checkIfComputerExist(id);
        this.computers.remove(computer);
        return computer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        buyComputerForBudget(budget);
        List<Computer> budgetComputers = this.computers.stream().filter(c -> c.getPrice() <= budget).collect(Collectors.toList());
        Computer bestComputer = null;
        for (Computer computer1 : budgetComputers) {
            if (bestComputer == null) {
                bestComputer = computer1;
            }
            if (computer1.getOverallPerformance() > computer1.getOverallPerformance()) {
                bestComputer = computer1;
            }
        }

        this.computers.remove(bestComputer);
        return bestComputer.toString();
    }


    @Override
    public String getComputerData(int id) {
        Computer computer = checkIfComputerExist(id);
        return computer.toString();
    }

    private Computer checkIfComputerExist(int computerId) {
        for (Computer computer : this.computers) {
            if (computer.getId() == computerId) {
                return computer;
            }
        }
        throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
    }

    private void buyComputerForBudget(double budget) {
        int length = this.computers.stream().filter(c -> c.getPrice() <= budget).toArray().length;
        if (this.computers.isEmpty() || length <= 0) {
            throw new IllegalArgumentException(String.format(CAN_NOT_BUY_COMPUTER, budget));
        }
    }
}
