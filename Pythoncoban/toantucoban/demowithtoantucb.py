#democb
# age=input("Moi ban nhap tuoi:")#nhap tuoi tu ban phim
# print("tuoi:",age)

# #demo2input(a mu b)
# a=int(input("moi ban so a:"))
# b=int(input("moi ban so b:"))
# print("a mu b =",a**b)

# #so sanh 2 so x va y nhap tu ban phim,
# #code không dc thụt lề nếu ko sẽ bị lỗi
# a=int(input("nhap so x:"))
# b=int(input("nhap so y:"))
# print("x > y:",a>b)
# print("x > y:",a<b)
# print("x > y:",a==b)


#với hai số tự nhiên a và b được nhập từ bàn phím.
# Sau đó, in ra màn hình các giá trị của Total trên từng dòng như sau:
# a=int(input("moi ban nhap so a:"))
# b=int(input("moi ban nhap so b:"))
# a += b
# print("a += b",a)
# a -= b
# print("a -= b",a)
# a *= b
# print("a *= b",a)
# a /= b
# print("a /= b",a)


#Viết chương trình nhập từ bàn phím xâu x và kiểm tra xem trong xâu x có chứa ký tự 'H' hay không,
# nếu có thì hiển thị ra màn hình True, ngược lại hiển thị False.
# x = input("Moi ban nhap x:")
#
# print("co H trong:<",x,">",'H' in x)


#Viết chương trình nhập từ bàn phím 2 số nguyên a và b.
# Sau đó kiểm tra xem số a và b có cùng giá trị hay không,
# nếu có thì hiển thị ra màn hình True, ngược lại hiển thị False.

# a=int(input("nhap so a:"))
# b=int(input("nhap so b:"))
# #su dung is hoặc is not (toán tử định danh) neu is đúng thì kq la true và ngược lại
# print("a giong b:",a is b)
# print("a khac b:",a is not b)

#Cho 4 số nguyên x, y, z và t được nhập từ bàn phím.
# Bạn hãy viết chương trình để kiểm tra 4 giá trị này có thoả mãn điều kiện x > y và z < t hay không.
# In ra màn hình "Result evaluation is True" nếu 4 số thoả mãn điều kiện;
# nếu không, in ra "Result evaluation is False".
x=int(input("moi ban nhap x:"))
y=int(input("moi ban nhap y:"))
z=int(input("moi ban nhap z:"))
t=int(input("moi ban nhap t:"))
print("Result evaluation is:",(x>y)and(z<t))#sử dụng toán tử logic