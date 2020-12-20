Ext.onReady(function(){   
	
	Ext.QuickTips.init();
    
    var emailfield = new Ext.form.TextField({
      	fieldLabel:'帳號(email)',
      	allowBlank:false,
      	name:'email',
      	id:'email',
      	vtype:'email',
      	msgTarget:'side'
      });
      
    var pwfield = new Ext.form.TextField({
      	fieldLabel:'密碼',
      	allowBlank:false,
      	inputType:'password',
      	name:'password',
      	id:'password',
      	msgTraget:'side'
      });
    
	var LoginPanel = new Ext.FormPanel({
		frame: true,
		region:'west',
		title: '登入',
		width: 300,
		modal:true,
		url:'user.do?action=login',
		items: [
			emailfield,pwfield
		],
		buttons:[{
			text:'確認',
			handler:function(){
				LoginPanel.getForm().submit({
						success:function(form,action){
							//window.location.reload();
							window.location.href="ExtJS/layout.html";
							Ext.Msg.alert('訊息','登入成功');
						},
						failure:function(){
							Ext.Msg.alert('錯誤','帳號密碼有誤請確認');
						}
				});
			}
		},{
			text:'註冊',
			handler:function(){
				register.show();
				var re = Ext.get('register');
				re.fadeIn();
			}
		}]
    });
	LoginPanel.render('login');
});