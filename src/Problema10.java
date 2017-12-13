/*
10. la aplicatia agenda de persoane facuta in lab 5 (sau pe acolo) sa putem salva si restaura persoanele dintr-un fisier text de forma

ionel, 0722463545345
maria, +56345634466
crina, 04656457567

eventual pe langa citire si scriere sa putem face si restul operatiilor … (optional )
 */

import fileoperation.FileOperationImpl1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Problema10 {
    public static void main(String[] args) {
        List<String> myFile = new FileOperationImpl1().readFromFileAsList("Agenda.txt");
        Problema10 obj = new Problema10();

        //creare agenda
        List<Person> myAgenda = obj.createAgenda(myFile);

        int optiune = -1;
        do {
            obj.afisareMeniu();
            Scanner sc = new Scanner(System.in);
            try {
                optiune = sc.nextInt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (optiune) {
                case 1: {
                    obj.afisareAgenda(myAgenda);
                    break;
                }

                case 2: {
                    obj.adaugareAgenda(myAgenda);
                    break;
                }

                case 3: {
                    obj.stergere(myAgenda);
                    break;
                }

                case 4: {
                    obj.cautareSimpla(myAgenda);
                    break;
                }

                case 5: {
                    obj.modificare(myAgenda);
                    break;
                }
            }
        }
        while (optiune != 0);

    }

    public List<Person> createAgenda(List<String> myFile) {
        List<Person> myAgenda = new ArrayList<>();

        for (String s : myFile
                ) {
            String[] column = s.split(", ", 2);
            Person p = new Person();
            p.setName(column[0]);
            p.setPhone(column[1]);
            myAgenda.add(p);
        }
        return myAgenda;
    }

    public void afisareMeniu() {
        System.out.println();
        System.out.println("1> Afisare");
        System.out.println("2> Adaugare");
        System.out.println("3> Stergere");
        System.out.println("4> Cautare simpla");
        System.out.println("5> Modificare");
        System.out.println("0> Exit");
    }

    public void afisareAgenda(List<Person> myAgenda) {
        System.out.println("Contacte: ");
        for (Person p : myAgenda
                ) {
            System.out.println(p.getName() + " " + p.getPhone());
        }
    }

    private String userInput(String label) {
        System.out.print(label);
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        return input;
    }

    private void adaugareAgenda(List<Person> myAgenda) {
        String name = userInput("Insert name: ");
        String phone = userInput("Insert phonenumber: ");
        FileOperationImpl1 obj = new FileOperationImpl1();

        //adaugare persoana noua in agenda arraylist
        Person p = new Person();
        p.setName(name);
        p.setPhone(phone);
        myAgenda.add(p);

        //adaugare persoana noua in documentul text
        String newContact = name + ", " + phone;
        if (myAgenda.size() == 1) {
            obj.writeFile("Agenda.txt", newContact);
        } else {
            obj.writeFile("Agenda.txt", "\n" + newContact);
        }
    }


    private void stergere(List<Person> myAgenda) {
        List<Integer> arrayOfIndexes = cautareSimpla(myAgenda);
        int indexes = arrayOfIndexes.size();

        if (indexes == 1) {                                     //Daca s-a gasit doar o persoana care corespunde, se va sterge automat din agenda
            int position = arrayOfIndexes.get(0);

            //Stergere din arrayul de persoane
            myAgenda.remove(position);
            System.out.println("Removal has been succesfully done.");
        }
        else if (indexes > 1) {                  //daca corespund mai multe persoane, se va tasta nr corespunzator uneia dintre ele si va fi stearsa
            String position = userInput("Introduce the number corresponding to the person you want to delete: ");
            try {
                int positionInt = Integer.parseInt(position);

                for (int i = 0; i < arrayOfIndexes.size(); i++) {
                    if (i == positionInt) {
                        int positionInAgenda = arrayOfIndexes.get(i);
                        myAgenda.remove(positionInAgenda);
                        System.out.println("Removal has been succesfully done.");
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //golire agenda si repopulare cu contactele ramase
        rescriereAgenda(myAgenda);
    }

    private void rescriereAgenda(List<Person> myAgenda) {
        if (myAgenda.size() > 0) {
            new FileOperationImpl1().writeFileMakeItEmpty("Agenda.txt", "");             //goleste agenda complet
            String nameN = myAgenda.get(0).getName();
            String phoneP = myAgenda.get(0).getPhone();
            String newContact = nameN + ", " + phoneP;
            new FileOperationImpl1().writeFile("Agenda.txt", newContact);
            for (int i = 1; i < myAgenda.size(); i++) {
                String name = myAgenda.get(i).getName();
                String phone = myAgenda.get(i).getPhone();
                String contact = name + ", " + phone;
                new FileOperationImpl1().writeFile("Agenda.txt", "\n" + contact);
            }
        }
    }
//
//    private void removeLine(String lineContent) throws IOException
//    {
//        File file = new File("myAgenda.txt");
//        List<String> out = Files.lines(file.toPath())
//                .filter(line -> !line.contains(lineContent))
//                .collect(Collectors.toList());
//        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
//    }


    private List<Integer> cautareSimpla(List<Person> myAgenda) {
        String input = userInput("Insert the person you are searching for: ");
        int counter = 0;
        List<Integer> arrayOfIndexes = new ArrayList<>();

        for (int i = 0; i < myAgenda.size(); i++) {
            Person p = myAgenda.get(i);
            if (p.getName().contains(input) || p.getPhone().contains(input)) {
                System.out.println(counter + ". " + p.getName() + " " + p.getPhone());
                counter++;
                arrayOfIndexes.add(i);
            }
        }

        if (counter == 0) {
            System.out.println("You do not have this person in your agenda.");
        }

        return arrayOfIndexes;
    }


    private void modificare(List<Person> myAgenda) {
        List<Integer> arrayOfIndexes = cautareSimpla(myAgenda);

        //doar un contact corespunde
        if (arrayOfIndexes.size() == 1) {
            int position = arrayOfIndexes.get(0);
            modificareEfectiva(myAgenda, position);
        }
        //corespund mai multe contacte
        if (arrayOfIndexes.size() > 1) {
            String position = userInput("Introduce the number corresponding to the person you want to change: ");
            try {
                int positionInt = Integer.parseInt(position);

                for (int i = 0; i < arrayOfIndexes.size(); i++) {
                    if (i == positionInt) {
                        int positionInAgenda = arrayOfIndexes.get(i);
                        modificareEfectiva(myAgenda, positionInAgenda);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //modificarea si in documentul text
        if (arrayOfIndexes.size() > 0) {
            rescriereAgenda(myAgenda);
        }
    }

    private void modificareEfectiva(List<Person> myAgenda, int position) {
        String input = userInput("Press 1 for changing the name or 2 for changing the phonenumber: ");
        int choice = 0;
        try{
            choice = Integer.parseInt(input);
        } catch(InputMismatchException e) {
            System.out.println("Invalid input!");
        }
        if(choice == 1) {
            String newName = userInput("Write the new name: ");
            myAgenda.get(position).setName(newName);
        }
        else if(choice == 2) {
            String newPhone = userInput("Write the new phonenumber: ");
            myAgenda.get(position).setPhone(newPhone);
        } else {
            System.out.println("Invalid input!");
        }
    }
}
