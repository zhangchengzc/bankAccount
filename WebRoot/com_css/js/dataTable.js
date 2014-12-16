function DataTable(divObj, tableObj, dataXmlDoc, 
					methodStrClicks, methodStrDblClicks, 
					beginRow, dbType, isClear) {
	this.divObj = divObj;
	this.tableObj = tableObj;
	this.dataXmlDoc = dataXmlDoc;
	this.beginRow = beginRow;
	this.methodStrClicks = methodStrClicks;
	this.methodStrDblClicks = methodStrDblClicks;
	this.beginRow = this.beginRow;
	this.dbType = dbType;
	this.isClear = isClear;
}
/**
 * 删除指定表格的除了表头的所有行
 */
DataTable.prototype.removeTableRow = function(tableObj) {
  var pRows =tableObj.rows;
  var vRCount=pRows.length;
  for(i = vRCount-1; i > 0; i--) {
    tableObj.deleteRow(i);
  }
  return true;
}
/**
 * 根据后台返回来的xml doc构造表格
 * @param divObj-表格所在div的对象
 * @param tableObj-表格对象
 * @param docs-xml对象数组
 * 例如：xml为：<root><cols><a>1111<a></cols><cols><a>22222<a></cols></root>
 * dom为xml对象
 * var root = dom.selectSingleNode("root");
 * var docs = dom.selectNodes("cols");
 * @param methodStrClick-单击事件方法的字符串
 * @param methodStrDblClick-双击事件方法的字符串
 */
DataTable.prototype.refreshTable = function() {
  if(this.beginRow == 'undefined' || this.beginRow == null || this.beginRow == "") {
  	this.beginRow = 0;
  }
  if(this.isClear == undefined || this.isClear == null) {
  	this.isClear = true;
  }
  if(this.isClear) {
  	this.removeTableRow(this.tableObj);
  }
  var tableHtml = this.divObj.innerHTML;
  var place = tableHtml.indexOf("</TBODY>");
  var tableHtmlHead = tableHtml.substring(0, place);
  
  var tableHtmlTail = tableHtml.substring(place);
  var len = this.dataXmlDoc.length;

  var pRows = this.tableObj.rows;
  var vRCount = pRows.length;
  if(vRCount < 1) {
    alert("表格缺少表头！");
    return;
  }
  var rowHead = pRows[0];
  var colHeads = rowHead.cells;
  place = 0;
  if(this.dbType=='oracle') {
  	place = 0;
  } else {
  	place = this.beginRow;
  }

  len = new Number(len) + new Number(place);

  for(i=place; i<len; i++) {
    tableHtmlHead = tableHtmlHead + "\n<tr>";
    for(j=0; j<colHeads.length; j++) {
      var docName = colHeads[j].id;
	  var tdAlign = colHeads[j].tdAlign;
	  var tdWidth = colHeads[j].width;
	  if(tdAlign == undefined || tdAlign == null || tdAlign == "") {
	  	tdAlign = "center";
	  }
	  
      var display = colHeads[j].style.display;
      if(display == "") {
        display="block";
      }
      if(docName == "no") {
        if(this.beginRow == null || this.beginRow == "") {
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align='center'>" + (i+1) + "</td>";
        } else {
          this.beginRow = (new Number(this.beginRow)) + 1;
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align='center'>" + this.beginRow + "</td>";
        }
        continue;
      } else if(docName == null || docName == "") {
        tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\">" + j + "</td>";
        continue;
      }
      
	  if(this.dataXmlDoc[i-place].selectSingleNode(docName.toString()) == null) {
	  	alert("在xml中没有表头id=" + docName.toString() + "的属性！");
	  	return;
	  }
      var tdValue = this.dataXmlDoc[i-place].selectSingleNode(docName.toString()).text;
	  if(tdValue == null || tdValue == "") {
	  	tdValue = "&nbsp;";
	  }
	  tdValue = tdValue.trim();
	  var dataType = colHeads[j].dataType;
	  if(tdValue!="&nbsp;" && dataType != null) {
	  	if(dataType == "date") {
	  		tdValue = tdValue.substring(0, tdValue.indexOf(" "));
	  	} else if(dataType == "radio") {
	  		tdValue = "<input type='radio' value='" + tdValue + "' name='" + docName + "' id='" + docName + "'>";
	  	} else if(dataType == "checkbox") {
	  		tdValue = "<input type='checkbox' value='" + tdValue + "' name='" + docName + "' id='" + docName + "'>";
	  	} else if(dataType == "money") {
	  		tdValue = "￥" + tdValue;
	  	} else if(dataType.indexOf("num") != -1) {
	  		var bIndex = dataType.indexOf("(");
	  		var eIndex = dataType.indexOf(")");
	  		var digits = dataType.substring(new Number(bIndex)+1, eIndex);
	  		if(digits == 0) {
	  			tdValue = Math.round(new Number(tdValue)) + "";
	  		} else {
	  			tdValue = custMondy(tdValue, digits);
	  		}
	  		/*if(digits == 0) {
	  			tdValue = Math.round(new Number(tdValue)) + "";
	  		} else {
	  			var pointPlace = tdValue.indexOf(".");
	  			if(pointPlace == -1) {
	  				tdValue += ".";
	  			} 
	  			var tmp = "0."; 
	  			for(var x=0; x<new Number(digits); x++) {
	  				tdValue += "0";	
	  				tmp += "0";
	  			}
	  			tmp = tmp.substring(0, tmp.length-1) + "1";
	  			
	  			var pointPlace = tdValue.indexOf(".");
	  			if(tdValue.substring(new Number(pointPlace)+new Number(digits)+1, new Number(pointPlace)+new Number(digits)+2) >= 5) {
	  				tdValue = new String(new Number(tdValue.substring(0, new Number(pointPlace)+new Number(digits)+1)) + new Number(tmp));
	  			} else {
	  				tdValue = (tdValue.substring(0, new Number(pointPlace)+new Number(digits)+1));
	  			}
	  		}*/
	  	}
	  }
	  
      if(tdValue.indexOf("href") == -1) {
        if(this.methodStrClicks != null && this.methodStrDblClicks != null) {
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align=\"" + tdAlign + "\" onclick=\"" + this.methodStrClicks[i]
            + "\" ondblclick=\"" + this.methodStrDblClicks[i] + "\" style='display:" +
            display + ";'>" + tdValue + "</td>";
        } else if(this.methodStrClicks != null && this.methodStrDblClicks == null) {
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align=\"" + tdAlign + "\" onclick=\"" + this.methodStrClicks[i]
            + "\" style='display:" + display + ";'>" + tdValue + "</td>";
        } else if(this.methodStrClicks == null && this.methodStrDblClicks != null) {
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align=\"" + tdAlign + "\" ondblclick=\"" +
            this.methodStrDblClicks[i] + "\" style='display:" + display +
            ";'>" + tdValue + "</td>";
        } else if(this.methodStrClicks == null && this.methodStrDblClicks == null) {
          tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align=\"" + tdAlign + "\" style='display:" + display + ";'>"
            + tdValue + "</td>";
        }
      } else {
        tableHtmlHead = tableHtmlHead + "<td width=\"" + tdWidth + "\" align=\"" + tdAlign + "\">" + tdValue + "</td>";
      }
      
    }
    tableHtmlHead = tableHtmlHead + "\n</tr>";
  }
  tableHtml = tableHtmlHead + tableHtmlTail;
  
  this.divObj.innerHTML = tableHtml;
}
	
/**
 * 将docs中名称为elementName的字段的值替换为newValue
 * 例如xml：<root><cols><a>1111<a></cols><cols><a>22222<a></cols></root>
 * dom为xml对象
 * 将字段a的值替换为<input type='checkbox' name='a' value='@elementValue@'>，并且
 * checkbox的value等于a的值，只要将checkbox按上边方式写即可(value='@elementValue@')
 * 其他的控件类似
 * root = dom.selectSingleNode("root");
 * docs = root.selectNodes("cols");
 * replaceElementValue(docs, "a", "<input type='checkbox' name='a' value='@elementValue@'>")
 */
DataTable.prototype.replaceElementValue = function(docs, elementName, newValue) {
  for(var i=0; i<docs.length; i++) {
    var element = docs[i].selectSingleNode(elementName);
    var elementValue = element.text;

    var replaceValue = this.replaceStr(newValue, "@elementValue@", elementValue);
    element.text = replaceValue;
  }
}

/**
 * 将字符串str中的oldValue全部替换为newValue
 */
DataTable.prototype.replaceStr = function(str, oldValue, newValue) {
  var place = str.indexOf(oldValue);
  newStr = str;
  if(place != -1) {
    var beginStr = str.substring(0, place);
    var endStr = str.substring(place+oldValue.length);
    newStr = beginStr + newValue + endStr;
    this.replaceStr(newStr, oldValue, newValue);
  }
  return newStr;
}

/**
 * 将表格的表头存入xml，表格头为excel表头
 */
DataTable.prototype.createExcelHead = function(dom, tableObj, sheetName) {
	if(dom == null) {
		dom = new ActiveXObject("Msxml2.DOMDocument.3.0");
		dom.setProperty("SelectionLanguage", "XPath");
		root = dom.createElement("root");
		setExcelHead(dom, root, tableObj, sheetName);
		dom.appendChild(root);
	} else {
		root = dom.selectSingleNode("root");
		this.setExcelHead(dom, root, tableObj, sheetName);
	}
	
	return dom;
}

/**
 * 表格中的自定义属性noExcel为n时，生成excel表格时，标题没有这一列
 * noExcel为y或null时，生成表格标题有这一列
 */
DataTable.prototype.setExcelHead = function(dom, root, tableObj, sheetName) {
	//创建excel表格表头
	var excelHead = dom.createElement("excelHead");
	var pRows =tableObj.rows;
	var pCells = pRows[0].cells;
	for(var i=0; i<pCells.length; i++) {
	if(pCells[i].noExcel == "n") {
	  continue;
	}
	var name = dom.createElement((pCells[i].id).toString());
	name.text = pCells[i].innerText;
	excelHead.appendChild(name);
	}
	root.appendChild(excelHead);
	
	//创建excel表格名字
	var sheetNameDoc = dom.createElement("sheetName");
	sheetNameDoc.text = sheetName;
	root.appendChild(sheetNameDoc);
}