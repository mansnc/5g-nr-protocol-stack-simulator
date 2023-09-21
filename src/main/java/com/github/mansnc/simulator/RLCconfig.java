package com.github.mansnc.simulator;


public class RLCconfig {

    public class Mode{
        private  boolean TM = false; // transparent Mode
        private  boolean UM = true; // unacknowledged Mode
        private  boolean AM = false; // acknowledged Mode; Not supported in current implementation
   
    // getter and setter for each
    public boolean isTM(){
        return TM;
    }
    public void setTM(boolean TM){
        this.TM = TM;
    }
    public boolean isUM(){
        return UM;
    }
    public void setUM(boolean UM){
        this.UM = UM;
    }
    public boolean isAM(){
        return AM;
    }
    public void setAM(boolean AM){
        if (AM){
            this.AM= AM;
        } else { 
            throw new UnsupportedOperationException("AM is not supported!");
            }
        
        }
    }

    private Mode mode = new Mode();
    public Mode getMode(){
        return mode;
    }
}


