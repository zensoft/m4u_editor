package utils.filters;

import java.util.Arrays;
import java.util.function.Predicate;

import utils.Code;

public class CountryFilter implements Predicate<Code>{

	private Code[] choosedCountries;
	
	public CountryFilter(Code[] choosedCountries) {
		super();
		this.choosedCountries = choosedCountries;
	}

	private boolean isAllCountries() {
		return "all".equalsIgnoreCase(choosedCountries[0].getCode());
	}
	
	private boolean isCountryCodeInList(Code countryCode) {
		return Arrays.asList(choosedCountries).contains(countryCode);
	}
	
	@Override
	public boolean test(Code countryCode) {
		if (isAllCountries()) {
			return true;
		} else {
			return isCountryCodeInList(countryCode);
		}
	}

}
