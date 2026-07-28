package com.starbox.engine.collision;

import com.starbox.engine.GameEvent;
import com.starbox.engine.GameEventType;
import com.starbox.engine.entities.*;
import com.starbox.engine.entities.enemy.Enemy;
import com.starbox.engine.entities.player.Player;
import com.starbox.engine.entities.player.Powerup;

import java.util.ArrayList;
import java.util.List;


/**
 * Collides stuff
 */
public final class CollisionSystem {

    private CollisionSystem() {
        // no instances
    }

    /**
     * Marks colliding bullets/enemies as dead and damages the player on
     * enemy contact.
     *
     * @return the score gained from any enemies destroyed this tick
     */
    public static CollisionResult resolve(Player player, List<Bullet> bullets, List<Enemy> enemies) {

        int scoreGained = 0;
        var events = new ArrayList<GameEvent>();


        for (Bullet bullet : bullets) {

            // Enemy bullets vs player
            if (bullet.getOwner() != Bullet.Owner.PLAYER || !bullet.isAlive()) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    bullet.kill();
                    events.add(new GameEvent(
                            GameEventType.PLAYER_HIT,
                            bullet.getX() + bullet.getWidth() / 2.0,
                            bullet.getY() + bullet.getHeight() / 2.0));

                    if(player.damage(bullet.getDamage())) {
                        events.add(new GameEvent(
                                GameEventType.PLAYER_KILLED,
                                bullet.getX() + bullet.getWidth() / 2.0,
                                bullet.getY() + bullet.getHeight() / 2.0));
                    }
                }
            }

            // Player bullet vs enemies
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bullet.kill();
                    events.add(new GameEvent(
                            GameEventType.ENEMY_HIT,
                            bullet.getX() + bullet.getWidth() / 2.0,
                            bullet.getY() + bullet.getHeight() / 2.0));

                    if (enemy.damage(bullet.getDamage())) {
                        scoreGained += enemy.getScoreValue();
                        events.add(new GameEvent(
                                GameEventType.ENEMY_KILLED,
                                bullet.getX() + bullet.getWidth() / 2.0,
                                bullet.getY() + bullet.getHeight() / 2.0));
                    }
                    break;
                }
            }
        }

        // Enemy bullets vs player


        // Enemies vs player (simple contact damage)
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            if (enemy.getBounds().intersects(player.getBounds())) {
                player.damage(enemy.getDamage());

                if (enemy.diesOnPlayerContact()) {
                    enemy.kill();
                    events.add(new GameEvent(
                            GameEventType.ENEMY_KILLED,
                            enemy.getX() + enemy.getWidth() / 2.0,
                            enemy.getY() + enemy.getHeight() / 2.0));
                }
            }
        }

        return new CollisionResult(scoreGained, events);
    }

    public static PickUpResult pickUpResolve(Player player, List<Powerup> powerups) {

        List<Powerup> collectedPowerUps = new ArrayList<>();

        for (Powerup powerup : powerups) {
            if (powerup.getBounds().intersects(player.getBounds())) {
                powerup.kill();
                collectedPowerUps.add(powerup);
            }
        }

        return new PickUpResult(collectedPowerUps);
    }
}
