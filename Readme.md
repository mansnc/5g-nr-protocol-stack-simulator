# 5G NR Protocol Stack Simulator

- [5G NR Protocol Stack Simulator](#5g-nr-protocol-stack-simulator)
  - [Java Environment Setup](#java-environment-setup)
  - [Data Generation and Input Methods](#data-generation-and-input-methods)
    - [Random Bit Stream Generation](#random-bit-stream-generation)
    - [Read From Console](#read-from-console)
    - [Read From File](#read-from-file)
  - [PHY Layer](#phy-layer)
  - [Mac Layer](#mac-layer)
    - [Step 1: Get input data stream](#step-1-get-input-data-stream)
    - [Step 2: Segmentation to SDUs](#step-2-segmentation-to-sdus)
    - [Step 3: Add Mac Headers](#step-3-add-mac-headers)
  - [RLC Layer](#rlc-layer)

## Java Environment Setup
Powered by object oriented Java programing, this simulator will provide fully modular franework to to simulate and test fundamnetal functionalities of 5G protocol stack that aligns with 5G New Radio Specifications. **Maven** is also integrated to this project to facilitate project elements from sub layers to resources. At the moment, only development environment is setup Mac layer and unit test cases will be added accordingly once getting bit stream analyzed through modulations (from transmitter side) or through RLC (from receiver side). Any setup for any class will be fed through a separate class called **config** and each class or method will read appropriate parameters from this class. 

## Data Generation and Input Methods

All data input are handled through a class named  **DataInput** in which the input bit stream can be generated through the following methods: 

### Random Bit Stream Generation 
Getting config setup through **config** class in which the length of randomized bit stream is determined, this function will generate random bit stream with the given length and will store it in a file called **tv_01_ran_XX.txt**. The name indicates that this is going to be an input test vector (TV) later in unit test design procedure, "ran" indicates that this TV is generated through randomized method of this class and "XX" is the an indicate of the TV index, e.g., 01 for the first TV. This bit stream is stored in the resources directory automatically. Here is how this method can be called: 
```
config cfg = new config();
String bitstream = DataInput.getBitStream(cfg);
```
in which "getBitStream" is a method that calls all possible input types assuming the config setup. Here: 
```
if (cfg.getInput.enableRandomDataGen)
randomBitStreamGenerator(cfg);
```

### Read From Console
Input data can be read from the console in terms of the plain text, and then be converted into the bit stream accordingly. Here is how this method is called: 
```
String userInputConsole = readFromConsole();
bitStream = convertToBitStream(userInputConsole);
```

### Read From File
The input bit stream can be read from the input file as well in a txt format.
```
bitStream = readFromFile(cfg);
```
in which data path is given by config setup class. 


## PHY Layer 
To be continued...

## Mac Layer
Mac Layer class comprises of three main entities that each conducts one step of Mac processing: 

### Step 1: Get input data stream
By launching the **DataInput** class, the input data stream will be prepared for processing. 

### Step 2: Segmentation to SDUs
This step is all about making SDUs list in which the whole bit stream is segmented to different SDUs assuming the config setup for SDUs. 

```
String[] SDUsList = segmentIntoSDUs(bitStream, cfg);
```

### Step 3: Add Mac Headers

In this method, necessary header info will be added to all SDUs, re-packing them into PDUs and prepare for the next step. 

```
String[] PDUsList = addMacHeaders(SDUsList, cfg); 
```


## RLC Layer

To be continued....