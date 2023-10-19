import java.util.Arrays;
import java.util.Scanner;


public class sandwich {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		
		//Pre compute which numbers from 1 - 10 million are sandwich numbers
		boolean[] isSand = new boolean[10000000];
		for(int i=10;i<isSand.length;i++)isSand[i] = isSandwich(Integer.toString(i));
		
		//Pre compute powers of 10, since we'll use them a lot.
		long[] pow10 = new long[18];
		for(int i=0;i<18;i++)pow10[i]=(long)Math.pow(10,i);
		
		for(int t = 1; t <= T; t++) {
			long K = scan.nextLong();
			
			String string = Long.toString(K);
			int L = string.length();
			
			if(K < 10){System.out.println("Number #"+t+": There are 0 sandwich numbers that meet our criteria.");continue;}
			
			
			//Here I compute prefixes and suffixes of the input string
			//which will come in handy later.
			//Ex. prefixes of 123456 are: {1,12,123,1234,12345,123456}
			//Ex. suffixes of 123456 are: {6,56,456,3456,23456,123456}
			long[] prefix = new long[L], suffix = new long[L];
			for(int i=1;i<L;i++)prefix[i] = Long.parseLong(string.substring(0, i));
			for(int i=1;i<L;i++)suffix[i] = Long.parseLong(string.substring(L-i));
			
			long sum = 0;
			for(int i=1;i< Math.pow(10, L/2);i++){
				// if 'i' is a sandwich number itself, then it is guarenteed that we have
				// already counted it before, so if we don't skip it, we will overcount
				if(isSand[i])continue;
				
				if(i > prefix[(int)Math.round(L/2.0)])break;
				
				// 'i' = the figurative bread to our sandwich number. (ex: 123???123 or 43?43)
				
				int l = (int)Math.log10((double)i)+1;
				
				//Iterate through all possible number of lengths of sandwich numbers which are <= our input number
				// ex : 11, 1?1, 1??1, 1???1 ...
				// you can see, if our input number is sufficiently large, we could plug any combination of digits
				// in for the question marks. The number of ways to do this is 10^(number of question marks). 
				//The only thing we have to check for is when the composite string is the same length of the input string
				for(int j=0; j <= L-2*l; j++){
					
					//checking if the front end of our number is too large
					if (j==L-2*l && i > prefix[l]){
						continue;
					}
					//checking if the end of our number is too large
					else if (j==L-2*l && i== prefix[l] && i > suffix[l]){
						continue;
					}
					// if the number starts the same as the input string, then we can only have a specific number of 
					// sandwich number which are smaller than it.
					else if (j!=0&&j==L-2*l && i == prefix[l]){
						sum+=Long.parseLong(string.substring(l,l+j))+1;
					}
					else{
						sum+=pow10[j];
						
					}
				}
			}
			
			System.out.println("Number #"+t+": There are "+sum+" sandwich numbers that meet our criteria.");
		}
	}

	//checks if the number begins and ends with the same exact number
	private static boolean isSandwich(String s) {
		for(int i=1;i<=s.length()/2 ; i++)
			if(s.endsWith(s.substring(0, i)))return true;
		return false;
	}
}
