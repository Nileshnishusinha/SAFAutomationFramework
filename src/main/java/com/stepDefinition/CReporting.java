package com.stepDefinition;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CReporting {

    public static ExtentReports extent;
    public static  ExtentProperties extentProperties;
    public static ExtentTest logger;
    public static ExtentHtmlReporter htmlReporter;

    public static String strReportPath;
    public static String strFolderReportPath;
    public static String strfolderName;

    public boolean flgPassScreensshot = true;
    public boolean flgFailScreensshot = true;
    public boolean flgErrorScreensshot = true;
    public boolean flgInfoScreensshot = true;

    public static String dTime()
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return timeStamp;
    }

    public static void createReportFolder(String strFolderReportPath)throws Exception
    {
        strFolderReportPath = System.getProperty("user.dir")+"\\..\\TestOutput\\Reports\\"+strfolderName;
        strReportPath =strFolderReportPath+"\\Executionreport.html";
        File dir = new File(strFolderReportPath);
        dir.mkdir();
    }

//    public static void endCucumberReport()
//    {
//        try{
//            ExtentReports.
//        }
//    }

//    public void reportEvent(String strStatus, String strMessage)
//    {
//        switch (strStatus.toLowerCase())
//        {
//            case "pass":
//                Reporter.addStepLog(strMessage);
//                if (flgPassScreensshot)
//                {
//                    attachScreenshot();
//                }
//                break;
//        }
//    }

}
