package br.com.caelum.vraptor.simplevalidator;

import java.util.List;

public class ValidationStrategies {
	
	public static DefaultValidationStrategy<List> notEmpty(){
		return new DefaultValidationStrategy<List>() {
			@Override
			public void addErrors(List list) {
				if(list.isEmpty()) addError();
			}
		};
	}
	
	public static DefaultValidationStrategy<String> matches(final String matchingString){
		return new DefaultValidationStrategy<String>() {
			@Override
			public void addErrors(String string) {
				if(string == null || matchingString == null || !string.equals(matchingString)) addError();
			}
		};
	}

	public static DefaultValidationStrategy<String> notEmptyNorNull() {
		return new DefaultValidationStrategy<String>() {
			@Override
			public void addErrors(String string) {
				if(string == null || string.isEmpty()) addError();
			}
		};
	}
	
	public static DefaultValidationStrategy<Object> notNull() {
		return new DefaultValidationStrategy<Object>() {
			@Override
			public void addErrors(Object obj) {
				if(obj== null) addError();
			}
		};
	}
	
	
}
