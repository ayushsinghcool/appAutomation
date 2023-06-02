package src.globalConstant;

public class BooleanController {
    private BooleanController(){

    }

    private static boolean isFirstTimeOnboarding = true;
    private static boolean isTIDActivated = false;

    public static boolean getIsTIDActivated() {
        return isTIDActivated;
    }

    public static void setIsTIDActivated(boolean value) {
      isTIDActivated = value;
    }

    public static boolean getFirstTimeOnboarding() {
        return isFirstTimeOnboarding;
    }
    public static void setFirstTimeOnboarding(boolean value) {
        isFirstTimeOnboarding = value;
    }

}
