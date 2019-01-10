package Testing;

import java.io.IOException;

import org.testng.annotations.Factory;

public class Test1Factory {
	
	public int threadCount=4;//Should match with thread count in TestingTest1.xml
	
	@Factory
	public Object[] createInstances() throws IOException {
		Object[] result = new Object[threadCount]; 
		for (int i = 0; i < threadCount; i++) {
			System.out.println("Starting instance "+ i);
			result[i] = new Test1(i,threadCount);
		}
		System.out.println("Returning result");
		return result;
	}
	
}