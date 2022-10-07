package com.company.service;


import com.company.entities.Driver;
import com.company.entities.State;
import com.company.entities.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.company.Main.*;

public class ServiceImpl implements Service {
    List<Truck> trucks = new ArrayList<>(List.of(GSON.fromJson(readTruck(), Truck[].class)));
    List<Driver> drivers = new ArrayList<>(List.of(GSON.fromJson(readDriver(), Driver[].class)));


    public List<Truck> getTrucks() {
        return trucks;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void changeDriver(int truckId) {
        switch (trucks.get(truckId - 1).getState().ordinal()) {
            case 0 -> {
                Driver tmp = drivers.stream().filter(z -> z.getTruckName().length() == 0).findAny().orElse(null);
                if (tmp == null) System.out.println("Нет свободных водителей");
                else {
                    if (!trucks.get(truckId - 1).getDriver().equals("")) {
                        Driver tmp2 = drivers.stream().filter(z -> z.getTruckName().equals(trucks.get(truckId - 1).getTruckName())).findAny().orElse(null);
                        if (tmp2 != null) {
                            tmp2.setTruckName("");
                            drivers.set(tmp2.getIdDiver() - 1, tmp2);
                        }
                    }
                    trucks.get(truckId - 1).setDriver(tmp.getName());
                    trucks.set(truckId - 1, trucks.get(truckId - 1));
                    tmp.setTruckName(trucks.get(truckId - 1).getTruckName());
                    drivers.set(tmp.getIdDiver() - 1, tmp);
                    System.out.printf("Теперь грузовик <%s>, ведёт водитель <%s>\n", trucks.get(truckId - 1).getTruckName(), tmp.getName());
                }
            }
            case 1 ->
                    System.out.println("Грузовик <" + trucks.get(truckId - 1) + "> в пути, невозможно сменить водителя");
            default -> System.out.println("Нельзя сменить водителя");
        }
    }

    @Override
    public void startDriving(int truckId) {
        switch (trucks.get(truckId - 1).getState().ordinal()) {
            case 0 -> {
                if (trucks.get(truckId - 1).getDriver().length() > 1) {
                    trucks.get(truckId - 1).setState(State.ROUTE);
                    trucks.set(truckId - 1, trucks.get(truckId - 1));
                    System.out.println("Успешно вышли на маршрут <" + trucks.get(truckId - 1) + ">");
                } else System.out.println("Нет водителя");
            }
            case 1 -> System.out.println("Грузовик уже в пути");
            default -> {
                if (new Random().nextBoolean() && !trucks.get(truckId - 1).getDriver().equals("")) {
                    startDriving(trucks.get(truckId - 1).getId());
                } else {
                    trucks.get(truckId - 1).setState(State.BASE);
                    System.out.println("На базе");
                }
            }
        }
    }

    @Override
    public void startRepair(int truckId) {
        if (trucks.get(truckId - 1).getState().ordinal() == 2) {
            System.out.println(trucks.get(truckId - 1).getTruckName() + " уже в ремонте");
        } else {
            Truck t = trucks.get(truckId - 1);
            t.setState(State.REPAIR);
            trucks.set(truckId - 1, t);
            System.out.println("Грузовик " + trucks.get(truckId - 1).getTruckName() + " отправлен на ремонт");
        }
    }

    @Override
    public void changeTruckState() {
        Scanner s = new Scanner(System.in);
        System.out.print("ID грузовика: ");
        int select = s.nextInt();
        if (select > 0 && select <= trucks.size()) {
            Truck t = trucks.get(select - 1);
            System.out.printf("""
                    N : %d
                    Bus: %s
                    Driver : %s
                    Bus state : %s
                    """, t.getId(), t.getTruckName(), t.getDriver(), t.getState().name());
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
            writeTruck(trucks);
            writeDriver(drivers);
        } else System.out.println("Грузовик не найден");
        System.out.println("\nВведите + чтобы продолжить");
        if (!s.next().equals("+")) System.exit(0);
        else buttons();
    }
}












