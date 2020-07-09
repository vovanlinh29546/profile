# -*- coding: utf-8 -*-
"""
Created on Thu Jul  9 10:12:40 2020

@author: DELL
"""


"""
Vì Python là scripting language nên không thích hợp cho ML, Numpy giải quyết vấn đề trên bằng
cách xây dựng 1 thư viện viết bằng C nhưng có interface Python. Như vậy Numpy cộng hưởng 2 ưu
điểm của 2 ngôn ngữ: nhanh như C và đơn giản như Python. Điều này giúp ích rất nhiều cho cộng
đồng Machine Learning trên Python.
Mảng trong numpy gồm các phần tử có dùng kiểu giá trị, chỉ số không âm được bắt đầu từ 0, số
chiều được gọi là rank của mảng Numpy, và shape là một tuple các số nguyên đưa ra kích thước của
mảng theo mỗi chiều.
"""
import numpy as np
"""
import numpy as np

a = np.array([1, 2, 3]) # Tạo array 1 chiều
print(type(a)) # Prints "<class 'numpy.ndarray'>"
print(a.shape) # Prints "(3,)"
print(a[0], a[1], a[2]) # Prints "1 2 3"
a[0] = 5 # Thay đổi phần tử vị trí số 0
print(a) # Prints "[5, 2, 3]"
b = np.array([[1,2,3],[4,5,6]]) # Tạo array 2 chiều
print(b.shape) # Prints "(2, 3)"
print(b[0, 0], b[0, 1], b[1, 0]) # Prints "1 2 4"
#Ngoài ra có những cách khác để tạo array với giá trị mặc định
import numpy as np
a = np.zeros((2,2)) # Tạo array với tất cả các phần tử 0
print(a) # Prints "[[ 0. 0.]
# [ 0. 0.]]"
b = np.ones((1,2)) # Tạo array với các phần từ 1
print(b) # Prints "[[ 1. 1.]]"
c = np.full((2,2), 7) # Tạo array với các phần tử 7
print(c) # Prints "[[ 7. 7.]
# [ 7. 7.]]"

d = np.eye(2) # Tạo identity matrix kích thước 2*2
print(d) # Prints "[[ 1. 0.]
# [ 0. 1.]]"

e = np.random.random((2,2)) # Tạo array với các phần tử được tạo ngẫu nhiên
print(e) # Might print "[[ 0.91940167 0.08143941]
# [ 0.68744134 0.87236687]]"
"""

#Array indexing
'''
Tương tự như list, numpy array cũng có thể slice. Tuy nhiên vì numpy array có nhiều chiều, nên khi
dùng slice phải chỉ định rõ chiều nào.

import numpy as np
# Tạo array 2 chiều với kích thước (3, 4)
# [[ 1 2 3 4]
# [ 5 6 7 8]
# [ 9 10 11 12]]
a = np.array([[1,2,3,4], [5,6,7,8], [9,10,11,12]])
# Dùng slide để lấy ra subarray gồm 2 hàng đầu tiên (1 & 2) và 2 cột (2 & 3)
# Output là array kích thước 2*2
# [[2 3]
# [6 7]]
b = a[:2, 1:3]
print(a[0, 1]) # Prints "2"
a[0, 1] = 77 # Chỉnh sửa phần tử trong array
print(a[0, 1]) # Prints "77"
#Bên cạnh đó cũng có thể dùng các chỉ số với slice index. Tuy nhiên số chiều array sẽ giảm đi.
# Tạo array 2 chiều kích thước (3, 4)
# [[ 1 2 3 4]
# [ 5 6 7 8]
# [ 9 10 11 12]]
a = np.array([[1,2,3,4], [5,6,7,8], [9,10,11,12]])
row_r1 = a[1, :] # Lấy ra hàng thứ 2 trong a, output array 1 chiều
row_r2 = a[1:2, :] # Lấy ra hàng thứ 1&2 trong a, output array 2 chiều
print(row_r1, row_r1.shape) # Prints "[5 6 7 8] (4,)"
print(row_r2, row_r2.shape) # Prints "[[5 6 7 8]] (1, 4)"
#Thuộc tính shape cho mảng numpy trả về kích thước của mảng hang cột
'''
#Các phép tính trên array

'''
x = np.array([[1,2],[3,4]], dtype=np.float64)
y = np.array([[5,6],[7,8]], dtype=np.float64)
# Tính tổng
# [[ 6.0 8.0]
# [10.0 12.0]]
print(x + y)
print(np.add(x, y))
# Phép trừ
# [[-4.0 -4.0]
# [-4.0 -4.0]]
print(x - y)
print(np.subtract(x, y))
# Phép nhân element-wise
# [[ 5.0 12.0]
# [21.0 32.0]]
print(x * y)
print(np.multiply(x, y))
# Phép chia element-wise
# [[ 0.2 0.33333333]
# [ 0.42857143 0.5 ]]
print(x / y)
print(np.divide(x, y))
# Tính căn bậc hai
# [[ 1. 1.41421356]
# [ 1.73205081 2. ]]
print(np.sqrt(x))
#dùng để nhân element-wise chứ không phải nhân ma trận thông thường. Thay vào đó dùng np.dot
#để nhân ma trận.

x = np.array([[1,2],[3,4]])
# 1 2
# 3 4
y = np.array([[5,6],[7,8]])
#5 6
#7 8
v = np.array([9,10])
w = np.array([11, 12])
# Nhân ma trận, output số 219(1*11+10*12)
print(v.dot(w))
print(np.dot(v, w))

# Nhân ma trận; output ma trận 2*2
#khả thi khi số cột của ma trận bên trái bằng với số hàng của ma trận bên phải
# [[19 22]
# [43 50]]
#hướng dẫn nhân ma trận vs matraj
#1*5+2*7;3*5+4*7
#3*6+4*8;3*6+4*8

print(x.dot(y))
print(np.dot(x, y))


x = np.array([[1,2],[3,4]])
print(np.sum(x)) # Tính tổng tất cả phần tử trong array; prints "10"
print(np.sum(x, axis=0)) # Tính tổng phần tử mỗi cột; prints "[4 6]"
print(np.sum(x, axis=1)) # Tính tổng phần tử mỗi hàng; prints "[3 7]"
'''
#Broadcasting
'''
Broadcasting là một kĩ thuật cho phép numpy làm việc với các array có shape khác nhau khi thực
hiện các phép toán.
# Cộng vector v với mỗi hàng của ma trận x, kết quả lưu ở ma trận y.
x = np.array([[1,2,3], [4,5,6], [7,8,9], [10, 11, 12]])
v = np.array([1, 0, 1])
y = np.empty_like(x) # Tạo 1 array có chiều giống x
# Dùng loop để vector v với mỗi hàng của ma trận
for i in range(4):#i là số hàng của ma trân x
    y[i, :] = x[i, :] + v
# Kết quả của y
# [[ 2 2 4]
# [ 5 5 7]
# [ 8 8 10]
# [11 11 13]]
print(y)
'''


