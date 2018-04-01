package File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FileInputParser {


    public static boolean validateIds(Integer[][] text) {

        Set<Integer> ids = new HashSet<>();

        for (int i=0;i<text.length;i++){
            ids.add(text[i][0]);
        }
        boolean result = true;
        if (ids.size()!=text.length || Collections.min(ids)!=1 || Collections.max(ids)!= text.length)result=false;

        return result;
    }
}
