package rr;

import java.util.LinkedList;
import java.util.Random;
import finalsimulation.Event;
import finalsimulation.Pcb;




public class Rr {

	public static double interArrivalTime(int lamda) {       //function for determining arrival time of next person
	       
		Random rand = new Random();
         double Z= -Math.log(1-rand.nextDouble())/(lamda);
        
       
         return (double) (Math.round(Z * Math.pow(10, 3)) / Math.pow(10, 3))*10000; // return the time(seconds) that next person will arrive
    }
	
	
	
	
	
	
	public static void main(String[] args) {
		LinkedList <Event> readyq = new LinkedList<Event>();
		LinkedList <Event> inputq = new LinkedList<Event>();
		 double time=0, waitt=0, totalt=0, thput=0;
		LinkedList <Event> evlist = new LinkedList<Event>();
		int j=0, flag=0;
		
		Pcb p1[]= new Pcb[10];
		double exect[]= new double[10];
		while(j<10){
			p1[j]=new Pcb(120000+12000*j  ,'N');   //initializing process
			exect[j]=p1[j].endt;
			j++;
		}
		j=0;
		while(flag!=10){
		if(exect[j]>75000)
		{ int i=1;
		  time=0;
		  while(time<= 75000) {   //splitting into multiple i/0 and cpu bursts for one quantum
				
				if(i%2==0)
				{ Event e1=new Event(2,60,j);
				  inputq.add(e1);
				  i++;
				  time=time+60;
				  
				}
				else
				{
					Event e1=new Event(1,interArrivalTime(30+(5*j)),j);
					readyq.add(e1);
					i++;
					time=time+e1.time;
				}
		  }
				exect[j]=exect[j]-75000;
		
		j=(j+1)%10;
		
		}
		else if(exect[j]==75000)
		{
			int i=1;
			  time=0;
			
			  while(time<= 75000) {
					
					if(i%2==0)
					{ Event e1=new Event(2,60,j);
					  inputq.add(e1);
					  i++;
					  time=time+60;
					  
					}
					else
					{
						Event e1=new Event(1,interArrivalTime(30+(5*j)),j);
						readyq.add(e1);
						i++;
						time=time+e1.time;
					}
			  }
			exect[j]=-1;
			flag++;
			j=(j+1)%10;	
		}
		else if(exect[j]<75000&&exect[j]>0)
			{ int i=1;
			  time=0;
			while(time<= exect[j]) {
				
				if(i%2==0)
				{ Event e1=new Event(2,60,j);
				  inputq.add(e1);
				  i++;
				  time=time+60;
				  
				}
				else
				{
					Event e1=new Event(1,interArrivalTime(30+(5*j)),j);
					readyq.add(e1);
					i++;
					time=time+e1.time;
				}
			}
			exect[j]=-1;
			flag++;
			j=(j+1)%10;	}
		else
			j=(j+1)%10;
		}	
		
		
		
        j=0;
       
		
        
        
        double Ctime[]=new double[10];
        double timer[]=new double[10];
        int i=0;
        while(readyq.isEmpty()!=true||inputq.isEmpty()!=true) {    //entering into event queue
			if(readyq.isEmpty()!=true)
        	{ Event e1= readyq.poll();
			  p1[e1.pid].state='R';
			  Ctime[e1.pid]=e1.time+Ctime[e1.pid];
			  timer[e1.pid]=e1.time+timer[e1.pid];
			  e1.time=timer[e1.pid];
			  
			  evlist.add(e1);}
			if(inputq.isEmpty()!=true) 
			{  Event e2= inputq.poll();
			  p1[e2.pid].state='I';
			  timer[e2.pid]=e2.time+timer[e2.pid];
			  e2.time=timer[e2.pid];
			  evlist.add(e2);
			}
        
			
		}
		
		
		
	   while(i<10)            //stats for project collection
	   {   System.out.println("process "+i+" "+ Ctime[i]+" "+ timer[i] );
		   System.out.println("CPU util "+(Ctime[i]/p1[i].endt)*100+" ");
	       thput=p1[i].endt+thput;
		   i++;
	   }
		
	   System.out.println("avg throughput " +thput/10);
	   i=0;
	   while(i<10)
	   {  double temp=0;
		   for( j=0;j<=i;j++)
		   {  
			  totalt=totalt+timer[j]; 
		      temp=timer[j]+temp;
		   }
			waitt=(temp-Ctime[i])+waitt;
		   i++;
	   }
		
       System.out.println("avg turnaround "+totalt/10);
       System.out.println("avg waiting "+waitt/10);
    
	}
}