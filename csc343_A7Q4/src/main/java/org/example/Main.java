package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
    private final Lock left;
    private final Lock right;
    // Locks for the forks

    // Constructor for philosopher and forks
    public Philosopher(Lock left, Lock right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        // The philosopher and forks infinite loop
        while (true) {
            think();      // thinks for a while
            grabForks();  // grabs both forks
            eat();        // eats
            releaseForks(); // this releases the forks after eating
        }
    }

    // Simulates thinking
    private void think() {
        System.out.println(Thread.currentThread().getName() + " is thinking.");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        // Sleeps to simulate thinking
    }

    // the philosopher grabs both forks to avoid deadlock
    // Philosophers grab the left fork first then the right one
    private void grabForks() {
        if (Thread.currentThread().getName().equals("Philosopher 0")) {
            left.lock();
            right.lock();
        } else {
            right.lock();
            left.lock();
        }
    }

    // Simulates eating
    private void eat() {
        System.out.println(Thread.currentThread().getName() + " is eating.");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        // Sleeps to simulate eating
    }

    // Method to release the forks after eating
    private void releaseForks() {
        left.unlock();
        right.unlock();
        // Releases forks
    }
}

public class Main {
    public static void main(String[] args) {
        // Locks representing the first fork
        Lock fork1 = new ReentrantLock();
        Lock fork2 = new ReentrantLock();

        // philosophers with diff forks to avoid deadlock
        Philosopher philosopher1 = new Philosopher(fork1, fork2); // Philosopher 0 takes fork1 first, then fork2
        Philosopher philosopher2 = new Philosopher(fork2, fork1); // Philosopher 1 takes fork2 first, then fork1

        philosopher1.setName("Philosopher 0");
        philosopher2.setName("Philosopher 1");
        //Setting the names

        philosopher1.start();
        philosopher2.start();
        // Start philosopher 0's and 1's thread
    }
}
