package br.com.caelum.vraptor.simplevalidator.message;

public interface MessageHelper {

	MessageHelper addConfirmation(String message, Object...parameters);
	MessageHelper addAlert(String message, Object...parameters);
	MessageHelper addError(String message, Object...parameters);
	void onResult();
	void onValidator();

}
