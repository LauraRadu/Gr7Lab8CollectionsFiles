
import java.util.ArrayList;
import java.util.List;

public class DemoCollections {

    public static void main(String[] args) {

        DemoCollections d = new DemoCollections();
        d.demoListe();

    }

    private void demoListe() {

        List<Integer> listaDeNumere = new ArrayList<>();
        listaDeNumere.add(1);
        listaDeNumere.add(5);
       // listaDeNumere.add("ana are mere");
        listaDeNumere.add(12);


        listaDeNumere.remove(0);


        for (int i = 0; i < listaDeNumere.size(); i++) {


            int a = (int) listaDeNumere.get(i);
            System.out.println(a);

        }

        int contor =0;
        for (int elementCurent : listaDeNumere
                ) {
            System.out.println(elementCurent);
            contor++;

        }

    }


}
