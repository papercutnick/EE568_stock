package getData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Database.DBAccess;
import getData.Utils;
import getData.Predict;

public class Controller {
	
	public void parseCSV() throws IOException{
		String[] stocks = {"YHOO","BIDU","GOOG","MSFT","ORCL","COST","FB","AMZN","TSLA","CSCO"};
		for (String code:stocks){
			//URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s="+ code + "&f=l1vr2ejkghm3j3nc4s7pod1t1");
			//URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+code+"&a=00&b=01&c=2000&d=00&e=01&f=2018&g=d&ignore=.csv");
			//URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+stk+"&a="+map.get(month)+"&b="+day+"&c="+year+"&d="+map.get(month)+"&e="+day+"&f="+year+"&g=d&ignore=.csv");
			
			//URLConnection connection = url.openConnection(); 
			//InputStreamReader is = new InputStreamReader(connection.getInputStream());
			//BufferedReader br = new BufferedReader(is);
			//BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			//BufferedReader br = new BufferedReader(in);


	        String line = "";
	        String cvsSplitBy = ",";

	        try  (BufferedReader br = new BufferedReader(new FileReader(code+".csv"))) {
	        	String ttt = br.readLine(); //The first line is header. Ignore it.
	        	
	        	int count=0;
	            while ((line = br.readLine()) != null) {
	                // use comma as separator
	                String[] stockinfo = line.split(cvsSplitBy);
	                String date = stockinfo[0];
					double open = Double.parseDouble(stockinfo[1]);
					double high = Double.parseDouble(stockinfo[2]);
					double low = Double.parseDouble(stockinfo[3]);
					double close = Double.parseDouble(stockinfo[4]);
					int volume = Integer.parseInt(stockinfo[5]);
					int result=DBAccess.insertHistoryData(code, date, open, high, low, close, volume);
					if(result==0) System.out.println("Insertion failed.");
					count++;
	                if(count%100==0) System.out.println(count);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println(code+" done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}

	}
	
	
	//Used for the first phase presentation (Data Collector Module).
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//String t = DBAccess.getAvgPriceBetween("BIDU","2016-03-02","2017-03-02");
		//String t = WebService_Int.getAvgPriceInOneYear("BIDU");
		//WebService_Int a = new WebService_Int();
		//String t = a.getNamesHaveLowerAvgThan("BIDU");
		//boolean s = DBAccess.checkExsistence("BIDU","2017-03-02");
		//System.out.println(t);
		//Controller c = new Controller();
		//GetHistoryData g = new GetHistoryData("YHOO,BIDU,GOOG,MSFT,ORCL,COST,FB,AMZN,TSLA,CSCO");
		//g.run();
		//c.parseCSV();
		//GetHistoryData rl = new GetHistoryData("YHOO,BIDU,GOOG,MSFT,ORCL");
		//ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		//service.scheduleAtFixedRate(rl, 0, 15, TimeUnit.SECONDS);
		
		DBAccess.saveRecentTenDaysToCSV("BIDU");
		
		/*URL url = new URL(temp);
		URLConnection connection = url.openConnection(); 
		InputStreamReader is = new InputStreamReader(connection.getInputStream());
		BufferedReader br = new BufferedReader(is);
		System.out.println(br.readLine()); */
		
		/*
		GetRealTimeData rl = new GetRealTimeData("YHOO,BIDU,GOOG,MSFT,ORCL");
		GetHistoryData  hd = new GetHistoryData("YHOO,BIDU,GOOG,MSFT,ORCL");
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		//start getting real time data thread
		service.scheduleAtFixedRate(rl, 0, 15, TimeUnit.SECONDS);
		//start getting history data thread
		service.scheduleAtFixedRate(hd, 0, 1, TimeUnit.HOURS);*/
	}
}
