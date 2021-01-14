package layout.deamons;

import layout.components.Config;
import layout.components.Package;
import layout.components.Socket;

import java.util.HashMap;

public abstract class Daemon implements Config
{
    public boolean processPackage(Package p)
    {
        // if solved
        // send and return null

        // if not solved
        return false;
    }

    public void processOwnTasks()
    {
        // if do nothing
        return;
    }
}