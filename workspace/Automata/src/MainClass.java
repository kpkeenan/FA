import java.util.*;

public class MainClass {

	public static final int ASCII = 255;
	public static final char EPSILON = '3';
	public static Stack infixStack = new Stack();
	public static StringBuilder postfixString = new StringBuilder();
	public static FiniteAutomata NFA = new FiniteAutomata();
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		init(in);
	}
	
	public static void init(Scanner in){

		System.out.println("Please enter the regular language: ");
		String rL = in.next();
		String parsedString = parseString(rL);
		buildAutomata(parsedString);
		boolean keepTesting = true;
		String answer = "", input = "";
		System.out.println("Enter string to test against this language: ");
		input = in.next();
		boolean valid = NFA.test(NFA,input);
		if(valid) System.out.println("This string was valid");
		if(!valid) System.out.println("This string was NOT valid");
		iterate(NFA,input);
		while(keepTesting){
			System.out.println("Would you like to test another string? a.y \n b.no");
			answer = in.next();
			switch(answer.charAt(0)){
			case 'y':
				System.out.println("Please enter another string to test against this regular language: ");
				answer = in.next();
				valid = NFA.test(NFA, answer);
				System.out.println("Was the string valid? " + valid);
				keepTesting = true;
			case 'n':
			default:
				keepTesting = false;
				break;
			}
		}
	}
	
	public static boolean stillInString(String input, char i){
		return i>=0 && i <= input.length()-1;
	}
	
	public static void iterate(FiniteAutomata NFA, String input){
		char[] charInput = input.toCharArray();
		NFAState curr= NFA.getStartState().getNext();
	}
	
	public static void buildAutomata(String parsedString){
		NFA.startState = new NFAState(ASCII,EPSILON,true,false,0);
		NFA.currentState = NFA.startState;
		char[] parsedArray = parsedString.toCharArray();
		System.out.println(parsedString);
		for(int i = 0; i < parsedArray.length;i++){
			char character = parsedArray[i];
			switch(character){
			case'*':
				NFA.star(NFA.startState);
				NFA.renumber();
				break;
			case '+':
				NFA.plus(NFA.startState);
				NFA.renumber();
				break;
			case '|':
				NFA.or(NFA.startState, NFA.currentState);
				break;
			default:
				NFAState newState = new NFAState(ASCII,character,false,false,i);
				if(i==parsedString.length()-1){
					newState.setStateStatus('f', true);
				}
				NFA.currentState.addCharEdge(character, newState);
				NFA.states.add(newState);
				NFA.currentState.next = newState;
				NFA.currentState = NFA.currentState.next;
				break;
			}
		}
	}
	
	public static boolean testString(String input){
		return NFA.test(input);
	}
	
	public static String parseString(String regExp){
		char[] chars = regExp.toCharArray();
		for(int i = 0; i < regExp.length(); i++){
			char character = chars[i];
			if (!isOp(character)){
				postfixString.append(character); //append if just a regular character
			}
			else{
				if(isRightParentheses(character)){
					while(!infixStack.isEmpty() && (char)infixStack.peek()!= '('){
						postfixString.append((char)infixStack.pop());
					}
					if(!infixStack.isEmpty()){
						infixStack.pop();
					}
				}
				else{
					while(!infixStack.isEmpty() && lowerPrecedence(character,(char)infixStack.peek())){
						char c = (char)infixStack.pop();
						if(!isLeftParentheses(c)){
							postfixString.append(c);
						}
						else{
							character = c;
						}
					}
					infixStack.push(character);
				}
			}	
		}
		  while (!infixStack.isEmpty()) {
		      postfixString.append((char)infixStack.pop());
		    }
		return postfixString.toString();
	}
	
	public static boolean isLeftParentheses(char c){
		return c =='(';
	}
	
	public static boolean lowerPrecedence(char op1, char op2){
		switch(op1){
		case '*':
		case '+':
		case '|': return !(op2 == '*') || !(op2 == '+');
		case '(': return true;
		case '.': return op2 =='(';
		default: return false;
		}
	}
	
	public static boolean isRightParentheses(char c){
		return c==')';
	}
	
	public static boolean isOp(char c){
		return c=='*' ||c =='+' || c == '.' || c== '(' || c==')' || c =='|';
	}
}
