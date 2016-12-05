package com.sjsu.masterproject.client;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.sjsu.masterproject.model.Customer;

@Component
public class CustomerManagementServiceClient extends OnlineStoreServiceClient {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	private static final String SIGN_IN_URI = "/customer/login";

	public Boolean signIn(Customer customer) throws Exception {
		log.info("signIn {}", customer);

		if (customer == null || StringUtils.isBlank(customer.getId()) || StringUtils.isBlank(customer.getPassword())) {
			throw new Exception("signIn: Customer info is invalid!");
		}

		Boolean isSignedIn = false;
		String serviceEndPoint = getBaseServiceUrl(env) + SIGN_IN_URI;

		log.info("signIn: Service End point: {}", serviceEndPoint);

		try {
			isSignedIn = getRestTemplate().postForObject(serviceEndPoint, customer, Boolean.class);
		} catch (Exception e) {
			log.error("Error while signing in: {}!", customer, e);
		}

		log.info("isSignedIn:: {}", isSignedIn);

		return isSignedIn;
	}
}