package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.getDefaultKey;

import java.util.List;

public class ValidationStrategies {
	

	protected static final String DEFAULT_NOT_EMPTY_KEY = getDefaultKey("notEmpty");
	protected static final String DEFAULT_NOT_NULL_KEY = getDefaultKey("notNull");
	protected static final String DEFAULT_NOT_EMPTY_NOR_NULL_KEY = getDefaultKey("notEmptyNorNull");
	protected static final String DEFAULT_LESS_THAN_KEY = getDefaultKey("lessThan");
	protected static final String DEFAULT_BIGGER_THAN_KEY = getDefaultKey("biggerThan");
	protected static final String DEFAULT_EMAIL_KEY = getDefaultKey("email");
	protected static final String DEFAULT_MATCHES_KEY = getDefaultKey("matches");
	protected static final String DEFAULT_LENGTH_BIGGER_KEY = getDefaultKey("lengthBiggerThan");
	protected static final String DEFAULT_LENGTH_LESS_KEY = getDefaultKey("lengthLessThan");
	protected static final String DEFAULT_AND_KEY = getDefaultKey("and");

	public static <T> SimpleValidationStrategy<T> and(final SimpleValidationStrategy<T>... validations) {
		return new AndValidationStrategy<T>(DEFAULT_AND_KEY, validations);
	}

	public static SimpleValidationStrategy<List> notEmpty() {
		return new SimpleValidationStrategy<List>(DEFAULT_NOT_EMPTY_KEY) {
			@Override
			public boolean shouldAddError(List list) {
				return list.isEmpty();
			}
		};
	}

	public static SimpleValidationStrategy<String> matches(final String matchingString) {
		return new SimpleValidationStrategy<String>(DEFAULT_MATCHES_KEY, matchingString) {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || matchingString == null
						|| !string.equals(matchingString);
			}
		};
	}

	public static SimpleValidationStrategy<String> notEmptyNorNull() {
		return new SimpleValidationStrategy<String>(DEFAULT_NOT_EMPTY_NOR_NULL_KEY) {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || string.isEmpty();
			}

		};
	}

	public static SimpleValidationStrategy<Object> notNull() {
		return new SimpleValidationStrategy<Object>(DEFAULT_NOT_NULL_KEY) {
			@Override
			public boolean shouldAddError(Object obj) {
				return obj == null;
			}
		};
	}

	public static SimpleValidationStrategy<Long> lessThan(final Long bigger) {
		return new SimpleValidationStrategy<Long>(DEFAULT_LESS_THAN_KEY, bigger) {
			
			@Override
			public boolean shouldAddError(Long number) {
				return number >= bigger;
			}
		};
	}

	public static SimpleValidationStrategy<Long> biggerThan(final Long lesser) {
		return new SimpleValidationStrategy<Long>(DEFAULT_BIGGER_THAN_KEY, lesser) {
			@Override
			public boolean shouldAddError(Long number) {
				return number <= lesser;
			}
		};
	}

	public static SimpleValidationStrategy<String> lengthLessThan(final Long bigger) {
		return new SimpleValidationStrategy<String>(DEFAULT_LENGTH_LESS_KEY, bigger) {
			@Override
			public boolean shouldAddError(String string) {
				return string != null && string.length() >= bigger;
			}
		};
	}

	public static SimpleValidationStrategy<String> lengthBiggerThan(final Long lesser) {
		return new SimpleValidationStrategy<String>(DEFAULT_LENGTH_BIGGER_KEY, lesser) {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || string.length() <= lesser;
			}
		};
	}

	public static SimpleValidationStrategy<String> email() {
		return new SimpleValidationStrategy<String>(getDefaultKey("email")) {

			@Override
			protected boolean shouldAddError(String email) {
				String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
						+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				return  email== null || !email.matches(regex);
			}
		};
	}
	
	protected static String getDefaultKey(String key) {
		return ValidationStrategies.class.getSimpleName()+"."+key;
	}

}
