package xplorer.br.com.apiidwall.view.utils;

import java.util.regex.Pattern;

public class EmailValidator {

    /**
     * Local Part : [a-zA-Z0-9._!#$%&'*+-/=?^`{|}~"]+
     * Domain: [a-zA-Z0-9](?:[a-zA-Z0-9-]+[a-zA-Z0-9])?(?:\.[a-zA-Z0-9]+)?
     * */
    private static final String regex = "[a-zA-Z0-9._!#$%&'*+-/=?^`{|}~\"]+" +
            "@[a-zA-Z0-9](?:[a-zA-Z0-9-]+[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9]+)?";
    private static final Pattern pattern = Pattern.compile(regex);

    public static boolean validator(String email) {
        return pattern.matcher(email).find();
    }

}
