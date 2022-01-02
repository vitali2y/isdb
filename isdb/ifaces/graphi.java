/**
 * graphi.java
 * ISDBj
 */

package isdb.ifaces;

import isdb.datas.dbdata;

/**
 * Інтерфейс роботи с графічними відображеннями (з використанням APPLETів).
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class graphi
{
    /** Буфер збереження об'екта */
    private String strBody = "";
    private int iNumbParam = 0;
    private String strCurDate = null;
    private String strSite = null;
    private String strSwitch = null;

    /**
     * Конструктор
     * @param oDBData поточни дані об'екта
     */
    public graphi (dbdata oDBData)
    {
        strCurDate = dbi.dbdate (oDBData);
        strSite = cfgi.getAffiliate ();
        strSwitch = cfgi.getSwitch ();
    }

    /**
     * Повернення об'екта
     * @return сформований об'ект типу APPLET в форматі HTML
     */
    public String getApplet ()
    {
        String strTmp = "<APPLET " + strBody + "</APPLET>";
        strBody = "";
        iNumbParam = 0;
        return strTmp;
    }

    /**
     * Встановлення об'екта "діаграма".
     * <P>Використовуемі параметри:
     * <P><B>V0</B>, <B>V1</B>, <B>V2</B>, etc ... - Values for pie chart
     * <P><B>T0</B>, <B>T1</B>, <B>T2</B>, etc ... - Titles for each value to display in legend
     * <P><B>U0</B>, <B>U1</B>, <B>U2</B>, etc ... - URL to hyperlink when pie piece is clicked. If not specified, no hyperlinking.
     * <P><B>URLOutside</B> - URL to hyperlink when user clicks outside pie. Default is nowhere.
     * <P><B>ChartTitle</B> - Title of the pie chart
     * <P><B>TitleTextColor</B> - Color of the title, valid colors are...
     * <P><B>TitleFontSize</B> - Size of chart tile font. Default is 14 point font
     * <P><B>LegendTextColor</B> - Color of the legend text. See "TitleTextColor" for valid color values. Default is red
     * <P><B>LegendFontSize</B> - Size of legend font. Default is 14 point font
     * <P><B>PieTextColor</B> - Color of text labeling pie chart. See "TitleTextColor" for valid color values. Default is white
     * <P><B>PieFontSize</B> - Size of text labeling pie chart. Default is 14 point font
     * <P><B>BackgroundColor</B> - Color of the background. See "TitleTextColor" for valid color values. Default is black.
     * <P><B>Background</B> - relative URL to background image. This parameter and "BackgroundColor" are mutually exclusive. The image is cascaded in the background of the chart.
     * <P><B>FontName</B> - Name of font to use, default is Helvetica
     * <P><B>Tips</B> - true if values to be displayed when user moves mouse over chart. Default is true
     * @see <A HREF="http://http://www.pcnet.com/~desrosi">Орігінал PieChart.java</A>
     * @see <A HREF="http://www.vermontlife.com/gary/java/PieChart.java">Орігінал PieChart.java</A>
     * @see <A HREF="mailto:desrosi@pcnet.com">Автор PieChart.java</A>
     * @author Gary T. Desrosiers
     */
    public void setPieChart ()
    {
        iNumbParam = 0;
        strBody = "CODE=\"PieChart.class\" CODEBASE=\"/applets\" WIDTH=600 HEIGHT=400>" +
                  "<PARAM NAME=PieTextColor VALUE=\"yellow\">" +
                  "<PARAM NAME=ChartTitle VALUE=\"\nСтаном на " + strCurDate + "\">" +
                  "<PARAM NAME=TitleFontSize VALUE=\"18\">" +
                  // "<PARAM NAME=LegendFontSize VALUE=\"16\">" +
                  "<PARAM NAME=LegendFontSize VALUE=\"12\">" +
                  // "<PARAM NAME=background VALUE=\"tan_paper.gif\">" +
                  "<PARAM NAME=LegendTextColor VALUE=\"red\">";
    }

    /**
     * Встановлення об'екта "графік"
     * @param iCount
     * @see <A HREF="http://stud1.tuwien.ac.at/~e9125168/javaa/classes/Chart.java">Орігінал Chart.java</A>
     * @see <A HREF="http://stud4.tuwien.ac.at/~e9125168/javaa/jchart.html">Java Applet Chart головна сторінка</A>
     * @author Sami Shaio of Sun Microsystems
     */
    public void setGraphChart (int iCount)
    {
        iNumbParam = 1;
        strBody = "CODE=\"Chart.class\" CODEBASE=\"/applets\" WIDTH=600 HEIGHT=400>" +
                  "<PARAM NAME=title VALUE=\"" + strSite + "      " + strSwitch + "\">" +
                  "<PARAM NAME=orientation VALUE=\"vertical\">" +
                  "<PARAM NAME=scale VALUE=\"4\">" +
                  "<PARAM NAME=columns VALUE=\"" + new Integer (iCount).toString () + "\">";
    }

    /**
     * Додавання елемента діаграми
     * @param strName
     * @param strValue
     * @param strURL
     */
    public void addPieChartElement (String strName, String strValue, String strURL)
    {
        String strCurPar = (new Integer (iNumbParam)).toString ();
        iNumbParam++;
        strBody += "<PARAM NAME=V" + strCurPar + " VALUE=\"" + strValue + "\">" +
                   "<PARAM NAME=T" + strCurPar + " VALUE=\"" + strName + "\">" +
                   "<PARAM NAME=U" + strCurPar + " VALUE=\"" + strURL + "\">";
        // "<PARAM NAME=C" + strCurPar + "_STYLE VALUE=\"striped\">";;
    }

    /**
     * Додавання елемента графіка
     * @param strName
     * @param strValue
     */
    public void addGraphChartElement (String strName, String strValue)
    {
        String strCurPar = (new Integer (iNumbParam)).toString ();
        iNumbParam++;
        strBody += "<PARAM NAME=c" + strCurPar + " VALUE=\"" + strValue + "\">" +
                   "<PARAM NAME=c" + strCurPar + "_label VALUE=\"" + strName + "\">" +
                   "<PARAM NAME=c" + strCurPar + "_style VALUE=\"solid\">" +
                   "<PARAM NAME=c" + strCurPar + "_color VALUE=\"magenta\">";
    }
}

