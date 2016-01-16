package com.tradableapp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradable.api.services.analytics.AccountAnalyticListener;
import com.tradable.api.services.analytics.AccountMetricsUpdateEvent;
import com.tradable.api.services.analytics.CurrentAccountAnalyticService;

public class AccountController implements AccountAnalyticListener {

	@Autowired
	private CurrentAccountAnalyticService service;
	
	@PostConstruct
    public void init() {
        service.addAccountAnalyticListener(this);
    }

    @PreDestroy
    public void unsubscribe() {
        service.removeAccountAnalyticListener(this);
    }
	
	@Override
	public void accountMetricsChanged(AccountMetricsUpdateEvent event) {
		System.out.println("Account metrics changed " + event);
	}
	
}
