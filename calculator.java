package first_den;

import static org.junit.Assert.*;

public class calculator {
	
	
	public static void main(String[] args) 
	{ 
		calculator t = new calculator();
		String a = t.test("2+2").toString();
		System.out.println(a);
		
		assertEquals( a , "4");
	} 
	

	public Integer test(String args) {
				
		String str = args;
		
		if(str.contains("+")) {
			
			String[] parts = str.split("\\+");
			return sum(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
		}
		else if(str.contains("-")) {
			
			String[] parts = str.split("\\-");
			return sub(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
		}
		else if(str.contains("*")) {
			
			String[] parts = str.split("\\*");
			return mult(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
		}
		return -99999;
	}
	
	public Integer sum(int a, int b) {
		
		return a+b;
		
	}
	
	public Integer sub(int a, int b) {
		
		return a-b;
		
	}
	
	public Integer mult(int a, int b) {
		
		return a*b;
		
	}
	
}


/*
 * Parametre olarak String degilde, ayrý ayrý alsaydý int a int b ve char operasyon seklinde, oyle olurdu,
calculator c = new calculator();
switch(op){
	case '+' : test_sum(a,b,expected);
	case '-' : test_sub(a,b,expected);
	case '*' : test_mult(a,b,expected);
}
*/
