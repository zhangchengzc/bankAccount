<%@ page contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
        <script src="com_css/js/jquery.blockUI.js" type="text/javascript"></script>
    <script type="text/javascript">
    var grid = null;   
    var manager=null;  
        $(function() {
            $.metadata.setType("attr", "validate");
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
	        f_initGrid();
            $("form").ligerForm();
            get_curDay();
	        get_lDay();
           // $("#txtUserGroup").ligerComboBox({ data: null, isMultiSelect: true, isShowCheckBox: true });
             
        }); 
        
        
        
        
        
       
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
                { display: '公告内容', name: 'messageContent', width: 150, minWidth: 100},
                { display: '发往网点总数', name: 'totalCount', width: 100, minWidth: 60 }, 
                { display: '已阅读', name: 'readedCount', width: 50, minWidth: 60 },                
                { display: '未阅读', name: 'unReadCount', width: 50, minWidth: 60 },                               
                { display: '发起时间', name: 'sendTime', width: 150, minWidth: 60},
                { display: '发起人', name: 'sendUser', width: 80, minWidth: 60 }
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:true,
	                url: 'messageReadDetailQueryAction',allowAdjustColWidth:true,
	                width: '100%', height: '98%',frozen:false,
	               detail: { onShowDetail: f_showOrder }
            });
                   
        }
        
        function f_showOrder(row, detailPanel,callback)
	         {
	         
	             var actionUrl = "messageDeptReadDetailAction?messageId="+row.messageId;
	             actionUrl1 = encodeURI(encodeURI(actionUrl));
            	 var gridDetail = document.createElement('div'); 
	             $(detailPanel).append(gridDetail);
	             $(gridDetail).css('margin',10).ligerGrid({
	                 columns:
                            [
                            { display: '网点名称', name: 'deptName',width: 150 , minWidth: 60},
                            { display: '阅读状态', name: 'readStatus', width: 100, minWidth: 60,
                        render: function (row)
					    {
							
					        if (row.readStatus != '0')
					                return  '<font color="#ff0000">未阅读</font>'; 
					        else return "已阅读";
					       
					    } },
                            { display: '阅读人', name: 'readUser', width: 150, minWidth: 60},
                			{ display: '阅读时间', name: 'readTime', width: 150, minWidth: 60}                 
                            ], dataAction: 'server', pageSize:10,where : f_getWhere(),isScroll: false, showToggleColBtn: false,
                            url: actionUrl1, showTitle: false, columnWidth: 100, width: '90%',
                            onAfterShowData: callback,frozen:false
                  
	             });  
	             $(detailPanel).show();    
	         
	        }
         function f_query()
         {        
        	if (!manager) return;	        	
        	var messageType = encodeURI($("#messageType").val());	        	
        	var messageTitle = encodeURI($("#messageTitle").val());
        	var toDeptId = encodeURI($("#toDeptId").val());
        	var txtBeginDate = encodeURI($("#txtBeginDate").val());
        	var txtEndDate = encodeURI($("#txtEndDate").val());
        	
            manager.setOptions(
                { parms: [{name:'messageType',value:messageType},{name:'toDeptId',value:toDeptId},{name:'messageTitle',value:messageTitle},{name:'txtBeginDate',value:txtBeginDate},{name:'txtEndDate',value:txtEndDate}] }
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
            var toDeptId = encodeURI($("#toDeptId").val());
        	var messageTitle = encodeURI($("#messageTitle").val());
        	var txtBeginDate = encodeURI($("#txtBeginDate").val());
        	var txtEndDate =encodeURI($("#txtEndDate").val());
        	
            manager.setOptions(
                 { parms: [{name:'messageType',value:messageType},{name:'toDeptId',value:toDeptId},{name:'messageTitle',value:messageTitle},{name:'txtBeginDate',value:txtBeginDate},{name:'txtEndDate',value:txtEndDate}] }
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
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
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
