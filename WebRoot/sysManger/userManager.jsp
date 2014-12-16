<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>

<html>
<head>
<title>用户管理</title>
    <link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
    <link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>       
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script> 
    
    <script type="text/javascript">
    var grid = null;   
    var manager=null;  
        $(function() {
            
            <pageADMOperAuth.tld:pageADMOperAuth menuId="'010301','010302','010303'"></pageADMOperAuth.tld:pageADMOperAuth>
             f_initGrid();
            var v = $("form").validate({
	                //调试状态，不会提交数据的
	                debug: true,
	                errorLabelContainer: "#errorLabelContainer", wrapper: "li",
	                invalidHandler: function (form, validator) {
	                    $("#errorLabelContainer").show();
	                    setTimeout(function () {
	                        $.ligerDialog.tip({ content: $("#errorLabelContainer").html() });
	                    }, 10);
	                },
	                submitHandler: function () {
	                    $("#errorLabelContainer").hide();
	                    alert("Submitted!");
	                }
	            });            
	            $(".l-button-test").click(function () {
	                alert(v.element($("#txtName")));
	            });
            $("form").ligerForm();
          
            manager= $("#maingrid4").ligerGetGridManager();
           // $("#txtUserGroup").ligerComboBox({ data: null, isMultiSelect: true, isShowCheckBox: true });
             
        }); 
        
        
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
   

         function f_modify()
        {
            
            if(!manager) {
            		$.ligerDialog.error('请查询用户清单后,选中需要修改的条目,再修改！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要修改的行！'); return; }
            	/*var modifyUrl=
            	  $.ligerDialog.open({ title: '修改用户信息', width: 650,modal:false, height: 300, url: 'userManagerModifyAction?userId='+row.userId, buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            	]
            	});*/
            	$.ligerDialog.open({ 
  	        	  title: '修改用户信息',
  	        	  height: 400, 
  	        	  width: 700, 
  	        	  url: 'userManagerModifyAction?userId='+row.userId,	        	
  	        	   showMax: true,	        	   
  	        	  isResize: true,   
  	        	  slide: false,  	        
  	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
  	          }); 
            }
        }
        
         function f_delete()
        {
           
            var manager = $("#maingrid4").ligerGetGridManager();
            if(!manager) {
            		$.ligerDialog.error('请查询用户清单后,选中需要删除的条目,再删除！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要删除的行！'); return; }
            	$.ligerDialog.confirm('确认要删除该用户信息吗?若删除,用户信息将丢失', function (yes)
                     {
              
                     if(yes)
                     {
                     	var xhr = getXMLHttpRequest();
    					xhr.open("POST", "userManagerDeleteUserAction?userId="+row.userId, true);   
    					xhr.send(); 
    					xhr.onreadystatechange = function() { 
       						if (xhr.readyState == 4 && xhr.status == 200) {  
           					var responseContext = xhr.responseText;
                            if(responseContext=='success') 
                            {
                            	manager.deleteSelectedRow();
                            	$.ligerDialog.success('删除成功！');
                            }else
                            {
                            	$.ligerDialog.error('对不起，您无权限删除用户,如确定要删除,请联系系统管理员！');
                            }
        
       	   
       }   
  
    }   
                     
                     }
                      
                     }) 
            	//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
            }
            
    
        }
      
        function f_closeAddWindow(item, dialog)
        {
           $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
                     
                     {
                     if(yes)
                         dialog.close();
                     })
            
        }
        
        
         function f_initGrid()
        {
      
        grid = manager = $("#maingrid4").ligerGrid({
        	
                columns: [
                          { display: '序号', name: 'orderId',width: 50, minWidth: 60 },
                          { display: '卡号', name: 'userId',  width: 150, minWidth: 60 },
                          { display: '用户姓名', name: 'userName', width: 150, minWidth: 60},
                          { display: '银行卡余额', name: 'remark', width: 150, minWidth: 60},
                          { display: '用户所属组', name: 'groupName', width: 150, minWidth: 60},
                          { display: '状态', name: 'userState', width: 50, minWidth: 60 },
                          { display: '用户联系方式', name: 'userMobPhone', width: 150, minWidth: 60 },
                          { display: '邮箱', name: 'userEmail', width: 200, minWidth: 60 }             
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:false, checkbox:false,
	                url: 'userManagerQueryAction',allowAdjustColWidth:true,
	                width: '100%', height: '98%', 
	                toolbar: toolBar
            });
                   
        }
        
  
        function f_getWhere()
        {
            if (!grid) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.orderId.indexOf(key) > -1;
            };
            return clause; 
        }
        
 
        
    </script>
    <style type="text/css">
body{ font-size:12px;}
.blackbold_b { /* 标题样式 */
	line-height: 22px;
	width: 150px;
	height: 22px;
	font-size: 12px;
	font-weight: bold;
	color: #FF5317
}
.l-table-edit-td{ padding:4px;font-size:12px;}


    </style>

</head>

<body style="padding:6px; overflow:hidden;">
<div id="toptoolbar"></div> 


    
    <div style="display:none">
    <!--  数据统计代码 --></div>

   
 


 <table width="100%" id="result">
 <tr >
		<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
	</tr>
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr>
		<td class="blackbold_b"><img src="images/util/arrow6.gif" hspace="5">用户信息</td>
	</tr>
	    
</table>


<div id="maingrid4" style="margin:0; padding:0"></div>
<div style="display: none;"> </div>

</body>
</html>
