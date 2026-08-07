package com.zapphon.liveevents.core.presets;

import com.zapphon.liveevents.core.engine.LiveEngine;
import com.zapphon.liveevents.core.events.LightningEvent;
import com.zapphon.liveevents.core.events.MobSpawnEvent;
import com.zapphon.liveevents.core.events.ParticleEvent;
import com.zapphon.liveevents.core.events.SoundEvent;
import com.zapphon.liveevents.core.events.TitleEvent;
import com.zapphon.liveevents.core.events.TntEvent;
import com.zapphon.liveevents.core.events.WolfFollowEvent;
import com.zapphon.liveevents.models.GiftType;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PresetExecutor {

    private final LiveEngine engine;

    public PresetExecutor(LiveEngine engine) {
        this.engine = engine;
    }

    public boolean execute(Preset preset, String viewerName, Player target) {

        if (preset == null || target == null) {
            return false;
        }

        for (PresetEvent event : preset.getEvents()) {

            String type = event.getType().toLowerCase();
            int delay = event.getInt("delay", 0);

            switch (type) {

                case "spawn_mob" -> {
                    try {
                        EntityType entityType = EntityType.valueOf(
                                event.getString("mob", "ZOMBIE").toUpperCase()
                        );

                        engine.submit(
                                new MobSpawnEvent(
                                        viewerName,
                                        GiftType.CUSTOM,
                                        target,
                                        entityType,
                                        event.getInt("amount", 1),
                                        delay,
                                        event.getString("armor", null)
                                )
                        );
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                case "tamed_wolf", "follow_wolf" -> engine.submit(
                        new WolfFollowEvent(
                                viewerName,
                                GiftType.FOLLOW,
                                target,
                                delay
                        )
                );

                case "tnt" -> engine.submit(
                        new TntEvent(
                                viewerName,
                                GiftType.CUSTOM,
                                target,
                                event.getInt("amount", 1),
                                delay
                        )
                );

                case "lightning" -> engine.submit(
                        new LightningEvent(
                                viewerName,
                                GiftType.CUSTOM,
                                target,
                                event.getInt("amount", 1),
                                delay
                        )
                );

                case "title" -> engine.submit(
                        new TitleEvent(
                                viewerName,
                                GiftType.CUSTOM,
                                target,
                                substituirVariaveis(
                                        event.getString("title", "Presente recebido!"),
                                        viewerName
                                ),
                                substituirVariaveis(
                                        event.getString("subtitle", ""),
                                        viewerName
                                ),
                                delay
                        )
                );

                case "sound" -> {
                    try {
                        Sound sound = Sound.valueOf(
                                event.getString(
                                        "sound",
                                        "ENTITY_PLAYER_LEVELUP"
                                ).toUpperCase()
                        );

                        engine.submit(
                                new SoundEvent(
                                        viewerName,
                                        GiftType.CUSTOM,
                                        target,
                                        sound,
                                        1.0F,
                                        1.0F,
                                        delay
                                )
                        );
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                case "particle" -> {
                    try {
                        Particle particle = Particle.valueOf(
                                event.getString("particle", "HEART").toUpperCase()
                        );

                        engine.submit(
                                new ParticleEvent(
                                        viewerName,
                                        GiftType.CUSTOM,
                                        target,
                                        particle,
                                        event.getInt("amount", 20),
                                        delay
                                )
                        );
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                default -> {
                }
            }
        }

        return true;
    }

    private String substituirVariaveis(String text, String viewerName) {
        return text.replace(
                "{viewer}",
                viewerName == null ? "Espectador" : viewerName
        );
    }
}