package com.zapphon.liveevents.managers;

import com.zapphon.liveevents.actions.Action;

public class EventManager {

    public void execute(Action action) {

        action.execute();

    }

}