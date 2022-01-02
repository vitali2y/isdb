/**
 * outdata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;
import java.util.Enumeration;

import isdb.datas.*;

/**
 * Поточні вихідні дані об'екта.
 * @version 1.0 final, 26-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class outdata
{
    /** Поточні вихідні дані об'екта */
    private Hashtable hashOut;
    private javax.servlet.http.HttpServletResponse oThisResStream;

    /** Скритий параметр форми */
    private static final String HIDE_FIELD = "*HIDE_FIELD";
    private static final String DATA_JS = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "DATA_JS";

    /**
     * Конструктор.
     * @param oResStream вихідний потік
     */
    public outdata (javax.servlet.http.HttpServletResponse oResStream)
    {
        hashOut = new Hashtable ();
        oThisResStream = oResStream;
    }

    /**
     * Збереження значення поля об'екта.
     * @param oKey назва значення поля об'екта
     * @param oValue значення поля об'екта
     */
    public void setFld (Object oKey, Object oValue)
    {
        hashOut.put (oKey, oValue);
    }

    /**
     * Повернення значення поля об'екта.
     * @param oKey назва значення поля об'екта
     * @return значення поля об'екта
     * @see #getVal (Object)
     */
    public String getFld (Object oKey)
    {
        String strVal = (String) hashOut.get (oKey);
        if ((String.valueOf (strVal) == "null"))
            strVal = "";
        return strVal;
    }

    /**
     * Повернення значення(ь) скритого(их) поля(ів) форми об'екта.
     * @return значення(ь) скритого(их) поля(ів) форми об'екта
     */
    public String getHideFld ()
    {
        String strVal = (String) hashOut.get (HIDE_FIELD);
        if (String.valueOf (strVal) == "null")
            strVal = "";
        return strVal;
    }

    /**
     * Збереження значення(ь) скритого(их) поля(ів) форми об'екта.
     * @param oKey назва начення(ь) скритого(их) поля(ів) форми
     * @param oValue значення(ь) скритого(их) поля(ів) форми об'екта
     */
    public void setHideFld (Object oKey, Object oValue)
    {
        String strVal = getHideFld ();
        if (String.valueOf (strVal) == "null")
            strVal = "";
        if (oKey == null)
            strVal += oValue;
        else
            strVal += isdb.ifaces.htmli.formhidepar ((String) oKey, (String) oValue);
        hashOut.put (HIDE_FIELD, strVal);
    }

    /**
     * Збереження значення(ь) скритого(их) поля(ів) форми об'екта.
     * @param oValue значення(ь) скритого(их) поля(ів) форми об'екта
     * @see #setHideFld (oKey, oValue)
     */
    public void setHideFld (Object oValue)
    {
        setHideFld (null, oValue);
    }

    /**
     * Встановлення нових JS даних об'екта.
     * @param oJSData нові JavaScript дані
     * @see #getJSData ()
     */
    public void setJSData (jsdata oJSData)
    {
        hashOut.put (DATA_JS, oJSData);
    }

    /**
     * Повернення JS даних, які існують в вихідних даних об'екта.
     * @return JavaScript дані об'екта
     * @see #putJSData (jsdata)
     */
    public jsdata getJSData ()
    {
        return (jsdata) hashOut.get (DATA_JS);
    }

    /**
     * Повернення поточного вихідного потоку для відображення інформації.
     * @return вихідний потік
     */
    public javax.servlet.http.HttpServletResponse getOutStream ()
    {
        return oThisResStream;
    }
}

