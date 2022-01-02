/**
 * objequipdirnr.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці EQUIPDIRNRS
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objequipdirnr extends isdbobj
{
    public static final String COL_EQUIP_ID = "EQUIP_ID";
    public static final String COL_CHANNEL = "CHANNEL";

    /**
     * Конструктор
     */
    public objequipdirnr ()
    {
        super (isdb.miscs.dclrs.TBL_EQUIPDIRNR, isdbobj.COL_DIRNR_ID);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("EQUIPDIRNRS.DIRNR_ID, NAME||'-'||NUMBEREQUIP||'/'||CHANNEL||' ('||ATE||')'");
        oSQLData.setFrom ("DIRNRS, EQUIPDIRNRS, EQUIPS, TYPEEQUIPS, ATES");
        oSQLData.setWhere ("DIRNRS.ID = DIRNR_ID");
        oSQLData.setWhere ("TYPEEQUIPS.ID = TYPEEQUIP_ID");
        oSQLData.setWhere ("EQUIPS.ID = EQUIP_ID");
        oSQLData.setWhere ("ATES.ID = ATE_ID");
        oSQLData.setOrder ("ATE||NAME||NUMBEREQUIP||CHANNEL");
        oDBData.setSQLData (oSQLData);
    }
}

