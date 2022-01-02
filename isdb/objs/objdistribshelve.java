/**
 * objdistribshelve.java
 * ISDBj
 */

package isdb.objs;

import isdb.objs.*;
import isdb.datas.*;

/**
 * Об'ект таблиці DISTRIBSHELVES
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objdistribshelve extends isdbobj
{
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_SHELF = "SHELF";

    /**
     * Конструктор
     */
    public objdistribshelve ()
    {
        super (isdb.miscs.dclrs.TBL_DISTRIBSHELF);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("DISTRIBSHELVES.ID, SHELF||' ('||ATE||')'");
        oSQLData.setFrom ("DISTRIBSHELVES");
        oSQLData.setFrom ("ATES");
        oSQLData.setWhere ("ATE_ID=ATES.ID");
        oSQLData.setOrder ("ATE||SHELF");
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
        if (strKey.equals (objdistrib.COL_SHELF_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }

    /**
     * Повернення назви ідентифікатора головного об'екта, ссилка якого ссилаеться на поточний об'ект
     * <P>По замовчанню виконується конвертація назви об'екта в назву ідентифікатора об'екта.
     * @param oDBData поточни дані об'екта
     * @return назви ідентифікатора головного об'екта
     */
    public String getNameRefIdMaster (dbdata oDBData)
    {
        return "SHELF_ID";
    }
}

