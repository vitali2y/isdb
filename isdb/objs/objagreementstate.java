/**
 * objagreementstate.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці AGREEMENTSTATES.
 * @version 1.0 final, 17-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objagreementstate extends isdbobj
{
    // Поля таблиці AGREEMENTSTATES
    public static final String COL_STATE = "STATE";

    /**
     * Конструктор.
     */
    public objagreementstate ()
    {
        super (isdb.miscs.dclrs.TBL_AGREEMENTSTATE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,STATE");
        oSQLData.setFrom ("AGREEMENTSTATES");
        oSQLData.setOrder ("STATE");
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
        return true;
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

