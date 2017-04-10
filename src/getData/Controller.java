package getData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

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
}
