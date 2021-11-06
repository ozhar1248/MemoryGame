package MemoryGame;

import java.util.ArrayList;

public class Package {
    private int operation;
    ArrayList<String> parameters;

    public Package(int operation) {
        this.operation = operation;
        parameters = new ArrayList<>();
    }

    public Package(String message) {
        message = message.trim();
        String[] tokens = message.split("#");
        this.operation = Integer.parseInt(tokens[0]);
        parameters = new ArrayList<>();
        for (int i=1; i<tokens.length; ++i) {
            addParameter(tokens[i]);
        }
    }

    public void addParameter(String par) {
        parameters.add(par);
    }

    public int getOperation() {
        return operation;
    }

    /*parameter indexes start with 1*/
    public String getParameter(int index) {
        return parameters.get(index-1); //TODO check index boundry
    }

    public String toString() {
        String message = ""+getOperation();
        for (int i=0; i<parameters.size(); ++i) {
            message += "#"+parameters.get(i);
        }
        return message;
    }
}
