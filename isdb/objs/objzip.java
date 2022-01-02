/**
 * objzip.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці ZIPS
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objzip extends isdbobj
{

    // Поля таблиці ZIPS
    public static final String COL_ZIP = "ZIP";

    /**
     * Конструктор
     */
    public objzip ()
    {
        super (isdb.miscs.dclrs.TBL_ZIP);
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
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_ZIP);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_INSERT);
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oCreateNewRecordButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
                // oOutData.setButton (oCreateNewRecordButton.getButton (oOutData.getOutStream ()));
            }
        }
        oSQLData.setColumn ("ID,ZIP");
        oSQLData.setFrom ("ZIPS");
        oSQLData.setOrder ("ZIP");
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
        if (strKey.equals (objfirm.COL_ZIP_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }
}

