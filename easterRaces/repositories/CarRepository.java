package easterRaces.repositories;
import easterRaces.entities.cars.Car;
import easterRaces.repositories.interfaces.Repository;
import java.util.*;


public class CarRepository implements Repository<Car> {

    private Collection<Car> models;

    public CarRepository() {
        this.models = new HashSet<>();
    }

    @Override
    public Car getByName(String name) {

        Car car = this.models.stream().filter(f -> f.getModel().equals(name)).findFirst().orElse(null);

        return car;
    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableCollection(models);
    }

    @Override
    public void add(Car model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Car model) {
        return this.models.remove(model);
    }
}
