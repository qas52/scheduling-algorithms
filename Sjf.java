package sjf;
import java.util.LinkedList;
import java.util.Random;

import finalsimulation.Event;
import finalsimulation.Pcb;
public class Sjf {
	
	public static double interArrivalTime(int lamda) {       //function for determining arrival time of next person
	       
		Random rand = new Random();
         double Z= -Math.log(1-rand.nextDouble())/(lamda);
        
       
         return (double) (Math.round(Z * Math.pow(10, 3)) / Math.pow(10, 3))*10000; // return the time(seconds) that next person will arrive
    }
	public static void main(String[] args) {
		LinkedList <Event> readyq = new LinkedList<Event>();
		LinkedList <Event> inputq = new LinkedList<Event>();
		 double time=0, waitt=0, totalt=0,thput=0,temp=0;
		LinkedList <Event> evlist = new LinkedList<Event>();
		int j=0,i=0;
		LinkedList <Pcb> pq= new LinkedList<Pcb>();
		
		while(j<10){
			Pcb p1= new Pcb(120000+12000*((int) (Math.random()*(10-1)) + 1) ,'N');
			while(i<pq.size()) {     //for sorting with shortest executing time as first rest all process is same as for fcfs
				
				if(pq.get(i).endt<p1.endt)
					i++;
				else
					break;
				}
				pq.add(i,p1);
				i=0;
				
				j++;
		}
		j=0;
		while(j<10){
		
		
		 i=1;
		time=0;
		
		while(time<= pq.get(j).endt) {
			
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
		while(readyq.isEmpty()!=true||inputq.isEmpty()!=true) {
			
			
			if(pq.get(j).state=='R')
			{  Event e1=readyq.poll();
			   time=time+e1.time;
			   e1.time=evlist.peekLast().time+e1.time;
			   evlist.add(e1);
			   pq.get(j).state='I';
			   
			}
			else if(pq.get(j).state=='I')
			{
			   Event e1=inputq.poll();
			   e1.time=evlist.peekLast().time+e1.time;
			   evlist.add(e1);
			   pq.get(j).state='R';
			}
			else
			{
				Event e1=readyq.poll();
				   
				   
				   pq.get(j).state='I';
				   time=e1.time;
				   evlist.add(e1);
			}
			if(evlist.peekLast().time>pq.get(j).endt)
			{	
			System.out.println("Process "+evlist.peekLast().pid+" "+time+" "+pq.get(j).endt);
			System.out.println("CPU util "+(time/pq.get(j).endt)*100);
			thput=pq.get(j).endt+thput;
			pq.get(j).state='C';
			System.out.println("state of process "+evlist.peekLast().pid +" = "+(char)pq.get(j).state);
			totalt=pq.get(j).endt+totalt;
			waitt=(totalt-time)+waitt;
			temp=totalt+temp;
			
			j++;
			
			}
			
		}
		
		
		System.out.println("avg throughput "+thput/10);
		System.out.println("avg waiting time " +waitt/10);
		System.out.println("avg turnaround time"+temp/10);
		
		
		
     
    
	}

}
