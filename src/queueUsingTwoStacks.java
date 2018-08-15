public class queueUsingTwoStacks {

	private Stack<E> s1 = new Stack<E>();
	private Stack<E> s2 = new Stack<E>();
	
	public void queue(E item) {
		s1.push(item);
	}
	
	public E dequeue() {
		if(s2.isEmpty()) {
			while(!s1.isEmpty()) {
				s2.push(s1.pop());
			}
		}
		
		return s2.pop();
	}
	
}
