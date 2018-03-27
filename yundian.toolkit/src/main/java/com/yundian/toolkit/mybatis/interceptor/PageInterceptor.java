package com.yundian.toolkit.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.yundian.toolkit.utils.Paging;
import com.yundian.toolkit.utils.ReflectHelper;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})}) 
public class PageInterceptor implements Interceptor {

	private String dialect = "";	//数据库方言
	private String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	
	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation ivk) throws Throwable {
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			
			if(mappedStatement.getId().matches(pageSqlId)){ //拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if(parameterObject!=null && parameterObject instanceof Map){
					if(((Map) parameterObject).containsKey("page")){
						Paging page = (Paging)((Map) parameterObject).get("page");
						if(page==null)return ivk.proceed();
						String sql = boundSql.getSql();
						sql = sql.replaceAll("[\\t|\\n|\\r]", " ");
						Connection connection = (Connection) ivk.getArgs()[0];
						StringBuffer strCount= new StringBuffer("SELECT COUNT(0) AS $recordcount ");
						if(sql.toLowerCase().contains("group by")){
							strCount.append(" from (");
							if(sql.toString().toLowerCase().lastIndexOf("order by") > -1){
								strCount.append(sql.substring(0, sql.toLowerCase().lastIndexOf("order by"))).append(") xxpg");
							}else{
								strCount.append(sql).append(") xxpg");
							}
						}else{
							if(sql.toLowerCase().indexOf("select") > -1 && sql.toLowerCase().indexOf(" from") > -1){
								strCount.append(sql.substring(sql.toLowerCase().indexOf(" from")));
							}
							if(strCount.toString().toLowerCase().lastIndexOf("order by") > -1){
								strCount = new StringBuffer(strCount.substring(0, strCount.toString().toLowerCase().lastIndexOf("order by")));
							}
						}
						PreparedStatement countStmt = connection.prepareStatement(strCount.toString());
						BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), strCount.toString(), boundSql.getParameterMappings(),parameterObject);
						if(strCount.indexOf("?") > -1){
							setParameters(countStmt,mappedStatement,countBS,parameterObject);
						}
						ResultSet rs = countStmt.executeQuery();
						Integer count = 0;
						if (rs.next()) {
							count = rs.getInt(1);
						}
						rs.close();
						countStmt.close();
						
						page.setTotal_item(count);
						 
						String pageSql = generatePageSql(sql,page);
						ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
					}
				}
			}
		}
		Object processResult= ivk.proceed();
		return processResult;
	}
	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					try{
						typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql, Paging page){
		
		if(page!=null && StringUtils.isNotEmpty(dialect)){
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)){
				pageSql.append(sql);
				pageSql.append(" limit "+page.getCurrent_page()*page.getPage_size()+","+page.getPage_size());
			}else if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrent_page()*page.getPage_size());
				pageSql.append(") where row_id>");
				pageSql.append((page.getCurrent_page()-1)*page.getPage_size());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isEmpty(dialect)) {
			try {
				throw new Exception("dialect property is not found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			try {
				throw new Exception("pageSqlId property is not found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getPageSqlId() {
		return pageSqlId;
	}

	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;
	}
}
