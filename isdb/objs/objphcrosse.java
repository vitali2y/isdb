/**
 * objphcrosse.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PHCROSSES
 * @version 1.0betta, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objphcrosse extends isdbobj
{
    // Поля таблиці PHCROSSES
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_PHONE = "PHONE";

    /**
     * Конструктор
     */
    public objphcrosse ()
    {
        super (isdb.miscs.dclrs.TBL_PHCROSS);
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
                buttondata oCreateNewRecordButton = new buttondata ();
                oCreateNewRecordButton.setUrl ("isdbform");
                oCreateNewRecordButton.setName (isdb.miscs.dclrs.TITLE_REG_NEW_RECORD);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_PHCROSSES);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("PHCROSSES.ID, PHONE||' ('||ATE||')'");
        oSQLData.setFrom ("PHCROSSES, ATES");
        oSQLData.setWhere ("ATES.ID = ATE_ID");
        oSQLData.setOrder ("PHONE||ATE");
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
}

