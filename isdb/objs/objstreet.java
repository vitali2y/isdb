/**
 * objstreet.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці STREETS.
 * @version 1.0 final, 7-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objstreet extends isdbobj
{

    // Поля таблиці STREETS
    public static final String COL_NAME = "NAME";
    public static final String COL_TYPELOCATION_ID = "TYPELOCATION_ID";

    /**
     * Конструктор.
     */
    public objstreet ()
    {
        super (isdb.miscs.dclrs.TBL_STREET);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME)) != "null")
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME).equals (isdb.miscs.dclrs.REGIME_MAINT))
            {
                // додаткові кнопки навігації
                // створити новий запис
                buttondata oCreateNewRecordButton = new buttondata (oDBData);
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("STREETS.ID,NAME||' ,'||TYPELOCATION");
        oSQLData.setFrom ("STREETS, TYPELOCATIONS");
        oSQLData.setWhere ("TYPELOCATION_ID = TYPELOCATIONS.ID");
        oSQLData.setOrder ("NAME||TYPELOCATION");
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
        if (strKey.equals (objfirm.COL_STREET_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }
}

