package com.ausy_technologies.finalproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class Audit {
    private static Audit single_instance = null;
    public String s;

    private Audit() {
        //System.out.println("Audit()");
    }

    public static Audit getInstance() {
        if (single_instance == null)
            single_instance = new Audit();
        return single_instance;
    }

    public void writePersonsToFile(String string) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("logs.csv", true))) {
            bufferedWriter.write("[" + timestamp + "] " + string);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("Could not write data to file");
            return;
        }
        System.out.println("Successfully wrote " + timestamp + "\n\n");
    }
}
