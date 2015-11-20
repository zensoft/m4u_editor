package utils.filters;

import java.util.function.Predicate;

public class DuplicatedLinesFilter implements Predicate<String>{

	private String lineKey; 
	
	public DuplicatedLinesFilter(String lineKey) {
		super();
		this.lineKey = lineKey;
	}

	@Override
	public boolean test(String line) {
		return !line.startsWith(lineKey + "=");
	}

}
