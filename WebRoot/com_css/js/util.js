$Id = function (eId) {
	return document.getElementById(eId);
}

$Name = function(eName) {
	return document.getElementByName(eName);
}

$TagName = function(eTagName) {
	return document.getElementsByTagName(eTagName);
}

//字符串去空格
String.prototype.trim = function() {return this.replace(/(^\s*)|(\s*$)/g, ""); }

//只能输入数字
function onlyNumber(obj){
  obj.value=obj.value.replace(/[^\d]/g,'');
}

//只能输入整数
function onlyInteger(obj){
  obj.value=obj.value.replace(/[^-\d]/g,'');
}

//只能输入数字和英文
function onlyNumberEng(){
  obj.value=obj.value.replace(/[\W]/g,'');
}

//小写钱数转换为大写钱数
function money_convert(moneyConvert) { 
	var SmallMonth = [] ;
	var integerMonth = "";
	var xsMonth = "";
	var lastMonth = "";
  	var neg = true;
  	if (new Number(moneyConvert) < 0 ) {
		moneyConvert = -new Number(moneyConvert);
		neg = true;
	} else {
		neg = false;
	}
	SmallMonth = (new String(moneyConvert)).split(".");
	for(var i = (SmallMonth[0].length - 1); i > -1; i--) {
		var xxFig = SmallMonth[0].substring(i,(i+1));
		var dxFig = getCaptFig(xxFig);
		var danwei = getMoneyUnit((SmallMonth[0].length - 1) - i);
		integerMonth = dxFig + danwei + integerMonth;
	}
	if (SmallMonth[1] != null) {
		var io = SmallMonth[1].length - 1;
		if(SmallMonth[1].length == 1) {
			var xxFig1 = SmallMonth[1].substring(io,(io+1));
			var dxFig1 = getCaptFig(xxFig1);
			var danwei1 = getMoneyUnit((-1));
			xsMonth = xsMonth + dxFig1 + danwei1;
		} 
		if(SmallMonth[1].length > 1) {
			for(var m = io; m > io-2; m--) {
				var xxFig2 = SmallMonth[1].substring((io - m),(io - m + 1));
				var dxFig2 = getCaptFig(xxFig2);
				var danwei2 = getMoneyUnit((m-(io+1)));
				xsMonth = xsMonth + dxFig2 + danwei2;
			}
		}
	}
	lastMonth = integerMonth + xsMonth;
	if(neg) {
		lastMonth = "负" + lastMonth;
	} else {
		lastMonth = lastMonth;
	} 
	return lastMonth;
}
//大写数字（0-9）
function getCaptFig(fig) {
	var wei = "";
	fig = new Number(fig);
	if(fig == 0) {
		wei = "零";
		return wei;
	}
	if(fig == 1) {
		wei = "壹";
		return wei;	
	}
	if(fig == 2) {
		wei = "贰";
		return wei;
	}
	if(fig == 3) {
		wei = "叁";
		return wei;
	}
	if(fig == 4) {
		wei = "肆";
		return wei;
	}
	if(fig == 5) {
		wei = "伍";
		return wei;
	}	
	if(fig == 6) {
		wei = "陆";
		return wei;
	}
	if(fig == 7) {
		wei = "柒";
		return wei;
	}
	if(fig == 8) {
		wei = "捌";
		return wei;
	}
	if(fig == 9) {
		wei = "玖";
		return wei;
	}
}
//大写钱的单位(最高到仟亿、最低到厘)
function getMoneyUnit(qianwei) {
	var qw = "";
	qianwei = new Number(qianwei);
	if(qianwei == -3) {
		qw = "厘";
		return qw;
	}
	if(qianwei == -2) {
		qw = "分";
		return qw;
	}
	if(qianwei == -1) {
		qw = "角";
		return qw;
	}
		if(qianwei == 0) {
		qw = "元";
		return qw;
	}
		if(qianwei == 1) {
		qw = "拾";
		return qw;
	}
		if(qianwei == 2) {
		qw = "佰";
		return qw;
	}
		if(qianwei == 3) {
		qw = "仟";
		return qw;
	}
		if(qianwei == 4) {
		qw = "万";
		return qw;
	}
		if(qianwei == 5) {
		qw = "拾";
		return qw;
	}
		if(qianwei == 6) {
		qw = "佰";
		return qw;
	}
		if(qianwei == 7) {
		qw = "仟";
		return qw;
	}
		if(qianwei == 8) {
		qw = "亿";
		return qw;
	}
		if(qianwei == 9) {
		qw = "拾";
		return qw;
	}
		if(qianwei == 10) {
		qw = "佰";
		return qw;
	}
		if(qianwei == 11) {
		qw = "仟";
		return qw;
	}
}

//保留两位小数
function returnEfficiency(num) {
	return (str2Float(parseFloat(num),2,'str'));
}
//保留小数i
function custMondy(num,i) {
	return (str2Float(parseFloat(num),parseInt(i),'str'));
}
//保留小数i
function str2Float(as_str,ai_digit,as_type)
{
   var fdb_tmp = 0;
   var fi_digit = 0;
   var fs_digit = "1";
   var fs_str = "" + as_str;
   var fs_tmp1 = "";
   var fs_tmp2 = "";
   var fi_pos = 0;
   var fi_len = 0;
   fdb_tmp = parseFloat(isNaN(parseFloat(fs_str))?0:fs_str);
   
   switch (true)
   {
	  case (ai_digit==null):       //不改变值,只转换为数字
		 fdb_tmp = fdb_tmp;
		 break;
	  case (ai_digit==0):          //取得整数
		 fdb_tmp = Math.round(fdb_tmp);
		 break;
	  case (ai_digit>0):            //按照传入的小数点位数四舍五入取值
		 for (var i=0;i<ai_digit;i++) fs_digit +="0";
		 fi_digit = parseInt(fs_digit);
		 fdb_tmp = Math.round(fdb_tmp * fi_digit) / fi_digit;
		 if (as_type=="str")
		 {
			fs_tmp1 = fdb_tmp.toString();
			fs_tmp1 +=((fs_tmp1.indexOf(".")!=-1)?"":".") + fs_digit.substr(1);
			fi_pos = fs_tmp1.indexOf(".") + 1 + ai_digit;
			fdb_tmp = fs_tmp1.substr(0,fi_pos);
		 }
		break;
   }
   return fdb_tmp;
} 

/**
 * 增加select的option
 */
function addOption(selObj, value, label, pos) {
  var objOption = new Option(label, value);
  selObj.add(objOption, pos);
}

function removeOption(selObj) {
  if(selObj.options.length == 0) {
    return;
  }

  for(var a=selObj.options.length-1; a>=0; a--) {
    selObj.remove(a);
  }
}

function sendXml(url, arg) {
    paramDoc = arg?arg:"";
	var xmlRPC = new XmlRPC(url, "POST", false);
  	xmlRPC.send(paramDoc);
  	doc = xmlRPC.getXml();
  	
  	return doc;
}

function clearTable(tableObj) {
	var pRows =tableObj.rows;
  	var vRCount=pRows.length;
  	for(i = vRCount-1; i > 0; i--) {
   		tableObj.deleteRow(i);
  	}
  	return true;
}

function setSelectDisabled(flag) {
	selObjs = document.getElementsByTagName("select");
	for(var ss=0; ss<selObjs.length; ss++) {
		selObjs[ss].disabled = flag;
	}
}

function setInputDisabled(flag) {
	inputObjs = document.getElementsByTagName("input");
	for(var io=0; io<inputObjs.length; io++) {
		inputObjs[io].disabled = flag;
	}
}

/**
 * 将某一列上的文字超长的后边加省略号
 * @param tableObj 要加省略号的表对象
 * @param colIndexs 列索引，可以是多列，列索引之间用“,”分隔
 * @param colWidths 列宽度，和列索引对应，每一列宽度之间用“,”分隔
 */
function setDivElideText(tableObj, colIndexs, colWidths) {
	var rowObj = tableObj.rows;
	var colIndexArray = colIndexs.split(",");
	var colWidthArray = colWidths.split(",");
	for(var i=1; i<rowObj.length; i++) {
		for(var ii=0; ii<colIndexArray.length; ii++) {
			var tmpText = rowObj[i].cells[colIndexArray[ii]].innerText;
			rowObj[i].cells[colIndexArray[ii]].width = new Number(colWidthArray[ii]);
			rowObj[i].cells[colIndexArray[ii]].innerHTML = "<DIV STYLE='width:" + colWidthArray[ii] + ";border:0px solid blue;overflow:hidden;text-overflow:ellipsis'>" + 
				"<NOBR>" + tmpText + "</NOBR></DIV>";
			rowObj[i].cells[colIndexArray[ii]].title = tmpText;
		}
	}
}

//计算左坐标点
function calculateOffsetLeft(field){
	return calculateOffset(field,"offsetLeft");
}

//计算顶坐标点
function calculateOffsetTop(field){
	return calculateOffset(field,"offsetTop");
}
		
//计算坐标点
function calculateOffset(field,attr){
	var offset = 0;
	while(field){
		offset += field[attr];
		field = field.offsetParent;
	}
	return offset;
}

//在窗口位置显示日历表控件,传入参数可以为对象或者对象的ID;
function viewCalendar(edit, flag) {
	if(event.keyCode != 13) {
		return;
	}
	if(typeof(edit) == "string") {
    	edit=document.getElementById(edit);
  	}
  	//if(flag) {
  		showx = calculateOffsetLeft(edit) + 214;
  		showy = calculateOffsetTop(edit) + 128;
  	/*} else {
  		showx = calculateOffsetLeft(edit);
  		showy = calculateOffsetTop(edit);
  	}*/
  	//注:为了和任何路径的jsp文件匹配,显示日历使用绝对路径方式,如果web应用的上下文路径改变,需要改变本方法中的路径
  	retval = window.showModalDialog("/EMWeb/template/html/CalendarDlg1.htm", 
  		"", "dialogWidth:192px; dialogHeight:205px; dialogLeft:"+showx+"px; dialogTop:"+showy+"px; status:no; directories:yes;scrollbars:no;Resizable=no;");
  	if( retval != null ) edit.value=retval;
}

function keyDown() {
	if(event.keyCode == 0x8) {
		if(document.activeElement.type == "text") {
			if(document.activeElement.readOnly) {
				event.keyCode = null;
				return false;
			}
		}
		if(document.activeElement.tagName == "TEXTAREA") {
			if(document.activeElement.readOnly) {
				event.keyCode = null;
				return false;
			}
		}
		if(document.activeElement.type != "text" && 
			document.activeElement.type != "password" && 
			document.activeElement.tagName != "TEXTAREA") {
			event.keyCode = null;
			return false;
		}
	}
	if(event.keyCode == 13) {
		if(document.activeElement.onclick != null) {
			document.activeElement.click();
			return false;
		}
  		event.keyCode=9; 
  		return true;
  	}
}

function showCal(){
	
   	var x = 0;
   	var y = 0;
   	if(arguments[2] == "u") {
   		x = calculateOffsetLeft(arguments[0]);
   		y = calculateOffsetTop(arguments[0]) -205;
   	} else {
		x = calculateOffsetLeft(arguments[0]);
   		y = calculateOffsetTop(arguments[0]);
   	}
   	var appendStr = '';
   	if(arguments[1]){
   		appendStr += "," + arguments[1];
   	}
   	if(arguments[3]){
   		appendStr += "," + arguments[3];
   	}
   	var htmlStr = '<iframe name="calFrame" id="calFrame" value="' + arguments[0].name + appendStr + '" frameborder="0" scrolling="no" width="195" height="100%" marginwidth="0" src="/CAWeb/template/html/CalendarDlg.htm"></iframe>';
   	var c = new Container("calWin", 192, 205, x, y + 21, "<left>日历</left>",htmlStr);
   	c.open();
}

document.onkeydown = keyDown;

function setOnReset() {
	var inputObj = document.getElementsByTagName("input");
	for(var i=0; i<inputObj.length; i++) {
		if(inputObj[i].type == "button" && 
			inputObj[i].value == "取消") {
			inputObj[i].onclick = function() {
				window.location.reload();
			}
			break;
		}
	}
}

function setFocus() {
	var inputObj = document.getElementsByTagName("input");

	if(inputObj.length > 0) {
		inputObj[0].focus();
	} else {
		inputObj.focus();
	}
}
//通过文本控件的onchange事件、清空隐藏控件
function clearHidByOnchange(eName,hidName) {
	if((typeof (document.all(eName)) == 'undefined') || (document.all(eName) == null) ) {
		alert('控件' + eName + '不存在');
		return;
	}
	if((typeof (document.all(hidName)) == 'undefined') || (document.all(hidName) == null) ) {
		alert('隐藏控件' + hidName + '不存在');
		return;
	}
	if((typeof (document.all(hidName).onchange) == 'undefined') || (document.all(hidName).onchange == "") ) {
		return;
	}
	document.all(eName).onchange = function(){document.all(hidName).value='';};
	
}

function setDirectionKey() {
	//下 40 上 38 左 37 右 39
	//alert(event.keyCode);
	var currentCellIndex = document.activeElement.parentElement.cellIndex;
	var currentRowIndex = document.activeElement.parentElement.parentElement.rowIndex;
	var currentObj = document.activeElement;
	var currentCellObj = document.activeElement.parentElement;
	var currentRowObj = document.activeElement.parentElement.parentElement;
	var currentTableObj = document.activeElement.parentElement.parentElement.parentElement;
	if(event.keyCode == 38) {
		//判断该列的上一行是否存在
		if(currentRowIndex != 0) {
			if(currentTableObj.rows[currentRowIndex-1].cells[currentCellIndex].firstChild.disabled == true) {
				return;	
			}
			currentTableObj.rows[currentRowIndex-1].cells[currentCellIndex].firstChild.focus();
		}
	} else if(event.keyCode == 40) {
		if(currentTableObj.rows[currentRowIndex+1] != null) {
			if(currentTableObj.rows[currentRowIndex+1].cells[currentCellIndex].firstChild.disabled == true) {
				return;	
			}
			currentTableObj.rows[currentRowIndex+1].cells[currentCellIndex].firstChild.focus();
		}
	} else if(event.keyCode == 37) {
		if(currentCellIndex != 0) {
			if(currentTableObj.rows[currentRowIndex].cells[currentCellIndex-1].firstChild.disabled == true) {
				return;	
			}
			currentTableObj.rows[currentRowIndex].cells[currentCellIndex-1].firstChild.focus();	
		}
	} else if(event.keyCode == 39) {
		if(currentTableObj.rows[currentRowIndex].cells[currentCellIndex+1] != null) {
			if(currentTableObj.rows[currentRowIndex].cells[currentCellIndex+1].firstChild.disabled == true) {
				return;	
			}
			currentTableObj.rows[currentRowIndex].cells[currentCellIndex+1].firstChild.focus();	
		}
	}
}