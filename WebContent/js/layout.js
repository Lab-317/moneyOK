Ext.onReady(function(){
	
	var layout = new Ext.Viewport({
		layout:'border',
		items:[
		    {
				contentEl:'center',
				region:'center',
				bodyStyle:'background-image:url(../img/back7.jpg);background-repeat:repeat-x;background-position:5% 0%;,0;background-color: rgb(20, 119, 192);',
				items:[budget,account,transaction,analysis]
			},{
				region:'west',
				//bodyStyle:'background-image:url(../img/back2.png);background-repeat:repeat-x;background-color: rgb(20, 119, 192);',
				width:200,
				contentEl:'dockMenu',
				//collapsible:true,
				bodyStyle:'background-image:url(../img/back6.jpg);background-repeat:repeat-y;background-color: rgb(20, 119, 192);'
				//items:[mainList]
				
			},{
				region:'south',
				contentEl:'buttom'
			}
		]
	});
});
