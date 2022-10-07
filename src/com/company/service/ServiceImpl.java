package com.company.service;


import com.company.entities.Driver;
import com.company.entities.State;
import com.company.entities.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.company.Main.*;

public class ServiceImpl implements Service{
    List<Truck> trucks = new ArrayList<>(List.of(GSON.fromJson(readTruck(), Truck[].class)));
    List<Driver> drivers = new ArrayList<>(List.of(GSON.fromJson(readDriver(),Driver[].class)));


    public List<Truck> getTrucks() {
        return trucks;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void changeDriver(int truckId) {

    }

    @Override
    public void startDriving(int truckId) {

    }

    @Override
    public void startRepair(int truckId) {

    }

    @Override
    public void changeTruckState() {
        Scanner s = new Scanner(System.in);
        System.out.print("ID грузовика: ");
        int select = s.nextInt();
        if (select > 0 && select <= trucks.size()) {
            Truck t = trucks.get(select - 1);
            System.out.print("Выберите действие:\n1. Сменить водителя\n2. Отправить на маршрут\n3. Отправить на ремонт\n4. Восстанивить все данные json\n: ");
            int x = s.nextInt() - 1;
            if (x != 3) {
                switch (x) {
                    case 0 -> changeDriver(t.getId());
                    case 1 -> startDriving(t.getId());
                    case 2 -> startRepair(t.getId());
                    default -> System.out.println("Действие не найдено");
                }
            } else {
                drivers = drivers.stream().peek(drv -> drv.setTruckName("")).toList();
                trucks = trucks.stream().peek(trk -> trk.setDriver("")).peek(trk -> trk.setState(State.BASE)).toList();
                System.out.println("Выполнено");
            }
        } else System.out.println("Грузовик не найден");
    }
}












