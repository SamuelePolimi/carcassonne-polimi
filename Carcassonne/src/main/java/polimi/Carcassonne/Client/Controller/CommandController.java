package polimi.Carcassonne.Client.Controller;


/**
 * This class has to split strings to use for commands
 * @author Omar Scotti - Samuele Tosatto
 */
public class CommandController {
	private String name;
	private String value[];
	/**
	 * Split strings at ':' and ','
	 * @param text: string to split
	 */
	public CommandController(String text){
		value = toStandardize(text).split(":");
		name = value[0];
		if(value.length>1){
		value=value[1].split(",");
		}
	}
	/**
	 * Prepare the string (remove ' ' and uniform the string to lower case)
	 * @param s: string to standardize
	 * @return standardized string
	 */
	private String toStandardize(String s){
		StringBuffer b = new StringBuffer();
		String t = s.toLowerCase();
		for(int i=0;i<s.length();i++){
			if(t.charAt(i)!=' '){
			b.append(t.charAt(i));
			}
		}
		return b.toString();
	}
	/**
	 * 
	 * @return name of the command
	 */
	public String getName(){
		return name;
	}
	/**
	 * 
	 * @param n: string to receive
	 * @return string
	 */
	public String getValue(int n){
		return value[n];
	}
	/**
	 * 
	 * @return length of value
	 */
	public int numberOfValues(){
		return value.length;
	}
}
