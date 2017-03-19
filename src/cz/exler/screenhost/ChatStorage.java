package cz.exler.screenhost;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ChatStorage is a class for storing list of chat records.
 * Chat record structure is defined by private helper class
 * <a ref="#ChatRecord">ChatRecord</a>.
 * It provides functionality for appending new chat record
 * and returning JSON representation of all stored log records.
 * Note: The string parts of each log record is base64 encoded.
 * The base64 encoding is done because it is more safe for passing
 * through the system.
 * <p>
 * TODO Suggested improvement: Add handling of parallel access
 */
public class ChatStorage {
    /**
     * Initiates chat record history with an empty list.
     */
    ChatStorage(){
        m_chatHistory = new ArrayList();
    }

    /**
     * Appends to the chat record list a new chat record item.
     * The chat record consists of current time, name of the user
     * and a message.
     *
     * @param username name of the user that wrote the message (base64 encoded)
     * @param message content of the message (base64 encoded)
     */
    public void addChatRecord(String username, String message){
        m_chatHistory.add(new ChatRecord(System.currentTimeMillis(),username, message));
    }

    /**
     * Generates JSON representation of all stored chat records.
     * The JSON format is as follows:
     * {"chatRecords":[
     * {"nameB64":"<user>","msgB64":"<message>"},
     * {"nameB64":"<user>","msgB64":"<message>"},
     * ...
     * ]}
     *
     * @return JSON formatted string representing all stored chat records
     */
    public String getAsJson(){
        String resultingJson = "{\"chatRecords\":[";
        Iterator it = m_chatHistory.iterator();
        while(it.hasNext()){
            ChatRecord current = (ChatRecord) it.next();
            resultingJson += current.getAsJson() + ",";
        }
        // Remove trailing ','
        if(resultingJson.endsWith(",")) {
            resultingJson = resultingJson.substring(0,resultingJson.length() - 1);
        }
        resultingJson += "]}";
        return resultingJson;
    }

    // Private
    /**
     * ChatRecord is helper class representing single chat record.
     * The chat record consists of time, String representation
     * of name of the user and String representation of a message.
     */
    private class ChatRecord{
        /**
         * Chat record data.
         */
        public long time;
        public String user;
        public String msg;

        /**
         * Constructs chat record from time, name of the user
         * and a message.
         *
         * @param timestamp long value representing timestamp
         *                  from when the message was recieved
         * @param username name of the user writing the message (base64 encoded)
         * @param message content of the message (base64 encoded)
         */
        ChatRecord(long timestamp, String username, String message){
            time = timestamp;
            user = username;
            msg = message;
        }

        /**
         * Generates JSON representation of a chat record. The JSON format
         * is as follows:
         * {"nameB64":"<user>","msgB64":"<message>"}
         *
         * @return JSON formatted string representing all single chat record
         */
        public String getAsJson(){
            return "{\"nameB64\":\""  + user + "\",\"msgB64\":\"" + msg + "\"}";
        }
    }

    /**
     * Internal variable for storing all log records
     */
    private ArrayList m_chatHistory = null;

}
