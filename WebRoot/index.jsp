<%@ page contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="com_css/uploadify-v3.1/uploadify.css">
    <script type="text/javascript" src="com_css/js/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="com_css/uploadify-v3.1/jquery.uploadify-3.1.min.js"></script>
   
  </head>
 <script type="text/javascript">
$(function () {
            $('#file_upload').uploadify({
               // 'debug': true,                             //是否开启调试模式 
                'auto': false,                            //是否自动上传 
                'multi': false,                            //是否允许多个上传 
                'removeCompleted': false,                            //上传完毕上传列表是否去除 
                'progressData': 'percentage',                     //进度 percentage或speed 
                'buttonText': 'BROWSE...',                      //flash按钮文字 
				
                //  'formData': {'id':'测试','af':'ddd'},           //表单数据  json格式 

                'method': 'post',                           //提交方法  post或get 
                'fileSizeLimit': '50MB',                           //上传文件大小设置 单位可以是B、KB、MB、GB
                //          'fileTypeDesc'      : 'description',                    //弹窗选择文件框类型处提示 

                //          'fileTypeExts'      : '*.gif; *.jpg; *.png; *.zip',     //文件类型 
                'swf': 'com_css/uploadify-v3.1/uploadify.swf',                  //swf位置 
                'width': 200,                              //flash按钮宽度 
              //  'height': 100,                              //flash按钮高度 
				'cancelImage': 'com_css/uploadify-v3.1/uploadify-cancel.png',//[必须设置]取消图片的路径			
				
                'uploader': 'com_css/uploadify-v3.1/uploadify.php',                  //提交到的处理页面 
                'uploadLimit': 1,                                //上传个数限制 
                'onUploadComplete': function (file) {                  //上传完成时事件 
                    alert('The file ' + file.name + ' finished processing.');
                    $('#file_upload').uploadify('disable', true);       //设置上传按钮不可用 
                },

                'onUploadError': function (file, errorCode, errorMsg, errorString) {    //错误提示 
                    alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
                }

 

                // Your options here 

            });

        });

        function changeBtnText() {
            $('#file_upload').uploadify('settings', 'formData', { 'id': '测试', 'af': 'ddd' }); //settings可以设置上面各种参数也。

        }

        function uploadstart() {
            $('#file_upload').uploadify('settings', 'formData', { 'id': 123, 'sid': 22, 'pid': 333 });   //设置表单数据 
            $('#file_upload').uploadify('upload');                                              //开始上传 
        } 

  

    </script>

<body>
    <input id="file_upload" type="file" name="file_upload" />
    <a href="javascript:$('#file_upload').uploadify('upload')">Upload Files</a>
     | <a href="javascript:$('#file_upload').uploadify('cancel')">Cancel First File</a>
    | <a href="javascript:$('#file_upload').uploadify('cancel', '*')">Clear the Queue</a>
    | <a href="javascript:$('#file_upload').uploadify('upload', '*')">Upload the Files</a>
    | <a href="javascript:$('#file_upload').uploadify('disable', true)">Disable the Button</a>
    | <a href="javascript:$('#file_upload').uploadify('disable', false)">Enable the Button</a>
    | <a href="javascript:$('#file_upload').uploadify('stop')">Stop the Uploads!</a>
    <a href="javascript:uploadstart()">开始上传</a> 

</body>

</html>
