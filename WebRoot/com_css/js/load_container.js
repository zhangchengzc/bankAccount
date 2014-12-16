var isIe = (document.all)?true:false;
//显示透明的层覆盖整个页面
function showClarityDiv(oWindow) {
	var sWidth,sHeight; 
	sWidth = oWindow.document.body.clientWidth; 
	sHeight = oWindow.document.body.clientHeight; 
	var bgObj = oWindow.document.createElement("div"); 
	bgObj.setAttribute('id','clarityDiv'); 
	var styleStr = "top:0px;left:0px;position:absolute;background:#245;width:"+sWidth+"px;height:"+sHeight+"px;";  
	styleStr += "filter:alpha(opacity=5)";
	bgObj.style.cssText = styleStr;  
	oWindow.document.body.appendChild(bgObj);
	loadShowBackground(bgObj,5);
}

//让背景渐渐变暗  
function loadShowBackground(obj,endInt) {
	if(isIe) {
		obj.filters.alpha.opacity+=1; 
		if(obj.filters.alpha.opacity<endInt) {  
			setTimeout(function(){this.showBackground(obj,endInt)},5);  
		}  
	} else {
		var al=parseFloat(obj.style.opacity);
		al+=0.01;  
		obj.style.opacity=al;  
		if(al<(endInt/100)) {
			setTimeout(function(){this.showBackground(obj,endInt)},5);
		}  
	}
}

function hideClarityDiv(oWindow) {
	element = oWindow.document.getElementById("clarityDiv");
  	if (element != null && element != ""){
    	oWindow.document.body.removeChild(element);
  	}
}
//显示正在运行提示信息
function showLoadingDiv(oWindow, imgPath) {
  showClarityDiv(oWindow);
  oWindow.scrollTo(0,0);//add by zwz ,将页面重新定位到首行
  var runDiv = oWindow.document.createElement("DIV");
  runDiv.id = "__runDiv";
  runDiv.style.position = "absolute";
  runDiv.style.background = "";
  runDiv.style.top = oWindow.document.body.offsetHeight/2-80;
  runDiv.style.left = oWindow.document.body.offsetWidth/2-225;
  runDiv.style.width = 450;
  runDiv.style.height = 240;
  if(imgPath == null || imgPath.trim() == "") {
    imgPath = "../images"
  } else {
  	imgPath = "/" + imgPath + "/images";
  }
  var divStr = '<center>';
  divStr = divStr + '<table width="350"style="border:1px solid #18418d;" cellspacing="0" cellpadding="0">';
  divStr = divStr + '<tr>';
  divStr = divStr + '<td width="120" height="45" align="center"><img src="' + imgPath + '/running.gif" border="0"></td>';
  divStr = divStr + '<td><span id=txtLoading0 style="font-size:14px;color:#000000">程序正在运行,请稍候...</span></td>';
  divStr = divStr + '</tr>';
  divStr = divStr + '</table>';
  divStr = divStr + '</center>';
  runDiv.innerHTML = divStr;
  oWindow.document.body.appendChild(runDiv);
}
//删除透明层和正在运行提示信息
function hideLoadingDiv(oWindow) {
  var element;
  element = oWindow.document.getElementById("clarityDiv");
  if (element != null && element != ""){
    oWindow.document.body.removeChild(element);
  }
  element = oWindow.document.getElementById("__runDiv");
  if (element != null && element != ""){
    oWindow.document.body.removeChild(element);
  }
}