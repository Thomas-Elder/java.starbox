package com.starbox.engine.entities;

import com.starbox.engine.entities.player.firing.MultiShotFiring;
import com.starbox.engine.entities.player.firing.SingleShotFiring;
import com.starbox.engine.entities.player.firing.SpreadShotFiring;
import com.starbox.engine.entities.player.firing.PlayerFiringBehavior;

import java.awt.*;

public enum PowerupType {
    MULTISHOT(new MultiShotFiring(0.18, 5.0, 6, 12, 240, 7), Color.GREEN),
    RAPIDFIRE(new SingleShotFiring(0.09, 5.0, 6, 10, 480 ), Color.GREEN),
    SPREADSHOT(new SpreadShotFiring(0.3, 5.0, 6, 12, 240, 7, 45), Color.GREEN);

    private final PlayerFiringBehavior grantedBehavior;
    private final Color color;

    PowerupType(PlayerFiringBehavior grantedBehavior, Color color) {
        this.grantedBehavior = grantedBehavior;
        this.color = color;
    }

    public PlayerFiringBehavior getGrantedBehavior() {
        return grantedBehavior;
    }

    public Color getColor(){
        return color;
    }
}
