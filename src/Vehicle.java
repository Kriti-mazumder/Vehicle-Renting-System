import java.util.UUID;

abstract class Vehicle {
    private String vehicleCode;
    private String model;
    protected double baseRatePerDay;

    public Vehicle(String model, double baseRatePerDay) {
        this.vehicleCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.model = model;
        this.baseRatePerDay = baseRatePerDay;
    }
    public abstract double calculateRent(int days);
    public String getVehicleCode() { return vehicleCode; }
    public String getModel() { return model; }
}

class Bike extends Vehicle {
    public Bike(String model) {
        super(model, 150.0);
    }

    @Override
    public double calculateRent(int days) {

        return days * baseRatePerDay;
    }
}


class Car extends Vehicle {
    public Car(String model) { super(model, 500.0); }

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}


class Truck extends Vehicle {
    public Truck(String model) { super(model, 1000.0); }

    @Override
    public double calculateRent(int days) {
        return (days * baseRatePerDay) ;
    }
}

class Microbus extends Vehicle {
    public Microbus(String model) { super(model, 720.0); }

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String model) { super(model, 1500.0); }

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}