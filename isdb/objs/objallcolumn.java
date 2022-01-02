/**
 * objallcolumn.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці ALL_TAB_COLUMNS.
 * @version 1.0 final, 8-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objallcolumn extends isdbobj
{
    // Орігінальни поля таблиці USER_TAB_COLUMNS
    public static final String COL_COLUMN_NAME = "COLUMN_NAME";
    public static final String COL_TABLE_NAME = "TABLE_NAME";
    private static final String OBJECT_NAME = "allcolumn";
    private static final String TBL_ALLCOLUMN = "ALL_TAB_COLUMNS";

    /**
     * Конструктор.
     */
    public objallcolumn ()
    {
        super (TBL_ALLCOLUMN);
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
        if (strNameField.equals (COL_COLUMN_NAME))
            return isdb.ifaces.htmli.center ("Поля вибраних об'ектів БД");
        return super.desc (strNameField);
    }

    /**
     * Повернення шапки об'екта.
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Поля об'ектів БД";
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        /*
        select COLUMN_NAME from sys.user_tab_columns
        where TABLE_NAME = 'AGREEMENTS' and
        COLUMN_NAME!='ID' and
        COLUMN_NAME!='PERSON_ID' and
        COLUMN_NAME!='STATEDATE'

        select TABLE_NAME, COLUMN_NAME from all_tab_columns
        where OWNER = 'ISDB_DBA'
        */

        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn (COL_COLUMN_NAME + "," + COL_COLUMN_NAME);
        oSQLData.setFrom (TBL_ALLCOLUMN);
        oSQLData.setWhere (COL_COLUMN_NAME + "!='ID'");
        oSQLData.setWhere (COL_COLUMN_NAME + "!='PERSON_ID'");
        oSQLData.setWhere (COL_COLUMN_NAME + "!='STATEDATE'");
        oSQLData.setWhere ("OWNER='ISDB_DBA'");
        oSQLData.setOrder ("1");
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

