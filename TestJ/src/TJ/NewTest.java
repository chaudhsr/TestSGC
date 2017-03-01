package TJ;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTest { 
 
	private WebDriver driver;
	Workbook wb;
	Sheet sh1;
	int numrow=0;
	int numcol=0;
	String value = null;
	 
	 @BeforeTest
		public void beforeTest() {			
			System.setProperty("webdriver.gecko.driver", "/Applications/Selenium/geckodriver") ;

		} 
	
	 @Test (dataProvider="testdata", priority =0)
	 
	 public void login(String userId, String passWord) throws InterruptedException, IOException

		{	
		 
		 	driver = new FirefoxDriver();
		    driver.manage().window().maximize();
			driver.get("http://www.demo.guru99.com/v4/");

		 	System.out.println (userId);
		 	System.out.println (passWord);
		 	
		//	driver.findElement(By.id("uid")).clear();

			driver.findElement(By.name("uid")).sendKeys(userId);
			driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);	
			
			//driver.findElement(By.id("password")).clear();
			driver.findElement(By.name("password")).sendKeys(passWord); 
			
						
			driver.findElement(By.name("btnLogin")).click();		 
			    
					
					  WebDriverWait wait = new WebDriverWait(driver, 0 /*timeout in seconds*/);

				       //until you see the image 
					   wait.until(ExpectedConditions.presenceOfElementLocated(By.className("barone")));						
					   System.out.println (driver.getTitle());
					   
					
						
						Thread.sleep(5000);
						
						if (driver.getTitle().contains("Guru99 Bank Manager HomePage"))
						{
							
							  WebElement element=driver.findElement(By.tagName("tr"));
							   List<WebElement> rowCollection=element.findElements(By.tagName("tr")); //how to get this?

							  // System.out.println("Number of rows in this table: "+rowCollection.size());							

								//Here i_RowNum and i_ColNum, i am using to indicate Row and Column numbers. It may or may not be required in real-time Test Cases.       
								int i_RowNum=1;
								for(WebElement rowElement:rowCollection)
								{
								      List<WebElement> colCollection=rowElement.findElements(By.tagName("td"));
								      int i_ColNum=1;
								      for(WebElement colElement:colCollection)
								      {
								          // System.out.println("Row "+i_RowNum+" Column "+i_ColNum+" Data "+colElement.getText());
								           
								           value = colElement.getText();
								           
								          //System.out.println(value);
								           
								           if (value.contains("Manger Id :"))
								           {
								        	   System.out.println (value);
								           }
								           
								           i_ColNum=i_ColNum+1;
								           
								          //System.out.println("\n");
								       }
								    i_RowNum=i_RowNum+1;
								 }	
								
							File screenshot =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
								
							FileUtils.copyFile(screenshot, new File("/Applications/Selenium/GuruBankScreenshot.jpg"));
		
							
						Assert.assertTrue(driver.getTitle().contains("Guru99 Bank Manager HomePage"),"Guru99 Bank Manager HomePage");
						}
						else
							
						{
	
								Alert al=driver.switchTo().alert();
								System.out.println(al.getText());
								al.accept();
						}
			
			   
					  
				

		}
	 
	 @DataProvider(name="testdata")

		public Object[][] TestDataFeed(){

		try {

		// load workbook
		wb=Workbook.getWorkbook(new File("/Users/anupamtyagi/Downloads/Parameterization.xls"));

		// load sheet in my case I am referring to first sheet only
		sh1= wb.getSheet(0);

		// get number of rows so that we can run loop based on this
		numrow= sh1.getRows();
		
		System.out.println(+numrow);
		
		// get number of columns so that we can run loop based on this
		numcol= sh1.getColumns();
				
		System.out.println(+numcol);

		}
		
		catch (Exception e)
		{
		e.printStackTrace();
		}

		// Create 2 D array and pass row and columns

		Object [][] bookdata=new Object[numrow][sh1.getColumns()];

		// This will run a loop and each iteration  it will fetch new row

		for(int i=0;i<numrow;i++){

		// Fetch first row username
		bookdata[i][0]=sh1.getCell(0,i).getContents();

		// Fetch first row password
		bookdata[i][1]=sh1.getCell(1,i).getContents();

		}

		// Return 2d array object so that test script can use the same

		return bookdata;

		}
	 
	 
//	@AfterMethod

//		public void tearDown()
//		{
//			driver.close();
//		}
	
	 
}
