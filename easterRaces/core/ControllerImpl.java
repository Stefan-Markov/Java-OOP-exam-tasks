package easterRaces.core;

import easterRaces.common.OutputMessages;
import easterRaces.core.interfaces.Controller;
import easterRaces.entities.cars.Car;
import easterRaces.entities.cars.MuscleCar;
import easterRaces.entities.cars.SportsCar;
import easterRaces.entities.drivers.Driver;
import easterRaces.entities.drivers.DriverImpl;
import easterRaces.entities.racers.Race;
import easterRaces.entities.racers.RaceImpl;

import easterRaces.repositories.interfaces.Repository;

import java.util.*;

public class ControllerImpl implements Controller {

    private Repository<Driver> riderRepository;
    private Repository<Car> motorcycleRepository;
    private Repository<Race> raceRepository;

    public ControllerImpl(Repository<Driver> riderRepository, Repository<Car> motorcycleRepository, Repository<Race> raceRepository) {

        this.riderRepository = riderRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driver) {

        Driver driverS = new DriverImpl(driver);
        if (this.riderRepository.getByName(driver) != null) {
            throw new IllegalArgumentException(String.format("Driver %s is already created.", driver));
        }

        riderRepository.add(driverS);
        return String.format(OutputMessages.DRIVER_CREATED, driver);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        Car car;
        String message = "";

        if (this.motorcycleRepository.getByName(model) != null) {
            throw new IllegalArgumentException(String.format("Car %s is already created.", model));
        }

        if (type.equals("Muscle")) {
            car = new MuscleCar(model, horsePower);
            motorcycleRepository.add(car);
            message = String.format(OutputMessages.CAR_CREATED, type + "Car", model);
        } else if (type.equals("Sports")) {
            car = new SportsCar(model, horsePower);
            motorcycleRepository.add(car);
            message = String.format(OutputMessages.CAR_CREATED, type + "Car", model);
        }

        return message;
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {

        Car car = this.motorcycleRepository.getByName(carModel);

        Driver driver = this.riderRepository.getByName(driverName);

        if (driver == null) {
            throw new IllegalArgumentException(String.format("Driver %s could not be found.", driverName));
        }

        if (car == null) {
            throw new IllegalArgumentException(String.format("Car %s could not be found.", carModel));
        }

        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {

        Race race = this.raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format("Race %s could not be found.", raceName));
        }

        Driver driver = this.riderRepository.getByName(driverName);
        if (driver == null) {
            throw new IllegalArgumentException(String.format("Driver %s could not be found.", driverName));
        }
        if (driver.getCanParticipate()) {
            throw new IllegalArgumentException(String.format("Driver %s could not participate in race.", driverName));

        }
        race.addDriver(driver);

        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {

        Race race = this.raceRepository.getByName(raceName);

        if (race == null) {
            throw new IllegalArgumentException(String.format("Race %s could not be found.", raceName));
        }
        if (race.getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format("Race %s cannot start with less than 3 participants.", raceName));
        }
        int laps = race.getLaps();

        List<Driver> drivers = new ArrayList<>();

        for (Driver driver : race.getDrivers()) {
            double points = driver.getCar().calculateRacePoints(laps);
            drivers.add(driver);
        }

        drivers.stream().sorted(Comparator.comparing(e -> e.getCar().calculateRacePoints(laps))).limit(3);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Driver %s wins %s race.", drivers.get(0).getName(), raceName)).append(System.lineSeparator());
        sb.append(String.format("Driver %s is second in %s race.", drivers.get(1).getName(), raceName)).append(System.lineSeparator());
        sb.append(String.format("Driver %s is third in %s race.", drivers.get(2).getName(), raceName)).append(System.lineSeparator());

        this.raceRepository.remove(race);
        return sb.toString().trim();
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = new RaceImpl(name, laps);

        if (this.raceRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format("Race %s is already created.", name));
        }

        raceRepository.add(race);

        return String.format(OutputMessages.RACE_CREATED, name);
    }
}
