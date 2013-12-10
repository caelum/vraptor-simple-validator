package br.com.caelum.vraptor.simplevalidator;


public class FakeStrategy implements ValidationStrategyHelper {

	private boolean invalid;

	public boolean isInvalid() {
		return invalid;
	}

	@Override
	public void addError(String message, Object... parameters) {
		invalid = true;
	}

	@Override
	public void addAlert(String message, Object... parameters) {
		invalid = true;
	}

	@Override
	public void addConfirmation(String message, Object[] parameters) {
	}
}