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

class Multithreading extends Thread {
	
	public String homePage;
	public int maxThreadCount=4;
	
	public Multithreading(String hP) {
		homePage=hP;
	}
	
	WebDriver driver;
	
    public void run() {
        try {
            System.out.println ("Child " +Thread.currentThread().getId() +" is born");
            System.out.println();
        	System.out.println("Homepage is: "+homePage);
        	System.out.println();
        	System.out.println();
        	
        	int localNWCount=0, localWCount=0, localextCount=0;
        	ArrayList<String> homePageLinks=new ArrayList<String>();
        	
            String url = "";
            HttpURLConnection huc;
            int respCode = 200;
            
            System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Testing_Eon\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.get(homePage);
            
            List<WebElement> links = driver.findElements(By.tagName("a"));
            Iterator<WebElement> linksIterator = links.iterator();
            
            while(linksIterator.hasNext()){
            	
                url = linksIterator.next().getAttribute("href");
                System.out.println(url);
            
                if(url == null || url.isEmpty()){
                	localNWCount++;
                	System.out.println("URL is either not configured for anchor tag or it is empty");
                    continue;
                }
                
                if(!url.startsWith(homePage)){
                	localextCount++;
                    System.out.println("URL belongs to another domain, skipping it.");
                    continue;
                }
                
                try {
                    huc = (HttpURLConnection)(new URL(url).openConnection());
                    huc.setRequestMethod("HEAD");
                    
                    huc.connect();
                    
                    respCode = huc.getResponseCode();
     
                    if(respCode >= 400){
                    	localNWCount++;
                        System.out.println(url+" is a broken link");
                    }
                    else{
                    	localWCount++;
                    	if(!homePage.startsWith(url)) {
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
            
            LinkTest.NWCount+=localNWCount;
            LinkTest.WCount+=localWCount;
            LinkTest.extCount+=localextCount;
            
            System.out.println("Not Working : " + LinkTest.NWCount);
            System.out.println("Working : " + LinkTest.WCount);
            System.out.println("External : " + LinkTest.extCount);
            driver.quit();
            
            Multithreading object[]=new Multithreading[homePageLinks.size()];
                        
            for (int i=0; i<homePageLinks.size(); i++)
            {
            	if(i>=maxThreadCount) {
            		object[i-maxThreadCount].join();
            	}
                object[i] = new Multithreading(homePageLinks.get(i));
                object[i].start();
            }
            
            for(int i=0; i<homePageLinks.size(); i++) {
            	object[i].join(); // TODO Exception handling
            }
              
            System.out.println ("Child " +Thread.currentThread().getId() +" has died");
        }
        catch (Exception e) {
            System.out.println ("Child is killed due to Abnormality");

        }
    }
    
}
