#Bạn hãy viết chương trình để kiểm tra xem con mèo của bạn là già hay còn trẻ.
# Nếu tuổi của con mèo dưới 5 (age <5), thì hiển thị ra màn hình dòng chữ Your cat is young,
# ngược lại nếu tuổi của con mèo lớn hơn hoặc bằng 5 tuổi thì hiển thị ra Your cat is old.
# age = int(input("Moi ban nhap tuoi:"))
# if age < 5:
#     print("Your cat is young")
# else :
#     print("Your cat is old")

# # toán tử 3 ngôi
# age = int(input("Moi ban nhap tuoi:"))
# print("Your cat is young") if age < 5 else print("Your cat is old")
#
# #Cho số nguyên temperature chỉ nhiệt độ được nhập từ bàn phím,
# #bạn hãy viết chương trình in ra màn hình theo các yêu cầu như sau:
# temperature = int(input("MOi ban nhap nhiet do:"))
# if temperature>=100:
#     print("tay at home and enjoy a good movie")
# elif temperature>=92:
#     print("tay at home")
# elif temperature==75:
#     print("Go outside and enjoy the weather")
# elif temperature<0:
#     print("It's cool outside")
# else:
#     print("Let's go to school")


#Cho trước 3 số nguyên x, y, z được từ bàn phím.
# Bạn hãy viết chương trình hiển thị ra màn hình theo yêu cầu sau:

# x=int(input("Moi ban nhap x:"))
# y=int(input("Moi ban nhap y:"))
# z=int(input("Moi ban nhap z:"))
#
# if x%2==0:
#     if y>=20:
#         print("y is greater than or equal to 20")
#     else:
#         print("y is less than 20")
# else:
#     if z>=30:
#         print("z is greater than or equal to 23")
#     else:
#         print("z is less than 30")

# #Viết chương trình Python tính giá trị trung bình (avg) của ba biến a, b, c
# # nhập từ bàn phím (a, b, c là ba số tự nhiên) với điều kiện như sau:
# a=int(input("Moi ban nhap a:"))
# b=int(input("Moi ban nhap b:"))
# c=int(input("Moi ban nhap c:"))
# avg=(a+b+c)/3
# if  avg > a and avg>b:
#     print("The average value is greater than both a and b")
# elif avg>a and avg>c:
#     print("The average value is greater than both a and c")
# elif avg>b and avg>c:
#     print("The average value is greater than both b and c")
# elif avg >a:
#     print("he average value is greater than a")
# elif avg > b:
#     print("he average value is greater than b")
# elif avg > c:
#     print("he average value is greater than c")

#Cho số nguyên age chỉ tuổi của vật nuôi được nhập từ bàn phím,
# bạn hãy hiển thị ra màn hình theo yêu cầu sau:

age = int(input("Moi ban nhap tuoi:"))

if age <= 0:
    print("This can hardly be true")
elif age == 1:
    print("About 1 human year")
elif age == 2:
    print("About 2 human years")
elif age > 2:
    print("Over 5 human years")