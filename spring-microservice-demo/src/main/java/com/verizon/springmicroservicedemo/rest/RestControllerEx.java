package com.verizon.springmicroservicedemo.rest;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.springmicroservicedemo.model.Employee;


@RestController
@RequestMapping("/message")
public class RestControllerEx {
	
	@Autowired
	private RabbitTemplate template;
	
	@Value("${msg}")
	private String msg;
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private DirectExchange directExchange;
	
	@Autowired
	private TopicExchange topicExchange;
	
	@Autowired
	private FanoutExchange fanoutExchange;
	
	
	
	@RequestMapping
	public String getMessage() {
		template.convertAndSend(directExchange.getName(), "directRoutingKey", "This is a direct key exchange");
		return "Message is: " + msg + "and port is" + port;
	}
	
	@RequestMapping("/topicMessage")
	public String getTopicMessage() {
		Employee emp = new Employee();
		emp.setId(1);
		emp.setName("Test Name");
		template.convertAndSend(topicExchange.getName(), "one.topic.send", emp);
		return "Message is: " + msg + "and port is" + port;
	}
	
	@RequestMapping("/fanoutMessage")
	public String getFanoutMessage() {
		template.convertAndSend(fanoutExchange.getName(), "*", "This is a Fanout key exchange");
		return "Message is: " + msg + "and port is" + port;
	}
}
