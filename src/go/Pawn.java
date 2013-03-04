package go;

import java.awt.image.BufferedImage;

/**
 *
 */
public class Pawn {
//extends Color {
    private int _breaths;
	private Player _owner;
	private Field _field;
	
//    private Color _color;
//    private static int _size;
    
	Pawn(Field field, Player player) {
		this._breaths = 0;
		this._owner = player;
		this._field = field;
		this.BreathsInitialize(this._field.GetGoban().FindCordX(_field), this._field.GetGoban().FindCordY(_field), this._field.GetGoban().GetSize());
	}
    
    private void BreathsInitialize(int row, int column, int size ) {
		if ((row) > 0 && 
			(row) < (size)-1 &&
			(column) > 0 &&
			(column) < (size)-1) {
				this._breaths = 4;
		}
		else if (((row) == 0 && 
				(column) == 0) || ((row) == 0 && 
				(column) == (size)-1) || ((row) == (size)-1 && 
				(column) == 0) || ((row) == (size)-1 && 
				(column) == (size)-1)) {
					this._breaths = 2;
		}
		else
		{
			this._breaths = 3;
		}
		System.out.format("Breaths after initialization: %d\n", this._breaths);
    }
    
    public static void SetSize(int size) {
    
    }
	
	public BufferedImage GetImage() {
		return this._owner.GetImage();
	}
    
	public void UpdateBreaths(int breaths) {
        this._breaths += breaths;
    }

	public void ReduceBreaths(int breaths) {
        this._breaths -= breaths;
    }
    
    public void AddBreaths(int breaths) {
        this._breaths += breaths;
    }
    
    public int GetBreaths() {
        return this._breaths;
    }

	public int GetColor() {
        return _owner.GetColor();
    }
}

/*
 * class pawn 
{
private:
	static int _size;
	int _breaths;
	// virtual color _color;
public:
	color _color;
	pawn(int, int);
	void BreathsInitialize(int, int);
	static void SetSize(int);

	virtual char GetImage(); 

	void UpdateBreaths(int);
	void ReduceBreaths(int = 1);
	void AddBreaths(int = 1);

	int GetBreaths();
	color GetColor();
}; // class pawn

#endif // PAWN_HPP
 */