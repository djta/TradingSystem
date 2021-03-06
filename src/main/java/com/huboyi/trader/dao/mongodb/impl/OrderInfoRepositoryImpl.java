//package com.huboyi.position.dao.mongodb.impl;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.mongodb.core.IndexOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.index.Index;
//import org.springframework.data.mongodb.core.index.IndexInfo;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//
//import com.huboyi.position.dao.OrderInfoRepository;
//import com.huboyi.position.po.OrderInfoPO;
//
//
///**
// * 订单信息DAO实现类。
// * 
// * @author FrankTaylor <mailto:franktaylor@163.com>
// * @since 1.0
// */
//public class OrderInfoRepositoryImpl implements OrderInfoRepository {
//	
//	/** 日志。*/
//	private final Logger log = Logger.getLogger(OrderInfoRepositoryImpl.class);
//	
//	/** Spring Data MongoTemplate。*/
//	@Autowired
//	@Qualifier("mongoTemplate")
//	private MongoTemplate mongoTemplate;
//	
//	@Override
//	public void createIndex(String stockCode) {
//		StringBuilder logMsg = new StringBuilder();
//		logMsg.append("invoke createIndex method").append("\n");
//		logMsg.append("@param [stockCode = " + stockCode + "]");
//		log.info(logMsg.toString());
//		
//		try {
//			IndexOperations indexOps = mongoTemplate.indexOps(getDocCollectionName(stockCode));
//			
//			/*
//			 * 1.删除该集合上的所有索引。
//			 * 原生语句：db.orderInfo_123456.dropIndexes();
//			 */
//			indexOps.dropAllIndexes();
//			
//			/*
//			 * 2.在该集合上建立复合索引，且该索引还是唯一和稀疏的。
//			 * 原生语句：db.orderInfo_123456.ensureIndex({"tradeDate" : 1}, {"unique" : true, "dropDups" : true, "sparse" : true, "name" : "tradeDate", "background" : 1})
//			 */
//			String indexName = "tradeDate";
//			indexOps.ensureIndex(
//					new Index()
//					.on("tradeDate", Direction.ASC)
////					.unique(Duplicates.DROP) Dropping Duplicates was removed in MongoDB Server 2.8.0-rc0.
//					.sparse()
//					.named(indexName)
//					.background()
//			);
//			
//			/*
//			 * 3.验证索引是否建立成功。
//			 * 原生语句：db.orderInfo_123456.getIndexes();
//			 */
//			boolean isSuccessEnsureIndex = false;
//			List<IndexInfo> indexInfoList = indexOps.getIndexInfo();
//			for (IndexInfo info : indexInfoList) {
//				if (info.getName().equals(indexName)) {
//					isSuccessEnsureIndex = true;
//				}
//			}
//			if (isSuccessEnsureIndex) {				
//				log.info("创建  [证券代码 = " + stockCode + "] 订单信息记录的索引成功。");
//			} else {
//				throw new RuntimeException();
//			}
//			
//		} catch (Exception e) {
//			String errorMsg = "创建  [证券代码 = " + stockCode + "] 订单信息记录的索引失败!";
//			log.error(errorMsg, e);
//			throw new RuntimeException(errorMsg, e);
//		}
//	}
//	
//	@Override
//	public void insert(OrderInfoPO po) {
//		StringBuilder logMsg = new StringBuilder();
//		logMsg.append("invoke insert method").append("\n");
//		logMsg.append("@param [po = " + po + "]");
//		log.info(logMsg.toString());
//		
//		try {			
//			mongoTemplate.insert(po, getDocCollectionName(po.getStockCode()));
//			log.info("插入 [证券代码 = " + po.getStockCode() + "] 的订单信息记录成功。");
//		} catch (Throwable e) {
//			String errorMsg = "插入 [证券代码 = " + po.getStockCode() + "] 的订单信息记录失败!";
//			log.error(errorMsg, e);
//			throw new RuntimeException(errorMsg, e);
//		}
//	}
//	
//	@Override
//	public OrderInfoPO findNewOne(String stockCode) {
//		StringBuilder logMsg = new StringBuilder();
//		logMsg.append("invoke findNewOne method").append("\n");
//		logMsg.append("@param [stockCode = " + stockCode + "]");
//		log.info(logMsg.toString());
//		
//		try {
//			/*
//			 * 原生语句：db.orderInfo_123456.find().sort({tradeDate : -1}).limit(1);
//			 */
//			Query query = new Query();
//			query.with(new Sort(Direction.DESC, "tradeDate"));
//			query.limit(1);
//			
//			List<OrderInfoPO> poList = mongoTemplate.find(query, OrderInfoPO.class, getDocCollectionName(stockCode));
//			
//			log.info("查询  [证券代码 = " + stockCode + "] 最近的一条订单记录成功。");
//			
//			if (poList != null && poList.size() == 1) {
//				return poList.get(0);
//			} else {
//				return null;
//			}			
//		} catch (Throwable e) {
//			String errorMsg = "查询 [证券代码 = " + stockCode + "] 最近的一条订单记录失败!";
//			log.error(errorMsg, e);
//			throw new RuntimeException(errorMsg, e);
//		}
//	}
//	
//	@Override
//	public List<OrderInfoPO> findOrderInfoList(String stockCode, Integer beginTradeDate, Integer endTradeDate, Integer beginPage, Integer endPage) {
//		StringBuilder logMsg = new StringBuilder();
//		logMsg.append("invoke findOrderInfoList method").append("\n");
//		logMsg.append("@param [stockCode = " + stockCode + "]").append("\n");
//		logMsg.append("@param [beginTradeDate = " + beginTradeDate + "]").append("\n");
//		logMsg.append("@param [endTradeDate = " + endTradeDate + "]").append("\n");
//		logMsg.append("@param [beginPage = " + beginPage + "]").append("\n");
//		logMsg.append("@param [endPage = " + endPage + "]").append("\n");
//		log.info(logMsg.toString());
//		
//		try {
//			/*
//			 * 原生语句：
//			 * db.orderInfo_123456
//			 * .find({"tradeDate" : {"$gte" : 20100101, "$lte" : 20151212}})
//			 * .sort({tradeDate : 1})
//			 * .hint('tradeDate')
//			 * .skip(10)
//			 * .limit(10);
//			 */
//			
//			Query query = new Query();
//			if (beginTradeDate != null && endTradeDate != null) {				
//				query.addCriteria(Criteria.where("tradeDate").gte(beginTradeDate).lte(endTradeDate));
//			} else {
//				if (beginTradeDate != null) {				
//					query.addCriteria(Criteria.where("tradeDate").gte(beginTradeDate));
//				} 
//				if (endTradeDate != null) {				
//					query.addCriteria(Criteria.where("tradeDate").lte(endTradeDate));
//				}
//			}
//			
//			query.with(new Sort(Direction.ASC, "tradeDate"));
//			query.withHint("tradeDate");
//			
//			if (beginPage != null) {
//				query.skip(beginPage);
//			}
//			
//			if (endPage != null) {
//				query.limit(endPage);
//			}
//			
//			log.info("按照条件查询 [证券代码 = " + stockCode + "] 的订单信息记录成功。");
//			
//			return mongoTemplate.find(query, OrderInfoPO.class, getDocCollectionName(stockCode));
//		} catch (Exception e) {
//			String errorMsg = "按照条件查询 [证券代码 = " + stockCode + "] 的订单信息记录失败!";
//			log.error(errorMsg, e);
//			throw new RuntimeException(errorMsg, e);
//		}
//	}
//	
//	@Override
//	public void dropCollection(String stockCode) {
//		StringBuilder logMsg = new StringBuilder();
//		logMsg.append("invoke dropCollection method").append("\n");
//		logMsg.append("@param [stockCode = " + stockCode + "]");
//		log.info(logMsg.toString());
//		
//		try {
//			/*
//			 * 原生语句：db.orderInfo_123456.drop();
//			 */
//			mongoTemplate.dropCollection(getDocCollectionName(stockCode));
//			log.info("删除  [证券代码 = " + stockCode + "] 用于记录订单信息的集合成功。");
//		} catch (Exception e) {
//			String errorMsg = "删除  [证券代码 = " + stockCode + "] 用于记录订单信息的集合失败!";
//			log.error(errorMsg, e);
//			throw new RuntimeException(errorMsg, e);
//		}
//	}
//	
//	/**
//	 * 得到MongoDB中文档集合的名称。
//	 * 
//	 * @param stockCode 证券代码
//	 * @return String
//	 */
//	private String getDocCollectionName(String stockCode) {
//		return "orderInfo_" + stockCode;
//	}
//}