package go.history;

import go.Player;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

/**
 *
 */
public class History extends JTextArea {
	
    private List<HistoryEvent> _list;
	private ListModel _guiList; 

    public History(int width, int height) {
		super(5, 20);
		setEditable(false);

		
		//		_guiList = new DefaultListModel();
//		this.s
//		this.setSize(width, height);
//		this.setPreferredSize(new Dimension(width, height));
		
		_list = new ArrayList();
    }
	
//    public void paintComponent(Graphics g) {
//	
//    }
	
    /**
	 *
	 * @param player
	 * @param cordX
	 * @param cordY
	 */
	public void Add(Player player, Integer cordX, Integer cordY) {
		HistoryEvent event = new HistoryEvent(player, cordX, cordY);
		_list.add(event);
		append("Player " + player.GetColorName() + " (" + cordX + ";" + cordY + ")\n");
//        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
//        textArea.setCaretPosition(textArea.getDocument().getLength());
//		this.a
    }

    public int GetSize() {
       return _list.size();
    }

    public HistoryEvent GetEvent(int i) {
        return _list.get(GetSize() - i);
    }
}
