package getData;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.DBAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetHistoryData extends Thread{
	// Integration of retrieving data and storing to database. Only for using at the first stage normally.
	// history: code, date, open, high, low, close, volume.
	private String[] stockCodes;
	
	public GetHistoryData(String stocks){
		this.stockCodes = stocks.split(",");
	}
	
	public void run(){
		boolean existed = true;
		for(String stk: this.stockCodes){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, -1);
			Date date1=c.getTime();
			SimpleDateFormat syear = new SimpleDateFormat("yyyy");
			SimpleDateFormat smonth = new SimpleDateFormat("MM");
			SimpleDateFormat sday = new SimpleDateFormat("dd");
	        //GregorianCalendar date = new GregorianCalendar();
	        String year = syear.format(date1.getTime());
	        String month = smonth.format(date1.getTime());
	        String day = sday.format(date1.getTime());
	        HashMap<String, String> map= new HashMap<String, String>();map.put("01","00");map.put("02","01");map.put("03","02");map.put("04","03");map.put("05","04");map.put("06","05");map.put("07","06");map.put("08","07");map.put("09","08");map.put("10","09");map.put("11","10");map.put("12","11");
			
	        existed = DBAccess.checkExsistence(stk, year+"-"+month+"-"+day);
	        
			if(!existed){
				try{
					URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+stk+"&a="+map.get(month)+"&b="+day+"&c="+year+"&d="+map.get(month)+"&e="+day+"&f="+year+"&g=d&ignore=.csv");
					URLConnection connection = url.openConnection(); 
					InputStreamReader is = new InputStreamReader(connection.getInputStream());
					BufferedReader br = new BufferedReader(is);
					
					// Parse CSV Into Array
					br.readLine(); 
					String line = br.readLine(); //read twice readline() because the first line is the titles.
					//Only split on commas that aren't in quotes
					if(line != null){
						String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
						String code = stk;
						String date = stockinfo[0];
						double open = Double.parseDouble(stockinfo[1]);
						double high = Double.parseDouble(stockinfo[2]);
						double low = Double.parseDouble(stockinfo[3]);
						double close = Double.parseDouble(stockinfo[4]);
						int volume = Integer.parseInt(stockinfo[5]);
						
						System.out.println("History Data Date:"+date+" stock code:"+code+" open:"+open+" high:"+high+" low:"+low+" close:"+close+" volume:"+volume);
						
						int result=DBAccess.insertHistoryData(code, date, open, high, low, close, volume);
						if(result!=0)
							System.out.println("Insert history data complete.");
						else
							System.out.println("Insert  history data failed.");
					}
				} catch(Exception e){
					e.printStackTrace();
					return;
				}
			}
		}
		
	}
}
