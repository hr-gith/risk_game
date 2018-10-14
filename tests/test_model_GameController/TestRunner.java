package test_model_GameController;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;

import test_models.Test_StartupPhase; 


public class TestRunner {
	
	public static void main(String[] args){
		
		Result result = JUnitCore.runClasses(Test_StartupPhase.class); 
		
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString()); 
		}
		
		System.out.println(result.wasSuccessful()); 
		
		
	}
	
	

}