import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.openqa.selenium.WebElement;

public class RedboxScrape {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Ryon\\Downloads\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		//driver.manage().window().maximize();
		String baseurl = "http://www.redbox.com/locations/state";
		driver.get(baseurl);
		
		List<WebElement> states = driver.findElements(By.xpath("//div[@class='store-column']/a"));
		File outputFile = new File("C:\\Users\\Ryon\\Desktop\\RedboxScrape.txt");
		BufferedWriter output = null;
		try{
			output = new BufferedWriter(new FileWriter(outputFile, true));
			for(int statecount = 0; statecount < states.size(); statecount++){
				List<WebElement> statesRefreshed = driver.findElements(By.xpath("//div[@class='store-column']/a"));
				driver.get(statesRefreshed.get(statecount).getAttribute("href"));
				List<WebElement> cities = driver.findElements(By.xpath("//div[@class='store-column']/a"));
				for(int citycount = 0; citycount <  cities.size(); citycount++){
					List<WebElement> citiesRefreshed = driver.findElements(By.xpath("//div[@class='store-column']/a"));
					driver.get(citiesRefreshed.get(citycount).getAttribute("href"));
					List<WebElement> zips = driver.findElements(By.xpath("//div[@class='store-column']/a"));
					for(int zipcount = 0; zipcount < zips.size(); zipcount++){
						List<WebElement> zipsRefreshed = driver.findElements(By.xpath("//div[@class='store-column']/a"));
						driver.get(zipsRefreshed.get(zipcount).getAttribute("href"));
						List<WebElement> companyName = driver.findElements(By.xpath("//div[@class='store-name']"));
						List<WebElement> location = driver.findElements(By.xpath("//div[@class='store-address']/p"));
						for(int sitecount = 0; sitecount < companyName.size(); sitecount++){
							output.write(companyName.get(sitecount).getText() + " " + location.get(sitecount).getText().replaceAll("\n", "|"));
							output.newLine();
							output.flush();
							System.out.print(companyName.get(sitecount).getText() + " ");
							System.out.println(location.get(sitecount).getText().replaceAll("\n", "|"));
						}
						 
						driver.navigate().back();
					}//End zip loop
					driver.navigate().back();
				}//End city loop
				driver.navigate().back();
			}//End state loop
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}//End main
}//End class
