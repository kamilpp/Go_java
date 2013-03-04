package go;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 */
public class Go extends JFrame {

    /**
     * @param args the command line arguments
     */
	
	Go() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GO v1.0");
		setVisible(true);

//		DisplaySplashScreen();
		RunGame();
	}
	
	private void DisplaySplashScreen() {
		setSize(200, 200);
		setResizable(false);

		JRadioButton [] button = new JRadioButton[3];
		button[0] = new JRadioButton("9");
		button[1] = new JRadioButton("13");
		button[2] = new JRadioButton("19");
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < 3; ++i) {
			group.add(button[i]);
		}
		
		JLabel picture;
		Icon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/go.png")).getScaledInstance(150, 150, 0));
		} catch (Exception ex) {
			System.err.printf("Loading file %s failed\n", "/images/go.png");
		}
		picture = new JLabel(img);
		picture.setVerticalTextPosition(JLabel.CENTER);
		picture.setVerticalAlignment(JLabel.CENTER);

		
		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		for (int i = 0; i < 3; ++i) {
			radioPanel.add(button[i]);
		}
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createGlue());
		horizontalBox.add(radioPanel);
		horizontalBox.add(Box.createGlue());
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(picture);
		add(horizontalBox);

		remove(picture);
		remove(horizontalBox);

		RunGame();
	}
	
	private void RunGame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.width -= 55;
		screenSize.height -= 26;
		setResizable(false);
		setSize(screenSize);
		setPreferredSize(screenSize);
		
		Game gra = new Game(19, screenSize);
		setContentPane(gra);
	}
	
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Go go = new Go();
			};
		});
   }
}

// resizowanie pól - nie mieści się 19!

















// GUI 
// pawny rysowac
// historia dodawac
// funkcja podswietlajaca mozliwe ruchy
// zmiana rundy
// score aktualizowac


				
//                gui.getContentPane().setBackground(java.awt.Color.red);
					// zczytaj rozmiar
			//	
			//	while (true) {
			//	    try {
			//	        // zczytaj akcje
			//		// wywolaj funkcje interpretera
			//		
			//		1. postaw pionek
			//		    game.SettlePawn(cordX, cordY)
			//		2. poddaj sie
			//			game.True();
			//		3. czekaj
			//			game.Rest();
			//		4. wyjdz
			//		
			//		5. gra skonczona
			//			
			//	    } catch (postawinio pionek) {
			//		// zmiana rundy
			//	    } catch (poddaj sie) {
			//		// zmiana rundy
			//	    } catch (czekaj) {
			//		// zmiana rundy
			//	    } catch (wyjdz) {
			//		// break
			//	    } catch (finished) {
			//		//kto wygrał
			//		//czy chcesz dalej grac
			//		// zczytaj rozmiar
			//		// this._game = new Game(rozmiar);
			//
			//	    }
			//	}
			//	*/