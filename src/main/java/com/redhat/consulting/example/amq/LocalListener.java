package com.redhat.consulting.example.amq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class LocalListener implements MessageListener {

	private Long total = 0l;

	public void onMessage(Message message) {
		try {
			System.out.println("Received " + ((TextMessage) message).getText());

			total++;
			
			System.out.println( "Total " + total);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}