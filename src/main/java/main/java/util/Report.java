package main.java.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
public class Report {
	static void report(){
		try {
			System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
			TransformerFactory transFact= TransformerFactory.newInstance(); 
			Transformer trans=transFact.newTransformer(new StreamSource("testng-results.xsl") );
			trans.setParameter("testNgXslt.outputDir", ReadConfigProperty.configpath+"\\test-output");
			trans.setParameter("testNgXslt.cssFile", "xml/custom.css");
			trans.setParameter("testNgXslt.showRuntimeTotals", "true");
			trans.setParameter("testNgXslt.sortTestCaseLinks", "true");
			trans.setParameter("testNgXslt.testDetailsFilter", "FAIL,PASS,SKIP,CONF,BY_CLASS");
			FileOutputStream os=new FileOutputStream(ReadConfigProperty.configpath+"\\test-output"+"\\report.html");
			trans.transform(new StreamSource(ReadConfigProperty.configpath+"\\test-output"+"\\testng-results.xml"), new StreamResult(os));
		} catch (TransformerConfigurationException e) {
			MainTestNG.LOGGER.info("TransformerConfigurationException"+e);
		
		} catch (FileNotFoundException e) {
			MainTestNG.LOGGER.info("FileNotFoundException"+e);
		} catch (TransformerException e) {
			MainTestNG.LOGGER.info("TransformerException"+e);
		}
	}
}
