	Ext.apply(Ext.form.VTypes, {
	    checkpw: function(val, field) {     
	        if (field.initialPassField) {               
			var register = Ext.getCmp(field.initialPassField);
			return (val == register.getValue());
	        }
			return true
	    },
	    checkpwText: '密碼確認錯誤'
	});

	Ext.QuickTips.init();
	
    var re_email = new Ext.form.TextField({
      	fieldLabel:'帳號(email)',
      	allowBlank:false,
      	name:'email',
      	id:'re_email',
      	vtype:'email',
      	msgTarget:'side'
      });
      
    var re_pw = new Ext.form.TextField({
      	fieldLabel:'密碼',
      	allowBlank:false,
      	inputType:'password',
      	name:'password',
      	id:'re_password',
      	msgTraget:'side'
      });
    
    var checkpwfield = new Ext.form.TextField({
      	fieldLabel:'確認密碼',
      	allowBlank:false,
      	inputType:'password',
      	name:'checkpassword',
      	id:'checkpassword',
      	vtype:'checkpw',
      	initialPassField:'re_password',
      	msgTarget:'side'
      });
      
    var panel = new Ext.FormPanel({
        frame: true,
    	items: [
			re_email,re_pw,checkpwfield
		]
    });
    
    var rgMask = new Ext.LoadMask(Ext.getBody(), {msg:"註冊中..請稍候....", removeMask:true});
    
	var register = new Ext.Window({
 		el:'register',
 		title: '註冊',
		width:300,
		closeAction:'hide',
		items:[panel],
		modal:true,
		
		buttons:[
			{
				text:'確認',
				handler:function(){
					rgMask.show();
					register.setActive(false);
					panel.getForm().submit({
						url:'user.do?action=register',
						method:'POST',
							success:function(form,action){
								Ext.Msg.alert('訊息','註冊成功');
								panel.getForm().reset();
								register.hide();
								rgMask.hide();
							},
							failure:function(){
								Ext.Msg.alert('錯誤','操作失敗');
								panel.getForm().reset();
								rgMask.hide();
							}
					});
				}
			},
			{
				text:'取消',
				handler:function(){
					//register.getForm().reset();
					register.hide();
				}
			}
		]
    });