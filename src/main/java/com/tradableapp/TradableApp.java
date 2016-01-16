package com.tradableapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.tradable.api.entities.Account;
import com.tradable.api.entities.Instrument;
import com.tradable.api.entities.Order;
import com.tradable.api.entities.OrderDuration;
import com.tradable.api.entities.OrderSide;
import com.tradable.api.entities.OrderStatus;
import com.tradable.api.entities.OrderType;
import com.tradable.api.services.account.AccountUpdateEvent;
import com.tradable.api.services.account.CurrentAccountService;
import com.tradable.api.services.account.CurrentAccountServiceListener;
import com.tradable.api.services.analytics.AccountAnalyticListener;
import com.tradable.api.services.analytics.AccountMetricsUpdateEvent;
import com.tradable.api.services.analytics.CurrentAccountAnalyticService;
import com.tradable.api.services.executor.OrderActionRequest;
import com.tradable.api.services.executor.OrderActionResponse;
import com.tradable.api.services.executor.OrderActionResult;
import com.tradable.api.services.executor.PlaceOrderAction;
import com.tradable.api.services.executor.PlaceOrderActionBuilder;
import com.tradable.api.services.executor.TradingRequest;
import com.tradable.api.services.executor.TradingRequestListener;
import com.tradable.api.services.executor.TradingResponse;
import com.tradable.api.services.instrument.InstrumentService;
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
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class TradableApp extends JPanel implements WorkspaceModule {
	
	private static final String TITLE = "Tradable App";
	DefaultTableModel model;
	private JTable table;
	
	@SuppressWarnings("serial")
	public TradableApp(QuoteTickService tickService, final CurrentAccountService currentAccountService, final CurrentAccountAnalyticService currentAccountAnalyticService, final InstrumentService instrument, 
					   final TradingRequestExecutorController requestExecutor) {
		setLayout(null);
		setSize(399, 381);
		putClientProperty(WorkspaceModuleProperties.COMPONENT_TITLE, TITLE);
		putClientProperty(WorkspaceModuleProperties.COMPONENT_RESIZE_ENABLED, false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 3, 400, 328);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Overview", null, scrollPane, null);
		add(tabbedPane);
		
		model = new DefaultTableModel(new Object[][] {},new String[] {"ID", "Rate", "Status"}) {
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(false);
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    table.setRowSorter(sorter);
	    
	    JPanel panel = new JPanel();
	    tabbedPane.addTab("Bot", null, panel, null);
	    panel.setLayout(null);
	    
	    JLabel lblNewLabel = new JLabel("DO SOME BOT STUFF HERE");
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
	    lblNewLabel.setBounds(48, 11, 298, 69);
	    panel.add(lblNewLabel);
	    
	    JPanel panel_1 = new JPanel();
	    tabbedPane.addTab("Log", null, panel_1, null);
	    panel_1.setLayout(null);
	    
	    JScrollPane scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(10, 11, 375, 272);
	    panel_1.add(scrollPane_1);
	    
	    final DefaultListModel listModel = new DefaultListModel();
	    listModel.addElement("Test1");
	    
	    JList list = new JList(listModel);
	    scrollPane_1.setViewportView(list);
		
		final JLabel test = new JLabel("<Orders>");
		test.setBounds(289, 352, 98, 14);
		add(test);
		
		// Titles
		JLabel lblAskTitle = new JLabel("Ask:");
		lblAskTitle.setBounds(10, 337, 70, 14);
		add(lblAskTitle);
		
		// Values
		final JLabel lblAsk = new JLabel("<Ask>");
		lblAsk.setBounds(20, 352, 60, 14);
		add(lblAsk);
		
		JLabel lblBidTitle = new JLabel("Bid:");
		lblBidTitle.setBounds(91, 337, 70, 14);
		add(lblBidTitle);
		
		final JLabel lblBid = new JLabel("<Bid>");
		lblBid.setBounds(101, 352, 60, 14);
		add(lblBid);
		
		JLabel lblBalanceTitle = new JLabel("Balance:");
		lblBalanceTitle.setBounds(178, 336, 79, 14);
		add(lblBalanceTitle);
		
		final JLabel lblBalance = new JLabel("<Balance>");
		lblBalance.setBounds(188, 352, 69, 14);
		add(lblBalance);
		
		JLabel lblActiveOrders = new JLabel("Active Orders:");
		lblActiveOrders.setBounds(274, 337, 108, 14);
		add(lblActiveOrders);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(85, 337, 7, 37);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(171, 337, 7, 37);
		add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(267, 337, 7, 37);
		add(separator_2);
		
	    JButton btnNewButton = new JButton("DO STUFF");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		listModel.addElement("Bot is doing stuff");
	    	}
	    });
	    btnNewButton.setBounds(10, 69, 375, 103);
	    panel.add(btnNewButton);
		
		
		JButton btnSetOrder = new JButton("Set order");
		btnSetOrder.setBounds(301, 36, 89, 23);
		add(btnSetOrder);
		
		/**
		 * 
		 * Quote Tick Subscription
		 *  
		**/
		final QuoteTickSubscription quoteTickSubscription = tickService.createSubscription();
		 
		quoteTickSubscription.setListener(new QuoteTickListener() {
		 
		  @Override
		  public void quotesUpdated(QuoteTickEvent event) {
		    for (String symbol : event.getSymbols()) {
		                Quote ask = quoteTickSubscription.getAsk(symbol);
		                Quote bid = quoteTickSubscription.getBid(symbol);
		                
		                lblAsk.setText(String.valueOf(ask.getPrice()));
		        	    listModel.addElement("Ask Was set to: " + ask.getPrice());
		                lblBid.setText(String.valueOf(bid.getPrice()));
		                listModel.addElement("Bid Was set to: " + ask.getPrice());
		      
		    }
		  }
		});
		 
		quoteTickSubscription.addSymbol("GBPUSD");
		
		/**
		 * 
		 * Account Metrics
		 * 
		**/
		currentAccountAnalyticService.addAccountAnalyticListener(new AccountAnalyticListener() {

		    @PreDestroy
		    public void unsubscribe() {
		    	currentAccountAnalyticService.removeAccountAnalyticListener(this);
		    }
			
			@Override
			public void accountMetricsChanged(AccountMetricsUpdateEvent event) {
				lblBalance.setText(String.valueOf(event.getAccountMetrics().getBalance()));
				listModel.addElement("Balance has changed to: " + event.getAccountMetrics().getBalance());
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
//					listModel.addElement("New Order with id: " + order.getOrderId() + " was added");
					
					if(order.getStatus() == OrderStatus.WORKING || order.getStatus() == OrderStatus.ACCEPTED || order.getStatus() == OrderStatus.NEW){
						orderArray.add(order);
					}
				}
				
				test.setText(String.valueOf(orderArray.size()));
			}
		});
	}

	private static final long serialVersionUID = 1L;
	
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
