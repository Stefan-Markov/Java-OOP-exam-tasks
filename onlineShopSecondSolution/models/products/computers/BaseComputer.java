package onlineShopSecondSolution.models.products.computers;

import onlineShopSecondSolution.models.products.BaseProduct;
import onlineShopSecondSolution.models.products.Product;
import onlineShopSecondSolution.models.products.components.Component;
import onlineShopSecondSolution.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;

import static onlineShopSecondSolution.common.constants.ExceptionMessages.*;

public abstract class BaseComputer extends BaseProduct implements Computer {
    private List<Component> components;
    private List<Peripheral> peripherals;


    protected BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }


    @Override
    public List<Component> getComponents() {
        return this.components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return this.peripherals;
    }

    @Override
    public void addComponent(Component component) {
        checkIfCollectionContainsProduct(component, this.getComponents(), EXISTING_COMPONENT);
        this.getComponents().add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component component = checkIfListContainsComponent(componentType, this.getComponents(), NOT_EXISTING_COMPONENT);
        this.getComponents().remove(component);
        return component;
    }


    @Override
    public void addPeripheral(Peripheral peripheral) {
        //да се види
        checkIfCollectionContainsProduct(peripheral, this.getPeripherals(), EXISTING_PERIPHERAL);
        this.getPeripherals().add(peripheral);
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral peripheral = checkIfListContainsComponent(peripheralType, this.getPeripherals(), NOT_EXISTING_PERIPHERAL);
        this.getPeripherals().remove(peripheral);
        return peripheral;
    }

    @Override
    public double getPrice() {
        double price = super.getPrice();
        for (Component component : this.components) {
            price += component.getPrice();
        }
        for (Peripheral peripheral : this.peripherals) {
            price += peripheral.getPrice();
        }
        return price;
    }

    @Override
    public double getOverallPerformance() {
        if (this.getComponents().isEmpty()) {
            return super.getOverallPerformance();
        }
        double average = this.getComponents().stream().mapToDouble(Product::getPrice).average().orElse(0);
        return super.getOverallPerformance() + average;
    }

    @Override
    public String toString() {
        StringBuilder computer = new StringBuilder(super.toString()).append(System.lineSeparator());
        String newRow = System.lineSeparator();
        computer.append(String.format(" Components (%s):", this.getComponents().size()
        )).append(System.lineSeparator());
        for (Component component : this.components) {

            computer.append("  ").append(component.toString()).append(newRow);
        }
        computer.append(String.format
                (" Peripherals (%s); Average Overall Performance (%.2f):",
                        this.getPeripherals().size(),
                        getAveragePeripheralPerformance()
                )).append(System.lineSeparator());
        for (Peripheral peripheral : this.getPeripherals()) {
            computer.append("  ").append(peripheral.toString()).append(newRow);
        }

        return computer.toString().trim();
    }

    private double getAveragePeripheralPerformance() {
        return this.getPeripherals().stream().mapToDouble(Product::getOverallPerformance).average().orElse(0);
    }

    private <T> void checkIfCollectionContainsProduct(T computerProduct, List<T> listComputerProducts, String exceptionMessage) {
        if (listComputerProducts.contains(computerProduct)) {
            throw new IllegalArgumentException(String.format
                    (exceptionMessage, computerProduct.getClass().getSimpleName(),
                            this.getClass().getSimpleName(),
                            this.getId()));
        }
    }

    private <T> T checkIfListContainsComponent(String productType, List<T> listToCheckTheType, String exception) {
        for (T component : listToCheckTheType) {
            if (component.getClass().getSimpleName().equals(productType)) {
                return component;
            }
        }
        throw new IllegalArgumentException(String.format
                (exception, productType, this.getClass().getSimpleName(), this.getId()));
    }

}
