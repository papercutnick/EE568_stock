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
			return "Error fetching realtime quote.";
		}
	}

	public static String retrieveHistory(String code){
		// ? about parameters format and return type
		return "";
		
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
