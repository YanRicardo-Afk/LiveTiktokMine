package com.zapphon.liveevents.core.queues;

import com.zapphon.liveevents.core.events.BaseEvent;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {

    private final Queue<BaseEvent> queue =
            new LinkedList<>();

    public void add(BaseEvent event){

        queue.add(event);

    }

    public BaseEvent poll(){

        return queue.poll();

    }

    public boolean isEmpty(){

        return queue.isEmpty();

    }

    public int size(){

        return queue.size();

    }

}