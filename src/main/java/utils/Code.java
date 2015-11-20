package utils;

public enum Code {

	CZ("cz"),HU("hu"),PL("pl"),RO("ro"),SK("sk"),UA("ua"),ALL("all");
	
	private final String code;

	private Code(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static Code[] iterableCodes = {CZ,HU,PL,RO,SK,UA};
	
	public static Code getFromString(String str) {
		str = str.toLowerCase();
		for (Code c : Code.values()) {
			if (c.getCode().equals(str))
				return c;
		}
		return null;
	}
	
}
