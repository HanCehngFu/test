var layer = layui.layer; //全局生命layer
var windHeight = document.documentElement.availHeight; //获取页面高度
var iframeHeight = 0;


//********获取某时间setdate的第arg天
function rangeFun(arg, setdate) {
    var date1 = new Date(setdate);
    var date2 = new Date(date1);
    date2.setDate(date1.getDate() + arg);
    var times = date2.getFullYear() + "/" + (date2.getMonth() + 1) + "/" + date2.getDate();
    return times
}
//格式化时间
function FormatDate(str, type) {
    if (str == null || str == "") {
        return "";
    } else {
        // var date = new Date(parseInt(str));
        var date = str;
        console.log(date)
        if (type == "yyyy-mm-dd") {
            return date.getFullYear() + "-" + add0((date.getMonth() + 1)) + "-" + add0(date.getDate());
        } else if (type == "yyyy/mm/dd") {
            return date.getFullYear() + "/" + add0((date.getMonth() + 1)) + "/" + add0(date.getDate());
        } else if (type == "yyyy.mm.dd") {
            return date.getFullYear() + "." + add0((date.getMonth() + 1)) + "." + add0(date.getDate());
        } else if (type == "yyyymmdd") {
            return date.getFullYear() + add0((date.getMonth() + 1)) + add0(date.getDate());
        } else if (type == "yyyy-mm-dd hh:MM:ss") {
            return date.getFullYear() + "-" + add0((date.getMonth() + 1)) + "-" +
                add0(date.getDate()) + " " + add0(date.getHours()) + ":" +
                add0(date.getMinutes()) + ":" + add0(date.getSeconds());
        } else if (type == "yyyy-mm-dd hh:MM") {
            return date.getFullYear() + "-" + add0((date.getMonth() + 1)) + "-" +
                add0(date.getDate()) + " " + add0(date.getHours()) + ":" +
                add0(date.getMinutes());
        } else if (type == "yyyy.mm.dd hh:MM") {
            return date.getFullYear() + "." + add0((date.getMonth() + 1)) + "." +
                add0(date.getDate()) + " " + add0(date.getHours()) + ":" +
                add0(date.getMinutes());
        } else if (type == "T-yyyy-mm-dd") {
            str = str.replace("T", " ");
            str = str.split(" ")[0];
            return str;
        } else if (type == "T-yyyy-mm-dd hh:MM") {
            str = str.replace("T", " ");
            str = str.substring(0, 16);
            return str;
        } else if (type == "T-yyyy-mm-dd hh:MM:ss") {
            str = str.replace("T", " ");
            str = str.substring(0, 19);
            return str;
        } else if (type == "mm-dd") {
            return add0((date.getMonth() + 1)) + "-" + add0(date.getDate())
        } else if (type == "hh:MM") {
            return add0(date.getHours()) + ":" + add0(date.getMinutes());
        };

    }
}

function add0(m) {
    return m < 10 ? '0' + m : m
}
//获取URL中参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); //返回参数值
    return null;
}
//数组去空值
function clear_arr_trim(array) {

    for (var i = 0; i < array.length; i++) {
        if (array[i] == "" || typeof (array[i]) == "undefined") {
            array.splice(i, 1);
            i = i - 1;
        }
    }
    return array;
}

//判断值是否存在数组中
Array.prototype.in_array = function (element) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == element) {
            return true;
        }
    }
    return false;
}

//删除数组中的元素
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
}
// 数组去重

Array.prototype.removeRepeat = function (arr) {
    for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
            if (arr[i] == arr[j]) {
                //如果第一个等于第二个，splice方法删除第二个
                arr.splice(j, 1);
                j--;
            }
        }
    }

    return arr;

}
/*********计算时间差*********************/
function GetDateDiff(startDate, endDate) {
	var startTime = new Date(startDate).getTime();
	var endTime = new Date(endDate).getTime();
	var dates =  Math.abs((startTime - endTime) / (1000 * 60 * 60 * 24));
	return dates + 1;
}