package com.huboyi.system.po;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

/**
 * 订单信息PO。
 * 
 * @author FrankTaylor <mailto:franktaylor@163.com>
 * @since 2014/12/30
 * @version 1.0
 */
public class OrderInfoPO {
	@Id
	/** id */
	private String id;
	/** 合同编号。*/
	private String contractCode;
	
	// --- 
	/** 系统名称。*/
	private String systemName;
	/** 证券代码。*/
	private String stockCode;
	/** 证券名称。*/
	private String stockName;
	/** 成交日期（格式：%Y%m%d）。*/
	private Integer tradeDate;
	/** 成交时间（详细时间）。*/
	private Long tradeTime;
	/** 买卖标志。*/
	private String tradeFlag;
	/**
	 * 买入时：成交金额 ==（交易总金额 + 手续费 + 印花税 + 过户费 + 结算费）。
	 * 卖出时：成交金额 ==（交易总金额 - 手续费 - 印花税 - 过户费 - 结算费）。
	 */
	private BigDecimal tradePrice;
	/** 成交数量。*/
	private Long tradeNumber;
	/** 成交金额。*/
	private BigDecimal tradeMoney;
	
	// --- 
	/** 股东代码。*/
	private String stockholder = "672288";
	
	@Override
	public String toString () {
		StringBuilder builder = new StringBuilder();
		builder.append("{ \n")
		.append("    ").append("id").append(":").append("'").append(id).append("'").append(", \n")
		.append("    ").append("contractCode").append(":").append("'").append(contractCode).append("'").append(", \n")
		
		// --- 
		.append("    ").append("systemName").append(":").append("'").append(systemName).append("'").append(", \n")
		.append("    ").append("stockCode").append(":").append("'").append(stockCode).append("'").append(", \n")
		.append("    ").append("stockName").append(":").append("'").append(stockName).append("'").append(", \n")
		.append("    ").append("tradeDate").append(":").append("'").append(tradeDate).append("'").append(", \n")
		.append("    ").append("tradeTime").append(":").append("'").append(tradeTime).append("'").append(", \n")
		.append("    ").append("tradeFlag").append(":").append("'").append(tradeFlag).append("'").append(", \n")
		.append("    ").append("tradePrice").append(":").append("'").append(tradePrice).append("'").append(", \n")
		.append("    ").append("tradeNumber").append(":").append("'").append(tradeNumber).append("'").append(", \n")
		.append("    ").append("tradeMoney").append(":").append("'").append(tradeMoney).append("'").append(", \n")
		
		// --- 
		.append("    ").append("stockholder").append(":").append("'").append(stockholder).append("'").append(", \n");
		builder.append("} \n");
		return builder.toString();
	}
	
	// --- get method and set method ---
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Integer getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Integer tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeFlag() {
		return tradeFlag;
	}

	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Long getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(Long tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	public BigDecimal getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(BigDecimal tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getStockholder() {
		return stockholder;
	}

	public void setStockholder(String stockholder) {
		this.stockholder = stockholder;
	}
}