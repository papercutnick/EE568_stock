package getData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import getData.Utils;

public class Controller {
	
	public static String retrieveRealtime(String code){
		return Utils.retrieveRealtime(code);
	}

	public static String retrieveHistory(String code){
		// ? about parameters format and return type
		return Utils.retrieveHistory(code);
		
	}

	public static String predictNext(String code){
		// predict stock price of tomorrow
		return Utils.predictNext(code);
		
	}
	
	public static int predictShortterm(String code){
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return Utils.predictShortterm(code);
	}

	public static int predictLongterm(String code){
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return Utils.predictLongterm(code);
	}
	
	public static void main(String[] args) {
		System.out.println(retrieveRealtime("BIDU"));
	}
	
	
	/*
	 * Used for the first phase presentation (Data Collector Module).
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetRealTimeData rl = new GetRealTimeData("YHOO,BIDU,GOOG,MSFT,ORCL");
		GetHistoryData  hd = new GetHistoryData("YHOO,BIDU,GOOG,MSFT,ORCL");
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		//start getting real time data thread
		service.scheduleAtFixedRate(rl, 0, 15, TimeUnit.SECONDS);
		//start getting history data thread
		service.scheduleAtFixedRate(hd, 0, 1, TimeUnit.HOURS);
	}
	*/
}
