import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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

public class EfficientWordMarkov extends BaseWordMarkov{
	
	private Map<WordGram,ArrayList<String>> myMap;
	
	/** Construct an EfficientMarkov object with
	 * the specified order
	 * @param order size of this markov generator
	 */
	public EfficientWordMarkov(int order) {
		super(order);
		myMap = new HashMap<WordGram,ArrayList<String>>();
	}
	
	/**
	 * Default constructor has order 3 from the BaseMarkov superclass
	 */
	public EfficientWordMarkov() {
		super();
		myMap = new HashMap<WordGram,ArrayList<String>>();
	}
	
	@Override
	public void setTraining(String text) {
		myMap.clear();
		myWords = text.split("\\s+");
		for(int index = 0; index < myWords.length() - this.myOrder + 1; index++) {
			String gramKey = myWords.substring(index, index + this.myOrder);
			String nextValue;
			if (index + this.myOrder >= myWords.length()) {
				nextValue = PSEUDO_EOS;
			}
			else {
				nextValue = Character.toString(myWords.charAt(index + this.myOrder));
			}
			if (myMap.get(gramKey) == null) {
				myMap.put(gramKey, new ArrayList<String>());
			}
			myMap.get(gramKey).add(nextValue);
		}
	}
	
	@Override
	public ArrayList<String> getFollows(WordGram key){
		if (!myMap.containsKey(key)) {
			throw new NoSuchElementException(key + " not in map");
		}
		return myMap.get(key);
	}
}