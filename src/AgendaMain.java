import java.util.ArrayList;
import java.util.List;

public class AgendaMain {

    List<Person> agenda = new ArrayList<>();

    private void listAll() {
        for (Person p: agenda
                ) {

            System.out.println(p.getName());
            System.out.println(p.getPhone());
            System.out.println("------------");

        }
    }

    private void add(String name, String phone) {

        Person p = new Person();
        p.setName(name);
        p.setPhone(phone);

        agenda.add(p);

    }

    private void remove(int index) {


        agenda.remove(index);

    }


    private String find(String name) {

        String number = "0";
        for (Person p: agenda
                ) {

            if(p.getName().equalsIgnoreCase(name)) {
                number = p.getPhone();
            }
        }

        return number;
    }


    public static void main(String[] args) {

        AgendaMain am = new AgendaMain();
        am.listAll();

        am.add("ionel", "547566435765");
        am.add("alina", "68678658");



        String telefon = am.find("gdfhdfgd");

        System.out.println("tel:"+telefon);


//        am.listAll();
//
//        am.remove(1);
//
//        am.listAll();


    }
}