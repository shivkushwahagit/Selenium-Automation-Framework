package main.java.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

public class ExcelLibrary {
	static Map<String, Workbook> workbooktable = new HashMap<String, Workbook>();
	public static Map<String, Integer> dict = new Hashtable<String, Integer>();
	public static List<String> list = new ArrayList<String>();
	static ReadConfigProperty config = new ReadConfigProperty();
	static int rowTestcase;
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;
    static XSSFRow row;
    static XSSFCell cell;
	public static Workbook getWorkbook(String path) {
		Workbook workbook = null;
		if (workbooktable.containsKey(path)) {
			workbook = workbooktable.get(path);
		} else {
			try {
				InputStream file = new FileInputStream(path);
				workbook = WorkbookFactory.create(file);
				workbooktable.put(path, workbook);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				MainTestNG.LOGGER.info("FileNotFoundException" + e);
			} catch (IllegalFormatException e) {
				e.printStackTrace();
				MainTestNG.LOGGER.info("InvalidFormatException" + e);
			} catch (IOException e) {
				e.printStackTrace();
				MainTestNG.LOGGER.info("IOException" + e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	public static List<String> getNumberOfSheetsinSuite(String testPath) {
		List<String> listOfSheets = new ArrayList<String>();
		Workbook workbook = getWorkbook(testPath);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			listOfSheets.add(workbook.getSheetName(i));
		}
		return listOfSheets;
	}

	/**
	 * To get the number of sheets in test data sheet
	 */
	public static List<String> getNumberOfSheetsinTestDataSheet(String testPath) {
		List<String> listOfSheets = new ArrayList<String>();
		Workbook workbook = getWorkbook(testPath);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if (!(workbook.getSheetName(i)).equalsIgnoreCase(config.getConfigValues("TestCase_SheetName"))) {
				listOfSheets.add(workbook.getSheetName(i));
		    	}
			}
		return listOfSheets;
	}

	public static int getRows(String testSheetName, String pathOfFile)
			throws IllegalFormatException, IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Reporter.log("getting total number of rows");
		Sheet sheet = workbook.getSheet(testSheetName);
		return sheet.getLastRowNum();
	}
	
	public static int readTestCase(String Testcase,String path,String excelsheetname)throws IllegalFormatException, IOException
    {
    try {
    	Workbook workbook = getWorkbook(path);
    	Sheet sheet = workbook.getSheet(excelsheetname); 
        int rowNum = sheet.getLastRowNum()+1;
        int colNum = sheet.getRow(0).getLastCellNum();
        for (int i=1; i<rowNum; i++){
        	Row row =sheet.getRow(i);
            int j=0;
                   Cell cell = row.getCell(j);
                      String value = cell.toString();
                            if(value.equalsIgnoreCase(Testcase))
                               {
                                rowTestcase=row.getRowNum();
                                    return rowTestcase;
                                }
                            }
                      //  file.close();
    }
     catch(Exception e){
         e.printStackTrace();
    }
    System.out.println("rownumber Done: " +rowTestcase);
    return -1;
    }

	/**
	 * Get the total columns inside excel sheet
	 */
	public static int getColumns(String testSheetName, String pathOfFile)
			throws IllegalFormatException, IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Reporter.log("getting total number of columns");
		Sheet sheet = workbook.getSheet(testSheetName);
		return sheet.getRow(0).getLastCellNum();

	}

	/**
	 * Get the column names inside excel sheet
	 */
	public static List getColumnNames(String testSheetName, String pathOfFile,int j) throws IllegalFormatException, IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		for (int i = 0; i <= j; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue().toString());
			}
		}
		return list;
	}

	/**
	 * Get the total number of rows for each column inside excel sheet
	 */
	public static void getNumberOfRowsPerColumn(String testSheetName,
			String pathOfFile, int j) throws IllegalFormatException,
			IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		int totColumns = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i <= totColumns; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue().toString());
			}
		}
	}

	/**
	 * Read the content of the cell
	 */
	public static String readCell(int rowNum, int colNum, String testSheetName,String pathOfFile) {
		Workbook workbook;
		String cellValue = null;
		workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			Cell cell = row.getCell(colNum);
			if (cell != null) {
				DataFormatter dataFormatter = new DataFormatter();
				String data = dataFormatter.formatCellValue(cell);
				cellValue = data;
			}
		}
		return cellValue;
	}
	
	 public static void setCellData(int rowNum,int colNum,String testSheetName,String pathOfFile,String setValue) throws Exception{
	      try{
	    	Workbook workbook;
	  		workbook = getWorkbook(pathOfFile);
	  		Sheet sheet = workbook.getSheet(testSheetName);
	  		Row row = sheet.getRow(rowNum); 
	  		Cell cell=row.getCell(colNum, Row.RETURN_BLANK_AS_NULL);
	  		if(cell==null) {
                cell=row.createCell(colNum);
                cell.setCellValue(setValue);
	  		  }
                else
                {
                 cell.setCellValue(setValue);
                }
	           OutputStream fileOut = new FileOutputStream(pathOfFile);
	           workbook.write(fileOut);
	           ((FileInputStream) workbook).close();
	           fileOut.flush();
	           fileOut.close();
	          
	         }
	       catch(Exception e){
	       throw (e);
	    }
	  } 
	 public static boolean openFile(String fileName) {
         try {
         	FileInputStream  file = new FileInputStream(fileName);
             workbook = new XSSFWorkbook(file);
             file.close();
             return true;
         }
         catch(Exception e) {
             e.printStackTrace();
             System.out.println("Excel file is not opened.");
             return false;
         }
     }

      public static void setCellDataNew(String Result,String path,String excelsheetname,int RowNum, int ColNum) throws Exception{
			   try{ 
			        openFile(path);
		            sheet = workbook.getSheet(excelsheetname);
		            row=sheet.getRow(RowNum);  
                    cell=row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
                    if(cell==null) {
                       cell=row.createCell(ColNum);
				       cell.setCellValue(Result);
				    }else
				      {
					  cell.setCellValue(Result);
				      }
				FileOutputStream fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				 fileOut.flush();
					fileOut.close();
				     }
			   catch(Exception e){
					  throw (e);
			}
		}	
	 
	public void clean() {
		workbooktable.clear();
		list.clear();
	}

}