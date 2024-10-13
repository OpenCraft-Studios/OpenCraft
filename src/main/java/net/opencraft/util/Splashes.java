package net.opencraft.util;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class Splashes {

	@Nonnull
	private List<String> splashes = List.of("missigno");
	
	public void load(Reader reader) {
		try (BufferedReader br = new BufferedReader(reader)) {
		    List<String> lines = br.lines().collect(Collectors.toList());
		    splashes = new ArrayList<>(lines);
		} catch (IOException e) {
		    System.err.println("Failed to load splashes!");
		}
	}

	public void load(InputStream in) {
		load(new InputStreamReader(in));
	}
	
	public void load(URI uri) {
		throw new UnsupportedOperationException();
	}
	
	/* Pick Random overload */
	public String pickRandom() {
		return pickRandom(Math::random);
	}
	
	public String pickRandom(Random random) {
		return pickRandom(size -> random.nextInt(size));
	}
	
	public String pickRandom(Supplier<Double> rnd) {
		Function<Integer, Integer> func = size -> (int) (rnd.get() * size);
		return pickRandom(func);
	}
	
	public String pickRandom(Function<Integer, Integer> rnd) {
		int len = splashes.size();
		if (len == 1)
			return splashes.get(0);
		
		return splashes.get(rnd.apply(len));
	}
	
}
