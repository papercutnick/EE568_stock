package getData;

import java.io.IOException;
import java.rmi.RemoteException;

import Database.DBAccess;
import getData.Chart;

public class WebService_Imp implements WebService_Int{
	public String getLatestPrice(String code) throws java.rmi.RemoteException{
		return DBAccess.getLatestPrice(code);
	}
	
	public String getHighestPriceInTenDays(String code) throws java.rmi.RemoteException{
		return DBAccess.getHighestPriceInTenDays(code);
	}

	public String getAvgPriceInOneYear(String code) throws java.rmi.RemoteException{
		String latestDate = DBAccess.getLatestDate(code);
		String earliestDate = DBAccess.getEarliestDate(code);
		if(latestDate.length()!=10 || latestDate.length()!=10){
			return "";
		}
		int date = Integer.parseInt(latestDate.substring(0, 4));
		String yearago = String.valueOf(date-1)+latestDate.substring(4);
		String earlirindb = earliestDate.compareTo(yearago)>0?earliestDate:yearago;
		return DBAccess.getAvgPriceBetween(code, earlirindb, latestDate);
	}

	public String getLowestPriceInOneYear(String code) throws java.rmi.RemoteException{
		String latestDate = DBAccess.getLatestDate(code);
		String earliestDate = DBAccess.getEarliestDate(code);
		int date = Integer.parseInt(latestDate.substring(0, 4));
		String yearago = String.valueOf(date-1)+latestDate.substring(4);
		String earlirindb = earliestDate.compareTo(yearago)>0?earliestDate:yearago;
		return DBAccess.getLowestPriceBetween(code, earlirindb, latestDate);
	}
	
	public String getNamesHaveLowerAvgThan(String code) throws java.rmi.RemoteException{
		WebService_Imp temp = new WebService_Imp();
		String[] codes = code.split(",");
		float lowest = 999;
		for (String c:codes){
			lowest = Math.min(lowest, Float.parseFloat(temp.getLowestPriceInOneYear(c)));
		}
		String[] stocks = {"YHOO","BIDU","GOOG","MSFT","ORCL","COST","FB","AMZN","TSLA","CSCO"};
		String cand = "";
		System.out.println(code+"'s lowest: "+String.valueOf(lowest));
		for (String stock:stocks){
			float avg = Float.parseFloat(temp.getAvgPriceInOneYear(stock));
			//System.out.println(stock+"'s avg: "+String.valueOf(avg));
			if(avg<lowest){
				cand=cand+stock+",";
			}
		}
		return cand;
	}
	
	public String retrieveHistory(String code) throws java.rmi.RemoteException{
		// ? about parameters format and return type
		return Utils.retrieveHistory(code, code, code, code, code, code, code);
		
	}

	public String predictNext_ANN(String code) throws java.rmi.RemoteException {
		// Using ANN to predict that it would go up or go down.
		String temp = "";
		try {
			DBAccess.saveRecentTenDaysToCSV(code);
			Predict p = new Predict(code+"_temp.csv");
			temp = p.predictPrice("ANN");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	public String predictNext_Bayes(String code) throws RemoteException {
		// TODO Auto-generated method stub
		String temp = "";
		try {
			DBAccess.saveRecentTenDaysToCSV(code);
			Predict p = new Predict(code+"_temp.csv");
			temp = p.predictPrice("bayes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	public String predictNext_Indicator(String code) throws RemoteException {
		// TODO Auto-generated method stub
		String temp = "";
		try {
			DBAccess.saveRecentFiftyDaysToCSV(code);
			temp = Chart.SendCharts(code+"_temp.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

}
