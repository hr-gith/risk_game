package test_views;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 * Runner class for junit tests
 * 
 *
 */
public class Runner {
	
	/**
	 * this is the main method of the runner class for junit testing
	 * @param args arguments for the main method of test class
	 */
	public static void main(String[] args){
		
		Result result = JUnitCore.runClasses(World_Domination_ViewTest.class);
		
	     for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
		
	}

}