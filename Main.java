package finalsimulation;

import java.util.LinkedList;
import java.util.Random;




public class Main {

	public static double interArrivalTime(int lamda) {       //function for determining arrival time of next process cpu request
	       
		Random rand = new Random();
         double Z= -Math.log(1-rand.nextDouble())/(lamda);
        
       
         return (double) (Math.round(Z * Math.pow(10, 3)) / Math.pow(10, 3))*10000; // return the time(ms) 
    }
	
	
	
	
	
	
	public static void main(String[] args) {
		LinkedList <Event> readyq = new LinkedList<Event>();  //cpu queue
		LinkedList <Event> inputq = new LinkedList<Event>();  //i/o queue
		 double time=0, thput=0, waitt=0, totalt=0, temp=0; //for stats
		LinkedList <Event> evlist = new LinkedList<Event>(); //event list
		int j=0;
		
		Pcb p1[]= new Pcb[10];  //PCB for declaring and initializing run times
		while(j<10){
			p1[j]=new Pcb(120000+12000*((int) (Math.random()*(10-1)) + 1) ,'N');
			j++;
		}
		j=0;
		while(j<10){       // instead of switching process from one queue to another , i first split the process into multiple cpu and i/o bursts which each sum upto their respective process run time 
		
		
		int i=1;
		time=0;
		
		while(time<= p1[j].endt) {
			
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
		j++;
		}
		
        j=0;
        time=0;
		while(readyq.isEmpty()!=true||inputq.isEmpty()!=true) {   //i just pop them from the queue depending on which state(ready or input) the process is going through
			
			
			if(p1[j].state=='R')
			{  Event e1=readyq.poll();
			   time=time+e1.time;
			   e1.time=evlist.peekLast().time+e1.time;
			   evlist.add(e1);
			   p1[j].state='I';
			   
			}
			else if(p1[j].state=='I')
			{
			   Event e1=inputq.poll();
			   e1.time=evlist.peekLast().time+e1.time;
			   evlist.add(e1);
			   p1[j].state='R';
			}
			else
			{
				Event e1=readyq.poll();
				   
				   
				   p1[j].state='I';
				   time=e1.time;
				   evlist.add(e1);
			}
			if(evlist.peekLast().time>p1[j].endt)  //for determining when one process has finished executing so i can proceed with next one
			{	
			System.out.println("Process "+evlist.peekLast().pid+" "+time+" "+p1[j].endt);
			System.out.println("CPU util "+(time/p1[j].endt)*100);
			thput=p1[j].endt+thput;
			p1[j].state='C';
			System.out.println("state of process "+evlist.peekLast().pid +" = "+(char)p1[j].state);
			totalt=p1[j].endt+totalt;
			waitt=(totalt-time)+waitt;
			temp=totalt+temp;
			
			j++;
			
			}
			
		}
		
		
		//System.out.println(p1[9].endt);
		System.out.println("avg throughput "+thput/10);
		System.out.println("avg waiting time " +waitt/10);
		System.out.println("avg turnaround time"+temp/10);
		
		
		
     
    
	}
}

