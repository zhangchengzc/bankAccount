<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld"
	prefix="pageADMOperAuth.tld"%>
<html>
	<head>
		<title>帮助~O(∩_∩)O</title>
		<link
			href="../com_css/LigerUILib/ligerUI/skins/Silvery/css/ligerui-form.css"
			rel="stylesheet" type="text/css" />
		<link
			href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-common.css"
			rel="stylesheet" type="text/css" />
		<link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css"
			rel="stylesheet" type="text/css" />
		<script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/ligerUI/js/ligerui.min.js"
			type="text/javascript">
</script>
		<script
			src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js"
			type="text/javascript">
</script>

		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript">
</script>
		<link
			href="../com_css/LigerUILib/ligerUI/skins/Silvery/css/ligerui-dialog.css"
			rel="stylesheet" type="text/css" />
		<link
			href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-grid.css"
			rel="stylesheet" type="text/css" />
		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript">
</script>
		<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js"
			type="text/javascript">
</script>
<script type="text/javascript">
var grid = null;
var manager = null;
$(function() {
            f_initGrid();
            $("form").ligerForm();
            $(".l-button-test").click(function() {
                alert(v.element($("#txtName")));
            });
        }); 
        function CheckValue(){
        	if($("#title").val() == null) return;
        	manager= $("#maingrid4").ligerGetGridManager();
        }
        
        function f_add()
        {
            $.ligerDialog.open({ title: '新增商品信息', width: 700, height: 400, url: 'storage/commodityAdd.jsp', buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            ]
            });
        }
         function f_query()
         {     
         	//alert('123');   
        	if (!manager) return;
        	
        	var title= encodeURI($("#title").val());
        	 alert(title);
        	var writer=encodeURI($("#writer").val());
            manager.setOptions(
                { parms: [{ name: 'title', value: title},{name:'writer',value:writer}] }
            );
            manager.loadDataInit(true);
           
         }
       
       function f_query_all()
         {        
        	if (!manager) return;
           	document.getElementById('title').value='全部';
            document.getElementById('writer').value='全部';
        	var title= encodeURI($("#title").val());
        	var writer=encodeURI($("#writer").val());
            manager.setOptions(
                { parms: [{ name: 'title', value: title},{name:'writer',value:writer}] }
            );

            manager.loadDataInit(true);
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
            		$.ligerDialog.error('请查询商品清单后,选中需要修改的条目,再修改！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要修改的行！'); return; }
       
            	  $.ligerDialog.open({ title: '修改商品信息', width: 700, height: 400, url: 'commodityRecordModifyAction?prodId='+row.prodId, buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            	]
            	});
            }
        }
        
         function f_delete()
        {
           
            var manager = $("#maingrid4").ligerGetGridManager();
           
            if(!manager) {
            		$.ligerDialog.error('请查询商品清单后,选中需要删除的条目,再删除！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要删除的行！'); return; }
            	$.ligerDialog.confirm('确认要删除该商品吗?若删除,会删除该商品及其所有供应记录', function (yes)
                     {
              
                     if(yes)
                     {
                     	var xhr = getXMLHttpRequest();
    					xhr.open("POST", "commodityRecordDeleteAction?prodId="+row.prodId, true);   
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
                            	$.ligerDialog.error('系统异常,删除失败,请联系系统管理员！');
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
        grid = $("#maingrid4").ligerGrid({
                columns: [
                { display: '序号', name: 'orderId',width: 40, minWidth: 40 },
                { display: '帮助文档名称', name: 'title',  width: 240, minWidth: 60 },
                { display: '作者', name: 'writer',  width: 100, minWidth: 60 },
                { display: '类型', name: 'category', width: 90, minWidth: 60},
                { display: '上传时间', name: 'time', width: 160, minWidth: 60},
                { display: '状态', name: 'state', width: 170, minWidth: 60},
                { display: '备注', name: 'description', width: 190, minWidth: 200},
                { display: '操作', isAllowHide: false,width: 140,
                    render: function (row)
                    {
                    	var html;
                           	html = '<a href="../helpDoc/1.pdf" target="_self">查看</a>';
                        return html;
                    }
                   }
                ], dataAction: 'server',pageSize:30,where : f_getWhere(),
                url: 'helpListQueryAction', sortName: 'orderId',
                width: '100%', height: '98%'
            });
                   
        }
        
        
         
      
             function onedit(prodId,categoryName,brandName,prodVersion)

        {
			 $.ligerDialog.confirm('确认要更改该商品的供应状态吗? 商品类型:'+categoryName+',商品品牌:'+brandName+',商品型号:'+prodVersion, function (yes)
                     {
                     var serverURL = 'commodityStateChangeAction?prodId='+prodId;
                     if(yes)
                       $.ajax({   
              				url : serverURL,  //后台处理程序   
             				type:'post',    //数据发送方式   
              				dataType:'text',   //接受数据格式   
              				success:submit_Result //回传函数(这里是函数名)    
       					});   
                         
                     })
        
    	}   
         
          function submit_Result(result){ //回传函数实体，参数为XMLhttpRequest.responseText   
      if(result='success'){   
           $.ligerDialog.success('更改成功!');
            var manager = $("#maingrid4").ligerGetGridManager();
         manager.loadData();
        }else{   
          
            alert('系统异常,更改失败,请重试或联系系统管理员！');
              
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
body {
	font-size: 12px;
}

.l-table-edit {
	
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

.l-verify-tip {
	left: 230px;
	top: 120px;
}

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
	<body style="padding: 6px; overflow: hidden;">
		
		<table width="100%">
			<tr>
				<td colspan="2" height="5"></td>
			</tr>
			<tr>
				<td class="blackbold">
					<img src="../images/util/arrow6.gif" hspace="5">
					查询条件
				</td>
			</tr>
			<tr>
				<td colspan="2" height="1" bgcolor="#BBD2E9"></td>
			</tr>
			
		</table>
		<div style="display: none">
			<!--  数据统计代码 -->
		</div>
		<fieldset style="top: inherit; width: 100%">
			<form name="form1" method="post" action="supplierQuery" id="form1">
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td">
							帮助文档标题:
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="text" id="title" onchange="CheckValue()" />
						</td>

						<td align="right" class="l-table-edit-td">
							上传用户:
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="text" id="writer" />
						</td>
					</tr>
				</table>
				<br/>
				<input type="button" value="查询" id="Button1"
					class="l-button l-button-submit" onclick="f_query()" />
				<input type="button" value="查询所有" class="l-button l-button-submit"
					onclick="f_query_all()" />
			</form>
		</fieldset>
		<table width="100%" id="result">
			<tr>
				<td colspan="2" height="1" bgcolor="#BBD2E9"></td>
			</tr>
			<tr>
				<td colspan="2" height="5"></td>
			</tr>
			<tr>
				<td class="blackbold_b">
					<img src="../images/util/arrow6.gif" hspace="5">
					商品信息
				</td>
			</tr>
		</table>
		<div id="maingrid4" style="margin: 0; padding: 0"></div>
	</body>
</html>
