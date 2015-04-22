import java.util.*;
public class NFAState {
	public ArrayList<NFAState> charSet[];
	public NFAState next;
	private boolean isFinal;
	private boolean isStart;
	private char entryCharacter;
	private int ID;
	
	public NFAState(int n,char ent,boolean s,boolean f,int id){
		charSet = new ArrayList[n];
		for(int i = 0; i < n; i++){
			charSet[i] = new ArrayList();	
			}
		entryCharacter = ent;
		isStart = s;
		isFinal = f; 
		ID = id;
	}
	
	public NFAState(){
		charSet = new ArrayList[255];
		for(int i = 0; i < charSet.length;i++){
			charSet[i] = new ArrayList();
		}
		entryCharacter ='3';
		isStart = true;
		isFinal = false;
	}
	
	public void addCharEdge(char i,NFAState state){
		this.charSet[i].add(state);
	}
	
	public void setEntryCharacter(char c){
		entryCharacter = c;
	}
	
	public char getEntryCharacter(){
		return entryCharacter;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
	
	public boolean isStart(){
		return isStart;
	}
	
	public void setID(int id){
		ID = id;
	}
	
	public void setStateStatus(char s,boolean f){
		switch(s){
		case 's': isStart = f; break;
		case 'f': isFinal = f;  break;
		default: System.out.println("bad character passed to state finality method: "  + s);break;
		}
	}
	
	
	public boolean isNull(char c){
		return charSet[c].size()==0;
	}
	
	public NFAState getNext(){
		return next;
	}
	
	public void setNext(char c, NFAState next){
	
	}
	
	public  NFAState getStateAt(char c){
		return charSet[(int)c].get(0);
	}
	
	public boolean matches(NFAState head, int len,char[] c){
		boolean found = true;
		return found;
	}
}