package similarityMeasures;
import org.simmetrics.metrics.JaroWinkler;
import weka.core.Stopwords;

import java.util.Enumeration;

/**
 * Created by Lucho on 07/05/2016.
 * 523dd212d123-3432-5ds43asdgg345gs
 */
public class SimilarityMeasures {

    // Algorithm Taken from http://stackoverflow.com/a/13478355/6302905 and Source Code PHP string.c # similar_text()
    public float similarText(String first, String second)   {
        first = this.normalize(first);
        second = this.normalize(second);
        return (float)(this.similar(first, second)*200)/(first.length()+second.length());
    }

    private int similar(String first, String second)  {
        int p, q, l, sum;
        int pos1=0;
        int pos2=0;
        int max=0;
        char[] arr1 = first.toCharArray();
        char[] arr2 = second.toCharArray();
        int firstLength = arr1.length;
        int secondLength = arr2.length;

        for (p = 0; p < firstLength; p++) {
            for (q = 0; q < secondLength; q++) {
                for (l = 0; (p + l < firstLength) && (q + l < secondLength) && (arr1[p+l] == arr2[q+l]); l++);
                if (l > max) {
                    max = l;
                    pos1 = p;
                    pos2 = q;
                }

            }
        }
        sum = max;
        if (sum > 0) {
            if (pos1 > 0 && pos2 > 0) {
                sum += this.similar(first.substring(0, pos1>firstLength ? firstLength : pos1), second.substring(0, pos2>secondLength ? secondLength : pos2));
            }

            if ((pos1 + max < firstLength) && (pos2 + max < secondLength)) {
                sum += this.similar(first.substring(pos1 + max, firstLength), second.substring(pos2 + max, secondLength));
            }
        }
        return sum;
    }

    public float measureJaroWinkler(String str1, String str2){
        JaroWinkler jw = new JaroWinkler();
        return jw.compare(this.normalize(str1),this.normalize(str2));
    }

    public String normalize(String str){
        //Case normalisation
        //Diacritics suppression
        //s.replaceAll("/[á]+/g","a");
//        s.replaceAll("[é]+","e");
//        s.replaceAll("[í]+","i");
//        s.replaceAll("[ó]+","o");
//        s.replaceAll("[ú]+","u");
//        s.toLowerCase();
        //Blank normalisation
        //s.trim();
        //Link stripping
        //s.replaceAll("-"," ");
        //Digit suppresion
        //s.replaceAll("[0-9]+","");
        //Punctuation elimination
        //s.replaceAll("[.]+","");
        Stopwords sw = new Stopwords();
        sw.clear(); sw.add("has"); sw.add("is"); sw.add("in"); sw.add("its");
        String strg = str.toLowerCase()
                .trim()
                .replace("-"," ")
                .replace("[0-9]+","")
                .replace("[.]+","");
        String nxstr;
        Enumeration el = sw.elements();
        while (el.hasMoreElements()){
            nxstr = el.nextElement().toString();
            if(strg.contains(nxstr)){
                strg = strg.replaceAll(nxstr,"");
            }
        }
        return strg.trim();
    }
}
