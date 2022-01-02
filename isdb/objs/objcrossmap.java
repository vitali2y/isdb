/**
 * objcrossmap.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект: кросіровки (відображення карти кросіровок)
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objcrossmap extends isdbobj
{
    public static final String PAR_DETAILINFO = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "DETAILINFO";
    private String strAteTrunkLineFlag = isdb.miscs.dclrs.NO;
    private String strDistribFlag = isdb.miscs.dclrs.NO;
    private String strShelfTrunkLineFlag = isdb.miscs.dclrs.NO;
    private String strAteCrossFlag = isdb.miscs.dclrs.NO;
    private String strSwitch = null;

    /**
     * Конструктор
     */
    public objcrossmap ()
    {
        super (isdb.miscs.dclrs.TBL_DIRNR);
        strAteTrunkLineFlag = cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_ATETRUNKLINE);
        strDistribFlag = cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_DISTRIB);
        strShelfTrunkLineFlag = cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_SHELFTRUNKLINE);
        strAteCrossFlag = cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_ATECROSSLINE);
        strSwitch = cfgi.getOption (isdb.miscs.dclrs.OPT_SWITCH);
    }

    /**
     * Повернення шапки об'екта
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу кросіровок по АТС";
        return "Структурна карта кросіровок";
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        boolean bDetail = false;
        boolean bModify = false;
        String strBody = "";
        String strRemarks = "";
        String strDetailInfo = "";
        String strTitle = "";
        String strDirNrTitle = null;
        String strLocation = "";
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        if ((String.valueOf (strId) != "null") && strId.equals (isdb.miscs.dclrs.OBJ_NULL))
            strId = oDBData.getVal (COL_ID);
        String strSesnId = oDBData.getSession ();
        String strRegime = oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME);
        String strInvertRegime = strRegime;

        if (strRegime.equals (isdb.miscs.dclrs.REGIME_RETRIEVE))
            strRegime = isdb.miscs.dclrs.REGIME_QUERY;
        if (strInvertRegime.equals (isdb.miscs.dclrs.REGIME_QUERY) ||
                strInvertRegime.equals (isdb.miscs.dclrs.REGIME_RETRIEVE))
        {
            strInvertRegime = isdb.miscs.dclrs.REGIME_UPDATE;
            bModify = false;
        }
        else
        {
            strInvertRegime = isdb.miscs.dclrs.REGIME_QUERY;
            bModify = true;
        }
        if (String.valueOf (strId) == "null")
        {
            oDBData.setVal (isdb.miscs.dclrs.PAR_ID, isdb.miscs.dclrs.OBJ_NULL);
            strId = isdb.miscs.dclrs.OBJ_NULL;
        }
        objdstrcrosstrace oDstrCrossTrace = null;
        objshlfcrosstrace oShelfCrossTrace = null;
        objatecrosstrace oAteCrossTrace = null;
        objtrunkcrosstrace oTrunkCrossTrace = null;

        // Додаткові об'екти
        objdirnr oDirNr = new objdirnr ();
        objloclentrace oLocLenTrace = new objloclentrace ();
        objlocexchmdftrace oLocExchTrace = new objlocexchmdftrace ();
        objloclinemdftrace oLocLineTrace = new objloclinemdftrace ();

        objlocation oLocation = new objlocation ();
        objequipcrosstrace oEquipCrossTrace = new objequipcrosstrace ();
        if (strDistribFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            oDstrCrossTrace = new objdstrcrosstrace ();
        if (strShelfTrunkLineFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            oShelfCrossTrace = new objshlfcrosstrace ();
        if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            oAteCrossTrace = new objatecrosstrace ();
        if (strAteTrunkLineFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            oTrunkCrossTrace = new objtrunkcrosstrace ();

        // ... та їх данні
        dbdata oDirNrDBData = new dbdata (strSesnId);
        dbdata oDstrCrossTraceDBData = new dbdata (strSesnId);
        dbdata oLocLenTraceDBData = new dbdata (strSesnId);
        dbdata oLocExchTraceDBData = new dbdata (strSesnId);
        dbdata oLocLineTraceDBData = new dbdata (strSesnId);

        dbdata oEquipCrossTraceDBData = new dbdata (strSesnId);
        dbdata oShelfCrossTraceDBData = new dbdata (strSesnId);
        dbdata oAteCrossTraceDBData = new dbdata (strSesnId);
        dbdata oTrunkCrossTraceDBData = new dbdata (strSesnId);
        dbdata oLocationDBData = new dbdata (strSesnId);

        // ініціалізація додаткових данних об'ектів
        oDirNrDBData.setVal (isdbobj.COL_ID, strId);
        oDstrCrossTraceDBData.setVal (isdbobj.COL_ID, strId);
        oLocLenTraceDBData.setVal (isdbobj.COL_ID, strId);
        oLocExchTraceDBData.setVal (isdbobj.COL_ID, strId);
        oLocLineTraceDBData.setVal (isdbobj.COL_ID, strId);
        oEquipCrossTraceDBData.setVal (isdbobj.COL_ID, strId);
        oShelfCrossTraceDBData.setVal (isdbobj.COL_ID, strId);
        oAteCrossTraceDBData.setVal (isdbobj.COL_ID, strId);
        oTrunkCrossTraceDBData.setVal (isdbobj.COL_ID, strId);
        oLocationDBData.setVal (isdbobj.COL_ID, retrieve (objfirm.COL_STREET_ID, oDBData));

        oDirNr.select (oDirNrDBData);
        oLocLenTrace.select (oLocLenTraceDBData);
        oLocExchTrace.select (oLocExchTraceDBData);
        oLocLineTrace.select (oLocLineTraceDBData);

        oLocation.select (oLocationDBData);
        oEquipCrossTrace.select (oEquipCrossTraceDBData);
        if (strDistribFlag.equals (isdb.miscs.dclrs.YES))          // використовуеться режим?
            oDstrCrossTrace.select (oDstrCrossTraceDBData);
        if (strShelfTrunkLineFlag.equals (isdb.miscs.dclrs.YES))   // використовуеться режим?
            oShelfCrossTrace.select (oShelfCrossTraceDBData);
        if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))         // використовуеться режим?
            oAteCrossTrace.select (oAteCrossTraceDBData);
        if (strAteTrunkLineFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            oTrunkCrossTrace.select (oTrunkCrossTraceDBData);

        String strDetail = oDBData.getVal (PAR_DETAILINFO);

        // додаткові кнопки навігації
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_UPDATE))
        {
            buttondata oSelectButton = new buttondata ();
            oSelectButton.setName ("Вибрати");
            oSelectButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
            oSelectButton.setPar (isdb.miscs.dclrs.PAR_ID, isdb.miscs.dclrs.OBJ_NULL);
            oSelectButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oSelectButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oSelectButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_QUERY);
            oDBData.setButton (oSelectButton.getButton (oOutData));
        }
        else
        {
            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT));
            else
            {
                if (!oDBData.getVal (isdb.miscs.dclrs.PAR_ID).equals (isdb.miscs.dclrs.OBJ_NULL))
                {
                    buttondata oUpdateButton = new buttondata ();
                    oUpdateButton.setName ("Оновити");
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                    oUpdateButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_UPDATE);
                    oDBData.setButton (oUpdateButton.getButton (oOutData));
                }
            }
        }
        buttondata oDetailButton = new buttondata ();
        oDetailButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
        oDetailButton.setPar (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
        oDetailButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oDetailButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oDetailButton.setPar (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));

        // режим огляду: детальніше чи скороченно?
        if ((String.valueOf (strId) == "null") ||
                strId.equals (isdb.miscs.dclrs.OBJ_NULL));
        else
        {
            if ((String.valueOf (oDBData.getVal (PAR_DETAILINFO)) == "null") ||
                    oDBData.getVal (PAR_DETAILINFO).equals (isdb.miscs.dclrs.NO))
            {
                oDetailButton.setName ("Детальніше");
                oDetailButton.setPar (PAR_DETAILINFO, isdb.miscs.dclrs.YES);
                bDetail = false;
            }
            else
            {
                oDetailButton.setName ("Скорочено");
                oDetailButton.setPar (PAR_DETAILINFO, isdb.miscs.dclrs.NO);
                bDetail = true;
            }
            oDBData.setButton (oDetailButton.getButton (oOutData));
        }
        // формування параметрів форми для роботи з потрібними даними
        String strLink = "isdbproperty?" +
                         isdb.miscs.dclrs.PAR_DEPT + "=" + oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT) + "&" +
                         isdb.miscs.dclrs.PAR_APPL + "=dbreview" + "&" +
                         isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_CROSSMAP + "&" +
                         isdb.miscs.dclrs.PAR_REGIME + "=" + strRegime;
        if (bModify)            // якщо - режим огляду, то ідентиф-р не потрібен
        {
            if ((String.valueOf (strId) == "null") ||
                    strId.equals (isdb.miscs.dclrs.OBJ_NULL));
            else
                strLink += "&" + isdb.miscs.dclrs.PAR_ID + "=" + strId;
        }

        if (bModify)  // модіфікація даних?
            strDirNrTitle = isdb.ifaces.htmli.image ("phone.gif", 98, 57, "Номер телефона вибран для оновлення даних");
        else
            strDirNrTitle =
              isdb.ifaces.htmli.href (oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                      isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR),
                                      isdb.ifaces.htmli.image ("phone.gif", 98, 57, "Виберіть потрібний телефон"));

        if (bDetail)              // детальний огляд?
        {
            strLocation = oLocationDBData.getVal (objlocation.COL_LOCATION);
            if (String.valueOf (strLocation) != "null")
                strLocation = "за адресою: " + strLocation + ", " + oDirNrDBData.getVal (objdirnr.COL_HOUSE);
            if (String.valueOf (strLocation) != "null")
                strLocation = isdb.ifaces.htmli.value (strLocation);
            else
                strLocation = "";
        }

        // телефон
        String strDirNr =
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strDirNrTitle +
                                          isdb.ifaces.htmli.crlf () +
                                          isdb.ifaces.htmli.subtitle (oDirNr.getTitle ()), 33) +
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.table (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (
                          oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData) +
                          isdb.ifaces.htmli.crlf () +
                          strLocation
                          , 100)), 0), 100)), 1), 100));

        // Станційни дани
        String strLocCrossTrace =
          oLocLenTrace.value (objloclentrace.COL_LEN, oLocLenTraceDBData);
        if (bDetail)              // детальний огляд?
            strLocCrossTrace +=
              isdb.ifaces.htmli.crlf () +
              oLocLenTrace.notes (oLocLenTraceDBData);
        strLocCrossTrace +=
          oLocExchTrace.value (objlocexchmdftrace.COL_EXCHMDF, oLocExchTraceDBData);
        if (bDetail)              // детальний огляд?
            strLocCrossTrace +=
              isdb.ifaces.htmli.crlf () +
              oLocExchTrace.notes (oLocExchTraceDBData);
        strLocCrossTrace +=
          oLocLineTrace.value (objloclinemdftrace.COL_LINEMDF, oLocLineTraceDBData);
        if (bDetail)              // детальний огляд?
            strLocCrossTrace +=
              isdb.ifaces.htmli.crlf () +
              oLocLineTrace.notes (oLocLineTraceDBData);
        if (bModify)  // модіфікація даних?
            strTitle = "Оновлення даних станційного обладнання";
        else
            strTitle = "Виберіть потрібне станційне обладнання";
        String strExchange =
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.href (
                      oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                          isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TYPEEXCHCROSS),
                      isdb.ifaces.htmli.image ("5ess.gif", 101, 102, strTitle)) +
                    isdb.ifaces.htmli.crlf () +
                    isdb.ifaces.htmli.subtitle (
                      "Станційни дани " + strSwitch), 33) +
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.table (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (
                          strLocCrossTrace, 100)),
                      0),
                    100)
                ),
                1),
              100)
          );

        // Додаткове обладнання
        String strEquipCrossTrace =
          oEquipCrossTrace.value (
            objequipcrosstrace.COL_EQUIP, oEquipCrossTraceDBData);
        if (bDetail)              // детальний огляд?
            strEquipCrossTrace +=
              isdb.ifaces.htmli.crlf () +
              oEquipCrossTrace.notes (oEquipCrossTraceDBData);
        if (bModify)  // модіфікація даних?
            strTitle = "Оновлення даних додаткового обладнання";
        else
            strTitle = "Виберіть потрібне додаткове обладнання";
        strEquipCrossTrace =
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.href (
                      oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                          isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_EQUIPDIRNR),
                      isdb.ifaces.htmli.image ("modem.gif", 99, 80, strTitle)) +
                    isdb.ifaces.htmli.crlf () +
                    isdb.ifaces.htmli.subtitle (
                      oEquipCrossTrace.getTitle ()), 33) +
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.table (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (
                          strEquipCrossTrace, 100)), 0), 100)), 1), 100));

        // міжстанційні з'еднувальні лінії / з'еднувальні лінії
        String strTrunkCrossTrace = "";
        if (strAteTrunkLineFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
        {
            strTrunkCrossTrace =
              oTrunkCrossTrace.value (
                objtrunkcrosstrace.COL_TRUNKPAIR, oTrunkCrossTraceDBData);
            if (bDetail)              // детальний огляд?
                strTrunkCrossTrace +=
                  isdb.ifaces.htmli.crlf () +
                  oTrunkCrossTrace.notes (oTrunkCrossTraceDBData);
            if (bModify)  // модіфікація даних?
                strTitle = "Оновлення даних з'еднувальних лінії";
            else
                strTitle = "Виберіть потрібні з'еднувальни лінії";
            strTrunkCrossTrace =
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.href (
                          oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_ATETRUNKLINE),
                          isdb.ifaces.htmli.image ("phonelines.gif", 101, 98, strTitle)) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.subtitle (
                          oTrunkCrossTrace.getTitle ()), 33) +
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.table (
                          isdb.ifaces.htmli.row (
                            isdb.ifaces.htmli.cell (
                              strTrunkCrossTrace, 100)), 0), 100)), 1), 100));
        }

        // розподілення
        String strDstrCrossTrace = "";
        if (strDistribFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
        {
            strDstrCrossTrace =
              oDstrCrossTrace.value (
                objdstrcrosstrace.COL_DISTRIB, oDstrCrossTraceDBData);
            if (bDetail)              // детальний огляд?
                strDstrCrossTrace +=
                  isdb.ifaces.htmli.crlf () +
                  oDstrCrossTrace.notes (oDstrCrossTraceDBData);
            if (bModify)  // модіфікація даних?
                strTitle = "Оновлення даних розподілу";
            else
                strTitle = "Виберіть потрібний розподіл";
            strDstrCrossTrace =
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.href (
                          oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DISTRIB),
                          isdb.ifaces.htmli.image ("distrib.gif", 132, 54, strTitle)) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.subtitle (
                          oDstrCrossTrace.getTitle ()), 33) +
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.table (
                          isdb.ifaces.htmli.row (
                            isdb.ifaces.htmli.cell (
                              strDstrCrossTrace, 100)
                          ),
                          0),
                        100)
                    ),
                    1),
                  100)
              );
        }

        // шафа
        String strShelfCrossTrace = "";
        if (strShelfTrunkLineFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
        {
            strShelfCrossTrace =
              oShelfCrossTrace.value (
                objshlfcrosstrace.COL_TRUNKLINE, oShelfCrossTraceDBData);
            if (bDetail)              // детальний огляд?
                strShelfCrossTrace +=
                  isdb.ifaces.htmli.crlf () +
                  oShelfCrossTrace.notes (oShelfCrossTraceDBData);
            if (bModify)  // модіфікація даних?
                strTitle = "Оновлення даних розподільних шаф";
            else
                strTitle = "Виберіть потрібні дані розподільних шаф";
            strShelfCrossTrace =
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.href (
                          oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SHELFTRUNKLINE),
                          isdb.ifaces.htmli.image ("shelf.gif", 59, 100, strTitle)) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.subtitle (
                          oShelfCrossTrace.getTitle ()), 33) +
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.table (
                          isdb.ifaces.htmli.row (
                            isdb.ifaces.htmli.cell (
                              strShelfCrossTrace, 100)), 0), 100)), 1), 100));
        }

        // Крос віддаленої АТС
        String strAteCrossTrace = "";
        if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
        {
            strAteCrossTrace =
              oAteCrossTrace.value (objatecrosstrace.COL_CROSS, oAteCrossTraceDBData);
            if (bDetail)              // детальний огляд?
                strAteCrossTrace +=
                  isdb.ifaces.htmli.crlf () +
                  oAteCrossTrace.notes (oAteCrossTraceDBData);
            if (bModify)  // модіфікація даних?
                strTitle = "Оновлення даних кроса віддаленої АТС";
            else
                strTitle = "Виберіть потрібні дані кроса віддаленої АТС";
            strAteCrossTrace =
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.href (oOutData.getOutStream ().encodeUrl (strLink + "&" +
                                                isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_ATECROSSPAIR),
                                                isdb.ifaces.htmli.image ("ate.gif", 159, 100, strTitle)) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.subtitle (
                          oAteCrossTrace.getTitle ()), 33) +
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.table (
                          isdb.ifaces.htmli.row (
                            isdb.ifaces.htmli.cell (
                              strAteCrossTrace, 100)), 0), 100)), 1), 100));
        }

        // повернення підготовленої форми
        return

          // телефон
          strDirNr +

          // Станційни дани
          strExchange +

          // додаткове обладнання
          strEquipCrossTrace +

          // АТС
          strAteCrossTrace +

          // (міжстанційні) з'еднувальні лінії
          strTrunkCrossTrace +

          // шафа
          strShelfCrossTrace +

          // розподілення
          strDstrCrossTrace;
    }

    /**
     * Приготування графіків
     * @param oDBData поточни дані об'екта
     * @return сформована HTML сторінка з аплетом для формування діаграми.
     */
    public String graph (dbdata oDBData)
    {
        String strGraph = null;
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            String strObject = null;
            if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_EWSD))      // яка використовуеться станція?
                strObject = isdb.miscs.dclrs.OBJ_ATETRUNKLINE;
            else
                strObject = isdb.miscs.dclrs.OBJ_ATECROSSPAIR;

            // будова об'екта
            isdbobj oCross = null;
            try
            {
                oCross = (isdb.objs.isdbobj) Class.forName ("isdb.objs.obj" + strObject).newInstance ();
            }
            catch (ClassNotFoundException ex)
            { ex.printStackTrace (); }
            catch (ClassCastException ex)
            { ex.printStackTrace (); }
            catch (IllegalAccessException  ex)
            { ex.printStackTrace (); }
            catch (InstantiationException ex)
            { ex.printStackTrace (); }

            dbdata oCrossDBData = new dbdata (oDBData.getSession ());
            oCrossDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, strObject);
            oCrossDBData.setVal (isdb.miscs.dclrs.PAR_NEXTOBJECT, isdb.miscs.dclrs.OBJ_CROSSMAP);
            oCrossDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oCross.describe (oCrossDBData);

            graphi oCrossGraph = new graphi (oDBData);
            oCrossGraph.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_EWSD))      // яка використовуеться станція?
                hashVals = dbi.getListObj (oDBData.getSession (),
                                           hashVals, "select distinct ATE_ID, ATE from ATES, ATETRUNKLINES, DIRNRS" +
                                           " where ATES.ID = ATE_ID and" +
                                           " DIRNRS.ID = DIRNR_ID" +
                                           " order by ATE");
            else
                hashVals = dbi.getListObj (oDBData.getSession (),
                                           hashVals, "select distinct ATE_ID, ATE from ATES, ATECROSSPAIRS, DIRNRS" +
                                           " where ATES.ID = ATE_ID and" +
                                           " DIRNRS.ID = DIRNR_ID" +
                                           " order by ATE");
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oCrossGraph.addPieChartElement ((String) hashVals.get (strKey),
                                                oCross.countItems (oCrossDBData, "ATE_ID=" + strKey),
                                                oCross.setURLObj (oCrossDBData,
                                                                  "ATE_ID=" + strKey,
                                                                  isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_CROSSMAP + "&" +
                                                                  isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE));
            }
            strGraph = oCrossGraph.getApplet ();
        }
        return strGraph;
    }

    /**
     * Чи е головна кнопка керування у об'екта
     * @param oDBData поточни дані об'екта
     * @return true - немає
     */
    public boolean isSubmited (dbdata oDBData)
    {
        return true;
    }
}

