# Invaders 2

<img align="right" src="https://raw.githubusercontent.com/Moon70/Invaders2/master/Screenshot1.jpg)">
A simple shoot-em-up game, created in 2017.

Press SPACE to start,
ESC to exit game,
cursor keys left/right to move your spaceship,
X to fire.

Shoot the Invaders, avoid the comets, easy?









<img align="right" src="https://raw.githubusercontent.com/Moon70/Invaders2/master/Screenshot2.jpg)">
There are 7 galaxies, four levels per galaxy.
Each time you enter a new galaxy, you´ll get a bonus:

1. faster weapon

2. stronger weapon (destroy two invaders with one shot)

3. bonus life

As soon as you get shot you loose your improved weapon!





<img align="right" src="https://raw.githubusercontent.com/Moon70/Invaders2/master/Screenshot3.jpg)">
I was thinking about to make a game for a while, and therefor thinking about the Invaders game I did in 1992 on the Commodore Amiga together with two friends. Being not a graphician, it´s always good to have something to recycle :-)
In addition, i was curious if it´s possible (performance, timing) to create a game like this in Java, without any external APIs.







<img align="right" src="https://raw.githubusercontent.com/Moon70/Invaders2/master/Screenshot4.jpg)">
But my motivation was very limited, mainly because i had no clue how to create 'cool' endless scrolling backgrounds. This was really a big problem. Then one day, on my way to work, i suddenly had the idea how to 'calculate' them out of those fantastic Hubble-Space-Telescope images. The next weekend, i wrote a POC, and it looked good enough :-)



### How to build: ###

If you´re not familiar with the build tool 'Apache Maven', these few lines might help:

* download and unzip [Apache Maven](http://maven.apache.org/download.cgi)
* add the **...\apache-maven-x.x.x\bin** folder to your path variable
* check out both [LunarEngine](https://github.com/Moon70/LunarEngine) and [Invaders2](https://github.com/Moon70/Invaders2)
* open CMD, change directory to lunarengine folder, that one which contains the pom.xml
* execute this command: **mvn clean install**
* now the engine should be compiled and installed in you local maven repository
* change directory to invaders2 folder, again that one containing the pom.xml
* execute this command: **mvn clean package**
* now the target folder should contain two .jar files, the bigger one (containing also the engine) is the executable one

