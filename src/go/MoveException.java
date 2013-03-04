/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package go;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patryk
 */
public class MoveException extends Exception{
	private Map <Integer, String> _explanations = new HashMap<Integer, String>();
	private String _description;
	public MoveException() {
		super();
//		_explanations.put(new Integer(1), "This field is already occupied. Please chose another one.");
//		_explanations.put(new Integer(2), "Such move would cause the Ko. Please avoid such moves.");
//		_explanations.put(new Integer(3), "This is the suicidal move. It's forbidden.");
		_explanations.put(new Integer(1), "Field occupied!");
		_explanations.put(new Integer(2), "Ko! Illegal move!");
		_explanations.put(new Integer(3), "Suicide! Truce?");

	}
	public void InstanceOfExcepton(int i){
		this._description = this._explanations.get(new Integer(i));
	}
	public String GetMessage() {
		return this._description;
	}
	
}
