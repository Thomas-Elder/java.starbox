package com.starbox.engine;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Enemy;
import com.starbox.engine.entities.Explosion;
import com.starbox.engine.entities.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Resolves collisions between bullets, enemies, and the player for a
 * single tick. Pulled out of GameEngine so collision rules can be read,
 * tested, and changed independently of the rest of the update loop.
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
        var explosions = new ArrayList<Explosion>();


        for (Bullet bullet : bullets) {

            // Enemy bullets vs player
            if (bullet.getOwner() != Bullet.Owner.PLAYER || !bullet.isAlive()) {
                if (bullet.getBounds().intersects(player.getBounds())) {
                    bullet.kill();
                    explosions.add(Explosion.centeredAt(
                            bullet.getX() + bullet.getWidth() / 2.0,
                            bullet.getY() + bullet.getHeight() / 2.0));
                    if(player.damage(bullet.getDamage())) {
                        explosions.add(Explosion.centeredAt(
                                player.getX() + player.getWidth() / 2.0,
                                player.getY() + player.getHeight() / 2.0,
                                player.getWidth()));
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
                    explosions.add(Explosion.centeredAt(
                            bullet.getX() + bullet.getWidth() / 2.0,
                            bullet.getY() + bullet.getHeight() / 2.0));

                    if (enemy.damage(bullet.getDamage())) {
                        scoreGained += enemy.getScoreValue();
                        explosions.add(Explosion.centeredAt(
                                enemy.getX() + enemy.getWidth() / 2.0,
                                enemy.getY() + enemy.getHeight() / 2.0,
                                        enemy.getWidth()));
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
                    explosions.add(Explosion.centeredAt(
                            enemy.getX() + enemy.getWidth() / 2.0,
                            enemy.getY() + enemy.getHeight() / 2.0));
                }
            }
        }

        return new CollisionResult(scoreGained, explosions);
    }
}
