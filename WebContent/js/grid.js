Ext.onReady(function(){
    var store = new Ext.data.SimpleStore({
        fields: ['id', 'name', 'desc'],
        data: [
            ['1', 'name1', 'desc1'],
            ['2', 'name2', 'desc2'],
            ['3', 'name3', 'desc3'],
            ['4', 'name4', 'desc4']
        ]
    });
    var grid = new Ext.grid.GridPanel({
        title: 'grid',
        viewConfig: {forceFit: true},
        store: store,
        columns: [
            {header:'id', dataIndex: 'id'},
            {header:'�迂', dataIndex:'name'},
            {header:'�膩', dataIndex:'desc'}
        ],
        tbar: new Ext.Toolbar(['�啣�','靽格','�芷']),
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store
        })
    });
    //grid.store.load();

    var win = new Ext.Window({
        el:'accountList',
		width: 400,
        height: 300,
        layout: 'fit',
        items: [grid]
    });
    win.show();
});