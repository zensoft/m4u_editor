package utils.filters;

import java.util.Collection;
import java.util.function.Predicate;

import model.I18nLine;

public class DuplicatedLinesFilter implements Predicate<String>{

	private Collection<I18nLine> collection; 
	
	public DuplicatedLinesFilter(Collection<I18nLine> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public boolean test(String line) {
		for (I18nLine i18nLine : collection) {
			if (line.startsWith(i18nLine.getKey() + "="))
				return false;
		}
		return true;
		//return !line.startsWith(lineKey + "=");
	}

}
