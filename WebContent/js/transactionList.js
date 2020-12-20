Ext.onReady(function(){
   
    var comboStore =  new Ext.data.ArrayStore({
        id: 0,
        fields: [
            'id',
            'displayText'
        ],
        data: [[1, '支出'], [2, '收入']]
    });

    var combo = new Ext.form.ComboBox({
    	id:'type',
        store:comboStore,
        mode: 'local',
        triggerAction: 'all',
        valueField: 'id',
        selectOnFocus :true,
        displayField: 'displayText'
    });
    Ext.namespace("Ext.ux");
    Ext.ux.comboBoxRenderer = function(combo) {
        return function(value) {
          var idx = combo.store.find(combo.valueField, value);
          var rec = combo.store.getAt(idx);
          return (rec == null ? '' : rec.get(combo.displayField) );
        };
      };
    var cm = new Ext.grid.ColumnModel([
        {header:'日期',dataIndex:'date',editor:new Ext.grid.GridEditor(
           new Ext.form.DateField({
        	   allowBlank: false,
        	   format:'Y/m/d'
           })
         ),xtype: 'datecolumn',format:'Y/m/d'},
        {header:'類型',dataIndex:'id',editor:new Ext.grid.GridEditor(
        		combo),renderer: Ext.ux.comboBoxRenderer(combo)},
        {header:'敘述',dataIndex:'description',editor:new Ext.grid.GridEditor(
                new Ext.form.TextField({}))},
        {header:'金額',dataIndex:'amount',editor:new Ext.grid.GridEditor(
                new Ext.form.TextField({
                    allowBlank: false
         }))},
    ]);

    var comboEditor = new Ext.grid.GridEditor(
        new Ext.form.ComboBox({
                allowBlank: false,
                triggerAction: 'all',
                mode: 'local',
                store: new Ext.data.ArrayStore({
                    id: 0,
                    fields: [
                        'id',
                        'displayText'
                    ],
                    data: [[1, '支出'], [2, '收入']]
                }),
                valueField: 'id',
                displayField: 'displayText'
     }));
    var data = [
        ['1990/12/21','支出','descn1','1'],
        ['1990/12/21','1','descn2','1'],
        ['1990/12/21','2','descn3','3'],
        ['1990/12/21','1','descn4','5'],
        ['1990/12/21','name5','descn5','6']
    ];
    var myStore =  new Ext.data.JsonStore({
    	  url: '/moneyOK/transaction.do?action=getVarTransactionJSON',
    	  fields: ['id','amount','description','item','date'],
    	  reader:jsonReader
    });
    var jsonReader = new Ext.data.JsonReader({
    	root:'root',
    	id:"id"},
    	[{name:'id'},
    	{name:'date'},
    	{name:'amount'}]
    );
    myStore.load();
    var store = new Ext.data.Store({
        proxy: new Ext.data.MemoryProxy(data),
        reader: new Ext.data.ArrayReader({}, [
            {name: 'name'},
            {name: 'type'},
            {name: 'descn'},
            {name: 'total'}
        ])
    });
    store.load();
    
    var grid = new Ext.grid.EditorGridPanel({
        title:'消費清單',
        store: myStore,
		//autoHeight:true,
        cm: cm
		//renderTo:'accountList'
    });
    
    var account = new Ext.Viewport({
    	el:'accountList',
    	width: 600,
    	height: 400,
		layout:'fit',
    	items:[grid]
    });
    account.show();
});
