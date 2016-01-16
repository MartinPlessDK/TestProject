package com.tradableapp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.tradable.api.entities.Account;
import com.tradable.api.entities.Order;
import com.tradable.api.entities.OrderStatus;
import com.tradable.api.services.account.AccountUpdateEvent;
import com.tradable.api.services.account.CurrentAccountService;
import com.tradable.api.services.account.CurrentAccountServiceListener;
import com.tradable.api.services.analytics.AccountAnalyticListener;
import com.tradable.api.services.analytics.AccountMetricsUpdateEvent;
import com.tradable.api.services.analytics.CurrentAccountAnalyticService;
import com.tradable.api.services.marketdata.Quote;
import com.tradable.api.services.marketdata.QuoteTickEvent;
import com.tradable.api.services.marketdata.QuoteTickListener;
import com.tradable.api.services.marketdata.QuoteTickService;
import com.tradable.api.services.marketdata.QuoteTickSubscription;
import com.tradable.ui.workspace.WorkspaceModule;
import com.tradable.ui.workspace.WorkspaceModuleProperties;
import com.tradable.ui.workspace.state.PersistedStateHolder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TradableApp extends JPanel implements WorkspaceModule {
	
	private static final String TITLE="Hello World";
	DefaultTableModel model;
	
	@SuppressWarnings("serial")
	public TradableApp(QuoteTickService tickService, final CurrentAccountService currentAccountService, final CurrentAccountAnalyticService currentAccountAnalyticService) {
		setLayout(null);
		setSize(400, 400);
		putClientProperty(WorkspaceModuleProperties.COMPONENT_TITLE, TITLE);
		putClientProperty(WorkspaceModuleProperties.COMPONENT_RESIZE_ENABLED, false);
		
		// Values
		final JLabel lblAsk = new JLabel("ASK");
		lblAsk.setBounds(10, 25, 230, 24);
		add(lblAsk);
		
		final JLabel lblBid = new JLabel("BID");
		lblBid.setBounds(10, 72, 230, 14);
		add(lblBid);
		
		final JLabel lblBalance = new JLabel("BALANCE");
		lblBalance.setBounds(10, 122, 230, 14);
		add(lblBalance);
		
		// Titles
		JLabel lblAskTitle = new JLabel("Ask:");
		lblAskTitle.setBounds(10, 11, 230, 14);
		add(lblAskTitle);
		
		JLabel lblBidTitle = new JLabel("Bid:");
		lblBidTitle.setBounds(10, 53, 230, 14);
		add(lblBidTitle);
		
		JLabel lblBalanceTitle = new JLabel("Balance:");
		lblBalanceTitle.setBounds(10, 97, 230, 14);
		add(lblBalanceTitle);
		
		final JLabel test = new JLabel("New label");
		test.setBounds(344, 11, 46, 14);
		add(test);
		
		model = new DefaultTableModel(new Object[][] {
			},
			new String[] {
				"ID", "Rate", "Status"
			}) {
			public boolean isCellEditable(int row, int column){return false;
			}
		};
		
		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		
		table.setBounds(10, 147, 380, 242);
		add(table);
		
		// Quote Tick Subscription
		final QuoteTickSubscription quoteTickSubscription = tickService.createSubscription();
		 
		quoteTickSubscription.setListener(new QuoteTickListener() {
		 
		  @Override
		  public void quotesUpdated(QuoteTickEvent event) {
		    for (String symbol : event.getSymbols()) {
		                Quote ask = quoteTickSubscription.getAsk(symbol);
		                Quote bid = quoteTickSubscription.getBid(symbol);
		                
		                lblAsk.setText(String.valueOf(ask.getPrice()));
		                lblBid.setText(String.valueOf(bid.getPrice()));
		      
		    }
		  }
		});
		 
		quoteTickSubscription.addSymbol("GBPUSD");
		
		// Account Metrics
		
		currentAccountAnalyticService.addAccountAnalyticListener(new AccountAnalyticListener() {

		    @PreDestroy
		    public void unsubscribe() {
		    	currentAccountAnalyticService.removeAccountAnalyticListener(this);
		    }
			
			@Override
			public void accountMetricsChanged(AccountMetricsUpdateEvent event) {
				lblBalance.setText(String.valueOf(event.getAccountMetrics().getBalance()));
			}
		});
		
		// Current Account Service
		
		currentAccountService.addListener(new CurrentAccountServiceListener() {
			
			@Override
			public void accountUpdated(AccountUpdateEvent event) {
				Account account = currentAccountService.getCurrentAccount();
				
				List<Order> orders = account.getOrders();
				ArrayList<Order> orderArray = new ArrayList<Order>();
				
				if (model.getRowCount() > 0) {
				    for (int i = model.getRowCount() - 1; i > -1; i--) {
				    	model.removeRow(i);
				    }
				}
				
				for (Order order : orders) {
					model.addRow(new Object[]{order.getOrderId(), order.getLimitPrice(), order.getStatus()});
					
					if(order.getStatus() == OrderStatus.WORKING || order.getStatus() == OrderStatus.ACCEPTED || order.getStatus() == OrderStatus.NEW){
						orderArray.add(order);
					}
				}

				test.setText(String.valueOf(orderArray.size()));
			}
		});
		
		
	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	public static void main(String[] args){
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersistedStateHolder getPersistedState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getVisualComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void loadPersistedState(PersistedStateHolder arg0) {
		// TODO Auto-generated method stub
		
	}
}
