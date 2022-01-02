/**
 * objtyperejectagr.java
 * ISDBj
 */
package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEREJECTAGRS.
 * @version 1.0 final, 11-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtyperejectagr extends isdbobj
{
    // Поля таблиці TYPEREJECTAGRS
    public static final String COL_REJECT = "REJECT";

    /**
     * Конструктор.
     */
    public objtyperejectagr ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEREJECTAGR);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn (COL_ID);
        oSQLData.setColumn (COL_REJECT);
        oSQLData.setFrom (isdb.miscs.dclrs.TBL_TYPEREJECTAGR);
        oSQLData.setOrder (COL_REJECT);
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        return false;
        /*
                if (strKey.equals (objagreement.COL_TYPEREJECTAGR_ID)) // ссилочне поле?
                    return true;
                return super.isObligatory (strKey, oDBData);
        */
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @param oDBData поточні дані об'екта
     * @return ознака можливісті на створення нового запису
     */
    public boolean isCreateable (dbdata oDBData)
    {
        return false;
    }
}

