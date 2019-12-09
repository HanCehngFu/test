<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="title" value="应急管理" />
<c:set var="ctx" value="http://${header['host']}${pageContext.request.contextPath}" />
<c:set var="version" value="1.0.0"/>
<c:set var="random" value="<%= java.lang.Math.random() %>"/>