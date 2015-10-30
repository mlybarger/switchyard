package com.example.switchyard.cameltest;

import org.apache.camel.builder.RouteBuilder;

public class CamelServiceRoute extends RouteBuilder {

	/**
	 * The Camel route is configured via this method.  The from endpoint is required to be a SwitchYard service.
	 */
	
	//TODO: this gets configured on jboss, not in the route itself.
	{
		System.setProperty("x.panel.queue.name", "queue/test1.out");
		System.setProperty("y.panel.queue.name", "queue/test2.out");
	}
	
	public void configure() {
		from("switchyard://CamelService").log(
				"Received message for 'CamelService' : ${body}")
				// add xpath filter when()...
				.setHeader("propName", xpath("/path").resultType(String.class))
				.recipientList(simple("jms:queue:${properties:${header.propName}}?connectionFactory=#activemq/ConnectionFactory"));
	}

}
 