package net.opencraft.util;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
	
}
