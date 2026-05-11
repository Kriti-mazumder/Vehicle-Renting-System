import java.util.UUID;

// --- VEHICLE HIERARCHY ---
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
    } // 150 taka per day

    @Override
    public double calculateRent(int days) {

        return days * baseRatePerDay;
    }
}

// 2. INHERITANCE & POLYMORPHISM: Car subclass
class Car extends Vehicle {
    public Car(String model) { super(model, 500.0); } // 500 taka per day

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}

// Subclass 3: Truck
class Truck extends Vehicle {
    public Truck(String model) { super(model, 1000.0); } // 1000 Taka/day

    @Override
    public double calculateRent(int days) {
        // Trucks might have a heavy-duty surcharge
        return (days * baseRatePerDay) ;
    }
}
// Subclass 4: Microbus
class Microbus extends Vehicle {
    public Microbus(String model) { super(model, 720.0); } // 720 Taka/day

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}
// Subclass 5: Luxury Car (High-end pricing)
class LuxuryCar extends Vehicle {
    public LuxuryCar(String model) { super(model, 1500.0); } // 1500 Taka/day

    @Override
    public double calculateRent(int days) {
        return days * baseRatePerDay;
    }
}