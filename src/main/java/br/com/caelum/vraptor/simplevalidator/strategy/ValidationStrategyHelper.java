package br.com.caelum.vraptor.simplevalidator.strategy;

public interface ValidationStrategyHelper {
	void addError(String message, Object...parameters);	
	void addAlert(String message, Object...parameters);
	void addConfirmation(String message, Object...parameters);
}
