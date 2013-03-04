package go;
import go.history.History;
import go.history.HistoryEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 *
 */
public class Field extends JPanel implements MouseListener {
	private static BufferedImage _image;
    private static int _size;
    private static Goban _goban;
    private Pawn _pawn;
    private static History _history;
    private Field[] _neighbour = new Field[4];
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
	private static List <Field> visited = new ArrayList();

	public Field() {
	}
	
    public static void SetSize(int size) {
        Field._size = size;
    }
	
    public static int GetSize() {
		return Field._size;
    }
	
    public Field(Goban goban) {
        super();
		this._pawn = null;
        this.addMouseListener(this);
        this._goban = goban;
        //System.out.println("make field");
        //File _imageFile = new File("images/field.jpg");
        try {
            _image = ImageIO.read(this.getClass().getResource("images/field.jpg"));
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
        }
        setPreferredSize(new Dimension(Field._size, Field._size));
        setVisible(true);
    }
    
    
    public void SettlePawn(Player pawnOwner) throws MoveException {
		try{
			this.AffirmPosition(new Pawn(this, pawnOwner));
			//this._pawn = new Pawn(this, pawnOwner);
			this.repaint();
			System.out.println("SettlePawn");
		}
		catch (FieldException err){
			System.out.format("Exception id = %d", err.GetMessage());
			MoveException explanation = new MoveException();
			explanation.InstanceOfExcepton(err.GetMessage());
			throw explanation;
		}	
    }
	
	public void AffirmPosition(Pawn newPawn) throws FieldException  {
		if (this._pawn != null) {
			System.out.println("Field occupied!\n");
			FieldException error = new FieldException(1);
			throw error;
		}	
		this._pawn = newPawn;
		this.UpdateBreaths(-1);
		System.out.format("this.pawn._breaths: %d", this._pawn.GetBreaths());
		System.out.println("Checking Ko...");
		if (this.Ko())	{
			this.UpdateBreaths(1);
			this._pawn = null;
			FieldException err = new FieldException(2);
			throw err;
		}
		System.out.println("Checking Suicide...");
		if(this.Suicide())
		{
			System.out.println("Suicide returns true...");
			this.UpdateBreaths(1);
			this._pawn = null;
			FieldException err = new FieldException(3);
			throw err;
		}
	} // AffirmPosition

    /**
     *
     * @param hist
     */
    public void SetHistory(History hist) {
        Field._history = hist;
    } // SetHistory


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		System.out.println("sdsfds");
        g2d.drawImage(this._image.getScaledInstance(this._size, this._size, Image.SCALE_FAST), 0, 0, this);
        if (this._pawn != null)  {
            //System.out.println("Drawing pawn");
            g2d.drawImage(this._pawn.GetImage().getScaledInstance(this._size, this._size, Image.SCALE_FAST), 0, 0, this);
        }
    }
    
    public boolean Ko() {
		return Field._goban.Ko(this);
    } // Ko

    public boolean Suicide() {
		if (CheckIfBeatsOpponent()) {// check if pawn beats opponent stones, pawn can be added
            //this.CarryOnBeating();
            return false; // to nie jest samobojstwo
		}
		if (!CheckIfIsSuicide()) {
			return false;
		}
		return true;
    } // Suicide


    public boolean CheckIfBeatsOpponent() {
	System.out.println("CheckIfBeatsOpponent\n");
	int sum = 5; //zakladamy ze nie bije
	for (int i = 0; i < 4; i++) {
            if (this._neighbour[i] != null && 
		this._neighbour[i]._pawn != null && 
		this._neighbour[i]._pawn.GetColor() != this._pawn.GetColor()) {
			sum =  Math.min(sum, this._neighbour[i].NeighbourhoodCheck(Math.abs(this._pawn.GetColor()-1), 1));
		}
	}
	if (sum == 0) { // jesli jednak bije
            System.out.println("CheckIfBeatsOpponent returns TRUE!\n");
            return true;
	} else {
            System.out.println("CheckIfBeatsOpponent returns FALSE!\n");
            return false;
        }
    } // CheckIfBeatsOpponent

    public boolean CheckIfIsSuicide() {
		System.out.println("CheckIfIsSuicide\n");
		if (this.NeighbourhoodCheck(this._pawn.GetColor(), 1) == 0) { // bedzie zbity!
			System.out.println("CheckIfIsSuicide returns TRUE!\n");
			return true;
		} else {
			System.out.println("CheckIfIsSuicide returns FALSE!\n");
			return false;
		}
    } // CheckIfSuicide

    /**
     * Uwaga, tutaj trzeba sie zastanowic na co zamienić static vectora żeby bylo dobrze
     * @param colorArg
     * @param clear
     * @return
     */

    public int NeighbourhoodCheck(int colorArg, int clear) { //returns breaths of stone (group of stones)
		// nie ma co robić static, gc będzie po nas czyścił :D
		// nie chodzi o to żeby nie było smieci, tylko żeby działało tak jak w cpp...
		if(clear == 1) {
			visited.clear();
		}
		visited.add(this); 
		if (this._pawn.GetBreaths() == 0) {
            int lmax = 0;
            for(int i = 0; i < 4; ++i) {
                if (this._neighbour[i] != null && 
                    this._neighbour[i]._pawn != null &&
                    this._neighbour[i]._pawn.GetColor() == colorArg) { //jeśli istnieje i ma ten sam kolor
                      boolean beenVisited = false;
					  Iterator it = visited.iterator();
					  while (it.hasNext()) {
						if (it.next() == this._neighbour[i]) {
                          beenVisited = true; 
                          break;
						}
					  } // oraz nie był odwiedzony
					  if (!beenVisited) {
						  int tmp = this._neighbour[i].NeighbourhoodCheck(colorArg, 0);
						  //lmax = Math.max(lmax, this._neighbour[i].NeighbourhoodCheck(searchedColor, 0));
						  lmax = Math.max(lmax, tmp);
					  }
					}
			}
			System.out.format("neighbour max is and returns %d\n", lmax);
			return lmax;
		} else {
			return this._pawn.GetBreaths();
         }
	} // NeighbourhoodCheck()


    public int CarryOnBeating() {
		System.out.println("CarryOnBeating\n");
		int beatenStones = 0;
		for (int i = 0; i < 4; i++) {
			if (this._neighbour[i] != null && 
				this._neighbour[i]._pawn != null && 
				this._neighbour[i]._pawn.GetColor() != this._pawn.GetColor() &&
				this._neighbour[i].NeighbourhoodCheck(Math.abs(this._pawn.GetColor()-1), 1) == 0) {
				System.out.println("Condition satisfied - will be beaten\n");
				beatenStones += this._neighbour[i].Beat();
			}
		}
		return beatenStones;
    } // CarryOnBeating

    public int Beat() {
		int beatenStones = 1;
		int tmp_color = this._pawn.GetColor();
		System.out.println(" BeatOpponent\n");
		this.UpdateBreaths(1);
		this._pawn = null;
		this.repaint();
		System.out.println("Redraw and proceed to destroy neighbours\n");
		for (int i = 0; i < 4; i++) {
			if (this._neighbour[i] != null && 
						this._neighbour[i]._pawn != null && 
						this._neighbour[i]._pawn.GetColor() == tmp_color) {
				beatenStones += this._neighbour[i].Beat();
			}
	}
        return beatenStones;
    } // Beat

    public void UpdateBreaths(int flag) {	
		for (int i = 0; i < 4; ++i) {
            if (this._neighbour[i] != null && 
                this._neighbour[i]._pawn != null) {
                    _neighbour[i]._pawn.UpdateBreaths(flag);
                    this._pawn.UpdateBreaths(flag);
            }
		}
    } // UpdateBreaths

    float width() {
        return this._image.getWidth();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this._goban.FieldClicked(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

	public void SetNeighbours(Field up, Field right, Field down, Field left) {
		this._neighbour[UP] = up;
		this._neighbour[RIGHT] = right;
		this._neighbour[DOWN] = down;
		this._neighbour[LEFT] = left;
	}
	
	public Goban GetGoban(){
		return Field._goban;
	}
	
	public int[] CountPoints(List <Field> visited) //lista juz odwiedzonych
	{
		boolean foundSearchedColor = false; 
		Field tmp;				

		int[] out; 	
		out = new int[2];
		out[0] = -1;		
		out[1] = 0;			

		if (this._pawn != null) {
			return out;
		} 
		List <Field> search;		
		search = new LinkedList<Field>();
		search.add(this);			
		while(!search.isEmpty())		
		{

			tmp = search.get(0);	
			search.remove(0);
			if (tmp.GetGoban().FindCordY(tmp) == 0 && tmp.GetGoban().FindCordX(tmp) == 1) {
	//			System.out.format("%d %d %d %d %d", tmp, tmp._neighbour[0],tmp._neighbour[1],tmp._neighbour[2],tmp._neighbour[3]);
			}
			System.out.format("Obrabiamy %d %d\n", tmp.GetGoban().FindCordY(tmp), tmp.GetGoban().FindCordX(tmp));
			// jeśli nie był odwiedzony
			boolean beenVisited = false;
			if (visited.contains(tmp)) {
					beenVisited = true; 
					break;
			}
			if (beenVisited) {
				continue;
			}
			visited.add(tmp); 
			out[1]++;
			for(int i = 0 ; i < 4; ++i)
			{
				if (tmp._neighbour[i] == null) {
					continue;
				}
				if (tmp.GetGoban().FindCordY(tmp) == 0 && tmp.GetGoban().FindCordX(tmp) == 1) {
//					System.out.format("%d %d %d %d %d", tmp, tmp._neighbour[0],tmp._neighbour[1],tmp._neighbour[2],tmp._neighbour[3]);
				}
//				System.out.format("Obrabiamy neighboura %d %d color %d pawn %d\n", tmp._neighbour[i].GetGoban().FindCordY(tmp), tmp._neighbour[i].GetGoban().FindCordX(tmp), tmp._neighbour[i]._pawn, (tmp._neighbour[i]._pawn != null) ? tmp._neighbour[i]._pawn.GetColor() : 10 );
				if (!foundSearchedColor &&
					tmp._neighbour[i]._pawn != null) {
					foundSearchedColor = true;
					System.out.format("5 %d\n", out[0]);
					System.out.format("5.2 %d\n", tmp._neighbour[i]._pawn.GetColor());
					out[0] = tmp._neighbour[i]._pawn.GetColor();
				}
				if (tmp._neighbour[i]._pawn != null) {
					if (tmp._neighbour[i]._pawn.GetColor() != out[0]) {
						out[0] = -1;
					}				
				} else {
					search.add(tmp._neighbour[i]);		
				} 
			}
		}	
		return out;
	}
}
