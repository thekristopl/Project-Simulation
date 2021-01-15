package simulation;

import gui.*;
import java.util.ArrayList;
import layout.Layout;
import layout.devices.*;

public class Main {

    private MainFrame mainFrame; 

    public static void main(String[] args) {
        
        Main mainProgram = new Main();
        
        mainProgram.mainFrame = new MainFrame(new Layout());
        mainProgram.mainFrame.setSize(1280, 720);
        mainProgram.mainFrame.setVisible(true);
        
    }
}
