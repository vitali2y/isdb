/**
 * objtypewwwservice.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEWWWSERVICES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypewwwservice extends isdbobj
{

    // Орігінальни поля таблиці TYPEWWWSERVICES
    public static final String COL_SERVICE = "SERVICE";

    /**
     * Конструктор
     */
    public objtypewwwservice ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEWWWSERVICE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, SERVICE");
        oSQLData.setFrom ("TYPEWWWSERVICES");
        oSQLData.setOrder ("SERVICE");
        oDBData.setSQLData (oSQLData);
    }

    /*
     * Повернення ознаки можливості створення нових записів об'екта.
     * @param oDBData поточні дані об'екта
     * @return ознака можливісті на створення нового запису
     */
    public boolean isCreateable (dbdata oDBData)
    {
        return false;
    }

    /**
     * Повернення назви ідентифікатора головного об'екта, ссилка якого ссилаеться на поточний об'ект
     * @return назви ідентифікатора головного об'екта
     */
    public String getNameRefIdMaster ()
    {
        return "WWWSERV_ID";
    }
}

