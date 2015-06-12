package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import system.Return;
import util.Job;
import util.JobList;
import util.Schedule;
import api.ReturnDecomposition;
import api.ReturnValue;
import api.TaskRecursive;


public class ScheduleTasks extends TaskRecursive<ResultSchedule> {
	
	/**
	 * Finds the optimal schedule for a list of jobs without dependences
	 */
	
	private static final int JOBFACTOR = 10;
	private List<Job> jobs;
	private int computers;
	private Schedule schedule;
	private LowerBound lowerBound;
	
	
	public ScheduleTasks(int computers, List<Job> jobs) {
		this.computers = computers;
		this.jobs = jobs;
		schedule = new Schedule(computers);
		lowerBound = new LowerBoundZero(computers, jobs);

	}
	
	public ScheduleTasks(int computers, ScheduleTasks parent, List<Job> jobs, int id) {
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
	public ReturnValue<ResultSchedule> solve() {
		final Stack<ScheduleTasks> stack = new Stack<>();
        stack.push( this );
        SharedSchedule sharedSchedule = ( SharedSchedule ) shared();
        Schedule shortestSchedule = sharedSchedule.schedule();
        double shortestScheduleCost = sharedSchedule.cost();
        while ( ! stack.isEmpty() ) 
        {
        	ScheduleTasks currentTask = stack.pop();
            List<ScheduleTasks> children = currentTask.children( shortestScheduleCost );
            for ( ScheduleTasks child : children )
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
	public Return divideAndConquer() {
		SharedSchedule localShared = (SharedSchedule) shared();
		List<ScheduleTasks> children = children(( localShared ).cost() );
		if(children.size() == 0){
			return new ReturnValueSchedule(this, new ResultSchedule(localShared.schedule(), localShared.cost()));
		}
		return new ReturnDecomposition(new CompareSchedules(), children);
	}

	private List<ScheduleTasks> children(double cost) {
		List<ScheduleTasks> children = new LinkedList<>();
		for (int i = 1; i <= computers; i++) {
			ScheduleTasks child = new ScheduleTasks(computers, this, new ArrayList<Job>(jobs), i);
			if(child.lowerBound().cost() < cost){
				children.add(child);
			} 
		}
		return children;
	}
	
	private boolean isComplete() { return jobs.isEmpty(); }
	
	public List<Job> getJobs(){
		return jobs;
	}
	
	public int getNumberOfComputers(){
		return computers;
	}
	
	@Override
	public boolean isAtomic() {
		return jobs.size() <= JOBFACTOR/computers;
		
	}
	
	public LowerBound lowerBound(){ return lowerBound; };
	
	public double cost() { return lowerBound().cost(); }
	
	public Schedule getSchedule(){ return schedule; };
}
