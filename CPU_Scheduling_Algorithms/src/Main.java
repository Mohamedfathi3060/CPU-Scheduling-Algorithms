import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingDeque;

class Process {
    public String Name;
    public Color color;
    public int Priority;
    public int arrivalTime;
    public int burstTime;
    public int originalBurstTime;
    public int waitingTime;
    public int turnAroundTime;
    public int AGFactor;
    public int Quantum;
    public int lastQuantum;
    public int Rand;
    public int start ;
    public int end ;
    public int remainingTime ;
    public int starvationBurst ;

    public ArrayList<Integer> quantumHistory;
    public ArrayList<interval> intervals;

    // SAWY
    public Process(String name, Color c, int arrivalTime, int burstTime) {
        this.Name = name;
        this.color = c;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.starvationBurst = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0 ;
        this.remainingTime = burstTime;
        this.start = 0 ;
        this.end = 0 ;
        this.intervals = new ArrayList<>();
    }
    public Process(String n ,int arrivalTime,int burstTime,int Priority,Color c){
        this.color= c;
        this.intervals = new ArrayList<>();
        this.Name = n ;
        this.arrivalTime = arrivalTime;
        this.Priority =Priority;
        this.burstTime=burstTime;
    }
    public Process(String name, Color color, int priority, int arrivalTime, int burstTime, int quantum) {
        Name = name;
        this.color = color;
        Priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        Quantum = quantum;
        this.quantumHistory = new ArrayList<>();
        quantumHistory.add(quantum);
        Random R = new Random();
        this.Rand = R.nextInt(21);
        this.lastQuantum = 0;
        calcAGFactor();
        this.intervals = new ArrayList<>();
    }
    public Process(String name, Color color, int priority, int arrivalTime, int burstTime, int quantum, int r) {
        Name = name;
        this.color = color;
        Priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        Quantum = quantum;
        this.quantumHistory = new ArrayList<>();
        quantumHistory.add(quantum);
        if (r != 0)
            this.Rand = r;
        else {
            Random R = new Random();
            this.Rand = R.nextInt(21);
        }
        this.lastQuantum = 0;
        calcAGFactor();
        this.intervals = new ArrayList<>();
    }
    public void calcAGFactor(){
        Random random = new Random();
        int Random = random.nextInt(21);
        if (Rand < 10)
        {
            AGFactor = Rand + arrivalTime + burstTime;
        }
        else if (Rand > 10)
        {
            AGFactor = 10 + arrivalTime + burstTime;
        }
        else
        {
            AGFactor = Priority + arrivalTime + burstTime;
        }
    }
    public int quantumMean(){
        int sum = quantumHistory.get(quantumHistory.size()-1);
        int count = 1;
        for (int i = 0; i < quantumHistory.size()-1;i++){
            sum += i;
            count++;
        }
        return (sum/count);
    }

    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getName() {
        return Name;
    }

    public Color getColor() {
        return color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getStarvationBurst() {
        return starvationBurst;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getEnd() {
        return end;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setStarvationBurst(int starvationBurst) {
        this.starvationBurst = starvationBurst;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnedTime(int turnedTime) {
        this.turnAroundTime = turnedTime;
    }

    public int getTurnedTime() {
        return turnAroundTime;
    }


    public int getRemainingTime() {
        return remainingTime;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
class interval {
    public int start;
    public int end;
    interval(int s, int e){
        this.start = s;
        this.end = e;
    }
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

///////////////////////// priority
class PriorityComparsion implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        if(o1.Priority == o2.Priority){
            return o1.arrivalTime - o2.arrivalTime;
        }
        else{
            return o1.Priority - o2.Priority;
        }
    }
}

class priorityScheduling {
    public static int TIME  = 0;
    public ArrayList<Process> processes ;
    public ArrayList<Process> die ;


    public PriorityQueue<Process> ready;
    priorityScheduling(){
        ready = new PriorityQueue<>(new PriorityComparsion());
        processes = new ArrayList<>();
        die = new ArrayList<>();
    }

    public void checkAvailable (){
        // for all Processadd to queue arrived process
//        for (Processx : processes){
//            if(x.arrivalTime <= TIME){
//                ready.add(x);
//                processes.remove(x);
//            }
//        }
        Iterator<Process> iterator = processes.iterator();
        while (iterator.hasNext()) {
            Process p = iterator.next();
            // Perform some checks and modifications
            if (p.arrivalTime <= TIME) {
                ready.add(p);
                iterator.remove();
            }
        }
    }
    public void increaseOld() {
        ArrayList<Process> processesToModify = new ArrayList<>();

        // Find processes to modify
        for (Process x : ready) {
            if (TIME - x.arrivalTime >= 7 && x.Priority > 1) {
                processesToModify.add(x);
            }
        }

        // Modify the PriorityQueue outside the loop
        for (Process x : processesToModify) {
            ready.remove(x); // Remove the old Processfrom PriorityQueue
            x.Priority--;    // Modify the process
            ready.add(x);    // Add the modified Processback to PriorityQueue
        }
    }


    public void kernel(){
        // fetch from processes array
        // get maxPriority from ready if exist
        // increase TIME and save it's timing Data
        Process curr = null;
        double waitingTime = 0 ;
        double turnAround=0;
        int nP=0;
        while (!ready.isEmpty() || !processes.isEmpty()) {
            checkAvailable();
            increaseOld();
            if(ready.isEmpty()){
                TIME++;
            }
            else{
                nP++;
                curr  = ready.poll();
                int wait = TIME- curr.arrivalTime ;
                waitingTime += wait;
                curr.intervals.add(new interval(TIME,TIME+ curr.burstTime));
                TIME += curr.burstTime;
                System.out.println(curr.Name + "\nhas Turn Around time "+ (wait+curr.burstTime)+
                        "\nhas waiting time "+ (wait)
                );
                turnAround += wait + curr.burstTime;
                System.out.println();
                System.out.println();
                die.add(curr);
            }

        }


        System.out.println("Waiting Average Time = "+waitingTime/nP);
        System.out.println("Turn Around Average Time = "+waitingTime/nP);

    }

    public static Color getColorByName(String colorName) {
        if ("white".equalsIgnoreCase(colorName) || "WHITE".equalsIgnoreCase(colorName)) {
            return Color.WHITE;
        } else if ("lightGray".equalsIgnoreCase(colorName) || "LIGHT_GRAY".equalsIgnoreCase(colorName)) {
            return Color.LIGHT_GRAY;
        } else if ("gray".equalsIgnoreCase(colorName) || "GRAY".equalsIgnoreCase(colorName)) {
            return Color.GRAY;
        } else if ("darkGray".equalsIgnoreCase(colorName) || "DARK_GRAY".equalsIgnoreCase(colorName)) {
            return Color.DARK_GRAY;
        } else if ("black".equalsIgnoreCase(colorName) || "BLACK".equalsIgnoreCase(colorName)) {
            return Color.BLACK;
        } else if ("red".equalsIgnoreCase(colorName) || "RED".equalsIgnoreCase(colorName)) {
            return Color.RED;
        } else if ("pink".equalsIgnoreCase(colorName) || "PINK".equalsIgnoreCase(colorName)) {
            return Color.PINK;
        } else if ("orange".equalsIgnoreCase(colorName) || "ORANGE".equalsIgnoreCase(colorName)) {
            return Color.ORANGE;
        } else if ("yellow".equalsIgnoreCase(colorName) || "YELLOW".equalsIgnoreCase(colorName)) {
            return Color.YELLOW;
        } else if ("green".equalsIgnoreCase(colorName) || "GREEN".equalsIgnoreCase(colorName)) {
            return Color.GREEN;
        } else if ("magenta".equalsIgnoreCase(colorName) || "MAGENTA".equalsIgnoreCase(colorName)) {
            return Color.MAGENTA;
        } else if ("cyan".equalsIgnoreCase(colorName) || "CYAN".equalsIgnoreCase(colorName)) {
            return Color.CYAN;
        } else if ("blue".equalsIgnoreCase(colorName) || "BLUE".equalsIgnoreCase(colorName)) {
            return Color.BLUE;
        }

        return null;  // Invalid color Name
    }

    public static ArrayList<Process> RUN() {
        Scanner scanner = new Scanner(System.in);
        int pNum = 0 ;
        System.out.print("Number of processes =>");
        pNum = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        String pName ,pColor  ;
        int pAt, pBt ,pPn ;
        priorityScheduling app = new priorityScheduling();
//        for (int i = 0; i < pNum; i++) {
//            System.out.print(i+1+" Process Name => ");
//            pName = scanner.nextLine();
//            System.out.print(i+1+" Process Color => ");
//            pColor = scanner.nextLine();
//            System.out.print(i+1+" Process Burst Time => ");
//            pBt = scanner.nextInt();
//            System.out.print(i+1+" Process Priority Number => ");
//            pPn = scanner.nextInt();
//            System.out.print(i+1+" Process Arrival Time => ");
//            pAt = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//            Processj = new process(pName,pAt,pBt,pPn,getColorByName(pColor)) ;
//            app.processes.add(j);
//            System.out.println();
//            System.out.println();
//            System.out.println();
//        }
        app.processes.add(new Process("process1",0,15,1,Color.black));
        app.processes.add(new Process("process2",1,1,6,Color.RED));
        app.processes.add(new Process("process3",14,2,1,Color.BLUE));
        app.processes.add(new Process("process4",14,2,2,Color.CYAN));
        app.processes.add(new Process("process5",14,2,3,Color.YELLOW));
        app.processes.add(new Process("process6",14,2,4,Color.pink));
        app.processes.add(new Process("process7",14,2,5,Color.ORANGE));


        app.kernel();
        return app.die ;
//        GUI gui = new GUI();
//        gui.Build(app.die);
    }

}


//////////////////////// AG
class AGScheduler {
    public ArrayList<Process> Processes;
    public Deque<Process> readyQueue;
    public ArrayList<Process> dieList;
    public ArrayList<Process> executionOrder;
    //public final int QUANTUM = 4;
    public int TIME = 0;

    public AGScheduler(ArrayList<Process> processes) {
        Processes = processes;
        readyQueue = new LinkedBlockingDeque<>();
        dieList = new ArrayList<>();
        executionOrder = new ArrayList<>();
    }

    public Process getBestProcess() {
        Process x = readyQueue.peek();
        for (Process process : readyQueue) {
            if (process.AGFactor <= x.AGFactor) {
                x = process;
            }
        }
        return x;
    }

    public void checkAvailableProcess(Process x) {
        for (Process p :
                Processes) {
            if (p.arrivalTime <= TIME && !readyQueue.contains(p) && p != x) readyQueue.addLast(p);
        }
    }

    public void updateQuantum(Process process, int State) {
        switch (State) {
            case 1:
                process.Quantum = process.lastQuantum + (int) Math.ceil(process.quantumMean() * 0.1);
                process.quantumHistory.add(process.Quantum);
                break;
            case 2:
                process.Quantum += process.lastQuantum;
                process.quantumHistory.add(process.Quantum);
                break;
            case 3:
                process.Quantum = 0;
                process.quantumHistory.add(process.Quantum);
                Processes.remove(process);
                dieList.add(process);
                process.waitingTime = TIME - process.arrivalTime - process.originalBurstTime;
                process.turnAroundTime = TIME - process.arrivalTime;
                break;
            default:
                break;
        }
    }

    public void Kernel() {
        Process prev = null;
        boolean thereIsBetter = false;
        int beginningTime = 0;
        while (Processes.size() > 0) {
            checkAvailableProcess(prev);
            if (!readyQueue.isEmpty()) {
                Process p;
                if (!thereIsBetter)
                    p = readyQueue.peek();
                else {
                    p = getBestProcess();
                    thereIsBetter = false;
                }
                beginningTime = TIME;
                executionOrder.add(p);
                if (prev != null && Processes.contains(prev))
                    readyQueue.addLast(prev);
                p.lastQuantum = p.Quantum;
                int temp = (int) Math.ceil((double) p.Quantum / 2);
                if (p.burstTime < temp)
                    temp = p.burstTime;
                p.burstTime -= temp;
                TIME += temp;
                p.Quantum -= temp;
                checkAvailableProcess(null);
                while (p.Quantum > 0 && p.burstTime > 0) {
                    if (p == getBestProcess()) {
                        p.Quantum--;
                        p.burstTime--;
                        TIME++;
                    } else {
                        if (p.Quantum >= 0) {
                            updateQuantum(p, 2);
                            prev = p;
                            readyQueue.remove(p);
                            thereIsBetter = true;
                            p.intervals.add(new interval(beginningTime, TIME));
                            break;
                        }
                    }
                    checkAvailableProcess(prev);
                }
                if (p.burstTime > 0 && p.Quantum == 0) {
                    updateQuantum(p, 1);
                    prev = p;
                    readyQueue.remove(p);
                    p.intervals.add(new interval(beginningTime, TIME));
                } else if (p.burstTime <= 0) {
                    updateQuantum(p, 3);
                    prev = p;
                    readyQueue.remove(p);
                    p.intervals.add(new interval(beginningTime, TIME));
                }
            } else {
                TIME++;
            }
        }
    }

    public double clcAVGTAT() {
        int sum = 0;
        for (Process p :
                dieList) {
            sum += p.turnAroundTime;
        }
        return (double) sum / dieList.size();
    }

    public double clcAVGWT() {
        int sum = 0;
        for (Process p :
                dieList) {
            sum += p.waitingTime;
        }
        return (double) sum / dieList.size();
    }

    public void printDetails() {
        System.out.print("Process Execution Order: ");
        for (int i = 0; i < executionOrder.size(); i++) {
            System.out.print(executionOrder.get(i).Name);
            if (i < executionOrder.size() - 1)
                System.out.print(" ");
        }
        System.out.println();
        for (Process process : dieList) {
            System.out.println(process.Name + " Waiting Time : " + process.waitingTime);
        }
        for (Process process : dieList) {
            System.out.println(process.Name + " Turn Around Time: " + process.turnAroundTime);
        }
        double AVGWT = clcAVGWT();
        System.out.println("Average Waiting Time: " + AVGWT);
        double AVTAT = clcAVGTAT();
        System.out.println("Average Turn Around Time: " + AVTAT);

        for (int i = 0; i < dieList.size(); i++) {
            System.out.print(dieList.get(i).Name + " Quantum History : ");
            for (int j = 0; j < dieList.get(i).quantumHistory.size(); j++) {
                System.out.print(dieList.get(i).quantumHistory.get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
class AG {
    public static ArrayList<Process> RUN (){
        ArrayList<Process> p = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of process");
        int number = input.nextInt();
        System.out.println("Enter Round Robin Time Quantum");
        int roundRobin = input.nextInt();
        System.out.println("Enter the context");
        int context = input.nextInt();
        for(int i = 0;i<number ; i ++) {
            System.out.println("Enter the name ");
            String name = input.next();
            System.out.println("Enter the color ");
            String color = input.next();
            System.out.println("Enter the arrivalTime");
            int arrivalTime = input.nextInt();
            System.out.println("Enter the burstTime");
            int burstTime = input.nextInt();
            System.out.println("Enter the priority number");
            int priorityNumber = 0;
//            priorityNumber = input.nextInt();
//            System.out.println("Enter the Random number");
            int random = input.nextInt();
            p.add(new Process(name,priorityScheduling.getColorByName(color),priorityNumber,arrivalTime,burstTime,roundRobin,random));
        }
        AGScheduler AG = new AGScheduler(p);
        AG.Kernel();
        AG.printDetails();
        return AG.dieList;
    }

}

////////////////////// SJF , SRTF
class SRTF{
    ArrayList<Process>processes = new ArrayList<>();
    PriorityQueue<Process>readyQueue  = new PriorityQueue<>(Comparator.comparingInt(Process::getStarvationBurst).thenComparingInt(Process::getArrivalTime));
    ArrayList<Process>processOrder = new ArrayList<>();
    ArrayList<Process>die = new ArrayList<>();
    int averageWaiting ;
    int averageTruning ;
    Process current;
    int time ;
    SRTF(ArrayList<Process>proc) {
        this.processes = proc ;
        time = 0 ;
        current=null;
        averageWaiting = 0;
        averageTruning=0;
    }
    void getFirstProcess()
    {
        int x = 100000;
        int index = -1 ;
        for(int i = 0 ; i <processes.size();i++)
        {
            if(processes.get(i).getArrivalTime()<x)
            {
                index = i;
                x=processes.get(i).getArrivalTime();
            }
            else if(processes.get(i).getArrivalTime()==x)
            {
                if(index!=-1&&processes.get(i).getBurstTime()<processes.get(index).getBurstTime())
                {
                    index = i ;
                }
            }
        }
        time =x;
        Process p = processes.get(index);
        processes.remove(index);
        readyQueue.add(p);
    }
    void getArivalProcess()
    {
        int number = processes.size();
        ArrayList<Integer>l = new ArrayList<>();
        for(int i = 0 ; i <number;i++)
        {
            if(processes.get(i).getArrivalTime()==time)
            {
                Process p = processes.get(i);
                l.add(i);
                readyQueue.add(p);
            }
        }
        for(int i =l.size()-1;i>=0;i--)
        {
            int z = l.get(i);
            processes.remove(z);
        }
        Process first = readyQueue.peek();
        if(current!=null&&!current.getName().equals(first.getName()))
        {
            current.setEnd(time);
            processOrder.add(current);
            current.intervals.add(new interval(current.getStart(),current.getEnd()));
            current=first;
            current.setStart(time);
        }

    }
    void starvation()
    {
        PriorityQueue<Process>temp  = new PriorityQueue<>(Comparator.comparingInt(Process::getStarvationBurst));
        for(Process p : readyQueue)
        {
            p.setStarvationBurst(p.getStarvationBurst()-(time - p.getArrivalTime()));
            temp.add(p);
        }
        readyQueue.clear();
        readyQueue.addAll(temp);
        Process first = readyQueue.peek();
        if(current!=null &&!current.getName().equals(first.getName()))
        {
            current.setEnd(time);
            processOrder.add(current);
            current.intervals.add(new interval(current.getStart(),current.getEnd()));
            current=first;
            current.setStart(time);
        }
    }
    void build()
    {
        while(true)
        {
            if(processes.isEmpty()&&readyQueue.isEmpty())
            {
                break;
            }
            if(readyQueue.isEmpty())
            {
                getFirstProcess();
            }
            if( current==null ) {
                current = readyQueue.peek();
                current.setStart(time);
            }
            if((time+1)%5==0)
            {
                starvation();
            }
            time++;
            assert current != null;
            current.setRemainingTime(current.getRemainingTime()-1);
            if(current.getRemainingTime()==0)
            {
                current.setWaitingTime(time - current.getArrivalTime()-current.getBurstTime());
                int truned = time - current.getArrivalTime();
                current .setTurnedTime(truned);
                current.setEnd(time);
                processOrder.add(current);
                current.intervals.add(new interval(current.getStart(),current.getEnd()));
                die.add(current);
                readyQueue.poll();
                current = null;
            }
            if(processes.size()!=0)
            {
                getArivalProcess();
            }
        }
    }
    void show()
    {
        System.out.print("Process Execution Order : ");
        for(int i = 0 ; i <processOrder.size();i++)
        {
            System.out.print(processOrder.get(i).getName()+ "  ");
        }
        System.out.println();
        for(int i = 0 ; i <die.size();i++)
        {
            System.out.println("Name : "+die.get(i).getName()+"  "+"Waiting Time :"+" "+die.get(i).getWaitingTime()
                    +"           Trun Around Time     "+die.get(i).getTurnedTime());
            averageWaiting+=die.get(i).getWaitingTime();
            averageTruning+=die.get(i).getTurnedTime();
        }
        int x = die.size();
        averageTruning/=x;
        averageWaiting/=x;
        System.out.println("Average Trun Around Time :  "+averageTruning);
        System.out.println("Average  Waiting Time  :  "+averageWaiting);

    }


}
class SJF{
    int context ;
    int avergeWait = 0 ;
    int avergeAround = 0 ;
    int time ;
    ArrayList<Process> processes = new ArrayList<>();
    PriorityQueue<Process>readyQueue  = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime).thenComparingInt(Process::getArrivalTime));
    ArrayList<Process>die = new ArrayList<>();
    SJF(ArrayList<Process>processes , int context)
    {
        this.processes=processes;
        this.context = context;
        time = 0;
    }
    void getFirstProcess()
    {
        int x = 100000;
        int index = -1 ;
        for(int i = 0 ; i <processes.size();i++)
        {
            if(processes.get(i).getArrivalTime()<x)
            {
                index = i;
                x=processes.get(i).getArrivalTime();
            }
            else if(processes.get(i).getArrivalTime()==x)
            {
                if(index!=-1&&processes.get(i).getBurstTime()<processes.get(index).getBurstTime())
                {
                    index = i ;
                }
            }
        }
        time =x;
        Process p = processes.get(index);
        processes.remove(index);
        readyQueue.add(p);
    }
    void getArivalProcess()
    {
        int number = processes.size();
        ArrayList<Integer>l = new ArrayList<>();
        for(int i = 0 ; i <number;i++)
        {
            if(processes.get(i).getArrivalTime()<=time)
            {
                Process p = processes.get(i);
                l.add(i);
                readyQueue.add(p);
            }
        }
        for(int i =l.size()-1;i>=0;i--)
        {
            int z = l.get(i);
            processes.remove(z);
        }

    }
    void build()
    {
        die = new ArrayList<>();
        boolean f = false;
        while(true)
        {
            if(processes.isEmpty()&&readyQueue.isEmpty())
            {
                break;
            }
            if(readyQueue.isEmpty())
            {
                getFirstProcess();
            }
            Process p = readyQueue.poll();
            if(f)
            {
                time+=context;
            }
            p.setStart(time);
            p.setWaitingTime(time-p.getArrivalTime());
            time+=p.getBurstTime();
            int truned = time - p.getArrivalTime();
            p.setTurnedTime(truned);
            p.setEnd(time);
            p.intervals.add(new interval(p.getStart(),p.getEnd()));
            die.add(p);
            getArivalProcess();
            f=true;

        }
    }
    void show()
    {
        System.out.print("Process Execution Order :");
        for(int i = 0 ; i< die.size();i++)
        {
            System.out.print(die.get(i).getName()+ "  ");
        }
        System.out.println();
        for (int i = 0 ; i < die.size();i++)
        {
            avergeWait+=die.get(i).getWaitingTime();
            avergeAround+=die.get(i).getTurnedTime();
            System.out.println("Name : "+die.get(i).getName()+"   Waiting Time  : "+die.get(i).getWaitingTime()+"   Trun Around Time : "+die.get(i).getTurnedTime());

        }
        int x = avergeWait/(int)die.size();
        int y = avergeAround/(int)die.size();
        System.out.println("Average Waiting Time : " +x);
        System.out.println("Average Trun Around Time : "+y);

    }
}
class Main_sawy {
    public static ArrayList<Process> srtf(){
        ArrayList<Process> proc = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of Process");
        int number = input.nextInt();
        for (int i = 0; i < number; i++) {
            System.out.println("Enter the name ");
            String name = input.next();
            System.out.println("Enter the color ");
            String color = input.next();
            System.out.println("Enter the arrivalTime");
            int arivalTime = input.nextInt();
            System.out.println("Enter the burstTime");
            int burstTime = input.nextInt();
            Process p = new Process(name,priorityScheduling.getColorByName(color),arivalTime,burstTime);
            proc.add(p);
        }
//        proc.add(new Process("p0",Color.BLACK,0,7));
//        proc.add(new Process("p1",Color.BLACK,2,4));
//        proc.add(new Process("p2",Color.BLACK,4,1));
//        proc.add(new Process("p3",Color.BLACK,5,4));

        SRTF s = new SRTF(proc);
        s.build();
        s.show();
        return s.die ;
    }
    public static ArrayList<Process> sjf(){
        ArrayList<Process> proc = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of Process");
        int number = input.nextInt();
        System.out.println("Enter the context");
        int context = input.nextInt();
        for(int i = 0;i<number ; i ++) {
            System.out.println("Enter the name ");
            String name = input.next();
            System.out.println("Enter the color ");
            String color = input.next();
            System.out.println("Enter the arrivalTime");
            int arivalTime = input.nextInt();
            System.out.println("Enter the burstTime");
            int burstTime = input.nextInt();
            Process p = new Process(name,priorityScheduling.getColorByName(color),arivalTime,burstTime);
            proc.add(p);
        }
//        proc.add(new Process("p0",Color.BLACK,0,7));
//        proc.add(new Process("p1",Color.BLACK,2,4));
//        proc.add(new Process("p2",Color.BLACK,4,1));
//        proc.add(new Process("p3",Color.BLACK,5,4));

        SJF s = new SJF(proc,context);
        s.build();
        s.show();
        return s.die ;
    }

}

///////////////////////// MAIN

public class Main{
    public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);
            String choice = "";
      while (true) {
          System.out.println("Enter Algorithm Number");
          System.out.println("1- SJF");
          System.out.println("2- SRTF");
          System.out.println("3- Priority Scheduling");
          System.out.println("4- AG-Algorithm");
          choice = scanner.nextLine();
          GUI gui = new GUI();
          if (Objects.equals(choice, "1")) {
              gui.Build(Main_sawy.sjf());
          } else if (Objects.equals(choice, "2")) {
              gui.Build(Main_sawy.srtf());
          } else if (Objects.equals(choice, "3")) {
              gui.Build(priorityScheduling.RUN());
          } else if (Objects.equals(choice, "4")) {
              gui.Build(AG.RUN());
          } else {
              // Error
              System.out.println("Not Found");
          }

      }
    }
}

////////////////// GUI
class  GUI {
    public void Build(ArrayList<Process> data){
       JFrame f = new JFrame();
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.setLocationRelativeTo(null);
       f.setVisible(true);
       f.setSize(1200,800);
       f.setLayout(new GridLayout(1,1));

       JPanel p = new JPanel(new GridLayout(data.size(),1,0,0));
       p.setSize(new Dimension(2000,f.getHeight()));
        for (int i = 0; i < data.size(); i++) {
            pane p1 = new pane(data.get(i));
            p.add(p1);
        }
        f.add(p);
        f.add(new JLabel("PROCESS"));
    }
}
class pane extends JPanel{
    Process pr;
    public pane(Process p){

        this.pr = p ;
        this.setSize(100,1000);

    }
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawString(pr.Name,0,getHeight()/3);

        g2d.setColor(Color.black);
        g2d.drawLine(60,getHeight()/3,getWidth(),getHeight()/3);

        if(pr.color== null){
            g2d.setColor(Color.black);
        }
        else{
            g2d.setColor(pr.color);
        }

        for (int i = 0; i < pr.intervals.size(); i++) {
            interval x = pr.intervals.get(i);

            g2d.fillRect(((x.start)*10)+60,0,((x.end-x.start)*10),2*getHeight()/3);
        }

    }
}
