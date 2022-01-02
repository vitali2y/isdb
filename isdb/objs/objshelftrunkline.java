/**
 * objshelftrunkline.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці SHELFTRUNKLINES.
 * @version 1.0final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objshelftrunkline extends isdbobj
{
    // Поля таблиці SHELFTRUNKLINES
    public static final String COL_TRUNK_ID = "TRUNK_ID";
    public static final String COL_PAIR = "PAIR";

    /**
     * Конструктор
     */
    public objshelftrunkline ()
    {
        super (isdb.miscs.dclrs.TBL_SHELFTRUNKLINE, isdbobj.COL_DIRNR_ID);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("DIRNR_ID, TRUNK||'/'||PAIR||', шафа '||SHELF||' ('||ATE||')'");
        oSQLData.setFrom ("SHELFTRUNKLINES, SHELFTRUNKS, DISTRIBSHELVES, ATES");
        oSQLData.setWhere ("DISTRIBSHELVES.ID = SHELF_ID");
        oSQLData.setWhere ("SHELFTRUNKS.ID = TRUNK_ID");
        oSQLData.setWhere ("ATES.ID = ATE_ID");
        oSQLData.setOrder ("ATE||SHELF||TRUNK||PAIR");
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSERT))
            oDBData.setVal (isdb.miscs.dclrs.PAR_IMMEDUPDATE, isdb.miscs.dclrs.YES);
        return super.fields (oDBData, oOutData);
    }

    /**
     * Повернення назви ссилочного об'екта
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals ("TRUNK_ID"))
            return "SHELFTRUNKS";
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни дані об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        transactiondata oTransactionData = new transactiondata ();
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSCOMMIT))
            oDBData.setVal (COL_DIRNR_ID, oDBData.getVal (COL_ID));

        ///
        Exception e66 = new Exception ("writeData: " + oDBData.toString () + "/" + oPoolData.toString (oDBData.getSession ()));
        e66.printStackTrace ();
        ///

        super.writeData (oDBData, oPoolData, oTransactionData);
    }
}

