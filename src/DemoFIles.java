import fileoperation.FileOperationImpl1;
import fileoperation.FileOperations;

import java.util.*;

public class DemoFIles {

    int lyrics(List<String> poem) {
        int counter = 0;
        for (String vers : poem
                ) {
            if (!(vers.isEmpty() || vers.startsWith("...") || vers.startsWith("http")))
                counter++;
        }
        return counter;
    }

    int numberStrofe(List<String> poemList) {
        //cate strofe sunt?
        int count = 1;
        for (String vers : poemList
                ) {
            if (vers.isEmpty()) {
                count++;
            }
            if (vers.startsWith("...")) {
                count--;
            }
        }
        return count;

        //a 2-a varianta de cate srofe sunt
//        int counting = 1;
//        for (int i = 0; i < poemList.size(); i++) {
//            if (poemList.get(i).isEmpty() && !poemList.get(i - 1).startsWith("...")) {
//                counting++;
//            }
//        }
//        System.out.println(counting);
    }


    int repeatingWord(List<String> poem) {
        int counter = 0;
        for (String vers : poem
                ) {

            StringTokenizer st = new StringTokenizer(vers);      //iau versul curent ca sa-l impart in cuvinte
            while (st.hasMoreElements()) {
                String cuvant = (String) st.nextElement();
                if (cuvant.contains("stele")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private void nonuniqueWords(String poem) {
        StringTokenizer st = new StringTokenizer(poem);      //iau versul curent ca sa-l impart in cuvinte
        int counter = st.countTokens();
        while (st.hasMoreTokens()) {
            String cuvant = (String) st.nextToken();
            if (cuvant.contains("...") || cuvant.equals("-")) {
                counter--;
            }
        }
        System.out.println("The poem has " + counter + " words.");
    }

    int uniqueWords(String poem) {
        Set<String> words = new HashSet<>();

        //delete all unwanted symbols, mai putin cratimele, respectiv liniile de dialog
        //cuvintele cu cratima vor fi luate impreuna ca un cuvant
        poem = poem.replaceAll("[.,?!';:]", "");

        StringTokenizer st = new StringTokenizer(poem);
        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken().toLowerCase();
            if (!currentToken.equals("-")) {       //nu numara si liniile de dialog.
                words.add(currentToken);
            }
        }
        return words.size();
    }

    void repeatingWantedWord(String poem) {
        poem = poem.replaceAll("[.,?!';:]", "");

        StringTokenizer st = new StringTokenizer(poem);
        String wantedWord = "luna";
        int counter = 0;
        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();
            if (currentWord.equalsIgnoreCase(wantedWord)) {
                counter++;
            }
        }

        System.out.println("\"" + wantedWord + "\"" + " appears " + counter + " times in your text.");
    }

    void shortVerses(List<String> poem) {
        for (String s : poem
                ) {
            StringTokenizer st = new StringTokenizer(s);
            int words = st.countTokens();
            if (words < 4) {
                if (st.hasMoreTokens()) {
                    if (!st.nextToken().startsWith(".")) {
                        new FileOperationImpl1().writeFile("4words.txt", s + "\n");
                    }
                } else {           //printeaza si randurile goale dintre strofe
                    new FileOperationImpl1().writeFile("4words.txt", s + "\n");
                }
            }
        }
    }

    void verses(List<String> poem) {
        int shortest = Integer.MAX_VALUE;
        int longest = 0;

        String shortestVerse = "";
        String longestVerse = "";

        for (String s : poem) {
            if (s.startsWith("...")) {
                continue;
            } else {
                StringTokenizer st = new StringTokenizer(s);
                int length = st.countTokens();
                while (st.hasMoreTokens()) {
                    String currentWord = st.nextToken();
                    if (currentWord.equals("-")) {
                        length--;
                    }
                    if (length < shortest) {
                        shortest = length;
                        shortestVerse = s;
                    }
                    if (length > longest) {
                        longest = length;
                        longestVerse = s;
                    }
                }
            }
        }
        System.out.println("The first shortest verse has " + shortest + " words: " + shortestVerse);
        System.out.println("The first longest verse has " + longest + " words: " + longestVerse);
    }


    void longestWord(String poem) {
        poem = poem.replaceAll("[.,?!';:]", "");
        StringTokenizer st = new StringTokenizer(poem);
        int longestSize = 0;
        Set<String> words = new HashSet<>();          //un hashset in care vom pune cuvintele de aceeasi lungime, cu cea mai mare lungime. Daca se gaseste o noua lungime mai mare, se sterg cuvintele si se pun cele noi, cu lungimea cea mai mare

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();
            if (currentWord.length() > longestSize) {
                longestSize = currentWord.length();
                words.clear();
                words.add(currentWord);
            } else if (currentWord.length() == longestSize) {
                words.add(currentWord);
            }
        }

        System.out.println("The longest words have " + longestSize + " letters. They are: " + words);
    }

    void mostFrequentWord(String poem) {
        poem = poem.replaceAll("[.,?!';:]", "");
        StringTokenizer st = new StringTokenizer(poem);
        Map<String, Integer> words = new HashMap<>();

        while (st.hasMoreTokens()) {
            String currentWord = st.nextToken();

            if (words.containsKey(currentWord)) {
                words.put(currentWord, words.get(currentWord) + 1);
            } else {
                words.put(currentWord, 1);
            }
        }

        //gaseste numarul cel mai mare de repetari ale unui cuvant
        int maxAppearances = 0;
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            //System.out.println(entry.getKey() + " : " + entry.getValue());         //printeaza tot mapul
            int value = entry.getValue();
            if(value > maxAppearances) {
                maxAppearances = value;
            }
        }

        //gaseste cuvantul cu maximul de aparitii
        for (Object o : words.keySet()) {
            if (words.get(o).equals(maxAppearances)) {
                System.out.println("The word " + o + " appears " + maxAppearances + " times");
            }
        }

        //apel metoda de sortare a hashmapului dupa nr de aparitii al cuvintelor si printarea lor
        Map<String, Integer> result =MostFrequentWordClass.sortByValue(words);
        System.out.println(result);
    }



    public static void main(String[] args) {

        FileOperations fo = new FileOperationImpl1();
        DemoFIles obj = new DemoFIles();
        List<String> poemList = fo.readFromFileAsList("luc.txt");
        String poem = fo.readFromFile("luc.txt");

        //cate versuri are
        int versuri = obj.lyrics(poemList);
        System.out.println("The poem has " + versuri + " lyrics.");

        //cate strofe are?
        int strofe = obj.numberStrofe(poemList);
        System.out.println("The poem has " + strofe + " strofe.");

        //cat se repeta un cuvant
        int word = obj.repeatingWord(poemList);
        System.out.println("Word stele is encountered " + word + " times.");

        //cuvinte neunice
        obj.nonuniqueWords(poem);

        //cuvinte unice
        int words = obj.uniqueWords(poem);
        System.out.println("The poem contains " + words + " unique words.");

        //de cate ori apare un cuvant anume
        obj.repeatingWantedWord(poem);

        //versuri cu mai putin de 4 cuvinte
        obj.shortVerses(poemList);

        //primele cel mai scurt si cel mai lung versuri
        obj.verses(poemList);

        //cel mai lung cuvant din poezie
        obj.longestWord(poem);

        //de cate ori se gasesc cuvintele cu hashMap
        obj.mostFrequentWord(poem);

    }
}
