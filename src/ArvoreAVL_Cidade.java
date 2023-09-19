public class ArvoreAVL_Cidade {
    private class Nodo {
        private int altd, alte;
        private String nomeMunicipio;
        private double area;
        private int populacao;
        private ArvoreAVL_Cidade.Nodo dir, esq;

        public Nodo(String nomeMunicipio, double area, int populacao) {
            dir = esq = null;
            altd = alte = 0;
            this.nomeMunicipio = nomeMunicipio;
            this.area = area;
            this.populacao = populacao;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Nodo{");
            sb.append("altd=").append(altd);
            sb.append(", alte=").append(alte);
            sb.append(", nomeMunicipio='").append(nomeMunicipio).append('\'');
            sb.append(", area=").append(area);
            sb.append(", populacao=").append(populacao);
            sb.append('}');
            return sb.toString();
        }
    }

    ArvoreAVL_Cidade.Nodo raiz;

    public ArvoreAVL_Cidade() {
        raiz = null;
    }

    public void inserir(String nomeMunicipio, double area, int populacao) {
        raiz = inserirDado(raiz, nomeMunicipio, area, populacao);
    }


    private ArvoreAVL_Cidade.Nodo inserirDado(ArvoreAVL_Cidade.Nodo raiz, String nomeMunicipio, double area, int populacao) {
        if (raiz == null) {
            raiz = new ArvoreAVL_Cidade.Nodo(nomeMunicipio, area, populacao);
            return raiz;
        }

        if (raiz.nomeMunicipio.compareToIgnoreCase(nomeMunicipio) > 0) {
            raiz.esq = inserirDado(raiz.esq, nomeMunicipio, area, populacao);
            if (raiz.esq.altd > raiz.esq.alte) {
                raiz.alte = raiz.esq.altd + 1;
            } else {
                raiz.alte = raiz.esq.alte + 1;
            }
            raiz = balanceamento(raiz);
        } else if (raiz.nomeMunicipio.compareToIgnoreCase(nomeMunicipio) < 0) {
            raiz.dir = inserirDado(raiz.dir, nomeMunicipio, area, populacao);
            if (raiz.dir.altd > raiz.dir.alte) {
                raiz.altd = raiz.dir.altd + 1;
            } else {
                raiz.altd = raiz.dir.alte + 1;
            }
            raiz = balanceamento(raiz);
        } else if (raiz.nomeMunicipio.compareToIgnoreCase(nomeMunicipio) == 0) {
            System.out.println("Cidade repetida");
            return null;
        }
        return raiz;
    }

    private ArvoreAVL_Cidade.Nodo balanceamento(ArvoreAVL_Cidade.Nodo raiz) {
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

    private void balancearArvore(ArvoreAVL_Cidade.Nodo raiz) {
        raiz = balanceamento(raiz);
        if (raiz.esq != null) {
            balancearArvore(raiz.esq);
        }
        if (raiz.dir != null) {
            balancearArvore(raiz.dir);
        }
    }

    private ArvoreAVL_Cidade.Nodo rotacao_esquerda(ArvoreAVL_Cidade.Nodo raiz) {
        ArvoreAVL_Cidade.Nodo aux1, aux2;
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

    private ArvoreAVL_Cidade.Nodo rotacao_direita(ArvoreAVL_Cidade.Nodo raiz) {
        ArvoreAVL_Cidade.Nodo aux1, aux2;
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

   /* public void remover(int chave) {
        raiz = removerItem(raiz, chave);
        balancearTudo();
    }

    private ArvoreAVL_Cidade.Nodo removerItem(ArvoreAVL_Cidade.Nodo raiz, int chave) { //NÃO CALCULA A ALTURA AINDA, TEM QUE ARRUMAR
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
            if (raiz.esq == null) {
                //caso em que o nó não possui filho a esquerda
                return raiz.dir;
            } else if (raiz.dir == null) {
                //casoo em que o nó não possui filha a direita
                return raiz.esq;
            } else {
                //caso em que o nó possui ambos os filhos
                //nó sucessor sereá o menor da subárvoreda direita
                ArvoreAVL_Cidade.Nodo sucessor = encontrarSucessor(raiz.dir);
                raiz.dado = sucessor.dado;
                raiz.dir = removerItem(raiz.dir, sucessor.dado);
            }
        }
        return raiz;
    }*/

    private ArvoreAVL_Cidade.Nodo encontrarSucessor(ArvoreAVL_Cidade.Nodo nodo) {
        while (nodo.esq != null) {
            nodo = nodo.esq;
        }
        return nodo;
    }

    public void mostrar() {
        mostrarNormal(raiz);
    }

    private void mostrarNormal(ArvoreAVL_Cidade.Nodo raiz) {
        if (raiz != null) {
            System.out.println("Raiz atual: " + raiz.toString());
            if (raiz.esq != null) {
                System.out.println("Filho esquerda: " + raiz.esq.toString());
            }
            if (raiz.dir != null) {
                System.out.println("Filho direita: " + raiz.dir.toString());
            }
            System.out.println("-");
            mostrarNormal(raiz.esq);
            mostrarNormal(raiz.dir);
        }
    }

    //8 - A)
    public int contarNodos() {
        return contadorNodos(raiz);
    }

    private int contadorNodos(ArvoreAVL_Cidade.Nodo raiz) {
        int nodos = 0;
        if (raiz != null) {
            nodos++;
            nodos += contadorNodos(raiz.esq);
            nodos += contadorNodos(raiz.dir);
        }
        return nodos;
    }

    //8 - B)
    public void mostrarMaiorQue(int x) {
        mostrarGrandes(raiz, x);
    }

    private void mostrarGrandes(ArvoreAVL_Cidade.Nodo raiz, int x) {
        if (raiz != null) {
            if (raiz.populacao > x) {
                System.out.println(raiz.nomeMunicipio + "tem mais que " + x + " habitantes");
            }
            mostrarGrandes(raiz.esq, x);
            mostrarGrandes(raiz.dir, x);
        }
    }

    //8 - C)
    public void mostrarDensidade() {
        densidadePopulacional(raiz);
    }

    private void densidadePopulacional(ArvoreAVL_Cidade.Nodo raiz) {
        if (raiz != null) {
            double densidade = raiz.populacao / (raiz.area / 1000);
            System.out.println("Densidade populacional de " + raiz.nomeMunicipio + ": " + densidade);
            densidadePopulacional(raiz.esq);
            densidadePopulacional(raiz.dir);
        }
    }

    //8 - D)
    public double mostrarPorcentagemTerritorio() {
        double areaTotal = calcularAreaTotal(raiz);

        return (areaTotal * 100) / 8510000;
    }

    private double calcularAreaTotal(ArvoreAVL_Cidade.Nodo raiz) {
        double areaTotal = 0;
        if (raiz != null) {
            areaTotal = raiz.area;
            areaTotal += calcularAreaTotal(raiz.esq);
            areaTotal += calcularAreaTotal(raiz.dir);
        }
        return areaTotal / 1000; //m² pra km²
    }

    //8 - E)
    public Nodo mostrarMaiorPopulacao() {
        return calcularMaiorPopulacao(raiz);
    }

    private Nodo calcularMaiorPopulacao(ArvoreAVL_Cidade.Nodo raiz) {
        Nodo maior = raiz;
        if (raiz != null) {
            Nodo esq = calcularMaiorPopulacao(raiz.esq);
            Nodo dir = calcularMaiorPopulacao(raiz.dir);
            if (esq != null) {
                if (maior.populacao < esq.populacao) {
                    maior = esq;
                }
            }
            if (dir != null) {
                if (maior.populacao < dir.populacao) {
                    maior = dir;
                }
            }
        }
        return maior;
    }
}
