import threading
import time
import random

class Filosofo(threading.Thread):
    def __init__(self, id, garfo_esquerda, garfo_direita):
        super().__init__()
        self.id = id
        self.garfo_esquerda = garfo_esquerda
        self.garfo_direita = garfo_direita

    def pensar(self):
        print(f"Filósofo {self.id} está pensando.")
        time.sleep(random.uniform(1, 3))

    def comer(self):
        print(f"Filósofo {self.id} está comendo.")
        time.sleep(random.uniform(1, 3))

    def run(self):
        while True:
            self.pensar()
            primeiro_garfo, segundo_garfo = (self.garfo_esquerda, self.garfo_direita) if self.id % 2 == 0 else (self.garfo_direita, self.garfo_esquerda)
            with primeiro_garfo:
                print(f"Filósofo {self.id} pegou o primeiro garfo.")
                with segundo_garfo:
                    print(f"Filósofo {self.id} pegou o segundo garfo e está pronto para comer.")
                    self.comer()
                print(f"Filósofo {self.id} soltou o segundo garfo.")
            print(f"Filósofo {self.id} soltou o primeiro garfo.")

if __name__ == "__main__":
    num_filosofos = 5
    garfos = [threading.Lock() for _ in range(num_filosofos)]
    filosofos = []

    for i in range(num_filosofos):
        filosofo = Filosofo(i, garfos[i], garfos[(i + 1) % num_filosofos])
        filosofos.append(filosofo)

    for filosofo in filosofos:
        filosofo.start()

    try:
        for filosofo in filosofos:
            filosofo.join()
    except KeyboardInterrupt:
        print("Encerrando o programa...")
