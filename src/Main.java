public class Main {
    public static void main(String[] args) {
        ArvoreAVL arvoreAVL = new ArvoreAVL();

        /*String palavra = "questaofcil";

        for (int i = 0; i < palavra.length(); i++) {
             char character = palavra.charAt(i);

            arvoreAVL.inserir(character);
            System.out.println("-------------------------");
        }*/

        arvoreAVL.inserir(5);
        arvoreAVL.inserir(3);
        arvoreAVL.inserir(2);
        arvoreAVL.inserir(7);
        arvoreAVL.inserir(4);
        arvoreAVL.inserir(22);
        arvoreAVL.inserir(23);
        arvoreAVL.inserir(24);
        arvoreAVL.inserir(8);
        arvoreAVL.inserir(2);

        //3
        System.out.println(arvoreAVL.isAVL());

        //4
        System.out.println(arvoreAVL.contarPrimos());

        //5
        arvoreAVL.inserir(2);
        arvoreAVL.mostrar();


        //6
        arvoreAVL.mostrarNivel(1);

        //7
        System.out.println(arvoreAVL.somarImpar());

        System.out.println("---------------------------------");

    }
}
