package com.github.mansnc.simulator;

public class macLayer {


    public static String[] segmentIntoPDUs(String bitStream, int payLoadSize, int maxHeaderSize){

        if (bitStream!=""){

            int chunkSize  = payLoadSize - maxHeaderSize;  // chunk size per PDU
            int numberOfPDUs = (int) Math.ceil((double)bitStream.length()/chunkSize); // number of required PDUs

            // slicing the bitStream into chunks
            String[] chunkStrings = new String[numberOfPDUs];
            int startIndex = 0, endIndex = 0;
            int chunk_idx = 0;
            String tmpChunk="";
            while(endIndex<bitStream.length()){
                if (bitStream.length() - endIndex >=chunkSize){
                    endIndex+= chunkSize;
                    tmpChunk = bitStream.substring(startIndex, endIndex);
                }
                else if (bitStream.length() - endIndex <chunkSize){
                    endIndex = bitStream.length() ;
                    tmpChunk = bitStream.substring(startIndex, endIndex);
                }
                chunkStrings[chunk_idx] = tmpChunk;
                startIndex+=chunkSize;
                chunk_idx++;
            }

            // check if zero-padding is needed 
            if (chunkStrings[chunkStrings.length - 1].length()<chunkSize)
                chunkStrings = zeroPadding(chunkStrings, chunkSize);

            return chunkStrings;

                }
        else{
            System.out.println("Input is null; no data to be segmented!"); 
            String[] noSegments = new String[1];
            return noSegments;
            }
                
    }

    public static String[] zeroPadding(String[] chunkStrings, int chunkSize){
        String lastChunk = chunkStrings[chunkStrings.length - 1]; 
            int requiredZeros = chunkSize-lastChunk.length();
            while(requiredZeros>0){
                lastChunk+='0';
                requiredZeros--;
            }
            chunkStrings[chunkStrings.length - 1] = lastChunk;
            return chunkStrings;
    }

    public static void main(String args[]){
        String filePath = "/tv_in_01.txt";
        String rawData = DataInput.readFromFile(filePath);
        String bitStream = DataInput.convertToBitStream(rawData);
        System.out.println(bitStream);
        int payLoadSize = 8;
        int maxHeaderSize = 2;
        String[] segments = segmentIntoPDUs(bitStream, payLoadSize, maxHeaderSize);

    }
    
}
