
package net.opencraft.client.input;

import org.joml.Vector2f;

import net.opencraft.client.config.GameSettings;
import net.opencraft.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovementInput {

    public float moveStrafe;
    public float moveForward;
    public boolean jump;
    private GameSettings settings;
    private KeyboardInput keyboard;

    public enum Intent {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        JUMP
    }

    public MovementInput(final GameSettings settings, KeyboardInput keyboard) {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        this.jump = false;
        this.settings = settings;
        this.keyboard = keyboard;
    }

    public void updatePlayerMoveState() {
        Vector2f movement = new Vector2f(0.0f, 0.0f);

        if(isInputActive(GameSettings.PlayerInput.FORWARD)) {
            movement.x += 1.0f;
        }
        if(isInputActive(GameSettings.PlayerInput.BACKWARD)) {
            movement.x -= 1.0f;
        }
        if(isInputActive(GameSettings.PlayerInput.LEFT)) {
            movement.y += 1.0f;
        }
        if(isInputActive(GameSettings.PlayerInput.RIGHT)) {
            movement.y -= 1.0f;
        }
		jump = isInputActive(GameSettings.PlayerInput.JUMP);

        if (movement.lengthSquared() > 0) {
            movement.normalize();
        }

        moveForward = movement.x;
        moveStrafe = movement.y;
    }

    public boolean isInputActive(GameSettings.PlayerInput input) {
        return keyboard.pressedKeys.contains(settings.keyBindings.get(input));
    }
}