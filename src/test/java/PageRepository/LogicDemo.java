package PageRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class LogicDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Stack<String> stack = new Stack<String>();  

		stack.push("Ayush");  
		stack.push("Garvit");  
		stack.push("Amit");  
		stack.push("Ashish");  
		stack.push("Garima");  
        System.out.println("---"+stack.peek()+"---");
		
		stack.pop();  


		Iterator<String> itr=stack.iterator();  
		while(itr.hasNext()){  
		System.out.println(itr.next());  
		}  
		  
		
		
		
		
	}

}
