package com.jaishna.wrapperframework.utils;

import com.jaishna.wrapperframework.service.BillingService;
import com.portal.pcm.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.net.*;

public class ContextUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextUtils.class);

    public static PortalContext createContext() {
        PortalContext ctx = null;
        try {

            ResourceUtils.getFile("classpath:Infranet.properties");

            ClassLoader cl = ClassLoader.getSystemClassLoader();

            URL[] urls = ((URLClassLoader)cl).getURLs();

            for(URL url: urls){
                System.out.println(url.getFile());
            }

            LOGGER.info("---------------------------------------------------------------");
            LOGGER.info("Creating PCM context using configuration in Infranet.properties");

            ctx = new PortalContext();
            ctx.connect();

            LOGGER.info("Context successfully created.");
            // print out some info about the connection
            LOGGER.info("current DB: " + ctx.getCurrentDB());
            LOGGER.info("user ID:    " + ctx.getUserID());

            // close the connection
           // ctx.close(true);
           // LOGGER.info("PCM connection closed.");
        } catch (EBufException ebufex) {
            LOGGER.info("Your connection to the server failed.");
            LOGGER.info(" * Do you have a correct Infranet.properties file in the classpath?");
            LOGGER.info(" * Is the Infranet server CM up?");
            LOGGER.info("Here's the error:");
            LOGGER.info("Here's the error:" +ebufex);
        } catch(Exception e) {
            LOGGER.info("Here's the error:" + e);
        }
        return ctx;
    }
}
