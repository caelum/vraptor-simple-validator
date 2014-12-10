package br.com.caelum.vraptor.simplevalidator.strategy;


public interface CustomValidationStrategy<T> extends ValidationStrategy<T>{
	public void addErrors(T t);
}
