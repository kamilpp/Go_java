
package go;

/**
 *
 * @author patryk
 */
public class FieldException extends Exception {
	private int _message;
	public FieldException(int message){
		super();
		this._message = message;
	}
	int GetMessage(){
		return this._message;
	}
}
