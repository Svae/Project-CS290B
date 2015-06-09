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

public class ScheduleTasks extends TaskRecursive<ResultSchedule> {
	
	
	private List<Job> jobs;
	private int computers;
	private Schedule schedule;
	private LowerBound lowerBound;
	private boolean constrains;
	
	
	public ScheduleTasks(int computers, List<Job> jobs, boolean constraints) {
		this.computers = computers;
		this.jobs = jobs;
		this.constrains = constraints;
		schedule = new Schedule(computers);
		lowerBound = new LowerBoundSimpleConstraints(computers, jobs);
		
	}
	
	

	public ScheduleTasks(int computers, ScheduleTasks parent, List<Job> jobs, int id, boolean constraints) {
		this.computers = computers;
		this.jobs = jobs;
		this.constrains = constraints;


		HashMap<Integer, JobList> copy = new HashMap<Integer, JobList>();
		for(Map.Entry<Integer,JobList> entry : parent.getSchedule().getSchedule().entrySet()){
		    copy.put(entry.getKey(), new JobList(entry.getValue()));
		}
		this.schedule = new Schedule(computers, copy , parent.getSchedule().getMaxLength());
		if(constraints){
			lowerBound = new LowerBoundSimpleConstraints(this.schedule, findAvailableJobs(), id, constraints);
		}
		else lowerBound = parent.lowerBound.make(this.schedule, this.jobs.remove(0), id, constraints);
		//lowerBound = new LowerBoundLpt(schedule, this.jobs.remove(0), id, computers, jobs);
		
	}


	@Override
	public boolean isAtomic() {
		System.out.println("Atomic: " + jobs.size());
		return jobs.size() <= 4;
	}
	
	public LowerBound lowerBound(){ return lowerBound; };
	
	public double cost() { return lowerBound().cost(); }
	
	public Schedule getSchedule(){ return schedule; };

	@Override
	public ReturnValue<ResultSchedule> solve() {
		System.out.println("Solving");
		final Stack<ScheduleTasks> stack = new Stack<>();
        stack.push( this );
        SharedSchedule sharedSchedule = ( SharedSchedule ) shared();
        Schedule shortestSchedule = sharedSchedule.schedule();
        double shortestScheduleCost = sharedSchedule.cost();
        while ( ! stack.isEmpty() ) 
        {
        	ScheduleTasks currentTask = stack.pop();
            List<ScheduleTasks> children = currentTask.children( shortestScheduleCost );
            System.out.println("Children: " + children.size());
            for ( ScheduleTasks child : children )
            {   // child lower bound < upper bound.
            	System.out.println("FOR");
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
		shared( new SharedSchedule(this.schedule, schedule.getMaxLength()));
		return new ReturnValueSchedule(this, new ResultSchedule(schedule, schedule.getMaxLength()));
	}

	@Override
	public ReturnDecomposition divideAndConquer() {
		System.out.println("Divide");
		return new ReturnDecomposition(new CompareSchedules(), children(( (SharedSchedule) shared()).cost() ));
	}

	private List<ScheduleTasks> children(double cost) {
		List<ScheduleTasks> children = new LinkedList<>();
		for (int i = 1; i <= computers; i++) {
			ScheduleTasks child = new ScheduleTasks(computers, this, new ArrayList<Job>(jobs), i, this.constrains);
			if(child.lowerBound().cost() < cost){
				children.add(child);
			}
		}
		return children;
	}
	
	private boolean isComplete() { return jobs.isEmpty(); }

	private Job findAvailableJobs() {
		Job temp;
		Job max;
		boolean ava;
		for(Job j: jobs){
			ava = true;
			temp = null;
			max = null;
			if (j.hasDependences()){
				List<Integer> l = j.getDependences();
			
				for (Integer id : l) {
					temp = schedule.containsJob(id);
					if(schedule.containsJob(id) == null) ava = false;
					else{
						if(j == null) max = temp;
						else {
							if(j.getStop()<temp.getStop()) max = temp;
						}
					}
				}
				if(ava){
					System.out.println("Before: " + jobs.size());
					jobs.remove(j);
					System.out.println("After: " + jobs.size());
					j.setStart(max.getStop());
					return j;
				}
			} else {
				return j;
			}
		}
		return null;
	}
	
}
