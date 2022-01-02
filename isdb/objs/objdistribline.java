/**
 * objdistribline.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці DISTRIBS.
 * @version 1.0 final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objdistribline extends isdbobj
{
    public static final String COL_SHELF_ID = "SHELF_ID";
    public static final String COL_DISTRIB = "DISTRIB";
    public static final String COL_PAIR = "PAIR";

    /**
     * Конструктор
     */
    public objdistribline ()
    {
        super (isdb.miscs.dclrs.TBL_DISTRIB, isdbobj.COL_DIRNR_ID);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("DIRNR_ID, SHELF||' ('||ATE||'), '||DISTRIB||', '||PAIR");
        oSQLData.setFrom ("DISTRIBS, DISTRIBSHELVES, ATES");
        oSQLData.setWhere ("SHELF_ID = DISTRIBSHELVES.ID");
        oSQLData.setWhere ("ATE_ID = ATES.ID");
        oSQLData.setOrder ("ATE||SHELF||DISTRIB||PAIR");
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
        if (strNameRefKey.equals ("SHELF_ID"))
            return "DISTRIBSHELVES";
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Проведення транзакції
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

