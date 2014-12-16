<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>修改用户</title>
		<link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
		<link href="com_css/LigerUILib/ligerUI/skins/Gray/css/all.css"	rel="stylesheet" type="text/css" />
		<link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css"	rel="stylesheet" type="text/css" />
		<script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js"		type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/core/base.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js"	type="text/javascript"></script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js"		type="text/javascript"></script>
		<script	src="com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
		<script src="com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
		<script src="com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
		<script src="com_css/js/jquery.blockUI.js" type="text/javascript"></script>
		<script type="text/javascript">
$(function() {

	document.getElementById("txtName").value = "${request.txtName}";
	document.getElementById("txtUserGroup").value = "${request.txtUserGroup}";
	document.getElementById("txtPhone").value = "${request.txtPhone}";
	document.getElementById("txtEmail").value = "${request.userEmail}";
	$.metadata.setType("attr", "validate");
	var v = $("form").validate( {
		debug : true,
		errorPlacement : function(lable, element) {
			if (element.hasClass("l-textarea"))
				element.ligerTip( {
					content : lable.html(),
					appendIdTo : lable
				});
			else if (element.hasClass("l-text-field"))
				element.parent().ligerTip( {
					content : lable.html(),
					appendIdTo : lable
				});
			else
				lable.appendTo(element.parents("td:first").next("td"));
		},
		success : function(lable) {
			lable.ligerHideTip();
		},
		submitHandler : function() {
			$("form .l-text,.l-textarea").ligerHideTip();
			ajaxUpdate();
		}
	});
	$(".l-button-test").click(function() {
		alert(v.element($("#txtName")));
	});
	$("form").ligerForm();
});
function alamTxt(id, txtContext) {

	$("#" + id).ligerTip( {
		content : txtContext,
		width : 150
	});
}

function removeAlamTxt(id) {
	$("#" + id).ligerHideTip();
}
/*
function selectParentRole(obj) {
	var firstNum = 0;
	var newData = new Array();
	var time = new Date();
	var url = "sysManger/organizationAddDeptTree.jsp";
	var orgIdAndName = window
			.showModalDialog(
					url + "?time=" + time,
					window,
					'dialogHeight:400px;dialogWidth:300px;center:yes;status:no;scroll:yes;help:no;resizable:yes;edge:Raised');
	if (orgIdAndName) {
		var org = orgIdAndName.split(":");
		var orgId = org[0];
		var orgName = org[1];
		if (orgId.indexOf(";;") != -1) {
			orgId = orgId.substring(0, orgId.indexOf(";;"));
		}
		document.getElementById("hidParentDeptId").value = orgId;
		document.getElementById("parentDeptName").value = orgName;
		switch (orgId.length) {
		case 1:
			firstNum = 1;
			break;
		case 3:
			firstNum = 2;
			break;
		case 6:
			firstNum = 3;
			break;
		case 9:
			firstNum = 4;
			break;
		case 12:
			firstNum = 5;
			break;
		}
		for (i = 0; i < userGroupData.length; i++) {
			if (userGroupData[i].id.substring(0, 1) == firstNum) {
				newData.push(userGroupData[i]);
			}
		}
		$("#txtUserGroup").ligerGetComboBoxManager().setData(newData);
		$("#txtUserGroup").val('');
	}
}
*/
function ajaxUpdate() {
	$.blockUI( {
		message : '<h5><img src="images/loading.gif" /> 系统正在提交中……</h5>'
	});
	 var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
	var myUrl = 'userManagerModifyUserAction?txtUserId=' + "${request.txtUserId}";
	$.ajax( {
		url : myUrl, //后台处理程序   
		type : 'post', //数据发送方式   
		dataType : 'text', //接受数据格式   
		data : params, //要传递的数据；就是上面序列化的值   
		success : submit_Result
	//回传函数(这里是函数名)    
			});
}
function submit_Result(result) { //回传函数实体，参数为XMLhttpRequest.responseText   
	$.unblockUI();
	if (result == 'success') {

		alert('修改成功');
		window.parent.$("#maingrid4").ligerGrid().loadData(true);
		var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();

	} else {

		alert('系统异常,新增失败,请重试或联系系统管理员！');

	}
}
function getXMLHttpRequest() {
	var xhr;
	try {
		xhr = new XMLHttpRequest();
	} catch (err1) {
		try {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
			alert(xhr);
		} catch (err2) {
			alert("您的浏览器版本不支持Ajax....");
		}

	}

	return xhr;
}
function ajaxPasswordUpdate() {
	$.ligerDialog.confirm('确认要重置密码为111111?', function(yes) {
		if (yes) {
			var xhr = getXMLHttpRequest();
			xhr.open("POST", "userManagerPasswordModifyAction?txtUserId="
					+ "${request.txtUserId}", true);
			xhr.send();

			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var responseContext = xhr.responseText;

					if (responseContext == 'success') {
						$.ligerDialog.success('密码已重置为111111！');
					} else {
						$.ligerDialog.error('密码重置失败,请联系系统管理员！');
					}
				}
			}

		}

	})
}

</script>
		<style type="text/css">
body {
	font-size: 12px;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}

.l-verify-tip {
	left: 230px;
	top: 120px;
}
</style>

	</head>

	<body style="padding: 10px">
		<sysUserGroup:sysUserGroup></sysUserGroup:sysUserGroup>
		<form name="form1" method="post" action="" id="form1">

			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>用户姓名:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="txtUserId" type="hidden" id="txtUserId" />
						<input name="txtName" type="text" id="txtName" ltype="text"
							validate="{required:true,minlength:1,maxlength:30}">					</td>
					
				</tr>
		
				<tr>
					<td align="right" class="l-table-edit-td">
						用户组:
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="txtUserGroup" name="txtUserGroup"
							validate="{required:true}" readonly="readonly"
							 />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						联系电话:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="txtPhone" type="text" id="txtPhone" ltype="text"
							validate="{minlength:0,maxlength:20}"
							onfocus="alamTxt('txtPhone','请输入用户联系电话')"
							onblur="removeAlamTxt('txtPhone')" />
					</td>
					
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">
						邮箱:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="txtEmail" type="text" id="txtEmail" ltype="text"
							validate="{email:true}" onfocus="alamTxt('txtEmail','请输入用户邮箱')"
							onblur="removeAlamTxt('txtEmail')" />
					</td>
					
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" valign="top">
						<font color="#ff0000">*</font>是否启用:
					</td>
					<td align="left" class="l-table-edit-td">
						<input id="isUseable" type="radio" name="isUseable" value="1"
							checked="checked" />
						<label for="isUseable">
							可用
						</label>
						<input id="isUseable" type="radio" name="isUseable" value="0" />
						<label for="isUseable">
							禁用
						</label>
					</td>
					
				</tr>
				 <tr>
				  	 <td align="right" class="l-table-edit-td" ></td>
	                 <td align="left" class="l-table-edit-td">
	                 	<input type="submit" value="提交"  class="l-button l-button-submit" /> 
	                  	<input class="l-button l-button-submit" type="button" value="重置密码"
							onclick="ajaxPasswordUpdate()" name="addButton">
	              	</td>
				 </tr>
			</table>
			
		</form>
	
		<div style="display: none">
			<!--  数据统计代码 -->
		</div>


	</body>
</html>
