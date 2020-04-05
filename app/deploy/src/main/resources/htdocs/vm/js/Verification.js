//判断输入是否是有效的电子邮件
function isemail(str)
{
 var result=str.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);
 if(result==null) return false;
 return true;
}
// 判断输入是否是一个由 0-9 / A-Z / a-z 组成的字符串
function isalphanumber(str){
	var result=str.match(/^[a-zA-Z0-9]+$/); 
	if(result==null) return false; 
	return true;
}
//判断输入是否是一个数字--(数字包含小数)--
function isnumber(str)
{
 return !isNaN(str);
}
//判断输入是否是一个整数
function isint(str)
{
 var result=str.match(/^(-|\+)?\d+$/);
 if(result==null) return false;
 return true;
}
//去除字符串的首尾的空格
function trim(str){
	 return str.replace(/(^\s*)|(\s*$)/g, "");
}
//匹配中国邮政编码(6位)
function ispostcode(str)
{
 var result=str.match(/[1-9]\d{5}(?!\d)/);
 if(result==null) return false;
 return true;
}
//匹配国内电话号码(0511-4405222 或 021-87888822)
function istell(str)
{
 var result=str.match(/\d{3}-\d{8}|\d{4}-\d{7}/);
 if(result==null) return false;
 return true;
}
//匹配腾讯QQ号
function isqq(str)
{
 var result=str.match(/[1-9][0-9]{4,}/);
 if(result==null) return false;
 return true;
}
//匹配身份证(15位或18位)
function isidcard(str)
{
 var result=str.match(/\d{15}|\d{18}/);
 if(result==null) return false;
 return true;
}
//验证手机号码
function checkMobile(str) {
   var re = /^1\d{10}$/
   if (re.test(str)) {
       return true;
   } else {
	   return false;
   }
}
//验证网址
function checkUrl(urlString){
	if(urlString!=""){
		var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
		if(!reg.test(urlString)){
			return false;
		}else{
			return true;
		}
	 }
}
//验证企业注册工商号  15位数字
function checkre(str){
	var reg=/(^\d{15}$)/;
	if(!reg.test(str)){
		return false;
	}else{
		return true;
	}
}
// 验证经度-180.0～+180.0（整数部分为0～180，必须输入1到5位小数）
function checklongitude(str){
	var reg= /^[\-\+]?(0?\d{1,2}\.\d{1,5}|1[0-7]?\d{1}\.\d{1,5}|180\.0{1,5})$/;
	if(!reg.test(str)){
		return false;
	}else{
		return true;
	}
}
//验证纬度 -90.0～+90.0（整数部分为0～90，必须输入1到5位小数）
function checklatitude(str){
	var reg= /^[\-\+]?([0-8]?\d{1}\.\d{1,5}|90\.0{1,5})$/;
	if(!reg.test(str)){
		return false;
	}else{
		return true;
	}
}
//验证是否是图片格式
function checkphotofile(filename){
	var strExtension = filename.substr(filename.lastIndexOf('.') + 1);
	 strExtension = strExtension.toLocaleLowerCase();
	if (strExtension != 'jpg' && strExtension != 'gif'
	&& strExtension != 'png' && strExtension != 'bmp') {
	   
	    return false;
	}else{
		return true;
	}
}
//验证是否是视频格式
function checkvideofile(filename){
	var strExtension = filename.substr(filename.lastIndexOf('.') + 1);
	 strExtension = strExtension.toLocaleLowerCase();
	if (strExtension != 'avi' && strExtension != 'rmvb'
	&& strExtension != 'rm' && strExtension != 'asf'
		&& strExtension !=  'divx' && strExtension !=  'mpg' 
			&& strExtension !=  'mpeg' && strExtension !=  'mpe' 
				&& strExtension !=  'wmv' && strExtension !=  'mp4' 
					&& strExtension !=  'mkv' && strExtension !=  'vob' ) {
	   
	    return false;
	}else{
		return true;
	}
}
//验证是否是音频格式
function checkmusicfile(filename){
	var strExtension = filename.substr(filename.lastIndexOf('.') + 1);
	 strExtension = strExtension.toLocaleLowerCase();
	if (strExtension != 'mp1' && strExtension != 'mp2'
		&& strExtension != 'mp3' && strExtension != 'wma'
			&& strExtension !=  'wmv' && strExtension !=  'rm' 
				&& strExtension !=  'aac' && strExtension !=  'mid' 
					&& strExtension !=  'wav' && strExtension !=  'vqf' 
						&& strExtension !=  'avi' && strExtension !=  'mpg'
							&& strExtension !=  'mpeg' && strExtension !=  'cda') {
	   
	    return false;
	}else{
		return true;
	}
}
//验证是否是xlsx格式
function checkxlsxfile(filename){
	var strExtension = filename.substr(filename.lastIndexOf('.') + 1);
	 strExtension = strExtension.toLocaleLowerCase();
	if (strExtension != 'xlsx') {
	    return false;
	}else{
		return true;
	}
}