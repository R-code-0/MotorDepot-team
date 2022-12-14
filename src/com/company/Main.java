package com.company;

import com.company.entities.Driver;
import com.company.entities.Truck;
import com.company.service.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static final GsonBuilder BUILDER = new GsonBuilder();
    public static final Gson GSON = BUILDER.setPrettyPrinting().create();
    public static final Path WRITE_PATH = Paths.get("./truck.json");
    public static final Path WRITE_PATH1 = Paths.get("./driver.json");

    public static void main(String[] args){
        buttons();
    }

    public static void buttons(){
        ServiceImpl srv = new ServiceImpl();
        System.out.println("""
                #   | Bus              |  Driver   |  State
                ————|——————————————————|———————————|—————————>""");
        for (Truck s : srv.getTrucks()) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("""
                #   | Driver           | Bus
                ————|——————————————————|——————————>""");
        for (Driver s : srv.getDrivers()) {
            System.out.println(s);
        }
        srv.changeTruckState();
    }


   public static String readTruck() {
       return getString(WRITE_PATH);
   }

   public static String readDriver() {
       return getString(WRITE_PATH1);
   }
    public static void writeTruck(List<Truck> truck) {
        try (FileWriter file = new FileWriter(WRITE_PATH.toString())) {
            file.write(GSON.toJson(truck));
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeDriver(List<Driver> driver) {
        try (FileWriter file = new FileWriter(WRITE_PATH1.toString())) {
            file.write(GSON.toJson(driver));
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getString(Path writePath1) {
        StringBuilder json = new StringBuilder();
        try (FileReader fr = new FileReader(String.valueOf(writePath1))){
            int a;
            while ((a = fr.read()) != -1) {
                json.append((char) a);
            }
            return json.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Завершение");
            System.exit(404);
        }
        return json.toString();
    }
}