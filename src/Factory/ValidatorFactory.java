package Factory;

import Validators.*;

public class ValidatorFactory {

    public static Validator getValidator(int mode) {
        if (mode == 0) return new SequentialValidator();
        if (mode == 3) return new ThreeThreadValidator();
        if (mode == 27) return new TwentySevenThreadValidator();
        return new SequentialValidator(); // default
    }
}
