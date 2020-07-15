# -*- coding: utf-8 -*-
"""
Created on Sat Jul 11 13:07:14 2020

@author: DELL
"""


from __future__ import division, print_function, unicode_literals
import numpy as np 
import matplotlib.pyplot as plt

# so phong
sp = np.array([[3, 2, 2, 1, 3]]).T
# dt
dt = np.array([[ 2000, 800, 850,  550, 2000]]).T
#khu
khu = np.array([[ 200, 400, 200,  200, 100]]).T
#gia
y = np.array([[ 250.000, 300.000, 150.000,  78.000, 150.000]]).T
one = np.ones((sp.shape[0], 1))
spbar = np.concatenate((one, sp), axis = 1)

dtbar = np.concatenate((spbar, dt), axis = 1)

khubar = np.concatenate((dtbar, khu), axis = 1)
print(khubar)

from sklearn import datasets, linear_model

# fit the model by Linear Regression
regr = linear_model.LinearRegression(fit_intercept=False) # fit_intercept = False for calculating the bias
regr.fit(khubar, y)
w =regr.coef_
print( 'Solution found by scikit-learn  : ', w )
w0= w[:,0]
w1= w[:,1]#sophong
w2= w[:,2]#dientich
w3= w[:,3]#khu
# Compare two results

y=w0+w1*3+w2*2000+w3*200
print( 'Solution found by scikit-learn  : ', y )