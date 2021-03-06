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
        d.put("x", this.X);
        d.put("y", this.Y);
        d.put("isMinimized", this.IsMinimized);
        d.put("isMaximized", this.IsMaximized);
        return d;
    }

    static State toState(Map<String, Object> map){
        return new State(((Long)map.get("x")).intValue(), ((Long)map.get("y")).intValue(),
                (boolean)map.get("isMinimized"), (boolean)map.get("isMaximized"));
    }

}