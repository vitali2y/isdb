/**
 * objphonestate.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PHONESTATES.
 * @version 1.0 final, 4-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objphonestate extends isdbobj
{
    // Поля таблиці PHONESTATES
    public static final String COL_STATE = "STATE";

    /**
     * Конструктор.
     */
    public objphonestate ()
    {
        super (isdb.miscs.dclrs.TBL_PHONESTATE);
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
        oSQLData.setFrom ("PHONESTATES");
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
        // if (strKey.equals (objdirnr.COL_PHONESTATE_ID) ||
        // strKey.equals (objfailrpt.COL_FAIL_ID))
        // return false;
        if (strKey.equals (objdirnr.COL_PHONESTATE_ID) ||
                (strKey.equals (objfailrpt.COL_FAIL_ID) && oDBData.isObject (isdb.miscs.dclrs.OBJ_FAILRPT)))
            return false;
        /*
                if (strKey.equals (objfailrpt.COL_FAIL_ID))
                {
                    ///
                    Exception e1 = new Exception ("isObligatory:" + oDBData.toString ());
                    e1.printStackTrace ();
                    ///
                    return true;
                }
        */
        return super.isObligatory (strKey, oDBData);
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @return ознака можливості створення нових записів об'екта.
     */
    public boolean isCreateable ()
    {
        return false;
    }
}

