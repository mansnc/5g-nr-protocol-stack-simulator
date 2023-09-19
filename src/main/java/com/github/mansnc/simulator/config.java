package com.github.mansnc.simulator;

public class config {
    
    public static class Mac{
        public  String LCID_bits = "01"; // for user data; "10" for control data
        public int TBS = 7000;
        // Sub-carrier Spacing (SCS) in kHz	Approx. Max Transport Block Size in bits
        // 15	            ~7000 to ~10000
        // 30	            ~14000 to ~20000
        // 60	            ~28000 to ~40000
        // 120	            ~50000 to ~100000
        // 240	            ~100000 to ~200000
        // 480          	~200000 to ~400000
        public int numerology = 0;
        public int headerSize = 9;
       
    }

    public static class Input{
        boolean getFromConsole = false;
        boolean readFromTextFile = true;
        boolean readFromBinaryFile = false;
        boolean writeToTextFile = true;
        boolean writeToBinaryFile = false;
        boolean enableRandomDataGen = true;

        String dataPath = "/tv_in_ran_01.txt";
    }

    public static class randDataGen{
        String fileName = "/tv_in_ran_01.txt";
        String folderPathToStoreData = "5g-nr-protocol-stack-simulator/src/main/resources/";
        int bitStreamLength = 100000; // to be used when random data generation is initiated
    }

    public Mac mac = new Mac();
    public Input getInput = new Input();
    public randDataGen randomDataGen = new randDataGen();
}
