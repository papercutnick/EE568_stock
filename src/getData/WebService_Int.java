package getData;

import java.rmi.RemoteException;

public interface WebService_Int {
	public String getLatestPrice(String code) throws RemoteException;
	//Query 1.

	public String getHighestPriceInTenDays(String code) throws RemoteException;
	//Query 2.
	
	public String getAvgPriceInOneYear(String code) throws RemoteException;
	//Query 3.
	
	public String getLowestPriceInOneYear(String code) throws RemoteException;
	//Query 4.
	
	public String getNamesHaveLowerAvgThan(String code) throws RemoteException;
	//Query 5. Output Ex.: AMZN,YHOO,BIDU,
	
	public String retrieveHistory(String code) throws RemoteException;
	//not done yet.

	public String predictNext_ANN(String code) throws RemoteException;

	public String predictNext_Bayes(String code) throws RemoteException;
	
	public String predictNext_Indicator(String code) throws RemoteException; 

	
}
