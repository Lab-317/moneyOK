$(function () { 
	// Stack initialize
	var openspeed = 600;
	var closespeed = 600;
	$('.stack2>img').toggle(function(){
		var vertical = 0;
		var horizontal = 0;
		var $el=$(this);
		$el.next().children().each(function(){
			$(this).animate({top: vertical + 'px', left: horizontal + 'px'}, openspeed);
			vertical = vertical + 55;
			horizontal = (horizontal)*2;
		}
		);
		$el.next().animate({top: '40px', left: '10px'}, openspeed).addClass('openStack')
		   .find('li a>img').animate({width: '50px', marginLeft: '9px'}, openspeed);
		$el.animate({paddingBottom: '0'});
	}, null);

	// Stacks additional animation
	$('.stack2 li a').hover(function(){
		$("img",this).animate({width: '100px'}, 100);
		$("span",this).animate({marginLeft: '80px',marginTop: '20px'});
	},function(){
		$("img",this).animate({width: '50px'}, 100);
		$("span",this).animate({marginLeft: '30px',marginTop: '0px'});
	});
});