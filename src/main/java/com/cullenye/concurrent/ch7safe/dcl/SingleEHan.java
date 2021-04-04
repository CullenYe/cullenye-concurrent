package com.cullenye.concurrent.ch7safe.dcl;

/**
 * 饿汉式
 */
public class SingleEHan {

     private SingleEHan(){}

     private static SingleEHan singleEHan = new SingleEHan();
}
