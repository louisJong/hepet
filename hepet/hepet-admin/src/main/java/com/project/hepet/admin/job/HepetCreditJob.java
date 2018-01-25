package com.project.hepet.admin.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.project.hepet.service.OrderService;

public class HepetCreditJob {

	@Autowired
	private OrderService orderService;

	@Scheduled(fixedRate = 20 * 60 * 1000)
<<<<<<< HEAD
	public void orderClose() {
=======
	public void order() {
>>>>>>> branch 'master' of https://github.com/louisJong/hepet.git
		orderService.closeOrder();
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void orderFinish() {
		orderService.orderFinish();
	}
}
