package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundEvent extends PlayerEvent {

    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SoundEvent(
            String viewer,
            GiftType gift,
            Player target,
            Sound sound,
            float volume,
            float pitch,
            int delayTicks
    ) {
        super(viewer, gift, target, delayTicks);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}