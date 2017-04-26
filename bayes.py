from __future__ import with_statement 
import math
import numpy
import csv
import os
import sys

x = 11
M = 4 #not too large, or over fitting since there are not too much data
MP1 = M + 1
N = range(1,11)
alpha = 0.005
beta = 11.1
# price_real = 0

def getCsvfile(name):
    with open(name,'rt', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        column = [row[5] for row in reader]

#         global price_real #the latest real price
#         price_real = round(float(column[0]),2)
        tmp = column[0:10] #10 numbers
        i = 9
        data = []
        while i >= 0:
            num = round(float(tmp[i]),2)
            data.append(num)
            i-=1
    return data   
    
# function: PhiX
def getPhiX(x):
    PhiX = []  
    i = 0 
    while i < MP1: # 0,...,M
        PhiX.append(math.pow(x,i)) # x^0,x^1,...,x^M
        i += 1
    PhiX = numpy.array(PhiX)
    return PhiX

# function: S^(-1)
def inverseS():
    tmp = numpy.zeros((MP1,MP1)) 
    n = 0
    for n in range (0,len(N)):
        PhiXn =getPhiX(N[n])  
        PhiXn.shape=(MP1,1) 
        PhiXn_T = numpy.transpose(PhiXn) 
        tmp += numpy.dot(PhiXn,PhiXn_T)
    beta_Sig = beta*numpy.matrix(tmp) #multiplying beta
    
    I = numpy.eye(MP1,MP1)
    alpha_I = alpha*numpy.matrix(I) #multiplying alpha
    
    Inverse_S = alpha_I + beta_Sig
    return Inverse_S 

#function: m(x)
def getmx(x, Data):
    PhiX = getPhiX(x)
    PhiX.shape= (MP1 ,1)
    PhiX_T = numpy.transpose(PhiX)
    
    Inverse_S = inverseS()
    S =  numpy.linalg.inv(Inverse_S) 
      
    tmp = numpy.dot(PhiX_T,S)
    mult1 = beta*numpy.matrix(tmp)
    
    mult2 = numpy.zeros((MP1,1))
    n = 0
    for n in range (0,len(N)):
        phiXn = getPhiX(N[n])
        phiXn.shape= (MP1 ,1)
        tmp2 = phiXn*Data[n]
        mult2 += tmp2

    mx = numpy.dot(mult1,mult2)
    return mx

#function: s(x)^2
def gets2(x):
    PhiX = getPhiX(x)
    PhiX.shape = (MP1,1)
    PhiX_T = numpy.transpose(PhiX)
    
    Inverse_S = inverseS()    
    S =  numpy.linalg.inv(Inverse_S)    
    tmp = numpy.dot(PhiX_T,S)
    s2 = numpy.dot(tmp,PhiX)
    s2 += (1/beta)
    return s2

def main():

    filename = sys.argv[1]
    Data = getCsvfile(filename)
    price_predict = getmx(x,Data)
    print("1111111")
    print("222222")
    print(round(float(price_predict),2))   

main()




