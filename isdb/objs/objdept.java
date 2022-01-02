/**
 * objdept.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці DEPTS
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objdept extends isdbobj
{
    // Поля таблиці DEPTS
    public static final String COL_DEPARTMENT = "DEPARTMENT";
    public static final String COL_DEPTNAME = "DEPTNAME";

    /**
     * Конструктор
     */
    public objdept ()
    {
        super (isdb.miscs.dclrs.TBL_DEPT);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, DEPARTMENT");
        oSQLData.setFrom ("DEPTS");
        oSQLData.setOrder ("DEPARTMENT");
        oDBData.setSQLData (oSQLData);
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

