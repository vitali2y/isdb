/**
 * objwwworder.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці WWWORDERS.
 * @version 1.0 final, 10-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objwwworder extends isdbobj
{
    // Поля таблиці WWWORDERS
    public static final String COL_ORD_NUM = "ORD_NUM";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_WWWORDERSTATE_ID = "WWWORDERSTATE_ID";
    public static final String COL_FIRM_ID = "FIRM_ID";
    public static final String COL_CONTACTADMPERSON = "CONTACTADMPERSON";
    public static final String COL_PHONEADMPERSON = "PHONEADMPERSON";
    public static final String COL_FAXADMPERSON = "FAXADMPERSON";
    public static final String COL_EMAILADMPERSON = "EMAILADMPERSON";
    public static final String COL_CONTACTTECHPERSON = "CONTACTTECHPERSON";
    public static final String COL_PHONETECHPERSON = "PHONETECHPERSON";
    public static final String COL_FAXTECHPERSON = "FAXTECHPERSON";
    public static final String COL_EMAILTECHPERSON = "EMAILTECHPERSON";
    public static final String COL_HARDWARE = "HARDWARE";
    public static final String COL_SOFTWARE = "SOFTWARE";
    public static final String COL_STATEDATE = "STATEDATE";
    public static final String COL_FINISHDATE = "FINISHDATE";

    /** Послідуючій номер нового запису */
    private static String NUMBERWWWORDER = "NUMBERWWWORDER";

    /**
     * Конструктор.
     */
    public objwwworder ()
    {
        super (isdb.miscs.dclrs.TBL_WWWORDER);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        // внесення інформації стосовно замовлених Интернет-послуг?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
            return "Замовлення абонентом нових Интернет-послуг";
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE))
            return "Замовлені абонентом Интернет-послуги";
        return super.getTitle (oDBData);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("WWWORDERS.ID, 'угода '||ORD_NUM||' від '|| to_char (WWWORDERS.STARTDATE, 'DD-MM-YY')");
        oSQLData.setFrom ("WWWORDERS,FIRMS");
        oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
        oSQLData.setOrder ("WWWORDERS.STARTDATE");
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
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objfirm oFirm = new objfirm ();
        objwwworderstate oWWWOrderState = new objwwworderstate ();

        // .. та їх дані
        dbdata oFirmDBData = new dbdata (strSesnId);
        dbdata oWWWOrderStateDBData = new dbdata (strSesnId);

        // внесення інформації стосовно замовлених Интернет-послуг?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oFirmDBData.setVal (COL_ID, oDBData.getVal (COL_ID));
            oFirm.select (oFirmDBData);
            oWWWOrderStateDBData.setVal (COL_ID, "F");
            oWWWOrderState.select (oWWWOrderStateDBData);

            String strTmp = getNextSeqId (oDBData, NUMBERWWWORDER);
            jsdata oJSData = oOutData.getJSData ();
            oJSData.setJS (COL_ORD_NUM, isdb.ifaces.jsi.JS_FUNC_CHK_VAL, "-1", strTmp);
            oOutData.setJSData (oJSData);
            oOutData.setFld (COL_ORD_NUM, field (COL_ORD_NUM, strTmp, 5, oOutData));
            /*
            + isdb.ifaces.htmli.getCheckBoxPrintRpt (isdb.miscs.dclrs.OBJ_WWWORDER, isdb.miscs.dclrs.PAPER_ORDER_WWWORDER));
            */
            oOutData.setFld (COL_STARTDATE, field (COL_STARTDATE, dbi.dbdate (oDBData), 14, oOutData));
            oOutData.setFld (COL_WWWORDERSTATE_ID, oWWWOrderState.value (objwwworderstate.COL_STATE, oWWWOrderStateDBData));
            oOutData.setFld (COL_FIRM_ID, oFirm.value (objfirm.COL_NAME, oFirmDBData));
            oOutData.setFld (COL_HARDWARE, textareafield (COL_HARDWARE, oDBData, oOutData));
            oOutData.setFld (COL_SOFTWARE, textareafield (COL_SOFTWARE, oDBData, oOutData));
            // WWW & FTP
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVWWW, isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_WWWSRVWWW, "Послуги WWW та FTP"));
            // DNS
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVDNS, isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_WWWSRVDNS, "Послуга DNS: надання нового або підтримка існуючого DNS-імені"));
            // Надання постійних IP адрес
            oJSData.setJS (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_WWWSRVCONSTIP, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            // Постійні IP адреси
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVCONSTIP, field (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_WWWSRVCONSTIP, "", 5, oOutData) + " замовлених постійних IP адрес");
            // Послуга електронної пошти (e-mail)
            oJSData.setJS (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_WWWSRVEMAIL, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            // Послуга E-mail
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVEMAIL, field (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_WWWSRVEMAIL, "", 5, oOutData) + " замовлених адрес електронної пошти (e-mail)");
            // SMTP
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVSMTP, isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_WWWSRVSMTP, "Підтримка поштового серверу (SMTP)"));
            // Web-хостінг
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVWEBHOST, isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_WWWSRVWEBHOST, "Розміщення Web-сторінок на сервері Утел"));
            // ISDN карти
            oOutData.setFld (isdb.miscs.dclrs.OBJ_WWWSRVISDNCARD, isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_WWWSRVISDNCARD, "Використання ISDN-карти, що належить Утел"));
            oJSData.setJS (COL_PHONEADMPERSON, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            oJSData.setJS (COL_PHONEADMPERSON, isdb.ifaces.jsi.JS_FUNC_CHK_SIZE, "10", "10");
            oJSData.setJS (COL_PHONETECHPERSON, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            oJSData.setJS (COL_FAXADMPERSON, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            oJSData.setJS (COL_FAXTECHPERSON, isdb.ifaces.jsi.JS_FUNC_CHK_NUM);
            oOutData.setJSData (oJSData);

            oOutData.setFld (COL_CONTACTADMPERSON, field (COL_CONTACTADMPERSON, oDBData, oOutData));
            oOutData.setFld (COL_PHONEADMPERSON, field (COL_PHONEADMPERSON, oDBData, oOutData));
            oOutData.setFld (COL_FAXADMPERSON, field (COL_FAXADMPERSON, oDBData, oOutData));
            oOutData.setFld (COL_EMAILADMPERSON, field (COL_EMAILADMPERSON, oDBData, oOutData));
            oOutData.setFld (COL_CONTACTTECHPERSON, field (COL_CONTACTTECHPERSON, oDBData, oOutData));
            oOutData.setFld (COL_PHONETECHPERSON, field (COL_PHONETECHPERSON, oDBData, oOutData));
            oOutData.setFld (COL_FAXTECHPERSON, field (COL_FAXTECHPERSON, oDBData, oOutData));
            oOutData.setFld (COL_EMAILTECHPERSON, field (COL_EMAILTECHPERSON, oDBData, oOutData));
            oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));
            oOutData.setHideFld (COL_WWWORDERSTATE_ID, "F");
            oOutData.setHideFld (COL_FIRM_ID, oDBData.getVal (COL_ID));

            // додаткові кнопки навігації
            buttondata oFirmButton = new buttondata ();
            oFirmButton.setName ("Абонент");
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, strId);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oFirmButton.getButton (oOutData));
        }
        else
        {
            oOutData.setFld (COL_ORD_NUM, value (COL_ORD_NUM, oDBData));
            oOutData.setFld (COL_STARTDATE, value (COL_STARTDATE, oDBData));
            oOutData.setFld (COL_FIRM_ID, oFirm.value (objfirm.COL_NAME, oFirmDBData));
            // вибірка інформації стосовно замовлених Интернет-послуг?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                oOutData.setFld (COL_REMARKS, value (COL_REMARKS, oDBData));
            else	// режим: REGIME_UPDATE?
                oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));
        }

        // приготування сторінки
        // перший ряд
        return isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld (COL_ORD_NUM), 30) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_STARTDATE), 20) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_WWWORDERSTATE_ID), 25) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_FIRM_ID), 25)) +

               // другий ряд
               isdb.ifaces.htmli.paragraph (getTitle (),
                                            isdb.ifaces.htmli.cell (
                                              isdb.ifaces.htmli.unorderedlist (
                                                isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVWWW)) +
                                                isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVDNS)) +
                                                isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVCONSTIP)) +
                                                isdb.ifaces.htmli.listitem ("Послуги електронної пошти" +
                                                                            isdb.ifaces.htmli.unorderedlist (
                                                                              isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVEMAIL)) +
                                                                              isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVSMTP)))) +
                                                isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVWEBHOST)) +
                                                isdb.ifaces.htmli.listitem (oOutData.getFld (isdb.miscs.dclrs.OBJ_WWWSRVISDNCARD))), 100)) +

               // третій ряд
               isdb.ifaces.htmli.paragraph ("Контактна особа з адміністративних питань",
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_CONTACTADMPERSON) +
                                                                    isdb.ifaces.htmli.crlf () +
                                                                    oOutData.getFld (COL_PHONEADMPERSON) +
                                                                    oOutData.getFld (COL_FAXADMPERSON) +
                                                                    isdb.ifaces.htmli.crlf () +
                                                                    oOutData.getFld (COL_EMAILADMPERSON), 100)) +

               // четвертий ряд
               isdb.ifaces.htmli.paragraph ("Контактна особа з техничних питань",
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_CONTACTTECHPERSON) +
                                                                    isdb.ifaces.htmli.crlf () +
                                                                    oOutData.getFld (COL_PHONETECHPERSON) +
                                                                    oOutData.getFld (COL_FAXTECHPERSON) +
                                                                    isdb.ifaces.htmli.crlf () +
                                                                    oOutData.getFld (COL_EMAILTECHPERSON), 100)) +

               // п'ятий ряд
               isdb.ifaces.htmli.paragraph ("Загальна інформація щодо використання послуг",
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_HARDWARE), 50) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_SOFTWARE), 50)) +

               // шостий ряд
               isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld (COL_REMARKS), 100)) +

               // сховани параметри
               oOutData.getHideFld ();
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)       // не знайдено?
        {
            if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null")
            {
                // актівація послуг Інтернет?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals ("WWWORDERSTATE_ID='L'"))
                    return "Актівованих Інтернет послуг немає!";
                // заблоковані Інтернет послуги?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals ("WWWORDERSTATE_ID='B'"))
                    return "Заблокованих Інтернет послуг немає!";
                // не активні Інтернет послуги?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals ("WWWORDERSTATE_ID='N'"))
                    return "Не активних Інтернет послуг немає!";
            }
            return "Нема замовлених Інтернет-послуг!";
        }
        return super.getMsg (iNumberMsg, oDBData);
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни данні об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
            oDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdbobj.COL_ID));
        super.writeData (oDBData, oPoolData);
    }

    /**
     * pre-маніпуляція даними поперед загрузки в стек даних.
     * @param oDBData поточни дані об'екта
     */
    public void prePrepareData (dbdata oDBData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT))
            oDBData.setVal (objwwwservice.COL_WWWORDER_ID, oDBData.getVal (COL_ID));
    }
}

