<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
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
        	//document.getElementById("initialDeptName").value = "${request.deptName}";
        	//document.getElementById("deptName").value = "${request.deptName}";
        	//document.getElementById("hidDeptId").value = "${request.deptId}";            
           // document.getElementById("companyName").value = "${request.companyName}";
            //document.getElementById("deptPhone").value = "${request.phone}";
           // document.getElementById("parentDeptName").value = "${request.parentDeptName}";
           // document.getElementById("hidParentDeptId").value = "${request.parentDeptId}";
           
           // document.getElementById("totalCredit").value = "${request.totalCredit}";
           // document.getElementById("availableCredit").value = "${request.availableCredit}";
         //   document.getElementById("changeCredit").value = "${request.changeCredit}";
            var organState= "${request.state}";
            var radioObj = document.all("isUseable");           
			for(var ii=0; ii<radioObj.length; ii++) {
				if(radioObj[ii].value== organState) {
					radioObj[ii].checked = true; 
				}
			}
			var deptType= "${request.deptType}";
			$("#initialDeptType").val("${request.deptType}");
			var radioDeptType = document.all("deptType");
			for(var i=0; i<radioDeptType.length; i++){
				if(radioDeptType[i].value == deptType){
					radioDeptType[i].checked = true;
				}
			}
			
            
            manager= window.parent.$("#maingrid4").ligerGetGridManager();
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
            $("form").ligerForm();
            $(".l-button-test").click(function() {
                alert(v.element($("#txtName")));
            });            
        });  
           function checklegal(){    
      		
      		var pn=/^[1-9]\d{5}(?!\d)$/;      	
      		var postNum = document.getElementById("postNum").value;  
			
      		if(postNum != ""){
    	  		  if(pn.test(postNum) == false)
    	  		  {
    	  		    alert("请正确填写邮政编码，邮政编码为6位数字组成。");
    	  		    $("#postNum").val("");
    	  		    return false;
    	  		  }
      		  }
      		
          }

    function ajaxUpdate(){
    	  var ab=/^(13[0-9]|15[0-9]|18[0-9]|023)\d{8}$/;
  		  var phone = document.getElementById("deptPhone").value;
  		  var transferPhone = document.getElementById("transferUserPhone").value;   		
  		  if(phone != ""){
	  		  if(ab.test(phone) == false)
	  		  {
	  		    alert("部门电话有误，请正确填写手机号码,例如 13812345678，或者固定电话02312345678");
	  		    return false;
	  		  }
  		  }
  		  if(transferPhone != ""){
	  		  if(ab.test(transferPhone) == false)
	  		  {
	  		    alert("收件人电话有误，请正确填写手机号码,例如 13812345678，或者固定电话02312345678");
	  		    return false;
	  		  }
  		  }
  		  //alert("修改前"+initialDeptName);
  		 // alert("修改后"+document.getElementById("deptName").value);
    	 	$.blockUI({message:'<h4><img src="../images/loading.gif" /> 系统正在提交中……</h4>' }); 
         	 var params=$('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
			 params = decodeURIComponent(params,true);	      
			 params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');
		     
	      	 $.ajax({   
	              url :'organizationDeptUpdateAction',  //后台处理程序   
	             type:'post',    //数据发送方式   
	              dataType:'text',   //接受数据格式   
	              data:params,   //要传递的数据；就是上面序列化的值   
	              success:submit_Result //回传函数(这里是函数名)    
	       	}); 
      	
 	 }   
  		function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
	  		$.unblockUI();
     		if(result=='success'){  
        		$.ligerDialog.success('提交成功，请关闭对话框！','提示',function(){		  		
 	   			 
 	   			  window.parent.$("#maingrid4").ligerGrid().loadData(true);	   	
    			  var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
   		      });
        
        	}else if(result=='deptNameError'){   
          
            	alert('此部门已存在,请检查部门名称！');
            	window.parent.$("#maingrid4").ligerGrid().loadData(true);	   	
    		
              
       		}else {
      			$.ligerDialog.error('系统异常，请联系管理员！');
     		   
     		}
     			
     		}
 		
        function selectParentRole(obj) {
        	$.ligerDialog.confirm('确认要修改父部门吗，修改父部门会丢失其下属部门', function (yes){
        	if(yes){
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
        	})
			}
        
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

    <form name="form1" method="post" action="organizationDeptUpdateAction" id="form1">
<div>
</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <%
					 HttpSession s= request.getSession();
					 String deptId = (String)s.getAttribute("deptId");
					if(deptId.equals("2") || deptId.equals("1") ){ %>
					
            <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>部门名称:</td>
                <td align="left" class="l-table-edit-td">
                <input  type="hidden" name="hidDeptId" id="hidDeptId" value="${request.deptId}"/>
                <input  type="hidden" name="initialDeptName" id="initialDeptName" value="${request.deptName}"/>
                <input name="deptName"  type="text" id="deptName" ltype="text" validate="{required:true,minlength:2,maxlength:300}" value="${request.deptName}"/></td>
                
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>网点类型:</td>
                <td align="left" class="l-table-edit-td">
                    <input id="deptType" type="radio" name="deptType" value="维修中心" checked="checked" />维修中心
                    <input id="deptType" type="radio" name="deptType" value="接机点" />接机点
                    <input  type="hidden" name="initialDeptType" id="initialDeptType" vale="${request.deptType}" />
                </td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公司名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="companyName" type="text" id="companyName" ltype="text" validate="{required:true}" value="${request.companyName}"/>
                </td>
                
            </tr>

       <!--    <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>父部门名称:</td>
                <td align="left" class="l-table-edit-td">
                <input  type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
                <input name="parentDeptName" type="text" id="parentDeptName" readonly="readonly" ltype="text" onfocus="this.blur()" />此部门不能被修改，如需改动，请删掉后重新添加！</td>
                
            </tr>           
 -->  
        	<tr>
                <td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>是否可用:</td>
                <td align="left" class="l-table-edit-td">
                    <input id="isUseable" type="radio" name="isUseable" value="可用" checked="checked" /><label for="isUseable">可用</label>
                     <input id="isUseable" type="radio" name="isUseable" value="不可用" /><label for="isUseable">不可用</label>
                </td>
            </tr> 
          <%}else{ %>
            	
           <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>部门名称:</td>
                <td align="left" class="l-table-edit-td">
                <input  type="hidden" name="hidDeptId" id="hidDeptId" value="${request.deptId}"/>
                <input name="deptName"  type="text" id="deptName" ltype="text" validate="{required:true,minlength:2,maxlength:300}" readonly="readonly" value="${request.deptName}"/>
                <input  type="hidden" name="initialDeptName" id="initialDeptName" value="${request.deptName}"/> 
                <input  type="hidden" name="initialDeptType" id="initialDeptType" value="${request.deptType}" />
                <input  type="hidden" name="deptType" id="deptType" value="${request.deptType}" />
                </td>
              
               
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公司名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="companyName" type="text" id="companyName" ltype="text" validate="{required:true}" readonly="readonly" value="${request.companyName}"/>
                </td>
                
            </tr>

            <tr>
                <td align="right" class="l-table-edit-td"><font color="#ff0000">*</font>父部门名称:</td>
                <td align="left" class="l-table-edit-td">
                <input  type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
                <input name="parentDeptName" type="text" id="parentDeptName" readonly="readonly" ltype="text" value="${request.parentDeptName}" onfocus="this.blur()" />此部门不能被修改，如需改动，请删掉后重新添加！</td>
                
            </tr>           

        	<tr style="display:none">
                <td align="right" class="l-table-edit-td" valign="top"><font color="#ff0000">*</font>是否可用:</td>
                <td align="left" class="l-table-edit-td">
                    <input id="isUseable" type="radio" name="isUseable" value="可用" checked="checked" /><label for="isUseable">可用</label>
                     <input id="isUseable" type="radio" name="isUseable" value="不可用" /><label for="isUseable">不可用</label>
                </td>
            </tr> 
            	
      <%} %>
            <tr>
                <td align="right" class="l-table-edit-td">服务电话:</td>
                <td align="left" class="l-table-edit-td"><input name="deptPhone" type="text" id="deptPhone" ltype="text" value="${request.deptPhone}"/></td>
                
            </tr>
            <tr>      
                <td align="right" class="l-table-edit-td">物流地址:</td>
                <td align="left" class="l-table-edit-td"> 
                <textarea cols="100" rows="4" class="l-textarea" name="address" id="address" style="width:400px" >${request.deptAddr}</textarea>
                </td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">收件人:</td>
                
                <td align="left" class="l-table-edit-td">
                	<input name="transferUserName"  type="text" id="transferUserName" ltype="text" value="${request.transferUserName}"/>
                </td>
                
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td">收件人电话:</td>
                
                <td align="left" class="l-table-edit-td">
                	<input name="transferUserPhone"  type="text" id="transferUserPhone" ltype="text" value="${request.transferUserPhone}"/>
                </td>
                
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td">邮编:</td>
               
               <td align="left" class="l-table-edit-td">
               		<input name="postNum"  type="text" id="postNum" ltype="text" onblur="checklegal()"  value="${request.postNum}"/>
               </td>
                
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td">备注:</td>
                <td align="left" class="l-table-edit-td"> 
                <textarea cols="100" rows="4" class="l-textarea" name="remark" id="remark" style="width:400px" >${request.remark}</textarea>
                </td>
            </tr>
           
        </table>
 <br />
<input type="button" value="提交" onclick="ajaxUpdate()"  class="l-button l-button-submit" /> 
    </form>
    <div style="display:none">
    <!--  数据统计代码 --></div>

    
</body>
</html>
