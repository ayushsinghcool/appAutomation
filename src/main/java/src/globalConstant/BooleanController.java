package src.globalConstant;

public class BooleanController {
    private BooleanController(){

    }

    private static boolean isFirstTimeOnboarding = true;

    public static boolean getFirstTimeOnboarding() {
        return isFirstTimeOnboarding;
    }
    public static void setFirstTimeOnboarding(boolean value) {
        isFirstTimeOnboarding = value;
    }

}
