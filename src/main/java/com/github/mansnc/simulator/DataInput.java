package com.github.mansnc.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class DataInput {

    public static void randomBitStreamGenerator(config cfg){
        
        int length = cfg.randomDataGen.bitStreamLength;

        StringBuilder randomBitStreamBuilder = new StringBuilder(length);
        Random ran = new Random();
        for (int i=0; i<length; i++){
            randomBitStreamBuilder.append(ran.nextInt(2));
        }
       String randomBitStream = randomBitStreamBuilder.toString();
        System.out.println(randomBitStream.toString());
        String randDataPath = cfg.randomDataGen.folderPathToStoreData + cfg.randomDataGen.fileName;

        if (cfg.getInput.writeToTextFile){
            try {
                Files.write(Paths.get(randDataPath),randomBitStream.getBytes());
            } catch (Exception e) {
                System.out.println("Cannot write to file");
            }
        }


    }

    public static String readFromConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your text stream here: ");
        String s = sc.nextLine();
        return s;
    }

    public static String readFromFile(config cfg) {
        String dataPath = cfg.getInput.dataPath;
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

    public static String getBitStream(config cfg) {
        String bitStream="";
        if (cfg.getInput.enableRandomDataGen)
            randomBitStreamGenerator(cfg);
        if (cfg.getInput.readFromTextFile)
            bitStream = readFromFile(cfg);
        if (cfg.getInput.getFromConsole){
            String userInputConsole = readFromConsole();
            bitStream = convertToBitStream(userInputConsole);
        }
        
        return bitStream;
    }
}
