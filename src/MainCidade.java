public class MainCidade {
    public static void main(String[] args) {
        ArvoreAVL_Cidade teste = new ArvoreAVL_Cidade();
        teste.inserir("Alfredo Wagner", 3000000d, 3);
        teste.inserir("Moji das Cruzes", 2000000d, 190);
        teste.inserir("Cubatão", 10000d, 99999999);
        teste.inserir("Floripa", 674844d, 537200);

        teste.mostrar();

        System.out.println("Número de Cidades: " + teste.contarNodos());
        teste.mostrarMaiorQue(100000);
        teste.mostrarDensidade();
        System.out.println(teste.mostrarPorcentagemTerritorio());
        System.out.println(teste.mostrarMaiorPopulacao());

    }
}
