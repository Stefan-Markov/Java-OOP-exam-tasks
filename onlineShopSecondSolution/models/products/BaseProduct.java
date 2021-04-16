package onlineShopSecondSolution.models.products;

import static onlineShopSecondSolution.common.constants.ExceptionMessages.*;

public abstract class BaseProduct implements Product {

    private int id;
    private String manufacturer;
    private String model;
    private double price;
    private double overallPerformance;

    protected BaseProduct(int id, String manufacturer, String model, double price, double overallPerformance) {
        setId(id);
        setManufacturer(manufacturer);
        setModel(model);
        setPrice(price);
        setOverallPerformance(overallPerformance);
    }

    public void setId(int id) {
        checkIfInputIsZeroOrNegative(id, INVALID_PRODUCT_ID);
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        checkNameNullEmpty(manufacturer, INVALID_MANUFACTURER);
        this.manufacturer = manufacturer;
    }


    public void setModel(String model) {
        checkNameNullEmpty(model, INVALID_MODEL);
        this.model = model;
    }

    public void setPrice(double price) {
        checkIfInputIsZeroOrNegative((int) price, INVALID_PRICE);
        this.price = price;
    }

    public void setOverallPerformance(double overallPerformance) {
        checkIfInputIsZeroOrNegative((int) overallPerformance, INVALID_OVERALL_PERFORMANCE);
        this.overallPerformance = overallPerformance;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public double getOverallPerformance() {
        return this.overallPerformance;
    }

    private void checkIfInputIsZeroOrNegative(int id, String message) {
        if (id <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void checkNameNullEmpty(String manufacturer, String invalidManufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException(invalidManufacturer);
        }
    }

    @Override
    public String toString() {


        return String.format("Overall Performance: %.2f. Price: %.2f - %s: %s %s (Id: %s)"

                , this.getOverallPerformance(), this.getPrice(), this.getClass().getSimpleName(), this.getManufacturer(), this.getModel(), this.getId());

    }
}
