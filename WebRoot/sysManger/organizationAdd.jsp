<%@ page contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加部门</title>
	<link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
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
    <script src="../com_css/uploadify/swfobject.js" type="text/javascript" ></script>
	<script src="../com_css/uploadify/jquery.uploadify.v2.1.4.min.js" type="text/javascript"></script>
    <script type="text/javascript">
           $(function() {
        	   $.metadata.setType("attr", "validate");
          		var v = $("form").validate({
                debug: true,
                errorPlacement: function(lable, element) {
                    if (element.hasClass("l-textarea")) element.ligerTip({ content: lable.html(), appendIdTo: lable });
                    else if (element.hasClass("l-text-field")) element.parent().ligerTip({ content: lable.html(), appendIdTo: lable });
                    else lable.appendTo(element.parents("td:first").next("td"));
                },
                success: function(lable) {
                    lable.ligerHideTip();
                },
                submitHandler: function() {
                    $("form .l-text,.l-textarea").ligerHideTip();
                    ajaxUpdate();
                }
            });
           // $("form").ligerForm();
            
            $(".l-button-test").click(function() {
                alert(v.element($("#txtName")));
            }); 
            //$("#brandName").ligerComboBox({url:'brandQueryAction', isMultiSelect: false,  valueFieldID: 'brandId'});		 
            //$("#accountDate").ligerDateEditor({ showTime: false });     
            
        });  
        
          function checklegal(){    //验证账号、密码是否合法
      		var cd=/^[A-Za-z0-9]\w{5,19}$/;
      		var us=/^[A-Za-z0-9]\w{2,19}$/;
      		var pn=/^[1-9]\d{5}(?!\d)$/;
      		var id = document.getElementById("userId").value;
      		var psw = document.getElementById("userPsw").value;
      		var postNum = document.getElementById("postNum").value;
      		if(id != ""){
  	  		  if(us.test(id) == false)
  	  		  {
  	  		    alert("请正确填写用户登录名,登录名长度3~20位，且只能以字母或数字开头，账号中只能包含字母、数字、下划线。");
  	  		    $("#userId").val("");
  	  		    return false;
  	  		  }
    		  }
      		
      		if(psw != ""){
    	  		  if(cd.test(psw) == false)
    	  		  {
    	  		    alert("请正确填写用户密码,密码长度6~20位，且只能以字母或数字开头，密码中只能包含字母、数字、下划线。");
    	  		    $("#userPsw").val("");
    	  		    return false;
    	  		  }
      		  }
      		if(postNum != ""){
    	  		  if(pn.test(postNum) == false)
    	  		  {
    	  		    alert("请正确填写邮政编码，邮政编码为6位数字组成。");
    	  		    $("#postNum").val("");
    	  		    return false;
    	  		  }
      		  }
          }
	function onlyNum() 
	{
		var re = /^[1-9]+[0-9]*(\.\d+)?$/;
		if(!re.test($("#beginCredit").val())){
			alert("请输入大于1的数!");
			$("#beginCredit").val("10000.00");
			return
		}
	} 
        function ajaxUpdate(){//提交表单内容
      
        	
        	//验证电话号码手机号码，包含至今所有号段 
        	
  		  var ab=/^(13[0-9]|15[0-9]|18[0-9]|023)\d{8}$/;
  		  var phone = document.getElementById("deptPhone").value;
  		  var transferUserPhone = document.getElementById("transferUserPhone").value;
  		  if(phone != ""){
	  		  if(ab.test(phone) == false)
	  		  {
	  		    alert("请正确填写手机号码,例如 13812345678，或者固定电话02312345678");
	  		    return false;
	  		  }
  		  }
  		   if(transferUserPhone != ""){
	  		  if(ab.test(transferUserPhone) == false)
	  		  {
	  		    alert("请正确填写手机号码,例如 13812345678，或者固定电话02312345678");
	  		    return false;
	  		  }
  		  }
        	//判断是否上传了凭证图片
      //  	if(accountPic==""){
      //  		alert("请上传凭证图片后再提交!");
      //  		return;
      //  	}
        	//var hidParentDeptId = $("#hidParentDeptId").val();
        	//if(hidParentDeptId==""||hidParentDeptId==null){
        	//	alert("请选择父部门后再提交!");
     	 	//	return;
        	//}
        	  $.blockUI({message:'<h4><img src="../images/loading.gif" /> 系统正在提交中……</h4>' }); 
	         var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
			
		      $.ajax({   //把表单内容插入数据库
		              url :'organizationDeptAddAction',  //后台处理程序   
		             type:'post',    //数据发送方式   
		              dataType:'text',   //接受数据格式   
		              data:params,   //要传递的数据；就是上面序列化的值   
		              success:submit_Result //回传函数(这里是函数名)    
		       });  
	             
 }   
        
   function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
  		$.unblockUI();
  		
  		if(result=='success') {

       		 window.parent.$("#maingrid4").ligerGrid().loadData(true);		
       		  	$.ligerDialog.success('提交成功，请关闭对话框！','提示',function(){		  		
	   			 var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
  		      });	

       		
  		} else if(result=='deptError'){
		 	$.ligerDialog.error('父部门填写不正确！');
  		}else if(result=='deptExist'){
  			 $.ligerDialog.error('部门名称已存在，请重新输入！');
  		}else if(result=='idExist'){
  			$.ligerDialog.error('员工号已存在，请重新输入！');
  		}else if(result=='error'){
  			$.ligerDialog.error('系统异常，联系管理员！');
  			//window.parent.$("#maingrid4").ligerGrid().loadData(true);	   	
    		//	var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
  		}
 }
   
   /*function isAfterToday(){
		var temp =$("#accountDate").val() ;
			var today = new Date();   
	   		var day = today.getDate();   
	   		var month = today.getMonth() + 1;   
	   		var year = today.getFullYear(); 
	   		var date = year *10000 + month*100  + day;  
	   		//alert(date);
	   		temp =temp.replace(/-/g,"");
	   		if( temp >date){
		   		alert("打款日期不能选择今天以后");
		   		$("#accountDate").val('') ;
	   			return false;
	   		}
	}*/
    /*    function selectParentRole(obj) {
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
   		}
}
   */     
    </script>
    <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
    </style>

</head>

<body style="padding:10px">

    <form name="form1" method="post" action="" id="form1">

        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>部门名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="deptName" type="text" id="deptName" ltype="text" validate="{required:true}" />
                </td>
                
            </tr>
           <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>营销中心:</td>
                <td align="left" class="l-table-edit-td">
                	<select name="saleCentre" id="saleCentre" ltype="select">							
							<option value="一营销中心"  selected="selected">一营销中心</option>
							<option value="二营销中心">二营销中心</option>
							<option value="三营销中心">三营销中心</option>		
							<option value="四营销中心">四营销中心</option>	
							<option value="五营销中心">五营销中心</option>		
							<option value="六营销中心">六营销中心</option>					
					</select>
                </td>
                
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>网点类型:</td>
                <td align="left" class="l-table-edit-td">
                    <input id="deptType" type="radio" name="deptType" value="维修中心" checked="checked" />维修中心
                    <input id="deptType" type="radio" name="deptType" value="接机点" />接机点
                </td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公司名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="companyName" type="text" id="companyName" ltype="text" validate="{required:true}" />
                </td>
                
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td">部门电话:</td>
                <td align="left" class="l-table-edit-td"><input name="deptPhone" type="text" id="deptPhone" ltype="text" /></td>
                
            </tr>
              
         <!--   <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>父部门名称:</td>
                <td align="left" class="l-table-edit-td">
               	 	<input  type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
                	<input name="parentDeptName" type="text" id="parentDeptName" ltype="text" value="点击选择部门" readonly="readonly" onclick="selectParentRole(this)" />
                
                </td>
                
            </tr>
 		--> 
  
          <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>店长登录名:</td>
                <td align="left" class="l-table-edit-td">
                <input name="userId" type="text" id="userId" ltype="text" validate="{required:true,minlength:1,maxlength:30}" onblur="checklegal()" /></td>
               
            </tr>
             

          
              <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>店长姓名:</td>
                <td align="left" class="l-table-edit-td"><input name="userName"  type="text" id="userName" ltype="text" validate="{required:true,minlength:1,maxlength:100}" /></td>
               
            </tr>
      
              <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>店长密码:</td>
                <td align="left" class="l-table-edit-td"><input name="userPsw"  type="text" id="userPsw" ltype="text" validate="{required:true,minlength:1,maxlength:30}" onblur="checklegal()"/></td>
                
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>是否可用:</td>
                <td align="left" class="l-table-edit-td">
                    <input id="isUseable" type="radio" name="isUseable" value="可用" checked="checked" />可用 
                    <input id="isUseable" type="radio" name="isUseable" value="不可用" />不可用
                </td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td">地址:</td>
                <td align="left" class="l-table-edit-td"> 
                <textarea cols="100" rows="4" class="l-textarea" name="address" id="address" style="width:300px" ></textarea>
               
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">收件人:</td>
                
                <td align="left" class="l-table-edit-td">
                	<input name="transferUserName"  type="text" id="transferUserName" ltype="text" />
                </td>
                
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">收件人电话:</td>
                
                <td align="left" class="l-table-edit-td">
                	<input name="transferUserPhone"  type="text" id="transferUserPhone" ltype="text" />
                </td>
                
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td">邮编:</td>
               
               <td align="left" class="l-table-edit-td">
               		<input name="postNum"  type="text" id="postNum" ltype="text" onblur="checklegal()"/>
               </td>
                
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">备注:</td>
                <td align="left" class="l-table-edit-td"> 
                <textarea cols="100" rows="4" class="l-textarea" name="remark" id="remark" style="width:300px" ></textarea>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"></td>
                <td align="left" class="l-table-edit-td"> 
                 <input type="submit" value="提交" class="l-button l-button-submit" /> 
                </td>
            </tr>
          
        </table>


    </form>
    <div style="display:none">
    <!--  数据统计代码 --></div>

    
</body>
</html>
