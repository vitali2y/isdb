/**
 * objequip.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці EQUIPS
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objequip extends isdbobj
{
    // Поля таблиці EQUIPS
    public static final String COL_TPEEQUIP_ID = "TYPEEQUIP_ID";
    public static final String COL_NUMBEREQUIP = "NUMBEREQUIP";
    public static final String COL_ATE_ID = "ATE_ID";
    public static final String COL_CHANNEL = "CHANNEL";

    /**
     * Конструктор
     */
    public objequip ()
    {
        super (isdb.miscs.dclrs.TBL_EQUIP);
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
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_EQUIPDIRNR);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("EQUIPS.ID,NAME||'-'||NUMBEREQUIP||' ('||ATE||')'");
        oSQLData.setFrom ("EQUIPS,TYPEEQUIPS,ATES");
        oSQLData.setWhere ("TYPEEQUIPS.ID=TYPEEQUIP_ID");
        oSQLData.setWhere ("ATES.ID=ATE_ID");
        oSQLData.setOrder ("ATE||NAME||NUMBEREQUIP");
        oDBData.setSQLData (oSQLData);
    }
}

