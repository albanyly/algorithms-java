package com.albanyly.algorithms;

import java.util.Stack;

public class HanoiTower {

	/*
	 * - Tower as Stack, three towers: source, destination, and buffer
	 * - source tower has n disks of different sizes, asc from top down
	 * - move all disks from source to destination tower
	 * - move one disk at a time
	 * - at anytime, must a smaller disk on top of a larger disk
	 * 
	 * - n = 1, move directly
	 * - n = 2, move d1 to buffer tower, d2 to destination, d1 from
	 *   buffer to destination tower
	 * - n = 3, d1 to destination, d2 to buffer, d1 to buffer, d3 to destination,
	 *   d1 back to source, d2 to destination, d1 to destination
	 *   
	 * - This is DP.
	 * - Recursively 3 steps:
	 * - 1st: move n-1 to buffer
	 * - 2nd: move n from source to destination
	 * - 3rd: move n-1 from buffer to destination
	 * - repeat above with n -> n-1
	 * 
	 */
	public static void hanoiTower(int n) {
		if (n <= 0) {
			return;
		}
		Tower source = new Tower("source");
		Tower dest = new Tower("dest");
		Tower buffer = new Tower("buffer");
		if (n == 1) {
			dest.push(1);
			dest.print();
			return;
		}
		for (int i = n; i > 0; i--) {
			source.push(i);
		}
		System.out.println("Before action:");
		System.out.println("Source Tower:");
		source.print();
		System.out.println("Dest Towner:");
		dest.print();
		System.out.println("Buffer Towner:");
		buffer.print();
		System.out.println("\r\n");
		
		moveDisk(n, source, dest, buffer);
		
		System.out.println("\r\nAfter action:");
		System.out.println("Source Tower:");
		source.print();
		System.out.println("Dest Towner:");
		dest.print();
		System.out.println("Buffer Towner:");
		buffer.print();
	}
	
	protected static void moveDisk(int n, Tower source, Tower dest, Tower buffer) {
		if (n == 1) {
			source.moveTo(dest);
		}
		else if (n > 1){
			moveDisk(n-1, source, buffer, dest);
			source.moveTo(dest);
			moveDisk(n-1, buffer, dest, source);
			System.out.println("Disk " + n + " to 1 moved from source to dest");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Tower of Hanoi, size = 5");
		hanoiTower(5);
	}
}

class Tower {
	private String label;
	private Stack<Integer> stack;
	public Tower(String label) {
		this.label = label;
		this.stack = new Stack<Integer>();
	}
	public String getLabel() {
		return label;
	}
	public void push(int i) {
		this.stack.push(i);
	}
	public Integer pop() {
		return this.stack.pop();
	}
	public Integer peek() {
		return this.stack.peek();
	}
	public void moveTo(Tower t) {
		if (!stack.isEmpty()) {
			//System.out.println("Source before move:");
			//this.print();
			Integer i = this.pop();
			t.push(i);
			System.out.println("move disk " + i + " from " + getLabel() + "\tto " + t.getLabel());
			//System.out.println("Dest after move:");
			//t.print();
		}
	}
	public void print() {
		System.out.print("Tower " + getLabel() + ": ");
		for (int i = 0; i < stack.size(); i++) {
			System.out.print(" " + stack.get(i));
		}
		System.out.print("\r\n");
	}
}
