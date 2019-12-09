package com.nest_lot.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.alibaba.fastjson.JSON;
import com.nest_lot.constant.ResultBase;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.utils.Tools;

/**
 * 作者：wf
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 *   将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 *   想将其做退出登录处理，然后再重定向到踢出登录提示页面
 *   日期：2018年6月16日
 */
public class KickoutSessionControlFilter extends AccessControlFilter
{
	private String kickoutUrl; 				// 踢出后到的地址
	private boolean kickoutAfter = false; 	// 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = 1; 			// 同一个帐号最大会话数 默认1

	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;

	public void setKickoutUrl(String kickoutUrl)
	{
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter)
	{
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession)
	{
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager)
	{
		this.sessionManager = sessionManager;
	}

	// 设置Cache的key的前缀
	public void setCacheManager(CacheManager cacheManager)
	{
		this.cache = cacheManager.getCache("shiro_redis_cache");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
	{
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
	{
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered())
			return true;

		Session session = subject.getSession();
		Object key =  subject.getPrincipals().getPrimaryPrincipal();
		SysUserBean userInfo  = new SysUserBean();
		BeanUtils.copyProperties(userInfo, key);
		String userLoginId = userInfo.getUserLoginId();
		if (Tools.isTrimEmpty(userLoginId))
			return true;
		
		Serializable sessionId = session.getId();

		// 读取缓存 没有就存入
		Deque<Serializable> deque = cache.get(userLoginId);
		if (deque == null)
			deque = new LinkedList<Serializable>();

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列

		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null)
		{
			// 将sessionId存入队列
			deque.push(sessionId);
			// 将用户的sessionId队列缓存
			cache.put(userLoginId, deque);
		}

		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession)
		{
			Serializable kickoutSessionId = null;
			if (kickoutAfter)
			{
				// 如果踢出后者
				kickoutSessionId = deque.removeFirst();
				// 踢出后再更新下缓存队列
				cache.put(userLoginId, deque);
			}
			else
			{
				// 否则踢出前者
				kickoutSessionId = deque.removeLast();
				// 踢出后再更新下缓存队列
				cache.put(userLoginId, deque);
			}

			try
			{
				// 获取被踢出的sessionId的session对象
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null)
				{
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			}
			catch (Exception e)
			{
				// ignore exception
			}
		}

		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true)
		{
			// 会话被踢出了
			try
			{
				// 退出登录
				subject.logout();
			}
			catch (Exception e)
			{
				// ignore
			}
			saveRequest(request);

			// 判断是不是Ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With")))
			{
				out(response, "该用户已在其他地方登陆");
			}
			else
			{
				// 重定向
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
			return false;
		}
		return true;
	}

	private void out(ServletResponse hresponse, String msg) throws IOException
	{
		ResultBase reslut = new ResultBase();
		reslut.setCode(1001);
		reslut.setMessage(msg);
		
		try
		{
			hresponse.setCharacterEncoding("UTF-8");
			PrintWriter out = hresponse.getWriter();
			out.println(JSON.toJSONString(reslut));
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			
		}
	}
}
