/**
 * objtypeequip.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEEQUIPS.
 * @version 1.0 final, 4-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypeequip extends isdbobj
{
    // Поля таблиці TYPEEQUIPS
    public static final String COL_NAME = "NAME";
    public static final String COL_CHANNELS = "CHANNELS";

    /**
     * Конструктор,
     */
    public objtypeequip ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEEQUIP);
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
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_TYPEEQUIP);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("ID,NAME||' ('||CHANNELS||')'");
        oSQLData.setFrom ("TYPEEQUIPS");
        oSQLData.setOrder ("NAME");
        oDBData.setSQLData (oSQLData);
    }
}

