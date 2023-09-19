package com.github.mansnc.simulator;

public class main_app {

    public static void main(String[] args){
        config cfg = new config();
        String bitstream = DataInput.getBitStream(cfg);
        String[] PDUs = macLayer.getPDUs(bitstream, cfg);
    }

    
}
