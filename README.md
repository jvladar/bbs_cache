# Paralelné programovanie a distribuované systémy 2022

## Cvičenie 5, zadanie [Modifikácia problému divochov #22](https://uim.fei.stuba.sk/i-ppds/5-cvicenie-problem-fajciarov-problem-divochov-🚬/)

### **1. Zistite, o akú kombináciu synchronizačných problémov sa jedná.**

V našej úlohe sa jedná o zosynchronizovanie prístupu N divochov a M kuchárov k jednému hrncu z ktorého divosi jedia a kuchári do neho naberajú jedlo. Problém je založený na príncipe procenti/konzumenti, pričom kuchári majú prístup ku hrncu (spoločné úložisko) len keď je prázdny, to zistia vždy ak jedného z nich hladný dicoch zobudí. Divosi majú prístup ku hrncu len vtedy ak je v ňom jedlo (ktoré kuchári navarili), ak divoch zistí že už je hrniec prázdny, zobudí kuchárov. 
Musíme vykonať správne synchronizačné úkony, ako prvé som nastavil aby bol hrniec prázdny a aby začali jesť divosi. Túto kombináciu som zabezpečil s nastavením semaforu na 1 v premennej emptyPot a vyvolaním wait na Evente na kuchároch. Prvý divoch teda zistí že je hrniec prázdny a vyvolaním signálu na Event zobudí kuchárov, následne čaká na signál od kuchárov že je hrniec plný, toto zabezpečí metóda unlock v Lightswitchi ktou vyvolá posledný kuchár.
Problém aby mal pristúp ku hrncu počas naberania iba jeden dicoch sme vyriešili Mutexom.

### **2. Napíšte pseudokód riešenia úlohy.**

```
# inicializácia počtu divochov, kuchárov a veľkosti hrnca (počet porcií)
savages = 4
potSize = 6
cookers = 4


class Shared:
    def __init__(self):
        self.servings = 0
        self.mutex = Mutex()
        self.event = Event()
        self.emptyPot = Semaphore(1)
        self.fullPot = Semaphore(0)
        self.ls = Lightswitch()


class Lightswitch:
    def __init__(self):
        self.mutex = Mutex()
        self.counter = 0

    def lock(self, shared):
        self.mutex.lock()
        self.counter += 1
        counter = self.counter
        if self.counter je rovný 1:
            shared.emptyPot.wait()
        self.mutex.unlock()
        return counter

    def unlock(self, shared):
        self.mutex.lock()
        self.counter -= 1
        # podmienku nizsie zavola len posledny kuchár ktory prišiel do Lightswitchu
        if self.counter je rovný 0:
            # ak všetci kuchári dovarili, nastavím veľkosť hrnca (porcií) na potSize
            shared.servings = potSize
            # hrniec je plný, posledný kuchár môže dať signál divochom
            print(f'NAVARENE,hrniec plny. Pocet porcii = {potSize}')
            shared.emptyPot.signal()
            shared.fullPot.signal()
        self.mutex.unlock()

def eat(savage_id):
    print("divoch %2d: hoduje" % savage_id)
    sleep(randint(50 až 100ms)

def savage(i, shared):
    sleep(randint(1 až 100ms)
    while True:
        shared.mutex.lock()
        if shared.servings je rovný 0:
            print(f'divoch{i}: prazdny hrniec')
            # divoch ktorý zistí že je prázdny hrniec, dá signál kuchárom
            shared.event.signal()
            # zabezpečíme znovupoužitie Eventu
            shared.event.clear() 
            # čakáme kým dostaneme signál od kuchára že je hrniec plný
            shared.fullPot.wait()
        shared.servings -= 1
        print(f'divoch{i}: nabera jest, pocet porcii v hrnci = {shared.servings} ')
        shared.mutex.unlock()
        eat(i)

def cook(i, shared):
    while True:
        # kuchári začínajú variť keď im divoch pošle signál
        shared.event.wait()
        # prvý kuchár zamkne prístup ku hrncu, všetci kuchári ale ku nemu majú prístup
        shared.ls.lock(shared)
        print(f'varenie kuchara: {i}')
        sleep(randint(50 až 100ms)
        shared.ls.unlock(shared)

def main():
    shared = Shared()
    savagee = [Thread(savage, i+1, shared) for i in range(savages)]
    cookk = [Thread(cook, j+1, shared) for j in range(cookers)]

    for thread in savagee + cookk:
        thread.join()
```
### **3. Umiestnite vhodné výpisy na overenie funkčnosti modelu**

Nižšie je vidieť časť výpisov z kódu. Na začiatku môžeme vidieť že divosi hodujú a divoch2 zistí že je prázdny hrniec. Dá signál kuchárom ktorý postupne začnú variť, posledný kuchár nastaví počet porcií na 6. Divosi môžu začať opäť jesť a zopakovať tento proces odznova. 

```

...
divoch3: nabera jest, pocet porcii v hrnci = 1 
divoch  3: hoduje
divoch4: nabera jest, pocet porcii v hrnci = 0 
divoch  4: hoduje
divoch2: prazdny hrniec
varenie kuchara: 3
varenie kuchara: 1
varenie kuchara: 2
varenie kuchara: 4
NAVARENE,hrniec plny. Pocet porcii = 6
divoch2: nabera jest, pocet porcii v hrnci = 5 
divoch  2: hoduje
divoch1: nabera jest, pocet porcii v hrnci = 4 
divoch  1: hoduje
divoch4: nabera jest, pocet porcii v hrnci = 3 
divoch  4: hoduje
divoch3: nabera jest, pocet porcii v hrnci = 2 
divoch  3: hoduje
divoch2: nabera jest, pocet porcii v hrnci = 1 
divoch  2: hoduje
divoch1: nabera jest, pocet porcii v hrnci = 0 
divoch  1: hoduje
divoch3: prazdny hrniec
varenie kuchara: 1
varenie kuchara: 4
varenie kuchara: 2
varenie kuchara: 3
NAVARENE,hrniec plny. Pocet porcii = 6
divoch3: nabera jest, pocet porcii v hrnci = 5 
divoch  3: hoduje
divoch4: nabera jest, pocet porcii v hrnci = 4 
divoch  4: hoduje
divoch1: nabera jest, pocet porcii v hrnci = 3 
divoch  1: hoduje
...

```

### **3. Zvoľte vhodne charakteristiky, na ktorých sa model zakladá (počty vlákien, časovania aktivít, hodnoty ďalších premenných).**

Na úkažku som zvolil vačší počet porcií ktoré signalizujú plný hrniec ako je počet divochov alebo kuchárov. Je tým pekne vidieť ako si môže jeden divoch naberáť z hrnca viac krát ako raz počas jedneho kola.

