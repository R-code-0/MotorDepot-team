package com.company.entities;

public class Truck {
    private final int id;
    private final String truckName;
    private final String driver;
    private final State State;


    public Truck(int id, String truckName, String driver, State State) {
        this.id = id;
        this.truckName = truckName;
        this.driver = driver;
        this.State = State;

    }

    @Override
    public String toString() {
        int x = 16 - truckName.length();
        int y = 9 - driver.length();
        int z = 3 - (id + "").length();
        return id + " ".repeat(z >= 0 ? z : 1) + " | " + truckName + " ".repeat(x) + " | " + driver + " ".repeat(y) + " | " + State;
    }
}

enum State {
    BASE, ROUTE, REPAIR


}

