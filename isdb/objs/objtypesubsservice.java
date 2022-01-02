/**
 * objtypesubsservice.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPESUBSSERVICES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypesubsservice extends isdbobj
{

    // Орігінальни поля таблиці TYPESUBSSERVICES
    public static final String COL_SERVICE = "SERVICE";

    /**
     * Конструктор
     */
    public objtypesubsservice ()
    {
        super (isdb.miscs.dclrs.TBL_TYPESUBSSERVICE);
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
        oSQLData.setFrom ("TYPESUBSSERVICES");
        oSQLData.setOrder ("SERVICE");
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
        if (strKey.equals (objsubsservice.COL_SUBSSERV_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
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
        return "SUBSSERV_ID";
    }
}

