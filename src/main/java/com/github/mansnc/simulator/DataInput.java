package com.github.mansnc.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DataInput {

    public static String readFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your data stream here: ");
        String s = sc.nextLine();
        return s;
    }

    public static String readFromFile(String filePath) {
        StringBuilder rawDataStream = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(DataInput.class.getResourceAsStream(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rawDataStream.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawDataStream.toString();
    }

    public static String convertToBitStream(String rawDataStream) {
        StringBuilder rawBitStream = new StringBuilder();
        for (char c : rawDataStream.toCharArray()) {
            String binaryString = Integer.toBinaryString(c);
            // here checking 8-bits for standard ASCII (might need 16 for Unicode)
            while (binaryString.length() < 8) {
                binaryString = "0" + binaryString;
            }
            rawBitStream.append(binaryString);
        }
        return rawBitStream.toString();
    }

    public static void main(String[] args) {

        String userInputConsole = readFromConsole();
        System.out.println("Here is your input: " + userInputConsole);
        String filePath = "/tv_in_01.txt";
        String userInputFile = readFromFile(filePath);
        System.out.println(userInputFile);
        String rawBitStream = convertToBitStream(userInputFile);
        System.out.println("Converted raw input to bitstream: " + rawBitStream);
    }
}
