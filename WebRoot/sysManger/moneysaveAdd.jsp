
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
<link
	href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="../com_css/LigerUILib/ligerUI/skins/Gray/css/all.css"
	rel="stylesheet" type="text/css" />
<link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/core/base.js"
	type="text/javascript"></script>

<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js"
	type="text/javascript"></script>
<script
	src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js"
	type="text/javascript"></script>
<script
	src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
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
      function ajaxUpdate(){  
    	  $.blockUI({message:'<h5><img src="../images/loading.gif" /> 系统正在提交中……</h5>' }); 
         var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
 
      $.ajax({   
              url :'moneySaveAddMoneyAction',  //后台处理程序   
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
	            alert('登录名已存在');
	            document.getElementById('txtUserId').value='';
	        }                       
        
        }else{   
          
            alert('系统异常,新增失败,请重试或联系系统管理员！');
              
        }   
 		}   
    </script>
<style type="text/css">
body {
	font-size: 12px;
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
</style>

</head>

<body style="padding:10px">

	<form name="form1" method="post" action="" id="form1">
		<div></div>

		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>请存钱:</td>
				<td align="left" class="l-table-edit-td"><input
					name="saveMoney" type="text" id="txtUserId" ltype="text"
					validate="{required:true,minlength:1,maxlength:20}"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" valign="top"></td>
				<td align="left" class="l-table-edit-td"><input type="submit"
					value="提交" class="l-button l-button-submit" /></td>

			</tr>
		</table>


	</form>
	<div style="display:none">
		<!--  数据统计代码 -->
	</div>


</body>
</html>
