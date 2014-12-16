<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
    <link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
 
    <link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>       
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>          
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
     <link href="com_css/jqueryUI/themes/base/jquery.ui.all.css" rel="stylesheet">
	<script src="com_css/jqueryUI/ui/jquery.ui.core.js"></script>
	<script src="com_css/jqueryUI/ui/jquery.ui.widget.js"></script>
	<script src="com_css/jqueryUI/ui/jquery.ui.position.js"></script>
	<script src="com_css/jqueryUI/ui/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript">
    var grid = null;   
    var manager=null;  
        $(function() {
	        	/*<pageADMOperAuth.tld:pageADMOperAuth menuId="'100'"></pageADMOperAuth.tld:pageADMOperAuth>*/
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
	        //f_initGrid(); 
	       $("form").ligerForm();
	       f_query();
	      
        }); 
        function f_closeAddWindow(item, dialog)
        {
            
           $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
                     
                     {
                     if(yes)
                         dialog.close();
                     });
            
        }
		function f_query_all()
		{
			 window.location.reload();
		}
       
        function f_query()
         {        
        	var machineType = encodeURI(encodeURI($("#machineType").val()));
        	var txtBeginDate = encodeURI($("#txtBeginDate").val());
        	var txtEndDate = encodeURI($("#txtEndDate").val());
        	var url = "performanceQueryAction?machineType="+machineType+"&txtBeginDate="+txtBeginDate+"&txtEndDate="+txtEndDate;
        	$('#lineChart').attr("src", url);  
        	
        	return;
            /*manager.setOptions(
                { parms: [{name:'machineType',value:machineType},{name:'ip',value:ip},{name:'metricsCode',value:metricsCode}] }
            );
            manager.loadData(true);*/
         }

	       
     function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
      if(result='notexist'){   
           $.ligerDialog.error('您所查询的单号不存在，请核实后再查!');
            
        }else{   
          
         var manager = $("#maingrid4").ligerGetGridManager();
          manager.loadData();
              
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

<body style="padding:6px; overflow:scroll;">
	<table width="100%">
		<tr>
			<td height="2"></td>
		</tr>
		<tr>
			<td class="blackbold"><img src="images/util/arrow6.gif"
				hspace="5">筛选条件</td>

		</tr>
		<tr>
			<td height="1" bgcolor="#BBD2E9"></td>
		</tr>

	</table>
	<fieldset style="top:inherit;width:100%; margin:10px;">
		<form name="form1" method="post" action="" id="form1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">			
				<tr>
					<td align="right" class="l-table-edit-td">存取钱:</td>
					<td>
						<select id="machineType" name="machineType">
							<option value="PC" selected="selected">存钱</option>
							<option value="VM">取钱</option>
						</select>
					<td align="right" class="l-table-edit-td">开始时间:</td>
					<td align="left" class="l-table-edit-td">
						<input name="txtBeginDate" type="text" id="txtBeginDate"  ltype="date"/>
					</td>
					<td align="right" class="l-table-edit-td">结束时间:</td>
					<td align="left" class="l-table-edit-td">
						<input name="txtEndDate" type="text" id="txtEndDate"  ltype="date"/>
					</td>	
					<td align="right" class="l-table-edit-td">
						<input type="button" value="查询" id="Button1" class="l-button" style="width:80px" onclick="f_query()"/>
					</td>
					<td><input type="button"	value="查询全部" id="Button2" class="l-button" style="width:80px" onclick="f_query_all()" /></td>
						<td align="left" class="l-table-edit-td"></td>
					
				</tr>
					<!--<td align="right" class="l-table-edit-td"><input type="button"
						value="查询所有" id="Button1" class="l-button" style="width:80px"
						onclick="f_query_all()" /></td>	
						-->
				</tr>
			</table>
		</form>
	</fieldset>

    
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
		<td class="blackbold_b"><img src="images/util/arrow6.gif" hspace="5">存取钱记录图</td>
	</tr>
	    
</table>

<iframe id="lineChart" src="performanceQueryAction" height="450px" 
	width="900px" frameborder="0"></iframe>
<div id="maingrid4" style="margin:0; padding:0"></div>
<div style="display: none;"> </div>
</body>
</html>
