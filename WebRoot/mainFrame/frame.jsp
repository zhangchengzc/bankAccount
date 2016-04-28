<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/userName.tld" prefix="userName"%>
<html>
	<head>
	
		<title>重庆邮电大学重邮小分队</title>
		<link href="mainFrame/css/default.css" rel="stylesheet"	type="text/css" />
		<link href="mainFrame/js/themes/default/easyui.css" rel="stylesheet" type="text/css"	/>
		<link href="mainFrame/js/themes/icon.css"  rel="stylesheet" type="text/css"	/>
		<script type="text/javascript" src="mainFrame/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="mainFrame/js/jquery.easyui.min.1.2.2.js"></script>
		<script type="text/javascript" src="mainFrame/js/initMenu.js"></script>
		<!-- initMenu.js 填充上下左中四个部分 的内容 -->
<style type="text/css">
#pageloading {
	position: absolute;
	left: 0px;
	top: 0px;
	background: white url('loading.gif') no-repeat center;
	width: 100%;
	height: 100%;
	z-index: 99999;
}
</style>
		<script type="text/javascript">
		
window.onbeforeunload = function() {
	if (document.body.offsetWidth - 50 < event.clientX && event.clientY < 0) {
		alert("退出成功，欢迎下次使用");
		var xhr1 = getXMLHttpRequest();
		var responseContext = "";

		xhr1.open("POST", "loginOutAction", true);

		xhr1.send();

		xhr1.onreadystatechange = function() {
		}

	}

}
//设置登录窗口
function openPwd() {
	$('#w').window( {
		title : '修改密码',
		width : 300,
		modal : true,
		shadow : true,
		closed : true,
		height : 160,
		resizable : false
	});
}
//关闭登录窗口
function closePwd() {
	$('#w').window('close');
}

//修改密码
function serverLogin() {

	var $newpass = $('#txtNewPass');
	var $rePass = $('#txtRePass');

	if ($newpass.val() == '') {
		msgShow('系统提示', '请输入密码！', 'warning');
		return false;
	}
	if ($rePass.val() == '') {
		msgShow('系统提示', '请再一次输入密码！', 'warning');
		return false;
	}

	if ($newpass.val() != $rePass.val()) {
		msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
		return false;
	}
//简写的 Ajax 函数
	$.post('changePWDAction?newpass=' + $newpass.val(), function(msg) {
		msgShow('系统提示', '恭喜，密码修改成功，下登录时生效！<br>您的新密码为：' + msg, 'info');
		$newpass.val('');
		$rePass.val('');
		openPwd();
	})

}

$(function() {
	
	getMenu();
	openPwd();
	$('#editpass').click(function() {
		$('#w').window('open');
	});

	$('#btnEp').click(function() {//提交修改密码
		serverLogin();
	})

	$('#btnCancel').click(function() {//关闭修改密码
		closePwd();
	})

	$("#help").bind("click", function() {
		addTab('帮助', 'mainFrame/helpManagement.jsp', 'icon-sys');
	});

	$('#loginOut').click(function() {
		$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

			if (r) {

				var xhr1 = getXMLHttpRequest();
				var responseContext = "";

				xhr1.open("POST", "loginOutAction", true);

				xhr1.send();

				xhr1.onreadystatechange = function() {
					location.href = '../bankAccount';	//点击安全退出，回到登录页面
				}

			}
		});
	})
});


</script>


	</head>

	<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
		<div id="pageloading"></div>
		<noscript>
			<div
				style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
				<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
		<!-- 上面部分 -->
		<div region="north" split="true" border="false"	 style="overflow: hidden; height: 40px; background: url('mainFrame/images/top.jpg')  repeat-x 100% bottom; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
			
			<span style="float: right; padding-right: 20px;" align="absmiddle"	class="head">欢迎<userName:userName></userName:userName>登陆 
				<a href="#" id="editpass">修改密码</a> 
				<a href="#" id="loginOut">安全退出</a> 
				<!-- <a href="#" id="help">帮助</a>  -->
			</span>		
			<span style="padding-left: 10px; font-size: 25px;"><!--  img
					src="mainFrame/images/logoBig.png" width="140" height="45"
					align="absmiddle" />-->家庭账户系统&nbsp;&nbsp;&nbsp;&nbsp;记账系统
			 </span>
		</div>
		<!-- 下面部分 脚 -->
		<div region="south" split="true"
			style="height: 30px; background: #D2E0F2;">
			<div class="footer">
				By 张成
			</div>
		</div>
		<!-- 左边部分  用户菜单-->
		<div region="west" hide="true" split="true" title="用户菜单"
			style="width: 180px;" id="west">
			<div id="nav" class="easyui-accordion" fit="true" border="false">
				<!--  菜单内容 -->

			</div>

		</div>
		<!-- 中间部分  主要内容显示页面 -->
		<div region="center" id="mainPanle" style="background: #eee; overflow-y: hidden">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div title="欢迎使用" style="padding: 20px; overflow: hidden; color: red;">
					<iframe name="content_frame" id="content_frame" marginwidth=0 marginheight=0 width=100% height="100%" src="" frameborder=0></iframe>
				</div>
			</div>
		</div>




		<!--修改密码窗口-->
		<div id="w" class="easyui-window" title="修改密码" collapsible="false"
			minimizable="false" maximizable="false" icon="icon-save"
			style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false"
					style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<table cellpadding=3>
						<tr>
							<td>
								新密码：
							</td>
							<td>
								<input id="txtNewPass" type="Password" class="txt01" />
							</td>
						</tr>
						<tr>
							<td>
								确认密码：
							</td>
							<td>
								<input id="txtRePass" type="Password" class="txt01" />
							</td>
						</tr>
					</table>
				</div>
				<div region="south" border="false"
					style="text-align: right; height: 30px; line-height: 30px;">
					<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
						href="javascript:void(0)"> 确定</a>
					<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel"
						href="javascript:void(0)">取消</a>
				</div>
			</div>
		</div>

		<div id="mm" class="easyui-menu" style="width: 150px;">
			<div id="mm-tabupdate">
				刷新
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabclose">
				关闭
			</div>
			<div id="mm-tabcloseall">
				全部关闭
			</div>
			<div id="mm-tabcloseother">
				除此之外全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">
				当前页右侧全部关闭
			</div>
			<div id="mm-tabcloseleft">
				当前页左侧全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">
				退出
			</div>
		</div>


	</body>
</html>
