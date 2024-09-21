
package net.opencraft.client.input;

import net.opencraft.entity.EntityPlayer;

public class MovementInput {

    public float moveStrafe;
    public float moveForward;
    public boolean field_1177_c;
    public boolean jump;

    public MovementInput() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        this.field_1177_c = false;
        this.jump = false;
    }

    public void updatePlayerMoveState(final EntityPlayer gi) {
    }

    public void resetKeyState() {
    }

    public void checkKeyForMovementInput(final int integer, final boolean boolean2) {
    }
}
