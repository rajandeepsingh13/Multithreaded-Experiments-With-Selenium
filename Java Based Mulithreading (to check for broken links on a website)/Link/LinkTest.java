//LIMIT THREAD COUNT

package Link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class LinkTest {
    
    private WebDriver driver;
    ArrayList<String> homePageLinks=new ArrayList<String>();
    
    public static int NWCount=0, WCount=0, extCount=0;
    
    @Test(priority = 0)
    public void og() throws InterruptedException {
    	Multithreading genesisObject=new Multithreading("ENTER HOMEPAGE HERE!!!!!!!!!!!!!");
    	System.out.println("Genesis has started");
    	System.out.println();
    	genesisObject.start();
    	genesisObject.join();
    	System.out.println("Genesis is finished");
    	
    	System.out.println("Links Not Working: " + NWCount);
    	System.out.println("Links Working: " + WCount);
    	System.out.println("External Lnks: " + extCount);
    }
    
    
    //SINGLE-THREADED SOLUTION
    
    /*
    
    public void lt(String homePage) throws InterruptedException {
        
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println(homePage);
    	System.out.println();
    	
        String url = "";
        HttpURLConnection huc;
        int respCode = 200;
        
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Testing_Eon\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(homePage);
        
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();
        
        while(it.hasNext()){
            url = it.next().getAttribute("href");
            System.out.println(url);
        
            if(url == null || url.isEmpty()){
            	NWCount++;
            	System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            
            if(!url.startsWith(homePage)){
            	extCount++;
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }
            
            try {
                huc = (HttpURLConnection)(new URL(url).openConnection());
                huc.setRequestMethod("HEAD");
                
                huc.connect();
                
                respCode = huc.getResponseCode();
 
                if(respCode >= 400){
                	NWCount++;
                    System.out.println(url+" is a broken link");
                }
                else{
                	WCount++;
                	if(!homePage.startsWith(url)) {
          //      		lt(url);
                		homePageLinks.add(url);
                	}
                    System.out.println(url+" is a valid link");
                }
                    
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        System.out.println("Not Working : " + NWCount);
        System.out.println("Working : " + WCount);
        System.out.println("External : " + extCount);
        driver.quit();
        
        Multithreading object[]=new Multithreading[homePageLinks.size()];
        
        for (int i=0; i<homePageLinks.size(); i++)
        {
            object[i] = new Multithreading(homePageLinks.get(i));
            object[i].start();
        }
        
        for(int i=0; i<homePageLinks.size(); i++) {
        	object[i].join(); // TODO Exception handling
        }
        

    }
    */
}