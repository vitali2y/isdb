//		Name:			PieChart
//		Written by:		Gary T. Desrosiers
//						desrosi@pcnet.com
//						http://www.pcnet.com/~desrosi
//		Date:			November 30, 1996.
//      Updated:        June 15th, 1999 - Bugfix and added optional frame specification for URLs (see note below)
//						December 9th, 1998 - Convert to Java 1.1 with new event model.
//		Description:	Creates basic pie charts
//		Parameters:		V0, V1, V2, etc...
//							Values for pie chart
//						T0, T1, T2, etc.
//							Titles for each value to display in legend
//						U0, U1, U2, etc.
//							URL to hyperlink when pie piece is clicked. If not specified, no hyperlinking.
//						URLOutside
//							URL to hyperlink when user clicks outside pie. Default is nowhere.
//						ChartTitle
//							Title of the pie chart
//						TitleTextColor
//							Color of the title, valid colors are...
//						TitleFontSize
//							Size of chart tile font. Default is 14 point font
//						LegendTextColor
//							Color of the legend text. See "TitleTextColor" for valid color values. Default is red
//						LegendFontSize
//							Size of legend font. Default is 14 point font
//						PieTextColor
//							Color of text labeling pie chart. See "TitleTextColor" for valid color values. Default is white
//						PieFontSize
//							Size of text labeling pie chart. Default is 14 point font
//						BackgroundColor
//							Color of the background. See "TitleTextColor" for valid color values. Default is black.
//						Background
//							relative URL to background image. This parameter and "BackgroundColor" are mutually exclusive. The image is cascaded in the background of the chart.
//						FontName
//							Name of font to use, default is Helvetica
//						Tips
//							true if values to be displayed when user moves mouse over chart. Default is true
//
//		note: All URLs can optionally specify a frame by suffixing a comma and then the frame name (no spaces) for
//				example: http://www.sun.com,_blank or http://www.ibm.com,main
import java.awt.*;
import java.awt.image.ImageObserver;
import java.applet.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.Frame;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

public class PieChart extends Applet implements MouseListener, MouseMotionListener
{
    String fontName = null;
    Color legendTextColor,pieTextColor,titleTextColor,backgroundColor;
    Image backgroundImage = null;
    Dimension imageDimensions = null;
    double values[] = new double[100];
    String[] titles = new String[100];
    String urls[] = new String[100];
    String URLoutside = null;
    int valuesSize = 0;
    Font legendFont,titleFont,pieFont;
    int legendFontSize,titleFontSize,pieFontSize;
    Color c[] = new Color[14];
    Rectangle r = null;
    String chartTitle;
    int topmargin = 10;
    int rightmargin = 10;
    int pieSize = 0,xPos = 0,yPos = 0;
    int mouseSelAngle = -1;
    double sum, factor;
    Graphics g2 = null;
    Image offScreenImage  = null;
    int xMousePos = -1, yMousePos = -1;
    int curPiece = -1;
    boolean wantTips = true;
    Frame parentFrame = null;

    public PieChart()
    {
    }

    private String commaFormat(String s)
    {
        StringBuffer buf = new StringBuffer();
        int i = s.length()-1;
        int dot = -1;
        int digits = 1;

        if(s.indexOf('e') != -1)
            return s;

        if((dot = s.indexOf('.')) != -1)
        {
            for(;i>=dot;i--)
                buf.insert(0,s.charAt(i));
        }
        for(;i>=0;i--)
        {
            buf.insert(0,s.charAt(i));
            if( ((digits%3) == 0) && (i>0))
            {
                buf.insert(0,',');
            }
            digits++;
        }
        return buf.toString();
    }

    private Color convertColor(String c)
    {
        if(c.equalsIgnoreCase("black"))
            return Color.black;
        if(c.equalsIgnoreCase("blue"))
            return Color.blue;
        if(c.equalsIgnoreCase("cyan"))
            return Color.cyan;
        if(c.equalsIgnoreCase("darkGray"))
            return Color.darkGray;
        if(c.equalsIgnoreCase("gray"))
            return Color.gray;
        if(c.equalsIgnoreCase("green"))
            return Color.green;
        if(c.equalsIgnoreCase("lightGray"))
            return Color.lightGray;
        if(c.equalsIgnoreCase("magenta"))
            return Color.magenta;
        if(c.equalsIgnoreCase("orange"))
            return Color.orange;
        if(c.equalsIgnoreCase("pink"))
            return Color.pink;
        if(c.equalsIgnoreCase("red"))
            return Color.red;
        if(c.equalsIgnoreCase("white"))
            return Color.white;
        if(c.equalsIgnoreCase("yellow"))
