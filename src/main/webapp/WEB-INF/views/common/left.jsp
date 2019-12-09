<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<!--头部管理员信息 开始-->
			<li class="nav-header">
				<div class="dropdown profile-element">
					<span><img alt="image" class="img-circle" src="${ctx}/static/img/profile_small.jpg" /></span> <a data-toggle="dropdown" class="dropdown-toggle"
						href="#"
					> <span class="clear"> <span class="block m-t-xs"> <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
						</span></a>
					<ul class="dropdown-menu animated fadeInRight m-t-xs">
						<!-- <li><a class="J_menuItem" href="profile.html">个人资料</a></li>
						<li><a class="J_menuItem" href="contacts.html">管理员管理</a></li>
						<li class="divider"></li> -->
						<li><a href="/logout">安全退出</a></li>
					</ul>
				</div>
				<div class="logo-element">
					<img src="${ctx}/favicon.ico" alt="">
				</div>
			</li>
			<!--头部管理员信息 结束-->
			<!--左侧菜单 开始-->
			
			
			
			<!--左侧菜单结束-->
		</ul>
	</div>
</nav>
<!--左侧导航结束-->