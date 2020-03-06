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
    private ReadWriteLock lock;

    private int progress;
    public Game(){
        lock = new ReentrantReadWriteLock();
    }
    public int getProgress() {
        int result = 0;
        try{
            lock.readLock().lock();
            result = progress;
        }finally{
            lock.readLock().unlock();
        }
        return result;
    }

    public void setProgress(int progress) {
        try{
            lock.writeLock().lock();
            this.progress = progress;
        }finally{
            lock.writeLock().unlock();
        }
    }
}
