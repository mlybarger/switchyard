package com.example.switchyard.cameltest;

import org.apache.camel.builder.RouteBuilder;

public class CamelServiceRoute extends RouteBuilder {

	/**
	 * The Camel route is configured via this method.  The from endpoint is required to be a SwitchYard service.
	 */
	
	//TODO: this should be configured via cli on jboss, not in the route itself.
	{
		// <path>x.panel</path>
		System.setProperty("x.panel", "queue/test1.out");
		// <path>y.panel</path>
		System.setProperty("y.panel", "queue/test2.out");
	}
	
	public void configure() {
		from("switchyard://CamelService").log(
				"Received message for 'CamelService' : ${body}")
				// add xpath filter when()...
				.setHeader("propName", xpath("/path").resultType(String.class))
				.recipientList(simple("jms:queue:${properties:${header.propName}}?connectionFactory=#activemq/ConnectionFactory"));
	}

}
 