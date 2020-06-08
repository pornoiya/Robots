package gui;

import java.util.HashMap;
import java.util.Map;

class State {
    Integer X;
    Integer Y;
    boolean IsMaximized;
    boolean IsMinimized;

    State(Integer x, Integer y, boolean isMin, boolean isMax){
        this.X = x;
        this.Y = y;
        this.IsMinimized = isMin;
        this.IsMaximized = isMax;
    }

    HashMap<String, Object> toHashMap(){
        var d = new HashMap<String, Object>();
        d.put("X", this.X);
        d.put("Y", this.Y);
        d.put("IsMinimized", this.IsMinimized);
        d.put("IsMaximized", this.IsMaximized);
        return d;
    }

    static State toState(Map<String, Object> map){
        return new State(((Long)map.get("X")).intValue(), ((Long)map.get("Y")).intValue(),
                (boolean)map.get("IsMinimized"), (boolean)map.get("IsMaximized"));
    }

}