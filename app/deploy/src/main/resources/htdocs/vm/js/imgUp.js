$(function(){
	var delParent;
	var imgfwUrl;
	var defaults = {
		fileType         : ["jpg","png","bmp","jpeg","JPG"],   // 上传文件的类型
		fileSize         : 1024 * 1024 * 10                  // 上传文件的大小 10M
	};
		/*点击图片的文本框*/
	$("#file").change(function(){	 
		var idFile = $(this).attr("id");
		var file = document.getElementById(idFile);
		var imgContainer = $(this).parents(".z_photo"); //存放图片的父亲元素
		var fileList = file.files; //获取的图片文件
		var input = $(this).parent();//文本框的父亲元素
		var imgArr = [];
		//遍历得到的图片文件
		var numUp = imgContainer.find(".up-section").length;
		var totalNum = numUp + fileList.length;  //总的数量
		if(fileList.length > 5 || totalNum > 5 ){
			alert("上传图片数目不可以超过5个，请重新选择");  //一次选择上传超过5个 或者是已经上传和这次上传的到的总数也不可以超过5个
		}
		else if(numUp < 5){
			fileList = validateUp(fileList);
			for(var i = 0;i<fileList.length;i++){
				uploadFile(fileList[i]);
				 var imgUrl = imgfwUrl;
			     imgArr.push(imgUrl);
			 var $section = $("<section class='up-section fl loading'>");
			     imgContainer.append($section);
			 var $span = $("<span class='up-span'>");
			     $span.appendTo($section);
			
		     var $img0 = $("<img class='close-upimg close-upimg1'>").on("click",function(event){
				    event.preventDefault();
					event.stopPropagation();
					delParent = $(this).parent();
					var numUp = delParent.siblings().length;
					if(numUp < 6){
						delParent.parent().find(".z_file").show();
					}
					 delParent.remove();
				});   
				$img0.attr("src","img/a7.png").appendTo($section);
		     var $img = $("<img class='up-img up-opcity up-img1'>");
		         $img.attr("src",imgArr[i]);
		         $img.appendTo($section);
		     var $p = $("<p class='img-name-p'>");
		         $p.html(fileList[i].name).appendTo($section);
		     var $input = $("<input id='taglocation' name='taglocation' value='' type='hidden'>");
		         $input.appendTo($section);
		     var $input2 = $("<input id='tags' name='tags' value='' type='hidden'/>");
		         $input2.appendTo($section);
                
                console.log(imgArr);
		      
		   }
		}
		setTimeout(function(){
             $(".up-section").removeClass("loading");
		 	 $(".up-img").removeClass("up-opcity");
		 },450);
		 numUp = imgContainer.find(".up-section").length;
		if(numUp >= 5){
			$(this).parent().hide();
		}
	});
	
	
		function validateUp(files){
			var arrFiles = [];//替换的文件数组
			for(var i = 0, file; file = files[i]; i++){
				//获取文件上传的后缀名
				var newStr = file.name.split("").reverse().join("");
				if(newStr.split(".")[0] != null){
						var type = newStr.split(".")[0].split("").reverse().join("");
						if(jQuery.inArray(type, defaults.fileType) > -1){
							// 类型符合，可以上传
							if (file.size >= defaults.fileSize) {
								alert(file.size);
								alert('您这个"'+ file.name +'"文件大小过大');	
							} else {
								// 在这里需要判断当前所有文件中
								arrFiles.push(file);	
							}
						}else{
							alert('您这个"'+ file.name +'"上传类型不符合');	
						}
					}else{
						alert('您这个"'+ file.name +'"没有类型, 无法识别');	
					}
			}
			return arrFiles;
		}
		
		function uploadFile(file){
			console.log(file);
			var formData = new FormData();
			formData.append("photo", file);
			jQuery.ajax({
		        url:"fileUploadForEditor.do",
		        dataType : "json",
		        type : 'post',
				cache : false,
				async: false,
				//	 contentType : "multipart/form-data; charset=utf-8",
				//	 dataType : 'json',
				data : formData,
				processData : false,
				contentType : false,
		        success:function(data){
		        	imgfwUrl =  data.data[0];
		            
		        },
		        error:function(e){
		            alert("异常!");
		            //$(".loading").css("display","none");
		        }
			});
		}
	
})

$(function(){
	var delParent;
	var defaults = {
		fileType         : ["jpg","png","bmp","jpeg","JPG"],   // 上传文件的类型
		fileSize         : 1024 * 1024 * 10                  // 上传文件的大小 10M
	};
		/*点击图片的文本框*/
	$("#file2").change(function(){	 
		var idFile = $(this).attr("id");
		var file = document.getElementById(idFile);
		var imgContainer = $(this).parents(".z_photo2"); //存放图片的父亲元素
		var fileList = file.files; //获取的图片文件
		var input = $(this).parent();//文本框的父亲元素
		var imgArr = [];
		//遍历得到的图片文件
		var numUp = imgContainer.find(".up-section").length;
		var totalNum = numUp + fileList.length;  //总的数量
		if(fileList.length > 5 || totalNum > 5 ){
			alert("上传图片数目不可以超过5个，请重新选择");  //一次选择上传超过5个 或者是已经上传和这次上传的到的总数也不可以超过5个
		}
		else if(numUp < 5){
			fileList = validateUp(fileList);
			for(var i = 0;i<fileList.length;i++){
				uploadFile(fileList[i]);
				 var imgUrl = imgfwUrl;
			     imgArr.push(imgUrl);
			 var $section = $("<section class='up-section fl loading'>");
			     imgContainer.append($section);
			 var $span = $("<span class='up-span'>");
			     $span.appendTo($section);
			
		     var $img0 = $("<img class='close-upimg close-upimg2'>").on("click",function(event){
				    event.preventDefault();
					event.stopPropagation();
					delParent = $(this).parent();
					var numUp = delParent.siblings().length;
					if(numUp < 6){
						delParent.parent().find(".z_file").show();
					}
					 delParent.remove();
				});   
				$img0.attr("src","img/a7.png").appendTo($section);
		     var $img = $("<img class='up-img up-opcity up-img2'>");
		         $img.attr("src",imgArr[i]);
		         $img.appendTo($section);
		     var $p = $("<p class='img-name-p'>");
		         $p.html(fileList[i].name).appendTo($section);
		     var $input = $("<input id='taglocation' name='taglocation' value='' type='hidden'>");
		         $input.appendTo($section);
		     var $input2 = $("<input id='tags' name='tags' value='' type='hidden'/>");
		         $input2.appendTo($section);
                
                console.log(imgArr);
		      
		   }
		}
		setTimeout(function(){
             $(".up-section").removeClass("loading");
		 	 $(".up-img").removeClass("up-opcity");
		 },450);
		 numUp = imgContainer.find(".up-section").length;
		if(numUp >= 5){
			$(this).parent().hide();
		}
	});
	
	
	function validateUp(files){
		var arrFiles = [];//替换的文件数组
		for(var i = 0, file; file = files[i]; i++){
			//获取文件上传的后缀名
			var newStr = file.name.split("").reverse().join("");
			if(newStr.split(".")[0] != null){
					var type = newStr.split(".")[0].split("").reverse().join("");
					if(jQuery.inArray(type, defaults.fileType) > -1){
						// 类型符合，可以上传
						if (file.size >= defaults.fileSize) {
							alert(file.size);
							alert('您这个"'+ file.name +'"文件大小过大');	
						} else {
							// 在这里需要判断当前所有文件中
							arrFiles.push(file);	
						}
					}else{
						alert('您这个"'+ file.name +'"上传类型不符合');	
					}
				}else{
					alert('您这个"'+ file.name +'"没有类型, 无法识别');	
				}
		}
		return arrFiles;
	}
	
	function uploadFile(file){
		console.log(file);
		var formData = new FormData();
		formData.append("photo", file);
		jQuery.ajax({
	        url:"fileUploadForEditor.do",
	        dataType : "json",
	        type : 'post',
			cache : false,
			async: false,
			//	 contentType : "multipart/form-data; charset=utf-8",
			//	 dataType : 'json',
			data : formData,
			processData : false,
			contentType : false,
	        success:function(data){
	        	imgfwUrl =  data.data[0];
	            
	        },
	        error:function(e){
	            alert("异常!");
	            //$(".loading").css("display","none");
	        }
		});
	}
	
})




