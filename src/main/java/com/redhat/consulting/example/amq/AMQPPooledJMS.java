package com.redhat.consulting.example.amq;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;


/**
 * This example performe AMQP protocol under JmsPoolConnectionFactory
 * 
 * 
 * @see LocalListener.java
 * 
 * AMQ 7 Broker and JmsPoolConnectionFactory supports JMS *2.0*. 
 * Actually JmsPoolConnectionFactory project was forked from the PooledConnectionFactory(org.apache.activemq/activemq-pool)
 * project  and enhanced to provide JMS 2.0 functionality. 
 * All the functions of JMS 2.0 cannot be used if you use PooledConnectionFactory.
 * 
 * @author rosantos@redhat.com
 *
 */
public class AMQPPooledJMS {

	private JmsPoolConnectionFactory jmsPollConnectionFactory = new JmsPoolConnectionFactory();
	
	public LocalListener listener = new LocalListener();
	
	public void bootstrap() throws Exception {

		ConnectionFactory connectionFactory = new JmsConnectionFactory(
				"failover:(amqp://127.0.0.1:61616,amqp://127.0.0.1:61617,amqp://127.0.0.1:61618,amqp://127.0.0.1:61619)?initialReconnectDelay=100&clientFailureCheckPeriod=10000");
		
		jmsPollConnectionFactory.setConnectionFactory(connectionFactory);	
		jmsPollConnectionFactory.setMaxConnections(50);
		jmsPollConnectionFactory.setUseAnonymousProducers(false);		
		jmsPollConnectionFactory.start();

		Connection connection = null;
		for (int i = 0; i < 1; i++) {

			connection = jmsPollConnectionFactory.createConnection("admin", "1qaz@WSX");
			connection.start();

			for (int a = 0; a < 50; a++) {

				// Step 2. Create a session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				Queue queue = session.createQueue("foo2");

				MessageConsumer consumer = session.createConsumer(queue);

				consumer.setMessageListener(listener);

			}

		}

	}

	public static void main(String[] args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Date start = new Date();
		
		AMQPPooledJMS foo = new AMQPPooledJMS();
		foo.bootstrap();

		Date end = new Date();

		System.out.println("Started"  + sdf.format(start) + " Fim: " + sdf.format(end));
		System.out.println("Total received messages: " + foo.listener.getTotal());
		
		
	}
	
}
