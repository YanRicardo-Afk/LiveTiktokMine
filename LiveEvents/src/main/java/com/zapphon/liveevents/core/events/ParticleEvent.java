package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleEvent extends PlayerEvent {

    private final Particle particle;
    private final int amount;

    public ParticleEvent(
            String viewer,
            GiftType gift,
            Player target,
            Particle particle,
            int amount,
            int delayTicks
    ) {
        super(viewer, gift, target, delayTicks);
        this.particle = particle;
        this.amount = amount;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getAmount() {
        return amount;
    }
}