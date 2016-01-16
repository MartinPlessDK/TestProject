package com.tradableapp;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tradable.api.entities.Instrument;
import com.tradable.api.services.instrument.InstrumentUpdateEvent;
import com.tradable.api.services.instrument.InstrumentServiceListener;
import com.tradable.api.services.instrument.InstrumentService;

@Component
public class InstrumentController {

    @Autowired
    InstrumentService instrumentService;

    private InstrumentServiceListener listener = new InstrumentServiceListener() {

        @Override
        public void instrumentsUpdated(InstrumentUpdateEvent event) {
            Map<Integer, Instrument> instruments = event.getUpdatedInstruments();
            for (Entry<Integer, Instrument> entry : instruments.entrySet()) {
                System.out.println("Instrument received " + entry.getValue().getSymbol());
            }
        }

    };

    @PostConstruct
    public void init() {
        instrumentService.addListener(listener);
    }

    @PreDestroy
    public void unInit() {
        instrumentService.removeListener(listener);
    }
}
