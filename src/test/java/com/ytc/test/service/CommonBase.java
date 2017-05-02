package com.ytc.test.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class CommonBase{
      private static final Logger logger = Logger.getLogger(CommonBase.class);

    public static ClassPathXmlApplicationContext createContext(String rand, @SuppressWarnings("rawtypes") Class callersClass, String originalPropFileName, String mainContextFile, String... additionalContextFiles) throws Exception {

        // create dynamic prop file
        String propFileName = originalPropFileName.replaceFirst(".properties", rand + ".properties");
        URL propUrl = callersClass.getClassLoader().getResource("config/" + originalPropFileName);
        File existingPropFile = new File(propUrl.toURI());
        Properties prop = new Properties();
        prop.load(new FileInputStream(existingPropFile));
        prop.setProperty("db.url", "jdbc:hsqldb:file:build/data/Test" + rand + "DB;hsqldb.write_delay=false;shutdown=true");
        prop.store(new FileOutputStream(new File(existingPropFile.getParentFile(), propFileName)), null);

        // create dynamic xml file
        String newXmlFile = mainContextFile.replaceFirst(".xml", rand + ".xml");
        URL xmlUrl = callersClass.getClassLoader().getResource(mainContextFile);
        File existingXmlFile = new File(xmlUrl.toURI());
        String appContextString = FileUtils.readFileToString(existingXmlFile);
        String newAppContextString = appContextString.replaceAll(originalPropFileName, propFileName);
        FileUtils.writeStringToFile(new File(existingXmlFile.getParentFile(), newXmlFile), newAppContextString);

        // load from new xml file
        String newContextFiles = newXmlFile;
        if (additionalContextFiles != null) {
            for (String add : additionalContextFiles) {
                if (StringUtils.isNotBlank(add)) {
                    newContextFiles = "," + add;
                }
            }
        }
        logger.info("Using app context from " + newContextFiles + " ");
        logger.info("content " + newAppContextString);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        ConfigurableEnvironment configurableEnvironment = context.getEnvironment();
        configurableEnvironment.setActiveProfiles("listeners");
        context.setEnvironment(configurableEnvironment);
        context.setConfigLocation(newContextFiles);
        context.refresh();
        return context;
    }

    public synchronized static String generateRandomAscii(int length) {
        String random = null;
        try {
            random = RandomStringUtils.randomAlphabetic(length);
            return random;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return random;
    }

}