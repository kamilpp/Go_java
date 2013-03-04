package go.history;

//import go.Color;

import go.Player;


/**
 *
 */
public class HistoryEvent {
    private int _cordX;
    private int _cordY;
	private Player _owner;

    public HistoryEvent(Player player, int cordX, int cordY) {
		_owner = player;
		_cordX = cordX;
		_cordY = cordY;
    }

    public int GetColumn() {
        return _cordX;
    }

    public int GetRow() {
		return _cordY;
	}
    
    
}
