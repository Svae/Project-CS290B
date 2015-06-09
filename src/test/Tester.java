package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tester {
	
	private int m = 3;
	
	/*private int[][] jobs = { 
			{1,1},
			{2,2},
			{3,1},
			{4,1},
	};*/
	List<String>list = new ArrayList<String>();
	private String[] jobs = {"1","2", "3"};
	public Tester() {
		//List<int[]> list = Arrays.asList(jobs);
		
		int k= 2; 
		//findTours(list, k);
		List<String> l1 = new ArrayList<String>();
		l1.add("1");
		l1.add("");
		l1.add("");
		generateInitTask(1, l1 );
		List<String> l2 = new ArrayList<String>();
		l2.add("");
		l2.add("1");
		l2.add("");
		generateInitTask(1, l2);
		List<String> l3 = new ArrayList<String>();
		l3.add("");
		l3.add("");
		l3.add("1");
		generateInitTask(1, l3);
		
	} 
	

	private void generateInitTask(int k, List<String> list) {
		List<String> temp;
		String s;
		if(k==jobs.length) printl(list);
		else{
		for (int j = 0; j < m; j++) {
			temp = new ArrayList<String>(list);
			s = temp.remove(j);
			s += ("," + jobs[k]);
			temp.add(j, s);
			generateInitTask(k+1, temp);
		}
		}
	}		


	private void printl(List<String> list2) {
		System.out.println("-----------------------");
		int n = 1;
		for (String string : list2) {
			System.out.println(n + ": " + string);
			n++;
		}
	}


	public static void main(String[] args) {
		Tester test = new Tester();
	}
}
