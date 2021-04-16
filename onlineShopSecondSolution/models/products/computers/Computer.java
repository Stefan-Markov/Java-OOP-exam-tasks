package onlineShopSecondSolution.models.products.computers;

import onlineShopSecondSolution.models.products.Product;
import onlineShopSecondSolution.models.products.components.Component;
import onlineShopSecondSolution.models.products.peripherals.Peripheral;

import java.util.List;

public interface Computer extends Product {
    List<Component> getComponents();

    List<Peripheral> getPeripherals();

    void addComponent(Component component);

    Component removeComponent(String componentType);

    void addPeripheral(Peripheral peripheral);

    Peripheral removePeripheral(String peripheralType);
}