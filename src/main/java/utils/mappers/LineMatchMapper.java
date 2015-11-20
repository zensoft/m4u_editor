package utils.mappers;

import java.util.function.Function;

public class LineMatchMapper implements Function<String, String> {

	private static final String EMPTY = "";
	private static final String COLON = "";
	
	private String countryCode;
	
	public LineMatchMapper(String countryCode) {
		super();
		this.countryCode = countryCode;
	}

	@Override
	public String apply(String line) {
		String countryCodeLowerCase = countryCode.toLowerCase();
		String countryCodeUpperCase = countryCode.toUpperCase();
		
		if (line.startsWith(countryCodeLowerCase)) {
			return line.replaceFirst(countryCodeLowerCase + COLON, EMPTY).trim();
		} else if (line.startsWith(countryCodeUpperCase)) {
			return line.replaceFirst(countryCodeUpperCase + COLON, EMPTY).trim();
		}
		return null;
	}
	
}
