package br.com.caelum.vraptor.simplevalidator;

public interface CustomValidationStrategy<T> extends ValidationStrategy<T>{
	public void addErrors(T t);
}
