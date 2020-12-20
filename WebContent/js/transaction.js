//Ext.onReady(function(){
//params:dayNum
	
	var i=0;
	
	Ext.QuickTips.init();
	
	var moneyOKRowSelect = new Ext.grid.RowSelectionModel({
    	listeners:{
    		selectionchange:function(){
    			var garbageEl = Ext.get('garbage');
    			garbageEl.addClass("garbageShow");
    			garbageEl.fadeIn({endOpacity:.25});

//    			garbageEl.pause(3);
//    			garbageEl.fadeOut();
//    			garbageEl.addClass("garbageHide");
    		}
    	}
    });
	
	var tr_proxy=new Ext.data.HttpProxy(    
		{url:'../transaction.do?action=getDateVarTransactionJSON'}
	);

	var tr_reader=new Ext.data.JsonReader(
		{
			totalProperty:'13'
			},[
			    {name:'id'},
				{name:'amount'},
				{name:'pId'},
				{name:'itemId'},					
				{name:'accountId'},
				{name:'type'},
				{name:'date'},
				{name:'itemName'},
				{name:'description'}
			]
		)
	
	var tr_store=new Ext.data.GroupingStore({
		proxy:tr_proxy,
		reader:tr_reader,
		sortInfo:{field:'date',direction:"ACS"}
	});	
	
	function tr_add(){
		var trR = new tr_Record({
			id:'-1',
			date:'',
			type:'0',
			itemId:'',
			itemName:'',
			accountId:'',
			description:'',
			amount:'0',
			pId:''
		});
		tr_grid.stopEditing();
		tr_store.insert(0,trR);
		tr_grid.startEditing(0,0);
	}
	
	var data = [
		['0','支出'],
		['1','收入']
	];
	
	var tr_typeStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:data
	});
	
	var tr_typeCombo = new Ext.form.ComboBox({  
    	typeAhead:true,
    	emptyText:'請選擇',
    	store:tr_typeStore,
    	mode:'local',
    	triggerAction:'all',
    	valueField:'value',
    	displayField:'text'
    });

    var tr_categoryStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:'../transaction.do?action=getTypeJSON'}),
		reader:new Ext.data.JsonReader({
		},[
			{name:'id'},
			{name:'name'}
		])
	});
	tr_categoryStore.load();
	
    var tr_categoryCombo = new Ext.form.ComboBox({  
    	typeAhead: true,
    	store:tr_categoryStore,
    	mode:'local',
    	valueField:'id',
    	displayField:'name',
    	triggerAction:'all',
    	emptyText:'請選擇',
    	allowBlank: false,
    	readOnly:true
    });
    
   	var tr_itemStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:'../transaction.do?action=getItemJSON'}),
		reader:new Ext.data.JsonReader({
		},[
			{name:'id'},
			{name:'name'},
			{name:'pId',mapping:'parentCategory.id'}
		])
	});
	tr_itemStore.load();
	
    var tr_itemCombo = new Ext.form.ComboBox({  
    	typeAhead: false,
    	store: tr_itemStore,
    	mode:'local',
    	valueField:'id',
    	displayField:'name',
    	triggerAction:'all',
    	lazyRender:true,
    	emptyText:'請選擇',
    	autocomplete:true
    });
    
    var itemRecord = new Ext.data.Record.create([
    	{name:'id',type:'string'},
    	{name:'name',type:'string'},
    	{name:'pId',type:'string'}
    ]);
    
    tr_itemCombo.on('expand',function() {
    	var Record = tr_grid.getSelectionModel().getSelected();
		tr_itemStore.filter('pId',Record.data['pId']);
    });
    
    tr_itemCombo.on('change',function(combo){
		var value = combo.value;
		var row_Record = tr_grid.getSelectionModel().getSelected();
		var record = combo.findRecord(combo.valueField, value);
		if(!record){
			--i;
			tr_itemStore.add(new itemRecord({
				id:i,
				name:value,
				pId:row_Record.data['pId']
			}));
			tr_itemStore.commitChanges();
			combo.setValue(i);
			row_Record.data['itemName']=value;
		}else{
			tr_itemStore.filter('pId',row_Record.data['pId']);
		}
    });
    
    tr_itemCombo.on('blur',function(){
    	tr_itemStore.clearFilter();
    });
    
    var tr_accountStore = new Ext.data.Store({
    	proxy:new Ext.data.HttpProxy({url:'../transaction.do?action=getAccountJSON'}),
    	reader:new Ext.data.JsonReader({
    	},[
    		{name:'id'},
    		{name:'name'}
    	])
    });
    tr_accountStore.load();
    
    var tr_accountCombo = new Ext.grid.GridEditor(new Ext.form.ComboBox({
    	typeAhead: true,
    	fieldLabel:'請選擇',
    	store:tr_accountStore,
    	valueField:'id',
    	displayField:'name',
    	lazyRender:true,
    	mode:'local',
    	triggerAction:'all',
    	emptyText:'請選擇',
    	readOnly:true
    }));
    
    var tr_cm = new Ext.grid.ColumnModel([
        {
			header:'序號',
			dataIndex:'id',
			autoWidth:true,
			hidden:true,
			editor: new Ext.form.TextField({
            }),
            allowBlank: false
		},
        {
			header:'日期',
			dataIndex:'date',
			autoWidth:true,
			renderer:function(value, p, record) {
                if (value && typeof value == 'object') {
                    return value.format('Y-m-d');
                }
                if (value) {
                    var dt = new Date();
                    dt =  Date.parseDate(value, "Y-m-d");
                    return dt.format('Y-m-d');
                }
                return value;
    		},
			editor: new Ext.form.DateField({ 
				dateFormat:'Y-m-d',
				allowBlank: false
            }),
			sortable:true
		},
		{
			header:'類型',
			dataIndex:'type',
			editor: tr_typeCombo,
			renderer:function(value){
				return value==0? "<span style='color:red';font-weight:bold;'>支出</span>":
									"<span style='color:green;font-weight:bold;'>收入</span>";
			},
            allowBlank: false,
			sortable:true
		},
		{
			header:'分類',
			dataIndex:'pId',
            editor: tr_categoryCombo,
            renderer : Ext.util.Format.comboRenderer(tr_categoryCombo),
			sortable:true
		},
        {
			header:'項目',
			dataIndex:'itemId',
            editor: tr_itemCombo,
            renderer:Ext.util.Format.comboRenderer(tr_itemCombo),
			sortable:true
		},
		{
			header:'項目名稱',
			dataIndex:'itemName',
			hidden:true,
            editor: new Ext.form.TextField({  			 
            })
		},
        {
			header:'詳細敘述',
			dataIndex:'description',
			editor: new Ext.form.TextField({  			 
            })
		},
		{
			header:'帳戶',
			dataIndex:'accountId',
			sortable:true,
			allowBlank: false,
			editor:tr_accountCombo,
			renderer:function(value){
				var rec = tr_accountStore.getById(value);
				if (rec == null) {
					return value;
				}
				else {
					return rec.data['name'];
				}
    		}
		},
        {
			header:'金額',
			dataIndex:'amount',
			renderer: 'usMoney',
			editor: new Ext.form.NumberField({
				allowBlank: false
            }),
			sortable:true
		}
    ]);
    
    var tr_Record = Ext.data.Record.create([
    	{name:'id',type:'string'},
		{name:'date',type:'string'},
		{name:'type',type:'string'},
		{name:'pId',type:'string'},
		{name:'itemId',type:'string'},
		{name:'itemName',type:'string'},
		{name:'description',type:'string'},
		{name:'accountId',type:'string'},
		{name:'amount',type:'string'}
	]);
	
    var dayfield = new Ext.form.NumberField({
    	width:50,
    	value:dayNum,
      	id:'day'
    });
    
    dayfield.on('change',function(field){
		dayNum = field.value;
		tr_store.load({
			params:{dayNum:dayNum}
		});
		loadTransactionChart();
//    	Ext.Ajax.request({
//    		url:'../transaction.do?action=getDateVarTransactionJSON',
//    		method:'POST',
//    		params:{dayNum:field.value},
//    		success:tr_store.reload(),
//    		failure:alert('紀錄更新失敗')
//    	});
    });
    
    var income_Checkbox = new Ext.form.Checkbox({
    	name:'income',
    	fieldLabel:'收入',
    	checked:true
    });
    
    income_Checkbox.on('check',function(checkbox){
    	if(!checkbox.getValue()){
    		expend_Checkbox.setValue(true);
    		tr_store.filter('type','false');
    	}else{
    		tr_store.clearFilter();
    	}
    });
    
    var expend_Checkbox = new Ext.form.Checkbox({
    	name:'expend',
    	fieldLabel:'支出',
    	checked:true
    });
    
    expend_Checkbox.on('check',function(checkbox){
    	if(!checkbox.getValue()){
    		income_Checkbox.setValue(true);
    		tr_store.filter('type','true');
    	}else{
    		tr_store.clearFilter();
    	}
    });
    
	var tr_tbar = new Ext.Toolbar(['-',
	{
		text:'新增',
		iconCls:'silk-add',
		scope:tr_store,
		handler:tr_add
	},'-',{
		text:'刪除',
		iconCls:'silk-delete',
		handler: function(){
			var rm_Records = tr_grid.getSelectionModel().getSelections();
			if(rm_Records.length==0){
				Ext.Msg.alert('訊息','請至少選擇一列刪除');
			}else{
				Ext.MessageBox.confirm('提示','確定刪除？',function(btn){
					if(btn=='yes'){
						url = "../transaction.do?action=removeVarTransactionJSON";
						remove(rm_Records,tr_store,url);
					}
				});
			}
		}
	},'-',{
		text:'儲存',
		iconCls:'icon-save',
		handler: function(){
			save(tr_store,'../transaction.do?action=updateVarTransactionJSON');
			Ext.MessageBox.show({
		       msg: '資料儲存中‧‧‧',
		       progressText: 'Saving...',
		       width:300,
		       wait:true,
		       waitConfig: {interval:200}
		       //icon:'../img/download.gif' //custom class in msg-box.html
			});
		}
	},'-'
	]);
	tr_tbar.add('->');
	tr_tbar.add('支出');
	tr_tbar.add(expend_Checkbox);
	tr_tbar.add('收入');
	tr_tbar.add(income_Checkbox);
	tr_tbar.add('-');
	tr_tbar.add('檢視天數:');
    tr_tbar.add(dayfield);
    tr_tbar.add('天');
	
    var tr_grid = new Ext.grid.EditorGridPanel({
    	ddGroup:'gridDDGroup',
    	id:'tr_grid',
        title:'消費紀錄',
        store: tr_store,
        cm: tr_cm,
        sm: moneyOKRowSelect,
        modal:true,
        loadMask:true,
        enableDragDrop:true,
        forceValidation : true,
		viewConfig:{
        	forceFit:true  //自動調整每列寬度
    	}
    });
    
    tr_grid.on('activate',function(grid){
    	tr_store.clearFilter();
    });

    
    var tr_dategrid = new Ext.grid.EditorGridPanel({
        title:'日期',
        store:tr_store,
        cm: tr_cm,
        sm: moneyOKRowSelect,
		modal:true,
		loadMask:true,
		view:new Ext.grid.GroupingView(
			{
			showGroupName:false,
			forceFit:true
			}
		)
    });
    
    tr_dategrid.on('activate',function(grid){
    	grid.store.clearFilter();
    	grid.store.groupBy('date');
    });
    
    var tr_categrid = new Ext.grid.EditorGridPanel({
        title:'分類',
        store:tr_store,
        cm: tr_cm,
        sm: moneyOKRowSelect,
		modal:true,
		loadMask:true,
		view:new Ext.grid.GroupingView(
			{
			showGroupName:false,
			forceFit:true
			}
		)
    });
    
    tr_categrid.on('activate',function(grid){
    	grid.store.clearFilter();
    	grid.store.groupBy('pId');
    });
    
    var tr_accgrid = new Ext.grid.EditorGridPanel({
        title:'帳戶',
        store:tr_store,
        cm: tr_cm,
        sm: moneyOKRowSelect,
		modal:true,
		loadMask:true,
		view:new Ext.grid.GroupingView(
			{
			showGroupName:false,
			forceFit:true
			}
		)
    });
    
    tr_accgrid.on('activate',function(grid){
    	grid.store.clearFilter();
    	grid.store.groupBy('accountId');
    });
    
    var incomefield = new Ext.form.NumberField({
    	width:100,
    	renderer: 'usMoney',
      	id:'incomefield',
      	readOnly:true
    });
    
    var expendfield = new Ext.form.NumberField({
    	width:100,
    	renderer: 'usMoney',
      	id:'expendfield',
      	readOnly:true
    });
    
    var tr_tabPanel = new Ext.TabPanel({
    	tbar:tr_tbar,
		bbar:new Ext.PagingToolbar({
			pageSize:50,
			store:tr_store,
			//displayInfo:true,
			items:['->','總收入',incomefield,'-','總支出',expendfield
			]
		}),
		loadMask:true,
		modal:true,
		activeTab:0,
    	items:[tr_grid,tr_dategrid,tr_categrid,tr_accgrid]
    });
    
    tr_store.on('load',function(){
		Ext.Ajax.request({
			url:'../transaction.do?action=getTotalJSON',
			method:'GET',
			params:{dayNum:dayfield.value},
			//scope:this,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				incomefield.setRawValue(obj[0].totalIncome);
				expendfield.setRawValue(obj[0].totalExpense);
			}
		});
	});
	
	tr_store.on('beforeload',function(){
		tr_itemStore.load();
	});
	
	tr_store.on('update',function(){
//   		ac_totalfield.setRawValue(ac_store.sum('total'));
		Ext.Ajax.request({
			url:'../transaction.do?action=getTotalJSON',
			method:'GET',
			params:{dayNum:dayfield.value},
			//scope:this,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				incomefield.setRawValue(obj[0].totalIncome);
				expendfield.setRawValue(obj[0].totalExpense);
			}
		});
	});
	
	tr_store.on('remove',function(){
//   		ac_totalfield.setRawValue(ac_store.sum('total'));
		
		Ext.Ajax.request({
			url:'../transaction.do?action=getTotalJSON',
			method:'GET',
			params:{dayNum:dayfield.value},
			//scope:this,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				incomefield.setRawValue(obj[0].totalIncome);
				expendfield.setRawValue(obj[0].totalExpense);
			}
		});
	});
//    tr_store.load();
  
    var tr_chart = new Ext.FormPanel({
    	frame:true,
    	title:'圖表',
    	defaultType:'textfield',
		//html:'<div id="tr_chartdiv" z-index:-100><div>',
    	contentEl: 'tr_chartdiv',
		width:400,
		split:true
    });
    
    var transaction = new Ext.Window({
		el:'tr_window',
		title:'消費',
		layout:'border',
		closeAction:'hide',
		width:1100,
		height:700,
//		width:1000,
//		height:600,
		plain:true,
		constrain:true,
		maximizable:true,
		collapsible:true,
    	items:[{
    		region:'west',
    		autoWidth:true,
    		collapsible:true,
    		layout:'fit',
    		items:[tr_chart]
    	},{
    		region:'center',
    		layout:'fit',
    		items:[tr_tabPanel]
    	}
    	]
    });
    
    transaction.on('beforehide',function(){
    	var garbageEl =  Ext.get('garbage');
		garbageEl.hide();
    });