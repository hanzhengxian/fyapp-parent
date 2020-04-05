$(document).ready(function(){  
	var  cl = $('body', parent.document).attr('class');
	if(cl.indexOf("left-side-collapsed")>0){
		
		 $("#page-wrapper").css('margin-left',"50px");
	}else{
		
		$("#page-wrapper").css('margin-left',"250px");
	}
});  