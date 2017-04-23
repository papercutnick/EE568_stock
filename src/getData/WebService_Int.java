package getData;

public class WebService_Int implements WebService_Imp{
	public String retrieveRealtime(String code) throws java.rmi.RemoteException{
		return Utils.retrieveRealtime(code);
	}

	public String retrieveHistory(String code) throws java.rmi.RemoteException{
		// ? about parameters format and return type
		return Utils.retrieveHistory(code);
		
	}

	public String predictNext(String code) throws java.rmi.RemoteException{
		// predict stock price of tomorrow
		return Utils.predictNext(code);
		
	}
	
	public int predictShortterm(String code) throws java.rmi.RemoteException{
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return Utils.predictShortterm(code);
	}

	public int predictLongterm(String code) throws java.rmi.RemoteException{
		// 1 for "buy", 0 for "hold", and -1 for "sell"
		return Utils.predictLongterm(code);
	}

}
