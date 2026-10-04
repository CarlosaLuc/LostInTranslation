package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the services of: <br/>
 * - converting language codes to their names <br/>
 * - converting language names to their codes
 */
public class LanguageCodeConverter {

    private final Map<String, String> languageCodeToLanguage = new HashMap<>();
    private final Map<String, String> languageToLanguageCode = new HashMap<>();

    /**
     * Default constructor that loads the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {

        this("language-codes.txt");
    }

    /**
     * Overloaded constructor that allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resources file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] split = line.split("\t");
                // well, I've been misleading by chatGPT, looks like we have a much easier method to split...
                // but I guess we can learn some new technique lol. Below are my previous method and explanation.
                // String[] split = line.trim().split("\\s+(?=\\S+$)", 2);
                // Split the line at the whitespace before the final token, so language names
                // containing spaces remain intact.
                // .trim() means remove leading and trailing whitespace,
                // .split(...) separates the line according to the specified regex pattern.
                // with specific conditions.
                // regex explanations:
                // 1. "\\s+" matches one or more whitespace characters.
                //      e.g., Canada   ca. There are a Tab(> one whitespace) between them.
                // 2. "(?=\\S+$)" ensures we only split at the whitespace before the final token.
                //      ( ： start of the lookahead.
                //      ?=xxx : is a positive lookahead, the following pattern must match the condition xxx,
                //              but it is not consumed as part of the match.
                //      \S : one non-whitespace character
                //      + : one or more of the previous item, so \S+ indicates one or more of the non-whitespace char.
                //      $ : end of the String.
                //      ) : end of the lookahead.
                //      (?=\S+$) means: from the current position, the rest of the string must consist of one or
                //      more non-whitespace characters all the way to the end.
                //      e.g.,Central Khmer	km. There is a space between "Central" and "Khmer",
                //      and some space between "Khmer" and "km". But we only want "km" to be our language code,
                //      which is also the final unempty token in this line
                // limit explanation: The limit of 2 ensures that the line is split into at most two parts.
                String language = split[0];
                String code = split[1];
                languageCodeToLanguage.put(code, language);
                languageToLanguageCode.put(language, code);
                // TODO Task A: use line to populate the instance variables
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return the name of the language for the given language code.
     * @param code the 2-letter language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task A: update this code to use the correct instance variable to return the appropriate value
        return languageCodeToLanguage.get(code);
        // languageCodeToLanguage is a Map<String, String>, which is something like:
        //                      {
        //                        "code1" -> "language1",
        //                        "code2" -> "language2"
        //                        ...
        //                       }
        // key -> value.For here, "code" is the key and "language" is the value.
        // So for this task, we want to get the corresponding value "language" for the key "code",
        // we just need to use map.get(key)
    }

    /**
     * Return the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TODO Task A: update this code to use the correct instance variable to return the appropriate value
        return languageToLanguageCode.get(language);
        // Same method as fromLanguageCode function.
    }

    /**
     * Return how many languages are included in this language code converter.
     * @return how many languages are included in this language code converter.
     */
    public int getNumLanguages() {
        return languageCodeToLanguage.size();
    }
}
