
public class getMinimumElement {

Stack<Integer> s1, s2;
	
	public MinStack() {
		
		s1 = new Stack<Integer>();
		s2 = new Stack<Integer>();
		
	}
	
	
	public void push(int x) {
		if(s2.isEmpty() || x <= getMin()) {
			s2.push(x);
		}
		s1.push(x);
	}
	
	
	public int top() {
		if(s1.isEmpty())
			return Integer.MAX_VALUE;
		return s2.peek();
	}
}
