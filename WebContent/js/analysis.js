var analysis_chart = new Ext.FormPanel({
	frame:true,
	title:'圖表',
	defaultType:'textfield',
	contentEl: 'analysis_chartdiv',
	width:1100,
	height:380
});

var detail_chart = new Ext.FormPanel({
	frame:true,
	title:'詳細資料',
	defaultType:'textfield',
	contentEl: 'detail_chartdiv',
	width:1100,
	height:320
});

var analysis = new Ext.Window({
	el:'al_window',
	title:'分析',
	layout:'fit',
	closeAction:'hide',
	width:1100,
	height:700,
	plain:true,
	constrain:true,
	maximizable:true,
	collapsible:true,
	items:[{
//		collapsible:true,
		layout:'vbox',
		items:[analysis_chart,detail_chart]
	}
	]
});