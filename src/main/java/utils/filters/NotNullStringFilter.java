package utils.filters;

import java.util.function.Predicate;

public class NotNullStringFilter implements Predicate<String>{

	@Override
	public boolean test(String t) {
		return t != null;
	}

}
