package onlineShop.models.products.computers;

import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.COMPUTER_COMPONENTS_TO_STRING;
import static onlineShop.common.constants.OutputMessages.COMPUTER_PERIPHERALS_TO_STRING;

public abstract class BaseComputer extends BaseProduct implements Computer {

    private List<Component> components;
    private List<Peripheral> peripherals;

    protected BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public double getOverallPerformance() {
        return super.getOverallPerformance() + this.components
                .stream()
                .mapToDouble(Product::getOverallPerformance)
                .average()
                .orElse(0);
    }

    @Override
    public double getPrice() {
        return super.getPrice() +
                this.components
                .stream()
                .mapToDouble(Product::getPrice)
                .sum()
                +
                this.peripherals
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public List<Component> getComponents() {
        return Collections.unmodifiableList(this.components);
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return Collections.unmodifiableList(this.peripherals);
    }

    @Override
    public void addComponent(Component component) {
        if (this.components.stream().anyMatch(c -> c.getClass().getSimpleName().equals(component.getClass().getSimpleName()))) {
            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT, component.getClass().getSimpleName(), this.getClass().getSimpleName(), this.getId()));
        }

        this.components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component component = this.components.stream()
                .filter(c -> c.getClass().getSimpleName().equals(componentType))
                .findFirst()
                .orElse(null);

        if (component == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType, this.getClass().getSimpleName(), this.getId()));
        }

        this.components.remove(component);
        return component;
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        if (this.peripherals.stream().anyMatch(p -> p.getClass().getSimpleName().equals(peripheral.getClass().getSimpleName()))) {
            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL, peripheral.getClass().getSimpleName(), this.getClass().getSimpleName(), this.getId()));
        }

        this.peripherals.add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral peripheral = this.peripherals.stream()
                .filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .findFirst()
                .orElse(null);

        if (peripheral == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL, peripheralType, this.getClass().getSimpleName(), this.getId()));
        }

        this.peripherals.remove(peripheral);
        return peripheral;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(super.toString())
                .append(System.lineSeparator());
        output.append(String.format(COMPUTER_COMPONENTS_TO_STRING, this.components.size()))
                .append(System.lineSeparator());

        for (Component component : this.components) {
            output.append("  ").append(component.toString())
                    .append(System.lineSeparator());
        }

        output.append(String.format(COMPUTER_PERIPHERALS_TO_STRING, this.peripherals.size(), this.peripherals.stream().mapToDouble(Product::getOverallPerformance).average().orElse(0)))
                .append(System.lineSeparator());

        for (Peripheral peripheral : this.peripherals) {
            output.append("  ").append(peripheral.toString())
                    .append(System.lineSeparator());
        }

        return output.toString().trim();
    }
}
