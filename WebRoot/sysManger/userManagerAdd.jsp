 <%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
	<link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
     
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="../com_css/js/jquery.blockUI.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
        	 $.metadata.setType("attr", "validate");
             var v = $("form").validate({
                 debug: true,
                 errorPlacement: function (lable, element)
                 {
                     if (element.hasClass("l-textarea"))
                     {
                         element.ligerTip({ content: lable.html(), target: element[0] }); 
                     }
                     else if (element.hasClass("l-text-field"))
                     {
                         element.parent().ligerTip({ content: lable.html(), target: element[0] });
                     }
                     else
                     {
                         lable.appendTo(element.parents("td:first").next("td"));
                     }
                 },
                 success: function (lable)
                 {
                     lable.ligerHideTip();
                     lable.remove();
                 },
                 submitHandler: function ()
                 {
                     $("form .l-text,.l-textarea").ligerHideTip();
                     ajaxUpdate();
                 }
             });
           
            $("form").ligerForm();
            $(".l-button-test").click(function(){
                alert(v.element($("#txtName")));
            });        
        });  
        function alamTxt(id,txtContext){

      $("#"+id).ligerTip({ content: txtContext, width: 150 });
      }
      
         function removeAlamTxt(id){
         	$("#"+id).ligerHideTip();
         }
        
         /*function   updateUserGroupData(){
      		if ($("#parentDeptName").val()=="点击选择部门"){
      			alert("请先选择部门，用户组会 自动生成");
      		}
         }
         function selectParentRole(obj) {
            var firstNum = 0;
            var newData = new Array();
			var time=new Date();
			var url="organizationAddDeptTree.jsp";
			var orgIdAndName = window.showModalDialog(url+"?time="+time , window,
	    		'dialogHeight:400px;dialogWidth:300px;center:yes;status:no;scroll:yes;help:no;resizable:yes;edge:Raised');
   			if(orgIdAndName){
				var org = orgIdAndName.split(":");
				var orgId = org[0];
				var orgName = org[1];
				if(orgId.indexOf(";;") != -1){
				orgId = orgId.substring(0,orgId.indexOf(";;"));
			}
			document.getElementById("hidParentDeptId").value = orgId;
			document.getElementById("parentDeptName").value = orgName;
			switch (orgId.length){
			case 1: firstNum=1;
					break;
			case 3: firstNum=2;
					break;
			case 6: firstNum=3;
					break;
			case 9: firstNum=4;
					break;
			case 12:firstNum=5;
					break;
			}
			
			for(i=0;i<userGroupData.length;i++){
				if(userGroupData[i].id.substring(0,1)==firstNum){
					newData.push(userGroupData[i]);
				}
			}
			$("#txtUserGroup").ligerGetComboBoxManager().setData(newData);
   		}
	}
  */
  
   function checklegal(){    //验证账号、密码是否合法

      		var cd=/^[A-Za-z0-9]\w{5,19}$/;
      		var us=/^[A-Za-z0-9]\w{2,19}$/;
      		var id = document.getElementById("txtUserId").value;
      		var psw = document.getElementById("txtPsw").value;
      		if(id != ""){
  	  		  if(us.test(id) == false){
  	  		 
  	  		    alert("请正确填写用户登录名,登录名长度3~20位，且只能以字母或数字开头，账号中只能包含字母、数字、下划线。");
  	  		    $("#txtUserId").val("");
  	  		    return false;
  	  		  }
    		}
      		if(psw != ""){
    	  		  if(cd.test(psw) == false)
    	  		  {
    	  		    alert("请正确填写用户密码,密码长度6~20位，且只能以字母或数字开头，密码中只能包含字母、数字、下划线。");
    	  		   $("#txtPsw").val("");
    	  		    return false;
    	  		  }
      		  }
   }
      function ajaxUpdate(){  
	  //txtPhone
	  //验证电话号码手机号码，包含至今所有号段   
		  var ab=/^(13[0-9]|15[0-9]|18[0-9]|023)\d{8}$/;
		  var phone = document.getElementById("txtPhone").value;
		  if(phone != ""){
	  		  if(ab.test(phone) == false)
	  		  {
	  		    alert("请正确填写手机号码,例如 13812345678，或者固定电话02312345678");
	  		    return false;
	  		  }
  		  }
    	  $.blockUI({message:'<h5><img src="../images/loading.gif" /> 系统正在提交中……</h5>' }); 
         var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
 
      $.ajax({   
              url :'userManagerAddUserAction',  //后台处理程序   
             type:'post',    //数据发送方式   
              dataType:'text',   //接受数据格式   
              data:params,   //要传递的数据；就是上面序列化的值   
              success:submit_Result //回传函数(这里是函数名)    
       });         
 }   
  function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
	  
	  $.unblockUI();
      if(result=='success'||result=='allExist'){ 
	      if(result=='success'){
	    	 	window.parent.$("#maingrid4").ligerGrid().loadData(true);	
	    	 	
	    	   	$.ligerDialog.success('提交成功，请关闭对话框！','提示',function(){	
	    	   		//window.parent.$.controls.Dialog.close();
	    	   		
	    	   		var parentWindow_body =  $(window.parent.document).find("body");
	    	   		if(! parentWindow_body.find(".dealWithMask").length){
	    				parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");
	    			 }
	    			parentWindow_body.find(".dealWithMask").focus();
		   			 window.parent.$.ligerDialog.close();
		   			
		   			 
	  		      });	
		 		
	        }else{
	            alert('卡号已存在,请重新输入卡号!');
	            document.getElementById('txtUserId').value='';
	        }                       
        
        }else{   
          
            alert('请联系管理员申请卡号,联系方式：管理员邮箱：18883282594@163.com！');
              
        }   
 		}   
    </script>
   <style type="text/css">
        body{ font-size:12px;}        
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}        
    </style>

</head>

<body style="padding:10px">

    <form name="form1" method="post" action="" id="form1">
<div>
</div>

        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>卡号:</td>
                <td align="left" class="l-table-edit-td">
                <input name="txtUserId" type="text" id="txtUserId" ltype="text" validate="{required:true,minlength:1,maxlength:20}" onblur="checklegal()"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>用户姓名:</td>
                <td align="left" class="l-table-edit-td">
                <input name="txtName" type="text" id="txtName" ltype="text" validate="{required:true,minlength:1,maxlength:30}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
    		 <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>初始密码:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="txtPsw" type="password" id="txtPsw" ltype="text" validate="{required:true,minlength:1,maxlength:20}"  onblur="checklegal()"/></td>
                <td align="left"></td>
    		</tr>
    		<tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>申请账户金钱:</td>
                <td align="left" class="l-table-edit-td">
                <input type="text" id="remark" name="remark" validate="{required:true}"  />
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">联系电话:</td>
                <td align="left" class="l-table-edit-td"><input name="txtPhone" type="text" id="txtPhone" ltype="text" /></td>
                <td align="left"></td>
            </tr>            
            <tr>
                <td align="right" class="l-table-edit-td">邮箱:</td>
                <td align="left" class="l-table-edit-td"><input name="txtEmail" type="text" id="txtEmail" ltype="text" validate="{email:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
			<td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>是否启用:</td>
                 <td align="left" class="l-table-edit-td">
                    <input id="isUseable" type="radio" name="isUseable" value="1" checked="checked" /><label for="isUseable">可用</label> 
                    <input id="isUseable" type="radio" name="isUseable" value="0" /><label for="isUseable">禁用</label>
                </td>
                
            </tr>   
            <tr> 
            <td align="right" class="l-table-edit-td" valign="top"></td>
                 <td align="left" class="l-table-edit-td">
                 <input type="submit" value="提交"  class="l-button l-button-submit" /> 
              	</td>
                
            </tr>         
        </table>


    </form>
    <div style="display:none">
    <!--  数据统计代码 --></div>

    
</body>
</html>
