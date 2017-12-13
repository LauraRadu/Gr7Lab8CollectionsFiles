/*
9. se da un fisier text de forma
ionel, password1
maria, password2
crina, password3

cerinta este sa se poata lansa notepad.exe doar daca userul introduce o combinatie user/parola dintre cele de mai sus
se poate folosi un Map/HashMap
de ex pt gasit in map se poate folosi un cod ca si acesta
String u , p ; // user si parola citite de la tastatura

Map<String,String> pwdStruct = new HashMap<String,String>();
/// aici punem din fisier in map
//apoi cand cautam facem cam asa

 for (Map.Entry<String, String> entry : pwdStruct.entrySet()) {
                if(u.equals(entry.getKey()) && p.equals(entry.getValue())) {
                // daca intra aici inseama ca userul a introdus in u si p ceva ce exista in fisier
}
 */

import fileoperation.FileOperationImpl1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogInProblema9 {
    public static void main(String[] args) {
        List<String> myFile = new FileOperationImpl1().readFromFileAsList("ConturiDeLogare.txt");

        LogInProblema9 obj = new LogInProblema9();
        Map<String, String> myAgenda = obj.createAgenda(myFile);

        obj.launchNotepad(myAgenda);
    }

    public Map<String, String> createAgenda(List<String> myFile) {
        Map<String, String> myAgenda = new HashMap<>();

        for (String s : myFile
                ) {
            s = s.replaceAll(",", "");
            String[] column = s.split(" ", 2);
            String user = column[0];
            String password = column[1];
            myAgenda.put(user, password);
        }
        return myAgenda;
    }

    private String input(String label) {
        Scanner scan = new Scanner(System.in);
        System.out.print(label);
        String inputFromUser = scan.nextLine();
        while (inputFromUser.equals("")) {
            System.out.print(label);
            inputFromUser = scan.nextLine();
        }
        return inputFromUser;
    }

    private boolean askForCredentials(Map<String, String> map) {
        String user = input("Username: ");
        String password = input("Password: ");
        boolean loggedIn = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (user.equals(entry.getKey()) && password.equals(entry.getValue())) {
                loggedIn = true;
            }
        }
        return loggedIn;
    }

    private void launchNotepad(Map<String, String> map) {
        boolean loggedIn = askForCredentials(map);

        if (loggedIn) {
            Runtime rs = Runtime.getRuntime();

            try {
                rs.exec("notepad");
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("User or password incorrect!");
        }
    }
}
