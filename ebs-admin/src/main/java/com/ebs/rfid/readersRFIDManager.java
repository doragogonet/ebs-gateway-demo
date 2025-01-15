package com.ebs.rfid;
import com.mot.rfid.api3.*;
import java.util.HashMap;
import java.util.Map;

public class readersRFIDManager {
    private static readersRFIDManager instance;
    private static Map<String, RFIDReader> readers;

    public readersRFIDManager() {
        readers = new HashMap<>();
    }
    // シングルトンインスタンスを取得
    public static readersRFIDManager getInstance() {
        if (instance == null) {
            instance = new readersRFIDManager();
        }
        return instance;
    }
    /**
     * Adds a new RFID reader to the manager.
     *
     * @param hostName   The hostname or IP address of the reader.
     * @param reader   The  reader.
     */
     public  void addReader(String hostName,RFIDReader reader) {
        readers.put(hostName, reader);
    }

    /**
     * get a new RFID reader to the manager.
     *
     * @param hostName   The hostname or IP address of the reader.
     */
     public  RFIDReader getReader(String hostName) {
	    RFIDReader reader = readers.getOrDefault(hostName,null);
        if (reader != null)
            System.out.println("getReader=>>>>>> " + reader.getHostName());
        return  reader;
     }


    /**
     * Disconnects all readers and clears the manager.
     */
    public  void shutdown() {
        readers.clear();
        System.out.println("All readers have been disconnected and cleared.");
    }

   
  }
