package com.google.android.gcm.demo.utils;

import com.google.android.gcm.demo.custqualifiers.Loggable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

/**
 * Created by VladyslavPrytula on 3/23/14.
 */
@Interceptor
@Loggable
public class LoggingInterceptor {
    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().toString(), ic.getMethod().getName());
        logger.severe(">>>" + ic.getTarget().toString() + " - " + ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            logger.severe("<<<" + ic.getTarget().toString() + " - " + ic.getMethod().getName());
            logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
        }
    }
}