import java.util.*;

/**
 * Efficient class for Markov text generation. This version
 * rescans the training text for each randomly-generated
 * character. This makes generating N characters an O(T+N) 
 * task where T is the size of the training text
 * 
 * 
 * @author Alec Ashforth
 *
 */

public class EfficientMarkov extends BaseMarkov{
	
	private Map<String,ArrayList<String>> myMap;
	
	/** Construct an EfficientMarkov object with
	 * the specified order
	 * @param order size of this markov generator
	 */
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<String,ArrayList<String>>();
	}
	
	/**
	 * Default constructor has order 3 from the BaseMarkov superclass
	 */
	public EfficientMarkov() {
		super();
		myMap = new HashMap<String,ArrayList<String>>();
	}
	
	@Override
	public void setTraining(String text) {
		myMap.clear();
		myText = text;
		for(int index = 0; index < myText.length() - this.myOrder + 1; index++) {
			String gramKey = myText.substring(index, index + this.myOrder);
			String nextValue;
			if (index + this.myOrder >= myText.length()) {
				nextValue = PSEUDO_EOS;
			}
			else {
				nextValue = Character.toString(myText.charAt(index + this.myOrder));
			}
			if (myMap.get(gramKey) == null) {
				myMap.put(gramKey, new ArrayList<String>());
			}
			myMap.get(gramKey).add(nextValue);
		}
	}
	
	@Override
	public ArrayList<String> getFollows(String key){
		if (!myMap.containsKey(key)) {
			throw new NoSuchElementException(key + " not in map");
		}
		return myMap.get(key);
	}
}
