package br.com.caelum.vraptor.simplevalidator;

import java.util.List;

public class ValidationStrategies {

	public static <T> SimpleValidationStrategy<T> and(final SimpleValidationStrategy<T>... validations) {
		return new AndValidationStrategy<T>(validations);
	}

	public static SimpleValidationStrategy<List> notEmpty() {
		return new SimpleValidationStrategy<List>(getDefaultKey("notEmpty")) {
			@Override
			public boolean shouldAddError(List list) {
				return list.isEmpty();
			}
		};
	}

	public static SimpleValidationStrategy<String> matches(final String matchingString) {
		return new SimpleValidationStrategy<String>(getDefaultKey("matches"), matchingString) {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || matchingString == null
						|| !string.equals(matchingString);
			}
		};
	}

	public static SimpleValidationStrategy<String> notEmptyNorNull() {
		return new SimpleValidationStrategy<String>(getDefaultKey("notEmptyNorNull")) {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || string.isEmpty();
			}

		};
	}

	public static SimpleValidationStrategy<Object> notNull() {
		return new SimpleValidationStrategy<Object>(getDefaultKey("notNull")) {
			@Override
			public boolean shouldAddError(Object obj) {
				return obj == null;
			}
		};
	}

	public static SimpleValidationStrategy<Long> lessThan(final Long bigger) {
		return new SimpleValidationStrategy<Long>(getDefaultKey("lessThan"), bigger) {
			
			@Override
			public boolean shouldAddError(Long number) {
				return number >= bigger;
			}
		};
	}

	public static SimpleValidationStrategy<Long> biggerThan(final Long lesser) {
		return new SimpleValidationStrategy<Long>(getDefaultKey("biggerThan"), lesser) {
			@Override
			public boolean shouldAddError(Long number) {
				return number <= lesser;
			}
		};
	}

	public static SimpleValidationStrategy<String> lengthLessThan(final Long bigger) {
		return new SimpleValidationStrategy<String>(getDefaultKey("lengthLessThan"), bigger) {
			@Override
			public boolean shouldAddError(String string) {
				return string != null && string.length() >= bigger;
			}
		};
	}

	public static SimpleValidationStrategy<String> lengthBiggerThan(final Long lesser) {
		return new SimpleValidationStrategy<String>(getDefaultKey("lengthBiggerThan"), lesser) {
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
