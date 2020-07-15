# -*- coding: utf-8 -*-
"""
Created on Sun Jul 12 13:19:13 2020

@author: DELL
"""
import matplotlib.image as mpimg
import matplotlib.pyplot as plt
import numpy as np
from sklearn.cluster import KMeans



img = mpimg.imread('girl3.png')
plt.imshow(img)
imgplot = plt.imshow(img)

plt.axis('off')
plt.show()
#Biến đổi bức ảnh thành 1 ma trận mà mỗi hàng là 1 pixel với 3 giá trị màu
X = img.reshape((img.shape[0]*img.shape[1], img.shape[2]))
#k là cluster  chia buc anh thanh 3 nhom màu
for K in [3]:
    kmeans = KMeans(n_clusters=K).fit(X)
    label = kmeans.predict(X)

    img4 = np.zeros_like(X)
    # replace each pixel by its center
    for k in range(K):
        img4[label == k] = kmeans.cluster_centers_[k]
    # reshape and display output image
    img5 = img4.reshape((img.shape[0], img.shape[1], img.shape[2]))
  #  plt.imshow(img5, interpolation='nearest')
  #  plt.axis('off')
  #  plt.show()
    #img6 = img4.reshape((img.shape[0], img.shape[1], img.shape[2]))
    print(img.shape[0])
    
    plt.imshow(img5, interpolation='nearest')
    plt.axis('off')
    plt.show()



