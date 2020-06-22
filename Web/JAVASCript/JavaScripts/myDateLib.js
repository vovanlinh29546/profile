// JavaScript Document
var myDate = new Object();

var day = new Date();
var vDay = day.getDate();
var vMonth = day.getMonth() + 1;
var vYear = day.getFullYear();

myDate.today = vDay + "/" + vMonth + "/" + vYear;

//return a String
myDate.convertToVn = function(oString){
	var oDate = new Date(oString);
	var vDay = oDate.getDate();
	var vMonth = oDate.getMonth() + 1;
	var vYear = oDate.getFullYear();
	return (vDay + "/" + vMonth + "/" + vYear);
}
