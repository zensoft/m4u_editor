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
	
}
