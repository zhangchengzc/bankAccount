 <%@ page contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加公告</title>
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
     <script src="../com_css/uploadify/swfobject.js" type="text/javascript" ></script>
	<script src="../com_css/uploadify/jquery.uploadify.v2.1.4.min.js" type="text/javascript"></script>
	 <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
	
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

         	var myUrlHead = 'sysScanImgUploadAction';
            
            // alert(myUrlHead);
   		 $("#myFileHead").uploadify({
                 'uploader' : '../com_css/uploadify/uploadify.swf',
                 'script' :myUrlHead ,//后台处理的请求
                 'cancelImg' : '../com_css/uploadify/cancel.png',
                 'fileDataName':'myFileHead',//服务器端根据这个接收文件
                 'queueID' : 'fileQueueHead',// 文件队列的ID，该ID与存放文件队列的div的ID一致
                 'queueSizeLimit' :5,//当允许多文件生成时，设置选择文件的个数
                 'fileDesc' : '请选择 gif,bmp,png,jpeg,jpg,doc,xls,ppt,txt,docx,xlsx,pptx,pdf文件类型',//用来设置选择文件对话框中的提示文本
                 'fileExt' : '*.gif;*.bmp;*.png;*.jpeg;*.jpg;*.doc;*.xls;*.txt;*.ppt;*.docx;*.xlsx;*.pptx;*.pdf', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                 'auto' : false,//false需要点击上传按钮才上传
                 'multi' : true,//可以上传多个文件
                 'simUploadLimit' :1,//允许同时上传的个数 
                 'buttonText' : '浏览文件',
                 'onCancel':function(event,queueId,fileObj,data){
                 	
                 },
                 'onError' : function(event, queueID, fileObj, errorObj) {
                     alert(" 上传失败" + errorObj.info + "错误类型" + errorObj.type);
                 },
                 'onComplete' : function(event, queueID, fileObj, response, data) {
                 	var resultArray=response.split("@"); 
                 	if(resultArray[0] == 'success'){
                 		alert("上传成功");
                 		$("#appendixName").val(resultArray[2]+';'+$("#appendixName").val());
                 	//	alert($("#appendixName").val());
                 	}else{
                 		alert("上传失败,请重试或联系管理员!");
                 	}
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
  function selectParentRole(obj) {
            var firstNum = 0;
            var newData = new Array();
			var time=new Date();
			var url="organizationAddDeptTree1.jsp";
			var deptName1 = window.showModalDialog(url+"?time="+time , window,
	    		'dialogHeight:450px;dialogWidth:500px;center:yes;status:no;scroll:yes;help:no;resizable:yes;edge:Raised');
   			//alert(deptName1);
   		   document.getElementById("toDeptName").value = deptName1;
	}
      function ajaxUpdate(){  
	  //txtPhone
	  //验证是否选择发往网点
	  
	  if($("#toDeptName").val() == '点击选择网点' || $("#toDeptName").val() == 'undefined'){
		  alert("请选择发往网点");
		  return;
	  }
    	  $.blockUI({message:'<h5><img src="../images/loading.gif" /> 系统正在提交中……</h5>' }); 
         var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
 
      $.ajax({   
              url :'messageAddAction',  //后台处理程序   
             type:'post',    //数据发送方式   
              dataType:'text',   //接受数据格式   
              data:params,   //要传递的数据；就是上面序列化的值   
              success:submit_Result //回传函数(这里是函数名)    
       });         
 }   
  function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
	  $.unblockUI();
      if(result=='success'){
    	  window.parent.$("#maingrid4").ligerGrid().loadData(true);		
    	   	$.ligerDialog.success('提交成功，请关闭对话框！','提示',function(){		  		
	   			 var parentWindow_body =  $(window.parent.document).find("body");
	   			 if(! parentWindow_body.find(".dealWithMask").length){
	   				 parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");
	   				 }
	   			 parentWindow_body.find(".dealWithMask").focus();
	   			 window.parent.$.ligerDialog.close();
  		      });	                      
        
        }else{   
          
	    	   $.ligerDialog.error('系统异常,提交失败,请重试或联系系统管理员！');
              
        }   
 		}   
    </script>
   <style type="text/css">
        body{ font-size:12px;}        
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}        
    </style>

</head>

<body style="padding:10px;background-color: #F7F8F9;">

    <form name="form1" method="post" action="" id="form1">
<div>
</div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>发往网点:</td>
                <td align="left" class="l-table-edit-td">
               	 	<input  type="hidden" name="deptId" id="deptId" />
                	<input name="toDeptName" type="text" id="toDeptName" ltype="text" value="点击选择网点" readonly="readonly" onclick="selectParentRole(this)" />
                
                </td>
                
            </tr>
        <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公告主题:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="messageTitle" type="text" id="messageTitle" validate="{required:true}"/>
                </td>
            </tr> 
        <tr>
                <td align="right" class="l-table-edit-td">公告类型:</td>
                     <td align="left" class="l-table-edit-td">
						<select name="messageType" id="messageType" ltype="select">							
							<option value="紧急通知">紧急通知</option>
							<option value="放假通知">放假通知</option>
							<option value="人事通知">人事通知</option>
							<option value="开会通知">开会通知</option>	
							<option value="其他">其他</option>				
						</select>
					</td>
               
            </tr>         
                <tr >
             <td align="right" class="l-table-edit-td">上传附件:</td>
                <td  id="more" align="left" class="l-table-edit-td">
                <div id="fileQueueHead"></div>
                   <input name="myFileHead" type="file"  id ="myFileHead" /> 
                   <a href="javascript:$('#myFileHead').uploadifyUpload()">上传</a>|
				   <a href="javascript:$('#myFileHead').uploadifyClearQueue()"> 取消上传</a>
				   	<input name="appendixName"  type="hidden"  id="appendixName" value = ""/>
				   	(支持office文档、txt文件、图片文件、pdf文档，可以上传多个)
                </td>  
          
            </tr>
            <tr>                                                     
                <td width="9%" align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公告内容:</td>
                <td align="left" class="l-table-edit-td">
                 <textarea type="textarea" validate="{required:true}"
                 id="messageContent" name="messageContent" style="width:800px;height:250px"></textarea>  
                 </td>
              </tr>  
            <tr> 
            <td align="right" class="l-table-edit-td" valign="top">&ldquo;</td>
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
