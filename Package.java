package MemoryGame;

import java.util.ArrayList;

public class Package {
    private int operation;
    private int senderID;
    ArrayList<String> parameters;

    public Package(int operation) {
        this.operation = operation;
        parameters = new ArrayList<>();
        senderID = 0; // 0 means server
    }

    public Package(String message) {
        if (message == null ) {
            System.out.println("I received null");
            return;
        }

        if (message.equals("exit")) {

        }
        message = message.trim();
        String[] tokens = message.split("#");
        this.senderID = Integer.parseInt(tokens[0]);
        this.operation = Integer.parseInt(tokens[1]);
        parameters = new ArrayList<>();
        for (int i=2; i<tokens.length; ++i) {
            addParameter(tokens[i]);
        }
    }

    public void addParameter(String par) {
        parameters.add(par);
    }

    public int getOperation() {
        return operation;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int id) {
        this.senderID = id;
    }

    /*parameter indexes start with 1*/
    public String getParameter(int index) {
        return parameters.get(index-1); //TODO check index boundry
    }


    public String toString() {
        String message = ""+getSenderID()+"#"+getOperation();
        for (int i=0; i<parameters.size(); ++i) {
            message += "#"+parameters.get(i);
        }
        return message;
    }
}
