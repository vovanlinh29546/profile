# -*- coding: utf-8 -*-
"""
Created on Wed Jul  8 11:15:47 2020

@author: DELL
"""


#số
'''
x = 3
print(type(x)) # Prints "<class 'int'>"
print(x) # Prints "3"
print(x + 1) # Cộng; prints "4"
print(x - 1) # Trừ; prints "2"
print(x * 2) # Nhân; prints "6"
print(x ** 2) # Lũy thừa; prints "9"
x += 1
print(x) # Prints "4"
x *= 2
print(x) # Prints "8"
y = 2.5
print(type(y)) # Prints "<class 'float'>"
print(y, y + 1, y * 2, y ** 2) # Prints "2.5 3.5 5.0 6.25"

'''

#phép tính logic
'''
t = True
f = False
print(type(t)) # Prints "<class 'bool'>"
print(t and f) # AND; prints "False"
print(t or f) # OR; prints "True"
print(not t) # NOT; prints "False"
print(t != f) # XOR; prints "True"
'''
#chuỗi
'''
"Python có hỗ trợ dạng chuỗi"
hello = 'hello' # gán giá trị chuỗi cho biến, chuỗi đặt trong 2 dấu '
world = "world" # chuỗi cũng có thể đặt trong dấu ".
print(hello) # Prints "hello"
print(len(hello)) # Độ dài chuỗi; prints "5"
hw = hello + ' ' + world # Nối chuỗi bằng dấu +
print(hw) # prints "hello world"
hw12 = '%s %s %d' % (hello, world, 12) # Cách format chuỗi
print(hw12) # prints "hello world 12"
"Kiểu string có rất nhiều method để xử lý chuỗi"
s = "hello"
print(s.capitalize()) # Viết hoa chữ cái đầu; prints "Hello"
print(s.upper()) # Viết hoa tất cả các chữ; prints "HELLO"
print(s.replace('l', '(ell)')) # Thay thế chuỗi; prints "he(ell)(ell)o"
print(' world '.strip()) # Bỏ đi khoảng trắng ở đầu và cuối chuỗi; prints "world"
lst = ["Welcome", "to", "Codelearn.io!"]
print(" ".join(lst))
kq se là: Welcome to Codelearn.io!
'''
#list
'''
List trong Python giống như mảng (array) nhưng không cố định kích thước và có thể chứa nhiều
kiểu dữ liệu khác nhau trong 1 list.
xs = [3, 1, 2] # Tạo 1 list
print(xs, xs[2]) # Prints "[3, 1, 2] 2"
print(xs[-1]) # Chỉ số âm là đếm phần tử từ cuối list lên; prints "2"
xs[2] = 'foo' # List có thể chứa nhiều kiểu phần tử khác nhau
print(xs) # Prints "[3, 1, 'foo']"
xs.append('bar') # Thêm phần tử vào cuối list
print(xs) # Prints "[3, 1, 'foo', 'bar']"
x = xs.pop() # Bỏ đi phần tử cuối cùng khỏi list và trả về phần tử đấy
print(x, xs) # Prints "bar [3, 1, 'foo']"
Slicing Thay vì lấy từng phần tử một trong list thì python hỗ trợ truy xuất nhiều phần tử 1 lúc gọi là
slicing.
nums = list(range(5)) # range sinh ta 1 list các phần tử
print(nums) # Prints "[0, 1, 2, 3, 4]"
print(nums[2:4]) # Lấy phần tử thứ 2->4, python chỉ số mảng từ 0;
print(nums[2:]) # Lấy từ phần tử thứ 2 đến hết; prints "[2, 3, 4]"
print(nums[:2]) # Lấy từ đầu đến phần tử thứ 2; prints "[0, 1]"
print(nums[:]) # Lấy tất cả phần tử trong list; prints "[0, 1, 2, 3, 4]"
print(nums[:-1]) # Lấy từ phần tử đầu đến phần tử gần cuối trong list; prints "[0, 1, 2, 3]"
nums[2:4] = [8, 9] # Gán giá trị mới cho phần tử trong mảng từ vị trí 2->4
print(nums) # Prints "[0, 1, 8, 9, 4]"
#Loops Để duyệt và in ra các phần tử trong list
animals = ['cat', 'dog', 'monkey']
for idx, animal1 in enumerate(animals):
    print('#%d: %s' % (idx + 1, animal1))
# Prints "#1: cat", "#2: dog", "#3: monkey", in mỗi thành phần trong list 1 dòng

'''
#Dictionaries
'''
Dictionaries lưu thông tin dưới dạng key, value.
d = {'cat': 'cute', 'dog': 'furry'} # Tạo dictionary, các phần tử dạng key:value
print(d['cat']) # Lấy ra value của key 'cat' trong dictionary; prints "cute"
print('cat' in d) # Kiểm tra key có trong dictionary không; prints "True"
d['fish'] = 'wet' # Gán key, value, d[key] = value
print(d['fish']) # Prints "wet"
print(d)# in ra {'cat': 'cute', 'dog': 'furry', 'fish': 'wet'}
# print(d['monkey']) # Lỗi vì key 'monkey' không trong dictionary;
del d['fish'] # Xóa phần tử key:value từ dictionary
#print(d['fish'])# Lỗi vì key 'fish' đã bị xóa trong dictionary;

#Loop Duyệt qua các phần tử trong dictionary

d = {'person': 2, 'cat': 4, 'spider': 8}
for animal, legs in d.items():#items() có tác dụng tách các phần tử trong dictionary vd:('person', 2),('cat', 4)
    print('A %s has %d legs' % (animal, legs))#trong do %s là string va %d la kieu số gán cho animal va legs
# Prints "A person has 2 legs", "A cat has 4 legs", "A spider has 8 legs"
'''
#function
# Hàm có input là 1 số và output xem số đấy âm, dương hay số 0
'''
def sign(x):
    if x > 0:
        return 'positive'
    elif x < 0:
       return 'negative'
    else:
        return 'zero'
for x in [-1, 0, 1]:
    print(sign(x))

# Prints "negative", "zero", "positive"

'''
