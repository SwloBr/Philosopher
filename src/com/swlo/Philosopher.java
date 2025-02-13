package com.swlo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;

class Philosopher extends Thread {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking.");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating.");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (true) {
            think();
            Lock firstFork = id % 2 == 0 ? leftFork : rightFork;
            Lock secondFork = id % 2 == 0 ? rightFork : leftFork;

            firstFork.lock();
            try {
                System.out.println("Philosopher " + id + " picked up the first fork.");
                secondFork.lock();
                try {
                    System.out.println("Philosopher " + id + " picked up the second fork and is ready to eat.");
                    eat();
                } finally {
                    secondFork.unlock();
                    System.out.println("Philosopher " + id + " put down the second fork.");
                }
            } finally {
                firstFork.unlock();
                System.out.println("Philosopher " + id + " put down the first fork.");
            }
        }
    }
}