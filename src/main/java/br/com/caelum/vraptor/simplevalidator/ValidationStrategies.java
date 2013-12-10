package br.com.caelum.vraptor.simplevalidator;

import java.util.List;

public class ValidationStrategies {
	
	public static <T> AbstractValidationStrategy<T> and(final AbstractValidationStrategy<T>...validations){
		return new AndValidationStrategy<T>(validations);
	}
	
	public static AbstractValidationStrategy<List> notEmpty(){
		return new AbstractValidationStrategy<List>() {
			@Override
			public boolean shouldAddError(List list) {
				return list.isEmpty();
			}
		};
	}
	
	public static AbstractValidationStrategy<String> matches(final String matchingString){
		return new AbstractValidationStrategy<String>() {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || matchingString == null || !string.equals(matchingString);
			}
		};
	}

	public static AbstractValidationStrategy<String> notEmptyNorNull() {
		return new AbstractValidationStrategy<String>() {
			@Override
			public boolean shouldAddError(String string) {
				return string == null || string.isEmpty();
			}
		};
	}
	
	public static AbstractValidationStrategy<Object> notNull() {
		return new AbstractValidationStrategy<Object>() {
			@Override
			public boolean shouldAddError(Object obj) {
				return obj== null;
			}
		};
	}
	
	public static AbstractValidationStrategy<Long> lessThan(final Long bigger) {
		return new AbstractValidationStrategy<Long>() {
			@Override
			public boolean shouldAddError(Long number) {
				return number >= bigger; 
			}
		};
	}
	
	public static AbstractValidationStrategy<Long> biggerThan(final Long lesser) {
		return new AbstractValidationStrategy<Long>() {
			@Override
			public boolean shouldAddError(Long number) {
				return number <= lesser;
			}
		};
	}
	
	
}
