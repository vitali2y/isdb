// http://www.freecode.com/cgi-bin/redir.pl?5121,http://www.geocities.com/ResearchTriangle/Lab/4841/colorcycle.html
// http://www.geocities.com/ResearchTriangle/Lab/4841/colorcycle.html
// Source code: http://www.freecode.com/cgi-bin/viewsource.pl?5121,ColourCycle.java
import java.applet.*;
import java.awt.*;

public class ColorCycle extends Applet implements Runnable
{
    private Thread m_Colorcycle = null;
    private Color linee[];
    private Image dbufferimage;
    private Graphics dbuffer;

    String scrolltext;
    int scrolltextpos = 0;
    int maxpos = 0;
    int rcol = 0;
    int gcol = 0;
    int bcol = 0;
    int linefactor = 4;
    Image logopicture;


    boolean pleasewait = true;
    boolean rcol_add = true;
    boolean bcol_add = false;
    boolean gcol_add = false;
    boolean growing = true;

    public void init()
    {
        String parm;
        linee = new Color[size().height/linefactor];

        // Loads picture
        MediaTracker tracker = new MediaTracker(this);
        logopicture = getImage(getDocumentBase(), getParameter("logo"));


        parm = getParameter("scroll");
        if ( parm !=null)
            scrolltext = parm;
        else
            scrolltext = "";

        parm = getParameter("factor");
        if ( parm !=null)
            linefactor = Integer.parseInt(parm);;

        tracker.addImage(logopicture, 0);
        try
        {
            tracker.waitForID(0);
        }
        catch(Exception e) {};

        for(maxpos = 0; maxpos<(size().height/linefactor)-1 ; maxpos++)
        {
            linee[maxpos]=new Color(0,0,0);
        }
        maxpos = size().height;
        dbufferimage = createImage(size().width,size().height);
        scrolltextpos = size().width + 20;
        dbuffer = dbufferimage.getGraphics();
        dbuffer.setFont(new Font("Arial",Font.BOLD,24));
    }

    public void update(Graphics g)
    {
        if (logopicture == null)
            return;
        pleasewait = true;
        for(maxpos = 1; maxpos < (size().height/linefactor)-1 ; maxpos++)
        {
            linee[maxpos-1]=linee[maxpos];
            dbuffer.setColor(linee[maxpos]);
            dbuffer.fillRect(0,(maxpos-1)*linefactor,size().width,((maxpos-1)*linefactor)+linefactor);
        }
        createcolorFade();
        linee[maxpos-1]=new Color(rcol,gcol,bcol);
        dbuffer.drawImage(logopicture,0,0,this);
        scrolltextpos -=2;
        dbuffer.setColor(new Color(rcol,gcol,bcol));
        dbuffer.drawString(scrolltext,scrolltextpos,size().height-15);
        pleasewait = false;
        if (scrolltextpos * -1 > scrolltext.length()*12)
            scrolltextpos =size().width + 30;
        paint(g);
    }


    public void paint(Graphics g)
    {
        if(!pleasewait)
        {
            if (dbufferimage!= null)
            {
                g.drawImage(dbufferimage, 0, 0, null);
            }
        }
    }

    public void start()
    {
        if (m_Colorcycle == null)
        {
            m_Colorcycle = new Thread(this);
            m_Colorcycle.start();
        }
    }

    public void stop()
    {
        if (m_Colorcycle != null)
        {
            m_Colorcycle.stop();
            m_Colorcycle = null;
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                repaint();
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                stop();
            }
        }
    }

    public void createcolorFade()
    {

        if( growing)
        {
            if( rcol_add == true )
            {
                rcol += 15;
                if ( rcol == 255)
                {
                    rcol_add = false;
                    gcol_add = true;
                }
            }
            if( gcol_add == true )
            {
                gcol += 15;
                if ( gcol == 255)
                {
                    gcol_add = false;
                    bcol_add = true;
                }
            }
            if( bcol_add == true )
            {
                bcol += 15;
                if ( bcol == 255)
                {
                    bcol_add = false;
                    gcol_add = true;
                    growing = false;
                }
            }
        }
        else
        {
            if( gcol_add == true )
            {
                gcol -= 15;
                if ( gcol == 0)
                {
                    gcol_add = false;
                    rcol_add = true;
                }
            }
            if( rcol_add == true )
            {
                rcol -= 15;
                if ( rcol == 0)
                {
                    rcol_add = false;
                    bcol_add = true;
                }
            }
            if( bcol_add == true )
            {
                bcol -= 15;
                if ( bcol == 0)
                {
                    bcol_add = false;
                    rcol_add = true;
                    growing = true;
                }
            }
        }
    }



}
