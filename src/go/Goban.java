package go;

import go.history.HistoryEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 */
public class Goban extends JPanel {
	
	private List <Field> _fieldList;
	private Field [][] _fields;
    private int _size;
    private int _fieldSize;
    private Game _game;
	public List <Field> visited;

    Goban(int size, int screenSize, Game game) {
		super();
		
        _game = game;
        _size = size;
		Field.SetSize(Math.min(screenSize / size - 2, 50));
		_fields = new Field[size][size];
		visited = new LinkedList <Field>();
		
		Dimension dimension = new Dimension(Field.GetSize() * _size, Field.GetSize() * _size);
		setPreferredSize(dimension);
		setSize(dimension);
		setBackground(java.awt.Color.red);
        setLayout(new GridLayout(size, size));
		
		for (int i = 0 ; i < _size; ++i) {
			for (int j = 0 ; j < _size; ++j) {
				_fields[i][j] = new Field(this);
				add(_fields[i][j]);
//				_fieldList.add(_fields[i][j]);
			}
		}

		Field [] neighbours = new Field[4];
        for (int i = 0 ; i < _size; ++i) {
            for (int j = 0 ; j < _size; ++j) {
				for (int k = 0; k < 4; ++k) {
					neighbours[k] = null;
				}
				
				if (i > 0) {
					neighbours[0] = _fields[i - 1][j];
				}
				if (j > 0) {
					neighbours[1] = _fields[i][j - 1];
				}
				if (i < _size - 2) {
					neighbours[2] = _fields[i + 1][j];
				}
				if (j < _size - 2) {
					neighbours[3] = _fields[i][j + 1];
				}
				
				_fields[i][j].SetNeighbours(neighbours[3], neighbours[2], neighbours[1], neighbours[0]);
            }
        }
		neighbours = null;

        System.out.printf("Goban constructed: %dx%d fields with %d size; summary %dx%d\n", this._size, this._size, Field.GetSize(), Field.GetSize() * this._size, Field.GetSize() * this._size);
    }
    
    public void FieldClicked(Field field) {
		_game.SetInfo("Nice move!");

		// Try to settle pawn
		try {
			field.SettlePawn(_game.GetPlayerTurn());
			System.out.println("Pawn settled");
			int cordX = FindCordX(field);
			int cordY = FindCordY(field);
			System.out.printf("%d%d\n", cordX, cordY);
			this._game.AddPoints(this._fields[cordY][cordX].CarryOnBeating());
			System.out.format("He has %d points so far",this._game.GetPlayerTurn().GetScore());
			_game.AddHistoryEvent(cordX, cordY); 
			_game.GetPlayerTurn().AddScore(field.CarryOnBeating());
			_game.ChangeTurn();			
		} 
		catch( MoveException mException ){
			_game.SetInfo(mException.GetMessage());
//			System.out.format("%s", mException.GetMessage());
			//mException.GetMessage() zwraca stringa z komunikatem.
		}
    }

	public boolean Ko(Field field) {
		if (_game.GetHistorySize() > 4) {
			HistoryEvent tmp = _game.GetHistoryEvent(2);
			System.out.printf("%d == %d, %d == %d\n", tmp.GetRow(), FindCordY(field),
				tmp.GetColumn(), FindCordX(field));
			if (tmp.GetRow() == FindCordY(field) &&
				tmp.GetColumn() == FindCordX(field)) {
				return true;
			}
		}
		return false;
	}

	
	public int FindCordY(Field field) {
		int Y = 0;
		for (Y = 0 ; Y < _size; ++Y) {
			for (int X = 0 ; X < _size; ++X) {
				if (_fields[Y][X] == field) {
					return Y;
				}
			}
		}
		System.err.printf("FindCordX in Goban.class critical error!\n");
		return Y;
	}
	
	public int FindCordX(Field field) {
		int X = 0;
		for (int Y = 0 ; Y < _size; ++Y) {
			for (X = 0 ; X < _size; ++X) {
				if (_fields[Y][X] == field) {
					return X;
				}
			}
		}
		System.err.printf("FindCordX in Goban.class critical error!\n");
		return X;
	}
	
	public int GetSize() {
		return this._size;
	}

	public void CountFinalPoints() {
		if(!visited.isEmpty()){
			visited.clear();
		}
		System.out.println("1");
		int[] tmp = new int[2];
		int i = 0;
		int j = 0;
		System.out.println("2");
		for (; i < _size; ++i){
			for(; j < _size ; ++j) {
				boolean beenVisited = false;
				if(visited.contains(_fields[i][j])) {
					beenVisited = true; 
					break;					
				}
				if(!beenVisited) {
					tmp = _fields[i][j].CountPoints(visited);
					System.out.format("Conut points %d %d returns %d %d\n", i, j, tmp[0], tmp[1]);
					for (int k = -1; k < 2; ++k) {
						if (tmp[0] == k && k >= 0) {
							this._game.GetPlayer(k).AddScore(tmp[1]);
						}
					}
				}
			}
		}
	} // CountFinalPoints
	
}
