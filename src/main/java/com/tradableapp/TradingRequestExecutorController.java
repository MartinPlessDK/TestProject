package com.tradableapp;

import com.tradable.api.services.account.CurrentAccountService;
import com.tradable.api.services.instrument.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradingRequestExecutorController {

    @Autowired
    InstrumentService instrumentService;

    @Autowired
    TradingRequestExecutorController requestExecutor;

    @Autowired
    CurrentAccountService accountService;
}