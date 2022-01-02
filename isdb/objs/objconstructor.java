/**
 * objconstructor.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці CONSTRUCTORS.
 * <P>Цей об'ект призначений для створення користувачем нестандартних
 * форм, звітів та гравиків та подальшого їх використання.
 * @version 1.0 final, 8-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objconstructor extends isdbobj
{

    // Орігінальни поля таблиці CONSTRUCTORS
    public static final String COL_NAME = "NAME";
    public static final String COL_APPLICATION = "APPLICATION";
    public static final String COL_COMMAND = "COMMAND";
    public static final String COL_FIELDS = "FIELDS";
    public static final String COL_DATETIMEFLAG = "DATETIMEFLAG";

    /** Фаза транзакції - введення назв таблиць */
    private static int TRNPHASE_TABLES = 1;
    /** Фаза транзакції - введення назв полів */
    private static int TRNPHASE_COLUMNS = 2;
    /** Фаза транзакції - крітерії вибірки */
    private static int TRNPHASE_CRITERIA = 3;
    /** Фаза транзакції - введення сортування */
    private static int TRNPHASE_ORDERBY = 4;
    /** Фаза транзакції - введення групповання */
    private static int TRNPHASE_GROUPBY = 5;

    /**
     * Конструктор.
     */
    public objconstructor ()
    {
        super (isdb.miscs.dclrs.TBL_CONSTRUCTOR);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT))
        {
            // додаткові кнопки навігації
            // створити новий запис
            buttondata oCreateNewRecordButton = new buttondata (oDBData);
            oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));

            // е зконструьовани об'екти?
            if (new Integer (countItems (oDBData)).intValue () > 0)
            {
                // вилучити запис
                buttondata oDeleteButton = new buttondata (oDBData, isdb.miscs.dclrs.REGIME_TYPESELECT);
                oDeleteButton.setUrl (isdb.miscs.dclrs.APPL_PROPERTY);
                oDeleteButton.setName ("Вилучити");
                oDeleteButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
                oDeleteButton.setPar (isdb.miscs.dclrs.PAR_NEXTREGIME, isdb.miscs.dclrs.REGIME_DELETE);
                oDBData.setButton (oDeleteButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("ID,NAME");
        oSQLData.setFrom ("CONSTRUCTORS");
        oSQLData.setOrder ("NAME");
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
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            return super.fields (oDBData, oOutData);
        }

        // додаткові об'екти
        objallobject oObjects = new objallobject ();
        objallcolumn oColumns = new objallcolumn ();

        // ... та їх дані
        dbdata oObjectsDBData = new dbdata (oDBData.getSession ());
        dbdata oColumnsDBData = new dbdata (oDBData.getSession ());

        // формування нової форми, звіта або графіка
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oObjects.select (oObjectsDBData);
            oObjectsDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            oOutData.setFld ("COMMAND", oObjects.list (COL_COMMAND, oObjectsDBData, oOutData, true));
            oOutData.setFld ("NAME", field (COL_NAME, oDBData, oOutData));
            oOutData.setFld ("APPLICATION", desc (COL_APPLICATION) +
                             htmli.select (COL_APPLICATION,
                                           isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.APPL_FORM, "Форма") +
                                           isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.APPL_REPORT, "Звіт", true) +
                                           isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.APPL_GRAPH, "Графік")));
            oOutData.setFld ("DATETIMEFLAG", isdb.ifaces.htmli.formcheckboxpar (COL_DATETIMEFLAG, "0", desc (COL_DATETIMEFLAG, false)));
            oOutData.setFld ("PARAMETERS", oOutData.getFld ("APPLICATION") +
                             isdb.ifaces.htmli.crlf () +
                             oOutData.getFld ("DATETIMEFLAG"));
            oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
        }
        else    // оновлення або вибірка інформації?
        {
            oOutData.setFld ("NAME", value (COL_NAME, oDBData));
            oOutData.setFld ("COMMAND",
                             htmli.paragraph (isdb.ifaces.htmli.cell (value (COL_COMMAND, oDBData), 100)));

            // вибірка інформації стосовно потрібної форми, звіта або графіка?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                oOutData.setFld ("NAME", value (COL_NAME, oDBData));
                oOutData.setFld ("REMARKS", value (COL_REMARKS, oDBData));
                if (oDBData.getVal (COL_APPLICATION).equals (isdb.miscs.dclrs.APPL_FORM))
                    oDBData.setVal (COL_APPLICATION, "Форма");
                else
                {
                    if (oDBData.getVal (COL_APPLICATION).equals (isdb.miscs.dclrs.APPL_REPORT))
                        oDBData.setVal (COL_APPLICATION, "Звіт");
                    else
                        oDBData.setVal (COL_APPLICATION, "Графік");
                }
                oOutData.setFld ("PARAMETERS", value (COL_APPLICATION, oDBData) +
                                 isdb.ifaces.htmli.crlf () +
                                 value (COL_DATETIMEFLAG, oDBData));
            }
            else	// оновлення інформації?
            {
                oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
                if (oDBData.isTrnPhase (TRNPHASE_COLUMNS))
                {
                    oColumnsDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                    oColumnsDBData.setVal (objallcolumn.COL_TABLE_NAME, isdb.datas.sqldata.SQL_FROM + oDBData.getVal (COL_COMMAND));
                    oOutData.setFld ("PARAMETERS", oColumns.list (objallcolumn.COL_COLUMN_NAME, oColumnsDBData, oOutData, true));
                }
                if (oDBData.isTrnPhase (TRNPHASE_CRITERIA))
                {
                    oOutData.setFld ("PARAMETERS", field (COL_FIELDS, oDBData, oOutData));
                }
                if (oDBData.isTrnPhase (TRNPHASE_GROUPBY))
                {
                    oOutData.setFld ("PARAMETERS", field (COL_COMMAND, oDBData, oOutData));
                }
            }
        }
        ///
        Exception enew55 = new Exception ("fields :\nDBData: " + oDBData.toString ());
        enew55.printStackTrace ();
        ///

        // приготування HTML сторінки
        return
          // перший ряд
          htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld ("NAME"), 100)) +

          // другий ряд
          htmli.paragraph ("Параметри для конструйовання",
                           // загальні параметри конструйовання
                           isdb.ifaces.htmli.cell (oOutData.getFld ("PARAMETERS"), 50) +
                           // SQL команда, яка була сформована
                           isdb.ifaces.htmli.cell (oOutData.getFld ("COMMAND"), 50)) +

          // третій ряд
          htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld ("REMARKS"), 100)) +

          // сховани параметри форми
          oOutData.getHideFld ();
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни данні об'екта
     * @param oPoolData пул зберегаемих значеннь, використовуемих в транзакціях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        super.writeData (oDBData, oPoolData);
        oDBData.setRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT);
        oDBData.setVal (isdb.miscs.dclrs.PAR_UPDATE_RECORD + oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT), "1");
        oDBData.setVal (isdb.miscs.dclrs.PAR_APPL, isdb.miscs.dclrs.APPL_FORM);
        String strTmp = oDBData.getVal (COL_COMMAND);
        oDBData.setNextTrnPhase ();
        ///
        Exception enew55 = new Exception ("writeData here:\nDBData: " + oDBData.toString ());
        enew55.printStackTrace ();
        ///
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        // return "Некоректно виконан попередній етап конструйовання!";
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)      // не знайдено?
            return "Ще не було створено сконструйованих структур!";
        return super.getMsg (iNumberMsg, oDBData);
    }
}

