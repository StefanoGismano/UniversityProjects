public class MioNome{
	public static boolean scan(String s){
		int i=0;
		int state=0;
		
		while(state>=0 && i<7){
			final char ch=s.charAt(i++);
			
			switch(state){
				case 0:
				if(ch=='S')
				    state=1;
				else
				    state=7;
				break;

				case 1:
				if(ch=='t')
				    state=2;
				else
				    state=8;
				break;
				
				case 2:
				if(ch=='e')
				    state=3;
				else
				    state=9;
				break;
				
				case 3:
				if(ch=='f')
				    state=4;
				else
				    state=10;
				break;
				
				case 4:
				if(ch=='a')
				    state=5;
				else
				    state=11;
				break;
				
				case 5:
				if(ch=='n')
				    state=6;
				else
				    state=12;
				break;
				
				case 6:
				    state=13;
				break;
				
				case 7:
				if(ch=='t')
				    state=8;
				else
				    state=-1;
				break;
				
				case 8:
				if(ch=='e')
				    state=9;
				else
				    state=-1;
				break;
				
				case 9:
				if(ch=='f')
				    state=10;
				else
				    state=-1;
				break;
				
				case 10:
				if(ch=='a')
				    state=11;
				else
				    state=-1;
				break;
				
				case 11:
				if(ch=='n')
				    state=12;
				else
				    state=-1;
				break;
				
				case 12:
				if(ch=='o')
				    state=13;
				else
				    state=-1;
				break;
			}
		}
		return state==13;
	}
	
	public static void main(String args[]){
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}