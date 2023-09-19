package com.github.mansnc.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class DataInput {

    public static void randomBitStreamGenerator(int length){
        StringBuilder randomBitStreamBuilder = new StringBuilder(length);
        Random ran = new Random();
        for (int i=0; i<length; i++){
            randomBitStreamBuilder.append(ran.nextInt(2));
        }
       String randomBitStream = randomBitStreamBuilder.toString();
        System.out.println(randomBitStream.toString());
        String dataPath = "5g-nr-protocol-stack-simulator/src/main/resources/" + "tv_in_ran_01.txt";
        try {
            Files.write(Paths.get(dataPath),randomBitStream.getBytes());
        } catch (Exception e) {
            System.out.println("Cannot write to file");
        }

    }

    public static String readFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your data stream here: ");
        String s = sc.nextLine();
        return s;
    }

    public static String readFromFile(String dataPath) {
        StringBuilder rawDataStream = new StringBuilder();
        // convert bytes to characters.
        InputStreamReader I_stream = new InputStreamReader(DataInput.class.getResourceAsStream(dataPath));
        // for efficiency reasons, the above line is often wrapped in a BufferedReader.
        // This reduces the number of IO operations by reading larger chunks at once and
        // buffering them.
        try (BufferedReader reader = new BufferedReader(I_stream)) {
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
        int bitStreamLength = 100000;
        randomBitStreamGenerator(bitStreamLength);
        // String userInputConsole = readFromConsole();
        // System.out.println("Here is your input: " + userInputConsole);
        String dataPath = "/tv_in_02.txt";
        String userInputFile = readFromFile(dataPath);
        System.out.println(userInputFile);
        String rawBitStream = convertToBitStream(userInputFile);
        System.out.println("Converted raw input to bitstream: " + rawBitStream);
    }
}
