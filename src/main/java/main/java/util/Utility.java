package main.java.util;

public class Utility {
	
	private static ReadConfigProperty config = new ReadConfigProperty();
	public static String[] getTestCaseSheets(){
		String testsheetnme = "TestCase_SheetName";
		String testCasePath = config.getConfigValues(testsheetnme);
		return testCasePath.split(",");
	}

}
