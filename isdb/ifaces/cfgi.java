/**
 * cfgi.java
 * ISDBj
 */

package isdb.ifaces;

import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Hashtable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import isdb.datas.dbdata;
import isdb.datas.cookiedata;

/**
 * Интерфейс конфігураційної та інформаційної підтримки системи.
 * @version 1.0 final, 29-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class cfgi
{
    // Розміщення конфігураційних файлів
    /** Розміщення конфігураційної діректорії */
    private final static String strCfgDir = "/home/www/servlets/isdb/cfg/";
    /// private final static String strCfgDir = "/home/www/servlets/vit/cfg/";
    /** Розміщення конфігураційного файла ini */
    private final static String strIniFile = strCfgDir + "isdb.ini";
    /** Розміщення файл(а/ів) повідомлень titles_LOCALE.properties */
    private final static String strMsgFile = strCfgDir + "titles_ua.properties";

    /** Конфігураційни параметри */
    // private static Properties oIni = new Properties ();
    private static Hashtable oIni = new Hashtable ();
    /** Повідомлення користувачу */
    // private static Properties oMsg = new Properties ();
    private static Hashtable oMsg = new Hashtable ();

    /**
     * Конструктор.
     */
    public cfgi ()
    {
        setDefaultOptions ();
    }

    /**
     * Одержання значення конфігураційної отції.
     * @param strOptionName назва параметра
     * @return значення параметра, null - якщо такого немає
     */
    public static String getOption (String strOptionName)
    {
        return (String) oIni.get (strOptionName);
    }

    /**
     * Встановлення значення конфігураційної отції.
     * @param strOptionName назва параметра
     * @param strOptionVal значення параметра
     */
    public static void setOption (String strOptionName, String strOptionVal)
    {
        oIni.put (strOptionName, strOptionVal);
    }

    /**
     * Встановлення значеннь конфігураційних отцій по замовчанню.
     */
    public static void setDefaultOptions ()
    {
        int iPos = -1;
        String strLine = null;
        try
        {
            BufferedReader brIn = new BufferedReader (new InputStreamReader (new FileInputStream (strIniFile), "Cp1251"));
            while ((strLine = brIn.readLine ()) != null)
            {
                if (strLine.indexOf ('#') != 0) // комментар?
                {
                    iPos = strLine.indexOf ('=');
                    if (iPos > -1)
                        setOption (strLine.substring (0, iPos), strLine.substring (iPos + 1, strLine.length ()));
                }
            }
            // oIni.load (new FileInputStream (strIni [0][PLATFORM]));
        }
        catch (FileNotFoundException ex) { ex.printStackTrace (); }
        catch (IOException ex) { ex.printStackTrace (); }
        try
        {
            BufferedReader brIn = new BufferedReader (new InputStreamReader (new FileInputStream (strMsgFile), "Cp1251"));
            while ((strLine = brIn.readLine ()) != null)
            {
                if (strLine.indexOf ('#') != 0) // комментар?
                {
                    iPos = strLine.indexOf ('=');
                    if (iPos > -1)
                        oMsg.put (strLine.substring (0, iPos), strLine.substring (iPos + 1, strLine.length ()));
                }
            }
            // oMsg.load (new FileInputStream (strMsgFile));
        }
        catch (FileNotFoundException ex) { ex.printStackTrace (); }
        catch (IOException ex) { ex.printStackTrace (); }
    }

    /**
     * Встановлення значеннь конфігураційних отцій.
     * @param oDBData поточни дані об'екта
     * @param oCookieData cookie об'ект, який включає в себе значення конфігураційних отцій
     */
    public static void setOptions (dbdata oDBData, cookiedata oCookieData)
    {
        if (oDBData == null)
        {
            setOption (isdb.miscs.dclrs.OPT_FONT, oCookieData.getVal (isdb.miscs.dclrs.OPT_FONT));
            setOption (isdb.miscs.dclrs.OPT_COLOR_BG, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_BG));
            setOption (isdb.miscs.dclrs.OPT_COLOR_TITLE, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_TITLE));
            setOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_BGTITLE));
            setOption (isdb.miscs.dclrs.OPT_COLOR_FORM, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_FORM));
            setOption (isdb.miscs.dclrs.OPT_COLOR_TEXT, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_TEXT));
            setOption (isdb.miscs.dclrs.OPT_COLOR_HEAD, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_HEAD));
            setOption (isdb.miscs.dclrs.OPT_COLOR_LINK, oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_LINK));
            setOption (isdb.miscs.dclrs.OPT_PICTURE_BG, oCookieData.getVal (isdb.miscs.dclrs.OPT_PICTURE_BG));
        }
        else
        {
            String strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_FONT);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_FONT);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_FONT);;
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_FONT, strVal);
            setOption (isdb.miscs.dclrs.OPT_FONT, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_BG);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_BG);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_BG, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_BG, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_FORM);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_FORM);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_FORM, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_FORM, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_TEXT);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_TEXT);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_TEXT, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_TEXT, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_HEAD);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_HEAD);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_HEAD, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_HEAD, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_LINK);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_LINK);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_LINK);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_LINK, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_LINK, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_PICTURE_BG);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_PICTURE_BG);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_PICTURE_BG);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_PICTURE_BG, strVal);
            setOption (isdb.miscs.dclrs.OPT_PICTURE_BG, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_TITLE);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_TITLE);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_TITLE);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_TITLE, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_TITLE, strVal);

            strVal = oDBData.getVal (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
            if (String.valueOf (strVal) == "null")           // користувач змінив параметр?
            {
                strVal = oCookieData.getVal (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
                if (String.valueOf (strVal) == "null")           // цей cookie був збережен у кліента?
                    strVal = getOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
            }
            else
                oCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, strVal);
            setOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, strVal);
        }
    }

    /**
     * Встановлення значеннь конфігураційних отцій.
     * @param oCookieData cookie об'ект, який включає в себе значення конфігураційних отцій
     * @see #setOptions (dbdata, cookiedata)
     */
    public static void setOptions (cookiedata oCookieData)
    {
        setOptions (null, oCookieData);
    }

    /**
     * Одержання значення отції повідомлення.
     * @param strMsgName назва параметра
     * @return значення параметра,
     * null - якщо такого немає
     */
    public static String getMessage (String strMsgName)
    {
        ///
        // Exception e1 = new Exception ("getMessage : strMsgName=" + strMsgName + ", oMsg.get (strMsgName)="+(String) oMsg.get (strMsgName));
        // e1.printStackTrace ();
        ///

        return (String) oMsg.get (strMsgName);
    }

    /**
     * Одержання назви версії.
     * @return назва версії
     */
    public static String getVersion ()
    {
        return isdb.miscs.dclrs.ISDB_VERSION +
               htmli.crlf () +
               "від " + isdb.miscs.dclrs.ISDB_DATE;
    }

    /**
     * Одержання назви філії.
     * @return назва філії
     */
    public static String getAffiliate ()
    {
        return "Філія: " + getOption (isdb.miscs.dclrs.OPT_SITE);
    }

    /**
     * Одержання типу станції філії.
     * @return типу станції філії
     */
    public static String getSwitch ()
    {
        return "Тип станції: " + getOption (isdb.miscs.dclrs.OPT_SWITCH);
    }

    /**
     * Одержання використовуемого кодування Java.
     * @return використовуеме кодування
     */
    public static String getJavaCharSet ()
    {
        String strVal = getOption (isdb.miscs.dclrs.OPT_JAVACHARSET);
        if (String.valueOf (strVal) == "null")
            strVal = "Cp1251";
        return strVal;
    }
}

