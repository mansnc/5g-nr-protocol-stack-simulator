package com.github.mansnc.simulator;

public class macLayer {

/////////////////////////////////////////////////////////
    public static String[] segmentIntoSDUs(String bitStream, config cfg){
        int TBS = cfg.mac.TBS;
        int headerSize = cfg.mac.headerSize;
        
        if (bitStream!=""){

            int maxMacSDUsize  = TBS - headerSize;  // chunk size per PDU
            int numberOfSDUs = (int) Math.ceil((double)bitStream.length()/maxMacSDUsize); // number of required PDUs

            // slicing the bitStream into chunks
            String[] macSDUsList = new String[numberOfSDUs];
            int startIndex = 0, endIndex = 0;
            int chunk_idx = 0;
            String tmpChunk="";
            while(endIndex<bitStream.length()){
                if (bitStream.length() - endIndex >=maxMacSDUsize){
                    endIndex+= maxMacSDUsize;
                    tmpChunk = bitStream.substring(startIndex, endIndex);
                }
                else if (bitStream.length() - endIndex <maxMacSDUsize){
                    endIndex = bitStream.length() ;
                    tmpChunk = bitStream.substring(startIndex, endIndex);
                }
                macSDUsList[chunk_idx] = tmpChunk;
                startIndex+=maxMacSDUsize;
                chunk_idx++;
            }

            // check if zero-padding is needed 
            if (macSDUsList[macSDUsList.length - 1].length()<maxMacSDUsize)
                macSDUsList = zeroPadding(macSDUsList, maxMacSDUsize);

            //System.out.println(macSDUsList[macSDUsList.length - 1]);
            return macSDUsList;
                }
        else{
            System.out.println("Input is null; no data to be segmented!"); 
            String[] noSegments = new String[1];
            return noSegments;
            }
                
    }
/////////////////////////////////////////////////////////
    public static String[] zeroPadding(String[] macSDUsList, int maxMacSDUsize){
        String lastChunk = macSDUsList[macSDUsList.length - 1]; 
            int requiredZeros = maxMacSDUsize-lastChunk.length();
            while(requiredZeros>0){
                lastChunk+='0';

                requiredZeros--;
            }
            macSDUsList[macSDUsList.length - 1] = lastChunk;
            return macSDUsList;
    }
/////////////////////////////////////////////////////////
    public static String[] addMacHeaders(String [] macSDUsList, config cfg){
        int headerSize = cfg.mac.headerSize;
        String LCID_bits = cfg.mac.LCID_bits;

        String[] macPDUsList = new String[macSDUsList.length];;
        for (int i=0; i<macSDUsList.length; i++){
            // convert i to HeaderSized-bits
            String bin_i = Integer.toBinaryString(i); 
            while(bin_i.length()<headerSize){
                bin_i='0'+bin_i;
            }
            macPDUsList[i] = LCID_bits + bin_i + macSDUsList[i];
        }

        return macPDUsList;
    }
/////////////////////////////////////////////////////////
public static String[] getPDUs(String bitStream, config cfg){

        String[] SDUsList = segmentIntoSDUs(bitStream, cfg);
        String[] PDUsList = addMacHeaders(SDUsList, cfg); 
        return PDUsList;
}

/////////////////////////////////////////////////////////
    public static void main(String args[]){

        config cfg = new config();        
        
        String bitStream = DataInput.readFromFile(cfg);
        
        String[] SDUsList = segmentIntoSDUs(bitStream, cfg);
        String[] PDUsList = addMacHeaders(SDUsList, cfg); 

    }
    
}
