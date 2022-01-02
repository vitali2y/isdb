Integrated Subscribers DataBase (ISDB)
======================================

This software has been developed by myself @ 2000 (at that sweet time it was used a manually compiled [Kaffe Virtual Machine](https://github.com/kaffe/kaffe) - because no *Sun*'s *Java* for *Digital*'s *Alpha Server* yet, and [ApacheJServ](http://pages.di.unipi.it/ghelli/didattica/bdldoc/A97329_03/web.902/q20202/install/howto.unix_install.html) *C*-based module for running servlets - because no *Tomcat* yet!), and practically used during quite a long time @ *Zhitomir* affiliate of *Utel* company, *Ukraine*.

Conversion for some *Java* source files has been executed as below:

```
find . -name "*.java" -exec ls {} + | awk '{ system("iconv --from-code WINDOWS-1251 --to-code utf-8 "$0" -o "$0".new && mv "$0".new "$0) }' | more
find . -exec dos2unix {} +
```
