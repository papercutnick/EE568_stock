package getData;

import java.rmi.RemoteException;

public interface WebService_Imp {
	public String getLatestPrice(String code) throws RemoteException;

	public String getHighestPriceInNDays(String code, int t) throws RemoteException;
	
	public String retrieveHistory(String code) throws RemoteException;

	public String predictNext(String code) throws RemoteException;
	
	public int predictShortterm(String code) throws RemoteException;

	public int predictLongterm(String code) throws RemoteException;

	
}
