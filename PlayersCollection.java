package MemoryGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PlayersCollection {
    private Map<Integer,Player> mapPlayers;
    private Stack<Integer> removedInt;
    private int highestUnusedInt;

    public PlayersCollection() {
        mapPlayers = new HashMap<>();
        removedInt = new Stack<>();
        highestUnusedInt = 1; //because 0 is server
    }

    public Player waitForNewPlayer(ServerSocket serversocket) {
        Socket socket = null;
        try {
            socket = serversocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = (!removedInt.empty())? removedInt.pop() : highestUnusedInt++;
        Player player = new Player(socket,id);
        mapPlayers.put(id,player);
        return player;
    }

    public void removePlayer(Player player) {
        int id = player.getId();
        mapPlayers.remove(id);
        removedInt.add(id);
    }

    public Player getPlayer(int id) {
        return mapPlayers.get(id);
    }

}
