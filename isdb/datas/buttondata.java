/**
 * buttondata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

import isdb.datas.*;

/**
 * Кнопка форми об'екта.
 * @version 1.0 final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class buttondata
{
    private static String PAR_URL = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "URL";
    private static String PAR_NAME = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "NAME";
    private static String PAR_WND = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "WND";
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = false;

    /** Признак відкривання нового вікна броузера */
    private boolean bOpenWnd;
    /** Кнопки управління даними */
    private Hashtable hashButton;
    /** Додаткові параметри для розкриття вікна */
    private String strParam;

    /**
     * Конструктор.
     * <P><B>Увага!</B><P>URL кнопки для переходу по замовчанню - isdbform (якщо не був використован метод setUrl)!
     */
    public buttondata ()
    {
        hashButton = new Hashtable ();
        setUrl ("isdbform");   // по замовчанню: URL кнопки
        bOpenWnd = false;      // по замовчанню: використовувати поточне вікно
    }

    /**
     * Конструктор.
     * <P><B>Увага!</B><P>По замовчанню використовуються: об'ект - поточний та відділ - поточний!
     * @param strRegime режим створення кнопки
     * @param oDBData поточні робочі дані об'екта
     * @see #buttondata ()
     */
    public buttondata (dbdata oDBData, String strRegime)
    {
        this ();
        if (String.valueOf (strRegime) == "null")       // по замовчанню?
        {
            strRegime = isdb.miscs.dclrs.REGIME_INSERT;
            setName (isdb.miscs.dclrs.TITLE_REG_NEW_RECORD);
        }
        /*
        if (strRegime.equals (isdb.miscs.dclrs.REGIME_PRINT))
                setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
        if (strRegime.equals (isdb.miscs.dclrs.REGIME_UPDATE))
                setName (isdb.miscs.dclrs.TITLE_REG_UPDATE);
        if (strRegime.equals (isdb.miscs.dclrs.REGIME_DELETE))
                setName (isdb.miscs.dclrs.TITLE_REG_DELETE);
        */
        setPar (isdb.miscs.dclrs.PAR_REGIME, strRegime);
        setPar (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
        setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
    }

    /**
     * Конструктор.
     * <P><B>Увага!</B><P>По замовчанню використовуються: режим - PAR_INSERT, об'ект - поточний та відділ - поточний!
     * @param oDBData поточні робочі дані об'екта
     * @see #buttondata (dbdata, String)
     */
    public buttondata (dbdata oDBData)
    {
        this (oDBData, null);
    }

    /**
     * Встановлення URL для кнопки.
     * @param strURLButton URL адреса для кнопки
     * @param bOpenWnd признак відкриття нового вікна (true - нове вікно)
     * @param strParam додатковий параметр відкриваемого вікна
     */
    public void setUrl (String strURLButton, boolean bOpenWnd, String strParam)
    {
        this.bOpenWnd = bOpenWnd;
        this.strParam = strParam;
        hashButton.put (PAR_URL, strURLButton);
    }

    /**
     * Встановлення URL для кнопки.
     * @param strURLButton URL адреса для кнопки
     * @param bOpenWnd признак відкриття нового вікна (true - нове вікно)
     * @see #setUrl (String, boolean, String)
     */
    public void setUrl (String strURLButton, boolean bOpenWnd)
    {
        setUrl (strURLButton, bOpenWnd, null);
    }

    /**
     * Встановлення URL для кнопки.
     * @param strURLButton URL адреса для кнопки
     * @param strParam додатковий параметр відкриваемого вікна
     * @see #setUrl (String, boolean, String)
     */
    public void setUrl (String strURLButton, String strParam)
    {
        setUrl (strURLButton, false, strParam);
    }

    /**
     * Встановлення URL для кнопки.
     * @param strURLButton URL адреса для кнопки
     * @see #setUrl (String, boolean, String)
     */
    public void setUrl (String strURLButton)
    {
        setUrl (strURLButton, false, null);
    }

    /**
     * Встановлення назви для кнопки.
     * @param strNameButton назва для кнопки
     */
    public void setName (String strNameButton)
    {
        hashButton.put (PAR_NAME, strNameButton);
    }

    /**
     * Встановлення параметра кнопки.
     * @param strParName назва параметра кнопки
     * @param strParVal значення параметра кнопки
     */
    public void setPar (String strParName, String strParVal)
    {
        if (String.valueOf (strParVal) != "null")
            hashButton.put (strParName, strParVal);
    }

    /**
     * Встановлення параметрів кнопки.
     * @param oDBData поточні дані об'екта
     * @see #setPar (String, String)
     */
    public void setPar (dbdata oDBData)
    {
        String strKey = null;
        String strVal = null;
        Enumeration enumPars = oDBData.getKeys ();
        while (enumPars.hasMoreElements ())
        {
            strKey = (String) enumPars.nextElement ();
            strVal = oDBData.getVal (strKey);
            if (strKey.equals (isdb.miscs.dclrs.PAR_FAILAPPL))
                setUrl (strVal);
            else
            {
                if (!strKey.startsWith (isdb.miscs.dclrs.SPECIAL_SERVICE_PREFIX))
                    setPar (strKey, strVal);
            }
            if (MODE_DEBUG)                // тестувал. режим?
            {
                Exception e878 = new Exception ("setPar: " + strKey + "=" + strVal);
                e878.printStackTrace ();
            }
        }
    }

    /**
     * Повернення кнопки управління форми.
     * @param oOutData поточні вихідні дані об'екта
     * @return сформована кнопка управління (в форматі форми HTTP)
     */
    public String getButton (outdata oOutData)
    {
        String strPar = null;
        String strPars = "";
        String strUrl = "javascript:void(0)";
        String strJSCall = null;

        javax.servlet.http.HttpServletResponse oResStream = oOutData.getOutStream ();
        Enumeration enumPars = hashButton.keys ();
        while (enumPars.hasMoreElements ())
        {
            strPar = (String) enumPars.nextElement ();
            if (!(strPar.equals (PAR_URL) ||
                    strPar.equals (PAR_NAME)))
            {
                if (strPar.startsWith ("&"))       // додатк. пврвметри?
                    strPars += (String) hashButton.get (strPar);
                else
                    strPars += isdb.ifaces.htmli.formhidepar (strPar, (String) hashButton.get (strPar));
            }
        }
        jsdata oJSData = new jsdata ();
        if (this.bOpenWnd)                           // відкривати нове вікно броузера?
        {
            oJSData.setJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + isdb.miscs.dclrs.REGIME_HELP, isdb.ifaces.jsi.JS_FUNC_WND_OPEN,
                           "'" + oResStream.encodeUrl ((String) hashButton.get (PAR_URL)) + "'", this.strParam);
            strJSCall = oJSData.getJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + isdb.miscs.dclrs.REGIME_HELP, isdb.ifaces.jsi.JS_ACTION_ONCLICK);
        }
        else
            strUrl = oResStream.encodeUrl ((String) hashButton.get (PAR_URL));
        return isdb.ifaces.htmli.form (strUrl, strPars,
                                       (String) hashButton.get (PAR_NAME), strJSCall);
    }

    /**
     * Повернення значеннь кнопки.
     * @return значення кнопки
     */
    public String toString ()
    {
        return "\nbuttondata: " + hashButton.toString ();
    }
}

