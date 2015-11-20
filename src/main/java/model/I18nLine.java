package model;

public class I18nLine {
	
	private String key;
	private String value;
	public I18nLine(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLine() {
		return key + "=" + value;
	}
	
	@Override
	public String toString() {
		return "I18nLine [key=" + key + ", value=" + value + "]";
	}
	
}
