var myThuVien ={};

myThuVien.show = function(message)
{
    alert(message);
}
myThuVien.add=function(a,b){
    a = document.forms[0].a.value;
    b = document.forms[0].b.value;
    a=Number(a);
    b=Number(b);
    return myThuVien.show(a+b);
}

myThuVien.minus=function(a,b){
    a = document.forms[0].a.value;
    b = document.forms[0].b.value;
    a=Number(a);
    b=Number(b);
    return myThuVien.show(a-b);
}

myThuVien.mul=function(a,b){
    a = document.forms[0].a.value;
    b = document.forms[0].b.value;
    a=Number(a);
    b=Number(b);
    return myThuVien.show(a*b);
}

myThuVien.div=function(a,b){
    a = document.forms[0].a.value;
    b = document.forms[0].b.value;
    a=Number(a);
    b=Number(b);
    return myThuVien.show(a/b);
}
myThuVien.max=function(a,b,c){
    a=Number(a);
    b=Number(b);
    c=Number(c);
    var max=a;
    if(max<b)max=b;
    if(max<c)max=c;
    return max;
}
myThuVien.max1=function(a,b,c){
    a = document.forms[0].a.value;
    b = document.forms[0].b.value; 
    c = document.forms[0].c.value; 
    a=Number(a);
    b=Number(b);
    c=Number(c);
    return myThuVien.show(Math.max(a,b,c));
}
