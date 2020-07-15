# -*- coding: utf-8 -*-
"""
Created on Fri Jul 10 15:39:08 2020

@author: DELL
"""


from __future__ import division, print_function, unicode_literals
import numpy as np 
import matplotlib.pyplot as plt
import pandas as pd

# dientich (m2)
X = np.array([[30, 32.4138, 34.8276, 37.2414, 39.6552]]).T
# Tien (vnd)
y = np.array([[ 448.524, 509.248, 535.104,  551.432, 623.418]]).T
one = np.ones((X.shape[0], 1))

Xbar = np.concatenate((one, X), axis = 1)
A = np.dot(Xbar.T, Xbar)

b = np.dot(Xbar.T, y)

w = np.dot(np.linalg.pinv(A), b)
print('w = ', w)
# Preparing the fitting line 
w_0 = w[0][0]
w_1 = w[1][0]

x0 = np.linspace(25, 45, 5)
y0 = w_0 + w_1*x0
# Drawing the fitting line 
plt.plot(X.T, y.T, 'ro')     # data 
plt.plot(x0, y0)               # the fitting line    
plt.axis([25, 50, 440, 680])
plt.xlabel('DIEN TICh(m2)')
plt.ylabel('Gia (vnd)')
plt.show()
y1 = w_1*50 + w_0


print( u'Predict weight of person with height 155 cm: %.2f (vnd)'  %(y1) )

from sklearn import datasets, linear_model

# fit the model by Linear Regression
regr = linear_model.LinearRegression(fit_intercept=False) # fit_intercept = False for calculating the bias
regr.fit(Xbar, y)

# Compare two results
print( 'Solution found by scikit-learn  : ', regr.coef_ )
print( 'Solution found by (5): ', w.T)