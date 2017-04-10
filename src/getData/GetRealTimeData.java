package getData;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import yahoo.Stock;
import yahoo.StockFetcher;
import Database.DBAccess;

public class GetRealTimeData extends Thread{
	private String stockCodes;
	
	public GetRealTimeData(String stockCodes){
		this.stockCodes = stockCodes;
	}
	
	public void run(){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			GregorianCalendar date = new GregorianCalendar();
			
			Stock[] data=StockFetcher.getStocks(stockCodes);
			
			for(int i=0;i<data.length;i++){
				Stock st = data[i];
				String code = st.getSymbol();
				String time = sdf.format(date.getTimeInMillis()-15*60000);
				double price = st.getPrice();
				int volume = st.getVolume();
				String d = st.getDate();
				String t = st.getTime();
				String fmtDate = sdf2.format(date.getTime());
				//DBAccess.insertRealtimeData(code, time, price, volume);
				
				//----------------------------------
				System.out.println("Date:"+d+" "+fmtDate+" System time:"+time+" Returned time:"+t+" stock code:"+code+" price:"+price+" volume:"+volume);
				int hour=0;
				if(t.endsWith("pm")){
					hour=Integer.valueOf(t.substring(0, t.indexOf(":")))+12;
				}else{
					hour=Integer.valueOf(t.substring(0, t.indexOf(":")));
				}
				if(hour<16){
					int result=DBAccess.insertRealtimeData(code, time, price, volume, fmtDate);
					if(result!=0)
						System.out.println("Insert real time data complete.");
					else
						System.out.println("Insert real time data failed.");
				}
				//-------------------------------------
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
