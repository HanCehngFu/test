package com.nest_lot.config;

import cn.ocoop.framework.sql.TC;
import cn.ocoop.framework.sql.tenant.MySqlDeleteTenantOptimizer;
import cn.ocoop.framework.sql.tenant.MySqlInsertTenantOptimizer;
import cn.ocoop.framework.sql.tenant.MySqlUpdateTenantOptimizer;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.nest_lot.mybatis.MySqlSelectTenantOptimizer;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by liolay on 2017/11/24.
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }), })
public class TenantIntercept implements Interceptor {
	private final String TENANT_COLUMN;
	private final String TENANT_COLUMN_TYPE;
	private final boolean INSERT_ENABLED, DELETE_ENABLED, UPDATE_ENABLED, SELECT_ENABLED;
	private final String[] SKIPTABLES = { "sys_department_info", "sys_department_role_info", "sys_funtree_info",
			"sys_role_funtree_info", "sys_role_info", "sys_user_info", "sys_user_role_info","nest_in_info","element_info","nest_area"
			,"style_info","landmark_info","consume_info"};

	public TenantIntercept() {
		TENANT_COLUMN = "mch_id";
		TENANT_COLUMN_TYPE = "Number";
		INSERT_ENABLED = true;
		DELETE_ENABLED = true;
		UPDATE_ENABLED = true;
		SELECT_ENABLED = true;
	}

	public Object realTarget(Object target) {
		if (Proxy.isProxyClass(target.getClass())) {
			MetaObject metaObject = SystemMetaObject.forObject(target);
			return realTarget(metaObject.getValue("h.target"));
		}
		return target;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (TC.get() == null) {
			return invocation.proceed();
		}

		StatementHandler statementHandler = (StatementHandler) realTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		String optimizedSql = boundSql.getSql();
		Boolean flage = true;
		for (int i = 0; i < SKIPTABLES.length; i++) {
			if (optimizedSql.indexOf(SKIPTABLES[i])>-1) {
				flage = false;
				break;
			}
		}
		if (flage) {
			if (INSERT_ENABLED && SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
				optimizedSql = new MySqlInsertTenantOptimizer(TENANT_COLUMN, TENANT_COLUMN_TYPE).optimize(optimizedSql);
			} else if (DELETE_ENABLED && SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
				optimizedSql = new MySqlDeleteTenantOptimizer(TENANT_COLUMN, TENANT_COLUMN_TYPE).optimize(optimizedSql);
			} else if (UPDATE_ENABLED && SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
				optimizedSql = new MySqlUpdateTenantOptimizer(TENANT_COLUMN, TENANT_COLUMN_TYPE).optimize(optimizedSql);
			} else if (SELECT_ENABLED && SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
				optimizedSql = new MySqlSelectTenantOptimizer(TENANT_COLUMN, TENANT_COLUMN_TYPE).optimize(optimizedSql);
			}
			metaObject.setValue("delegate.boundSql.sql", optimizedSql);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
