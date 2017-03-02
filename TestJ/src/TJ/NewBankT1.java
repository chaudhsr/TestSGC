package TJ;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class NewBankT1 {
	
	public WebDriver driver;

	Workbook wb;
	Sheet sh1;
	int numrow=0;
	int numcol=0;
	int numrow1=0;
	int numcol1=0;
	String custIdCaptured = null;

		int l=4;
		
	String value = null;
	
	@BeforeMethod
	  public void beforeMethod() {
			System.setProperty("webdriver.gecko.driver", "/Applications/Selenium/geckodriver") ;
			//driver = new FirefoxDriver();
		   
	  }
	
	 @Test (dataProvider="Logindata", priority =0)

   public void funcLogin(String userId, String passWord) throws InterruptedException {
	  	
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://www.demo.guru99.com/v4/");
		driver.findElement(By.name("uid")).sendKeys(userId);
		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);	
		
		//driver.findElement(By.id("password")).clear();
		driver.findElement(By.name("password")).sendKeys(passWord); 			
		driver.findElement(By.name("btnLogin")).click();
		
		Thread.sleep(3000);
		    
		  WebDriverWait wait = new WebDriverWait(driver, 0 /*timeout in seconds*/);
	       //until you see the image 
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.className("barone")));						
		  System.out.println (driver.getTitle());
		  Assert.assertTrue(driver.getTitle().contains("Guru99 Bank Manager HomePage"),"Guru99 Bank Manager HomePage");	
		  
		  	  
  }
  
  @DataProvider(name="Logindata")
	public Object[][] TestDataFeed(){

	try {
	// load workbook
	wb=Workbook.getWorkbook(new File("/Users/anupamtyagi/Downloads/addCust1.xls"));

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
  
  
	 @Test (dataProvider="AddCustomerdata", priority =1)

	   public void AddCust(String custName, String gender, String bday, String address, String city, String state, String pinCode, String mobile, String emailId, String pwd) throws InterruptedException {

		 
		// System.out.println(custName);
		// System.out.println(gender);
		// System.out.println(bday);
		// System.out.println(address);
		// System.out.println(city);
		// System.out.println(state);
		// System.out.println(pinCode);
		// System.out.println(mobile);
		// System.out.println(emailId);
		// System.out.println(pwd);
		 
		   List<WebElement> links=driver.findElements(By.tagName("a"));
		   //System.out.println("The number of links:"+links.size()); 
						
			String x = null;

			for(int i=0;i<links.size();i++)
			{
				x=links.get(i).getText();
				
				if(x.contains("New Customer")) 
				{
			    	links.get(i).click();			    	 
			    	break;  			    	
			    }				
			} 
			
			Thread.sleep(1000);
			
			WebDriverWait wait1 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
		       //until you see the image 
			wait1.until(ExpectedConditions.presenceOfElementLocated(By.className("barone")));						
			
			//System.out.println (driver.getTitle());
			Assert.assertTrue(driver.getTitle().contains("Guru99 Bank New Customer Entry Page"),"Guru99 Bank New Customer Entry Page");			    	
	    	
			WebDriverWait wait2 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
		       //until you see the image 
			wait2.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));										
			driver.findElement(By.name("name")).sendKeys(custName);
			
			WebElement inputBox = driver.findElement(By.name("name"));
			String textInsideInputBox = inputBox.getAttribute("value");
			System.out.println(textInsideInputBox);
			
			if (textInsideInputBox.isEmpty())
			{
				System.out.println("Customer name must not be blank");
				   System.exit(1);
			}
			
			WebDriverWait wait3 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
		       //until you see the image 
			wait3.until(ExpectedConditions.presenceOfElementLocated(By.name("rad1")));						
						
			//WebElement radbut=driver.findElement(By.name("rad1"));
			List<WebElement> radbut=driver.findElements(By.name("rad1"));
			
			   	String val=null;
				//System.out.println(radbut.size());

			   	for(int j=0;j<radbut.size();j++)
			   {		   
					val=radbut.get(j).getAttribute("value");
					//System.out.println(val);					
					if(gender.startsWith(val))
					{	
					radbut.get(j).click();	
					}
			   }

			   	WebDriverWait wait4 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait4.until(ExpectedConditions.presenceOfElementLocated(By.id("dob")));										
				driver.findElement(By.id("dob")).sendKeys(bday);

				WebDriverWait wait5 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait5.until(ExpectedConditions.presenceOfElementLocated(By.name("addr")));										
				driver.findElement(By.name("addr")).sendKeys(address);
	
				WebDriverWait wait6 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait6.until(ExpectedConditions.presenceOfElementLocated(By.name("city")));										
				driver.findElement(By.name("city")).sendKeys(city);
	
				WebDriverWait wait7 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait7.until(ExpectedConditions.presenceOfElementLocated(By.name("state")));										
				driver.findElement(By.name("state")).sendKeys(state);
	
				WebDriverWait wait8 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait8.until(ExpectedConditions.presenceOfElementLocated(By.name("pinno")));										
				driver.findElement(By.name("pinno")).sendKeys(pinCode);
	
				WebDriverWait wait9 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait9.until(ExpectedConditions.presenceOfElementLocated(By.name("telephoneno")));										
				driver.findElement(By.name("telephoneno")).sendKeys(mobile);
	
				WebDriverWait wait10 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait10.until(ExpectedConditions.presenceOfElementLocated(By.name("emailid")));										
				driver.findElement(By.name("emailid")).sendKeys(emailId);
	
				WebDriverWait wait11 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait11.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));										
				driver.findElement(By.name("password")).sendKeys(pwd);
	
				WebDriverWait wait12 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait12.until(ExpectedConditions.presenceOfElementLocated(By.name("sub")));										
				driver.findElement(By.name("sub")).click();
				
				Thread.sleep(2000);
				
				WebDriverWait wait13 = new WebDriverWait(driver, 0 /*timeout in seconds*/);
			       //until you see the image 
				wait13.until(ExpectedConditions.presenceOfElementLocated(By.className("barone")));						
				
				//System.out.println (driver.getTitle());
				Assert.assertTrue(driver.getTitle().contains("Guru99 Bank Customer Registration Page"),"Guru99 Bank Customer Registration Page");			    	
		    	
				ArrayList<String> ar = new ArrayList<String>();
				
				WebElement element=driver.findElement(By.id("customer"));
				List<WebElement> rowCollection=element.findElements(By.tagName("tr")); //how to get this?

				int i_RowNum=1;
				for(WebElement rowElement:rowCollection)
				{
				      List<WebElement> colCollection=rowElement.findElements(By.tagName("td"));
				      int i_ColNum=1;
				      for(WebElement colElement:colCollection)
				      {
				          // System.out.println("Row "+i_RowNum+" Column "+i_ColNum+" Data "+colElement.getText());				          
				           ar.add(colElement.getText());					          
				           i_ColNum=i_ColNum+1;
				          // System.out.println("\n");
				       }
				    i_RowNum=i_RowNum+1;
				 }
			
				
				for (int y=0; y< ar.size();y++)
				{
					//System.out.println(ar.get(y));				
					if(ar.get(y).contains("Customer ID"))
					{
						custIdCaptured = ar.get(y+1);
						//System.out.println("hello");
						//System.out.println(custIdCaptured);
						break;
					}
				}				
			
	 }
  
	 
	 @Test (priority =2)
	   
	 public void AddAccount() {
		 System.out.println(custIdCaptured);		 
	 }
	 
  
  @DataProvider(name="AddCustomerdata")
	public Object[][] CustDataFeed(){

	try {
	// load workbook
	wb=Workbook.getWorkbook(new File("/Users/anupamtyagi/Downloads/addCust1.xls"));

	// load sheet in my case I am referring to first sheet only
	sh1= wb.getSheet(1);
	
	// get number of rows so that we can run loop based on this
	numrow1= sh1.getRows();	
	//System.out.println(+numrow1);
	
	// get number of columns so that we can run loop based on this
	numcol1= sh1.getColumns();			
	//System.out.println(+numcol1);
	}
	
	catch (Exception e)
	{
	e.printStackTrace();
	}

	// Create 2 D array and pass row and columns

	Object [][] custdata=new Object[numrow1][sh1.getColumns()];

	// This will run a loop and each iteration  it will fetch new row
	for(int i=0;i<numrow1;i++){

		// Fetch first row CustName
	custdata[i][0]=sh1.getCell(0,i).getContents();
	
	// Fetch first row gender
	custdata[i][1]=sh1.getCell(1,i).getContents();
	
	// Fetch first row bday
	custdata[i][2]=sh1.getCell(2,i).getContents();
	
	// Fetch first row address
	custdata[i][3]=sh1.getCell(3,i).getContents();
	
	// Fetch first row city
	custdata[i][4]=sh1.getCell(4,i).getContents();
	
	// Fetch first row state
	custdata[i][5]=sh1.getCell(5,i).getContents();
	
	// Fetch first row pin
	custdata[i][6]=sh1.getCell(6,i).getContents();
	
	// Fetch first row mobile
	custdata[i][7]=sh1.getCell(7,i).getContents();
	
	// Fetch first row email
	custdata[i][8]=sh1.getCell(8,i).getContents();
	
	// Fetch first row pwd
	custdata[i][9]=sh1.getCell(9,i).getContents();
		
	}

	// Return 2d array object so that test script can use the same
	return custdata;

	}
  
  

  @AfterMethod
  public void afterMethod() {
  }

}
