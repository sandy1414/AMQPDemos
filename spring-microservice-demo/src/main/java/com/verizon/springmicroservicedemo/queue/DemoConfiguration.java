package com.verizon.springmicroservicedemo.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;


@Configuration
public class DemoConfiguration {
	
	@Bean(name= "directQueue")
	public org.springframework.amqp.core.Queue queue() {
		return new org.springframework.amqp.core.Queue("directQueue");
	}
	
	@Bean(name= "fanoutQueue1")
	public org.springframework.amqp.core.Queue fanoutQueue1() {
		return new org.springframework.amqp.core.Queue("fanoutQueue1");
	}
	

	@Bean(name= "fanoutQueue2")
	public org.springframework.amqp.core.Queue fanoutQueue2() {
		return new org.springframework.amqp.core.Queue("fanoutQueue2");
	}
	
	@Bean(name= "topicQueue")
	public org.springframework.amqp.core.Queue topicQueue() {
		return new org.springframework.amqp.core.Queue("topicQueue");
	}
	
	
	@Bean
	 public DirectExchange exchange() {
		return new DirectExchange("directExchange");
	}
	
	@Bean
	 public TopicExchange topicExchange() {
		return new TopicExchange("topicExchange");
	}
	

	@Bean
	 public FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}
	
	@Bean(name = "directBinding")
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("directRoutingKey");
	}
	
	@Bean(name= "topicBinding")
	public Binding topicBinding() {
		return BindingBuilder.bind(topicQueue()).to(topicExchange()).with("*.topic.*");
	}
	

	@Bean(name= "fanoutBinding1")
	public Binding fanoutBinding1() {
		return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
	}
	

	@Bean(name= "fanoutBinding2")
	public Binding fanoutBinding2() {
		return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
	}
	
	
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
	@Bean
	public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
		
	}
	
}
