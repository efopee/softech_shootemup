# softech_shootemup

A program indítása

A program indításakor a Gui osztály egy példánya jön létre. Ez Listener objektumokat rendel a beviteli eszközökhöz. Kirajzol egy üres képernyőt és egy menüt, amelyből a felhasználó három működési mód közül választhat (Single player, Start Server, Connect to Server). Amikor a felhasználó választott a menüből a Gui objektum a Control osztályt példányosítja, amely konstruktorának átadja a játékmódot (illetve szerverhez való csatlakozás esetén a távoli IP-címet).

A Control objektum tagváltozói a játékot leíró objektumok (játékosok, ellenfelek, lövedékek) kezdetben üres listái. Ezek inicializálását követően a Control objektum a Network ősosztály egyik alosztályába tartozó példányt hoz létre, attól függően, hogy a felhasználó szerver, vagy kliens módban indítja a játékot. Egyjátékos módban Network objektumra nincs szükség, a játék elindul.

A játék többjátékos módban való indításához pontosan egy szerver és egy kliens verziót futtató számítógépre van szükség, amelyek a helyi hálózaton keresztül csatlakoznak egymáshoz. Miután a TCP kapcsolat felépült a két gép között, a szerver módban futó játék Network objektuma a Control objektumnak jelezve elindítja a játékot.

A játék lépései

A szerver játékos Control objektuma időzítő szálat indít el, amely szabályos időközönként a játékot leíró objektumok pozícióját léptető függvényeket hívja meg. A léptetést követően az objektumokat tartalmazó listákat átadja a Guinak, amely kirajzolja azokat a pozíció tagváltozóiknak megfelelően. A Control a játék állapotát leíró listákat a Network objektumnak is átadja, amely a helyi hálózaton keresztül továbbítja a klienst futtató gépnek. A kliens ekkor a szerver felé továbbítja a kliens játékos sebességét és lövését.

A léptetés során a Control vizsgálja a játékosok, ellenfelek és lövedékek pozícióját, ütközés esetén a játékosok, illetve ellenfelek életpontjaiból a lövedék értékének megfelelően levon.

Mikor a játékos leüt egy billentyűt, a Gui meghívja a Control objektum egy függvényét, amely az adott játékos objektum sebességét és lövésállapotát beállítja. A fentiek szerint a kliens játékos billentyűleütései pontosan egy időzítési kvantummal késleltetve lépnek érvénybe.

A szerveren futó Timer szál szabályos időközönként egy játékeseményekért felelős taszkot is ütemez, amely adott valószínűséggel új ellenfelet ad hozzá a játéktérhez. Az új ellenfélnek véletlenszerű a kezdősebessége. A taszk adott valószínűséggel új lövedéket indít el valamelyik képernyőn lévő ellenféltől, amelyet véletlenszerűen választ ki.

A játék vége

Ha minden játékos életpontjai nullára csökkentek, a játék véget ér. A szerver Control objektuma leállítja az időzítő szálat, és jelzi a játék végét mind a kliens felé, mind a saját Gui objektuma felé. Ekkor mindkét gépen a Game Over felirat olvasható a képernyőn, új játék indítására van lehetőség.
