import cv2
img =cv2.imread('orange.jpg',1)#0 hình trắng đen,1 hình có màu
print(img)
cv2.imshow('image',img)

cv2.waitKey(0)#tương tác với hình ảnh

cv2.destroyAllWindows()#huy bỏ nếu bấm phím bất kỳ