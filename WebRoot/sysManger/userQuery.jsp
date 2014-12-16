<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<html>
<head>
<title>用户查询</title>
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
	                alert(v.element($("#gadgetsDescription2")));
	            });
            $("form").ligerForm();
           
          f_initGrid();
     
            manager= $("#maingrid4").ligerGetGridManager();
           // $("#txtUserGroup").ligerComboBox({ data: null, isMultiSelect: true, isShowCheckBox: true });
             
        }); 
        
        
    
        
        
       
         
       
       
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
                { display: '序号', name: 'orderId',width: 50, minWidth: 60 },
                { display: '登录名', name: 'userId',  width: 150, minWidth: 60 },
                { display: '用户姓名', name: 'userName', width: 150, minWidth: 60},
                { display: '用户部门', name: 'deptName', width: 150, minWidth: 60 },                
                { display: '用户所属组', name: 'groupName', width: 150, minWidth: 60},
                { display: '状态', name: 'userState', width: 50, minWidth: 60 }
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:false, checkbox:false,
	                url: 'userListQueryAction',allowAdjustColWidth:true,
	                width: '100%', height: '98%' 
	       
            });
                   
        }
        
        function f_query()
         {        
        	if (!manager) return;
        	var txtName=encodeURI($("#txtName").val());
        	var txtUserId= encodeURI($("#txtUserId").val());
        	var parentDeptName=encodeURI($("#parentDeptName").val());
        	var hidParentDeptId=encodeURI($("#hidParentDeptId").val());
            manager.setOptions(
                { parms: [{ name: 'txtName', value: txtName},{name:'txtUserId',value:txtUserId},{name:'parentDeptName',value:parentDeptName},{name:'hidParentDeptId',value:hidParentDeptId}] }
            );
            

            manager.loadData(true);
         }
         
         function f_query_all()
         {        
        	if (!manager) return;
        	document.getElementById('txtName').value='全部';
            document.getElementById('txtUserId').value='全部';
            document.getElementById('parentDeptName').value='全部';
            document.getElementById('hidParentDeptId').value='';
            
        	var txtName =encodeURI($("#txtName").val());
        	var txtUserId = encodeURI($("#txtUserId").val());
        	var parentDeptName = encodeURI($("#parentDeptName").val());
        	var hidParentDeptId = encodeURI($("#hidParentDeptId").val());
            manager.setOptions(
           		 { parms: [{ name: 'txtName', value: txtName},{name:'txtUserId',value:txtUserId},{name:'parentDeptName',value:parentDeptName},{name:'hidParentDeptId',value:hidParentDeptId}] }
            );
            
            manager.loadData(true);
         }
   
         function selectParentRole(obj) {
			var time=new Date();
			var url="pub/organizationAllDeptTree.jsp";
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
      
     function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
      if(result='success'){   
           $.ligerDialog.success('更改成功!');
            var manager = $("#maingrid4").ligerGetGridManager();
         manager.loadData();
        }else{   
          
            alert('系统异常,删除失败,请重试或联系系统管理员！');
              
       }   
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
               <td align="right" class="l-table-edit-td">登录名:</td>
                <td align="left" class="l-table-edit-td">
                    <input name="txtUserId" type="text" id="txtUserId" ltype="text" />
                </td>           
                <td align="right" class="l-table-edit-td">用户姓名:</td>
                <td align="left" class="l-table-edit-td">
                <input type="text" id="txtName" name="txtName" ltype="text" />
                </td>
           		<td align="right" class="l-table-edit-td">部门:</td>
                <td align="left" class="l-table-edit-td">
                     <input  type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
                     <input name="parentDeptName" type="text" id="parentDeptName" ltype="text" value="点击选择部门" readonly="readonly" onclick="selectParentRole(this)" />
                </td>
           </tr>   
           <tr>
            <td align="right" class="l-table-edit-td">
                	<input type="button" value="查询" id="Button1" class="l-button"  style="width:80px" onclick="f_query()"/> 
                </td>
                <td align="left" class="l-table-edit-td">
                  	<input type="button" value="查询所有" id="Button1" class="l-button" style="width:100px"  onclick="f_query_all()"/>   
                </td>
           </tr>            
        </table>
			    </form>
        		
      		 </fieldset>


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
