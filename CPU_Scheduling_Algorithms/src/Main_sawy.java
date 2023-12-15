import java.awt.*;
import java.util.*;


//class SRTF{
//    ArrayList<Process>processes = new ArrayList<>();
//    PriorityQueue<Process>readyQueue  = new PriorityQueue<>(Comparator.comparingInt(Process::getStarvationBurst).thenComparingInt(Process::getArrivalTime));
//    ArrayList<Process>processOrder = new ArrayList<>();
//    ArrayList<Process>die = new ArrayList<>();
//    int averageWaiting ;
//    int averageTruning ;
//    Process current;
//    int time ;
//    SRTF(ArrayList<Process>proc) {
//        this.processes = proc ;
//        time = 0 ;
//        current=null;
//        averageWaiting = 0;
//        averageTruning=0;
//    }
//    void getFirstProcess()
//    {
//        int x = 100000;
//        int index = -1 ;
//        for(int i = 0 ; i <processes.size();i++)
//        {
//            if(processes.get(i).getArrivalTime()<x)
//            {
//                index = i;
//                x=processes.get(i).getArrivalTime();
//            }
//            else if(processes.get(i).getArrivalTime()==x)
//            {
//                if(index!=-1&&processes.get(i).getBurstTime()<processes.get(index).getBurstTime())
//                {
//                    index = i ;
//                }
//            }
//        }
//        time =x;
//        Process p = processes.get(index);
//        processes.remove(index);
//        readyQueue.add(p);
//    }
//    void getArivalProcess()
//    {
//        int number = processes.size();
//        ArrayList<Integer>l = new ArrayList<>();
//        for(int i = 0 ; i <number;i++)
//        {
//            if(processes.get(i).getArrivalTime()==time)
//            {
//                Process p = processes.get(i);
//                l.add(i);
//                readyQueue.add(p);
//            }
//        }
//        for(int i =l.size()-1;i>=0;i--)
//        {
//            int z = l.get(i);
//            processes.remove(z);
//        }
//        Process first = readyQueue.peek();
//        if(current!=null&&!current.getName().equals(first.getName()))
//        {
//            current.setEnd(time);
//            processOrder.add(current);
//            current.intervals.add(new interval(current.getStart(),current.getEnd()));
//            current=first;
//            current.setStart(time);
//        }
//
//    }
//    void starvation()
//    {
//        PriorityQueue<Process>temp  = new PriorityQueue<>(Comparator.comparingInt(Process::getStarvationBurst));
//        for(Process p : readyQueue)
//        {
//            p.setStarvationBurst(p.getStarvationBurst()-(time - p.getArrivalTime()));
//            temp.add(p);
//        }
//        readyQueue.clear();
//        readyQueue.addAll(temp);
//        Process first = readyQueue.peek();
//        if(current!=null &&!current.getName().equals(first.getName()))
//        {
//            current.setEnd(time);
//            processOrder.add(current);
//            current.intervals.add(new interval(current.getStart(),current.getEnd()));
//            current=first;
//            current.setStart(time);
//        }
//    }
//    void build()
//    {
//        while(true)
//        {
//            if(processes.isEmpty()&&readyQueue.isEmpty())
//            {
//                break;
//            }
//            if(readyQueue.isEmpty())
//            {
//                getFirstProcess();
//            }
//            if( current==null ) {
//                current = readyQueue.peek();
//                current.setStart(time);
//            }
//            if((time+1)%5==0)
//            {
//                starvation();
//            }
//            time++;
//            assert current != null;
//            current.setRemainingTime(current.getRemainingTime()-1);
//            if(current.getRemainingTime()==0)
//            {
//                current.setWaitingTime(time - current.getArrivalTime()-current.getBurstTime());
//                int truned = time - current.getArrivalTime();
//                current .setTurnedTime(truned);
//                current.setEnd(time);
//                processOrder.add(current);
//                current.intervals.add(new interval(current.getStart(),current.getEnd()));
//                die.add(current);
//                readyQueue.poll();
//                current = null;
//            }
//            if(processes.size()!=0)
//            {
//                getArivalProcess();
//            }
//        }
//    }
//    void show()
//    {
//        System.out.print("Process Execution Order : ");
//        for(int i = 0 ; i <processOrder.size();i++)
//        {
//            System.out.print(processOrder.get(i).getName()+ "  ");
//        }
//        System.out.println();
//        for(int i = 0 ; i <die.size();i++)
//        {
//            System.out.println("Name : "+die.get(i).getName()+"  "+"Waiting Time :"+" "+die.get(i).getWaitingTime()
//                    +"           Trun Around Time     "+die.get(i).getTurnedTime());
//            averageWaiting+=die.get(i).getWaitingTime();
//            averageTruning+=die.get(i).getTurnedTime();
//        }
//        int x = die.size();
//        averageTruning/=x;
//        averageWaiting/=x;
//        System.out.println("Average Trun Around Time :  "+averageTruning);
//        System.out.println("Average  Waiting Time  :  "+averageWaiting);
//
//    }
//
//
//}
//class SJF{
//    int context ;
//    int avergeWait = 0 ;
//    int avergeAround = 0 ;
//    int time ;
//    ArrayList<Process> processes = new ArrayList<>();
//    PriorityQueue<Process>readyQueue  = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime).thenComparingInt(Process::getArrivalTime));
//    ArrayList<Process>die = new ArrayList<>();
//    SJF(ArrayList<Process>processes , int context)
//    {
//        this.processes=processes;
//        this.context = context;
//        time = 0;
//    }
//    void getFirstProcess()
//    {
//        int x = 100000;
//        int index = -1 ;
//        for(int i = 0 ; i <processes.size();i++)
//        {
//            if(processes.get(i).getArrivalTime()<x)
//            {
//                index = i;
//                x=processes.get(i).getArrivalTime();
//            }
//            else if(processes.get(i).getArrivalTime()==x)
//            {
//                if(index!=-1&&processes.get(i).getBurstTime()<processes.get(index).getBurstTime())
//                {
//                    index = i ;
//                }
//            }
//        }
//        time =x;
//        Process p = processes.get(index);
//        processes.remove(index);
//        readyQueue.add(p);
//    }
//    void getArivalProcess()
//    {
//        int number = processes.size();
//        ArrayList<Integer>l = new ArrayList<>();
//        for(int i = 0 ; i <number;i++)
//        {
//            if(processes.get(i).getArrivalTime()<=time)
//            {
//                Process p = processes.get(i);
//                l.add(i);
//                readyQueue.add(p);
//            }
//        }
//        for(int i =l.size()-1;i>=0;i--)
//        {
//            int z = l.get(i);
//            processes.remove(z);
//        }
//
//    }
//    void build()
//    {
//        die = new ArrayList<>();
//        boolean f = false;
//        while(true)
//        {
//            if(processes.isEmpty()&&readyQueue.isEmpty())
//            {
//                break;
//            }
//            if(readyQueue.isEmpty())
//            {
//                getFirstProcess();
//            }
//            Process p = readyQueue.poll();
//            if(f)
//            {
//                time+=context;
//            }
//            p.setStart(time);
//            p.setWaitingTime(time-p.getArrivalTime());
//            time+=p.getBurstTime();
//            int truned = time - p.getArrivalTime();
//            p.setTurnedTime(truned);
//            p.setEnd(time);
//            p.intervals.add(new interval(p.getStart(),p.getEnd()));
//            die.add(p);
//            getArivalProcess();
//            f=true;
//
//        }
//    }
//    void show()
//    {
//        System.out.print("Process Execution Order :");
//        for(int i = 0 ; i< die.size();i++)
//        {
//            System.out.print(die.get(i).getName()+ "  ");
//        }
//        System.out.println();
//        for (int i = 0 ; i < die.size();i++)
//        {
//            avergeWait+=die.get(i).getWaitingTime();
//            avergeAround+=die.get(i).getTurnedTime();
//            System.out.println("Name : "+die.get(i).getName()+"   Waiting Time  : "+die.get(i).getWaitingTime()+"   Trun Around Time : "+die.get(i).getTurnedTime());
//
//        }
//        int x = avergeWait/(int)die.size();
//        int y = avergeAround/(int)die.size();
//        System.out.println("Average Waiting Time : " +x);
//        System.out.println("Average Trun Around Time : "+y);
//
//    }
//}
//public class Main_sawy {
//    public static ArrayList<Process> srtf(){
//        ArrayList<Process> proc = new ArrayList<>();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the number of Process");
//        int number = input.nextInt();
//        for (int i = 0; i < number; i++) {
//            System.out.println("Enter the name ");
//             String name = input.next();
//             System.out.println("Enter the color ");
//             String color = input.next();
//            System.out.println("Enter the arrivalTime");
//            int arivalTime = input.nextInt();
//            System.out.println("Enter the burstTime");
//            int burstTime = input.nextInt();
//            Process p = new Process(name,priorityScheduling.getColorByName(color),arivalTime,burstTime);
//            proc.add(p);
//        }
////        proc.add(new Process("p0",Color.BLACK,0,7));
////        proc.add(new Process("p1",Color.BLACK,2,4));
////        proc.add(new Process("p2",Color.BLACK,4,1));
////        proc.add(new Process("p3",Color.BLACK,5,4));
//
//        SRTF s = new SRTF(proc);
//        s.build();
//        s.show();
//        return s.die ;
//    }
//    public static ArrayList<Process> sjf(){
//        ArrayList<Process> proc = new ArrayList<>();
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the number of Process");
//        int number = input.nextInt();
//        System.out.println("Enter the context");
//        int context = input.nextInt();
//        for(int i = 0;i<number ; i ++) {
//            System.out.println("Enter the name ");
//            String name = input.next();
//            System.out.println("Enter the color ");
//            String color = input.next();
//            System.out.println("Enter the arrivalTime");
//            int arivalTime = input.nextInt();
//            System.out.println("Enter the burstTime");
//            int burstTime = input.nextInt();
//            Process p = new Process(name,priorityScheduling.getColorByName(color),arivalTime,burstTime);
//            proc.add(p);
//        }
////        proc.add(new Process("p0",Color.BLACK,0,7));
////        proc.add(new Process("p1",Color.BLACK,2,4));
////        proc.add(new Process("p2",Color.BLACK,4,1));
////        proc.add(new Process("p3",Color.BLACK,5,4));
//
//        SJF s = new SJF(proc,1);
//        s.build();
//        s.show();
//        return s.die ;
//    }
//
//}