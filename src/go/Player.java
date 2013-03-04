package go;

import go.history.HistoryEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 */
public class Player {
	
	public final static int WHITE = 0;
	public final static int BLACK = 1;
    private Integer _score;
	private BufferedImage _image;
	private ImageIcon _icon, _smallIcon;
	private int _color;
	private List <Pawn> _pawnList;

    public Player(String pathToImage, int color) {
		this._score = 0;
		this._color = color;
		
		try {
			_image = ImageIO.read(new File(pathToImage));
			_icon = new ImageIcon(_image.getScaledInstance(70, 70, 0));
			_smallIcon = new ImageIcon(_image.getScaledInstance(25, 25, 0));
		} catch (IOException e) {
			System.err.printf("Loading file %s failed\n", pathToImage);
		}

    }
	
	public BufferedImage GetImage() {
		return this._image;
	}
    
    /**
     *
     * @param Points
     */
    public void AddScore(int Points) {
		this._score += Points;
    }

    public int GetScore() {
		return this._score;
    }

	public ImageIcon GetIcon() {
		return this._icon;
	}

	public ImageIcon GetSmallIcon() {
		return this._smallIcon;
	}
//	String GetDescription() {
//		return this._description;
//	}

    int GetColor() {
		return _color;
    }

	String GetPoints() {
		return _score.toString();
	}

	/**
	 *
	 * @return
	 */
	public String GetColorName() {
		if (_color == WHITE) {
			return "WHITE";
		} else {
			return "BLACK";
		}
	}
}
