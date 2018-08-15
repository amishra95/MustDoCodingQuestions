
public class stackUsingTwoQueues {

	Queue<Integer> q1 = new LinkedList<Integer>();
	Queue<Integer> q2 = new LinkedList<Integer>();
	
	public int pop() {
		
		if(q1.peek() == null) {
			System.out.println("The stack is empty");
			int i = 0;
			return i;
		}
		else {
			int pop = q1.remove();
			return pop;
		}
	}
	 public void push(int data) {

	        if (q1.peek() == null) {
	            q1.add(data);
	        } else {
	            for (int i = q1.size(); i > 0; i--) {
	                q2.add(q1.remove());
	            }
	            q1.add(data);
	            for (int j = q2.size(); j > 0; j--) {
	                q1.add(q2.remove());
	            }

	        }
	    
	}
}
