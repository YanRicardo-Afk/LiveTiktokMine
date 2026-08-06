package com.zapphon.liveevents.queue;

import com.zapphon.liveevents.models.LiveEvent;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {

    private final Queue<LiveEvent> queue = new LinkedList<>();

    public void add(LiveEvent event){

        queue.add(event);

    }

    public LiveEvent poll(){

        return queue.poll();

    }

    public boolean isEmpty(){

        return queue.isEmpty();

    }

    public int size(){

        return queue.size();

    }

}