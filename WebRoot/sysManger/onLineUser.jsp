<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% String userId = (String)session.getAttribute("userId");%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
    <script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script type="text/javascript">
    var grid = null;
    var gridManager = null;
		$(function () {            
            f_initGrid();
			gridManager = $("#maingrid4").ligerGetGridManager();
            $("#pageloading").hide();
            
            if("<%=userId%>"!="admin")
            {
            	$("#hide").hide();
            	/* var oDiv=document.getElementById("hide");
            	oDiv.style="display:none"; */
            }
        });
     function f_initGrid()
        {
        	grid = $("#maingrid4").ligerGrid({
                checkbox: true,
                columns: [
                { display: '用户ID', name: 'userID',width: 200 },
                { display: '用户姓名', name: 'userName', width: 200 },
                 { display: '部门', name: 'userDept', width: 200 },
                { display: '登陆IP', name: 'userIp', width: 200 },
                { display: '登陆时间', name: 'loginTime', width: 200}
                
                ], dataAction: 'server',pageSize:50,where : f_getWhere(),
                url: 'onLineUserAction', sortName: 'userId',
                width: '100%', height: '100%'
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
                return rowdata.userID.indexOf(key) > -1;
            };
            return clause; 
        }
        /*
        该例子实现 表单分页多选
        即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
        */

        function f_getChecked()
        {
        	var data = gridManager.getCheckedRows();
            if (data.length == 0)
            	 $.ligerDialog.error('请选择需要强制退出登录的用户!');
            else {
             	 var checkedIds = [];
          		 $(data).each(function (){
         		 checkedIds.push(this.userID);
          });
        $.ligerDialog.confirm('确定强制用户' + checkedIds.join(',') + '退出吗?', function (yes)
          {
        	    if(yes){
			          var actionAdr = encodeURI(encodeURI("onlineUserForceExitAction?userId="+checkedIds.join(',')));
			          $.ajax({
			                type: 'POST',
			                url: actionAdr,
			                success: function (data)
			                {
			                if(data=='sucess')
			                {
			                	gridManager.deleteSelectedRow();
			                	 $.ligerDialog.success('退出成功!');
			                } else 
			                {
			                	 $.ligerDialog.error('退出失败:'+data);
			                }
			                    
			                }
			            });
			           }
			  }); 
          
        }
        }
       
    </script>
</head>
<body style="padding:6px; overflow:hidden;">
<div class="l-loading" style="display:block" id="pageloading"></div> 
  <form>
  <div id ="hide">
 		<input type="button" value="强制用户退出登陆" onclick="f_getChecked()" class="l-button l-button-submit" style="width: 130px; height: 27px"/>
  </div>
   
  </form>
   <table width="100%" id="result">
   	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr >
		<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
	</tr>
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	</table>
 <div id="maingrid4" style="margin:0; padding:0"></div>
 <div style="display: none;"></div>

</body>
</html>
