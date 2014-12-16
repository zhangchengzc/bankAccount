<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<html>
<head>
   <link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>   
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
     <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="../com_css/js/jquery.blockUI.js" type="text/javascript"></script>
    <script type="text/javascript">

    var grid = null; 
   
      $(function ()
        {
         	<pageADMOperAuth.tld:pageADMOperAuth menuId="'010201','010202','010203'"></pageADMOperAuth.tld:pageADMOperAuth>            
            f_initGrid();
  			
             var sessionDeptId = "${session.deptId}";
           // alert(sessionDeptId);
           if(sessionDeptId=='1'){
        	   grid.toggleCol('parentDeptName', true);
        	   grid.toggleCol('totalCredit', true);
        	   //grid.toggleCol('availableCredit', true);
        	   grid.toggleCol('contractCredit', true);
           }else{
        	   grid.toggleCol('parentDeptName', false);
        	   grid.toggleCol('totalCredit', false);
        	  // grid.toggleCol('availableCredit', false);
        	   grid.toggleCol('contractCredit', false);
        	   
           }
             
        });
       
      
        function f_add()
        {
           
        	var m =$.ligerDialog.open({ 
	        	  title: '新增部门信息',
	        	  height: 400, 
	        	  width: 700, 
	        	  url: 'organizationAdd.jsp',	        	
	        	   showMax: true,	        	   
	        	  isResize: true,   
	        	  slide: false,  	        
	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
	          });
        	m.max();
        }
         function f_modify()
        {
         var  manager = $("#maingrid4").ligerGetGridManager();
          var sessionDeptId1 = "${session.deptId}";
            if(!manager) {
            		$.ligerDialog.error('请查询部门清单后,选中需要修改的条目,再修改！');
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { 
            		$.ligerDialog.error('请选择需要修改的行！'); 
            		return;
            	}
            	
            	if(sessionDeptId1 != '1'){
	            	if(sessionDeptId1 !== row.deptId){
	            		$.ligerDialog.error('对不起，您不能修改其他网点的数据！'); 
	            		return;
	            	}
            	}
            	 var m = $.ligerDialog.open({ 
   	        	  title: '修改部门信息',
   	        	  height: 400, 
   	        	  width: 700, 
   	        	  url: 'organizationModifyAction?deptId='+row.deptId,	        	
   	        	   showMax: true,	        	   
   	        	  isResize: true,   
   	        	  slide: false,  	        
   	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
   	          });
            	 m.max();
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
   

        
        
         function f_delete()
        {
           
            var manager = $("#maingrid4").ligerGetGridManager();
            if(!manager) {
            		$.ligerDialog.error('请查询部门清单后,选中需要删除的条目,再删除！');
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要删除的行！'); return; }
            	$.ligerDialog.confirm('确认要删除该部门吗?若删除,会删除该部门及额度和部门下的用户！', function (yes)
                     {
              
                     if(yes)
                     {
                     
                     
                             $.ajax({   
				              url :'organizationListDeleteAction?deptId='+row.deptId,  //后台处理程序   
				              type:'post',    //数据发送方式   
				              dataType:'text',   //接受数据格式   
				              data:null,   //要传递的数据；就是上面序列化的值   
				              success:function(result){
				            	 
                    		  	if(result=='success'){
                    		  		manager.deleteSelectedRow();
                    		  		$.ligerDialog.success('删除成功！');
                    		  	}else{
                    		  		$.ligerDialog.error('系统异常,删除失败,请重试或联系系统管理员！');
                    		  	}
				              } //回传函数(这里是函数名)    
					     }); 
                     
                     
       /*                       
                     	var xhr = getXMLHttpRequest();
    					xhr.open("POST", "organizationListDeleteAction?deptId="+row.deptId, true);   
    					xhr.send(); 
    					xhr.onreadystatechange = function() { 
       						if (xhr.readyState == 4 && xhr.status == 200) {  
           					var responseContext = xhr.responseText;
                            if(responseContext=='success') 
                            {
                            	manager.updateSelectedRow();
                            	$.ligerDialog.success('禁用成功！');
                            }else
                            {
                            	$.ligerDialog.error('对不起，您无权限禁用用户,如确定要禁用,请联系系统管理员！');
                            }
        
       	   
       }   
  
    }   
              */       
                     }
                      
                     });
            	//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
            } 
        }
        
        
 
        
        
        function f_closeAddWindow(item, dialog)
        {
            
           $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
                     {
                     if(yes)
                         dialog.close();
                     });
            
        }
        
        
        
        function getArgs(strParame) {
         var args = new Object();
         var query = location.search.substring(1); // Get query string
         var pairs = query.split("&"); // Break at ampersand
         for (var i = 0; i < pairs.length; i++) {
             var pos = pairs[i].indexOf('='); // Look for "name=value"
             if (pos == -1) continue; // If not found, skip
             var argname = pairs[i].substring(0, pos); // Extract the name
             var value = pairs[i].substring(pos + 1); // Extract the value
             value = decodeURIComponent(value); // Decode it, if needed
             args[argname] = value; // Store as a property
         }
         return args[strParame]; // Return the object
     }

        
           
         function f_initGrid()
        {
        var actionAdr = encodeURI(encodeURI("organizationListQueryAction?deptId="+getArgs('deptId')));
        grid = manager = $("#maingrid4").ligerGrid({
                columns: [
                { display: '营销中心', name: 'saleCentre',width: 80, minWidth: 30 },
                { display: '网点ID', name: 'deptId',  width: 50, minWidth: 40,hide:1 },
                { display: '网点名称', name: 'deptName', width: 80, minWidth: 40},
                { display: '公司名称', name: 'companyName', width: 100, minWidth: 40},
                { display: '父部门', name: 'parentDeptName',id:'parentDeptName', width: 80, minWidth: 40},
                { display: '配件保证金', name: 'totalCredit',id:'totalCredit',width: 70, minWidth: 40},
               // { display: '可用额度', name: 'availableCredit',id:'availableCredit', width: 70, minWidth: 40},
                { display: '合同保证金', name: 'contractCredit',id:'contractCredit', width: 70, minWidth: 40},
                //{ display: '待减额度', name: 'changeCredit', width: 70, minWidth: 40},
                { display: '服务电话', name: 'phoneNum', width: 80, minWidth: 40 },
                { display: '物流地址', name: 'deptAddress',width: 100, minWidth: 40},                
                { display: '收件人', name: 'transferUserName', width:  70, minWidth: 40 },
                { display: '收件人电话', name: 'transferUserPhone', width: 80, minWidth: 40 },
                { display: '时间', name: 'inDate', width: 80, minWidth: 40 },
                { display: '邮编', name: 'postNum', width: 50, minWidth: 40 }
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:true, checkbox:false,
                url: actionAdr,allowAdjustColWidth:true,
                width: '100%', height: '98%', 
                toolbar: toolBar
            });
        
        }
        
           function f_search()
        { 
            grid.loadData(f_getWhere());
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
body{ 
font-size:12px;

}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
.blackbold { /* 标题样式 */
	line-height: 22px;
	width: 100px;
	height: 22px;
	font-size: 12px;
	font-weight: bold;
	color: #FF5317
}

.blackbold_b { /* 标题样式 */
	line-height: 22px;
	width: 150px;
	height: 22px;
	font-size: 12px;
	font-weight: bold;
	color: #FF5317
}

    </style>
    

</head>
<body style="padding:6px; overflow:hidden;width:100%;height:100%">
<table width="100%">
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr>
		<td class="blackbold_b"><img src="../images/util/arrow6.gif" hspace="5">部门列表</td>
	</tr>
	    
</table>


<div id="maingrid4" style="margin:0; padding:0"></div>
<div style="display: none;"> </div>




 
</body>
</html>
