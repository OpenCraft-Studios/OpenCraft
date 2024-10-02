
package net.opencraft.client.input;

import org.joml.Vector2f;

import net.opencraft.client.config.GameSettings;
import net.opencraft.entity.EntityPlayer;

public class MovementInputFromOptions extends MovementInput {

    private boolean[] e;
    private GameSettings f;

    public MovementInputFromOptions(final GameSettings ja) {
        this.e = new boolean[10];
        this.f = ja;
    }

    @Override
    public void checkKeyForMovementInput(final int integer, final boolean boolean2) {
        int n = -1;
        if (integer == this.f.keyBindForward.keyCode) {
            n = 0;
        }
        if (integer == this.f.keyBindBack.keyCode) {
            n = 1;
        }
        if (integer == this.f.keyBindLeft.keyCode) {
            n = 2;
        }
        if (integer == this.f.keyBindRight.keyCode) {
            n = 3;
        }
        if (integer == this.f.keyBindJump.keyCode) {
            n = 4;
        }
        if (n >= 0) {
            this.e[n] = boolean2;
        }
    }

    @Override
    public void resetKeyState() {
        for (int i = 0; i < 10; ++i) {
            this.e[i] = false;
        }
    }

    @Override
    public void updatePlayerMoveState(final EntityPlayer gi) {
        Vector2f movement = new Vector2f(0.0f, 0.0f);

        if (this.e[0]) {
            movement.x += 1.0f;
        }
        if (this.e[1]) {
            movement.x -= 1.0f;
        }
        if (this.e[2]) {
            movement.y += 1.0f;
        }
        if (this.e[3]) {
            movement.y -= 1.0f;
        }

        if (movement.lengthSquared() > 0) {
            movement.normalize();
        }

        this.moveForward = movement.x;
        this.moveStrafe = movement.y;

        this.jump = this.e[4];
    }
}