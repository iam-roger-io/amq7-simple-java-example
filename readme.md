
# amq7-simple-java-example

Este projeto demosntra a produção e o envio de mensagens para um broker Red Hat AMQ 7.

**AMQPQueueExample**

A classe AMQPQueueExample **produz** e consome uma mensagem texto em uma fila chamada **simpleQueue**

A fila pode ser criada no **broker.xml** do AMQ 7 com o bloco abaixo.

    <address  name="simpleQueue">
	    <anycast>
		    <queue  name="simpleQueue"  />
		</anycast>
    </address>

Utilize o método mains desta classe para testa-la.

**AMQPPooledJMS**
A classe AMQPPooledJMS 7 demonstra a criação de um pool utilizando as bibliotecas natias do AMQ 7 / Artemis.

    <dependency>
    	<groupId>org.messaginghub</groupId>
    	<artifactId>pooled-jms</artifactId>
    	<version>1.0.4.redhat-00004</version>
    </dependency> 

Utilize o método mains desta classe para testa-la.

**AMQPPooledActiveMQ**
A classe AMQPPooledActiveMQ demosntra a criação de um pool utilizando a biblioteca PooledConnectionFactory disponível na lib activemq-pool (abaixo):

    <dependency>
    	<groupId>org.apache.activemq</groupId>
    	<artifactId>activemq-pool</artifactId>
    	<version>5.11.0.redhat-630416</version>
    </dependency>

Esta lib é funcional, porém não suporta as novas funcionalidades do AMQ 7 / Artemis.

Utilize o método mains desta classe para testa-la.