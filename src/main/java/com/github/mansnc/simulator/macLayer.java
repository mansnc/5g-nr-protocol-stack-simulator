package com.github.mansnc.simulator;

public class macLayer {

/////////////////////////////////////////////////////////
    public static String[] segmentIntoSDUs(String bitStream, int TBS, int HeaderSize){

        if (bitStream!=""){

            int maxMacSDUsize  = TBS - HeaderSize;  // chunk size per PDU
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
    public static String[] addMacHeaders(String [] macSDUsList, int HeaderSize, String LCID_bits){
        String[] macPDUsList = new String[macSDUsList.length];;
        for (int i=0; i<macSDUsList.length; i++){
            // convert i to HeaderSized-bits
            String bin_i = Integer.toBinaryString(i); 
            while(bin_i.length()<HeaderSize){
                bin_i='0'+bin_i;
            }
            macPDUsList[i] = LCID_bits + bin_i + macSDUsList[i];
        }

        return macPDUsList;
    }
/////////////////////////////////////////////////////////
public static String[] getPDUs(){
        String dataPath = "/tv_in_ran_01.txt";
        String bitStream = DataInput.readFromFile(dataPath);
        //System.out.println(bitStream);
        
        int TBS = 7000; // Transport Block Size; 
        // Sub-carrier Spacing (SCS) in kHz	Approx. Max Transport Block Size in bits
        // 15	            ~7000 to ~10000
        // 30	            ~14000 to ~20000
        // 60	            ~28000 to ~40000
        // 120	            ~50000 to ~100000
        // 240	            ~100000 to ~200000
        // 480          	~200000 to ~400000
        int HeaderSize = 9;
        String LCID_bits = "01"; // for user data; "10" for control data
        String[] SDUsList = segmentIntoSDUs(bitStream, TBS, HeaderSize+LCID_bits.length());
        String[] PDUsList = addMacHeaders(SDUsList, HeaderSize, LCID_bits); 
        return PDUsList;
}

/////////////////////////////////////////////////////////
    public static void main(String args[]){

        // String dataPath = "/tv_in_01.txt";
        // String rawData = DataInput.readFromFile(dataPath);
        // String bitStream = DataInput.convertToBitStream(rawData);
        // System.out.println(bitStream);

        String dataPath = "/tv_in_ran_01.txt";
        String bitStream = DataInput.readFromFile(dataPath);
        System.out.println(bitStream);


        int TBS = 7000; // Transport Block Size; 
        // Sub-carrier Spacing (SCS) in kHz	Approx. Max Transport Block Size in bits
        // 15	            ~7000 to ~10000
        // 30	            ~14000 to ~20000
        // 60	            ~28000 to ~40000
        // 120	            ~50000 to ~100000
        // 240	            ~100000 to ~200000
        // 480          	~200000 to ~400000
        int HeaderSize = 9;
        String LCID_bits = "01"; // for user data; "10" for control data
        String[] SDUsList = segmentIntoSDUs(bitStream, TBS, HeaderSize+LCID_bits.length());
        String[] PDUsList = addMacHeaders(SDUsList, HeaderSize, LCID_bits); 

    }
    
}
