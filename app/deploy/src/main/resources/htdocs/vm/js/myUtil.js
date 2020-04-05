/**
 * $('#formId').loadData(arr)   
 *     将数据填充到表单里  
 *     arr参数key为标签的name  value为填充的值
 * @('#formid').getData()  
 * 	   将form标签的数据生成一个json的数组并且选择验证空的input不会被加载到数组中
 * */
window.myUtil = new Object();//命名空间
var myUtil = window.myUtil;
$(function(){
	$.fn.loadData = function(data){
	    for(var i in data){
	    	$(this).find("[name='"+i+"']").val(data[i]);
		}
	}
	$.fn.getData = function(){
		return myUtil.getData($(this).find("input,select,textarea"));
	}
	$.fn.validateItem = function(o){
		var ob = $(this);
		var options = myUtil.objectOptions(ob);
		if(!options.required){
			return true;
		}
		if(ob.val().length == 0 && o){
			alert(options.message);
			return false;
		}
		if(options.validate != undefined){
			var validate = (myUtil.validates)[options.validate];
			if(!validate.validate(ob.val())){
				if(o){
					alert(validate.message);
				}
				return false;
			}
		}
		return true;
		
		//console.log("," + (ob.val().length == "0"));
	}
	$.fn.validateForm = function(b){
		var elements = $(this).find("input,select,textarea");
		for(var i = 0; i < elements.length; i++){
			var element = elements[i];
			var r = $(element).validateItem(b);
			if(!r){
				return false;
			}
		}
		return true;
	}
});

/**
 * 获得该标签数组中所有标签的内容并且会进行属性的判断
 * @param 标签的数组
 * */
myUtil.getData =  function(arr){
	var r = {};
	for(var i = 0; i < arr.length; i++){
		var element = $(arr[i]);
		var options = myUtil.objectOptions(element);
		var key = element.attr("name");
		var value = element.val();
		if(options.isNotBlank){
			if(myUtil.isNotBlank(value)){
				r[key] = value;
			}
			continue;
		}
		r[key] = value;
	}
	return r;
}
/**
 * 判断一个字符串是否是空串
 * */
myUtil.isNotBlank = function(s){
	if(s == undefined){
		return false;
	}
	return s.trim() != "";
}
//返回该标签的data-form的属性并且如果没有相应的默认值会设置默认值
myUtil.objectOptions = function(ob){
	var defaultOptions = JSON.parse(JSON.stringify(myUtil.options));
	var s = $(ob).attr("data-form");
	if(myUtil.isNotBlank(s)){
		var options = eval('({' + s +'})');
		for(var i in options){
			defaultOptions[i] = options[i];
		}
	}
	return defaultOptions;
}
myUtil.options = 
{
    isNotBlank : false,//判断是否为空串,如果是空串就不纳入对象中
    required : false,  //设置为必输入项
    message : "此项为必须项！",//改字段不输入时的提示语
    alert : "alert" //验证不通过时的提示方式 默认为alert()方法提示
};
myUtil.validates = 
	{
		number : {
			validate : function(value){
				return value >= 0 || value <= 0;
			},
			message : "必须输入一个数字！"
		}
	}


























