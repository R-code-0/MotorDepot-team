package com.company.entities;



public class Driver {
    private final int idDiver;
    private final String name;
    private final String truckName;

    public Driver(int idDiver, String name, String truckName) {
        this.idDiver = idDiver;
        this.name = name;
        this.truckName = truckName;
    }

    @Override
    public String toString() {
        int y = 16 - name.length();
        int z = 3 - (idDiver+"").length();
        return idDiver + " ".repeat(z) +  " | " + name + " ".repeat(y) + " | " + truckName;
    }
}
