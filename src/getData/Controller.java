package getData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Database.DBAccess;
import getData.Utils;

public class Controller {
	
	
	//Used for the first phase presentation (Data Collector Module).
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String t = DBAccess.getLatestPrice("BIDU");
		boolean s = DBAccess.checkExsistence("BIDU","2017-03-02");
		System.out.println(s?"a":"b");
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
