package com.redhat.consulting.example.amq;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;


/**
 * This example performe AMQP protocol under PooledConnectionFactory
 * PooledConnectionFactory is not supported by Red Hat.
 * 
 * @see JmsPoolConnectionFactory
 * @see LocalListener.java
 * 
 * @author rosantos@redhat.com
 * 
 * AMQ 7 Broker and JmsPoolConnectionFactory supports JMS *2.0*. 
 * Actually JmsPoolConnectionFactory project was forked from the PooledConnectionFactory(org.apache.activemq/activemq-pool)
 * project  * and enhanced to provide JMS 2.0 functionality. 
 * All the functions of JMS 2.0 cannot be used if you use PooledConnectionFactory.
 * 
 * 
 */
public class AMQPPooledActiveMQ {

	private PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
	
	public LocalListener listener = new LocalListener();
	
	public void bootstrap() throws Exception {

		ConnectionFactory connectionFactory = new JmsConnectionFactory(
				"failover:(amqp://127.0.0.1:61616,amqp://127.0.0.1:61617,amqp://127.0.0.1:61618,amqp://127.0.0.1:61619)?initialReconnectDelay=100");

		pooledConnectionFactory.setConnectionFactory(connectionFactory);
		pooledConnectionFactory.setReconnectOnException(true);
		pooledConnectionFactory.setMaximumActiveSessionPerConnection(50);
		pooledConnectionFactory.setMaxConnections(50);
		pooledConnectionFactory.setIdleTimeout(60000);
		pooledConnectionFactory.setUseAnonymousProducers(false);
		pooledConnectionFactory.start();
				
		Connection connection = null;
		for (int i = 0; i < 50; i++) {

			connection = pooledConnectionFactory.createConnection("admin", "1qaz@WSX");

			connection.start();

			for (int a = 0; a < 50; a++) {

				// Step 2. Create a session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				Queue queue = session.createQueue("addressTest");

				MessageConsumer consumer = session.createConsumer(queue);

				consumer.setMessageListener(listener);

			}

		}

	}

	public static void main(String[] args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Date start = new Date();
		
		AMQPPooledActiveMQ foo = new AMQPPooledActiveMQ();
		foo.bootstrap();

		Date end = new Date();

		System.out.println("Started"  + sdf.format(start) + " Fim: " + sdf.format(end));
		System.out.println("Total received messages: " + foo.listener.getTotal());
		
		
	}
	
}
