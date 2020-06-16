package gui;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WindowStateControl {
    static Map<String, State> getState(List<JInternalFrame> frames) {
        HashMap<String, State> framesInf = new HashMap<String, State>();
        for (JInternalFrame frame: frames)
        {
            State state = new State(frame.getX(), frame.getY(),
                    frame.isIcon(), frame.isMaximum());
            framesInf.put(frame.getTitle(), state);
        }
        return framesInf;
    }

    static void saveState(Map<String, State> windowsInfo){
            Map<String, Map<String, Object>> newData = new HashMap<>();
            windowsInfo.forEach((key, state) -> newData.put(key, state.toHashMap()));
        var data = new JSONObject(newData);
        try {
            FileWriter jsonWriter = new FileWriter("windowStates1.json");
            data.writeJSONString(jsonWriter);
            jsonWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Map<String, State> readState(File statesJson) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(statesJson))
        {
            JSONObject obj = (JSONObject)jsonParser.parse(reader);
            Map<String, HashMap<String, Object>> statesHM = new HashMap<String, HashMap<String, Object>>(obj);
            Map<String, State> states = new HashMap<>();
            statesHM.forEach((key, hashmap) -> {
                states.put(key, State.toState(hashmap));
            });
            return states;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void resetStates(JInternalFrame w, State currentWindowState)
    {
        w.setLocation((int)(long)currentWindowState.X, (int)(long)currentWindowState.Y);
        try {
            w.setMaximum(currentWindowState.IsMaximized);
            w.setIcon(currentWindowState.IsMinimized);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    static Map<String, State> initStateFile(String filename){
        var jsonStatesFile = new File(filename);
        Map<String, State> states = new HashMap<>();
        if (jsonStatesFile.exists() && !jsonStatesFile.isDirectory()) {
            states = WindowStateControl.readState(jsonStatesFile);
        }
        return states;
    }
}
