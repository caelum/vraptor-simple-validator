package br.com.caelum.vraptor.simplevalidator.message;

import static br.com.caelum.vraptor.simplevalidator.message.DefaultMessageHelper.CONFIRMATIONS_KEY;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.Message;

public class MessageHelperTest {

	private MockValidator validator;
	private MockResult result;
	private DefaultMessageHelper helper;

	@Before
	public void setUp() {
		validator = spy(new MockValidator());
		result = spy(new MockResult());
		MessageFactory factory = mock(MessageFactory.class);
		helper = new DefaultMessageHelper(validator, result, factory);
	}
	
	@Test
	public void should_add_message_into_result_only() {
		helper.addConfirmation("test").onResult();
		verify(result).include(matches(CONFIRMATIONS_KEY), any(Message.class));
		verify(validator, never()).add(any(Message.class));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void should_add_message_into_validator_only() {
		helper.addConfirmation("test").onValidator();
		verify(validator, atLeastOnce()).addAll(any(Collection.class));
		verify(result, never()).include(matches(CONFIRMATIONS_KEY), any(Message.class));
	}

}
