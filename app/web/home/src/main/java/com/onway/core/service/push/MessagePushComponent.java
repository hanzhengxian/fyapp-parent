package com.onway.core.service.push;

public interface MessagePushComponent {

	public void pushtoSingle(String alias, String title, String text)
			throws Exception;

	public void pushtoAPP(String title, String text) throws Exception;
}
