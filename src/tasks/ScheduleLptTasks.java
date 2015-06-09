package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import util.Job;
import util.JobList;
import util.Schedule;
import api.ReturnDecomposition;
import api.ReturnValue;
import api.TaskRecursive;

public class ScheduleLptTasks extends TaskRecursive<ResultSchedule> {
	
	
	private List<Job> jobs;
	private int computers;
	private Schedule schedule;
	private LowerBound lowerBound;
	
	
	public ScheduleLptTasks(int computers, List<Job> jobs) {
		this.computers = computers;
		this.jobs = jobs;
		schedule = new Schedule(computers);
		// Choose lowerbound type
		lowerBound = new LowerBoundLpt(computers, jobs);
		// lowerBound = new LowerBoundSimple(computers, jobs);

		
	}
	
	

	public ScheduleLptTasks(int computers, ScheduleLptTasks parent, List<Job> jobs, int id) {
		this.computers = computers;
		this.jobs = jobs;


		HashMap<Integer, JobList> copy = new HashMap<Integer, JobList>();
		for(Map.Entry<Integer,JobList> entry : parent.getSchedule().getSchedule().entrySet()){
		    copy.put(entry.getKey(), new JobList(entry.getValue()));
		}
		this.schedule = new Schedule(computers, copy , parent.getSchedule().getMaxLength());
		
		lowerBound = parent.lowerBound.make(this, this.jobs.remove(0), id);
		
	}


	@Override
	public boolean isAtomic() {
		return jobs.size() <= 3;
	}
	
	public LowerBound lowerBound(){ return lowerBound; };
	
	public double cost() { return lowerBound().cost(); }
	
	public Schedule getSchedule(){ return schedule; };

	@Override
	public ReturnValue<ResultSchedule> solve() {
		final Stack<ScheduleLptTasks> stack = new Stack<>();
        stack.push( this );
        SharedSchedule sharedSchedule = ( SharedSchedule ) shared();
        Schedule shortestSchedule = sharedSchedule.schedule();
        double shortestScheduleCost = sharedSchedule.cost();
        while ( ! stack.isEmpty() ) 
        {
        	ScheduleLptTasks currentTask = stack.pop();
            List<ScheduleLptTasks> children = currentTask.children( shortestScheduleCost );
            for ( ScheduleLptTasks child : children )
            {   // child lower bound < upper bound.
                if ( child.isComplete() )
                { 
                    shortestSchedule = child.getSchedule();
                    shortestScheduleCost = child.lowerBound().cost();
                } 
                else 
                { 
                    stack.push( child );
                } 
            }  
        } 
		shared( new SharedSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
		return new ReturnValueSchedule(this, new ResultSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
	}

	@Override
	public ReturnDecomposition divideAndConquer() {
		return new ReturnDecomposition(new CompareSchedules(), children(( (SharedSchedule) shared()).cost() ));
	}

	private List<ScheduleLptTasks> children(double cost) {
		List<ScheduleLptTasks> children = new LinkedList<>();
		for (int i = 1; i <= computers; i++) {
			ScheduleLptTasks child = new ScheduleLptTasks(computers, this, new ArrayList<Job>(jobs), i);
			// Add priority
			if(child.lowerBound().cost() < cost){
				children.add(child);
			}
		}
		return children;
	}
	
	private boolean isComplete() { return jobs.size() == 0; }
	
	public List<Job> getJobs(){
		return jobs;
	}
	
	public int getNumberOfComputers(){
		return computers;
	}
	
}
