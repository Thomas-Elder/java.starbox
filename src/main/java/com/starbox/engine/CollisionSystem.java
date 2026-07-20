package com.starbox.engine;

import com.starbox.engine.entities.Bullet;
import com.starbox.engine.entities.Enemy;
import com.starbox.engine.entities.Player;

import java.util.List;

/**
 * Resolves collisions between bullets, enemies, and the player for a
 * single tick. Pulled out of GameEngine so collision rules can be read,
 * tested, and changed independently of the rest of the update loop.
 */
public final class CollisionSystem {

    private static final int ENEMY_CONTACT_DAMAGE = 34;

    private CollisionSystem() {
        // no instances
    }

    /**
     * Marks colliding bullets/enemies as dead and damages the player on
     * enemy contact.
     *
     * @return the score gained from any enemies destroyed this tick
     */
    public static int resolve(Player player, List<Bullet> bullets, List<Enemy> enemies) {
        int scoreGained = 0;

        // Player bullets vs enemies
        for (Bullet bullet : bullets) {
            if (bullet.getOwner() != Bullet.Owner.PLAYER || !bullet.isAlive()) {
                continue;
            }
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bullet.kill();
                    enemy.kill();
                    scoreGained += enemy.getScoreValue();
                    break;
                }
            }
        }

        // Enemies vs player (simple contact damage)
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            if (enemy.getBounds().intersects(player.getBounds())) {
                enemy.kill();
                player.damage(ENEMY_CONTACT_DAMAGE);
            }
        }

        return scoreGained;
    }
}
