package com.project.hepet.admin.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.project.hepet.service.OrderService;

public class HepetCreditJob {

	@Autowired
	private OrderService orderService;

	@Scheduled(fixedRate = 1 * 60 * 1000)
	public void order() {
		orderService.closeOrder();
	}
}
