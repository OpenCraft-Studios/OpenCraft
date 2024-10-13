package net.opencraft.client.config.option;

public class BooleanOption extends Option<Boolean> {

	private boolean value = false;

	public BooleanOption() {
	}

	public BooleanOption(boolean value) {
		set(value);
	}

	@Override
	public Boolean get() {
		return value;
	}

	@Override
	public void set(Boolean value) {
		this.value = value;
	}

	public void toggle() {
		set(!get());
	}

	public <T> T either(T a, T b) {
		return get() ? a : b;
	}

	public void parse(String str) {
		set(Boolean.parseBoolean(str));
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public int hashCode() {
		return Boolean.hashCode(value);
	}

}
