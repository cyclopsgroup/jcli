package org.cyclopsgroup.jcli;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * A test to go over multi action use case
 */
public class MultiActionTest {
	/**
	 * Happy case of multi actions.
	 */
	@Test
	public void testMultiAction() throws InstantiationException, IllegalAccessException {
		Map<String, Class<?>> clis = new HashMap<String, Class<?>>();
		clis.put("nocli", WithoutCli.class);
		clis.put("simple", WithSimpleArgument.class);

		Class<?> type = clis.get("nocli");
		WithoutCli nocli = (WithoutCli) type.newInstance();
		ArgumentProcessor.newInstance(nocli)
				.process(Arrays.asList("-a", "aaaaa"), nocli);
		assertEquals("aaaaa", nocli.optionA);
	}
}
