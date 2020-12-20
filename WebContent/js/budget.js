  
    function cateRenderer(value){
		var rec = tr_categoryStore.getById(value);
		if (rec == null) {
			return value;
		}
		else {
			return rec.data['name'];	
		}
	}
    
    var bg_categoryStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:'../transaction.do?action=getTypeJSON'}),
		reader:new Ext.data.JsonReader({
		},[
			{name:'id'},
			{name:'name'}
		])
	});
	bg_categoryStore.load();
	
    var bg_categoryCombo = new Ext.form.ComboBox({  
    	typeAhead: true,
    	store:bg_categoryStore,
    	valueField:'id',
    	displayField:'name',
    	lazyRender:true,
    	mode:'local',
    	triggerAction:'all',
    	emptyText:'請選擇',
    	readOnly:true
    });

    var bg_cm = new Ext.grid.ColumnModel({
    		defaults:{
    			sortable:true
    		},
    	columns:[
		{header:'序號',dataIndex:'id',hidden:true,editor:new Ext.grid.GridEditor(
        new Ext.form.TextField({
            allowBlank: false
        }))},
        {header:'名稱',dataIndex:'name',editor:new Ext.grid.GridEditor(
        new Ext.form.TextField({
            allowBlank: false
        }))},
        {
        	header:'類型',
        	dataIndex:'type',
        	renderer:cateRenderer,
        	editor:bg_categoryCombo
        },
        {header:'敘述',dataIndex:'description',editor:new Ext.grid.GridEditor(
        new Ext.form.TextField({}))},
        {
			header:'帳戶',
			dataIndex:'accountId',
			sortable:true,
            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
            	//id:'typeCombo',
            	typeAhead: true,
            	store:tr_accountStore,
            	valueField: 'id',
            	displayField: 'name',
            	lazyRender:true,
	            mode: 'local',
	            triggerAction: 'all', 
	            emptyText: '請選擇',
	            readOnly:true
        	})),
	        renderer : function(value){
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
        	header:'額度',
        	dataIndex:'amount',
        	renderer: 'usMoney',
        	editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
            	allowBlank: false
        	})
        )},
        {
        	header:'餘額',
        	dataIndex:'total',
        	renderer: 'usMoney',
        	editor:new Ext.grid.GridEditor(new Ext.form.NumberField({
        		readOnly:true,
            	allowBlank: false,
            	readOnly:true
        	})
        )},
		{
			id:'startDate',
			header:'起始日',
			dataIndex:'startDate',
			renderer: function(value, p, record) {
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
			editor:new Ext.grid.GridEditor(new Ext.form.DateField({
				format:'Y-m-d',
            	allowBlank: false
        	})
        )},
		{
			id:'endDate',
			header:'終止日',
			dataIndex:'endDate',
			renderer: function(value, p, record) {
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
			editor:new Ext.grid.GridEditor(new Ext.form.DateField({
				format:'Y-m-d',
            	allowBlank: false
        	})
        )}
    ]});
    
    var budget_data = [
        ['1','name1','type1','descn1','1000','1/30/2009','1/30/2010'],
        ['2','name2','type2','descn2','1000','1/30/2009','1/30/2010'],
        ['3','name3','type3','descn3','3000','1/30/2009','1/30/2010'],
        ['4','name4','type4','descn4','5000','1/30/2009','1/30/2010'],
        ['5','name5','type5','descn5','6000','1/30/2009','1/30/2010']
    ];
    
    var bg_store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../budget.do?action=getBudgetJSON'}),
        reader: new Ext.data.JsonReader({
        	totalProperty:"totalProperty"
        }, [
            {name:'id'},
			{name:'name'},
            {name:'type'},
            {name:'description'},
            {name:'accountId'},
            {name:'amount'},
            {name:'total'},
			{name:'startDate'},
			{name:'endDate'}
        ])
    });
    //bg_store.load();
    tr_accountStore.load();
    
    var bg_Record = Ext.data.Record.create([
    	{name:'id',type:'string'},
		{name:'name',type:'string'},
		{name:'type',type:'string'},
		{name:'description',type:'string'},
		{name:'accountId',type:'string'},
		{name:'amount',type:'string'},
		{name:'total',type:'string'},
		{name:'startDate',type:'string'},
		{name:'endDate',tyep:'string'}
	]);
    
    function bg_add(){
		var bgR = new bg_Record({
			id:'-1',
			name:'',
			type:'',
			description:'',
			accountId:'',
			amount:'0',
			total:'0',
			startDate:'',
			endDate:''
		});
		bg_grid.stopEditing();
		bg_store.insert(0,bgR);
		bg_grid.startEditing(0,0);
	}
    
	    var bg_tbar = new Ext.Toolbar(['-',
	{
		text:'新增',
		iconCls:'silk-add',
		scope:bg_store,
		handler:bg_add
	},'-',{
		text:'刪除',
		iconCls:'silk-delete',
		handler:function(){
			var rm_Records = bg_grid.getSelectionModel().getSelections();
			if(rm_Records.length==0){
				Ext.Msg.alert('訊息','請至少選擇一列刪除');
			}else{
				Ext.MessageBox.confirm('提示','確定刪除？',function(btn){
					if(btn=='yes'){
						url="../budget.do?action=removeBudgetJSON";
						remove(rm_Records,bg_store,url);
					}
				});
			}
		}
	},'-',{
		text:'儲存',
		iconCls:'icon-save',
		handler:function(){
			save(bg_store,'../budget.do?action=updateBudgetJSON');
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
	
//	var bg_totalfield = new Ext.form.NumberField({
//    	width:100,
//    	renderer: 'usMoney',
//      	id:'bg_totalfield',
//      	readOnly:true
//    });
	
    var bg_grid = new Ext.grid.EditorGridPanel({
        title:'預算表',
        store: bg_store,
        cm: bg_cm,
        sm: new Ext.grid.RowSelectionModel(),
        tbar:bg_tbar,
		bbar:new Ext.PagingToolbar({
			pageSize:5,
			store:bg_store,
			displayInfo:true
			//items:['->','總預算',bg_totalfield]
		}),
		modal:true,
		loadMask:true,
		viewConfig:{
        	forceFit:true  //自動調整每列寬度
    	}
    });
    
    var bg_chart = new Ext.FormPanel({
    	frame:true,
    	title:'圖表',
    	defaultType:'textfield',
		//html:'<div id="bg_chartdiv" z-index:-100><div>',
    	contentEl: 'bg_chartdiv',
		width:450,
		split:true
    });
    
    var budget = new Ext.Window({
    	el:'bg_window',
    	title:'預算',
    	width:1100,
    	height: 700,
//    	width:1000,
//		height:600,
		layout:'border',
		closeAction:'hide',
		constrain:true,
		plain:true,
		maximizable:true,
		collapsible:true,
		
    	items:[
    		{
    		region:'west',
    		autoWidth:true,
    		collapsible:true,
    		layout:'fit',
    		items:[bg_chart]
    		},
    		{
    		region:'center',
    		layout:'fit',
    		items:[bg_grid]
    		}
    	]
    });