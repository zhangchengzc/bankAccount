<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告管理</title>
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
            
            <pageADMOperAuth.tld:pageADMOperAuth menuId="'010501','010504','010503'"></pageADMOperAuth.tld:pageADMOperAuth>
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
            get_curDay();
	        get_lDay();
            manager= $("#maingrid4").ligerGetGridManager();
           // $("#txtUserGroup").ligerComboBox({ data: null, isMultiSelect: true, isShowCheckBox: true });
             
        }); 
        
        
        function f_add()
        {
            /*$.ligerDialog.open({ title: '新增用户信息', width: 650, height: 300,modal:false,url: 'sysManger/userManagerAdd.jsp', buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            ]
            });*/
            var m =$.ligerDialog.open({ 
	        	  title: '新增公告信息',
	        	  height: 400, 
	        	  width: 700, 
	        	  url:'sysManger/messageAdd.jsp',	        	
	        	   showMax: true,	        	   
	        	  isResize: true,   
	        	  slide: false,  	        
	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
	          }); 
	          m.max();
        }
        
        
       
        function get_curDay() {
        	var today = new Date();   
    		var day = today.getDate();   
    		var month = today.getMonth() + 1;   
    		var year = today.getFullYear(); 
    		if(month*1 < 10){
    			month = '0'+month;
    		}
    		if(day*1 < 10){
    			day = '0'+day;
    		}
    		var date = year + "-" + month + "-" + day;  
    		$('#txtEndDate').val(date);
    		
        }
        
         function get_lDay() {   //获取本月1号
        	var today = new Date();
    		var month = today.getMonth() + 1;   
    		var year = today.getFullYear(); 
    		if(month*1 < 10){
    			month = '0'+month;
    		}
    		var date = year + "-" + month + "-" + "01";  
    		$('#txtBeginDate').val(date);
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
		
         function f_read()
         {
            if(!manager) {
            		$.ligerDialog.error('请查询清单后,选中条目,再修改！')
            		return;
            } else {
            	var row = manager.getSelectedRows();
            	if (row.length==0) { 
            	$.ligerDialog.error('请选择要查看的公告！'); 
            	return;}
            	else if(row.length>1){ 
		 	   $.ligerDialog.error('只能选择一项！'); 
		 	   return;
		 	   }else{	
            	var m = $.ligerDialog.open({ 
  	        	  title: '查看公告信息',
  	        	  height: 400, 
  	        	  width: 700, 
  	        	  url: 'messageReadAction?messageId='+row[0].messageId,	        	
  	        	   showMax: true,	        	   
  	        	  isResize: true,   
  	        	  slide: false,  	        
  	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
  	          }); 
  	          m.max();
            	}	     		
                }
             manager.loadData(true);
            
             }         
        
       
         function f_delete()
        {
           
            var manager = $("#maingrid4").ligerGetGridManager();
            if(!manager) {
            		$.ligerDialog.error('请查询用户清单后,选中需要删除的条目,再删除！')
            		return;
            } else {
		         	var row = manager.getCheckedRows();
		 	   if (row.length == 0) { $.ligerDialog.error('请选择需要删除的行！'); 
		 	   return;
		 	    }else if(row.length>1){ 
		 	   $.ligerDialog.error('只能选择一项！'); 
		 	   return;
		 	   }else{
		 			$.ligerDialog.confirm('确认要删除选中的公告单吗？删除后不能恢复，请谨慎操作！', function (yes) {              
		          if(yes)
		          {
				var messageId = row[0].messageId;	
				$.ajax({   
		            url :'messageDeleteAction?messageId='+messageId,  //后台处理程序   
		            type:'post',    //数据发送方式   
		            dataType:'text',   //接受数据格式   
		            data:null,   //要传递的数据；就是上面序列化的值   
		            success:function(result){
		          	 
		  		  	if(result=='success'){
		  		  		manager.deleteSelectedRow();
		  		  		$.ligerDialog.success('公告单删除成功！');
		  		  	}
		                else{
		  		  		$.ligerDialog.error('系统异常,删除失败,请重试或联系系统管理员！');
		  		  	}
		            } //回传函数(这里是函数名)    
			     });
            }     });
            }
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
                { display: '公告id', name: 'messageId',width: 50, minWidth: 60 , hide :1},
                { display: '公告主题', name: 'messageTitle',  width: 150, minWidth: 60 },
                { display: '公告类型', name: 'messageType',  width: 150, minWidth: 60 },
                { display: '公告内容', name: 'messageContent', width: 300, minWidth: 100},
                { display: '附件名字', name: 'appendixName', width: 150, minWidth: 60 , hide:1},                
                { display: '发起时间', name: 'sendTime', width: 150, minWidth: 60},
                { display: '发起人', name: 'sendUser', width: 80, minWidth: 60 },
                { display: '状态', name: 'readStatus', width: 70, minWidth: 60 ,
                render: function (row)
				    {
						
				        if (row.readStatus != '0')
				                return  '<font color="#ff0000">未阅读</font>'; 
				        else return "已阅读";
				       
				    }
				    }
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:true, checkbox:false,
	                url: 'messageManagerQueryAction',allowAdjustColWidth:true,
	                width: '100%', height: '98%', 
	                toolbar: toolBar
            });
                   
        }
        
         function f_query()
         {        
        	if (!manager) return;	        	
        	var messageType = encodeURI($("#messageType").val());	        	
        	var messageTitle = encodeURI($("#messageTitle").val());
        	var txtBeginDate = encodeURI($("#txtBeginDate").val());
        	var txtEndDate = encodeURI($("#txtEndDate").val());
        	
            manager.setOptions(
                { parms: [{name:'messageType',value:messageType},{name:'messageTitle',value:messageTitle},{name:'txtBeginDate',value:txtBeginDate},{name:'txtEndDate',value:txtEndDate}] }
            );
            

            manager.loadData(true);
         }
       
         
           function f_query_all()
         {        
        	if (!manager) return;
        	
        	$('#messageType').val('全部');
            $('#messageTitle').val('全部');
            $('#txtBeginDate').val('全部');
            $('#txtEndDate').val('全部');
            var messageType = encodeURI($("#messageType").val());
        	var messageTitle = encodeURI($("#messageTitle").val());
        	var txtBeginDate = encodeURI($("#txtBeginDate").val());
        	var txtEndDate =encodeURI($("#txtEndDate").val());
        	
            manager.setOptions(
                 { parms: [{name:'messageType',value:messageType},{name:'messageTitle',value:messageTitle},{name:'txtBeginDate',value:txtBeginDate},{name:'txtEndDate',value:txtEndDate}] }
            );

            manager.loadData(true);	             
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
<table width="100%">
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr>
		<td class="blackbold_b"><img src="images/util/arrow6.gif" hspace="5">筛选条件</td>
		
	</tr>
	<tr >
		<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
	</tr>

</table>   
    <div style="display:none">
    <!--  数据统计代码 --></div>

 <fieldset style="top:inherit;width:100%">
        		<form name="form1" method="post" action="supplierQuery" id="form1">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
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
               <td align="right" class="l-table-edit-td">公告名称:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="messageTitle" type="text" id="messageTitle"  />
                </td>           
           </tr>  
           <tr>
					<td align="right" class="l-table-edit-td">开始时间:</td>
					<td align="left" class="l-table-edit-td"><input
						name="txtBeginDate" type="text" id="txtBeginDate"  ltype="date"/>
					</td>
					<td align="right" class="l-table-edit-td">结束时间:</td>
					<td align="left" class="l-table-edit-td"><input
						name="txtEndDate" type="text" id="txtEndDate"  ltype="date"/></td>
						
					<td align="right" class="l-table-edit-td"><input type="button"
						value="查询" id="Button1" class="l-button" style="width:80px"
						onclick="f_query()" /></td>
					<td align="left" class="l-table-edit-td"><input type="button"
						value="查询所有" id="Button1" class="l-button" style="width:100px"
						onclick="f_query_all()" /></td>
				</tr>   
</table>
 </form>
        		
      </fieldset>
<table width="100%" id="result">
		<tr>
			<td  height="1" bgcolor="#BBD2E9"></td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="images/util/arrow6.gif"
				hspace="5">公告列表</td>
		</tr>

	</table>
<div id="maingrid4" style="margin:0; padding:0"></div>
<div style="display: none;"> </div>
</body>
</html>
