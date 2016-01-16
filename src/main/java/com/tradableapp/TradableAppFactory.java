package com.tradableapp;
import org.springframework.beans.factory.annotation.Autowired;

import com.tradable.api.services.account.CurrentAccountService;
import com.tradable.api.services.analytics.CurrentAccountAnalyticService;
import com.tradable.api.services.marketdata.QuoteTickService;
import com.tradable.ui.workspace.WorkspaceModule;
import com.tradable.ui.workspace.WorkspaceModuleCategory;
import com.tradable.ui.workspace.WorkspaceModuleFactory;

//This is a test

public class TradableAppFactory implements WorkspaceModuleFactory {

	@Autowired
	protected QuoteTickService quoteTickService;
	@Autowired
	protected CurrentAccountService currentAccountService;
	@Autowired
	protected CurrentAccountAnalyticService currentAccountAnalyticService;
	
	@Override
	public WorkspaceModule createModule() {
		// TODO Auto-generated method stub
		
		return new TradableApp(quoteTickService, currentAccountService, currentAccountAnalyticService);
	}

	@Override
	public WorkspaceModuleCategory getCategory() {
		// TODO Auto-generated method stub
		return WorkspaceModuleCategory.MISCELLANEOUS;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return "FiskeFar Kommer efter dig!";
	}

	@Override
	public String getFactoryId() {
		// TODO Auto-generated method stub
		return "com.tradableapp.FiskeFar";
	}
	
}
