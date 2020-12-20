//var msg = new Ext.MessageBox.alert();

var dayNum=0;

function remove(records,store,url){
	var jsonArray = [];
	Ext.each(records,function(item){
		jsonArray.push(item.id);
	});
	var rm_Ids = Ext.util.JSON.encode(jsonArray);
	if(records){
		for (var i = 0; i < records.length; i++) {
		store.remove(records[i]);
		}	
	}
	Ext.Ajax.request({
		url:url,
		method:'POST',
		params:{id:rm_Ids},
		scope:this,
		success:function(){
//			if(records){
//				for (var i = 0; i < records.length; i++) {
//				store.remove(records[i]);
//				}	
//			}
//			Ext.Msg.alert('訊息','刪除成功!');
			loadAccountChart();
			loadTransactionChart();
			loadBudgetChart();
		},
		failure:function(){
			Ext.Msg.alert('訊息','後台刪除失敗!');
		}
	});		
}
function loadAccountChart(){
    var xml;
	var conn = new Ext.data.Connection();
	conn.request({
	    url: '../account.do?action=getAccountChartXML',
	    method: 'POST',
	    success: function(responseObject) {
	        xml = responseObject.responseText;
	     
			var chart = new FusionCharts("../Charts/Column3D.swf", "ChartId", "500", "600", "0", "0");
			chart.setDataXML(xml);   
			chart.setTransparent(true);
			chart.render("ac_chartdiv");
	    },
	     failure: function() {
	         Ext.Msg.alert('Status', 'Unable to show history at this time. Please try again later.');
	     }
	});
}
function loadTransactionChart(){
    var xml;
	var conn = new Ext.data.Connection();
		conn.request({
		    url: '../transaction.do?action=getTransactionChartXML',
		    method: 'POST',
		    params:{dayNum:dayNum},
		    success: function(responseObject) {
		        xml = responseObject.responseText;
	     
				var chart = new FusionCharts("../Charts/Pie2D.swf", "ChartId", "400", "600", "0", "0");
				chart.setDataXML(xml);   
				chart.setTransparent(true);
				chart.render("tr_chartdiv");
		    },
		     failure: function() {
	         Ext.Msg.alert('Status', 'Unable to show history at this time. Please try again later.');
	     }
  	});
}
function loadBudgetChart(){
    var xml;
	var conn = new Ext.data.Connection();
		conn.request({
		    url: '../budget.do?action=getBudgetChartXML',
		    method: 'POST',
		    success: function(responseObject) {
		        xml = responseObject.responseText;
		        var chart = new FusionCharts("../Charts/StackedColumn3D.swf", "ChartId", "450", "600", "0", "0");
				chart.setDataXML(xml);   
				chart.setTransparent(true);
				chart.render("bg_chartdiv");
		    },
		     failure: function() {
	         Ext.Msg.alert('Status', 'Unable to show history at this time. Please try again later.');
	     }
  	});
}
function loadAnalysisChart(){
    var xml;
	var conn = new Ext.data.Connection();
		conn.request({
		    url: '../chart.do?action=getMonthlyAnalysis',
		    method: 'POST',
		    success: function(responseObject) {
		        xml = responseObject.responseText;
		        var chart3 = new FusionCharts("../Charts/MSColumn3D.swf", "ChartId", "1100", "350", "0", "0");
				chart3.setDataXML(xml);   
				chart3.setTransparent(true);
				chart3.render("analysis_chartdiv");
		     },
		     failure: function() {
		    		Ext.Msg.alert('Status', 'Unable to show history at this time. Please try again later.');
		     }
  	});
}
function detail(month,catgory){
    var xml;
	var conn = new Ext.data.Connection();
		conn.request({
		    url: '../chart.do?action=getMonthlyAnalysisDetail&month='+month+'&catgory='+catgory,
		    method: 'POST',
		    success: function(responseObject) {
		        xml = responseObject.responseText;
		        var chart3 = new FusionCharts("../Charts/Column3D.swf", "ChartId", "1100", "270", "0", "0");
				chart3.setDataXML(xml);
				chart3.setTransparent(true);
				chart3.render("detail_chartdiv");
		     },
		     failure: function() {
		    		Ext.Msg.alert('Status', 'Unable to show history at this time. Please try again later.');
		     }
  	});
}
function save(store,url){
	var bl;
	var m = store.getModifiedRecords();

	if(m!=''){
	var jsonArray = []; 
		Ext.each(m, function(item) { 
			jsonArray.push(item.data); 
		}); 
		var mDatas = Ext.util.JSON.encode(jsonArray); 
		Ext.Ajax.request({
			url:url,
			method:'POST',
			params:{datas:mDatas},
			scope:this,
			success:function(response){
				store.commitChanges();
				Ext.MessageBox.hide();
				Ext.Msg.alert('訊息','儲存成功');
				store.reload();
				loadAccountChart();
				loadTransactionChart();
				loadBudgetChart();
			},
			failure:function(){;
				store.rejectChanges();
				Ext.MessageBox.hide();
				Ext.Msg.alert('訊息','儲存失敗');
			}
		});
	}else{
		store.rejectChanges();
		Ext.MessageBox.hide();
		Ext.Msg.alert('訊息','資料無變動');
	}
}

Ext.util.Format.comboRenderer = function(combo){
    return function(value){
        var record = combo.findRecord(combo.valueField, value);
        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
    }
}

function formatDate(value){
    return value ? value.dateFormat('Y-m-d') : '';
}