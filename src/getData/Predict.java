package getData;
import java.io.*;

public class Predict{
	private String name;
//	private double[] price;
	
	public Predict(String str){
		this.name = str;
	}
	public String predictPrice(String method) throws IOException, InterruptedException{
		Process proc = null;
		if(method == "bayes"){
			proc = Runtime.getRuntime().exec("python bayes.py"+" "+this.name);  
		}
		else if(method == "ANN"){
			proc = Runtime.getRuntime().exec("python ANN.py"+" "+this.name);  
		}
		proc.waitFor(); 
		InputStream input = proc.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		String line = in.readLine();
		while(line!=null){
			String tmp = in.readLine();
			if(tmp != null){
				line = tmp;
			}
			else{
				break;
			}
		}
		return line;
    }
}
