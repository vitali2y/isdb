/**
 * objplan.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PLANS.
 * @version 1.0 final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objplan extends isdbobj
{
    // Поля таблиці PLANS
    public static final String COL_PLAN = "PLAN";
    public static final String COL_PERIOD = "PERIOD";
    public static final String COL_INCOME = "INCOME";
    public static final String COL_PLANINCOME = "PLANINCOME";

    /**
     * Конструктор.
     */
    public objplan ()
    {
        super (isdb.miscs.dclrs.TBL_PLAN);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.isNextRegime (isdb.miscs.dclrs.REGIME_UPDATE))  // перепланування діяльності?
            return "Перепланування діяльності";
        return super.getTitle (oDBData);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oDBData поточні робочі дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,to_char(PERIOD,'MM-YYYY')");
        oSQLData.setFrom ("PLANS");
        oSQLData.setOrder (COL_PERIOD);
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки обов'язковості введення значення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        if (strKey.equals (objplanservice.COL_PLAN_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
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
        objplanservice oPlanService = new objplanservice ();
        objplanequip oPlanEquip = new objplanequip ();
        objservice oService = new objservice ();
        objtypeequip oTypeEquip = new objtypeequip ();

        // ... та їх дані
        dbdata oPlanServiceDBData = new dbdata (oDBData.getSession ());
        dbdata oPlanEquipDBData = new dbdata (oDBData.getSession ());
        dbdata oServiceDBData = new dbdata (oDBData.getSession ());
        dbdata oTypeEquipDBData = new dbdata (oDBData.getSession ());

        oPlanService.describe (oPlanServiceDBData);
        oPlanEquip.describe (oPlanEquipDBData);
        oService.describe (oServiceDBData);
        oTypeEquip.describe (oTypeEquipDBData);

        // новий період планування?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oOutData.setFld (isdb.miscs.dclrs.TBL_PLANSERVICE,
                             oPlanService.checkboxlist (
                               objplanservice.COL_SERVICE_ID, objservice.COL_SERVICE, oDBData, oOutData));
            oOutData.setFld (isdb.miscs.dclrs.TBL_PLANEQUIP,
                             oPlanEquip.checkboxlist (
                               objplanequip.COL_TYPEEQUIP_ID, objtypeequip.COL_NAME, oDBData, oOutData));
            oOutData.setFld (COL_PERIOD, getListDate (COL_PERIOD, oDBData));
            oOutData.setFld (COL_INCOME, field (COL_INCOME, oDBData, oOutData));
            oOutData.setFld (COL_PLANINCOME, field (COL_PLANINCOME, oDBData, oOutData));
            oOutData.setFld (COL_PLAN,
                             isdb.ifaces.htmli.paragraph ("Загальні параметри планування",
                                                          isdb.ifaces.htmli.cell (oOutData.getFld (COL_PERIOD), 30) +
                                                          isdb.ifaces.htmli.cell (oOutData.getFld (COL_INCOME), 35) +
                                                          isdb.ifaces.htmli.cell (oOutData.getFld (COL_PLANINCOME), 35)));
            oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));
            // oOutData.setFld (COL_REMARKS, isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld (COL_REMARKS), 100)));
        }
        else    // оновлення або вибірка інформації?
        {
            reportdata oServiceRptData = new reportdata (getTitle ());
            sqldata oServiceSQLData = new sqldata ();
            reportdata oEquipRptData = new reportdata (getTitle ());
            sqldata oEquipSQLData = new sqldata ();

            // вибірка запланованих періодів?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_QUERY))
            {
                // додаткові кнопки навігації
                // створити новий запис
                buttondata oCreateNewRecordButton = new buttondata (oDBData);
                oCreateNewRecordButton.setName ("Новий період");
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
                // е запланов. періоди?
                if (new Integer (countItems (oDBData)).intValue () > 0)
                {
                    // перепланування
                    buttondata oUpdateButton = new buttondata (oDBData, isdb.miscs.dclrs.REGIME_TYPESELECT);
                    oUpdateButton.setUrl (isdb.miscs.dclrs.APPL_PROPERTY);
                    oUpdateButton.setName ("Перепланування");
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "to_char(PERIOD,'MM-YYYY')>=to_char(SYSDATE,'MM-YYYY')");
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_NEXTREGIME, isdb.miscs.dclrs.REGIME_UPDATE);
                    oDBData.setButton (oUpdateButton.getButton (oOutData));
                }
            }
            else
            {
                // огляд?
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                {
                    oOutData.setFld (COL_INCOME, value (COL_INCOME, oDBData));
                    oOutData.setFld (COL_PLANINCOME, value (COL_PLANINCOME, oDBData));
                    oOutData.setFld (COL_REMARKS, value (COL_REMARKS, oDBData));
                }
                else	// перепланування запланованих періодів?
                {
                    oOutData.setFld (COL_INCOME, field (COL_INCOME, oDBData, oOutData));
                    oOutData.setFld (COL_PLANINCOME, field (COL_PLANINCOME, oDBData, oOutData));
                    oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));

                    // перепланування обладнання
                    buttondata oEquipButton = new buttondata (oDBData, isdb.miscs.dclrs.REGIME_TYPESELECT);
                    oEquipButton.setUrl (isdb.miscs.dclrs.APPL_PROPERTY);
                    oEquipButton.setPar (COL_PERIOD, oDBData.getVal (COL_PERIOD));
                    oEquipButton.setName ("Обладнання");
                    oEquipButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_PLANEQUIP);
                    oEquipButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, COL_PERIOD + "=to_date('" + oDBData.getVal (COL_PERIOD) + "','DD-MM-YY')");
                    oEquipButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
                    oEquipButton.setPar (isdb.miscs.dclrs.PAR_NEXTREGIME, isdb.miscs.dclrs.REGIME_UPDATE);
                    oDBData.setButton (oEquipButton.getButton (oOutData));

                    // перепланування послуг
                    buttondata oServiceButton = new buttondata (oDBData, isdb.miscs.dclrs.REGIME_TYPESELECT);
                    oServiceButton.setUrl (isdb.miscs.dclrs.APPL_PROPERTY);
                    oServiceButton.setPar (COL_PERIOD, oDBData.getVal (COL_PERIOD));
                    oServiceButton.setName ("Послуги");
                    oServiceButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_PLANSERVICE);
                    oServiceButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, COL_PERIOD + "=to_date('" + oDBData.getVal (COL_PERIOD) + "','DD-MM-YY')");
                    oServiceButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
                    oServiceButton.setPar (isdb.miscs.dclrs.PAR_NEXTREGIME, isdb.miscs.dclrs.REGIME_UPDATE);
                    oDBData.setButton (oServiceButton.getButton (oOutData));

                    // крітерій вибврки по періодам
                    oServiceSQLData.setWhere (COL_PERIOD + "=to_date('" + oDBData.getVal (COL_PERIOD) + "','DD-MM-YY')");
                    oEquipSQLData.setWhere (COL_PERIOD + "=to_date('" + oDBData.getVal (COL_PERIOD) + "','DD-MM-YY')");
                }
                // oOutData.setFld (COL_REMARKS, isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld (COL_REMARKS), 100)));
            }
            oOutData.setFld (COL_REMARKS, isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (oOutData.getFld (COL_REMARKS), 100)));            // послуги
            oServiceRptData.createColumn (desc (COL_PERIOD), 33);
            oServiceRptData.createColumn (oService.desc (objservice.COL_SERVICE), 33);
            oServiceRptData.createColumn (oPlanService.desc (objplanservice.COL_SERVICES), 33);
            oServiceRptData.setWithoutSummary ();

            oServiceSQLData.setColumn ("to_char(PERIOD,'MM-YYYY')");
            oServiceSQLData.setColumn ("SERVICE");
            oServiceSQLData.setColumn ("SERVICES");

            oServiceSQLData.setFrom ("PLANS,PLANSERVICES,SERVICES");
            oServiceSQLData.setWhere ("PLANS.ID=PLAN_ID");
            oServiceSQLData.setWhere ("SERVICES.ID=SERVICE_ID");
            oServiceSQLData.setOrder (COL_PERIOD);
            oServiceRptData.setModeData (oServiceSQLData.getData ());

            oOutData.setFld (isdb.miscs.dclrs.TBL_PLANSERVICE,
                             isdb.ifaces.htmli.paragraph (
                               getItems (oDBData, oServiceRptData).getReport ()));

            // обладнання
            oEquipRptData.createColumn (desc (COL_PERIOD), 33);
            oEquipRptData.createColumn (oTypeEquip.desc (objtypeequip.COL_NAME), 33);
            oEquipRptData.createColumn (oPlanEquip.desc (objplanequip.COL_EQUIPS), 33);
            oEquipRptData.setWithoutSummary ();

            oEquipSQLData.setColumn ("to_char(PERIOD,'MM-YYYY')");
            oEquipSQLData.setColumn ("NAME");
            oEquipSQLData.setColumn ("EQUIPS");

            oEquipSQLData.setFrom ("PLANS,PLANEQUIPS,TYPEEQUIPS");
            oEquipSQLData.setWhere ("PLANS.ID=PLAN_ID");
            oEquipSQLData.setWhere ("TYPEEQUIPS.ID=TYPEEQUIP_ID");
            oEquipSQLData.setOrder (COL_PERIOD);
            oEquipRptData.setModeData (oEquipSQLData.getData ());

            oOutData.setFld (isdb.miscs.dclrs.TBL_PLANEQUIP,
                             isdb.ifaces.htmli.paragraph (
                               getItems (oDBData, oEquipRptData).getReport ()));
        }
        // формування послуг та обладнання
        oOutData.setFld ("SERVICEEQUIP", isdb.ifaces.htmli.paragraph ("Плануемі послуги та обладнання",
                         isdb.ifaces.htmli.cell (oOutData.getFld (isdb.miscs.dclrs.TBL_PLANSERVICE), 50) +
                         isdb.ifaces.htmli.cell (oOutData.getFld (isdb.miscs.dclrs.TBL_PLANEQUIP), 50)));

        // приготування HTML сторінки
        return
          // перший ряд
          oOutData.getFld (COL_PLAN) +

          // другий ряд
          oOutData.getFld ("SERVICEEQUIP") +

          // третій ряд
          oOutData.getFld (COL_REMARKS) +

          // сховани параметри форми
          oOutData.getHideFld ();
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @return ознака можливості створення нових записів об'екта.
     */
    public boolean isCreateable ()
    {
        return false;
    }

    /**
     * Приготування звіта.
     * @param oDBData поточни дані об'екта
     * @return сформований в форматі HTML звіт
     */
    public dbdata report (dbdata oDBData)
    {
        String strReport = "???";
        String strRptTitle = "???";
        String strRptNumb = "???";
        sqldata oSQLData = new sqldata ();

        // Робочий проект по плануванню діяльності
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.REPORT_PLAN_PROJECT))
        {
            strRptNumb = "";
            strRptTitle = "Планування діяльності. Робочий проект станом на " + isdb.ifaces.dbi.dbdate (oDBData);
            reportdata oServiceRptData = new reportdata (getTitle ());
            sqldata oServiceSQLData = new sqldata ();
            reportdata oEquipRptData = new reportdata (getTitle ());
            sqldata oEquipSQLData = new sqldata ();

            // додаткові об'екти
            objplanservice oPlanService = new objplanservice ();
            objplanequip oPlanEquip = new objplanequip ();
            objservice oService = new objservice ();
            objtypeequip oTypeEquip = new objtypeequip ();

            // ... та їх дані
            dbdata oPlanServiceDBData = new dbdata (oDBData.getSession ());
            dbdata oPlanEquipDBData = new dbdata (oDBData.getSession ());
            dbdata oServiceDBData = new dbdata (oDBData.getSession ());
            dbdata oTypeEquipDBData = new dbdata (oDBData.getSession ());

            oPlanService.describe (oPlanServiceDBData);
            oPlanEquip.describe (oPlanEquipDBData);
            oService.describe (oServiceDBData);
            oTypeEquip.describe (oTypeEquipDBData);

            // послуги
            oServiceRptData.createColumn (desc (COL_PERIOD), 25);
            oServiceRptData.createColumn (oService.desc (objservice.COL_SERVICE), 25);
            oServiceRptData.createColumn (oPlanService.desc (objplanservice.COL_SERVICES), 25);
            oServiceRptData.createColumn (oPlanService.desc (COL_REMARKS), 25);

            oServiceSQLData.setColumn ("to_char(PERIOD,'MM-YYYY')");
            oServiceSQLData.setColumn ("SERVICE");
            oServiceSQLData.setColumn ("SERVICES");
            oServiceSQLData.setColumn (isdb.miscs.dclrs.TBL_PLANSERVICE + "." + COL_REMARKS);

            oServiceSQLData.setFrom ("PLANS,PLANSERVICES,SERVICES");
            oServiceSQLData.setWhere ("PLANS.ID=PLAN_ID");
            oServiceSQLData.setWhere ("SERVICES.ID=SERVICE_ID");
            oServiceSQLData.setOrder (COL_PERIOD);
            oServiceRptData.setModeData (oServiceSQLData.getData ());

            // обладнання
            oEquipRptData.createColumn (desc (COL_PERIOD), 25);
            oEquipRptData.createColumn (oTypeEquip.desc (objtypeequip.COL_NAME), 25);
            oEquipRptData.createColumn (oPlanEquip.desc (objplanequip.COL_EQUIPS), 25);
            oEquipRptData.createColumn (oPlanEquip.desc (COL_REMARKS), 25);

            oEquipSQLData.setColumn ("to_char(PERIOD,'MM-YYYY')");
            oEquipSQLData.setColumn ("NAME");
            oEquipSQLData.setColumn ("EQUIPS");
            oEquipSQLData.setColumn (isdb.miscs.dclrs.TBL_PLANEQUIP + "." + COL_REMARKS);

            oEquipSQLData.setFrom ("PLANS,PLANEQUIPS,TYPEEQUIPS");
            oEquipSQLData.setWhere ("PLANS.ID=PLAN_ID");
            oEquipSQLData.setWhere ("TYPEEQUIPS.ID=TYPEEQUIP_ID");
            oEquipSQLData.setOrder (COL_PERIOD);
            oEquipRptData.setModeData (oEquipSQLData.getData ());

            strReport = getItems (oDBData, oServiceRptData).getReport () +
                        getItems (oDBData, oEquipRptData).getReport ();
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }
}

