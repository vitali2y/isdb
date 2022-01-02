/**
 * objtypelocation.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPELOCATIONS
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypelocation extends isdbobj
{
    // Поля таблиці TYPELOCATIONS
    public static final String COL_TYPELOCATION = "TYPELOCATION";

    /**
     * Конструктор
     */
    public objtypelocation ()
    {
        super (isdb.miscs.dclrs.TBL_TYPELOCATION);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,TYPELOCATION");
        oSQLData.setFrom ("TYPELOCATIONS");
        oSQLData.setOrder ("TYPELOCATION");
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

