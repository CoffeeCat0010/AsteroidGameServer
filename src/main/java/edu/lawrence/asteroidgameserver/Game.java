/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgameserver;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Justin
 */
public class Game {
    private ReadWriteLock progressLock;
    private ReadWriteLock startLock;

    private boolean started;

    private int[] progress;
    public Game(){
        progress = new int[]{0 , 0};
        progressLock = new ReentrantReadWriteLock();
        startLock = new ReentrantReadWriteLock();

    }
    public int getProgress(int id) {
        int result = 0;
        try{
            progressLock.readLock().lock();
            result = progress[id];
        }finally{
            progressLock.readLock().unlock();
        }
        return result;
    }

    public void setProgress(int newProgress, int id) {
        try{
            progressLock.writeLock().lock();
            progress[id] = newProgress;
        }finally{
            progressLock.writeLock().unlock();
        }
    }
    public void start(){
        startLock.writeLock().lock();
        started=true;
        startLock.writeLock().unlock();

    }
    public boolean isStarted(){
        startLock.readLock().lock();
        boolean result = started;
        startLock.readLock().unlock();
        return result;
    }
}
