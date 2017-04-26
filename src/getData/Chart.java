package getData;

import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  

import org.json.JSONArray;
import org.json.JSONObject;
 

  
public class Chart {
	
	public static void main(String args[]) throws Exception{
		//System.out.println(SendCharts("YHOO.csv"));

	}
	
	public static int period=30;
	
	
	public static final String ENCODE = "UTF-8";  
	  
    private static FileInputStream fis = null;  
    private static InputStreamReader isw = null;  
    private static BufferedReader br = null;  
  
     
    public static void CSVFileUtil(String filename) throws Exception {  
        fis = new FileInputStream(filename);  
        isw = new InputStreamReader(fis, ENCODE);  
        br = new BufferedReader(isw);  
    }  
  
    
    public static String SendCharts(String filename) throws Exception{
		//double[] test = {30.56,20.45,55.678,54.56,34.44};
		//double[] test1 = {29.56,19.45,50.678,54.56,33.44};
		//double[] test2 = {30.59,21.45,59.678,54.58,34.44};

		int N = 10;

		

		CSVFileUtil(filename);
		String [] sp=readLine(5,N+period).split(",");
		CSVFileUtil(filename);	
		String [] sh=readLine(3,N+period).split(",");
		CSVFileUtil(filename);	
		String [] sl=readLine(4,N+period).split(",");
		CSVFileUtil(filename);	
		String [] sv=readLine(6,N+period).split(",");
		//String [] s6=readLine(7,N+period).split(",");
		double price[]=new double[sp.length];
		double high[]=new double[sh.length];
		double low[]=new double[sl.length];
        int volume[]=new int[sv.length];
		for(int num=0;num<sp.length;num++){
			price[num]=Double.parseDouble(sp[num]);
			volume[num]=Integer.parseInt(sv[num]);
			//System.out.println("volume:"+volume[num]);
			high[num]=Double.parseDouble(sh[num]);
			low[num]=Double.parseDouble(sl[num]);
		}
		
		
		
		double c0[]=RSIChart(N,price);
		double c1[]=ADLChart(N,high,low,price,volume);
		double c2[]=SOChart(N,price);
		double c3[]=MAChart(N,price);
		double c4[]=ROCChart(N,price);
		double c5[]=OBVChart(N,price,volume);
		double c6[]=ARChart(N,high,low);
		
//		System.out.println("PMO="+RSI(N+period,price));
//		System.out.println("SO="+SO(N+period,price));
//		System.out.println("MA="+MA(N+period,price));
//		System.out.println("PMO="+PMO(N+period,price));
//		System.out.println("MFI="+MFI(N+period,high,low,price,volume));
//		System.out.println("DM="+DM(N+period,high,low,price));
//		System.out.println("WPR="+WPR(N+period,high,low,price));
//		System.out.println("CCI="+CCI(N+period,high,low,price));
//		System.out.println("AR="+AR(N+period,high,low));
//		System.out.println("ROC="+ROC(N+period,price));
//		System.out.println("ADL="+ADL(N+period,high,low,price));
//		System.out.println("PC="+PC(N+period,price));
//		System.out.println("UO="+UO(N+period,high,low,price));
		
		for(int num=0;num<c5.length;num++){
			c5[num]=c5[num]/1000000;
		}
        JSONObject json = new JSONObject();
        
        //鍚慾son涓坊鍔犳暟鎹�
        
        json.put("RSI", c0);            
        json.put("ADL", c1);
        json.put("SO", c2);
        json.put("MA", c3);
        json.put("ROC", c4);
        json.put("OBV", c5);
        json.put("AR", c6);
        
        JSONArray array1 = new JSONArray();
        array1.put(json);
         
        String jsonStr = array1.toString();
            
            
        //System.out.println(jsonStr);
        
        return jsonStr;
		
    	
    }
    
    public static String readLine(int row, int readsize) throws Exception {  
  
        StringBuffer readLine = new StringBuffer();  
        boolean bReadNext = true;  
        String strReadLine;
        int y=0;
        while (bReadNext) {  
            //  
            if (readLine.length() > 0) {  
                readLine.insert(0,",");  
            }  
            
 
             
            
            
            strReadLine = br.readLine();
              	// readLine is Null 
            if (strReadLine == null) {  
            	return null;  
            }  
            String stockItem[] =strReadLine.split(",");
                
            readLine.insert(0,stockItem[row]);  
            
            y++;
            // 濡傛灉鍙屽紩鍙锋槸濂囨暟鐨勬椂鍊欑户缁鍙栥�傝�冭檻鏈夋崲琛岀殑鏄儏鍐点��  
            if (y<readsize) {  
                bReadNext = true;  
            } else {  
                bReadNext = false;  
            }  
        }  
        return readLine.toString();  
    }  
	
	
	//indicators:
	
	
	
	
	//1.RSI   -1 for error, 2 for increase, 1 for decrease, 0 for hold
	
	
	
 

	public static double[] RSIChart(int N, double[] price){
		
		if((N+period) != price.length)
			return null;
				
		int i,j;
		double tc , yc;
		
		double upclose = 0, downclose = 0;
		double rsi[] = new double[period];
		
		for(j=0;j<period;j++){
			tc = price[1+j];
			yc = price[0+j];	
			for(i=1;i<N-1;i++){
				if(tc>yc){
					upclose = upclose + tc;				
				}
				else if(tc<yc){
					downclose = downclose +tc;
				}
				tc = price[i+1+j];
				yc = price[i+j];	
			}
		rsi[j] = 100 - 100/(1 + upclose/downclose);
		}
		return rsi;
	
	}
	
	
	public static double[] SOChart(int N, double[] price){
		if((N+period) != price.length)
			return null;
		
		int j;
		double k[]=new double[period];
		double[] b = new double[N];
		
		for(j=0;j<period;j++){
			double tc = price[N+j];
			System.arraycopy(price, 0+j, b, 0, N);
			double hn = getMax(b);
			double ln = getMin(b);		
			k[j] = (tc - ln)/(hn - ln)*100;	
		}
		
		return k;
		
	}
	
	
	public static double[] MAChart(int N, double[] price){
		if((N+period) != price.length)
			return null;
		
		double tma[] = new double[period];
		double[] b = new double[N];
		
		for(int j = 0;j<period;j++){			
			System.arraycopy(price, 0+j, b, 0, N);
			tma[j] = getAve(b);
		}
	
		return tma;
	}
	
	//9.Aroon indicator(AR)  N=14prefer:
	public static double[] ARChart(int N, double[] high, double[] low){
		if((N+period) != high.length)
			return null;
			
		int j;
		double[] b = new double[N];
		double[] aroon = new double[2*period];
		
		for(j=0;j<period;j++){
			System.arraycopy(high, 0+j, b, 0, N);
			aroon[j] = (getMaxIn(b)+1)/N*100;
			System.arraycopy(low, 0+j, b, 0, N);
			aroon[j+period] = (getMinIn(b)+1)/N*100;
		}
		
		return aroon;

	}
	
	public static double[] ROCChart(int N, double[] price){
		if((N+period) != price.length)
			return null;

		double roc[]= new double[period];
		int j;
		for(j=0;j<period;j++){
			roc[j]= (price[N-1+j]-price[j])/price[j]*100;
			//System.out.println("kkk666="+roc);
		}
		return roc;
	}
	
	

	public static double[] ADLChart(int N, double[] high, double[] low, double[] price,int[] volume){
		if((N+period) != price.length)
			return null;
		double adl[] = new double[period];
		int j;
		double tr =0;
		
		for (j=0;j<period;j++){
			double clvt=((price[j+N-1]-low[j+N-1])-(high[j+N-1]-price[j+N-1]))/(high[j+N-1]-low[j+N-1]);
			adl[j]=tr+ clvt*volume[N-1];
			tr = adl[j];
		}

		return adl;
	}
	
	

	public static double[] OBVChart(int N, double[] price,int[] volume){
		if((N+period) != price.length)
			return null;
		double obv[] = new double[period];
		int j;
		double tr =0;
		
		for (j=0;j<period;j++){
			
			if(price[j+N-1]>price[j+N-2])
				obv[j]=tr + volume[j+N-1];
			else if(price[j+N-1]<price[j+N-2])
				obv[j]=tr - volume[j+N-1];
			else
				obv[j]=tr;
			
			tr = obv[j];
		}

		return obv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int RSI(int N, double[] price){
		if(N != price.length)
			return -1;
		
		int i;
		double tc = price[1];
		double yc = price[0];		
		double upclose = 0, downclose = 0;
		
		for(i=1;i<N-1;i++){
			if(tc>yc){
				upclose = upclose + tc;				
			}
			else if(tc<yc){
				downclose = downclose +tc;
			}
			tc = price[i+1];
			yc = price[i];
		}
		
		double rsi = 100 - 100/(1 + upclose/downclose);
		if(rsi>50)
			return 2;
		else if(rsi<50)
			return 1;
		else
			return 0;
	
	}
	
	//2.Stochastic Oscillator:
	public static int SO(int N, double[] price){
		if(N != price.length)
			return -1;
		
		double tc = price[N-1];
    	double[] b = new double[price.length -1 ];
    	System.arraycopy(price, 0, b, 0, price.length-1);
		double hn = getMax(b);
		double ln = getMin(b);
		
		double k = (tc - ln)/(hn - ln)*100;		
		
		if(k>80)
			return 2;
		else if(k<20)
			return 1;
		else
			return 0;				
	}
	
	
	//3.Moving average:
	public static int MA(int N, double[] price){
		if(N != price.length)
			return -1;
		
		double tma = getAve(price); 
		double[] b = new double[price.length -1 ];
    	System.arraycopy(price, 0, b, 0, price.length-1);
		double yma = getAve(b);
		
		if(tma>yma)
			return 2;
		else if(tma<yma)
			return 1;
		else
			return 0;
	}
	
	//4.Price momentum oscillator(PMO):
	public static int PMO(int N, double[] price){
		if(N != price.length)
			return -1;
		double tc = price[N-1],ndac = price[0];
		double pmo = tc - ndac;
		
		if(pmo>0)
			return 2;
		else if(pmo<0)
			return 1;
		else
			return 0;
	}
	
	//5.Money flow index(MFI):
	public static int MFI(int N, double[] high, double[] low, double[] price, int[] volume){
		if(N != price.length)
			return -1;
		
		int i;
		//typical price * vlume = MF
		double tp[] = new double[N];
		double mf[] = new double[N];
		for(i=0;i<N;i++){
			tp[i] = (high[i] + low[i] + price[i])/3;
			mf[i] = tp[i] * volume[i];
		}
		
		//positive mf and negative mf:
		double pmf=0, nmf=0;
		for(i=0;i<N-1;i++){
			if(tp[i+1]>tp[i])
				pmf = pmf + mf[i+1];
			else if(tp[i+1]<tp[i])
				nmf = nmf + mf[i+1];			
		}
		
		double mfi = 100*(pmf/(pmf+nmf));
		
		if(mfi>80)
			return 2;
		else if(mfi<20)
			return 1;
		else
			return 0;
	}
	
	
	//6.Demarker indicator(DM):
	public static int DM(int N, double[] high, double[] low, double[] price){
		if(N != price.length)
			return -1;
		
		double demax[] = new double[N];
		double demin[] = new double[N];
		// demax and demin of N days
		for(int i=0;i<N-1;i++){
			if(high[i+1]>high[i])
				demax[i] = high[i+1] - high[i];
			else
				demax[i] = 0;
			
			if(low[i+1]<low[i])
				demin[i] = low[i] - low[i+1];
			else
				demin[i] = 0;			
		}
		
		//demarker
		double demarker = getAve(demax)/(getAve(demax)+getAve(demin));
		if(demarker>70)
			return 1;
		else if(demarker<30)
			return 2;
		else
			return 0;					
	}
	
	
	//7.William's percentage range(WPR):
	public static int WPR(int N, double[] high, double[] low, double[] price){
		if(N != price.length)
			return -1;
		
		double r = (getMax(high)-price[N-1])/(getMax(high)-getMax(low))*100;
		
		if(r<20)
			return 2;
		else if(r>80)
			return 1;
		else
			return 0;
	}
	
	
	
	//8.commodity channel index(CCI):
	public static int CCI(int N, double[] high, double[] low, double[] price){
		if(N != price.length)
			return -1;
		
		int i;
		//typical price
		double tp[] = new double[N];
		for(i=0;i<N;i++){
			tp[i] = (high[i] + low[i] + price[i])/3;
		}
		double sma = getAve(tp);

		double[] d = new double[N];
		for(i=0;i<N;i++)
			d[i]=Math.abs(tp[i]-sma);
 
		
		double cci = (tp[N-1]-sma)/getAve(d)/0.015;
		//System.out.println("cci="+cci);
		
		if(cci<-100)
			return 2;
		else if(cci>100)
			return 1;
		else
			return 0;
	}
	
	
	//9.Aroon indicator(AR)  N=14prefer:
	public static int AR(int N, double[] high, double[] low){
		if(N != high.length)
			return -1;
		
		double aup = (getMaxIn(high)+1)/N*100;
		double adown = (getMinIn(low)+1)/N*100;
		//System.out.println("kkk666="+aup+" "+adown);
		if(aup>70 && adown<30)
			return 2;
		else if(aup<30 && adown>70)
			return 1;
		else
			return 0;
	}
	
	//10.Rate of Change(ROC):
	public static int ROC(int N, double[] price){
		if(N != price.length)
			return -1;
		
		double roc = (price[N-1]-price[0])/price[0]*100;
		//System.out.println("kkk666="+roc);
		if(roc>0)
			return 2;
		else
			return 1;
	}
	
	
	//11.Moving average Convergence/Divergence(MACD):
	
	
	//12.Accumulation/distribution line(ADL):  N=60
	public static int ADL(int N, double[] high, double[] low, double[] price){
		if(N != high.length)
			return -1;
		
		double clvt=((price[N-1]-low[N-1])-(high[N-1]-price[N-1]))/(high[N-1]-low[N-1]);
		double clvp=((price[0]-low[0])-(high[0]-price[0]))/(high[0]-low[0]);
		//System.out.println("kkk666="+clvt+" "+clvp);
		
		if(clvt>clvp && price[N-1]<price[0])
			return 2;
		else if(clvt<clvp && price[N-1]>price[0])
			return 1;
		else
			return 0;
	}
	
	
	//13.Price Channel(PC): N=20 prefer
	public static int PC(int N, double[] price){
		if(N != price.length)
			return -1;
		
		double[] b = new double[price.length -1 ];
    	System.arraycopy(price, 0, b, 0, price.length-1);
		double pc = (getMax(b)+getMin(b))/2;
		if(price[N-1]>pc && price[N-1]>price[N-2])
			return 2;
		else if(price[N-1]<pc && price[N-1]<price[N-2])
			return 1;
		else
			return 0;
		
	}
	
	
	//14.Ultimate Oscillator(UO):N>4!
	public static int UO(int N, double[] high, double[] low, double[] price){
		if(N != high.length || N<4)
			return -1;

		
		double[] tl = new double[N];
		double[] bp = new double[N];
		double[] tr = new double[N];
		for(int i=N-1;i>0;i--){
			tl[N-i-1] = low[i]>price[i-1] ? price[i-1] : low[i];
			bp[N-i-1] = price[i]-tl[N-i-1];
			tr[N-i-1] = (high[i]-low[i])>(high[i]-price[i-1]) ? high[i]-low[i] : high[i]-price[i-1];
			tr[N-i-1] = tr[N-i-1]>(price[i-1]-low[i])?tr[N-i-1]:price[i-1]-low[i];			
		}
		
        double BPsum1 = 0, BPsum2 = 0, BPsum3 = 0;
        double TRsum1 = 0, TRsum2 = 0, TRsum3 = 0;
        for (int i = 0; i < bp.length; i++) {       
            BPsum3 += bp[i];
            TRsum3 += tr[i];
        }
		
        for (int i = 0; i < bp.length/2; i++) {       
            BPsum2 += bp[i];
            TRsum2 += tr[i];
        }
        
        for (int i = 0; i < bp.length/4; i++) {       
            BPsum1 += bp[i];
            TRsum1 += tr[i];
        }
	
        double uo = (4*(BPsum1/TRsum1) + 2*(BPsum2/TRsum2) + BPsum3/TRsum3)/7*100;
        //System.out.println("uo="+uo);
        if(uo>70)
        	return 2;
        else if(uo<30)
        	return 1;
        else
        	return 0;
	}
	
	

	
	
	//get max's index of a double array
    public static double getMaxIn(double[] arr){
        double max=arr[0];
        int itr=0;
        for(int i=1;i<arr.length;i++){
        	if(arr[i]>max){
        		max=arr[i];
        		itr=i;
            }
        }
         return itr;
    }
    
    //get min's index of a double array
    public static double getMinIn(double[] arr){
        double min=arr[0];
        int itr=0;
        for(int i=1;i<arr.length;i++){
        	if(arr[i]<min){
        		min=arr[i];
        		itr=i;
            }
        }
         return itr;
    }
	
	//get max of a double array
    public static double getMax(double[] arr){
        double max=arr[0];
        for(int i=1;i<arr.length;i++){
        	if(arr[i]>max){
        		max=arr[i];
            }
        }
         return max;
    }
    
    //get min of a double array
    public static double getMin(double[] arr){
        double min=arr[0];
        for(int i=1;i<arr.length;i++){
        	if(arr[i]<min){
        		min=arr[i];
            }
        }
         return min;
    }
    
    //get ave of a double array
    public static double getAve(double[] arr){
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {       
            sum += arr[i];
        }
        return sum/arr.length; 
     	
    }
	
	

	
	
	
}

