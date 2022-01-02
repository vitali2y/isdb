/**
 * objatetecnician.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення ATETECHNICIANS
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objatetechnician extends isdbobj
{
    // Поля таблиці ATETECHNICIANS
    public static final String COL_PHONETECHNICIAN = "PHONETECHNICIAN";

    /**
     * Конструктор
     */
    public objatetechnician ()
    {
        super (isdb.miscs.dclrs.VIEW_PHTECHNICIAN);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, PHONETECHNICIAN");
        oSQLData.setFrom ("ATETECHNICIANS");
        oSQLData.setOrder ("PHONETECHNICIAN");
        oDBData.setSQLData (oSQLData);
    }
}

