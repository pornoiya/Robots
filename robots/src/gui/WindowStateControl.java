package gui;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WindowStateControl {
    static HashMap<String, HashMap> getState(ArrayList<JInternalFrame> frames) {
        var framesInf = new HashMap<String, HashMap>();
        for (JInternalFrame frame: frames)
        {
            var frameStateInfo = new HashMap<>();
            frameStateInfo.put("x", frame.getX());
            frameStateInfo.put("y", frame.getY());
            frameStateInfo.put("isMaximized", frame.isMaximum());
            frameStateInfo.put("isMinimized", frame.isIcon());
            framesInf.put(frame.getTitle(), frameStateInfo);
        }
        return framesInf;
    }

    static void saveState(HashMap<String, HashMap> windowsInfo){
        var data = new JSONObject(windowsInfo);
        try {
            FileWriter jsonWriter = new FileWriter("windowStates.json");
            data.writeJSONString(jsonWriter);
            jsonWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    static HashMap<String, HashMap> readState(File statesJson) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(statesJson))
        {
            JSONObject obj = (JSONObject)jsonParser.parse(reader);
            return new HashMap<String, HashMap>(obj);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void resetStates(JInternalFrame w, HashMap currentWindowState)
    {
        w.setLocation((int)(long)currentWindowState.get("x"), (int)(long)currentWindowState.get("y"));
        try {
            w.setMaximum((boolean)currentWindowState.get("isMaximized"));
            w.setIcon((boolean)currentWindowState.get("isMinimized"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}