package br.com.caelum.vraptor.simplevalidator;

import java.util.List;

public class ValidationStrategies {
	
	public static <T> DefaultValidationStrategy<T> and(final DefaultValidationStrategy<T>...validations){
		return new AndValidationStrategy<T>(validations);
	}
	
	public static DefaultValidationStrategy<List> notEmpty(){
		return new DefaultValidationStrategy<List>() {
			@Override
			public boolean shouldAddError(List list) {
				return list.isEmpty();
			}
		};
	}
	
	public static DefaultValidationStrategy<String> matches(final String matchingString){
		return new DefaultValidationStrategy<String>() {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || matchingString == null || !string.equals(matchingString);
			}
		};
	}

	public static DefaultValidationStrategy<String> notEmptyNorNull() {
		return new DefaultValidationStrategy<String>() {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || string.isEmpty();
			}
		};
	}
	
	public static DefaultValidationStrategy<Object> notNull() {
		return new DefaultValidationStrategy<Object>() {
			@Override
			public boolean shouldAddError(Object obj) {
				return obj== null;
			}
		};
	}
	
	public static DefaultValidationStrategy<Long> lessThan(final Long bigger) {
		return new DefaultValidationStrategy<Long>() {
			@Override
			public boolean shouldAddError(Long number) {
				return number >= bigger; 
			}
		};
	}
	
	public static DefaultValidationStrategy<Long> biggerThan(final Long lesser) {
		return new DefaultValidationStrategy<Long>() {
			@Override
			public boolean shouldAddError(Long number) {
				return number <= lesser;
			}
		};
	}
	
	
}
