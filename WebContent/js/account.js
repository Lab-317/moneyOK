//Ext.onReady(function(){
	
	Ext.QuickTips.init();
    
	var ac_proxy=new Ext.data.HttpProxy(    
		{
			url:'../account.do?action=getAccountsJson'
		}
	);
    
    var ac_typeStore = new Ext.data.Store({
    	proxy:new Ext.data.HttpProxy({url:'../account.do?action=getAccountTypeJSON'}),
    	reader: new Ext.data.JsonReader({
    		
    	},[
    		{name:'id'},
    		{name:'name'}
    	])
    });
   	ac_typeStore.load();
    
    var ac_cm = new Ext.grid.ColumnModel([
        {
			header:'序號',
			dataIndex:'id',
			sortable:true,
			hidden:true,
			editor: new Ext.form.TextField({  
				allowBlank: false
            }) 
		},
		{
			header:'名稱',
			dataIndex:'name',
			sortable:true,
			editor: new Ext.form.TextField({
            	allowBlank: false
            })
		},
        {
			header:'類型',
			dataIndex:'type',
			sortable:true,
            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
            	//id:'typeCombo',
            	typeAhead: true,
            	store:ac_typeStore,
            	valueField: 'id',
            	displayField: 'name',
            	lazyRender:true,
	            mode: 'local',
	            triggerAction: 'all', 
	            emptyText: '請選擇',
	            //hiddenName:'typeCombo',
	            readOnly:true
        	})),
	        renderer : function(value){
				var rec = ac_typeStore.getAt(value);
				if (rec == null) {
					return value;
				}
				else {
					return rec.data['name'];
					
				}
	        }
		},
        {
			header:'敘述',
			dataIndex:'description',
			sortable:true,
			editor: new Ext.form.TextField({  
            	allowBlank: true
            })
		},
        {
			header:'金額',
			dataIndex:'total',
			sortable:true,
			renderer:'usMoney',
			editor: new Ext.form.NumberField({  
            	allowDecimals:false,
            	allowBlank: false
            })
		}
    ]);
    
	var ac_Record = Ext.data.Record.create([
		{name:'id',type:'string'},
		{name:'name',type:'string'},
		{name:'total',type:'string'},
		{name:'type'},
		{name:'description'}
	]);
	
	var ac_reader=new Ext.data.JsonReader({
			totalProperty:"totalProperty"
		},[
			{name: 'id'},
			{name: 'name'},
			{name: 'total'},
			{name: 'type'},
			{name:'description'}                      
		]
	)
	 
	var ac_store=new Ext.data.GroupingStore({
		proxy:ac_proxy,
		reader:ac_reader,
		groupField:'type',
		sortInfo:{field:'id',direction:'ASC'}
	});
	//ac_store.load();
	
	var ac_tbar = new Ext.Toolbar(['-',
		{
			text:'新增',
			iconCls:'silk-add',
			scope:ac_store,
			handler:ac_add
		},'-',{
			text:'刪除',
			iconCls:'silk-delete',
			handler: function(){
			var rm_Records = ac_grid.getSelectionModel().getSelections();
			if(rm_Records.length==0){
				Ext.Msg.alert('訊息','請至少選擇一列刪除');
			}else{
				Ext.MessageBox.confirm('提示','確定刪除？',function(btn){
					if(btn=='yes'){
						url = "../account.do?action=removeAccountJSON";
						remove(rm_Records,ac_store,url);
					}
				});
			}
		}
		},'-',{
			text:'儲存',
			iconCls:'icon-save',
			handler: function(){
			save(ac_store,'../account.do?action=updateAccountJSON');
			Ext.MessageBox.show({
		       msg: '資料儲存中‧‧‧',
		       progressText: 'Saving...',
		       width:300,
		       wait:true,
		       waitConfig: {interval:200}
		       //icon:'../img/download.gif' //custom class in msg-box.html
			});
		}
		},'-'])
	
	function ac_add(){
		var acR = new ac_Record({
			id:'-1',
			name:'',
			type:'',
			total:'0'
		});
		ac_grid.stopEditing();
		ac_store.insert(0,acR);
		ac_grid.startEditing(0,0);
		ac_store.sort('type','ASC');
		}
	
	var ac_totalfield = new Ext.form.NumberField({
    	width:100,
    	renderer: 'usMoney',
      	id:'ac_totalfield',
      	readOnly:true
    });
	
    var ac_grid = new Ext.grid.EditorGridPanel({
        title:'帳戶清單',
        store: ac_store,
		cm:ac_cm,
        sm: new Ext.grid.RowSelectionModel(),
        tbar:ac_tbar,
    	bbar:new Ext.PagingToolbar({
			pageSize:5,
			store:ac_store,
			//displayInfo:true
			items:['->','總資產',ac_totalfield]
		}),
		loadMask:true,
		view:new Ext.grid.GroupingView(
			{
			showGroupName:false,
			forceFit:true
			}
		)
    });
    
    var ac_chart = new Ext.FormPanel({
    	frame:true,
    	title:'圖表',
    	defaultType:'textfield',
		//html:'<div id="ac_chartdiv"><div>',
		contentEl: 'ac_chartdiv',
		width:550,
		split:true
    });
    
    var account = new Ext.Window({
    	el:'ac_window',
		title:'帳戶',
		layout:'border',
		closeAction:'hide',
		width:1100,
		height:700,
//		width:1000,
//		height:600,
		id:'ac_id',
		constrain:true,
		maximizable:true,
		collapsible:true,
    	items:[{
    		region:'west',
    		autoWidth:true,
    		collapsible:true,
    		items:[ac_chart]
    	},{
    		region:'center',
    		layout:'fit',
    		items:[ac_grid]
    	}
    	]
    });
    
    
    ac_store.on('load',function(){
//   		ac_totalfield.setRawValue(ac_store.sum('total'));
		Ext.Ajax.request({
			url:'../account.do?action=getAccountTotalJSON',
			method:'GET',
			//scope:this,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				ac_totalfield.setRawValue(obj[0].accountTotal);
			}
		});
	});
	
	ac_store.on('update',function(){
//   		ac_totalfield.setRawValue(ac_store.sum('total'));
		Ext.Ajax.request({
			url:'../account.do?action=getAccountTotalJSON',
			method:'GET',
			//scope:this,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				ac_totalfield.setRawValue(obj[0].accountTotal);
			}
		});
	});
	
	ac_store.on('remove',function(){
		ac_totalfield.setRawValue(ac_store.sum('total'));
	});
