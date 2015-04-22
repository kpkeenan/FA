import java.util.LinkedList;
public class FiniteAutomata {
	public static final int ASCII = 255;
	public NFAState startState;
	public NFAState currentState;
	public LinkedList<NFAState> states = new LinkedList<NFAState>();
	
	public FiniteAutomata(){
	}
	
	public FiniteAutomata(NFAState s){
		this.startState = s;
	}
	
	public void or(NFAState head1, NFAState head2){
		startState.addCharEdge('3', head1);
		startState.addCharEdge('3', head2);
	}
	
	public void star(NFAState head){
		NFAState newStart = new NFAState(ASCII,'3',false,true,0);
		this.startState = newStart;
		newStart.addCharEdge('3', head);
		for(NFAState  curr = head; curr!=null;curr = curr.getNext()){
			if(curr.isFinal()){
				curr.setStateStatus('f', false);
				curr.addCharEdge('3', head);
			}
		}
	}
	
	public NFAState getStartState(){
		return startState;
	}

	
	public boolean test(String input){
		boolean valid = false;
		return valid;
	}
	
	public void plus(NFAState head){
		for(NFAState n: states){
			if(n.isFinal()){
				n.setStateStatus('f', false);
				n.addCharEdge('3', head);
			}
		}
	}	
	public void renumber(){
		int i = 0;
		for(NFAState n : states){
			n.setID(i);
			i++;
		}
	}
	
	
	public boolean test(FiniteAutomata NFA, String testString){
		NFAState curr = NFA.startState;
		for(int i = 0; i < testString.length();i++){
			
			if(curr.charSet[testString.charAt(i)].size() == 0 && curr.charSet['3'].size() ==0) {
				return false;
			}
			if(curr.charSet[i]==null && curr.charSet['3']!=null){
				curr = curr.charSet['3'].get(0);
			}
			if(i==testString.length() && !curr.isFinal()) {
				System.out.println(curr.getEntryCharacter());
				return false;
			}
			else{
			curr = curr.getNext();
			}
		}
		return true;
	}
	
}
