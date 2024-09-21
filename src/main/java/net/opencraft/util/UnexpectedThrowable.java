
package net.opencraft.util;

public class UnexpectedThrowable {

    public final String description;
    public final Exception exception;

    public UnexpectedThrowable(final String string, final Exception exception) {
        this.description = string;
        this.exception = exception;
    }
}
