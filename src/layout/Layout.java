package layout;

import layout.devices.*;
import layout.components.Socket;
import tools.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class Layout
{
    private HashMap<Integer, Router> devices;

    // ------------------------------------ constructors ------------------------------------

    public Layout()
    {
        devices = new HashMap<>();
    }

    // ------------------------------------ getters ------------------------------------

    public Router router(int routerID)
    {
        return devices.get(routerID);
    }


    // ------------------------------------ configure  ------------------------------------

    public String addRouter(Router r)
    {
        if(devices.containsKey(r.getID())) return "Cannot add " + r.getID() + ": specified ID is already here";


        Thread thread = new Thread(r);
        thread.start();

        devices.put(r.getID(), r);
        return r.getID() + " added successfully";
    }

    public String remRouter(int routerID)
    {
        if(!devices.containsKey(routerID)) return "Cannot remove " + routerID + ": there is no item with specified ID";

        for(Socket socket : devices.get(routerID).getAllSockets().values())
        {
            if(socket.getOuterSocket() != null) { socket.getOuterSocket().setOuterSocket(null); }
        }

        devices.get(routerID).stopThread();
        devices.get(routerID).hideConsole();
        devices.remove(routerID);

        return routerID + " removed successfully";
    }

    public String connect(Socket s1, Socket s2)
    {
        if(s1 == s2) return "Cannot connect, you idiot try to put two plugs in to one socket";
        if(s1 == null) return "Cannot connect, first socket is null";
        if(s2 == null) return "Cannot connect, second socket is null";
        if(s1.getOuterSocket() != null ) return "Cannot connect, " + s1.getFullName() + " is occupied";
        if(s2.getOuterSocket() != null ) return "Cannot connect, " + s2.getFullName() + " is occupied";
        s1.setOuterSocket(s2);
        s2.setOuterSocket(s1);
        return "Connected successfully: " + s1.getFullName() + " - " + s2.getFullName();
    }

    public String disconnect(Socket s)
    {
        if(s.getOuterSocket() == null) return "Cannot disconnect, this socket is not connected";

        String s2name = s.getOuterSocket().getFullName();
        s.getOuterSocket().setOuterSocket(null);
        s.setOuterSocket(null);
        return "Disconnected successfully: " + s.getFullName() + " - " + s2name;
    }

    public ArrayList<String> notConnectedPorts(int routerID)
    {
        ArrayList<String> free = new ArrayList<>();
        Router r = router(routerID);

        for(Socket socket : r.getAllSockets().values())
        {
            if(socket.isFree()) free.add(socket.getName());
        }

        return free;
    }

    public ArrayList<String> connectedPorts(int routerID)
    {
        ArrayList<String> free = new ArrayList<>();
        Router r = router(routerID);

        for(Socket socket : r.getAllSockets().values())
        {
            if(!socket.isFree()) free.add(socket.getName());
        }

        return free;
    }

    private String repairUniformity(boolean returnLog)
    {
        String log = "";
        for (Router router : devices.values())
        {
            HashMap<String, Socket> sockets = router.getAllSockets();

            for(Socket socket : sockets.values())
            {
                if(socket.getOuterSocket() == null) {}
                else if(socket != socket.getOuterSocket().getOuterSocket())
                {
                    log += socket.getFullName() + " pointed " + socket.getOuterSocket().getFullName() + "\n";
                    socket.setOuterSocket(null);
                }
            }
        }

        if(returnLog) return "Connection uniformity repaired: " + log + "\n\n";
        else return null;
    }


    public ArrayList<Connection> getConnectionsInfo()
    {
        HashMap<String, Connection> connections = new HashMap<>();

        for (Router r : devices.values())
        {
            HashMap<String, Socket> sockets = r.getAllSockets();

            for(Socket s : sockets.values())
            {
                if(s.getOuterSocket() != null)
                {
                    Socket s2 = s.getOuterSocket();
                    Connection c = new Connection(r.getID(),s.getName(), s2.getParentID(), s2.getName());

                    if(!connections.containsKey(s2.getFullName()))
                    {
                        connections.put(s.getFullName(), c);
                    }
                }
            }
        }
        ArrayList<Connection> array = new ArrayList(connections.values());
        return array;
    }

}
