/**
 * htmli.java
 * ISDBj
 */

package isdb.ifaces;

/**
 * Інтерфейс формування HTML сторінок.
 * @version 1.0 final, 10-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class htmli
{
    /** Поточний фонт відображення */
    private static String strFont = cfgi.getOption (isdb.miscs.dclrs.OPT_FONT);
    /** Поточний кольор фону   */
    private static String strColorBg = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
    /** Поточний кольор назви форми   */
    private static String strColorTitle = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TITLE);
    /** Поточний кольор фону назви форми */
    private static String strColorBgTitle = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
    /** Поточний кольор відображаемого текста   */
    private static String strColorText = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
    /** Поточний кольор форм   */
    private static String strColorForm = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
    /** Поточний кольор заголовків форм   */
    private static String strColorHead = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
    /** Поточний кольор заголовків форм   */
    private static String strColorLink = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_LINK);
    /** Поточний кольор фонових шпалер   */
    private static String strPictureBG = cfgi.getOption (isdb.miscs.dclrs.OPT_PICTURE_BG);
    /** Використовуєме HTML кодування  */
    private static String strHTMLCharSet = cfgi.getOption (isdb.miscs.dclrs.OPT_HTMLCHARSET);

    /**
     * Повкрнення поточного кольору заголовків форм.
     * @return поточний кольор заголовків форм
     */
    public static String getColorHead ()
    {
        return strColorHead;
    }

    /**
     * Повкрнення поточного кольору відображаемого текста.
     * @return поточний кольор відображаемого текста
     */
    public static String getColorText ()
    {
        return strColorText;
    }

    /**
     * Формування HTML сторінки.
     * <P><B>Увага!</B><P>В цьому методі ініціалізуються первинні параметри відображення сторінок, форм, тощо.
     * @param strTitle назва сторінки
     * @param strBody тіло сторінки
     * @param strJSBody використовуемі JavaScript функції
     * @return сформована HTML сторінка
     */
    public static String page (String strTitle, String strBody, String strJSBody)
    {
        strFont = cfgi.getOption (isdb.miscs.dclrs.OPT_FONT);
        strColorBg = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
        strColorTitle = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TITLE);
        strColorBgTitle = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
        strColorText = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
        strColorForm = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
        strColorHead = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
        strColorLink = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_LINK);
        strPictureBG = cfgi.getOption (isdb.miscs.dclrs.OPT_PICTURE_BG);
        String strPageBody = "<HTML><HEAD>" +

                             // <meta http-equiv="refresh" content="1"
                             // URL="http://mysite/servlet/mysearch?target=patterns%in%java&options=verbose">

                             "<META http-equiv=\"Content-Type\" content=\"text/html;charset=" + strHTMLCharSet + "\">" +
                             "<TITLE>" + strTitle + "</TITLE>";
        if (String.valueOf (strJSBody) != "null")
            strPageBody += strJSBody;
        return (strPageBody += "</HEAD>" +
                               strBody +
                               "</HTML>");
    }

    /**
     * Формування HTML сторінки (в форматі TEXT).
     * @param strBody тіло сторінки
     * @return сформована HTML сторінка
     * @see #page (String, String, String)
     */
    public static String page (String strBody)
    {
        return page (null, strBody, null);
    }

    /**
     * Формування HTML сторінки.
     * @param strTitle назва сторінки
     * @param strBody тіло сторінки
     * @see #page (String, String, String)
     */
    public static String page (String strTitle, String strBody)
    {
        return page (strTitle, strBody, null);
    }

    /**
     * Формування тіла HTML сторінки.
     * @param strDept назва відділу
     * @param strLogo лого
     * @param strUrlInfo інформація стосовно лого
     * @param strBody тіло сторінки
     * @param strJSCall виклик JavaScript функцій
     * @return сформоване тіло HTML сторінки
     */
    public static String body (String strDept, String strLogo, String strUrlInfo, String strBody, String strJSCall)
    {
        String strTmpFont = strFont;
        String strTmpColorBg = strColorBg;
        String strTmpColorText = strColorText;
        String strTmpPictureBG = strPictureBG;

        if (String.valueOf (strUrlInfo) != "null")    // нема шапки?
        {
            if (String.valueOf (strTmpPictureBG) != "null")
            {
                if (String.valueOf (strTmpPictureBG) != "")   // є шпалери?
                    strTmpPictureBG = "BACKGROUND=\"../../../images/" + strPictureBG + "\" ";
            }
            if (String.valueOf (strLogo) != "null")
                strBody =
                  div (
                    table (
                      row (
                        cell (
                          href (strUrlInfo, strLogo), 30) +
                        cell (
                          "<FONT COLOR=\"#00FFFF\" SIZE=\"5\" FACE=\"" + strFont + "\">" + isdb.miscs.dclrs.ISDB_SHORTNAME + ":</FONT></P>" +
                          "<P ALIGN=\"center\"><FONT COLOR=\"#00FFFF\" SIZE=\"5\" FACE=\"" + strFont + "\">" + strDept + "</FONT></P>", 70)), 0, false)) +
                  strBody;
        }
        else
        {
            if (String.valueOf (strDept) == "null")    // нема відділу?
            {
                strTmpColorText = "330033"; // звіти відображаються чорним ...
                strTmpColorBg = "FFFFFF";   // ... на білому
                strTmpPictureBG = "";
            }
        }
        if (String.valueOf (strJSCall) == "null")  // використовуються JavaScript функції?
            strJSCall = "";
        else
            strJSCall = " " + strJSCall;

        if (String.valueOf (strTmpFont) == "null") // встановлен загальний фонт?
            strTmpFont = "";
        else
            strTmpFont = " STYLE=\"font-family: " + strTmpFont + "\" ";
        return "<BODY " + strTmpPictureBG + "BGCOLOR=\"#" + strTmpColorBg + "\" TEXT=\"#" + strTmpColorText + "\" " +
               strTmpFont +
               "LINK=\"#" + strColorLink + "\" VLINK=\"#" + strColorLink + "\" ALINK=\"#" + strColorLink + "\"" + strJSCall + ">" + strBody + "</BODY>";
    }

    /**
     * Формування тіла HTML сторінки.
     * @param strDept назва відділу
     * @param strLogo лого
     * @param strBody тіло сторінки
     * @return сформоване тіло HTML сторінки
     * @see #body (String, String, String, String, String)
     */
    public static String body (String strDept, String strLogo, String strBody)
    {
        return body (strDept, strLogo, null, strBody, null);
    }

    /**
      * Формування тіла HTML сторінки (для звітів).
      * @param strBody тіло сторінки
      * @param strJSCall виклик JavaScript функцій
      * @return сформоване тіло HTML сторінки
      * @see #body (String, String, String, String, String)
      */
    public static String body (String strBody, String strJSCall)
    {
        return body (null, null, null, strBody, strJSCall);
    }

    /**
     * Звіт про помилку.
     * @param strErrMsg помилковий звіт
     * @return сформований помилковий звіт
     */
    public static String error (String strErrMsg)
    {
        return "<H2><FONT COLOR=#FF0080><U>" +
               isdb.ifaces.htmli.div ("Увага!") +
               "</U>" +
               crlf (2) +
               "<I>" +
               hline () +
               isdb.ifaces.htmli.div (strErrMsg) +
               hline () +
               "</I></FONT></H2>" + crlf ();
    }

    /**
     * Центрування текста.
     * @return відцентрований текст
     */
    public static String center (String strValue)
    {
        return "<CENTER>" + strValue + "</CENTER>";
    }

    /**
     * Підготовлення розміра та кольору фонту відображення.
     * @param strValue значення для відображення
     * @param strColor кольор відображення
     * @param iSize розмір відображення
     * @return підготовлений текст
     */
    public static String font (String strValue, String strColor, int iSize)
    {
        if (String.valueOf (strColor) == "null")
            strColor = "";
        else
            strColor = "COLOR=\"" + strColor + "\" ";
        return "<FONT " + strColor + "SIZE=\"" + java.lang.String.valueOf (iSize) + "\">" +
               strValue +
               "</FONT>";
    }

    /**
     * Підготовлення розміра та кольору фонту відображення.
     * @param strValue значення для відображення
     * @param iSize розмір відображення
     * @return підготовлений текст
     * @see #font (String, String, int)
     */
    public static String font (String strValue, int iSize)
    {
        return font (strValue, null, iSize);
    }

    /**
     * Підготовлення розміра та кольору фонту відображення.
     * @param strValue значення для відображення
     * @param strColor кольор відображення
     * @return підготовлений текст
     * @see #font (String, String, int)
     */
    public static String font (String strValue, String strColor)
    {
        return font (strValue, strColor, 3);
    }

    /**
     * Формування заголовку.
     * @param strTitle заголовок
     */
    public static String title (String strTitle)
    {

        return "<FONT COLOR=\"#" + strColorTitle + "\" SIZE=\"5\">" + center (strTitle) + "</FONT>";
    }

    /**
     * Відмічений шріфт.
     * @param strValue значення для перетворення
     * @return перетворуеме значення
     */
    public static String bold (String strValue)
    {
        return "<B>" + strValue + "</B>";
    }

    /**
     * Назва (заголовок) документа.
     * @param strValue заголовок документа
     * @param strColor колір заголовку
     * @return сформований заголовок документа
     */
    public static String subtitle (String strValue, String strColor)
    {
        if (String.valueOf (strColor) == "null")
            strColor = strColorHead;
        return bold ("<I><FONT COLOR=\"#" + strColor + "\">" + strValue + "</FONT></I>");
    }

    /**
     * Назва (заголовок) документа.
     * @param strValue заголовок документа
     * @return сформований заголовок документа
     * @see #subtitle (String, String)
     */
    public static String subtitle (String strValue)
    {
        return subtitle (strValue, null);
    }

    /**
     * Секція документа.
     * @param strValue тіло для формування
     * @param trAlign вирівнювання секції
     * @return сформована секція документа
     */
    public static String div (String strValue, String strAlign)
    {
        if (strAlign.equalsIgnoreCase ("center"))
            strValue = center (strValue);
        return "<DIV ALIGN=\"" + strAlign + "\">" + strValue + "</DIV>";
    }

    /**
     * Секція документа.
     * @param strValue тіло для формування
     * @return сформована секція документа
     * @see #div (String, String)
     */
    public static String div (String strValue)
    {
        return div (strValue, "center");
    }

    /**
     * Таблиця документа.
     * @param strValue тіло таблицы для формування
     * @param iWidth розмыр бордюра
     * @param bBGColorSet колір поточної таблиці
     * @return сформована таблиця документа
     */
    public static String table (String strValue, int iWidth, boolean bBGColorSet)
    {
        String strBGColorTable = "";
        if (bBGColorSet)
            strBGColorTable = "BGCOLOR=\"#" + strColorForm + "\" ";
        return "<TABLE BORDER=\"" + java.lang.String.valueOf (iWidth) + "\" " +
               strBGColorTable +
               "WIDTH=\"100%\">" + strValue + "</TABLE>";
    }

    /**
     * Таблиця документа.
     * @param strValue тіло таблицы для формування
     * @param iWidth розмыр бордюра
     * @return сформована таблиця документа
     */
    public static String table (String strValue, int iWidth)
    {
        return table (strValue, iWidth, true);
    }

    /**
     * Таблиця документа.
     * @param strValue тіло таблицы для формування
     * @param bBGColorSet колір поточної таблиці
     * @return сформована таблиця документа
     */
    public static String table (String strValue, boolean bBGColorSet)
    {
        return table (strValue, 0, bBGColorSet);
    }

    /**
     * Секція таблиці документа.
     * @param strCellValue значення ячейки
     * @param iWidth розмыр бордюра
     * @param bBGColorSet колір поточної таблиці
     * @return сформована ячейка таблиці
     */
    public static String place (String strCellValue, int iWidth, boolean bBGColorSet)
    {
        return div (table (strCellValue, iWidth, bBGColorSet));
    }

    /**
     * Секція таблиці документа.
     * @param strCellValue значення ячейки
     * @param iWidth розмыр бордюра
     * @return сформована ячейка таблиці
     * @see #place (String, int, boolean)
     */
    public static String place (String strCellValue, int iWidth)
    {
        return div (table (strCellValue, iWidth, true));
    }

    /**
     * Секція таблиці документа.
     * @param iWidth розмыр бордюра
     * @param bBGColorSet колір поточної таблиці
     * @return сформована ячейка таблиці
     * @see #place (String, int, boolean)
     */
    public static String place (String strCellValue, boolean bBGColorSet)
    {
        return div (table (strCellValue, 0, bBGColorSet));
    }

    /**
     * Горізонтальна ячейка в таблиці.
     * @param strValue значення ячейки
     * @return сформована ячейка таблиці
     */
    public static String row (String strValue)
    {
        return "<TR VALIGN=TOP>" +
               strValue +
               "</TR>";
    }

    /**
     * Вертікальна ячейка в таблиці.
     * @param strValue значення ячейки
     * @param iWidth розмір в відсотках
     * @param strAlign вирівнювання ячейки
     * @return сформована ячейка таблиці
     */
    public static String cell (String strValue, int iWidth, String strAlign)
    {
        String strTmp = "<TD ALIGN=\"" + strAlign + "\"";
        if (iWidth != 0)
            strTmp += " WIDTH=\"" + java.lang.String.valueOf (iWidth) + "%\"";
        return strTmp + ">" + strValue + "</TD>";
    }

    /**
     * Вертікальна ячейка в таблиці.
     * @param strValue значення ячейки
     * @return сформована ячейка таблиці
     * @see #cell (String, int, String)
     */
    public static String cell (String strValue)
    {
        return cell (strValue, 0, "center");
    }

    /**
     * Вертікальна ячейка в таблиці.
     * @param strValue значення ячейки
     * @param strAlign вирівнювання ячейки
     * @return сформована ячейка таблиці
     * @see #cell (String, int, String)
     */
    public static String cell (String strValue, String strAlign)
    {
        return cell (strValue, 0, strAlign);
    }

    /**
     * Вертікальна ячейка в таблиці.
     * @param strValue значення ячейки
     * @param iWidth розмір в відсотках
     * @return сформована ячейка таблиці
     * @see #cell (String, int, String)
     */
    public static String cell (String strValue, int iWidth)
    {
        return cell (strValue, iWidth, "center");
    }

    /**
     * TEXTAREA поле форми для редагування.
     * @param strFieldName назва поля
     * @param strValue значення поля
     * @param strJSCall JavaScript функція
     * @return сформоване TEXTAREA поле форми
     */
    public static String textarea (String strFieldName, String strValue, String strJSCall)
    {
        if (strValue == null)
            strValue = "";
        if (strJSCall == null)
            strJSCall = "";
        return "<TEXTAREA NAME=\"" + strFieldName + "\" ROWS=\"3\" COLS=\"35\" " + strJSCall + ">" + strValue + "</TEXTAREA>";
    }

    /**
     * TEXTAREA поле форми для редагування
     * @param strFieldName назва поля
     * @param strValue значення поля
     * @return сформоване TEXTAREA поле форми
     * @see #textarea (String, String, String)
     */
    public static String textarea (String strFieldName, String strValue)
    {
        return textarea (strFieldName, strValue, null);
    }

    /**
     * HTML форма
     * @param strEncUrl	URL для переходу
     * @param strBody додаткови поля форми
     * @param strTitle лейба на пімпі кнопки форми
     * @param strJSCall JavaScript функці(я/ї)
     * @param bHideButton true - потрібно скрити кнопку
     * @return підготовлена HTML форма
     */
    public static String form (String strEncUrl, String strBody, String strTitle, String strJSCall, boolean bHideButton)
    {
        String strTypeAct = null;

        if (String.valueOf (strJSCall) == "null")
            strJSCall = "";
        else
            strJSCall = " " + strJSCall;
        if (!bHideButton)                   // потрібно скрити кнопку?
            strTypeAct = strTitle;
        if (String.valueOf (strTypeAct) != "null")
            strTypeAct = "<INPUT TYPE=\"submit\" VALUE=\"" + strTypeAct + "\">";
        else
            strTypeAct = "";
        return "<FORM ACTION=\"" + strEncUrl + "\" METHOD=\"POST\"" + strJSCall + ">" +
               strBody +
               strTypeAct +
               "</FORM>";
    }

    /**
     * HTML форма.
     * @param strEncUrl URL для переходу
     * @param strBody додаткови поля форми
     * @return підготовлена HTML форма
     * @see #form (String, String, String, String, boolean)
     */
    public static String form (String strEncUrl, String strBody)
    {
        return form (strEncUrl, strBody, null, null, false);
    }

    /**
     * HTML форма.
     * @param strEncUrl URL для переходу
     * @param strBody додаткови поля форми
     * @param strRegime режим виконання
     * @return підготовлена HTML форма
     * @see #form (String, String, String, String, boolean)
     */
    public static String form (String strEncUrl, String strBody, String strRegime)
    {
        return form (strEncUrl, strBody, strRegime, null, false);
    }

    /**
     * HTML форма.
     * @param strEncUrl	URL для переходу
     * @param strBody додаткови поля форми
     * @param strRegime режим виконання
     * @param strJSCall JavaScript функці(я/ї)
     * @return підготовлена HTML форма
     * @see #form (String, String, String, String, boolean)
     */
    public static String form (String strEncUrl, String strBody, String strRegime, String strJSCall)
    {
        return form (strEncUrl, strBody, strRegime, strJSCall, false);
    }

    /**
     * Скрите поле форми HTML.
     * @param strName назва поля
     * @param strValue значення для поля
     * @return сформоване скрите поле
     */
    public static String formhidepar (String strName, String strValue)
    {
        return "<INPUT TYPE=\"hidden\" NAME=\"" + strName + "\" VALUE=\"" + strValue + "\">";
    }

    /**
     * Додаткова кнопка форми HTML.
     * @param strName назва кнопки
     * @param strValue напис на кнопці
     * @param strJSCall виклик JavaScript функцій
     * @return сформована кнопка
     */
    public static String formbuttonpar (String strName, String strValue, String strJSCall)
    {
        if (strJSCall == null)
            strJSCall = "";
        return "<INPUT TYPE=\"button\" NAME=\"" + strName + "\" VALUE=\"" + strValue + "\" " + strJSCall + ">";
    }

    /**
     * Додаткова кнопка форми HTML.
     * @param strName назва кнопки
     * @param strValue напис на кнопці
     * @return сформована кнопка
     * @see #formbuttonpar (String, String, String)
     */
    public static String formbuttonpar (String strName, String strValue)
    {
        return formbuttonpar (strName, strValue, null);
    }

    /**
     * Додаткова кнопка очистки форми HTML.
     * @return сформована кнопка очистки
     */
    public static String formclearbuttonpar ()
    {
        return "<INPUT TYPE=\"reset\" VALUE=\"Очистити\">" + "   ";
    }

    /**
     * Радіо-кнопкаcheckbox форми HTML.
     * @param strName назва пімпи
     * @param strValue значення пімпи
     * @param strTitle напис на пімпі
     * @param bChecked true - включена пімпа, false - ні
     * @return сформована пімпа
     */
    public static String formcheckboxpar (String strName, String strValue, String strTitle, boolean bChecked)
    {
        if (String.valueOf (strTitle) == "null")
            strTitle = "";
        else strTitle = " " + strTitle;
        String strChecked = "";
        if (bChecked)
            strChecked = "CHECKED ";
        return div ("<INPUT TYPE=\"checkbox\" NAME=\"" + strName + "\" " + strChecked + "VALUE=\"" + strValue + "\">" + strTitle, "left");
    }

    /**
     * Радіо-кнопкаcheckbox форми HTML.
     * @param strName назва пімпи
     * @param strValue значення пімпи
     * @param strTitle напис на пімпі
     * @return сформована пімпа
     * @see #formcheckboxpar (String, String, String, boolean)
     */
    public static String formcheckboxpar (String strName, String strValue, String strTitle)
    {
        return formcheckboxpar (strName, strValue, strTitle, false);
    }

    /**
     * Радіо-кнопкаcheckbox форми HTML.
     * @param strName назва пімпи
     * @param strValue напис на пімпі
     * @param bChecked true - включена пімпа, false - ні
     * @return сформована пімпа
     * @see #formcheckboxpar (String, String, String, boolean)
     */
    public static String formcheckboxpar (String strName, String strValue, boolean bChecked)
    {
        return formcheckboxpar (strName, strValue, null, bChecked);
    }

    /**
     * Радіо-кнопкаcheckbox форми HTML.
     * @param strName назва пімпи
     * @param strValue напис на пімпі
     * @return сформована пімпа
     * @see #formcheckboxpar (String, String, String, boolean)
     */
    public static String formcheckboxpar (String strName, String strValue)
    {
        return formcheckboxpar (strName, strValue, null, false);
    }

    /**
     * Радіо-кнопкаcheckbox форми HTML.
     * @param strName назва пімпи
     * @return сформована пімпа
     * @see #formcheckboxpar (String, String, String, boolean)
     */
    public static String formcheckboxpar (String strName)
    {
        return formcheckboxpar (strName, "1", null, false);
    }

    /**
     * Радіо-кнопкаcheckbox "немає в списку" форми HTML.
     * @param strName назва об'екта, запис якого потрыбно створити
     * @param strTitle назва checkbox
     * @return сформований checkbox "немає в списку"
     */
    public static String getCheckBoxAbsentInList (String strName, String strTitle)
    {
        return formcheckboxpar (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + strName, "1", strTitle);
    }

    /**
     * Радіо-кнопкаcheckbox "немає в списку" форми HTML.
     * @param strName назва об'екта, запис якого потрыбно створити
     * @return сформований checkbox "немає в списку"
     * @see #getCheckBoxAbsentInList (String, String)
     */
    public static String getCheckBoxAbsentInList (String strName)
    {
        return getCheckBoxAbsentInList (strName, isdb.miscs.dclrs.RPT_IS_NOT_LIST);
    }

    /**
     * Радіо-кнопка(checkbox) "редагувати" форми HTML.
     * @param strName назва об'екта, запис якого потрібно оновляти
     */
    public static String getCheckBoxUpdateRecord (String strName)
    {
        return formcheckboxpar (isdb.miscs.dclrs.PAR_UPDATE_RECORD + strName, "1", "редагувати");
    }

    /**
     * Радіо-кнопка(checkbox) "друкувати" форми HTML.
     * @param strNameObj назва об'екта
     * @param strRptId ідентифікатор звіта об'екта
     * @param bEnable включена радіо-кнопка(true) чі ні
     * @return сформована радіо-кнопка"друкувати"
     */
    public static String getCheckBoxPrintRpt (String strNameObj, String strRptId, boolean bEnable)
    {
        return formcheckboxpar (isdb.miscs.dclrs.PAR_PRINT_REPORT + strNameObj, strRptId, isdb.miscs.dclrs.RPT_PRINT_REPORT, bEnable);
    }

    /**
     * Радіо-кнопка(checkbox) "друкувати" форми HTML.
     * <P>По замовчанню - включена.
     * @param strNameObj назва об'екта
     * @param strRptId ідентифікатор звіта об'екта
     * @return сформована радіо-кнопка"друкувати"
     * @see #getCheckBoxPrintRpt (String, String, boolean)
     */
    public static String getCheckBoxPrintRpt (String strNameObj, String strRptId)
    {
        return getCheckBoxPrintRpt (strNameObj, strRptId, true);
    }

    /**
     * Поле для введення пвролю форми HTML.
     * @param strName назва поля
     * @param strSize розмір поля
     * @param strJSCall використовуема JavaScript функція
     * @return сформоване поле для введення пвролю
     */
    public static String formpswdpar (String strName, String strSize, String strJSCall)
    {
        if (String.valueOf (strJSCall) == "null")
            strJSCall = "";
        return "<INPUT TYPE=\"password\" SIZE=\"" + strSize + "\" NAME=\"" + strName + "\" " + strJSCall + ">";
    }

    /**
      * Поле для введення пвролю форми HTML.
      * @param strName назва поля
      * @param strSize розмір поля
      * @return сформоване поле для введення пвролю
      * @see #formpswdpar (String, String)
      */
    public static String formpswdpar (String strName, String strSize)
    {
        return formpswdpar (strName, strSize);
    }

    /**
     * Поле для введення інформації у формі HTML.
     * @param strName назва поля
     * @param strValue значення для поля
     * @param strSize розмір поля
     * @param strJSCall використовуема JavaScript функція
     * @return сформоване поле
     */
    public static String formtextpar (String strName, String strValue, String strSize, String strJSCall)
    {
        if (String.valueOf (strValue) == "null")
            strValue = "";
        if (String.valueOf (strJSCall) == "null")
            strJSCall = "";
        return "<INPUT TYPE=\"text\" NAME=\"" + strName +
               "\" VALUE=\"" + strValue +
               "\" SIZE=\"" + strSize + "\" " + strJSCall + ">";
    }

    /**
     * Поле для введення інформації у формі HTML.
     * @param strName назва поля
     * @param strValue значення для поля
     * @param strSize розмір поля
     * @return сформоване поле
     * @see #formtextpar (String, String, String, String)
     */
    public static String formtextpar (String strName, String strValue, String strSize)
    {
        return formtextpar (strName, strValue, strSize, null);
    }

    /**
     * Радіопараметр форми HTML.
     * Для внутрішнього використання
     * @param strName назва радіопараметра
     * @param strValue значення для радіопараметра
     * @param bChecked вибране поле
     * @return сформований радіопараметр
     */
    public static String formradiopar (String strName, String strValue, boolean bChecked)
    {
        String strChecked = " ";
        if (bChecked)
            strChecked = " CHECKED ";
        else
            strChecked = " ";
        return "<INPUT TYPE=\"radio\"" + strChecked + "NAME=\"" + strName + "\" ALIGN=LEFT VALUE=\"" + strValue + "\">";
    }

    /**
     * Радіопараметр форми HTML.
     * @param strName назва радіопараметра
     * @param strValue значення для радіопараметра
     * @return сформований радіопараметр
     * @see #formradiopar (String, String, boolean)
     */
    public static String formradiopar (String strName, String strValue)
    {
        return formradiopar (strName, strValue, false);
    }

    /**
     * Значення списку для послідуючої вибірки (тег OPTION HTML).
     * @param strName ключ значення
     * @param strValue відображаеме поле значення
     * @param bSelected true - якщо вибрано
     * @return сформоване значення списку
     */
    public static String select_option (String strName, String strValue, boolean bSelected)
    {
        String strSelected = "";
        if (bSelected) strSelected = "SELECTED ";
        return "<OPTION " + strSelected + "VALUE=\"" + strName + "\">" +
               strValue + "</OPTION>";
    }

    /**
     * Значення списку для послідуючої вибірки (тег OPTION HTML).
     * @param strName ключ значення
     * @param strValue відображаеме поле значення
     * @return сформоване значення списку
     * @see #select_option (String, String, boolean)
     */
    public static String select_option (String strName, String strValue)
    {
        return select_option (strName, strValue, false);
    }

    /**
     * Відображення HTML списку значеннь (тег SELECT HTML).
     * @param strName назва поля
     * @param strValues значення для відображення
     * @param strJSCall виклик JavaScript функцій
     * @param bMultiple true - якщо це список з можливостью
     * багаторазової вібірки (за допомогою додаткових кнопок Shift та Ctrl)
     * @return список значень в HTML форматі
     */
    public static String select (String strName, String strValues, String strJSCall, boolean bMultiple)
    {
        String strInfo = "";
        String strMultiple = "";
        if (bMultiple)     // список з можливостью багатократної вібірки?
        {
            strMultiple = " MULTIPLE";
            strInfo = crlf () + font (bold ("Shift") + " - багатораз. вибір," + crlf () + bold ("Ctrl") + " - однораз. вибір", -2);
        }
        if (String.valueOf (strJSCall) == "null")
            strJSCall = "";
        else
            strJSCall = " " + strJSCall;
        return "<SELECT NAME=\"" + strName + "\"" + strMultiple + strJSCall + ">" +
               strValues +
               "</SELECT>" +
               div (strInfo, "left");
    }

    /**
     * Відображення HTML списку значеннь (тег SELECT HTML).
     * @param strName назва поля
     * @param strValues значення для відображення
     * @return список значень в HTML форматі
     * @see #select (String, String, String, boolean)
     */
    public static String select (String strName, String strValue)
    {
        return select (strName, strValue, null, false);
    }

    /**
     * Відображення попереджувального звіту користувачу.
     * @param strMsgMain головне повідомлення
     * @param strMsgCmpl додаткове повідомлення
     * @return сформований попереджувальний звіт
     */
    public static String msg (String strMsgMain, String strMsgCmpl)
    {
        String strTmpMsg = "<FONT SIZE=\"4\" COLOR=#FF0000><B><CENTER>" + strMsgMain + "</CENTER><BR>";
        if (String.valueOf (strMsgCmpl) != "null")
            strTmpMsg += "<CENTER>" + strMsgCmpl;
        return strTmpMsg += "</CENTER></B></FONT>";
    }

    /**
     * Відображення попереджувального звіту користувачу.
     * @param strMsgMain головне повідомлення
     * @return сформований попереджувальний звіт
     * @see #msg (String, String)
     */
    public static String msg (String strMsgMain)
    {
        return msg (strMsgMain, null);
    }

    /**
     * Відображення картінки (тег IMG HTML).
     * @param strImgFile назва графічного файла
     * @param iWidth шірина
     * @param iHeight висота
     * @param strAltText альтернативний текст при відображенні
     * @return сформована графічна картінка
     */
    public static String image (String strImgFile, int iWidth, int iHeight, String strAltText)
    {
        return "<IMG BORDER=0 SRC=\"../../../images/" + strImgFile + "\" WIDTH=\"" + java.lang.String.valueOf (iWidth) + "\" HEIGHT=\"" + java.lang.String.valueOf (iHeight) + "\" ALT=\"" + strAltText + "\"></A>";
    }

    /**
     * Відображення потрібного значення.
     * @param strValue відображаєме значення
     * @param strColor колір для відображення (в HTML RGB нотації)
     * @param bCenterAlign вирівнювання значення (true - по центру)
     * @return сформоване значення для відображення
     */
    public static String value (String strValue, String strColor, boolean bCenterAlign)
    {
        String strVal = "<FONT COLOR=#" + strColor + ">" +
                        bold (strValue) +
                        "</FONT>";
        if (bCenterAlign)     // вирівнювання по центру?
            strVal = center (strVal);
        return strVal;
    }

    /**
     * Відображення потрібного значення.
     * @param strValue відображаєме значення
     * @param strColor колір для відображення (в HTML RGB нотації)
     * @return сформоване значення для відображення
     * @see #value (String, String, boolean)
     */
    public static String value (String strValue, String strColor)
    {
        return value (strValue, strColor, true);
    }

    /**
     * Відображення потрібного значення в білому кольорі.
     * @param strValue відображаєме значення
     * @return сформоване значення для відображення
     * @see #value (String, String, boolean)
     */
    public static String value (String strValue)
    {
        return value (strValue, "FFFFFF", true);
    }

    /**
     * URL гіперзв'язок.
     * @param strRef URL для переходу
     * @param strTitle назва гіперзв'язку
     * @param strJSCall виклик JavaScript функції
     * @return сформований URL гіперзв'язок
     */
    public static String href (String strRef, String strTitle, String strJSCall)
    {
        if (strJSCall == null)
            strJSCall = "";
        return "<A HREF=\"" + strRef + "\" " + strJSCall + ">" + strTitle + "</A>";
    }

    /**
     * URL гіперзв'язок.
     * @param strRef URL для переходу
     * @param strTitle назва гіперзв'язку
     * @param strJSCall виклик JavaScript функції
     * @return сформований URL гіперзв'язок
     * @see #href (String, String, String)
     */
    public static String href (String strRef, String strTitle)
    {
        return href (strRef, strTitle, null);
    }

    /**
     * Меню.
     * @param strValue поля меню
     * @return сформоване меню
     */
    public static String menu (String strValue)
    {
        return "<MENU>" +
               strValue +
               "</MENU>";
    }

    /**
     * Несортований список.
     * @param strList елементи списку
     * @return сформований несортований список
     */
    public static String unorderedlist (String strList)
    {
        return "<UL>" +
               div (strList, "left") +
               "</UL>";
    }

    /**
     * Елемент списку з початоткової круглою відміткою
     * @param strElement елемент списку
     * @return сформований елемент списку
     */
    public static String listitem (String strElement)
    {
        return "<LI>" + strElement;
    }

    /**
     * URL гіперзв'язок пункту меню
     * @param strUrl URL перехід
     * @param strValue назва пункту меню
     * @param strJSCall виклик JavaScript функції
     * @return створений пункт меню
     */
    public static String menuitem (String strUrl, String strValue, String strJSCall)
    {
        return listitem (href (strUrl, strValue, strJSCall));
    }

    /**
     * URL гіперзв'язок пункту меню
     * @param strUrl URL перехід
     * @param strValue назва пункту меню
     * @return створений пункт меню
     * @see #menuitem (String, String, String)
     */
    public static String menuitem (String strUrl, String strValue)
    {
        return menuitem (strUrl, strValue, null);
    }

    /**
     * Перепустити необхідну кількість строчок
     * @param iCnt кількість строчок, яких потрібно пропустити
     * @return сформовані теги пропуску
     */
    public static String crlf (int iCnt)
    {
        String strRtrn = "";
        for (int iI = 0; iI < iCnt; iI++)
            strRtrn += "<BR>";
        return strRtrn;
    }

    /**
     * Перепустити одну строчку
     * @return сформованій тег пропуску
     * @see #crlf (int)
     */
    public static String crlf ()
    {
        return crlf (1);
    }

    /**
     * Повернення горізонтальної лінії
     * @return
     */
    public static String hline ()
    {
        return "<HR>";
    }

    /**
     * Повернення фрейма
     * @param
     * @return
     */
    public static String frame (String strURLFrame, boolean bScroll, boolean bBorder)
    {
        String strScroll = null;
        String strBorder = null;

        if (bScroll)
            strScroll = "Auto";
        else
            strScroll = "No";
        if (bBorder)
            strBorder = "yes";
        else
            strBorder = "no";
        return "<FRAME NAME=\"1\" SRC=\"" + strURLFrame + "\" MARGINWIDTH=\"10\" MARGINHEIGHT=\"10\" SCRILLING=\"" + strScroll + "\" FRAMEBORDER=\"" + strBorder + "\" NORESIZE>";
    }

    /**
     * Повернення набора фреймів, який содержить два горізонтальних фрейми
     * @param
     * @return
     */
    public static String frameset2h (String strFrames)
    {
        return "<FRAMESET ROWS=\"80%,*\" BORDER=0>" +
               strFrames +
               "</FRAMESET>";
    }

    /**
     * Повернення набора полів у вигляді окремого параграфа.
     * @param strTitle назва параграфа
     * @param strBody поля для формування
     * @return сформованний параграф
     */
    public static String paragraph (String strTitle, String strBody)
    {
        String strParTitle = null;
        if (String.valueOf (strTitle) == "null")     // є назва?
            strParTitle = "";
        else
            strParTitle = htmli.place (
                            htmli.row (
                              htmli.cell (htmli.subtitle (strTitle) +
                                          htmli.crlf (), 100)), 0);
        return htmli.row (
                 htmli.cell (
                   strParTitle +
                   htmli.place (htmli.row (strBody), 0), 100));
    }

    /**
     * Повернення набора полів у вигляді окремого параграфаю.
     * @param strBody поля для формування
     * @return сформованний параграф
     * @see #paragraph (String, String)
     */
    public static String paragraph (String strBody)
    {
        return paragraph (null, strBody);
    }

    /**
     * Повернення сформованих скриптів (типу JavaScript).
     * @param strTypeScript тип скрипту (null - JavaScript)
     * @param strBody тіло скрипту
     * @return сформованний скрипт
     */
    public static String script (String strTypeScript, String strBody)
    {
        String strTmpTypeScript;
        if (String.valueOf (strTypeScript) == "null")
            strTmpTypeScript = "JavaScript";
        else
            strTmpTypeScript = strTypeScript;
        return "<SCRIPT language=\"" + strTmpTypeScript + "\">" + strBody + "</SCRIPT>";
    }

    /**
     * Повернення сформованих JavaScript скриптів.
     * @param strBody тіло скрипту
     * @return сформованний скрипт
     * @see #script (String, String)
     */
    public static String script (String strBody)
    {
        return script (null, strBody);
    }
}

