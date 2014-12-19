<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>银行账户系统</title>
	
	<link href="com_css/css/login.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="com_css/formValidator/style/validator.css" type="text/css"></link>
	<!-- script type="text/javascript" src="com_css/js/jquery-1.6.2.min.js"></script>-->
	<link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
    <link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>         
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
      
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script type="text/javascript" src="com_css/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="com_css/formValidator/formValidator-4.0.1.min.js"></script>
	<script type="text/javascript" src="com_css/formValidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="com_css/js/loginjs.js"></script>
<script type="text/javascript">

function key_down(){
	if (event.keyCode == 13) {
		checkLogin();
	}
}
/* function f_add()
{
	$.ligerDialog.warn('请联系管理员申请卡号,联系方式：管理员邮箱：18883282594@163.com！');
		//m.max();
} */
function f_add()
{
    /*$.ligerDialog.open({ title: '新增用户信息', width: 650, height: 300,modal:false,url: 'sysManger/userManagerAdd.jsp', buttons: [
        { text: '关闭窗口', onclick: f_closeAddWindow }
    ]
    });*/
    $.ligerDialog.open({ 
    	  title: '新增用户信息',
    	  height: 400, 
    	  width: 700, 
    	  url:'sysManger/userManagerAdd.jsp',	        	
    	   showMax: true,	        	   
    	  isResize: true,   
    	  slide: false,  
    	 // modal:false,
    	  
    	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
      }); 
}
function f_add2()
{
	$.ligerDialog.open({ 
  	  title: '忘记密码',
  	  height: 400, 
  	  width: 700, 
  	  url:'sysManger/passwordForgot.jsp',	        	
  	   showMax: true,	        	   
  	  isResize: true,   
  	  slide: false,  
  	 // modal:false,
  	  
  	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
    }); 	
}
function f_closeAddWindow(item, dialog)
{
   $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
             
             {
             if(yes)
                 dialog.close();
             })
    
}
</script>	
<style type = "text/css">
	.login_btn{margin:15px}
</style>
</head>

<body>
<div id="container">
	<div id="header">
		<!--  img alt="" src="mainFrame/images/logoBig.png" width="250" height="100">-->
	</div>
	<div id="content">
		<div class="left_main">
			<ul class="news" style="color:#FFFFFF;">
			<li><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</font></li>
<li><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</font></li>

				<li><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重邮小分组银行账户系统</font></li>
				
			</ul>
		</div>
		<form id="form1" method="post" action = "loginAction">
			<fieldset class="right_main">
				<dl class="setting">
					<dt>
						<label>卡　号</label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="text" name="userId" id="loginAction_userId"/>
						</span>
						<div id="usernameTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label>密　码</label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="password" name="password" id="loginAction_password" />
						</span>
						<div id="userpassTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label>验证码</label>
					</dt>
					<dd>
						<span class="short_input">
							<input id="loginAction_usercaptcha" type="text" style="text-transform: uppercase;" name="usercaptcha" onkeydown="key_down()"/>
						</span>
						<span class="yzm">
							<img src="identify_Code.jsp" id="identifycode"/>
							<a href="javascript:changeCode()">换一张</a>
						</span>
						<div id="vdcodeTip" style="width:280px;"></div>
					</dd>
					<dd>
					<input type="button" id="loginBtn" value="注册" name="sm1"  class="login_btn" onClick="f_add()" />
						<div id="loginbottom" class="login">
							<font color="#ff0000"><div id="errorInfo"><div class="warn" id="resultpsw"></div></div></font>
						</div>
					<input type="button" id="loginBtn" value="登录" name="sm1"  class="login_btn" onClick="checkLogin()" />
						<div id="loginbottom" class="login">
							<font color="#ff0000"><div id="errorInfo"><div class="warn" id="resultpsw"></div></div></font>
						</div>
					<div style="margin-left:300px;width:55px;">
						<a href="#" onclick="f_add2()">
							忘记密码
						</a>
					</div>
					</dd>
				</dl>
				
			</fieldset>
		</form>
	</div>	
	<div id="footer" >by 张成,郭振方,程路,詹洪</div>
</div>
</body>
</html>