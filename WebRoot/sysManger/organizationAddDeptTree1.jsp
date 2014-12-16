<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<html>
<head>
<title>公告管理</title>
    <link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>       
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
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>   
   <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
    
    <script type="text/javascript">
        $(function ()
        {
            $("#tree").ligerTree({  
            url: 'messageDeptTree',checkbox: true
                /*{ text: '管理平台', children: [
                  //  { text: '省级维修中心(歇台子)' },
                   // { text: '省级维修中心B' },
                   /* { text: '主城接机点', children: [
                         { text: '杨家坪接机点(合)' },
                         { text: '沙坪坝接机点(合)' },
                         { text: '大学城接机点(合)' }
                    ]
                    }, 
                    { text: '巴南维修中心(合)' , children: [
                         { text: '綦江接机点(合)' },
                         { text: '南川接机点(合)' },
                         { text: '万盛接机点(合)' }
                    ]
                     },
                    { text: '渝北维修中心(合)' },
                    { text: '永川维修中心(合)' , children: [
                         { text: '大足接机点(合)' },
                         { text: '璧山接机点(合)' },
                         { text: '江津接机点(合)' },
                         { text: '荣昌接机点(合)' }
                    ] },
                    { text: '合川维修中心(合)' },
                    { text: '万州维修中心(合)'  , children: [
                         { text: '开县接机点(合)' },
                         { text: '云阳接机点(合)' },
                         { text: '梁平接机点(合)' },
                         { text: '城口接机点(合)' }
                    ]
                    },
                    { text: '涪陵维修中心(合)'  , children: [
                         { text: '忠县接机点(合)' },
                         { text: '石柱接机点(合)' },
                         { text: '垫江接机点(合)' },
                         { text: '丰都接机点(合)' },
                         { text: '武隆接机点(合)' }
                         
                    ] },
                    { text: '黔江维修中心(合)' , children: [
                         { text: '酉阳接机点(合)' },
                         { text: '秀山接机点(合)' },
                         { text: '彭水接机点(合)' }
                    ]},
                    { text: '奉节维修中心(合)' , children: [
                         { text: '巫山接机点(合)' },
                         { text: '巫溪接机点(合)' }
                    ]},
                    { text: '丽苑维修中心(合)' },
                    { text: '较场口维修中心(合)' },
                    { text: '江北维修中心(合)' },
                    { text: '南坪维修中心(合)' },
                    { text: '歇台子维修中心(合)' },
                    { text: '合川维修中心1(合)', children: [
                         { text: '铜梁接机点(合)' },
                         { text: '潼南接机点(合)' }
                        
                    ] },
                    { text: '涪陵维修中心(唐波网点)' },
                    { text: '北碚维修中心(合)' },
                    { text: '长寿维修中心(合)' }
                 ]
                }
                url:messaggeDeptTree;
            ]*/
            });
           manager = $("#tree").ligerGetTreeManager();
        });
        function getChecked()
        {
        	
        	var obj=document.getElementsByName('isAll');
        	var notes = manager.getChecked();
            var text = "";
        	
        	if(obj[0].checked){
        		text = '所有部门';
        	 } else {
        		 for (var i = 0; i < notes.length -1; i++)
                 {
                     
                     text += notes[i].data.text + ";";
                 }
                 if(i ==notes.length -1){
                          text += notes[i].data.text ;
                 }      		 
        	 }
        	
            
            	    		  	
            
            window.returnValue = text;
            window.close();
        }
    </script>
</head>
<body style="padding:10px">   
    <div style="width:300px; height:400px; margin:10px; float:left; border:1px solid #ccc; overflow:auto;">
    <ul id="tree"></ul>
    </div> 
 <br />
 		<div >全选&nbsp;&nbsp;&nbsp;<input name="isAll" value="all" type="checkbox" /></div>
 		<div style="margin-bottom:15px;margin-top:5px;">(温馨提示：发往所有部门勾选“全选”框即可)</div>
        <a class="l-button" onclick="getChecked()" style="float:left;margin-right:10px;">确认</a> 
    <br />
        <div style="display:none">
     
     
    </div>
</body>
</html>
