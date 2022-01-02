#
# Makefile for project ISDB
# Yermolenko Vitaly, Utel-Zhitomir
# Last updated - 15/V/2000
#

CLASSDIR=./isdb
JAVA_HOME=/usr/local/jdk1.2.2
# /usr/local/kaffe1.0.5new
CLASSPATH=-classpath .:/usr/local/jdk1.2.2/lib:/usr/local/lib/jsdk/jsdk.jar:/ora_srv/app/oracle/product/8.0.5/jdbc/lib/classes111.zip:/usr/local/JServ-1.0b1/lib/Apache-JServ.jar
OBFUSCLASSPATH=$(CLASSPATH):/home/www/servlets/vit/RetroGuard/RetroGuard.jar
JAVA=$(JAVA_HOME)/bin/java
JAVAC=$(JAVA_HOME)/bin/javac
JAVADOC=$(JAVA_HOME)/bin/javadoc
JAR=$(JAVA_HOME)/bin/jar
FLAGS=-O -encoding Cp1251

all: MISCS IFACES DATAS OBJS DEPTS DEPTCS DEPTMAINT DEPTSALES DEPTMNGR SERVLETS
	
ISDBJAR=isdb.jar
ISDBSOURCEJAR=isdb-source.jar
ISDBOBFUSCJAR=isdb-obfusc.jar
ISDBSOURCE=isdb-source.tar

	MISCSDIR=$(CLASSDIR)/miscs
MISCS: \
	$(MISCSDIR)/cnv2ukr.class           \
	$(MISCSDIR)/dclrs.class

IFACESDIR=$(CLASSDIR)/ifaces
IFACES: \
	$(IFACESDIR)/cfgi.class              \
	$(IFACESDIR)/dbi.class               \
	$(IFACESDIR)/graphi.class            \
	$(IFACESDIR)/htmli.class             \
	$(IFACESDIR)/jsi.class

DATASDIR=$(CLASSDIR)/datas
DATAS: \
	$(DATASDIR)/buttondata.class        \
	$(DATASDIR)/cookiedata.class                \
	$(DATASDIR)/dbdata.class            \
	$(DATASDIR)/globaldata.class                \
	$(DATASDIR)/jsdata.class            \
	$(DATASDIR)/loaddata.class          \
	$(DATASDIR)/menudata.class          \
	$(DATASDIR)/outdata.class           \
	$(DATASDIR)/pooldata.class          \
	$(DATASDIR)/reportdata.class                \
	$(DATASDIR)/sqldata.class           \
	$(DATASDIR)/stackdata.class         \
	$(DATASDIR)/stackurldata.class              \
	$(DATASDIR)/transactiondata.class

OBJSDIR=$(CLASSDIR)/objs
OBJS: \
	$(OBJSDIR)/dbobj.class             \
	$(OBJSDIR)/isdbobj.class           \
	$(OBJSDIR)/objactivitie.class              \
	$(OBJSDIR)/objagreement.class              \
	$(OBJSDIR)/objagreementstate.class         \
	$(OBJSDIR)/objallcolumn.class              \
	$(OBJSDIR)/objallobject.class              \
	$(OBJSDIR)/objanicategorie.class           \
	$(OBJSDIR)/objate.class            \
	$(OBJSDIR)/objatecrosspair.class           \
	$(OBJSDIR)/objatecrosstrace.class          \
	$(OBJSDIR)/objatetechnician.class          \
	$(OBJSDIR)/objatetrunkline.class           \
	$(OBJSDIR)/objbank.class           \
	$(OBJSDIR)/objbldirnr.class                \
	$(OBJSDIR)/objblrestriction.class          \
	$(OBJSDIR)/objconstructor.class            \
	$(OBJSDIR)/objcrossmap.class               \
	$(OBJSDIR)/objdept.class           \
	$(OBJSDIR)/objdirnr.class          \
	$(OBJSDIR)/objdistribline.class            \
#	$(OBJSDIR)/objdistribshelve.class          \
	$(OBJSDIR)/objdstrcrosstrace.class         \
	$(OBJSDIR)/objequip.class          \
	$(OBJSDIR)/objequipcrosstrace.class                \
	$(OBJSDIR)/objequipdirnr.class             \
	$(OBJSDIR)/objexchmdfpair.class            \
	$(OBJSDIR)/objfailrpt.class                \
	$(OBJSDIR)/objfirm.class           \
	$(OBJSDIR)/objfirmincome.class             \
	$(OBJSDIR)/objfirmstate.class              \
	$(OBJSDIR)/objlen.class            \
	$(OBJSDIR)/objlinemdfpair.class            \
	$(OBJSDIR)/objlocation.class               \
	$(OBJSDIR)/objlocexchmdftrace.class                \
	$(OBJSDIR)/objloclentrace.class            \
	$(OBJSDIR)/objloclinemdftrace.class                \
	$(OBJSDIR)/objperson.class         \
	$(OBJSDIR)/objphcrosse.class               \
	$(OBJSDIR)/objphonebycat.class             \
	$(OBJSDIR)/objphonestate.class             \
	$(OBJSDIR)/objplan.class           \
	$(OBJSDIR)/objplanequip.class              \
	$(OBJSDIR)/objplanservice.class            \
	$(OBJSDIR)/objpropertie.class              \
	$(OBJSDIR)/objregion.class         \
	$(OBJSDIR)/objservice.class                \
	$(OBJSDIR)/objsession.class                \
	$(OBJSDIR)/objshelftrunkline.class         \
	$(OBJSDIR)/objshlfcrosstrace.class         \
	$(OBJSDIR)/objstreet.class         \
	$(OBJSDIR)/objsubsservice.class            \
	$(OBJSDIR)/objsubsservice.class            \
	$(OBJSDIR)/objtarifflevel.class            \
	$(OBJSDIR)/objtechref.class                \
	$(OBJSDIR)/objtrunkcrosstrace.class                \
	$(OBJSDIR)/objtypeagreement.class          \
	$(OBJSDIR)/objtypeequip.class              \
	$(OBJSDIR)/objtypeexchcross.class          \
	$(OBJSDIR)/objtypefirm.class               \
	$(OBJSDIR)/objtypelocation.class           \
	$(OBJSDIR)/objtypephone.class              \
	$(OBJSDIR)/objtyperejectagr.class          \
	$(OBJSDIR)/objtypesubsservice.class                \
	$(OBJSDIR)/objtypewwwservice.class         \
	$(OBJSDIR)/objwwworder.class               \
	$(OBJSDIR)/objwwworderstate.class          \
	$(OBJSDIR)/objwwwservice.class             \
	$(OBJSDIR)/objwwwsrvconstip.class          \
	$(OBJSDIR)/objwwwsrvdns.class              \
	$(OBJSDIR)/objwwwsrvemail.class            \
	$(OBJSDIR)/objwwwsrvisdncard.class         \
	$(OBJSDIR)/objwwwsrvsmtp.class             \
	$(OBJSDIR)/objwwwsrvwebhost.class          \
	$(OBJSDIR)/objwwwsrvwww.class              \
	$(OBJSDIR)/objzip.class

DEPTSDIR=$(CLASSDIR)/depts
DEPTS: \
	$(DEPTSDIR)/deptcs.class            \
	$(DEPTSDIR)/deptmaint.class         \
	$(DEPTSDIR)/deptmngr.class          \
	$(DEPTSDIR)/deptobj.class           \
	$(DEPTSDIR)/deptsales.class

DEPTCSDIR=$(CLASSDIR)/depts/cs
DEPTCS: \
	$(DEPTCSDIR)/menudbreview.class              \
	$(DEPTCSDIR)/menugeneral.class               \
	$(DEPTCSDIR)/menumain.class          	\
	$(DEPTCSDIR)/menuoperations.class            \
	$(DEPTCSDIR)/menustateofday.class

DEPTMAINTDIR=$(CLASSDIR)/depts/maint
DEPTMAINT: \
	$(DEPTMAINTDIR)/menuadditdb.class               \
	$(DEPTMAINTDIR)/menudbreview.class              \
	$(DEPTMAINTDIR)/menugeneral.class               \
	$(DEPTMAINTDIR)/menumain.class          \
	$(DEPTMAINTDIR)/menuoperations.class            \
	$(DEPTMAINTDIR)/menureports.class               \
	$(DEPTMAINTDIR)/menustateofday.class

DEPTSALESDIR=$(CLASSDIR)/depts/sales
DEPTSALES: \
	$(DEPTSALESDIR)/menuadditdb.class                \
	$(DEPTSALESDIR)/menudbreview.class               \
	$(DEPTSALESDIR)/menugeneral.class                \
	$(DEPTSALESDIR)/menumain.class           \
	$(DEPTSALESDIR)/menuoperations.class             \
	$(DEPTSALESDIR)/menureports.class                \
	$(DEPTSALESDIR)/menustateofday.class

DEPTMNGRDIR=$(CLASSDIR)/depts/mngr
DEPTMNGR: \
	$(DEPTMNGRDIR)/menudbreview.class               \
	$(DEPTMNGRDIR)/menugeneral.class                \
	$(DEPTMNGRDIR)/menumain.class           \
	$(DEPTMNGRDIR)/menuoperations.class             \
	$(DEPTMNGRDIR)/menureports.class                \
	$(DEPTMNGRDIR)/menustateofday.class

SERVLETSDIR=$(CLASSDIR)
SERVLETS: \
        $(SERVLETSDIR)/isdbcommit.class         \
        $(SERVLETSDIR)/isdbform.class           \
        $(SERVLETSDIR)/isdbgraph.class          \
        $(SERVLETSDIR)/isdbinfo.class           \
        $(SERVLETSDIR)/isdblogin.class          \
        $(SERVLETSDIR)/isdbmenu.class           \
        $(SERVLETSDIR)/isdboption.class         \
        $(SERVLETSDIR)/isdbproperty.class       \
        $(SERVLETSDIR)/isdbreport.class

$(MISCSDIR)/cnv2ukr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(MISCSDIR)/cnv2ukr.java
$(MISCSDIR)/dclrs.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(MISCSDIR)/dclrs.java

$(IFACESDIR)/cfgi.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(IFACESDIR)/cfgi.java
$(IFACESDIR)/dbi.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(IFACESDIR)/dbi.java
$(IFACESDIR)/graphi.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(IFACESDIR)/graphi.java
$(IFACESDIR)/htmli.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(IFACESDIR)/htmli.java
$(IFACESDIR)/jsi.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(IFACESDIR)/jsi.java

$(DATASDIR)/buttondata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/buttondata.java
$(DATASDIR)/cookiedata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/cookiedata.java
$(DATASDIR)/dbdata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/dbdata.java
$(DATASDIR)/globaldata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/globaldata.java
$(DATASDIR)/jsdata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/jsdata.java
$(DATASDIR)/loaddata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/loaddata.java
$(DATASDIR)/menudata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/menudata.java
$(DATASDIR)/outdata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/outdata.java
$(DATASDIR)/pooldata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/pooldata.java
$(DATASDIR)/reportdata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/reportdata.java
$(DATASDIR)/sqldata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/sqldata.java
$(DATASDIR)/stackdata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/stackdata.java
$(DATASDIR)/stackurldata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/stackurldata.java
$(DATASDIR)/transactiondata.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DATASDIR)/transactiondata.java

$(OBJSDIR)/dbobj.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/dbobj.java
$(OBJSDIR)/isdbobj.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/isdbobj.java
$(OBJSDIR)/objactivitie.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objactivitie.java
$(OBJSDIR)/objagreement.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objagreement.java
$(OBJSDIR)/objagreementstate.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objagreementstate.java
$(OBJSDIR)/objallcolumn.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objallcolumn.java
$(OBJSDIR)/objallobject.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objallobject.java
$(OBJSDIR)/objanicategorie.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objanicategorie.java
$(OBJSDIR)/objate.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objate.java
$(OBJSDIR)/objatecrosspair.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objatecrosspair.java
$(OBJSDIR)/objatecrosstrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objatecrosstrace.java
$(OBJSDIR)/objatetechnician.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objatetechnician.java
$(OBJSDIR)/objatetrunkline.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objatetrunkline.java
$(OBJSDIR)/objbank.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objbank.java
$(OBJSDIR)/objbldirnr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objbldirnr.java
$(OBJSDIR)/objblrestriction.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objblrestriction.java
$(OBJSDIR)/objconstructor.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objconstructor.java
$(OBJSDIR)/objcrossmap.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objcrossmap.java
$(OBJSDIR)/objdept.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objdept.java
$(OBJSDIR)/objdirnr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objdirnr.java
$(OBJSDIR)/objdistribline.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objdistribline.java
$(OBJSDIR)/objdistribshelve.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objdistribshelve.java
$(OBJSDIR)/objdstrcrosstrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objdstrcrosstrace.java
$(OBJSDIR)/objequip.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objequip.java
$(OBJSDIR)/objequipcrosstrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objequipcrosstrace.java
$(OBJSDIR)/objequipdirnr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objequipdirnr.java
$(OBJSDIR)/objexchmdfpair.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objexchmdfpair.java
$(OBJSDIR)/objfailrpt.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objfailrpt.java
$(OBJSDIR)/objfirm.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objfirm.java
$(OBJSDIR)/objfirmincome.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objfirmincome.java
$(OBJSDIR)/objfirmstate.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objfirmstate.java
$(OBJSDIR)/objlen.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objlen.java
$(OBJSDIR)/objlinemdfpair.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objlinemdfpair.java
$(OBJSDIR)/objlocation.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objlocation.java
$(OBJSDIR)/objlocexchmdftrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objlocexchmdftrace.java
$(OBJSDIR)/objloclentrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objloclentrace.java
$(OBJSDIR)/objloclinemdftrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objloclinemdftrace.java
$(OBJSDIR)/objperson.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objperson.java
$(OBJSDIR)/objphcrosse.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objphcrosse.java
$(OBJSDIR)/objphonebycat.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objphonebycat.java
$(OBJSDIR)/objphonestate.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objphonestate.java
$(OBJSDIR)/objplan.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objplan.java
$(OBJSDIR)/objplanequip.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objplanequip.java
$(OBJSDIR)/objplanservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objplanservice.java
$(OBJSDIR)/objpropertie.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objpropertie.java
$(OBJSDIR)/objregion.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objregion.java
$(OBJSDIR)/objservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objservice.java
$(OBJSDIR)/objsession.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objsession.java
$(OBJSDIR)/objshelftrunkline.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objshelftrunkline.java
$(OBJSDIR)/objshlfcrosstrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objshlfcrosstrace.java
$(OBJSDIR)/objstreet.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objstreet.java
$(OBJSDIR)/objsubsservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objsubsservice.java
$(OBJSDIR)/objtarifflevel.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtarifflevel.java
$(OBJSDIR)/objtechref.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtechref.java
$(OBJSDIR)/objtrunkcrosstrace.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtrunkcrosstrace.java
$(OBJSDIR)/objtypeagreement.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypeagreement.java
$(OBJSDIR)/objtypeequip.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypeequip.java
$(OBJSDIR)/objtypeexchcross.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypeexchcross.java
$(OBJSDIR)/objtypefirm.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypefirm.java
$(OBJSDIR)/objtypelocation.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypelocation.java
$(OBJSDIR)/objtypephone.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypephone.java
$(OBJSDIR)/objtyperejectagr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtyperejectagr.java
$(OBJSDIR)/objtypesubsservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypesubsservice.java
$(OBJSDIR)/objtypewwwservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objtypewwwservice.java
$(OBJSDIR)/objwwworder.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwworder.java
$(OBJSDIR)/objwwworderstate.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwworderstate.java
$(OBJSDIR)/objwwwservice.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwservice.java
$(OBJSDIR)/objwwwsrvconstip.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvconstip.java
$(OBJSDIR)/objwwwsrvdns.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvdns.java
$(OBJSDIR)/objwwwsrvemail.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvemail.java
$(OBJSDIR)/objwwwsrvisdncard.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvisdncard.java
$(OBJSDIR)/objwwwsrvsmtp.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvsmtp.java
$(OBJSDIR)/objwwwsrvwebhost.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvwebhost.java
$(OBJSDIR)/objwwwsrvwww.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objwwwsrvwww.java
$(OBJSDIR)/objzip.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(OBJSDIR)/objzip.java

$(DEPTSDIR)/deptcs.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSDIR)/deptcs.java
$(DEPTSDIR)/deptmaint.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSDIR)/deptmaint.java
$(DEPTSDIR)/deptmngr.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSDIR)/deptmngr.java
$(DEPTSDIR)/deptobj.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSDIR)/deptobj.java
$(DEPTSDIR)/deptsales.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSDIR)/deptsales.java

$(DEPTCSDIR)/menudbreview.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTCSDIR)/menudbreview.java
$(DEPTCSDIR)/menugeneral.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTCSDIR)/menugeneral.java
$(DEPTCSDIR)/menumain.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTCSDIR)/menumain.java
$(DEPTCSDIR)/menuoperations.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTCSDIR)/menuoperations.java
$(DEPTCSDIR)/menustateofday.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTCSDIR)/menustateofday.java

$(DEPTMAINTDIR)/menuadditdb.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menuadditdb.java
$(DEPTMAINTDIR)/menudbreview.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menudbreview.java
$(DEPTMAINTDIR)/menugeneral.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menugeneral.java
$(DEPTMAINTDIR)/menumain.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menumain.java
$(DEPTMAINTDIR)/menuoperations.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menuoperations.java
$(DEPTMAINTDIR)/menureports.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menureports.java
$(DEPTMAINTDIR)/menustateofday.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMAINTDIR)/menustateofday.java

$(DEPTSALESDIR)/menuadditdb.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menuadditdb.java
$(DEPTSALESDIR)/menudbreview.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menudbreview.java
$(DEPTSALESDIR)/menugeneral.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menugeneral.java
$(DEPTSALESDIR)/menumain.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menumain.java
$(DEPTSALESDIR)/menuoperations.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menuoperations.java
$(DEPTSALESDIR)/menureports.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menureports.java
$(DEPTSALESDIR)/menustateofday.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTSALESDIR)/menustateofday.java

$(DEPTMNGRDIR)/menudbreview.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menudbreview.java
$(DEPTMNGRDIR)/menugeneral.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menugeneral.java
$(DEPTMNGRDIR)/menumain.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menumain.java
$(DEPTMNGRDIR)/menuoperations.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menuoperations.java
$(DEPTMNGRDIR)/menureports.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menureports.java
$(DEPTMNGRDIR)/menustateofday.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(DEPTMNGRDIR)/menustateofday.java


$(SERVLETSDIR)/isdbcommit.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbcommit.java
$(SERVLETSDIR)/isdbform.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbform.java
$(SERVLETSDIR)/isdbgraph.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbgraph.java
$(SERVLETSDIR)/isdbinfo.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbinfo.java
$(SERVLETSDIR)/isdblogin.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdblogin.java
$(SERVLETSDIR)/isdbmenu.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbmenu.java
$(SERVLETSDIR)/isdboption.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdboption.java
$(SERVLETSDIR)/isdbproperty.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbproperty.java
$(SERVLETSDIR)/isdbreport.class:
	$(JAVAC) $(FLAGS) $(CLASSPATH) $(SERVLETSDIR)/isdbreport.java

ISDBDOCDIR=/home/www/htdocs/apps/isdb/api
doc: 
	echo "Creating the ISDB documentation ..."
	rm -f -r /home/www/htdocs/apps/isdb/api/* 2>/dev/null
# cp /home/www/htdocs/apps/isdb/api/src/overview-summary.html /home/www/htdocs/apps/isdb/api
# cp /home/www/htdocs/apps/isdb/api/src/readme /home/www/htdocs/apps/isdb/api
# cp /home/www/htdocs/apps/isdb/api/src/API_users_guide.html /home/www/htdocs/apps/isdb/api
	$(JAVADOC) $(CLASSPATH) \
	-d $(ISDBDOCDIR) \
	-encoding Cp1251 \
	-docencoding Cp1251 \
	-author \
	-version \
	-windowtitle 'ISDB API Version 1.0 final' \
	-bottom 'Utel-Zhitomir, April 2000' \
	-doctitle '<I>ISDB<BR>(Integrated Subscriber`s DataBase)</I><BR>API<BR><font size="-1">Version 1.0 final</font>' \
	-overview overview-summary.html \
	isdb isdb.objs isdb.depts isdb.ifaces isdb.miscs isdb.datas
# -private \
# -header 'Hallo, header!' \

jar:
	echo "Creating jar file (isdb-source.jar) ..."
	$(JAR) cvf $(ISDBSOURCEJAR) \
	$(CLASSDIR)/*.class \
	$(DEPTSDIR)/*.class \
	$(DEPTMAINTDIR)/*.class \
	$(DEPTSALESDIR)/*.class \
	$(DEPTMNGRDIR)/*.class \
	$(DEPTCSDIR)/*.class \
	$(DATASDIR)/*.class \
	$(OBJSDIR)/*.class \
	$(MISCSDIR)/*.class \
	$(IFACESDIR)/*.class
# isdb/applets/*.class
	echo "File "$(ISDBSOURCEJAR)" have been created!"
	echo "jar: completed!"
	
obfuscation:
# set `date '+%y%m%d'`
	echo "Obfuscation is in progress ..."
	rm -f $(ISDBOBFUSCJAR)
	$(JAVA) $(OBFUSCLASSPATH) RetroGuard $(ISDBSOURCEJAR) $(ISDBOBFUSCJAR)
	rm -f $(ISDBJAR)
	ln -s $(ISDBOBFUSCJAR) $(ISDBJAR)
	echo "obfuscation: completed!"

style:
	echo "Formating Java source files ..."
	for CURFILE in $(CLASSDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DEPTSDIR)/*.java; \
	    do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DEPTCSDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DEPTMAINTDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DEPTSALESDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DEPTMNGRDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(MISCSDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(OBJSDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(DATASDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
	for CURFILE in $(IFACESDIR)/*.java; \
	do \
	    echo "   "$$CURFILE; \
	    $(JAVA) $(CLASSPATH) jstyle.JSBeautifier -t -s4 -fs $$CURFILE; \
	    mv -f $$CURFILE.js $$CURFILE; \
	done
							    
archive:
	tar cvf $(ISDBSOURCE)	\
	./Makefile                      \
	$(CLASSDIR)/*.java \
	$(DEPTSDIR)/*.java \
	$(DEPTMAINTDIR)/*.java \
	$(DEPTSALESDIR)/*.java \
	$(DEPTMNGRDIR)/*.java \
	$(DEPTCSDIR)/*.java \
	$(DATASDIR)/*.java \
	$(OBJSDIR)/*.java \
	$(MISCSDIR)/*.java \
	$(IFACESDIR)/*.java

clean:
	echo "Clearing all class files ..."
	rm -f \
	$(CLASSDIR)/*.class \
	$(DEPTSDIR)/*.class \
	$(DEPTMAINTDIR)/*.class \
	$(DEPTSALESDIR)/*.class \
	$(DEPTMNGRDIR)/*.class \
	$(DEPTCSDIR)/*.class \
	$(DATASDIR)/*.class \
	$(OBJSDIR)/*.class \
	$(MISCSDIR)/*.class \
	$(IFACESDIR)/*.class
	echo "remove: completed!"

# End of Makefile
