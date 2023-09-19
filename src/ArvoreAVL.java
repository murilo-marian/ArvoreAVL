import java.util.ArrayList;

public class ArvoreAVL {
    private class Nodo {
        private int dado, altd, alte, quantia;
        private Nodo dir, esq;

        public Nodo(int dado) {
            this.dado = dado;
            dir = esq = null;
            altd = alte = 0;

        }
    }

    Nodo raiz;

    public ArvoreAVL() {
        raiz = null;
    }

    public void inserir(int dado) {
        raiz = inserirDado(raiz, dado);
    }


    private Nodo inserirDado(Nodo raiz, int dado) {
        if (raiz == null) {
            raiz = new Nodo(dado);
            raiz.quantia++;
            return raiz;
        }

        if (dado < raiz.dado) {
            raiz.esq = inserirDado(raiz.esq, dado);
            if (raiz.esq.altd > raiz.esq.alte) {
                raiz.alte = raiz.esq.altd + 1;
            } else {
                raiz.alte = raiz.esq.alte + 1;
            }
            raiz = balanceamento(raiz);
        } else if (dado > raiz.dado) {
            raiz.dir = inserirDado(raiz.dir, dado);
            if (raiz.dir.altd > raiz.dir.alte) {
                raiz.altd = raiz.dir.altd + 1;
            } else {
                raiz.altd = raiz.dir.alte + 1;
            }
            raiz = balanceamento(raiz);
        } else if (dado == raiz.dado) {
            raiz.quantia++;
        }
        return raiz;
    }

    private Nodo balanceamento(Nodo raiz) {
        if (raiz != null) {
            int fb = raiz.altd - raiz.alte;
            int fbSubArv;
            if (fb == 2) {
                fbSubArv = raiz.dir.altd - raiz.dir.alte;
                if (fbSubArv >= 0) {
                    raiz = rotacao_esquerda(raiz);
                } else {
                    raiz.dir = rotacao_direita(raiz.dir);
                    raiz = rotacao_esquerda(raiz);
                }
            } else if (fb == -2) {
                fbSubArv = raiz.esq.altd - raiz.esq.alte;
                if (fbSubArv <= 0) {
                    raiz = rotacao_direita(raiz);
                } else {
                    raiz.esq = rotacao_esquerda(raiz.esq);
                    raiz = rotacao_direita(raiz);
                }
            }
            return raiz;
        } else {
            return null;
        }
    }

    public void balancearTudo() {
        balancearArvore(raiz);
    }

    private void balancearArvore(Nodo raiz) {
        raiz = balanceamento(raiz);
        if (raiz.esq != null) {
            balancearArvore(raiz.esq);
        }
        if (raiz.dir != null) {
            balancearArvore(raiz.dir);
        }
    }

    private Nodo rotacao_esquerda(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.dir;
        aux2 = aux1.esq;
        raiz.dir = aux2;
        aux1.esq = raiz;
        if (raiz.dir == null) {
            raiz.altd = 0;
        } else if (raiz.dir.alte > raiz.dir.altd) {
            raiz.altd = raiz.dir.alte + 1;
        } else {
            raiz.altd = raiz.dir.altd + 1;
        }
        if (aux1.esq.alte > aux1.esq.altd) {
            aux1.alte = aux1.esq.alte + 1;
        } else {
            aux1.alte = aux1.esq.altd + 1;
        }
        return aux1;
    }

    private Nodo rotacao_direita(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.esq;
        aux2 = aux1.dir;
        raiz.esq = aux2;
        aux1.dir = raiz;
        if (raiz.esq == null) {
            raiz.alte = 0;
        } else if (raiz.esq.alte > raiz.esq.altd) {
            raiz.alte = raiz.esq.alte + 1;
        } else {
            raiz.alte = raiz.esq.altd + 1;
        }
        if (aux1.dir.alte > aux1.dir.altd) {
            aux1.altd = aux1.dir.alte + 1;
        } else {
            aux1.altd = aux1.dir.altd + 1;
        }
        return aux1;
    }

    public void remover(int chave) {
        raiz = removerItem(raiz, chave);
    }

    private Nodo removerItem(Nodo raiz, int chave) { //NÃO CALCULA A ALTURA AINDA, TEM QUE ARRUMAR
        if (raiz == null) {
            //nó 404
            return null;
        }
        if (chave < raiz.dado) {
            //chave a ser removida está a esquerda
            raiz.esq = removerItem(raiz.esq, chave);
        } else if (chave > raiz.dado) {
            //chave a ser removida está a direita
            raiz.dir = removerItem(raiz.dir, chave);
        } else {
            //nó encontrado
            if (raiz.quantia > 1) {
                raiz.quantia--;
            } else if (raiz.esq == null) {
                //caso em que o nó não possui filho a esquerda
                raiz.alte = 0;
                return raiz.dir;
            } else if (raiz.dir == null) {
                //caso em que o nó não possui filha a direita
                raiz.altd = 0;
                return raiz.esq;
            } else {
                //caso em que o nó possui ambos os filhos
                //nó sucessor sereá o menor da subárvoreda direita
                Nodo sucessor = encontrarSucessor(raiz.dir);
                raiz.dado = sucessor.dado;
                raiz.dir = removerItem(raiz.dir, sucessor.dado);
            }
        }
        return raiz;
    }

    private Nodo encontrarSucessor(Nodo nodo) {
        while (nodo.esq != null) {
            nodo = nodo.esq;
        }
        return nodo;
    }

    public int contarPrimos() {
        return somaPrimos(raiz);
    }

    private int somaPrimos(Nodo raiz) {
        int contador = 0;
        if (raiz != null) {
            if (isPrimo(raiz.dado)) {
                contador++;
            }
            contador += somaPrimos(raiz.esq);
            contador += somaPrimos(raiz.dir);
        }
        return contador;
    }

    public static boolean isPrimo(int numero) {
        if (numero <= 1) {
            return false; // 0 e 1 não são primos
        }

        //Verifica se o número é divisível por qualquer número de 2 até a raiz quadrada dele
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false; //Se for divisível, não é primo
            }
        }

        return true; //Se não foi divisível por nenhum número, é primo
    }

    public void mostrarNivel(int especifico) {
        mostraNivel(raiz, 1, especifico);
    }

    private void mostraNivel(Nodo raiz, int nivel, int especifico) {
        if (raiz != null) {
            if (nivel < especifico) {
                mostraNivel(raiz.dir, nivel + 1, especifico);
                mostraNivel(raiz.esq, nivel + 1, especifico);
            } else if (nivel == especifico) {
                System.out.println(raiz.dado);
            }
        }
    }

    public int somarImpar() {
        return somarDadosImpares(raiz);
    }

    private int somarDadosImpares(Nodo raiz) {
        int soma = 0;
        if (raiz != null) {
            soma = raiz.dado;

            if (raiz.dir != null) {
                soma += somarDadosImpares(raiz.dir.dir);
                soma += somarDadosImpares(raiz.dir.esq);
            }
            if (raiz.esq != null) {
                soma += somarDadosImpares(raiz.esq.dir);
                soma += somarDadosImpares(raiz.esq.esq);
            }
        }
        return soma;
    }

    public void mostrarEmOrdem() {
        mostrandoOrdenado(raiz);
    }

    private void mostrandoOrdenado(Nodo raiz) {
        if (raiz != null) {
            mostrandoOrdenado(raiz.esq);
            System.out.println(raiz.dado + " - " + raiz.quantia + " vezes");
            mostrandoOrdenado(raiz.dir);
        }
    }

    public void mostrarChar() {
        mostrarCharacter(raiz);
        balancearTudo();
    }

    private void mostrarCharacter(Nodo raiz) {
        if (raiz != null) {
            System.out.println("Raiz atual: " + (char) raiz.dado);
            if (raiz.esq != null) {
                System.out.println("Filho esquerda: " + (char) raiz.esq.dado);
            }
            if (raiz.dir != null) {
                System.out.println("Filho direita: " + (char) raiz.dir.dado);
            }
            System.out.println("-");
            mostrarCharacter(raiz.esq);
            mostrarCharacter(raiz.dir);
        }
    }

    public void mostrar() {
        mostrarNormal(raiz);
    }

    private void mostrarNormal(Nodo raiz) {
        if (raiz != null) {
            System.out.println("Raiz atual: " + raiz.dado + " quantidade: " + raiz.quantia);
            if (raiz.esq != null) {
                System.out.println("Filho esquerda: " + raiz.esq.dado + " quantidade: " + raiz.quantia);
            }
            if (raiz.dir != null) {
                System.out.println("Filho direita: " + raiz.dir.dado + " quantidade: " + raiz.quantia);
            }
            System.out.println("-");
            mostrarNormal(raiz.esq);
            mostrarNormal(raiz.dir);
        }
    }

    public boolean isAVL() {
        return testeAVL(raiz);
    }

    private boolean testeAVL(Nodo raiz) {
        if (raiz != null) {
            int fb = raiz.altd - raiz.alte;
            if (fb == 2 || fb == -2) {
                return false;
            }
            boolean esquerda = testeAVL(raiz.esq);
            boolean direita = testeAVL(raiz.dir);
            if (esquerda && direita) {
                return true;
            } else {
                return false;
            }
        } else return true;
    }
}