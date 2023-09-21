package com.github.mansnc.simulator;

public class rlcLayer {

    public static void main(String[] args) {

        config cfg = new config();
        String bitStream = DataInput.getBitStream(cfg);

        String[] PDUs = macLayer.getPDUs(bitStream, cfg);

        if (cfg.getRLC().getMode().isAM()) {
            System.out.println("AM is enabled");
        }

    }

}
