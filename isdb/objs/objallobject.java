/**
 * objallobject.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці USER_CATALOG (CAT).
 * @version 1.0 final, 8-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objallobject extends isdbobj
{
    // Орігінальни поля таблиці USER_CATALOG
    public static final String COL_TABLENAME = "TABLE_NAME";
    private static final String OBJECT_NAME = "allobject";
    private static final String TBL_ALLOBJECT = "USER_CATALOG";

    /**
     * Конструктор.
     */
    public objallobject ()
    {
        super (TBL_ALLOBJECT);
        putName (OBJECT_NAME);
    }

    /**
     * Вибірка функціональної назви об'екта.
     * @return назва об'екта
     */
    public String getName ()
    {
        return OBJECT_NAME;
    }

    /**
     * Вибірка назви поля таблиці.
     * @param strNameField назва поля
     * @return значення назви поля в форматі HTML
     */
    public String desc (String strNameField)
    {
        if (strNameField.equals (objconstructor.COL_COMMAND)) // COL_TABLENAME))
            return isdb.ifaces.htmli.center ("Використовуемі об'екти БД");
        return super.desc (strNameField);
    }

    /**
     * Повернення шапки об'екта.
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Використовуемі об'екти БД";
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("TABLE_NAME,TABLE_NAME");
        oSQLData.setFrom (TBL_ALLOBJECT);
        oSQLData.setOrder ("1");
        oSQLData.setWhere ("TABLE_NAME not like '%SEQ'");
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
        if (strKey.equals (objconstructor.COL_COMMAND))
            return true;
        return super.isObligatory (strKey, oDBData);
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

