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


public class Utils {
	public static String retrieveRealtime(String code){
		try{
			URL url = new URL("http://download.finance.yahoo.com/d/quotes.csv?s="+code.toUpperCase()+"&f=l1&e=.csv");
			URLConnection connection = url.openConnection(); 
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);
			
			String line = br.readLine(); // Should be only one number in type String
			line.replaceAll(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", ""); // Delete possible symbols.
			return line;
		} catch(IOException e){
			e.printStackTrace();
			return "[Error] WebService error fetching realtime quote.";
		}
	}

	public static String retrieveHistory(String code, String from_year, String from_month, String from_day, String to_year, String to_month, String to_day){
		// Input and output type all String. Can be changed.
		return "";
		/*
		try{
			HashMap<String, String> map= new HashMap<String, String>();map.put("01","00");map.put("02","01");map.put("03","02");map.put("04","03");map.put("05","04");map.put("06","05");map.put("07","06");map.put("08","07");map.put("09","08");map.put("10","09");map.put("11","10");map.put("12","11");
			URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+code+"&a="+map.get(from_month)+"&b="+from_day+"&c="+from_year+"&d="+map.get(to_month)+"&e="+to_day+"&f="+to_year+"&g=d&ignore=.csv");
			URLConnection connection = url.openConnection(); 
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);
			
			// Parse CSV Into Array
			br.readLine(); //read twice readline() because the first line is the titles.
			String line;
			while((line=br.readLine()) != null){
				String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
				String date = stockinfo[0];
				String open = stockinfo[1];
				String high = stockinfo[2];
				String low = stockinfo[3];
				String close = stockinfo[4];
				String volume = stockinfo[5];
			}
			String line = br.readLine(); 
			//Only split on commas that aren't in quotes

			
			//System.out.println("History Data Date:"+date+" stock code:"+code+" open:"+open+" high:"+high+" low:"+low+" close:"+close+" volume:"+volume);
			
			int result=DBAccess.insertHistoryData(code, date, open, high, low, close, volume);
			if(result!=0)
				System.out.println("Insert history data complete.");
			else
				System.out.println("Insert  history data failed.");
			
		} catch(IOException e){
			e.printStackTrace();
			return;
		}
		*/
	}

	public static String predictNext(String code){
		// predict stock price of tomorrow
		return "";
		
	}
	
	public static int predictShortterm(String code){
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return 0;
	}

	public static int predictLongterm(String code){
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return 0;
	}
}
