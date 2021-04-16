package easterRaces.repositories;
import easterRaces.entities.drivers.Driver;
import easterRaces.repositories.interfaces.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class DriverRepository implements Repository<Driver> {
    private Collection<Driver> models;

    public DriverRepository() {
        this.models = new HashSet<>();
    }

    @Override
    public Driver getByName(String name) {

        return this.models.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Collection<Driver> getAll() {
        return Collections.unmodifiableCollection(models);
    }

    @Override
    public void add(Driver model) {
        this.models.add(model);
    }

    @Override
    public boolean remove(Driver model) {
        return this.models.remove(model);
    }
}
