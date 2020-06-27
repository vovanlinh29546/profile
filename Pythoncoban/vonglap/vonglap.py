#Vòng lặp while được dùng để lặp lại một hành động cho tới khi điều kiện lặp không còn thỏa mãn nữa

#vòng lặp for được dùng để lặp qua một tập hợp cho trước, vòng lặp for thường được sử dụng với hàm range().
# for i in range(1, 5):# sẽ trả về một tập hợp chứa các số từ 1 tới 4.
#     print(i)
#Cho số nguyên dương n được nhập từ bàn phím,
# bạn hãy viết chương trình hiển thị ra màn hình tổng các số từ 1 tới n.
#for
# n = int(input("Moi ban nhap n"))
# answer = 0
# for i in range(1, n + 1):
#     answer += i
# print(answer)

#while
# n = int(input("Moi ban nhap n"))
# i = 1
# answer = 0
# while i <= n:
#     answer += i
#     i += 1
# print(answer)

#Cho 2 số nguyên a và b được nhập từ bàn phím,
# bạn hãy viết chương trình hiển thị ra tổng các số lẻ từ a tới b.
#dk la a phai be hon b
# a = int(input("Moi ban nhap a"))
# b = int(input("Moi ban nhap b"))
# answer = 0
# #while
# # while a<=b:
# #     if a%2 != 0:
# #         answer += a
# #     a += 1
# # print("Moi ban nhap b",answer)
#
# #for
# for i in range(a,b+1):# i nằm trong khoảng a đến b nên sẽ là b +1 vì i có thể =b
#     if i %2 !=0:
#         answer+=i
# print("Moi ban nhap b",answer)

#Cho chuỗi s được nhập từ bàn phím,
# bạn hãy viết chương trình hiển thị ra màn hình các kí tự khác kí tự 'y' trong chuỗi s.
# break dung de thoat vong lap
# continue các câu lệnh dưới contiue sẽ ko thực thi
# kitu = input("Moi ban nhap chuoi")
# for i in kitu:
#     if i == 'y':
#         continue
#     print("Current character:", i)

#Cho số nguyên a được nhập từ bàn phím,
# bãn hãy viết chương trình hiển thị ra màn tích của a với các số từ 1 đến 5.
#a = int(input("Moi ban nhap a"))
# for
# for i in range(1,6):
#     print(a,"*",i,"=",a*i)
#wile
# i=1
# while i<6:
#     print(a, "*", i, "=", a * i)
#     i+=1

#Cho hai số nguyên a và b được nhâp từ bàn phím,
# bạn hãy viết chương trình đếm số các số chẵn và số các số lẻ trong khoảng từ a tới b.
# Sau đó hiển thị ra màn hình thông tin sau:

# a = int(input("Moi ban nhap a"))
# b = int(input("Moi ban nhap b"))
# sochang = 0
# sole = 0
#
# # for
# # for i in  range(a,b+1):
# #     if i%2 ==0:
# #         sochang+=1
# #     else:
# #         sole += 1
# # print("so chang:",sochang)
# # print("so le:",sole)


#round(number, ndigits)Trong đó number là số cần làm tròn, ndigits là số chữ số sau dầu phẩy cần làm tròn.
#Cho số nguyên n được nhập vào từ bàn phím,Lưu ý: chỉ hiển thị 2 số thập phân sau phẩy.
# bạ nhãy viết chương trình hiển thị ra tổng của dãy số 1/2 + 2/3 + ... + n/n+1.

# a = int(input("Moi ban nhap a"))
# tong=0
# for i in range(a,a+1):
#     tong += i / (i + 1)
#
# print("tong:",round(tong,2))



