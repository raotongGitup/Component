package com.example.router_compiler.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * @author: raotong
 * @data：2020/12/21 17:38
 * @Description :
 */
public class Log {
    private Messager messager;

    private Log(Messager messager) {
        this.messager = messager;
    }

    public static Log newLog(Messager messager) {
        return new Log(messager);

    }

    public void i(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }


}
