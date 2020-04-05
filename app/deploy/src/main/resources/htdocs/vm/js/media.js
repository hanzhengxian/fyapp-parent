function clean(){
	$("#a input").val("");
	$("#a select").val("");
}
function sorce(){
	$('#mytab').bootstrapTable("selectPage",1);	
}
function adduser(){
    if(!checkMobile($("#cell").val())){
    	alert("请输入正确格式的手机号码！");
    	return ;
    }
	$("form[name='userform']").ajaxSubmit({
	        type:"post",
	        url: "adduser.do",
	        dataType:"json",
	        beforeSubmit:function(){
	        },
	        success:function(data){
	            if(!data.bizSucc){
	                alert(data.err_msg);
	                return false;
	            }else{
					alert(data.data);
					
	            	window.location.reload(true);
	            }
	            
	        },
	        error:function(e){
	            alert("异常!");
	        }

	    });
}
 
	//获取上传进度
	function selectprogress(id){
		
		jQuery.ajax({
				 url : "selectprogress.do",
				 type : 'post',
				 dataType : 'json',
				 data:{id:id},
				 success : function(data) {
				 	
					var r = parseInt((data.progress / data.progresssize) * 100) ;
					
				   $('#progress').css("width",r+"%");
            		$('#progress').html(r+"%"); 
				 },
				 error : function() {  
				            alert("异常！");  
				     }  
		});
	}

var all;
//生成用户数据
$('#mytab').bootstrapTable({
    method: 'post',
    contentType: "application/x-www-form-urlencoded",//必须要有！！！！
    url:"selectallmedia.do?type=VIDEO",//要请求数据的文件路径
    dataType : 'json',
    striped: true, //是否显示行间隔色
  	pageNumber: 1, //初始化加载第一页，默认第一页
    pageSize:10,//单页记录数
    pageList:[5,10,20,30],//分页步进值
    pagination:true,//是否分页
    queryParamsType:'limit',//查询参数组织方式
    sidePagination:'server',//指定服务器端分页
    queryParams: function(p){
    	 return params(p);
    },
  	
    clickToSelect: true,//是否启用点击选中行
    toolbarAlign:'right',//工具栏对齐方式
    buttonsAlign:'right',//按钮对齐方式
    toolbar:'#toolbar',//指定工作栏
    columns:[
		 {
            title:'用户昵称',
            field:'NICK_NAME'
        },
		{
            title:'视频ID',
            field:'MEDIA_ID'
        },
		{
            title:'名称',
            field:'MEDIA_NAME'
        },
		
		{
            title:'购买数量',
            field:'BUY_COUNT'
        },
		{
            title:'价格',
            field:'MEDIA_PRICE'
        },
		{
            title:'排序',
            field:'RANK'
        },
		{
            title:'时长',
            field:'TIME_LENGTH'
        },
		{
            title:'观看收听人数',
            field:'VIEW_COUNT'
        },
		{
            title:'视频状态',
            field:'MEDIA_URL',
			formatter : function (value,row,index){
			 if(value==null) {return "未上传";}
			 else{return "已上传";}
			}
        },
		{
            title:'推荐状态',
            field:'RECOMMEND_FLG',
			formatter : function (value,row,index){
				if(value=="0") return "推荐";
				if(value=="1") return "不推荐";
			}
        },
		{
            title:'审核状态',
            field:'STATUS',
			formatter : function (value,row,index){
				if(value=="0") return "待审核";
				if(value=="1") return "审核通过";
				if(value=="2") return "驳回审核";
			}
        },
        {
            title:'操作',
            field:'ID',
            formatter: function (value,row,index){
				
				var video="";
				if((row.MEDIA_URL=="" || row.MEDIA_URL==null) && row.STATUS=="0"){
					video=" | <a href='#' onclick=showuploadvideo('"+value+"')>上传视频</a>"+
					" | <a href='#' onclick=showuploadmedia('"+index+"','1','1') >通过</a> | "+
					"<a href='#' onclick=showuploadmedia('"+index+"','2') >驳回</a>";
				}else if(row.STATUS=="0"){
					video = " | <a href='#' onclick=showuploadvideo('"+value+"')>修改视频</a>"+
						" | <a href='#' onclick=showuploadmedia('"+index+"','1') >通过</a> | "+
					"<a href='#' onclick=showuploadmedia('"+index+"','2') >驳回</a>";
				}else if(row.STATUS=="1"){
                    video=" | <a href='#' onclick=showuploadvideo('"+value+"')>修改视频</a>"+
                    	" | <a href='#' onclick=updateprice('"+value+"','"+row.MEDIA_PRICE+"')>收费设置</a>";
				}
				var flg=""
				if(row.RECOMMEND_FLG=="0") flg="<a href='#' onclick=updatemedia('"+value+"','RECOMMEND_FLG','1')>取消推荐</a>" ;
				if(row.RECOMMEND_FLG=="1") flg="<a href='#' onclick=updatemedia('"+value+"','RECOMMEND_FLG','0')>设为推荐</a>" ;
				
				var updown=""
				if(row.UP_DOWN=="UP") updown="<a href='#' onclick=updatemedia('"+value+"','UP_DOWN','DOWN')>下架</a>" ;
				if(row.UP_DOWN=="DOWN") updown="<a href='#' onclick=updatemedia('"+value+"','UP_DOWN','UP')>上架</a>" ;
				
                return "<a href='#' onclick=showmedia('"+index+"','"+row.STATUS+"')>查看详情</a> | "+flg+video+
            	"| <a href='#' onclick=showupdatename('"+value+"','"+row.MEDIA_NAME+"')>修改名称</a> | "+updown+
            	"| <a href='#' onclick=changeView('"+index+"')>修改封面</a> | ";
            }
        }
    ],
    locale:'zh-CN',//中文支持,
    responseHandler:function(res){
        //在ajax获取到数据，渲染表格之前，修改数据源
		all = res.rows;
        return res;
    }
})
function showupdatename(id,name){
	$("#updatenameid").val(id);
	if(name=="null"){
		$("#updatename").val("");
	}else{
		$("#updatename").val(name);
	}
	
	$("#uploadnamemodal").modal('show');
}
function updateprice(id,price){
	
	$("#updatepriceid").val(id);
	$("#MEDIAPRICE").val(price)
	$("#uploadprice").modal('show');
}

function  submitprice(){
	updatemedia($("#updatepriceid").val(),"MEDIA_PRICE",$("#MEDIAPRICE").val());
}
function submitupdatename(){
	if($("#updatename").val()==""){
		alert("请填写名称！");
		return ;
	}
	jQuery.ajax({
		 url : "updatealltable.do",
		 type : 'post',
		 data:{"id":$("#updatenameid").val(),"filed":"MEDIA_NAME","value":$("#updatename").val(),"table":"dxs_media"},
		 dataType : 'json',
		 success : function(json) {
		 if(!json.bizSucc){
           alert(json.err_msg);
           return false;
        }else{
			alert("修改成功！");
			showallmodal('uploadnamemodal', 'hide');
			showallmodal('uploadprice', 'hide');
			$("#mytab").bootstrapTable('refresh');
        }
		 },
		 error : function() {  
		            alert("异常！");  
		     }  
});
}
function updatemedia(id,filed,value){
 jQuery.ajax({
				 url : "updatemedia.do",
				 type : 'post',
				 data:{"id":id,"field":filed,"value":value},
				 dataType : 'json',
				 success : function(json) {
				 if(!json.bizSucc){
	                alert(json.err_msg);
	                return false;
	             }else{
					alert(json.data);
					showallmodal('uploadprice', 'hide');
					$("#mytab").bootstrapTable('refresh');
	             }
				 },
				 error : function() {  
				            alert("异常！");  
				     }  
		});
}
//显示审核的框
function showuploadmedia(index,type,isshow){
	if(isshow=="1"){
		alert("请先上传视频！");
		return ;
	}
	$("#updateid").val(all[index].ID);
	$("#updatestatus").val(type);
	$("#updateuserid").val(all[index].UP_USER_ID);
	$("#updatemediaid").val(all[index].MEDIA_ID);
	$("#uploadmedia").modal('show');
}
//审核
function submitmedia(){
	
		   jQuery.ajax({
				 url : "updatemedia.do",
				 type : 'post',
				 data:{"id":$("#updateid").val(),"field":"STATUS","value":$("#updatestatus").val(),"memo":$("#memo").val(),"userid":$("#updateuserid").val(),"MEDIA_ID":$("#updatemediaid").val()},
				 dataType : 'json',
				 success : function(json) {
				 if(!json.bizSucc){
	                alert(json.err_msg);
	                return false;
	             }else{
					alert(json.data);
					$("#uploadmedia").modal('hide');
					$("#uploadmedia textarea").val("");
					$("#mytab").bootstrapTable('refresh');
	             }
				 },
				 error : function() {  
				            alert("异常！");  
				     }  
		});
	
}

//显示添加框
function showallmodal(id,type){
	$("#"+id).modal(type);
}
function showmedia(number){
	for (var k in all[number]){  
		$("#"+k).val(all[number][k]);
    }  
	$("#VIEW_URL").attr('src',all[number].VIEW_URL);
	
	var myPlayer = videojs('my-video');
			videojs("my-video").ready(function(){
				var myPlayer = this;
				myPlayer.src(all[number].MEDIA_URL);
				myPlayer.play();
			});
	showallmodal('myModal','show');
	
}

//获取搜索的所有值
function params(params){
	 $("#a input[type='text']").each(function(){
		    params[$(this).attr("id")] = $(this).val();
	 });
	 $("#a select").each(function(){
		    params[$(this).attr("id")] = $(this).val();
	 });
	 return params;
}
function showuploadvideo(id){
	$("#id").val(id)
	$("#uploadfile").modal('show');
}

///////上传文件操作,较大文件分片处理/////////////////////////////////
var shardSize = 1 * 1024 * 1024;    //以1MB为一个分片
var succeed = 0;
var databgein;  //开始时间
var dataend;    //结束时间
var page = {
    init: function(){
        $("#submitmedia").click(function(){
        	if(document.getElementById("url").value != ""){
        		jQuery.ajax({
                    url: "changeMediaUrl.do",
                    type: "POST",
                    data: $('#mediaForm').serialize(),
                    cache: true,
                    success: function (data) {
                    	if (!data.bizSucc) {
    						alert(data.err_msg);
    						return false;
    					} else {
    						showallmodal('uploadfile', 'hide');
    						$("#url").val("");
    						$("#mytab").bootstrapTable('refresh');
    					}
                    }, error: function () {
                        alert("服务器出错!");
        				
                    }
                });
        	}
        	else {
        		if (document.getElementById("file").value == "") {
                    alert("请选择文件!");
        			return ;
                } 
        		 else if(!checkvideofile($("#file").val())){
        		 	alert("请选择视频文件！");
        			return ;
        		}
    			$('#submitmedia').attr("disabled",true)
    			$('#submitmedia').html("上传中！");
                databgein=new Date();
                var file = $("#file")[0].files[0];  //文件对象
                 repeatupload(file);
        	}
        });
    } 
};
$(document).ready(function(){  
	page.init();
});

 //用于生成uuid
function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var name,size;
function repeatupload(file) {
    name = file.name;        //文件名
    size = file.size;        //总大小
    shardCount = Math.ceil(size / shardSize);  //总片数
    var uuid = guid();
    var date = new Date().Format('yyyyMMdd');
    for (var i = 0; i < shardCount; i++) {
        upload(file, i,2,uuid,date,name);
    }
}

var parsize=0;
/*
     * file 文件对象
     * uuid 后端生成的uuid
     * filemd5 整个文件的md5
     * date  文件第一个分片上传的日期(如:20170122)
    */
function upload(file,i,type,uuid,date,name) {
   //计算每一片的起始与结束位置
    var start = i * shardSize;
    var end = Math.min(size, start + shardSize);
    //构造一个表单，FormData是HTML5新增的
    var form = new FormData();
    if (type === 1) {
        form.append("action", "check");  //检测分片是否上传
        $("#param").append("action==check ");
    } else {
        form.append("action", "upload");  //直接上传分片
        form.append("data", file.slice(start, end));  //slice方法用于切出文件的一部分
        $("#param").append("action==upload ");
    }

    form.append("uuid", uuid);
    form.append("date", date);
    form.append("name", name);
    form.append("size", size);
    form.append("total", shardCount);  //总片数
    form.append("index", i+1);        //当前是第几片

    var ssindex = i+1;

    //按大小切割文件段　　
    var data = file.slice(start, end);
    var r = new FileReader();
    r.readAsBinaryString(data);

    $(r).load(function (e) {
        var bolb = e.target.result;
        var md5 = hex_md5(bolb);
        form.append("md5", md5);
        //Ajax提交
        jQuery.ajax({
            url: "upload",
            type: "POST",
            data: form,
            async: true,        //异步
            processData: false,  //很重要，告诉jquery不要对form进行处理
            contentType: false,  //很重要，指定为false才能形成正确的Content-Type
            success: function (data) {
				if(data.flag=="3"){
					//修改进度条
					var r = parseInt(( parsize+1/shardCount) * 100) ;
					$('#progress').css("width",r+"%");
            		$('#progress').html(r+"%"); 
					//判断最后一次上传完成
    				if(data.status){
						$('#progress').css("width","100%");
            			$('#progress').html("100%"); 
            			//上传完成后修改数据库内容
    					up(data.fileaddress);
    				}
				}
				
            }, error: function () {
                alert("服务器出错!");
				
            }
        });
    })   
}
//上传完成后修改数据库内容
function up(fileaddress) {
   jQuery.ajax({
		 url : "uploadvideo.do",
		 type : 'post',
		 data:{"id":$("#id").val(),"fileaddress":fileaddress},
		 dataType : 'json',
		 success : function(json) {
		 if(json.flag) {
			alert(json.data);
			window.location.reload();
		 } 
		 else{
			 alert(json.data);
			 $('#submitmedia').attr("disabled","")
			$('#submitmedia').html("确定");
		 }
	},
	error : function() {  
		            alert("异常！");  
			$('#submitmedia').attr("disabled","")
			$('#submitmedia').html("确定");
		     }  
	});
}  

//修改数据
function changeMediaInfo() {
	if (confirm("确定修改该视频信息？")) {
		jQuery.ajax({
			url : "changeMediaInfo.do",
			type : 'post',
			data : $('#noticeform').serialize(),
			dataType : 'json',
			cache: true,
			success : function(json) {
				if (!json.bizSucc) {
					alert(json.err_msg);
					return false;
				} else {
					showallmodal('myModal', 'hide');
					$("#mytab").bootstrapTable('refresh');
				}
			},
			error : function() {
				alert("异常！");
			}
		});
	} else {
		return;
	}
}

function changeView(number){
	$("#viewId").val(all[number].ID);
	$("#preview").html("<img src="+all[number].VIEW_URL+" />");
	$("#oldurl").val(all[number].VIEW_URL);
	$("#changeView").modal('show');
}


//保存
function changeViewUrl() {
	var viewfile = $("#viewfile").val();
	var formData = new FormData();
	if ($("#viewId").val() == "") {
		if (viewfile == "") {
			alert("请选择上传图片！");
			return;
		}
		var strExtension = viewfile
				.substr(viewfile.lastIndexOf('.') + 1);
		strExtension = strExtension.toLocaleLowerCase();
		if (strExtension != 'jpg' && strExtension != 'gif'
				&& strExtension != 'png' && strExtension != 'bmp') {
			alert("请选择图片文件！");
			return;
		}
	}
	
	formData.append("oldurl", $("#oldurl").val());
	formData.append("id", $("#viewId").val());
	formData.append("viewfile", $("#viewfile")[0].files[0]);
	
	jQuery.ajax({
		url : "changeViewMedia.do",
		type : 'post',
		cache : false,
		//	 contentType : "multipart/form-data; charset=utf-8",
		//	 dataType : 'json',
		data : formData,
		processData : false,
		contentType : false,
		success : function(json) {
			if (json.flag) {
				alert(json.data);
				$("#changeView").modal("hide");
				$("#mytab").bootstrapTable('refresh');
			} else {
				alert(json.data);
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}

//图片预览
function preview(file) {
	var prevDiv = document.getElementById('preview');
	prevDiv.style.display = "block";
	if (file.files && file.files[0]) {
		var reader = new FileReader();
		reader.onload = function(evt) {
			prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
		}
		reader.readAsDataURL(file.files[0]);
	} else {
		prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
	}
}
/////////////////////////上传结束////////////////////////////////////