package br.com.caelum.vraptor.simplevalidator;


public class FakeMessageHelper implements MessageHelper{
	
	private boolean invalid;

	@Override
	public MessageHelper addConfirmation(String message,
			Object... parameters) {
		return this;
	}

	@Override
	public MessageHelper addAlert(String message, Object... parameters) {
		this.invalid = true;
		return this;
	}

	@Override
	public MessageHelper addError(String message, Object... parameters) {
		this.invalid = true;
		return this;
	}

	@Override
	public void onResult() {
	}

	@Override
	public void onValidator() {
	}

	public boolean isInvalid() {
		return invalid;
	}
}
