package go;

import go.history.History;
import go.history.HistoryEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 */
public final class Game extends JPanel implements ActionListener {
	
    private History _history;
    private Goban _goban; 
	private Player [] _players; 
	private static int _playerTurn;
	
	private JLabel _infoLabel, _turnLabel;
	private JLabel [] _playerScoreLabel;
	
    private final int WIDTH_ASIDE_PANEL = 250;
    private final int HEIGHT_SCORE_PANEL = 150;
    private final int HEIGHT_ACTION_PANEL = 100;

    public Game(int size, Dimension screenSize) {
		super();
		
		/*
		 * Create layout.
		 */
		setSize(screenSize);
		setPreferredSize(screenSize);
		setLayout(new BorderLayout());
		setBackground(Color.blue);
//		setVisible(true);

		/*
		 * Create history and goban.
		 */
		_history = new History(WIDTH_ASIDE_PANEL, screenSize.height - HEIGHT_ACTION_PANEL - HEIGHT_SCORE_PANEL);
		_goban  = new Goban(size, Math.min(screenSize.width - WIDTH_ASIDE_PANEL, screenSize.height), this);

		/*
		 * Add players.
		 */
		_players = new Player[2];
		_players[Player.WHITE] = new Player("src/go/images/white_pawn.png", Player.WHITE);
		_players[Player.BLACK] = new Player("src/go/images/black_pawn.png", Player.BLACK);
		
		/*
		 * Set turn.
		 */
		_playerTurn = Player.WHITE;
		
		/*
		 * Create score panel.
		 */
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.setSize(WIDTH_ASIDE_PANEL, HEIGHT_SCORE_PANEL);
		scorePanel.setPreferredSize(new Dimension(WIDTH_ASIDE_PANEL, HEIGHT_SCORE_PANEL));
		
			/*
			 * Add elements to score panel.
			 */
			Box horizontalBox;
			
			JLabel colonLabel;
			colonLabel = new JLabel(":");
			colonLabel.setFont(new Font("Serif", Font.BOLD, 30));
			
			
			_playerScoreLabel = new JLabel[2];
			for (int i = 0; i < 2; ++i) {
				_playerScoreLabel[i] = new JLabel("0", this._players[i].GetIcon(), JLabel.CENTER);
				_playerScoreLabel[i].setFont(new Font("Serif", Font.BOLD, 32));
				_playerScoreLabel[i].setHorizontalTextPosition(JLabel.CENTER);
			}
			_playerScoreLabel[Player.BLACK].setForeground(Color.white);
				
			horizontalBox = Box.createHorizontalBox();
			horizontalBox.add(Box.createGlue());
	//		horizontalBox.add(new JSeparator(SwingConstants.VERTICAL));
			horizontalBox.add(_playerScoreLabel[Player.WHITE]);
			horizontalBox.add(colonLabel);
			horizontalBox.add(_playerScoreLabel[Player.BLACK]);
			horizontalBox.add(Box.createGlue());
			scorePanel.add(horizontalBox);

	//		label3 = new JLabel(icon);
			/*
			 * Create action panel.
			 */
			JPanel actionPanel = new JPanel();
			actionPanel.setLayout(new FlowLayout());
//			actionPanel.setBorder(BorderFactory.createEtchedBorder());
//			actionPanel.setBackground(java.awt.Color.yellow);
//			actionPanel.setSize(WIDTH_ASIDE_PANEL, HEIGHT_ACTION_PANEL);
//			actionPanel.setPreferredSize(new Dimension(WIDTH_ASIDE_PANEL, HEIGHT_ACTION_PANEL));
			
			JButton but = new JButton("Truce");
//			but.setPreferredSize(new Dimension(20,20));			
			but.setSize(new Dimension(20,20));
			but.addActionListener(this);
			actionPanel.add(but);
			but = new JButton("Rest");
			but.addActionListener(this);

//			but.setPreferredSize(new Dimension(10,20));
			actionPanel.add(but);
//			scorePanel.add(actionPanel);
			
			_turnLabel = new JLabel("turn");
			_turnLabel.setFont(new Font("Serif", Font.BOLD, 20));
			_turnLabel.setIcon(_players[GetTurn()].GetSmallIcon()); 
			horizontalBox = Box.createHorizontalBox();
			horizontalBox.add(Box.createGlue());
			horizontalBox.add(_turnLabel);
			horizontalBox.add(Box.createGlue());
			
			JPanel tmp = new JPanel();
			tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
			tmp.setBorder(BorderFactory.createLineBorder(Color.black));
			tmp.add(actionPanel);
			tmp.add(horizontalBox);
//			scorePanel.add(horizontalBox);
			scorePanel.add(tmp);

			_infoLabel = new JLabel("Good luck!");
			_infoLabel.setFont(new Font("Serif", Font.PLAIN, 23));
			horizontalBox = Box.createHorizontalBox();
			horizontalBox.add(Box.createGlue());
			horizontalBox.add(_infoLabel);
			horizontalBox.add(Box.createGlue());

			scorePanel.add(horizontalBox);
			
		/*
		 * Create left (goban) panel.
		 */
		JPanel gobanPanel = new JPanel(new FlowLayout());
//		gobanPanel.setBackground(java.awt.Color.blue);
        System.out.printf("Goban panel size %d %d\n", screenSize.width - WIDTH_ASIDE_PANEL, screenSize.height);

		gobanPanel.setSize(screenSize.width - WIDTH_ASIDE_PANEL, screenSize.height);
		gobanPanel.setPreferredSize(new Dimension(screenSize.width - WIDTH_ASIDE_PANEL, screenSize.height));
		add(gobanPanel, BorderLayout.WEST);	

		/*
		 * Create right (aside) panel.
		 */
		JPanel asidePanel = new JPanel(new BorderLayout());
		asidePanel.setBackground(java.awt.Color.green);
		asidePanel.setSize(WIDTH_ASIDE_PANEL, screenSize.height);
		asidePanel.setPreferredSize(new Dimension(WIDTH_ASIDE_PANEL, screenSize.height));
		add(asidePanel, BorderLayout.EAST);	
		/*
		 * Add elements to panels.
		 */
		gobanPanel.setVisible(true);
		gobanPanel.add(this._goban, BorderLayout.CENTER);
//		asidePanel.add(actionPanel);
		asidePanel.add(scorePanel, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(_history); 
		scrollPane.setPreferredSize(new Dimension (WIDTH_ASIDE_PANEL, screenSize.height - HEIGHT_ACTION_PANEL - HEIGHT_SCORE_PANEL));
		scrollPane.setSize(new Dimension (WIDTH_ASIDE_PANEL, screenSize.height - HEIGHT_ACTION_PANEL - HEIGHT_SCORE_PANEL));

		asidePanel.add(scrollPane, BorderLayout.SOUTH);
		
		repaint();
    }

    public void SettlePawn() {

    }

    public void Rest() {

    }

    public void Truce() {

    }

	public Player GetPlayerTurn() {
		return _players[Game._playerTurn];
	}
	
	static public int GetTurn() {
		return Game._playerTurn;
	}

	public void ChangeTurn() {
		_playerTurn = (_playerTurn + 1) % 2;
		_turnLabel.setIcon(_players[GetTurn()].GetSmallIcon()); 
	}
	
    public void AddHistoryEvent(int cordX, int cordY) {
		_history.Add(GetPlayerTurn(), cordX, cordY);
	}

	public int GetHistorySize() {
		return _history.GetSize();
	}

	public HistoryEvent GetHistoryEvent(int i) {
		return _history.GetEvent(i);
	}
	
	public Player GetPlayer(int i){
		return _players[i];
	}
	
	public void SetInfo(String info) {
		_infoLabel.setText(info);
	}
	
	public void AddPoints(int points) {
		_players[GetTurn()].AddScore(points);
		_playerScoreLabel[GetTurn()].setText(_players[GetTurn()].GetPoints());
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton but = (JButton) ae.getSource();
		if (but.getText() == "Rest") {
			ChangeTurn();
			SetInfo("Player is resting");
		} else if (but.getText() == "Truce") {
				
			if (JOptionPane.showConfirmDialog(null, "Do you accept?", "Truce", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				_goban.CountFinalPoints();
				
				String winner = new String();
				if(_players[0].GetScore() > _players[1].GetScore()) {
					winner += "White wins!";
				}
				else if(_players[0].GetScore() == _players[1].GetScore()) {
					winner += "Cute, we have the draw!";
				}
				else {
					winner += "Black wins!";
				}
//				winner += "Final results:\n";
//				winner = winner + _players[0].GetPoints();
//				winner += ":";				
//				winner += _players[1].GetPoints();
				JOptionPane.showMessageDialog(null, winner);
				
				System.exit(0);
			}
		}
	}
}
